import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Ordenes_reparacion extends JFrame {
    private JPanel contentPane;
    private JPanel panelOrdenes;
    private int idMecanico; // ID del mecánico que ha iniciado sesión

    public Ordenes_reparacion(int idMecanico) {
        this.idMecanico = idMecanico; // Guardar el ID del mecánico
        initialize();
    }

    private void initialize() {
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
            // Cerrar la ventana actual
            dispose();

            // Abrir la ventana del Dashboard_mecanico
            Dashboard_mecanico dashboardFrame = new Dashboard_mecanico(idMecanico);
            dashboardFrame.setVisible(true);
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
             ResultSet rs = stmt.executeQuery("""
                 SELECT 
                     o.id_orden, 
                     o.id_vehiculo, 
                     o.descripcion, 
                     o.Usuarios_dni, 
                     u.Nombre AS nombre_mecanico, 
                     u.apellidos AS apellidos_mecanico
                 FROM 
                     ordenes_reparacion o
                 LEFT JOIN 
                     usuarios u ON o.mecanico_asignado = u.id_usuario
                 WHERE 
                     o.mecanico_asignado IS NULL
                     AND o.estado != 'Finalizada'  -- Excluir órdenes finalizadas
                 """)) {

            while (rs.next()) {
                String vehiculo = "Vehículo: " + rs.getString("id_vehiculo");
                String descripcion = rs.getString("descripcion");
                String cliente = "Cliente: " + rs.getString("Usuarios_dni");
                int idOrden = rs.getInt("id_orden");
                String nombreMecanico = rs.getString("nombre_mecanico");
                String apellidosMecanico = rs.getString("apellidos_mecanico");

                // Si el mecánico está asignado, mostrar su nombre y apellido
                String mecanico = (nombreMecanico != null && apellidosMecanico != null) 
                    ? "Mecánico: " + nombreMecanico + " " + apellidosMecanico 
                    : "Mecánico: No asignado";

                panelOrdenes.add(crearTarjetaOrden(vehiculo, descripcion, cliente, idOrden, mecanico));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        panelOrdenes.revalidate();
        panelOrdenes.repaint();
    }

    private JPanel crearTarjetaOrden(String vehiculo, String descripcion, String cliente, int idOrden, String mecanico) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBorder(new LineBorder(new Color(128, 128, 128), 1));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setPreferredSize(new Dimension(700, 100));

        // Panel de información
        JPanel panelInfo = new JPanel(new GridLayout(4, 1)); // Añadimos una fila para el mecánico
        panelInfo.add(new JLabel(vehiculo, SwingConstants.LEFT));
        panelInfo.add(new JLabel(cliente, SwingConstants.LEFT));
        panelInfo.add(new JLabel(descripcion, SwingConstants.LEFT));
        panelInfo.add(new JLabel(mecanico, SwingConstants.LEFT)); // Mostrar el mecánico
        tarjeta.add(panelInfo, BorderLayout.CENTER);

        // Botón "Asignar Orden"
        JButton btnAsignar = new JButton("ASIGNAR ORDEN");
        btnAsignar.addActionListener(e -> asignarOrden(idOrden));
        tarjeta.add(btnAsignar, BorderLayout.SOUTH);

        return tarjeta;
    }

    private void asignarOrden(int idOrden) {
        ConexionMysql conexion = new ConexionMysql();
        Connection cn = conexion.conectar();
        try {
            // Actualizar la base de datos con el ID del mecánico que ha iniciado sesión
            String query = "UPDATE ordenes_reparacion SET mecanico_asignado = ? WHERE id_orden = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, idMecanico); // Usar el ID del mecánico que ha iniciado sesión
            pstmt.setInt(2, idOrden);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Orden asignada correctamente.");

            // Recargar las órdenes para actualizar la interfaz
            panelOrdenes.removeAll();
            cargarOrdenesDesdeDB();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al asignar la orden.");
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}