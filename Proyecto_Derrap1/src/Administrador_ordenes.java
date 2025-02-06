import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Administrador_ordenes extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JTextField txtIdOrden, txtIdVehiculo, txtIdUsuario, txtMecanico, txtEstado, txtFechaProceso, txtFechaFinalizacion, txtDescripcion;
    private JButton btnCrear, btnActualizar, btnEliminar;
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
        btnCrear.setBackground(new Color(0, 153, 76));
        btnCrear.setForeground(Color.WHITE);
        panel.add(btnCrear);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(0, 102, 204));
        btnActualizar.setForeground(Color.WHITE);
        panel.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(204, 0, 0));
        btnEliminar.setForeground(Color.WHITE);
        panel.add(btnEliminar);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);

        cargarDatos();

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
    }

    private void cargarDatos() {
        ArrayList<String[]> ordenes = conexion.obtenerOrdenesReparacion();
        String[] columnNames = {"Matrícula", "Descripción", "Cliente"};
        Object[][] data = new Object[ordenes.size()][3];

        for (int i = 0; i < ordenes.size(); i++) {
            data[i] = ordenes.get(i);
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void crearOrden() {
        String idVehiculo = txtIdVehiculo.getText();
        String idUsuario = txtIdUsuario.getText();

        if (!tieneClienteAsociado(idVehiculo)) {
            JOptionPane.showMessageDialog(this, "No se puede crear una orden para un vehículo sin cliente asociado.");
            return;
        }

        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO ordenes_reparacion (Vehiculos_idVehiculos, Usuarios_dni, mecanico_seignado, estado, fecha_proreso, fecha_finalizacion, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, idVehiculo);
            stmt.setString(2, idUsuario);
            stmt.setString(3, txtMecanico.getText());
            stmt.setString(4, txtEstado.getText());
            stmt.setString(5, txtFechaProceso.getText());
            stmt.setString(6, txtFechaFinalizacion.getText());
            stmt.setString(7, txtDescripcion.getText());

            stmt.executeUpdate();
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean tieneClienteAsociado(String idVehiculo) {
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vehiculos WHERE idVehiculo = ? AND id_cliente IS NOT NULL")) {

            stmt.setString(1, idVehiculo);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void actualizarOrden() {
        int idOrden = Integer.parseInt(txtIdOrden.getText());

        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE ordenes_reparacion SET Vehiculos_idVehiculos = ?, Usuarios_dni = ?, mecanico_seignado = ?, estado = ?, fecha_proreso = ?, fecha_finalizacion = ?, descripcion = ? WHERE id_orden = ?")) {

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
}