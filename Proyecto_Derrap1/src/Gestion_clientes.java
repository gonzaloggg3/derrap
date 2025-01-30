import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gestion_clientes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Gestion_clientes frame = new Gestion_clientes();
                    frame.setVisible(true);
                    frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Gestion_clientes() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 734, 493);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Botón "HOME" reemplazando a "Admin"
        JButton btnHome = new JButton("HOME");
        btnHome.setFont(new Font("Arial", Font.PLAIN, 14));
        btnHome.setForeground(Color.BLACK);
        btnHome.setBounds(600, 15, 100, 30);
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dashboard_administrador dashboardFrame = new Dashboard_administrador();
                dashboardFrame.setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnHome);

        // Título superior
        JLabel lblTitle = new JLabel("GESTION DE CLIENTES");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(96, 125, 139));
        lblTitle.setBounds(0, 0, 734, 50);
        contentPane.add(lblTitle);

        // Botón "Crear Cliente"
        JButton btnCrear = new JButton("Crear Cliente");
        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Creacion_clientes crearClientesFrame = new Creacion_clientes();
                crearClientesFrame.setVisible(true);
                crearClientesFrame.setResizable(false);
            }
        });
        btnCrear.setFont(new Font("Arial", Font.PLAIN, 16));
        btnCrear.setHorizontalAlignment(SwingConstants.CENTER);
        btnCrear.setVerticalAlignment(SwingConstants.CENTER);
        btnCrear.setBackground(new Color(176, 190, 197));
        btnCrear.setBounds(100, 100, 200, 100);
        contentPane.add(btnCrear);

        // Botón "Consultar Cliente"
        JButton btnConsultar = new JButton("Consultar Cliente");
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Consultar_Cliente consultarClientesFrame = new Consultar_Cliente();
                consultarClientesFrame.setVisible(true);
            }
        });
        btnConsultar.setFont(new Font("Arial", Font.PLAIN, 16));
        btnConsultar.setHorizontalAlignment(SwingConstants.CENTER);
        btnConsultar.setVerticalAlignment(SwingConstants.CENTER);
        btnConsultar.setBackground(new Color(176, 190, 197));
        btnConsultar.setBounds(400, 100, 200, 100);
        contentPane.add(btnConsultar);

        // Botón "Eliminar Cliente"
        JButton btnEliminar = new JButton("Eliminar Cliente");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Eliminacion_Clientes eliminarClientesFrame = new Eliminacion_Clientes();
                eliminarClientesFrame.setVisible(true);
                eliminarClientesFrame.setResizable(false);
            }
        });
        btnEliminar.setFont(new Font("Arial", Font.PLAIN, 16));
        btnEliminar.setHorizontalAlignment(SwingConstants.CENTER);
        btnEliminar.setVerticalAlignment(SwingConstants.CENTER);
        btnEliminar.setBackground(new Color(176, 190, 197));
        btnEliminar.setBounds(100, 250, 200, 100);
        contentPane.add(btnEliminar);

        // Botón "Modificar Cliente"
        JButton btnModificar = new JButton("Modificar Cliente");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Modificacion_Clientes modificarClientesFrame = new Modificacion_Clientes();
                modificarClientesFrame.setVisible(true);
                modificarClientesFrame.setResizable(false);
            }
        });
        btnModificar.setFont(new Font("Arial", Font.PLAIN, 16));
        btnModificar.setHorizontalAlignment(SwingConstants.CENTER);
        btnModificar.setVerticalAlignment(SwingConstants.CENTER);
        btnModificar.setBackground(new Color(176, 190, 197));
        btnModificar.setBounds(400, 250, 200, 100);
        contentPane.add(btnModificar);
    }
}