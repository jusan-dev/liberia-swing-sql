/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 * Clase Categoría con un id y un nombre.
 * Sus métodos son los getters de sus atributos y un toString.
 * @author Juan Sánchez
 */
public class Categoria {
    /**
     * Entero que representa el identificador de la categoría
     */
    private int id;
    /**
     * Cadena que repreenta el nombre de la categoría
     */
    private String nombre;
    
    /**
     * Constructor completo de la clase Categoria
     * @param id     Entero con el id de la categoría
     * @param nombre Cadena con el nombre de la categoría
     */
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Devuelve el id de la categoría
     * @return Entero con el id de la categoría
     */
    public int getId() {
        return id;
    }
    /**
     * Devuelve el nombre de la categoría
     * @return Cadena con el nombre de la categoría
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Devuelve una cadena con el nombre de la categoría
     * @return Cadena con el nombre de la categoría
     */
    @Override
    public String toString() {
        return nombre;
    }
    
    
}
