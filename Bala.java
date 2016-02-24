


/**
 * Bala
 *
 * Clase del objeto bala hereda metodos de la clase Base
 *
 * @author David Orlando de la Fuente Garza, A0817582
 * @author Barbara Berenice Valdez Mireles, A01175920
 * @version 01
 * @date 24/02/2016
 */
import java.awt.Image;
import javax.swing.ImageIcon;

public class Bala extends Base {
    int iTrayectoria;

    public Bala(int iTrayectoria, int iX, int iY, Image imaImagen) {
        super(iX, iY, imaImagen);
        this.iTrayectoria = iTrayectoria;
    }
    
    /**
     * getTrayectoria
     * 
     * Método que regresa la trayectoria de un disparo 
     * 
     * @return un <code>entero</code> que es la trayectoria del disparo
     * 
     */
    public int getTrayectoria() {
        return iTrayectoria;
    }
    
    /**
     * setTrayectoria
     * 
     * Método modificador usado para cambiar la trayectoria de un disparo
     * 
     * @param iTrayectoria es la <code>trayectoria </code> del objeto.
     * 
     */
    public void setTrayectoria(int iTrayectoria) {
            this.iTrayectoria = iTrayectoria;
    }
    
     /**
     * Avanza
     * 
     * Método para modificar el movimiento de la bala
     * 
     * @param iTrayectoria es la <code>trayectoria </code> del objeto.
     * 
     */  
    public void avanza(int iTrayectoria) {
        
        setY(getY() - 2);                   //Se mueve hacia arriba
        
        if(iTrayectoria == 1) {             //Si es uno es a la izquierda en X
            setX(getX() - 2);
        }
        
        if(iTrayectoria == 2) {             //Si es dos es a la derecha en X
            setX(getX() + 2);
        }
    } 
    
}

