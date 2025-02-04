import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class inicio extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldDNI;
    private JPasswordField passwordField;
    ConexionMysql conexion = new ConexionMysql();
    Connection cn = null;
    Statement stm = null;
    ResultSet resultado = null;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    inicio frame = new inicio();
                    frame.setVisible(true);
                    frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public inicio() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 528);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel JL_Bienvenido = new JLabel("Bienvenido");
        JL_Bienvenido.setForeground(new Color(52, 152, 219));
        JL_Bienvenido.setHorizontalAlignment(SwingConstants.CENTER);
        JL_Bienvenido.setFont(new Font("Arial", Font.BOLD, 28));
        JL_Bienvenido.setBounds(280, 100, 250, 40);
        contentPane.add(JL_Bienvenido);

        JLabel lblDNI = new JLabel("DNI:");
        lblDNI.setForeground(new Color(255, 255, 255));
        lblDNI.setFont(new Font("Arial", Font.PLAIN, 18));
        lblDNI.setBounds(200, 180, 80, 25);
        contentPane.add(lblDNI);

        textFieldDNI = new JTextField();
        textFieldDNI.setBounds(280, 180, 250, 30);
        contentPane.add(textFieldDNI);
        textFieldDNI.setColumns(10);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setForeground(new Color(255, 255, 255));
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        lblPassword.setBounds(170, 230, 100, 25);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(280, 230, 250, 30);
        contentPane.add(passwordField);

        JLabel lblForgotPassword = new JLabel("¿Has olvidado tu contraseña?");
        lblForgotPassword.setForeground(new Color(52, 152, 219));
        lblForgotPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lblForgotPassword.setBounds(280, 270, 250, 25);
        lblForgotPassword.setText("<html><u>¿Has olvidado tu contraseña?</u></html>");
        contentPane.add(lblForgotPassword);

        JLabel jl1 = new JLabel("Talleres Derrap");
        jl1.setForeground(new Color(255, 255, 255));
        jl1.setHorizontalAlignment(SwingConstants.CENTER);
        jl1.setFont(new Font("Arial", Font.BOLD, 36));
        jl1.setOpaque(true);
        jl1.setBackground(new Color(0, 0, 0, 180));
        jl1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        jl1.setBounds(150, 10, 500, 70);
        contentPane.add(jl1);

        // Declarar e inicializar el botón btnAceptar
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Arial", Font.PLAIN, 18));
        btnAceptar.setBounds(350, 320, 100, 40);
        contentPane.add(btnAceptar);

        // Agregar ActionListener al botón btnAceptar
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cn = conexion.conectar();

                // Obtener DNI y contraseña de los campos de texto
                String dni = textFieldDNI.getText();
                String password = new String(passwordField.getPassword());

                try {
                    // Consulta SQL para obtener el rol y el ID del usuario
                    String query = "SELECT id_usuario, rol FROM Usuarios WHERE dni='" + dni + "' AND contraseña='" + password + "'";
                    stm = cn.createStatement();
                    resultado = stm.executeQuery(query);

                    if (resultado.next()) {
                        // Si se encuentra el usuario, obtener el rol y el ID
                        int rol = resultado.getInt("rol");
                        int idUsuario = resultado.getInt("id_usuario");

                        // Verificar el rol y abrir la ventana correspondiente
                        if (rol == 0) {
                            JOptionPane.showMessageDialog(contentPane, "Bienvenido a la pestaña de Administrador");
                            new Dashboard_administrador().setVisible(true);
                        } else if (rol == 1) {
                            JOptionPane.showMessageDialog(contentPane, "Bienvenido a la pestaña de Mecánico");
                            // Redirigir al Dashboard_mecanico y pasar el ID del mecánico
                            new Dashboard_mecanico(idUsuario).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(contentPane, "Rol no reconocido");
                        }

                        // Cierra la ventana de inicio de sesión
                        dispose();
                    } else {
                        // Si las credenciales no son válidas
                        JOptionPane.showMessageDialog(contentPane, "DNI o contraseña incorrectos");
                    }

                    // Cerrar conexiones
                    resultado.close();
                    stm.close();
                    cn.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(contentPane, "Error al conectar con la base de datos");
                }
            }
        });

        // Fondo de la pantalla
        JLabel JL_fonfo = new JLabel("");
        JL_fonfo.setBounds(0, 0, 834, 499);
        JL_fonfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoLogin.jpg")));
        contentPane.add(JL_fonfo);
    }
}