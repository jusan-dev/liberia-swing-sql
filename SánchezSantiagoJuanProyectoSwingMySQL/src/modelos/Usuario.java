package modelos;
/**
 *Clase usuario con un nombre y contraseña.
 * Cuenta con métodos para devolver todos sus datos.
 * No cuenta con un identificador porque es un atributo que solo tiene sentido
 * en la base de datos.
 * @author Juan Sánchez
 */
public class Usuario {
    /**
     * Cadena que representa el nombre del usuario
     */
    private String nombre;
    /**
     * Cadena que representa la contraseña del usuario
     */
    private String password;
    /**
     * Constructor completo del usuario
     * @param nombre    Cadena con el nombre del usuario
     * @param password  Cadena con la contraseña del usuario
     */
    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }
    /**
     * Devuelve el nombre del usuario
     * @return Cadena con el nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Devuelve la contraseña del usuario
     * @return Cadena con la contraseña del usuario
     */
    public String getPassword() {
        return password;
    }
}
