package controladores;

import ventanas.VentanaLogin;
import daos.UsuarioDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelos.Usuario;

/**
 *Clase Controlador que se encarga de administrar los eventos relacionados con
 * la ventana de inicio de sesión de la aplicación y que inicializa el controlador
 * principal cuando se inicia sesión exitósamente.
 * @author Juan Sánchez
 */
public class ControladorLogin {
    /**
     * Ventana con los componentes para mostrar una ventana de inicio de sesión
     */
    private VentanaLogin ventanaLogin;

    /**
     * El constructor inicializa la ventana de la clase ControladorLogin,
     * hace que la ventana de login sea visible y 
     * prepara los eventos de los diferentes componentes de la ventana.
     */
    public ControladorLogin() {
        this.ventanaLogin = new VentanaLogin();

        this.ventanaLogin.setVisible(true);
        
        //Evento que comprueba si los datos usados para el inicio de sesión son válidos
        this.ventanaLogin.jBotonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Se recoge el nombre y la contraseña insertados en la ventana
                String nombre = ventanaLogin.jTextoUsuario.getText();
                char[] passwordArray = ventanaLogin.jPassword.getPassword();
                
                /*
                La contraseña se pasa a String con StringBuilder porque era la manera
                más clara de convertir el array de caracteres en una cadena. También
                se vacía el array de la contraseña según lo establecido en 
                la documentación de getPassword()
                */
                StringBuilder sb = new StringBuilder();
                String password;
                for (int i = 0; i < passwordArray.length; i++) {
                    sb.append(passwordArray[i]);
                    passwordArray[i] = 0;
                }
                password = sb.toString();
                
                /*
                Aquí se comprueba que ambos campos cuenten con datos. Si es así,
                se procede a comprobar si el Usuario que se crea con ellos existe
                en la tabla 'usuarios' de la base de datos
                */
                if (!nombre.isBlank() && !password.isBlank()) {
                    Usuario usuario = new Usuario(nombre, password);
                    if (UsuarioDAO.existePersona(usuario)) {
                        /*
                        Si realmente existe el usuario y la contraseña, se inicializa
                        el controlador con toda la lógica de inserción y consulta
                        de productos y esta ventana se cierra para liberar memoria
                        */
                        ControladorPrincipal controlador = new ControladorPrincipal();
                        ventanaLogin.dispose();
                    } else {
                        //Si no existe el usuario, se despliega un mensaje de error
                        //y se limpia el campo de la contraseña
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña inválidos", "Error", JOptionPane.ERROR_MESSAGE);
                        ventanaLogin.jPassword.setText("");
                    }
                    //Si alguno de los campos está en blanco, se manda un mensaje de error personalizado
                }else if (nombre.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Introduce un usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null, "Introduce una contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                }
                /*
                Se termina poniendo el valor de password en null porque el método 
                getPassword() de JPasswordField aconseja vaciar la variable
                que se utiliza para guardar la contraseña
                */
                password = null;
            }

        });
    }
}
