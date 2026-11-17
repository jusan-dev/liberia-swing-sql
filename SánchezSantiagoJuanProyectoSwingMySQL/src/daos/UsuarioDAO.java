package daos;

import java.sql.*;
import java.sql.SQLException;
import modelos.Usuario;

/**
 *Clase encargada de trabajar con la tabla 'usuarios'.
 * Cuenta con un método que comprueba que el usuario que se pasa por parámetros existe.
 * @author Juan Sánchez
 */
public class UsuarioDAO {
    /**
     * Método que comprueba que el usuario que se introduce está en la base de datos.
     * Para eso, extrae de la base de datos el usuario que se pasa por parámetros.
     * @param usuario Objeto Usuario que se busca en la base de datos.
     * @return False si no se encuentra, true si se da un usuario con esos atributos
     * en la base de datos.
     */
    public static boolean existePersona(Usuario usuario) {
        //Se prepara la consulta de SQL, la sentencia, el ResultSet y el Usuario
        String sql = "SELECT usuario, password FROM usuarios WHERE usuario = ? AND password = ?";
        PreparedStatement pst;
        ResultSet rs;
        Usuario usuarioBuscado = null;
        try (Connection conexion = ConexionBBDD.conectar()) {
            //Se insertan los valores de la sentencia y se recoge su producto
            //en un ResultSet
            pst = conexion.prepareStatement(sql);
            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getPassword());
            rs = pst.executeQuery();
            //Como solo puede devolverse una fila como máximo, solo se comprueba 
            //una vez si hay algún conjunto. En una base de datos de usuarios 
            //nunca hay dos usuarios con el mismo nombre y contraseña.
            if (rs.next()) {
                usuarioBuscado = new Usuario(rs.getString("usuario"), rs.getString("password"));
            }
        } catch (SQLException ex) {
            System.out.println("Error en la consulta");
            System.out.println(ex.getMessage());
        }
        //Como usuarioBuscado solo tendrá un valor no null si la sentencia encuentra algo
        //no hace falta tratar los datos que hay en la variable para devolver un
        //booleano
        return usuarioBuscado != null;
    }
}
