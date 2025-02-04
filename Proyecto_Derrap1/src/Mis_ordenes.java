import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Mis_ordenes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelOrdenes;
    private int idMecanico; // ID del mecánico que ha iniciado sesión

    // Constructor que recibe el ID del mecánico
    public Mis_ordenes(int idMecanico) {
        this.idMecanico = idMecanico; // Guardar el ID del mecánico
        initialize();
    }

    private void initialize() {
        setTitle("Mis Órdenes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Título
        JLabel lblTitulo = new JLabel("MIS ÓRDENES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(96, 125, 139));
        lblTitulo.setForeground(Color.WHITE);
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        // Botón "VOLVER"
        JButton btnVolver = new JButton("VOLVER");
        btnVolver.addActionListener(e -> {
            Dashboard_mecanico dashboardFrame = new Dashboard_mecanico(idMecanico);
            dashboardFrame.setVisible(true);
            dispose(); // Cerrar la ventana actual
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

        // Cargar órdenes asignadas al mecánico
        cargarOrdenesAsignadas();
    }

    private void cargarOrdenesAsignadas() {
        ConexionMysql conexion = new ConexionMysql();
        try (Connection con = conexion.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("""
                 SELECT 
                     o.id_orden, 
                     o.id_vehiculo, 
                     o.descripcion, 
                     o.Usuarios_dni
                 FROM 
                     ordenes_reparacion o
                 WHERE 
                     o.mecanico_asignado = """ + idMecanico + """
                     AND o.estado != 'Finalizada'  -- Excluir órdenes finalizadas
                 """)) {

            while (rs.next()) {
                String vehiculo = "Vehículo: " + rs.getString("id_vehiculo");
                String descripcion = rs.getString("descripcion");
                String cliente = "Cliente: " + rs.getString("Usuarios_dni");
                int idOrden = rs.getInt("id_orden");

                panelOrdenes.add(crearTarjetaOrden(vehiculo, descripcion, cliente, idOrden));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        panelOrdenes.revalidate();
        panelOrdenes.repaint();
    }

    private JPanel crearTarjetaOrden(String vehiculo, String descripcion, String cliente, int idOrden) {
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

        // Botón "Desasignar"
        JButton btnDesasignar = new JButton("Desasignar");
        btnDesasignar.addActionListener(e -> desasignarOrden(idOrden));
        tarjeta.add(btnDesasignar, BorderLayout.WEST);

        // Botón "Terminar"
        JButton btnTerminar = new JButton("Terminar");
        btnTerminar.addActionListener(e -> terminarOrden(idOrden));
        tarjeta.add(btnTerminar, BorderLayout.EAST);

        return tarjeta;
    }

    private void desasignarOrden(int idOrden) {
        ConexionMysql conexion = new ConexionMysql();
        try (Connection con = conexion.conectar();
             PreparedStatement pstmt = con.prepareStatement("""
                 UPDATE ordenes_reparacion 
                 SET mecanico_asignado = NULL 
                 WHERE id_orden = ?""")) {

            pstmt.setInt(1, idOrden);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Orden desasignada correctamente.");

            // Recargar las órdenes
            panelOrdenes.removeAll();
            cargarOrdenesAsignadas();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al desasignar la orden.");
        }
    }

    private void terminarOrden(int idOrden) {
        ConexionMysql conexion = new ConexionMysql();
        try (Connection con = conexion.conectar();
             PreparedStatement pstmt = con.prepareStatement("""
                 UPDATE ordenes_reparacion 
                 SET estado = 'Finalizada' 
                 WHERE id_orden = ?""")) {

            pstmt.setInt(1, idOrden);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Orden terminada correctamente.");

            // Recargar las órdenes
            panelOrdenes.removeAll();
            cargarOrdenesAsignadas();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al terminar la orden.");
        }
    }
}