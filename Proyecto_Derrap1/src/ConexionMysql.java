import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ConexionMysql {
	
	private static final String CONTROLADOR ="com.mysql.jdbc.Driver";
	private static final String URL="jdbc:mysql://localhost:3306/proyecto_derrap";
	private static String USUARIO ="root";
	private static String CLAVE ="Medac123";
	Connection cn=null;
	Statement stm=null;
	ResultSet resultado=null;
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		

	}
	
	
public Connection conectar() {
		
		try {
		cn=DriverManager.getConnection(URL,USUARIO,CLAVE);
	
		
		System.out.println("Conexion Realizada");
		stm=cn.createStatement();
		}
		 
		catch(SQLException e) {
			
			System.out.print("no se ha realizado");
		}
		return cn;
		
	}
	
	public boolean validarUsuario(String dni, String password) {
	    this.conectar();
	    boolean usuarioValido = false;
	    String query = "SELECT * FROM usuarios WHERE dni = ? AND password = ?";
	    try (PreparedStatement stmt = cn.prepareStatement(query)) {
	        stmt.setString(1, dni);
	        stmt.setString(2, password);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                usuarioValido = true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error en la conexión con la base de datos");
	    } finally {
	        try {
	            if (cn != null) cn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return usuarioValido;
	}
	
	public boolean insertarCliente(String nombre, String apellidos, String dni, String telefono, String email, String direccion, String fechaRegistro) {
	    this.conectar();
	    boolean clienteInsertado = false;
	    String query = "INSERT INTO clientes (nombre, apellidos, dni, telefono, email, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement stmt = cn.prepareStatement(query)) {
	        stmt.setString(1, nombre);
	        stmt.setString(2, apellidos);
	        stmt.setString(3, dni);
	        stmt.setString(4, telefono);
	        stmt.setString(5, email);
	        stmt.setString(6, direccion);
	        stmt.setString(7, fechaRegistro);
	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            clienteInsertado = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (cn != null) cn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return clienteInsertado;
	}
	
	public ArrayList<String[]> obtenerOrdenesReparacion() {
	    this.conectar();
	    ArrayList<String[]> ordenes = new ArrayList<>();
	    String query = """
	        SELECT 
	            o.descripcion, 
	            v.matricula, 
	            c.nombre, 
	            c.apellidos
	        FROM 
	            ordenes_reparacion o
	        JOIN 
	            vehiculos v ON o.Vehiculos_idVehiculos = v.idVehiculo
	        JOIN 
	            clientes c ON v.id_cliente = c.id_cliente
	    """;
	    try (PreparedStatement stmt = cn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            String[] orden = new String[3];
	            orden[0] = rs.getString("matricula");
	            orden[1] = rs.getString("descripcion");
	            orden[2] = rs.getString("nombre") + " " + rs.getString("apellidos");
	            ordenes.add(orden);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (cn != null) cn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return ordenes;
	}
	
	
	
	
	
		}
