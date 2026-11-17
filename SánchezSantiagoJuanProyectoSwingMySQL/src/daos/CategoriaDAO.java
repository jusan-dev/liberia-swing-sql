package daos;
import java.sql.*;
import java.util.ArrayList;
import modelos.Categoria;
/**
 *Clase encargada de trabajar con la tabla 'categorias'.
 * Cuenta con un método para devolver las categorías presentes en la tabla 'categorias'
 * y con otro para seleccionar una Categoria en función de su id
 * @author Juan Sánchez
 */
public class CategoriaDAO {
    /**
     * Método que devuelve una lista con todas las categorías de la tabla
     * 'categorías'.
     * @return ArrayList con todas las categorías existentes en la base de datos
     */
    public static ArrayList<Categoria> consultarCategorias(){
        //Se crea la lista en la que se van a devolver los objetos Categoria
        ArrayList<Categoria> categorias = new ArrayList();
        //Se establece la consulta de SQL
        String sql = "SELECT * FROM categorias";
        ResultSet rs;
        try(Connection conexion = ConexionBBDD.conectar();
            Statement st = conexion.createStatement();){
            //Se ejecuta la consulta y se guarda en el ResultSet
            rs = st.executeQuery(sql);
            //Todos los objetos Categoria se guardan en la lista
            while(rs.next()){
                categorias.add(new Categoria(rs.getInt("id"), rs.getString("nombre")));
            }
        }catch (SQLException ex) {
            System.out.println("Error en la consulta");
            System.out.println(ex.getMessage());
        }
        //Se devuelve la lista
        return categorias;
    }
    /**
     * Método que devuelve la Categoria con el id especificado.
     * @param  id Entero que representa el id de la categoría a buscar
     * @return Categoria encontrada
     */
    public static Categoria consultarCategoria(int id){
        //Se establece la consulta de SQL
        String sql = "SELECT * FROM categorias WHERE id = ?";
        Categoria categoria = null;
        ResultSet rs;
        try(Connection conexion = ConexionBBDD.conectar();
            PreparedStatement pst = conexion.prepareStatement(sql);){
            //Se le da el valor necesario a la consulta
            pst.setInt(1, id);
            //Se ejecuta la consulta y se guarda en el ResultSet
            rs = pst.executeQuery();
            //Si hay una Categoria, se asigna a la variable categoria
            if(rs.next()){
                categoria = new Categoria(rs.getInt("id"), rs.getString("nombre"));
            }
        }catch (SQLException ex) {
            System.out.println("Error en la consulta");
            System.out.println(ex.getMessage());
        }
        //Se devuelve la variable
        return categoria;
    }
}
