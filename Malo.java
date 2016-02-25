/**
 * Malo
 *
 * @author David Orlando de la Fuente Garza, A0817582
 * @author Barbara Berenice Váldez Mireles, A01175920
 * @version 01
 * @date 24/02/2016
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class Malo extends Base {
    
    // Constructor de la clase Malo
    public Malo(int iX, int iY, Image imaImagen) {
        super(iX, iY, imaImagen);
    }
    
   /**
    * avanza
    * 
    * Método que hace que Malo avance
    * 
    **/
    public void avanza(int iVelocidad) {
        setY(getY() + iVelocidad);
    }

    /**
    * avanza
    * 
    * @param int coordenadas en X del objeto Base que se perseguirá
    * 
    **/
    public void avanza(int iX, int iVelocidad) {
        setY(getY() + iVelocidad);
                
        // Si Principal está a la izquierda, se mueve a la izquierda
        if(iX < getX()) {
            setX(getX() - 1);
        }
              
        // Si Princopal está a la derecha, se mueve a la derecha
        if(iX > getX()) {
            setX(getX() + 1);
        }
    }
}