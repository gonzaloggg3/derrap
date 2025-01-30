import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Modificacion_Clientes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtDni, txtNombre, txtApellidos, txtTelefono, txtEmail, txtDireccion, txtFechaRegistro;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Modificacion_Clientes frame = new Modificacion_Clientes();
                frame.setVisible(true);
                frame.setResizable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Modificacion_Clientes() {
        setTitle("Modificar Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(96, 125, 139));
        headerPanel.setBounds(0, 0, 800, 70);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);

        JLabel lblTitulo = new JLabel("MODIFICAR CLIENTE");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 0, 800, 70);
        headerPanel.add(lblTitulo);

        JButton btnVolver = new JButton("VOLVER");
        btnVolver.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		
        Gestion_clientes gestionClientes = new Gestion_clientes();
        gestionClientes.setVisible(true);
        		        
        		        // Cerrar la ventana actual
        		        dispose();
        		    
        	
        	}
        });
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 14));
        btnVolver.setBackground(new Color(69, 90, 100));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBounds(680, 20, 90, 30);
        headerPanel.add(btnVolver);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.LIGHT_GRAY);
        formPanel.setBounds(150, 100, 500, 400);
        formPanel.setLayout(new GridLayout(7, 1, 10, 10));
        contentPane.add(formPanel);

        txtDni = addFormField(formPanel, "DNI:");
        txtNombre = addFormField(formPanel, "Nombre:");
        txtApellidos = addFormField(formPanel, "Apellidos:");
        txtTelefono = addFormField(formPanel, "Teléfono:");
        txtEmail = addFormField(formPanel, "Email:");
        txtDireccion = addFormField(formPanel, "Dirección:");
        txtFechaRegistro = addFormField(formPanel, "Fecha de Registro:");

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        formPanel.add(buttonPanel);

        JButton btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnBuscar.setBackground(new Color(33, 150, 243));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarCliente());
        buttonPanel.add(btnBuscar);

        JButton btnModificar = new JButton("MODIFICAR");
        btnModificar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnModificar.setBackground(new Color(76, 175, 80));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.addActionListener(e -> modificarCliente());
        buttonPanel.add(btnModificar);
    }

    private JTextField addFormField(JPanel panel, String labelText) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setBackground(Color.LIGHT_GRAY);
        fieldPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        fieldPanel.add(label, BorderLayout.NORTH);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 30));
        fieldPanel.add(textField, BorderLayout.CENTER);

        panel.add(fieldPanel);
        return textField;
    }

    private void buscarCliente() {
        String dni = txtDni.getText();
        try {
            ConexionMysql conexion = new ConexionMysql();
            Connection cn = conexion.conectar();
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM clientes WHERE dni = ?");
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtApellidos.setText(rs.getString("apellidos"));
                txtTelefono.setText(rs.getString("telefono"));
                txtEmail.setText(rs.getString("email"));
                txtDireccion.setText(rs.getString("direccion"));
                txtFechaRegistro.setText(rs.getString("fecha_registro"));
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void modificarCliente() {
        String dni = txtDni.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();
        String direccion = txtDireccion.getText();
        String fechaRegistro = txtFechaRegistro.getText();

        try {
            ConexionMysql conexion = new ConexionMysql();
            Connection cn = conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(
                "UPDATE clientes SET nombre = ?, apellidos = ?, telefono = ?, email = ?, direccion = ?, fecha_registro = ? WHERE dni = ?"
            );
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, telefono);
            ps.setString(4, email);
            ps.setString(5, direccion);
            ps.setString(6, fechaRegistro);
            ps.setString(7, dni);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Cliente modificado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al modificar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}