import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Administrador_ordenes extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JTextField txtIdOrden, txtIdVehiculo, txtIdUsuario, txtMecanico, txtEstado, txtFechaProceso, txtFechaFinalizacion, txtDescripcion;
    private JButton btnCrear, btnActualizar, btnEliminar, btnVolver; // Botón "Volver" agregado
    private ConexionMysql conexion;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Administrador_ordenes frame = new Administrador_ordenes();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Administrador_ordenes() {
        conexion = new ConexionMysql();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(0, 2, 10, 10));

        panel.add(new JLabel("ID Orden:"));
        txtIdOrden = new JTextField();
        panel.add(txtIdOrden);
        txtIdOrden.setColumns(10);

        panel.add(new JLabel("ID Vehículo:"));
        txtIdVehiculo = new JTextField();
        panel.add(txtIdVehiculo);
        txtIdVehiculo.setColumns(10);

        panel.add(new JLabel("ID Usuario:"));
        txtIdUsuario = new JTextField();
        panel.add(txtIdUsuario);
        txtIdUsuario.setColumns(10);

        panel.add(new JLabel("Mecánico Asignado:"));
        txtMecanico = new JTextField();
        panel.add(txtMecanico);
        txtMecanico.setColumns(10);

        panel.add(new JLabel("Estado:"));
        txtEstado = new JTextField();
        panel.add(txtEstado);
        txtEstado.setColumns(10);

        panel.add(new JLabel("Fecha Proceso:"));
        txtFechaProceso = new JTextField();
        panel.add(txtFechaProceso);
        txtFechaProceso.setColumns(10);

        panel.add(new JLabel("Fecha Finalización:"));
        txtFechaFinalizacion = new JTextField();
        panel.add(txtFechaFinalizacion);
        txtFechaFinalizacion.setColumns(10);

        panel.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panel.add(txtDescripcion);
        txtDescripcion.setColumns(10);

        btnCrear = new JButton("Crear");
        btnCrear.setBackground(new Color(0, 0, 139)); // Azul oscuro
        btnCrear.setForeground(Color.WHITE);
        panel.add(btnCrear);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(0, 0, 139)); // Azul oscuro
        btnActualizar.setForeground(Color.WHITE);
        panel.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(0, 0, 139)); // Azul oscuro
        btnEliminar.setForeground(Color.WHITE);
        panel.add(btnEliminar);

        // Botón "Volver" agregado
        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(0, 0, 139)); // Azul oscuro
        btnVolver.setForeground(Color.WHITE);
        panel.add(btnVolver);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);

        cargarDatos();

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                Object value;

                value = table.getValueAt(row, 0);
                txtIdOrden.setText(value != null ? value.toString() : "");

                value = table.getValueAt(row, 1);
                txtIdVehiculo.setText(value != null ? value.toString() : "");

                value = table.getValueAt(row, 2);
                txtIdUsuario.setText(value != null ? value.toString() : "");

                value = table.getValueAt(row, 3);
                txtMecanico.setText(value != null ? value.toString() : "");

                value = table.getValueAt(row, 4);
                txtEstado.setText(value != null ? value.toString() : "");

                value = table.getValueAt(row, 5);
                txtFechaProceso.setText(value != null ? value.toString() : "");

                value = table.getValueAt(row, 6);
                txtFechaFinalizacion.setText(value != null ? value.toString() : "");

                value = table.getValueAt(row, 7);
                txtDescripcion.setText(value != null ? value.toString() : "");
            }
        });

        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearOrden();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarOrden();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarOrden();
            }
        });

        // Acción del botón "Volver"
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlDashboard();
            }
        });
    }

    private void cargarDatos() {
        ArrayList<String[]> ordenes = conexion.obtenerOrdenesReparacion2();
        String[] columnNames = {"ID Orden", "ID Vehículo", "ID Usuario", "Mecánico", "Estado", "Fecha Proceso", "Fecha Finalización", "Descripción"};
        Object[][] data = new Object[ordenes.size()][8];

        for (int i = 0; i < ordenes.size(); i++) {
            data[i] = ordenes.get(i);
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void crearOrden() {
        String idVehiculo = txtIdVehiculo.getText();
        String idUsuario = txtIdUsuario.getText();
        String idOrden = txtIdOrden.getText(); // Asegúrate de que este campo esté en tu interfaz

        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO ordenes_reparacion (id_orden, id_vehiculo, id_usuario, mecanico_asignado, estado, fecha_ingreso, fecha_finalizacion, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, idOrden); // Valor para id_orden
            stmt.setString(2, idVehiculo);
            stmt.setString(3, idUsuario);
            stmt.setString(4, txtMecanico.getText());
            stmt.setString(5, txtEstado.getText());
            stmt.setString(6, txtFechaProceso.getText());
            stmt.setString(7, txtFechaFinalizacion.getText());
            stmt.setString(8, txtDescripcion.getText());

            stmt.executeUpdate();
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizarOrden() {
        int idOrden = Integer.parseInt(txtIdOrden.getText());

        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE ordenes_reparacion SET id_vehiculo = ?, id_usuario = ?, mecanico_asignado = ?, estado = ?, fecha_ingreso = ?, fecha_finalizacion = ?, descripcion = ? WHERE id_orden = ?")) {

            stmt.setString(1, txtIdVehiculo.getText());
            stmt.setString(2, txtIdUsuario.getText());
            stmt.setString(3, txtMecanico.getText());
            stmt.setString(4, txtEstado.getText());
            stmt.setString(5, txtFechaProceso.getText());
            stmt.setString(6, txtFechaFinalizacion.getText());
            stmt.setString(7, txtDescripcion.getText());
            stmt.setInt(8, idOrden);

            stmt.executeUpdate();
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarOrden() {
        int idOrden = Integer.parseInt(txtIdOrden.getText());

        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM ordenes_reparacion WHERE id_orden = ?")) {

            stmt.setInt(1, idOrden);
            stmt.executeUpdate();
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para volver al Dashboard
    private void volverAlDashboard() {
        this.dispose(); // Cierra la ventana actual
        Dashboard_administrador dashboard = new Dashboard_administrador();
        dashboard.setVisible(true); // Abre la ventana del Dashboard
    }
}