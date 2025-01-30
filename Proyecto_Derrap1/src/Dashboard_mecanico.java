import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard_mecanico extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dashboard_mecanico frame = new Dashboard_mecanico();
                    frame.setVisible(true);
                    frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Dashboard_mecanico() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 734, 493);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
                // Etiqueta "Mecanico" en la esquina superior derecha
                JLabel lblMecanico = new JLabel("Mecanico");
                lblMecanico.setFont(new Font("Arial", Font.PLAIN, 14));
                lblMecanico.setForeground(Color.BLACK);
                lblMecanico.setHorizontalAlignment(SwingConstants.RIGHT);
                lblMecanico.setBounds(600, 15, 100, 20);
                contentPane.add(lblMecanico);

        // Título superior
        JLabel lblTitle = new JLabel("INICIO");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(96, 125, 139));
        lblTitle.setBounds(0, 0, 734, 50);
        contentPane.add(lblTitle);

        // Botón Órdenes de reparación
        JButton btnOrdenesReparacion = new JButton("Órdenes de reparación");
        btnOrdenesReparacion.setFont(new Font("Arial", Font.PLAIN, 16));
        btnOrdenesReparacion.setHorizontalAlignment(SwingConstants.CENTER);
        btnOrdenesReparacion.setVerticalAlignment(SwingConstants.CENTER);
        btnOrdenesReparacion.setBackground(new Color(176, 190, 197));
        btnOrdenesReparacion.setBounds(100, 100, 200, 119);
        btnOrdenesReparacion.setIcon(new ImageIcon("/src/img/clientes.png")); 
        btnOrdenesReparacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción del botón
            	Ordenes_reparacion ordenes_reparacion = new Ordenes_reparacion();
            	ordenes_reparacion.setVisible(true); // Mostrar ventana de gestión de clientes
            	ordenes_reparacion.setResizable(false); // Desactivar el redimensionamiento
                dispose(); // Opcional: cerrar la ventana actual
            }
        });
        contentPane.add(btnOrdenesReparacion);

        // Botón Consulta de stock
        JButton btnConsultaStock = new JButton("Consulta de stock");
        btnConsultaStock.setFont(new Font("Arial", Font.PLAIN, 16));
        btnConsultaStock.setHorizontalAlignment(SwingConstants.CENTER);
        btnConsultaStock.setVerticalAlignment(SwingConstants.CENTER);
        btnConsultaStock.setBackground(new Color(176, 190, 197));
        btnConsultaStock.setBounds(400, 100, 200, 119);
        btnConsultaStock.setIcon(new ImageIcon("path/to/stock_icon.png")); // Coloca el icono adecuado
        btnConsultaStock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción del botón
            }
        });
        contentPane.add(btnConsultaStock);

        // Botón Solicitud de piezas
        JButton btnSolicitudPiezas = new JButton("Solicitud de piezas");
        btnSolicitudPiezas.setFont(new Font("Arial", Font.PLAIN, 16));
        btnSolicitudPiezas.setHorizontalAlignment(SwingConstants.CENTER);
        btnSolicitudPiezas.setVerticalAlignment(SwingConstants.CENTER);
        btnSolicitudPiezas.setBackground(new Color(176, 190, 197));
        btnSolicitudPiezas.setBounds(100, 250, 200, 100);
        btnSolicitudPiezas.setIcon(new ImageIcon("path/to/request_icon.png")); // Coloca el icono adecuado
        btnSolicitudPiezas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción del botón
            }
        });
        
        contentPane.add(btnSolicitudPiezas);

        // Botón Buscar vehículos
        JButton btnBuscarVehiculos = new JButton("Buscar vehículos");
        btnBuscarVehiculos.setFont(new Font("Arial", Font.PLAIN, 16));
        btnBuscarVehiculos.setHorizontalAlignment(SwingConstants.CENTER);
        btnBuscarVehiculos.setVerticalAlignment(SwingConstants.CENTER);
        btnBuscarVehiculos.setBackground(new Color(176, 190, 197));
        btnBuscarVehiculos.setBounds(400, 250, 200, 100);
        btnBuscarVehiculos.setIcon(new ImageIcon("path/to/vehicle_icon.png")); // Coloca el icono adecuado
        btnBuscarVehiculos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción del botón
            }
        });
        contentPane.add(btnBuscarVehiculos);
    }
    
   
}
