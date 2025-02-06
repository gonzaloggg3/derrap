import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard_administrador extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dashboard_administrador frame = new Dashboard_administrador();
                    frame.setVisible(true);
                    frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Dashboard_administrador() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 887, 693); 
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        JLabel lblAdmin = new JLabel("ADMIN");
        lblAdmin.setFont(new Font("Arial", Font.PLAIN, 14));
        lblAdmin.setForeground(Color.BLACK);
        lblAdmin.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAdmin.setBounds(800, 15, 80, 20);
        contentPane.add(lblAdmin);

        JLabel lblTitle = new JLabel("INICIO");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(96, 125, 139));
        lblTitle.setBounds(0, -3, 880, 50);
        contentPane.add(lblTitle);
        
        JButton btnClientes = new JButton("Gestión de clientes");
        btnClientes.setFont(new Font("Arial", Font.PLAIN, 16));
        btnClientes.setBackground(new Color(176, 190, 197));
        btnClientes.setBounds(50, 100, 200, 100);
        
        // Agregar ActionListener para abrir Gestion_clientes
        btnClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Gestion_clientes gestionClientesFrame = new Gestion_clientes();
                gestionClientesFrame.setVisible(true); // Mostrar ventana de gestión de clientes
                gestionClientesFrame.setResizable(false); // Desactivar el redimensionamiento
              
                dispose(); // Opcional: cerrar la ventana actual
            }
        });
        
        contentPane.add(btnClientes);

        JButton btnVehiculos = new JButton("Gestión de vehículos");
        btnVehiculos.setFont(new Font("Arial", Font.PLAIN, 16));
        btnVehiculos.setBackground(new Color(176, 190, 197));
        btnVehiculos.setBounds(300, 100, 200, 100);
        contentPane.add(btnVehiculos);

        JButton btnMecanicos = new JButton("Gestión de mecánicos");
        btnMecanicos.setFont(new Font("Arial", Font.PLAIN, 16));
        btnMecanicos.setBackground(new Color(176, 190, 197));
        btnMecanicos.setBounds(550, 100, 200, 100);
        contentPane.add(btnMecanicos);

        JButton btnOrdenes = new JButton("Órdenes de reparación");
        btnOrdenes.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                Administrador_ordenes administradorOrdenes = new Administrador_ordenes();
                administradorOrdenes.setVisible(true);
        		
        	}
        });
        btnOrdenes.setFont(new Font("Arial", Font.PLAIN, 16));
        btnOrdenes.setBackground(new Color(176, 190, 197));
        btnOrdenes.setBounds(50, 250, 200, 100);
        contentPane.add(btnOrdenes);

        JButton btnRepuestos = new JButton("Gestión de repuestos");
        btnRepuestos.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Admin_Stock AdminStock=new Admin_Stock();
        		AdminStock.setVisible(true);}
        });
        btnRepuestos.setFont(new Font("Arial", Font.PLAIN, 16));
        btnRepuestos.setBackground(new Color(176, 190, 197));
        btnRepuestos.setBounds(300, 250, 200, 100);
        contentPane.add(btnRepuestos);
        

        JButton btnInformes = new JButton("Informes");
        btnInformes.setFont(new Font("Arial", Font.PLAIN, 16));
        btnInformes.setBackground(new Color(176, 190, 197));
        btnInformes.setBounds(550, 250, 200, 100);
        contentPane.add(btnInformes);

        JButton btnPedidos = new JButton("Gestión de pedidos");
        btnPedidos.setFont(new Font("Arial", Font.PLAIN, 16));
        btnPedidos.setBackground(new Color(176, 190, 197));
        btnPedidos.setBounds(50, 400, 200, 100);
        contentPane.add(btnPedidos);

        JButton btnServicios = new JButton("Gestión de servicios");
        btnServicios.setFont(new Font("Arial", Font.PLAIN, 16));
        btnServicios.setBackground(new Color(176, 190, 197));
        btnServicios.setBounds(300, 400, 200, 100);
        contentPane.add(btnServicios);

        JButton btnFacturacion = new JButton("Gestión de facturación");
        btnFacturacion.setFont(new Font("Arial", Font.PLAIN, 16));
        btnFacturacion.setBackground(new Color(176, 190, 197));
        btnFacturacion.setBounds(550, 400, 200, 100);
        contentPane.add(btnFacturacion);

        JButton btnUsuarios = new JButton("Gestión de usuarios");
        btnUsuarios.setFont(new Font("Arial", Font.PLAIN, 16));
        btnUsuarios.setBackground(new Color(176, 190, 197));
        btnUsuarios.setBounds(300, 550, 200, 100); 
        contentPane.add(btnUsuarios);
    }
}