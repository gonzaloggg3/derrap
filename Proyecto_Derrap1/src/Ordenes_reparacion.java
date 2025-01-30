import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Ordenes_reparacion extends JFrame {
    private JPanel contentPane;
    private JPanel panelOrdenes;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Ordenes_reparacion frame = new Ordenes_reparacion();
                frame.setVisible(true);
                frame.setResizable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Ordenes_reparacion() {
        setTitle("Órdenes de Reparación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Título
        JLabel lblTitulo = new JLabel("ÓRDENES DE REPARACIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(96, 125, 139));
        lblTitulo.setForeground(Color.WHITE);
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        // Botón "VOLVER"
        JButton btnVolver = new JButton("VOLVER");
        btnVolver.addActionListener(e -> {
            // Código para volver al dashboard
            dispose();
        });
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);
        panelSuperior.add(btnVolver, BorderLayout.EAST);
        contentPane.add(panelSuperior, BorderLayout.NORTH);

        // Panel de órdenes con Scroll
        panelOrdenes = new JPanel();
        panelOrdenes.setLayout(new BoxLayout(panelOrdenes, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelOrdenes);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Cargar órdenes de la base de datos
        cargarOrdenesDesdeDB();
    }

    private void cargarOrdenesDesdeDB() {

        ConexionMysql conexion = new ConexionMysql();
        Connection cn = conexion.conectar();
        try (Connection con = conexion.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM ordenes_reparacion")) {

            while (rs.next()) {
                String vehiculo = "Vehículo: " + rs.getString("id_vehiculo");
                String descripcion = rs.getString("descripcion");
                String cliente = "Cliente: " + rs.getString("Usuarios_dni");
                panelOrdenes.add(crearTarjetaOrden(vehiculo, descripcion, cliente));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        panelOrdenes.revalidate();
        panelOrdenes.repaint();
    }

    private JPanel crearTarjetaOrden(String vehiculo, String descripcion, String cliente) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBorder(new LineBorder(new Color(128, 128, 128), 1));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setPreferredSize(new Dimension(700, 100));

        // Panel de información
        JPanel panelInfo = new JPanel(new GridLayout(3, 1));
        panelInfo.add(new JLabel(vehiculo, SwingConstants.LEFT));
        panelInfo.add(new JLabel(cliente, SwingConstants.LEFT));
        panelInfo.add(new JLabel(descripcion, SwingConstants.LEFT));
        tarjeta.add(panelInfo, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnTerminar = new JButton("TERMINAR");
        JButton btnModificar = new JButton("MODIFICAR");
        panelBotones.add(btnTerminar);
        panelBotones.add(btnModificar);
        tarjeta.add(panelBotones, BorderLayout.SOUTH);

        return tarjeta;
    }
}