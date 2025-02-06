import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Administrador_ordenes extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JTextField txtIdOrden, txtIdVehiculo, txtIdUsuario, txtMecanico, txtEstado, txtFechaProceso, txtFechaFinalizacion, txtDescripcion;
    private JButton btnCrear, btnActualizar, btnEliminar;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Administrador_ordenes frame = new Administrador_ordenes();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Administrador_ordenes() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(0, 2, 0, 0));

        panel.add(new JLabel("ID Orden:"));
        txtIdOrden = new JTextField();
        panel.add(txtIdOrden);
        txtIdOrden.setColumns(10);

        panel.add(new JLabel("ID Vehículo:"));
        txtIdVehiculo = new JTextField();
        panel.add(txtIdVehiculo);
        txtIdVehiculo.setColumns(10);

        panel.add(new JLabel("ID Usuario:"));
        txtIdUsuario = new JTextField();
        panel.add(txtIdUsuario);
        txtIdUsuario.setColumns(10);

        panel.add(new JLabel("Mecánico Asignado:"));
        txtMecanico = new JTextField();
        panel.add(txtMecanico);
        txtMecanico.setColumns(10);

        panel.add(new JLabel("Estado:"));
        txtEstado = new JTextField();
        panel.add(txtEstado);
        txtEstado.setColumns(10);

        panel.add(new JLabel("Fecha Proceso:"));
        txtFechaProceso = new JTextField();
        panel.add(txtFechaProceso);
        txtFechaProceso.setColumns(10);

        panel.add(new JLabel("Fecha Finalización:"));
        txtFechaFinalizacion = new JTextField();
        panel.add(txtFechaFinalizacion);
        txtFechaFinalizacion.setColumns(10);

        panel.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panel.add(txtDescripcion);
        txtDescripcion.setColumns(10);

        btnCrear = new JButton("Crear");
        panel.add(btnCrear);

        btnActualizar = new JButton("Actualizar");
        panel.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        panel.add(btnEliminar);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);

        // Aquí puedes cargar los datos de la base de datos en la tabla
        cargarDatos();

        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearOrden();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarOrden();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarOrden();
            }
        });
    }

    private void cargarDatos() {
        // Cargar datos de la base de datos en la tabla
    }

    private void crearOrden() {
        // Lógica para crear una nueva orden
    }

    private void actualizarOrden() {
        // Lógica para actualizar una orden existente
    }

    private void eliminarOrden() {
        // Lógica para eliminar una orden
    }
}
