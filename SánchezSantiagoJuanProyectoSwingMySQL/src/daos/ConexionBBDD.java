package daos;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *Clase encargada de conectarse con la base de datos.
 * @author Juan Sánchez
 */
public class ConexionBBDD {
    /**
     * Cadena con la URL que conecta a la base de datos con la que trabajar
     */
    private final static String URL = "jdbc:mysql://localhost:3306/bd_sanchez_proyecto";
    /**
     * Cadena con el usuario con el que acceder a la base de datos
     */
    private final static String USER = "root";
    /**
     * Cadena con la contraseña del usuario que accede a la base de datos
     */
    private final static String PASS = "";
    
    /**
     * Método que devuelve una conexión a la base de datos si se tiene éxito.
     * De lo contrario devuelve null.
     * @return Connection con la conexión a la base de datos
     */
    public static Connection conectar(){
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            //Se manda un error a la consola y al usuario en caso de que falle la conexión
            System.out.println("Error en la conexión a la base de datos");
            JOptionPane.showMessageDialog(null, "Error en la conexión a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
        return conexion;
    }
}
