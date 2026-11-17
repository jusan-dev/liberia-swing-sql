/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *Clase producto con un identificador, nombre, precio, cantidad y su categoría.
 * Cuenta con métodos para recuperar todos sus datos.
 * @author Juan Sánchez
 */
public class Producto{
    /**
     * Entero que representa el identificador del producto
     */
    private int id;
    /**
     * Cadena que representa el nombre del producto
     */
    private String nombre;
    /**
     * Double que representa el precio del producto
     */
    private double precio;
    /**
     * Entero que representa la cantidad de productos con los que se cuenta
     */
    private int stock;
    /**
     * Objeto que representa la categoría a la que pertence el producto
     */
    private Categoria categoria;
    
    /**
     * Constructor completo de la clase Producto
     * @param id          Entero con el identifcador del producto
     * @param nombre      Cadena con el nombre del producto
     * @param precio      Double con el precio del producto
     * @param stock       Entero con el número de productos
     * @param categoria   Categoria con la categoría del producto
     */
    public Producto(int id, String nombre, double precio, int stock, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }
    /**
     * Contructor sin id para la inserción de productos desde la interfaz gráfica
     * @param nombre      Cadena con el nombre del producto
     * @param precio      Double con el precio del producto
     * @param stock       Entero con el número de productos
     * @param categoria   Categoria con la categoría del producto
     */
    public Producto(String nombre, double precio, int stock, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }
    
    /**
     * Devuelve el identificador del producto
     * @return Entero con el identificador del producto
     */
    public int getId() {
        return id;
    }
    /**
     * Devuelve el nombre del producto
     * @return Cadena con el nombre del producto
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Devuelve el precio del producto
     * @return Double con el precio del producto
     */
    public double getPrecio() {
        return precio;
    }
    /**
     * Devuelve el stock del producto
     * @return Entero con el stock del producto
     */
    public int getStock(){
        return stock;
    }
    /**
     * Devuelve la categoría del producto
     * @return Categoria con la categoría del producto
     */
    public Categoria getCategoria() {
        return categoria;
    }     
}
