import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Gestion_Facturacion extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JTextField txtPiezas, txtFechaEmision, txtTotal, txtMetodoPago, txtIdServicio, txtEstadoPago;
    private JButton btnGenerarFactura, btnVolver;
    private ConexionMysql conexion;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Gestion_Facturacion frame = new Gestion_Facturacion();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Gestion_Facturacion() {
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

        panel.add(new JLabel("Piezas:"));
        txtPiezas = new JTextField();
        panel.add(txtPiezas);
        txtPiezas.setColumns(10);

        panel.add(new JLabel("Fecha Emisión:"));
        txtFechaEmision = new JTextField();
        panel.add(txtFechaEmision);
        txtFechaEmision.setColumns(10);

        panel.add(new JLabel("Total:"));
        txtTotal = new JTextField();
        panel.add(txtTotal);
        txtTotal.setColumns(10);

        panel.add(new JLabel("Método Pago:"));
        txtMetodoPago = new JTextField();
        panel.add(txtMetodoPago);
        txtMetodoPago.setColumns(10);

        panel.add(new JLabel("ID Servicio:"));
        txtIdServicio = new JTextField();
        panel.add(txtIdServicio);
        txtIdServicio.setColumns(10);

        panel.add(new JLabel("Estado Pago:"));
        txtEstadoPago = new JTextField();
        panel.add(txtEstadoPago);
        txtEstadoPago.setColumns(10);

        btnGenerarFactura = new JButton("Generar Factura");
        btnGenerarFactura.setBackground(new Color(0, 0, 139)); // Azul oscuro
        btnGenerarFactura.setForeground(Color.WHITE);
        panel.add(btnGenerarFactura);

        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(0, 0, 139)); // Azul oscuro
        btnVolver.setForeground(Color.WHITE);
        panel.add(btnVolver);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);

        cargarOrdenesFinalizadas();

        btnGenerarFactura.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generarFactura();
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlDashboard();
            }
        });
    }

    private void cargarOrdenesFinalizadas() {
        ArrayList<String[]> ordenes = conexion.obtenerOrdenesFinalizadas();
        String[] columnNames = {"ID Orden", "ID Vehículo", "ID Usuario", "Mecánico", "Estado", "Fecha Proceso", "Fecha Finalización", "Descripción"};
        Object[][] data = new Object[ordenes.size()][8];

        for (int i = 0; i < ordenes.size(); i++) {
            data[i] = ordenes.get(i);
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void generarFactura() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una orden para generar la factura.");
            return;
        }

        String idOrden = table.getValueAt(selectedRow, 0).toString();
        String idVehiculo = table.getValueAt(selectedRow, 1).toString();
        String idUsuario = table.getValueAt(selectedRow, 2).toString();
        String descripcion = table.getValueAt(selectedRow, 7).toString();

        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO factura (piezas, fecha_emision, total, metodo_pago, id_servicio, estado_pago) VALUES (?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, txtPiezas.getText());
            stmt.setString(2, txtFechaEmision.getText());
            stmt.setString(3, txtTotal.getText());
            stmt.setString(4, txtMetodoPago.getText());
            stmt.setString(5, txtIdServicio.getText());
            stmt.setString(6, txtEstadoPago.getText());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Factura generada correctamente.");
            cargarOrdenesFinalizadas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void volverAlDashboard() {
        this.dispose(); // Cierra la ventana actual
        Dashboard_administrador dashboard = new Dashboard_administrador();
        dashboard.setVisible(true); // Abre la ventana del Dashboard
    }
}