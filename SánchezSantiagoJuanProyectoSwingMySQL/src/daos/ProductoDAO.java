/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import modelos.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelos.Categoria;

/**
 *Clase encargada de trabajar con la tabla 'productos'.
 * Cuenta con métodos para insertar productos en esta tabla, para listar todos
 * los productos presentes en ella y para listar los productos de una Categoria
 * @author Juan Sánchez
 */
public class ProductoDAO {
    /**
     * Método que devuelve un entero según el éxito en la inserción del producto en la base de datos.
     * Si no se consigue insertar, devuelve 0, de lo contrario devuelve un valor distinto de 0.
     * @param p Producto que se va a insertar en la base de datos.
     * @return  Entero con el valor de éxito en la inserción.
     */
    public static int insertarProducto(Producto p){
        /*
        Se prepara la consulta de SQL, la sentencia a realizar y un entero
        que va a devolver el éxito o fracaso de la inserción
        */
        String sql = "INSERT INTO productos (nombre, precio, stock, categoria_id) VALUES (?, ?, ?, ?)";
        PreparedStatement pst;
        int resultado = 0;
        
        try(Connection conexion = ConexionBBDD.conectar()){
            /*
            Se insertan los valores en la sentencia y se recoge el valor 
            derivado de la ejecución de la sentencia
            */
            pst = conexion.prepareStatement(sql);
            pst.setString(1, p.getNombre());
            pst.setDouble(2, p.getPrecio());
            pst.setInt(3, p.getStock());
            pst.setInt(4, p.getCategoria().getId());
            resultado = pst.executeUpdate();
        } catch (SQLException ex) {
            //Si hay un error en la inserción se manda a consola
            System.out.println("Error en la inserción");
            System.out.println(ex.getMessage());
        }
        //Se devuelve el valor recogido de la sentencia
        return resultado;
    }
    /**
     * Método que devuelve todos los productos que tengan como identificador de 
     * su categoría el entero que se pasa como parámetro.
     * @param categoriaId Entero que representa el identificador de la categoría
     * de los productos a devolver.
     * @return Lista con los objetos Producto obtenidos.
     */
    public static List<Producto> consultarProductos(int categoriaId){
        /*
        Se establece la consulta a SQL, la sentencia, la lista y el ResultSet.
        Los alias para los campos de 'categorias' se usan para poder obtenerlos
        del ResultSet sin recurrir a índices
        */
        String sql = "SELECT p.id, p.nombre, p.precio, p.stock, c.id AS cat_id, c.nombre AS cat_nombre FROM productos p "
                + "LEFT JOIN categorias c on p.categoria_id = c.id WHERE p.categoria_id = ?";
        PreparedStatement pst;
        ResultSet rs;
        List<Producto> productos = new ArrayList();
        
        try(Connection conexion = ConexionBBDD.conectar()){
            //Se insertan los valores de la consulta y se introducen los valores
            //resultantes de la consulta en el ResultSet
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, categoriaId);
            rs = pst.executeQuery();
            
            //Se rellena el mapa con los valores del ResultSet
            while(rs.next()){
                Categoria catProducto = new Categoria(rs.getInt("cat_id"), rs.getString("cat_nombre"));
                productos.add(new Producto(rs.getInt("id"), rs.getString("nombre"), 
                                           rs.getDouble("precio"), rs.getInt("stock"),
                                           catProducto));
            }
        } catch (SQLException ex) {
            System.out.println("Error en la consulta");
            System.out.println(ex.getMessage());
        }
        //Se devueleve la lista
        return productos;
    }
    /**
     * Método que devuelve todos los productos de la base de datos
     * @return Lista con los objetos Producto
     */
    public static List<Producto> consultarTodosProductos(){
        //Se establece la consulta a SQL, la sentencia, la lista y el ResultSet
        String sql = "SELECT p.id, p.nombre, p.precio, p.stock, c.id AS cat_id, c.nombre as cat_nombre FROM productos p "
                + "LEFT JOIN categorias c on p.categoria_id = c.id";
        Statement st;
        ResultSet rs;
        List<Producto> productos = new ArrayList();
        
        try(Connection conexion = ConexionBBDD.conectar()){
            //Se ejecuta la consulta
            st = conexion.createStatement();
            rs = st.executeQuery(sql);
            
            //Se rellena la lista con los valores del ResultSet
            while(rs.next()){
                Categoria catProducto = new Categoria(rs.getInt("cat_id"), rs.getString("cat_nombre"));
                productos.add(new Producto(rs.getInt("id"), rs.getString("nombre"), 
                                           rs.getDouble("precio"), rs.getInt("stock"),
                                           catProducto));
            }
        } catch (SQLException ex) {
            System.out.println("Error en la consulta");
            System.out.println(ex.getMessage());
        }
        //Se devueleve la lista
        return productos;
    }
}
