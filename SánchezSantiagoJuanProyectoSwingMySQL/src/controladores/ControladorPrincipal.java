package controladores;

import ventanas.VentanaPrincipal;
import ventanas.VentanaProductos;
import ventanas.VentanaAltaProducto;
import daos.CategoriaDAO;
import daos.ProductoDAO;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import modelos.Categoria;
import modelos.Producto;

/**
 *Clase que se encarga de la lógica de inserción, consulta y navegación por las
 * ventanas posteriores al inicio de sesión.
 * @author Juan Sánchez
 */
public class ControladorPrincipal {
    /**
    Venta que funcina como nexo al que se vuelve del resto de operaciones de 
    consulta e inserción y es la única desde la que la aplicación puede terminar
    */
    private VentanaPrincipal ventanaPrincipal;
    /**
    Ventana utilizada para insertar productos en la base de datos
    */
    private VentanaAltaProducto ventanaAlta;
    /**
    Ventana que muestra todos los productos según la categoría que se establezca
    */
    private VentanaProductos ventanaConsulta;

    /**
     * Constructor por defecto que inicializa todos los atributos de la clase,
     * hace visible la ventana principal y establece que el comportamiento del
     * resto de ventanas al cerrarse sea el de liberar memoria pero no cerrar 
     * la aplicación. También cuenta con todos los eventos de los componentes de
     * estas ventanas.
     */
    public ControladorPrincipal() {
        this.ventanaPrincipal = new VentanaPrincipal();
        this.ventanaAlta = new VentanaAltaProducto();
        this.ventanaConsulta = new VentanaProductos();

        this.ventanaPrincipal.setVisible(true);
        this.ventanaAlta.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.ventanaConsulta.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //Eventos
        
        /*
        Evento que lleva de la ventana principal a la ventana de inserción de
        productos
        */
        this.ventanaPrincipal.jBotonAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarVentanaA(ventanaPrincipal, ventanaAlta);
            }
        });
        /*
        Evento que lleva de la ventana principal a la ventana de consulta de
        productos según su categoría
        */
        this.ventanaPrincipal.jBotonConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarVentanaA(ventanaPrincipal, ventanaConsulta);
            }
        });
        
        //Evento que cierra la aplicación cuando se pulsa el botón Salir
        this.ventanaPrincipal.jBotonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                Busqué cómo cerrar la aplicación con un botón que no fuera 
                la X del menú de la ventana y encontré que así se podía terminar 
                la aplicación
                */
                System.exit(0);
            }
        });
        
       
        
        ventanaAlta.addWindowListener(new WindowAdapter() {
             /*
            Evento que rellena el JComboBox de la ventana del alta de productos con
            el método cargarComboBox()
            */
            @Override
            public void windowOpened(WindowEvent e) {
                cargarComboBox(ventanaAlta.jComboCategoria);
            }
            //Evento que hace visible la ventana principal cuando la de alta de productos se cierra.
            //Además, se limpian los elementos de la ventana de alta.
            @Override
            public void windowClosing(WindowEvent e) {
                ventanaPrincipal.setVisible(true);
                limpiar(ventanaAlta);
            }
        });
        
        //Evento que inserta un producto válido en la base de datos
        ventanaAlta.jBotonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado;
                //Se recogen los valores que no producen errores al extraerlos de la ventana
                String nombre = ventanaAlta.jTextoNombre.getText();
                Categoria categoria = (Categoria) ventanaAlta.jComboCategoria.getSelectedItem();
                //Se comprueba que tanto el nombre como la categoría son válidos
                if (!nombre.isBlank() && categoria.getId() != -1) {
                    try {
                        //Si lo son, se intenta obtener los datos numéricos
                        double precio = Double.parseDouble(ventanaAlta.jTextoPrecio.getText());
                        int stock = Integer.parseInt(ventanaAlta.jTextoStock.getText());
                        //En caso de que no se produzca ningún error, se inserta el Producto
                        resultado = ProductoDAO.insertarProducto(new Producto(nombre, precio, stock, categoria));
                        //Se manda un mensaje de éxito si se ha podido insertar el Producto y se limpian los campos
                        if (resultado != 0) {
                            JOptionPane.showMessageDialog(null, "Producto introducido correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            limpiar(ventanaAlta);
                        //Si no se puede insertar el Producto, se manda un mensaje de error
                        }else{
                            JOptionPane.showMessageDialog(null, "No se ha podido introducir el producto", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    /*
                    Cuando la obtención del precio o del stock produce un error por introducir valores
                    inválidos, se lanza un mensaje genérico de error numérico
                    */       
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Datos numéricos (precio o stock) inválidos", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                //En caso de que el nombre esté vació se lanza un mensaje de error personalizado
                } else if(nombre.isBlank()){
                    JOptionPane.showMessageDialog(null, "Introduce un nombre válido", "Error", JOptionPane.ERROR_MESSAGE);
                /*
                Si la categoría seleccionada es la primera, que se usa a modo
                de presentación, también se manda un error personalizado
                */
                }else{
                    JOptionPane.showMessageDialog(null, "Introduce una categoría válida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        //Evento que vuelve a la ventana principal
        ventanaAlta.jBotonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                Se utiliza este método en vez de un cambio de la visibilidad de
                las ventanas porque entendía que de esta manera se evita que
                ventanas a las que el usuario es posible que no vuelva
                siguan estando cargadas.
                */
                volverAtras(ventanaAlta, ventanaPrincipal);
                //También se limpian los valores de la ventana
                limpiar(ventanaAlta);
            }
        });
        
        
        
        ventanaConsulta.addWindowListener(new WindowAdapter() {
            /*
            Evento que vuelve a hacer visible la ventana principal cuando la ventana
            de consulta por categoría se cierra, limpiando los campos de esta.
            */
            @Override
            public void windowClosing(WindowEvent e) {
                reiniciarConsulta();
            }
            /*
            Evento que introduce en el JComboBox de la ventana de consulta todas
            las categorías presentes en la base de datos.
            */
            @Override
            public void windowOpened(WindowEvent e) {
                cargarComboBox(ventanaConsulta.jComboCategorias);
            }
        });
        /*
        Evento que inserta en la tabla de la ventana de consulta todos los productos
        que pertenecen a la categoría seleccionada cuando se pulsa el botón de
        buscar
        */
        ventanaConsulta.jBotonBuscar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //Se vacía la tabla antes de insertar nada
                ventanaConsulta.modelo.setRowCount(0);
                //Se crea una variable que recogerá una Lista de Producto a mostrar
                List<Producto> productos;
                //Se recoge la Categoria seleccionda en el JComboBox
                Categoria categoria = (Categoria) ventanaConsulta.jComboCategorias.getSelectedItem();
                /*
                Si es la categoría general (con id de -1) se recogen todos los 
                productos en la lista, de lo contrario se recogen los productos 
                específicos de la Categoria
                */
                if(categoria.getId() == -1){
                    productos = ProductoDAO.consultarTodosProductos();
                }else{
                    productos = ProductoDAO.consultarProductos(categoria.getId());
                }
                //Todos los productos recogidos se insertan en la tabla.
                for(Producto p : productos){
                    Object[] objeto = {p.getId(), p.getNombre(), p.getPrecio(), 
                                       p.getStock(), p.getCategoria().getNombre()};
                    ventanaConsulta.modelo.addRow(objeto);
                }
             
                //Si no hay productos para esa categoría, se lanza un aviso
                if(ventanaConsulta.modelo.getRowCount() == 0){
                    JOptionPane.showMessageDialog(null, "No hay entradas para esa categoría", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
                /*
                Se actualiza la etiqueta del número de productos recogiendo el 
                número de filas en la tabla
                */
                ventanaConsulta.jEtiquetaNumProd.setText(Integer.toString(ventanaConsulta.modelo.getRowCount()));
            }
        });
        
        //Evento que cierra la ventana de consulta y abre la ventana principal.
        //También vacía la tabla y establece el JComboBox en la posición inicial
        ventanaConsulta.jBotonAtras.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               reiniciarConsulta(); 
            }
        });
    }
    
    /**
     * Método que se encarga de cambiar entre ventanas sin cerrar aquella de la
     * que se parte
     * @param aCerrar JFrame que va a pasar a ser invisible
     * @param aAbrir  JFrame que se busca hacer visible
     */
    private void cambiarVentanaA(JFrame aCerrar, JFrame aAbrir) {
        aCerrar.setVisible(false);
        aAbrir.setVisible(true);
    }
    /**
     * Método que inserta todos los objetos Categoria de la base de datos en el 
     * JComboBox que se introduzca como parámetro. Se inserta una Categoria especial
     * que representa todas las categorías.
     * @param jComboBox JCombBox al que se le insertan todas las categorías
     */
    private void cargarComboBox(JComboBox jComboBox) {
        /*
        Se introduce primero esta Categoria con un id de -1 para luego
        validar en la ventana de consulta y de alta que se ha escogido una Categoria válida.
        Como el JComboBox es de <Categoria> es necesario crear esta Categoria
        para insertarla primero en lugar de usar un String.
        */
        jComboBox.addItem(new Categoria(-1, "--Todas las categorias--"));
        for (Categoria c : CategoriaDAO.consultarCategorias()) {
            jComboBox.addItem(c);
        }
    }
    /**
     * Método semejante a cambiarVentanaA(), pero que libera memoria al cerrar
     * la ventana de la que se proviene.
     * @param cerrando JFrame que se cierra para liberar memoria
     * @param abrir    JFrame que se busca hacer visible
     */
    private void volverAtras(JFrame cerrando, JFrame abrir) {
        cerrando.dispose();
        abrir.setVisible(true);
    }
    /**
     * Método que limpia los campos de una ventana que se pasa por parámetro
     * y establece el primer elemento del desplegable como seleccionado
     * @param ventana JFrame a limpiar
     */
    private void limpiar(JFrame ventana){
        //Después de darme de cabezados para hacer que este método funcionara
        //vi que un JFrame deja sus componentes en un RootPane, así que 
        //saco los elementos del RootPane con getContentPane().
        Container contenidos = ventana.getContentPane();
        //Itero por todos los componentes de la ventana y pongo sus valores por defecto
        for(Component c : contenidos.getComponents()){
            if(c instanceof JTextField){
                JTextField texto = (JTextField) c;
                texto.setText("");
            }
            else if(c instanceof JComboBox){
                JComboBox caja = (JComboBox) c;
                caja.setSelectedIndex(0);
            }
        }
    }
    /**
     * Método que vacía la tabla, la etiqueta de productos y establece la
     * selección del ComboBox en su primer elemento de la ventana de consulta.
     */
    private void reiniciarConsulta(){
        volverAtras(ventanaConsulta, ventanaPrincipal);
        ventanaConsulta.modelo.setRowCount(0);
        ventanaConsulta.jComboCategorias.setSelectedIndex(0);
        ventanaConsulta.jEtiquetaNumProd.setText("");
    }
    /*
    Es un método muy específico de la ventana de consulta pero creo que dejaba
    más claro el código.
    */
      
//He dejado este método comentado en caso de que el de arriba no funcione en la
//la corrección (aunque debería).
//    private void limpiar(){
//        ventanaAlta.jTextoNombre.setText("");
//        ventanaAlta.jTextoPrecio.setText("");
//        ventanaAlta.jTextoStock.setText("");
//        ventanaAlta.jComboCategoria.setSelectedIndex(0);
//    }
}
