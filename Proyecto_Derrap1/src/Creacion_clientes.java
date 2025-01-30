import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Creacion_clientes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtDNI;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtDireccion;
    private JTextField txtFechaRegistro;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Creacion_clientes frame = new Creacion_clientes();
                frame.setVisible(true);
                frame.setResizable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Creacion_clientes() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        JLabel lblTitle = new JLabel("CREAR CLIENTE");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(0, 0, 800, 70);
        headerPanel.add(lblTitle);

        JButton btnVolver = new JButton("VOLVER");
        btnVolver.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Gestion_clientes dashboardFrame = new Gestion_clientes();
                dashboardFrame.setVisible(true);
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
        formPanel.setLayout(new GridLayout(8, 1, 10, 10));
        contentPane.add(formPanel);

        // Input Fields
        txtNombre = addFormField(formPanel, "Nombre:");
        txtApellidos = addFormField(formPanel, "Apellidos:");
        txtDNI = addFormField(formPanel, "DNI:");
        txtTelefono = addFormField(formPanel, "Teléfono:");
        txtCorreo = addFormField(formPanel, "Correo:");
        txtDireccion = addFormField(formPanel, "Dirección:");
        txtFechaRegistro = addFormField(formPanel, "Fecha de Registro:");

        // Save Button
        JButton btnGuardar = new JButton("CREAR");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.setBackground(new Color(33, 33, 33));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarCliente();
            }
        });

        btnGuardar.setPreferredSize(new Dimension(150, 40));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(btnGuardar);
        formPanel.add(buttonPanel);
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

    private void guardarCliente() {
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String dni = txtDNI.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String direccion = txtDireccion.getText();
        String fechaRegistro = txtFechaRegistro.getText();

        // Verifica que todos los campos estén llenos
        if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || telefono.isEmpty() || correo.isEmpty() || direccion.isEmpty() || fechaRegistro.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Conexión con la base de datos
            ConexionMysql conexion = new ConexionMysql();
            boolean success = conexion.insertarCliente(nombre, apellidos, dni, telefono, correo, direccion, fechaRegistro);

            if (success) {
                JOptionPane.showMessageDialog(this, "Cliente creado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos(); // Limpia los campos después de guardar
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el cliente en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtDNI.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtFechaRegistro.setText("");
    }
}