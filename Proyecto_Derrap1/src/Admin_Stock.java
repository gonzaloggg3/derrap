import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;

public class Admin_Stock extends JFrame {
//hhfdhfoehfhs
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtDNI;
    private JTextArea txtResultado;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Admin_Stock frame = new Admin_Stock();
                frame.setVisible(true);
                frame.setResizable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Admin_Stock() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(96, 125, 139));
        headerPanel.setBounds(0, 0, 800, 70);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);

        JLabel lblTitle = new JLabel("CONSULTAR CLIENTE");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(0, 0, 800, 70);
        headerPanel.add(lblTitle);

        JButton btnVolver = new JButton("VOLVER");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 14));
        btnVolver.setBackground(new Color(69, 90, 100));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBounds(680, 20, 90, 30);
        headerPanel.add(btnVolver);
        btnVolver.addActionListener(e -> dispose());

        // DNI input field
        JLabel lblDNI = new JLabel("Introduce DNI de cliente:");
        lblDNI.setFont(new Font("Arial",  Font.PLAIN, 16));
        lblDNI.setBounds(200, 120, 200, 30);
        contentPane.add(lblDNI);

        txtDNI = new JTextField();
        txtDNI.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDNI.setBounds(200, 160, 400, 30);
        contentPane.add(txtDNI);
        txtDNI.setColumns(10);

        // Search button
        JButton btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 16));
        btnBuscar.setBackground(new Color(33, 33, 33));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setBounds(320, 210, 150, 40);
        contentPane.add(btnBuscar);

        // Results display area
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Arial", Font.PLAIN, 14));
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(txtResultado);
        scrollPane.setBounds(200, 280, 400, 200);
        contentPane.add(scrollPane);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCliente();
            }
        });
    }

    private void buscarCliente() {
        String dni = txtDNI.getText().trim();
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce un DNI.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ConexionMysql conexion = new ConexionMysql();
        Connection cn = conexion.conectar();
        String query = "SELECT * FROM clientes WHERE dni = ?";
        try (PreparedStatement stmt = cn.prepareStatement(query)) {
            stmt.setString(1, dni);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String resultado = "CLIENTE\n";
                    resultado += "Nombre: " + rs.getString("nombre") + "\n";
                    resultado += "Apellidos: " + rs.getString("apellidos") + "\n";
                    resultado += "DNI: " + rs.getString("dni") + "\n";
                    resultado += "Teléfono: " + rs.getString("telefono") + "\n";
                    resultado += "Correo: " + rs.getString("email") + "\n";
                    resultado += "Dirección: " + rs.getString("direccion") + "\n";
                    resultado += "Fecha de Registro: " + rs.getString("fecha_registro") + "\n";
                    txtResultado.setText(resultado);
                } else {
                    txtResultado.setText("");
                    JOptionPane.showMessageDialog(this, "No se encontró ningún cliente con ese DNI.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
