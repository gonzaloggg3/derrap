import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Admin_Stock extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAgregar, btnModificar;
    private Connection cn;

    public Admin_Stock() {
        ConexionMysql conexionMysql = new ConexionMysql();
        this.cn = conexionMysql.conectar();  // Obtener la conexión a la base de datos
        setTitle("Gestión de Stock");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Modelo de la tabla
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Nombre", "Marca", "Stock", "Precio Venta"});
        table = new JTable(model);
        cargarDatos();
        
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Añadir Pieza");
        btnModificar = new JButton("Modificar Precio");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        // Eventos
        btnAgregar.addActionListener(e -> agregarPieza());
        btnModificar.addActionListener(e -> modificarPrecio());
    }
    
    private void cargarDatos() {
        model.setRowCount(0);
        try {
            String query = "SELECT id_pieza, nombre, marca, stock, precio_venta FROM piezas";
            PreparedStatement stmt = cn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id_pieza"), rs.getString("nombre"), rs.getString("marca"), rs.getInt("stock"), rs.getDouble("precio_venta")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void agregarPieza() {
        JTextField nombreField = new JTextField();
        JTextField marcaField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField precioField = new JTextField();
        
        Object[] fields = {
            "Nombre:", nombreField,
            "Marca:", marcaField,
            "Stock:", stockField,
            "Precio Venta:", precioField
        };
        
        int option = JOptionPane.showConfirmDialog(this, fields, "Añadir Pieza", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String query = "INSERT INTO piezas (nombre, marca, stock, precio_venta) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = cn.prepareStatement(query);
                stmt.setString(1, nombreField.getText());
                stmt.setString(2, marcaField.getText());
                stmt.setInt(3, Integer.parseInt(stockField.getText()));
                stmt.setDouble(4, Double.parseDouble(precioField.getText()));
                stmt.executeUpdate();
                cargarDatos();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void modificarPrecio() {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una pieza para modificar.");
            return;
        }
        
        int id = (int) table.getValueAt(fila, 0);
        String precioActual = table.getValueAt(fila, 4).toString();
        String nuevoPrecio = JOptionPane.showInputDialog(this, "Nuevo precio de venta:", precioActual);
        
        if (nuevoPrecio != null) {
            try {
                String query = "UPDATE piezas SET precio_venta = ? WHERE id_pieza = ?";
                PreparedStatement stmt = cn.prepareStatement(query);
                stmt.setDouble(1, Double.parseDouble(nuevoPrecio));
                stmt.setInt(2, id);
                stmt.executeUpdate();
                cargarDatos();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        // Crear y mostrar la interfaz de Admin_Stock
        SwingUtilities.invokeLater(() -> new Admin_Stock().setVisible(true));
    }
}