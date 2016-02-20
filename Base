/**
 * Base
 *
 * Modela la definiciÃ³n de todos los objetos de tipo
 * <code>Base</code>
 *
 * @author David Orlando de la Fuente Garza, A00817582
 * @version 01 
 * @date 13/01/2016
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class Base {

    private int iX;     //posicion en x.       
    private int iY;     //posicion en y.
    private int iAncho; //ancho del objeto
    private int iAlto; //largo del objeto
    private Image imaImagen;	//imagen.
    private ImageIcon imiImagen;  // imagen con medidas

    /**
     * Base
     * 
     * Metodo constructor usado para crear el objeto animal
     * creando el icono a partir de una imagen
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iAncho es el <code>ancho</code> del objeto.
     * @param iAlto es el <code>Largo</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public Base(int iX, int iY , Image imaImagen) {
        this.iX = iX;
        this.iY = iY;
        this.imaImagen = imaImagen;
        this.imiImagen = new ImageIcon(imaImagen);
        this.iAncho = this.imiImagen.getIconWidth();
        this.iAlto = this.imiImagen.getIconHeight();
    }

    
    /**
     * setX
     * 
     * Metodo modificador usado para cambiar la posicion en x del objeto
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public void setX(int iX) {
        this.iX = iX;
    }

    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en x del objeto 
     * 
     * @return iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public int getX() {
            return iX;
    }

    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en y del objeto 
     * 
     * @param iY es la <code>posicion en y</code> del objeto.
     * 
     */
    public void setY(int iY) {
            this.iY = iY;
    }

    /**
     * getY
     * 
     * Metodo de acceso que regresa la posicion en y del objeto 
     * 
     * @return posY es la <code>posicion en y</code> del objeto.
     * 
     */
    public int getY() {
        return iY;
    }

    /**
     * setImagen
     * 
     * Metodo modificador usado para cambiar el icono de imagen del objeto
     * tomandolo de un objeto imagen
     * 
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public void setImagen(Image imaImagen) {
        this.imaImagen = imaImagen;
        this.imiImagen = new ImageIcon(imaImagen);
        this.iAncho = this.imiImagen.getIconWidth();
        this.iAlto = this.imiImagen.getIconHeight();
    }

    /**
     * getImagen
     * 
     * Metodo de acceso que regresa la imagen que representa el icono del objeto
     * 
     * @return la imagen a partide del <code>icono</code> del objeto.
     * 
     */
    public Image getImagen() {
        return imaImagen;
    }

    /**
     * getAncho
     * 
     * Metodo de acceso que regresa el ancho del icono 
     * 
     * @return un <code>entero</code> que es el ancho de la imagen.
     * 
     */
    public int getAncho() {
        return iAncho;
    }

    /**
     * getAlto
     * 
     * Metodo que  da el alto del icono 
     * 
     * @return un <code>entero</code> que es el alto de la imagen.
     * 
     */
    public int getAlto() {
        return iAlto;
    }
    
    /**
     * paint
     * 
     * Metodo para pintar el animal
     * 
     * @param graGrafico    objeto de la clase <code>Graphics</code> para graficar
     * @param imoObserver  objeto de la clase <code>ImageObserver</code> es el 
     *    Applet donde se pintara
     * 
     */
    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        graGrafico.drawImage(getImagen(), getX(), getY(), getAncho(), getAlto(), imoObserver);
    }

    /**
     * equals
     * 
     * Metodo para checar igualdad con otro objeto
     * 
     * @param objObjeto    objeto de la clase <code>Object</code> para comparar
     * @return un valor <code>boleano</code> que sera verdadero si el objeto
     *   que invoca es igual al objeto recibido como parÃ¡metro
     * 
     */
    public boolean equals(Object objObjeto) {
        // Si el objeto parametro es una instancia de la clase Base
        if (objObjeto instanceof Base) {
            // Se regresa la comparación entre este objeto que invoca y el
            // objeto recibido como parametro
            Base basParam = (Base) objObjeto;
            return this.getX() ==  basParam.getX() && 
                    this.getY() == basParam.getY() &&
                    this.getAncho() == basParam.getAncho() &&
                    this.getAlto() == basParam.getAlto() &&
                    this.getImagen() == basParam.getImagen();
        }
        else {
            // Se regresa un falso porque el objeto recibido no es tipo Base
            return false;
        }
    }

    /**
     * toString
     * 
     * Metodo para obtener la interfaz del objeto
     * 
      * @return un valor <code>String</code> que representa al objeto
     * 
     */
    public String toString() {
        return " x: " + this.getX() + " y: "+ this.getY() +
                " ancho: " + this.getAncho() + " alto: " + this.getAlto();
    }
    
    /** Colisiona
    *
    * Checa la colisión con un objeto Base
    *
    * @param obj objeto de la clase <code>Object</code>
    * @return booleano <code> true </code> si colisiona <code> false
    * </code> si no colisiona
    **/
    
    public boolean colisiona(Object obj){
        // Checo si el objeto es de la clase Base
        if(obj instanceof Base){
            Rectangle recEste = new Rectangle(getX(), getY(), getAncho(), getAlto());
            Rectangle recOtro = new Rectangle(((Base) obj).getX(), ((Base) obj).getY(),
                    ((Base) obj).getAncho(), ((Base) obj).getAlto());
            return recEste.intersects(recOtro);
        }
        else{
            return false;
        }
    }    
    
    /** Colisiona
    *
    * Checa la colisión con un objeto Base
    *
    * @param iX valor <code>integer</code> que representa la x a revisar
    * @param iY valor <code>integer</code> que representa la Y a revisar
    * @return booleano <code> true </code> si colisiona <code> false
    * </code> si no colisiona
    **/
    
    public boolean colisiona(int iX, int iY){
        // Creo un rectángulo del presente personaje
        Rectangle recEste = new Rectangle(getX(), getY(), getAncho(), getAlto());
        
        // Regreso el valor de la intersección entre este personaje y (x, y)
        return recEste.contains(iX, iY);
    }
    
    /** Atrapa
    *
    * Checa si un objeto Base atrapa a otro
    *
    * @param base objeto de la clase Base
    * @return booleano <code> true </code> si colisiona <code> false
    * </code> si no colisiona
    **/
    
    public boolean atrapa(Base basObj){
        // Creo un rectángulo del presente personaje
        Rectangle recEste = new Rectangle(getX(), getY(), getAncho(), 
                getAlto() / 10);
        
        // Regreso el valor de la intersección entre este personaje y (x, y)
        Rectangle recEsq1 = new Rectangle(basObj.getX(), basObj.getY() 
                + basObj.getAlto() - 1, 1, 1);
        Rectangle recEsq2 = new Rectangle(basObj.getX() + basObj.getAncho(), 
                basObj.getY() + basObj.getAlto() - 1, 1 ,1);
        
        return recEste.intersects(recEsq1) && recEste.intersects(recEsq2);
    }
    
    
}
