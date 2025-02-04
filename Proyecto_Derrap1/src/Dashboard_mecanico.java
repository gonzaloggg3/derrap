import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard_mecanico extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int idMecanico; // Variable para almacenar el ID del mecánico

    // Constructor que recibe el ID del mecánico
    public Dashboard_mecanico(int idMecanico) {
        this.idMecanico = idMecanico; // Guardar el ID del mecánico
        initialize();
    }

    private void initialize() {
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
                // Abrir la ventana de órdenes de reparación y pasar el ID del mecánico
                Ordenes_reparacion ordenes_reparacion = new Ordenes_reparacion(idMecanico);
                ordenes_reparacion.setVisible(true); // Mostrar ventana de órdenes de reparación
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

        // Botón Mis Órdenes (antes Solicitud de piezas)
        JButton btnMisOrdenes = new JButton("Mis Órdenes");
        btnMisOrdenes.setFont(new Font("Arial", Font.PLAIN, 16));
        btnMisOrdenes.setHorizontalAlignment(SwingConstants.CENTER);
        btnMisOrdenes.setVerticalAlignment(SwingConstants.CENTER);
        btnMisOrdenes.setBackground(new Color(176, 190, 197));
        btnMisOrdenes.setBounds(100, 250, 200, 100);
        btnMisOrdenes.setIcon(new ImageIcon("path/to/request_icon.png")); // Coloca el icono adecuado
        btnMisOrdenes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana de Mis Órdenes y pasar el ID del mecánico
                Mis_ordenes misOrdenes = new Mis_ordenes(idMecanico);
                misOrdenes.setVisible(true); // Mostrar ventana de Mis Órdenes
                dispose(); // Opcional: cerrar la ventana actual
            }
        });
        contentPane.add(btnMisOrdenes);

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