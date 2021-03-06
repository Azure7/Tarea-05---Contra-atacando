import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Juego5
 *
 * Tarea 05 - Contra-atacando
 *
 * @author David Orlando de la Fuente Garza, A0817582
 * @author Barbara Berenice Váldez Mireles, A01175920
 * @version 01
 * @date 24/02/2016
 */

// Clase que hereda de JFrame e implementa el KeyListener
public class Juego5 extends JFrame implements Runnable, KeyListener {
   
    // Constructor del Juego en JFrame
    // Define el tamaño del JFrame, inicializa los personajes y corre el Juego
    public Juego5() {
        setSize(iWIDTH, iHEIGHT);
        init();
        start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Añado los escuchadores
        addKeyListener(this);
    }

    /* Objetos para manejar el buffer del Applet y la Imagen no parpadee */
     
    /* Objetos Base */
    private Base     basPrincipal;      // Objeto Principal
    private Bala     balDisparo;        // Objeto Disparo
    private LinkedList<Bala> lklDisparo;// Lista de objeto de Disparo
    private LinkedList<Malo> lklMalos;  // Lista de objetos de Malos
    private LinkedList<Image> lklVidas; // Lista de imagenes de Vida
    
    /* Objetos gráficos */
    private Graphics graGraficaApplet;  // Objeto gráfico de la Imagen
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet
    private Image    imaImagenFondo;    // Para dibujar la imagen de Fondo
    private Image    imaImagenGameOver; // Para dibujar la imagen de Game Over
    private Image    imaImagenPaused;   // Para dibujar la imagen de Pausa
    private Image    imaImagenVida;     // Para dibujar la imagen de Vida
    private Image    imaImagenPrincipal;// Imagen Principal
    private Image    imaImagenDisparo;  // Imagen Disparo
    private Image    imaImagenMalo;     // Imagen Malo
    private Color    colRojo;           // Color del Puntaje y Vidas
    
    /* Objetos de audio */
    private SoundClip audPuntos;        // Sonido cuando se destruye un Malo
    private SoundClip audPierdeVida;    // Principal pierde una vida
    
    /* Variables enteras del Juego */ 
    private int      iScore;            // Puntaje del juego
    private int      iVidas;            // Vidas de Principal
    private int      iVelMal;           // Velocidad de los Malos
    private int      iContMalos;        // Contador de colisiones con Malos
    
    /* Variables booleanas */
    private boolean  bDisparo;          // Registra si se puede o no disparar
    private boolean  bPause;            // Booleano de Pausa del Juego
    private boolean  bGameOver;         // Booleano del Game Over
    
    /* Variables de movimiento de Principal */
    private char     cTeclaMov;         // Dirección de movimiento de Principal
    private char     cTecla;            // Dirección de movimiento de Disparo
   
    /* Dimensiones del JFrame */
    private static final int iWIDTH = 800;    // Ancho del JFrame
    private static final int iHEIGHT = 500;   // Altura del JFrame
    
    /** 
     * init
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inicializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     * 
     */
    
    public void init() {   
        // Llamo a las funciones para inicializar la interfaz del Juego, 
        // así como crear y reposicionar a Principal y Malos
        incializaEscenario();
        creaPrincipal();
        creaMalos();
        
        // Creo la lista de los Disparos
        lklDisparo = new LinkedList<Bala>();
    }
    
    /**
     * inicializaEscenario
     * 
     * Metodo que inicializo las imágenes de Fondo, los Sonidos y el texto 
     * a mostrar en pantalla
     */
    public void incializaEscenario() {
        // Declaro el color de la fuente de color rojo
        colRojo = new Color (200, 0, 30);
        
        // Inicializo las vidas de Principal al en 5
        iVidas = 5;
        
        // Inicializo el contador de Malos que han colisionado con Principal
        iContMalos = 0;
        
        // Inicializo el puntaje del Juego
        iScore = 0;
        
        // Incializo la tecla de movimiento en espacio en blanco
        cTecla = ' ';
        cTeclaMov = ' ';
        
        // Al inicio del juego se puede disparar
        bDisparo = true;
        
        // Inicialzo la Pausa en false
        bPause = false;
        
        // Inicializo el estatus de Game Over
        bGameOver = false;
                           
        // Defino el Sonido para cuando se pierde una vida
        audPierdeVida = new SoundClip("Explosion.wav"); 
        
        // Defino el Sonido para cuando se ganan puntos
        audPuntos = new SoundClip("mice.wav"); 
		
        // Creo la Imagen de Fondo
        URL urlImagenFondo = this.getClass().getResource("ciudad.png");
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        
        // Creo la Imagen de Game Over
        URL urlImagenGameOver = this.getClass().getResource("gameover.jpg");
        imaImagenGameOver = Toolkit.getDefaultToolkit()
                .getImage(urlImagenGameOver);
        
        //Creo la Imagen de Pausa
        URL urlImagenPaused = this.getClass().getResource("ps.png");
        imaImagenPaused = Toolkit.getDefaultToolkit()
                .getImage(urlImagenPaused);
        
        // Defino la Imagen de Vidas
	URL urlImagenVida = this.getClass().getResource("Vida.png");
        imaImagenVida = Toolkit.getDefaultToolkit().getImage(urlImagenVida);
    }
    
    /** creaPrincipal()
    *
    * Crea la Imagen y Objeto para Principal
    *
    * 
    **/
    public void creaPrincipal() {
        // Defino la Imagen de Principal
	URL urlImagenPrincipal = this.getClass().getResource("hielog.gif");
        imaImagenPrincipal = Toolkit.getDefaultToolkit().getImage
                (urlImagenPrincipal);
        
        // Creo el objeto para Principal
	basPrincipal = new Base(0, 0, 
                Toolkit.getDefaultToolkit().getImage(urlImagenPrincipal));
        
        // Reposiciona a Principal en medio en la parte inferior de la pantalla
        basPrincipal.setX(getWidth() / 2 - basPrincipal.getAncho() / 2);
        basPrincipal.setY(getHeight() - basPrincipal.getAlto());
    }
    
    /** creaDisparo()
    *
    * Crea la Imagen y Objeto para el Disparo
    *
    * 
    **/
    public void creaDisparo(char cTrayectoria) {
        // Defino la Imagen de Disparo
	URL urlImagenDisparo = this.getClass().getResource("coin.gif");
        imaImagenDisparo = Toolkit.getDefaultToolkit().getImage
                (urlImagenDisparo);
        
        // Creo el objeto para Disparo
	balDisparo = new Bala(0, 0, 0, 
                 Toolkit.getDefaultToolkit().getImage(urlImagenDisparo));
        
        // Posiciono a Disparo justo encima de Principal
        balDisparo.setX(basPrincipal.getX() + basPrincipal.getAncho() / 4);
        balDisparo.setY(basPrincipal.getY());
        
        // Le asigno su trayectoria
        if(cTecla == 'A') {
            balDisparo.setTrayectoria(1);
        }
        
        if(cTecla == 'S') {
            balDisparo.setTrayectoria(2);
        }
        
        // Añado el disparo a la lista
        lklDisparo.add(balDisparo);
    }
    
    /** creaMalos()
    *
    * Crea la Imagen y la lista de Objetos para Malos
    *
    * 
    **/
    public void creaMalos() {
        // Defino la Imagen de Malo
	URL urlImagenMalo = this.getClass().getResource("goomba.gif");
        imaImagenMalo = Toolkit.getDefaultToolkit().getImage(urlImagenMalo);
        
        // Creo la lista de los Malos
        lklMalos = new LinkedList<Malo>();
        
        // Declaro el número al azar entre 10 y 15 para crear a los Malos
        int iRanMalos = ((int) (Math.random() * 6 + 10));
        
        // Creo a los Malos
        for(int iI = 0; iI < iRanMalos; iI++){
            // Creo a un Malo            
            Malo malMalo = new Malo(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenMalo));
            
            // Añado a Malo a la Lista
            lklMalos.add(malMalo);
        } 
        
        // Inicializo la velocidad de los Malos
        iVelMal = 1;
        
        // Reposiciono a todos los Malos en la parte derecha del Applet
        for(Malo malMalo : lklMalos) {           
            reposicionaMalo(malMalo); 
        }
    }
    
    /** 
     * reposicionaMalo
     * Reposiciona a Malo de manera random en la parte derecha de la Pantalla
     * @param malMalo
     * 
     */    
    public void reposicionaMalo(Malo malMalo){
        // Reposiciono a cada Moneda de la Lista de manera random encima del 
            // marco para que caigan a diferente momento
        malMalo.setX((int) (Math.random() 
            * (getWidth() - malMalo.getAncho())));
        malMalo.setY(0 - (int) (Math.random() 
            * (getHeight() - malMalo.getAlto())));       
    }
    
    /**
     * checaColision
     * 
     * Método que manda a llamar a los métodos de colisión de Principal
     * y de los Malos
     * 
     */
    public void checaColision(){
        checaBordes();
        checaColisionDisparo();
        checaColisionMalos();
    }
    
    /**
     * checaBordes
     * 
     * Método para evitar que Principal se salga por los bordes del Applet
     * 
     */
    public void checaBordes() {
        // Evito que Principal se salga por los bordes de X
        if(basPrincipal.getX() <= 0){
            basPrincipal.setX(0);
        }
        else if(basPrincipal.getX() + basPrincipal.getAncho() >= getWidth()){
            basPrincipal.setX(getWidth() - basPrincipal.getAncho());
        }
        
        // Evito que Principal se salga por los bordes de Y
        if(basPrincipal.getY() <= 30){
            basPrincipal.setY(30);
        }
        else if(basPrincipal.getY() + basPrincipal.getAlto() >= getHeight()){
            basPrincipal.setY(getHeight() - basPrincipal.getAlto());
        }
    }
    
    /**
     * checaColisionDisparo
     * 
     */
    public void checaColisionDisparo() {
        // Para cada disparo en pantalla
        for(int i = 0; i < lklDisparo.size(); i++) {
            // Borra el disparo si colisiona con el tope de la pantalla
            if(lklDisparo.get(i).getY() <= 0) {
                lklDisparo.remove(i);
            }
            // Borra el disparo si colisiona con los lados de la pantalla
            else if(lklDisparo.get(i).getX() <= 0 
                    || lklDisparo.get(i).getX() >= getWidth()) {
                lklDisparo.remove(i);
            }
        }
    }
    
    /**
     * checaColisionMalos
     * 
     * Método para revisar la colisión de los Malos
     * 
     */
    public void checaColisionMalos() {
        // Si un Malo colisiona con Principal, aumenta el contador de colisiones
        // y reposiciono a Malo para que vuelve entrar por la derecha del Applet
        for(Malo malMalo : lklMalos){
            if(malMalo.colisiona(basPrincipal)) {
                iContMalos++;
                reposicionaMalo(malMalo);
                // El puntaje sólo puede disminuir si es mayor a 0
                if(iScore > 0) {
                    iScore--;
                }
            }    
            
            // Si Malo toca la parte inferior de la pantalla, lo reposiciona
            if(malMalo.getY() >= getHeight() - malMalo.getAlto()) {
                reposicionaMalo(malMalo);
            }
        }
        
        // Para cada Malo
        for(Malo malMalo : lklMalos) {
            for(Base basDisparo : lklDisparo) {
                // Revisa si colisiona con un Disparo
                if(basDisparo.colisiona(malMalo)) {
                    // Si colisiona, reposiciona a Malo, elimina el Disparo
                    // y suma 10 puntos
                    audPuntos.play();
                    lklDisparo.remove(basDisparo);
                    reposicionaMalo(malMalo);
                    iScore += 10;
                    break;
                }
            }
        }
    }
	
    /** 
     * actualiza
     * 
     * Metodo que mandar a llamar a los métodos de actualización de 
     * movimiento de los personajes y de guardar y cargar archivos
     * 
     */
    public void actualiza(){ 
        // Actualizo la posición de Principal según la tecla presionada
        actualizaPrincipal(cTeclaMov);
        actualizaMalos();
        
        // Si se presionó espacio, se crea un disparo y evitar
        // que se vuelva disparar hasta que no se suelte la tecla
        // Sólo puede un máximo de 5 balas en pantalla
        if(cTecla == 'K' || cTecla == 'A' || cTecla == 'S') {
            if(bDisparo && lklDisparo.size() < 20) {
               creaDisparo(cTecla);
               bDisparo = false; 
            }
        }
        actualizaDisparo();
    }
    
    /** 
    * actualizaPrincipal
    *
    * Actualiza las coordenadas de Principal según la tecla presionada
    *
    * @param char tecla que el usuario tiene presionada
    **/
    public void actualizaPrincipal(char cChar) {
        switch(cChar){
            // Si se presiona la tecla izquierda, se mueve a la izquierda
            case '3':
                basPrincipal.setX(basPrincipal.getX() - 3);
                break;
            
            // Si se presiona la tecla derecha, se mueve a la derecha
            case '4':
                basPrincipal.setX(basPrincipal.getX() + 3);
                break;
        }
        
        // Cuando Principal haya colisionado 5 veces con un Malo
        if(iContMalos >= 5){
            // Reproduce el sonido indiciando que se perdió una vida
            audPierdeVida.play();
            iVidas--;
            // Se reinicia el contador de Malos y se incrementa su velocidad
            iContMalos = 0;
            iVelMal++;
        }
    }
    
    /** 
     * actualizaDisparo
     * 
     * Método que actualiza la posición del Disparo
     */
    public void actualizaDisparo() {
        for(Bala balDisparo : lklDisparo) { 
            balDisparo.avanza(balDisparo.getTrayectoria());
        }
    }
    
    /**
     * actualizaMalos
     * 
     * Método que actualiza la posición de los Malos
     */
    public void actualizaMalos() {
        // Actualizo al 10% de los Malos para que sigan a Principal
        for(int iA = 0; iA < (lklMalos.size() / 10) + 1; iA++) {
            lklMalos.get(iA).avanza(basPrincipal.getX(), iVelMal);
        }
        
        // Actualizo al resto de los Malos para que caigan normalmente
        for(int iA = lklMalos.size() / 10; iA < lklMalos.size(); iA++) {
            lklMalos.get(iA).avanza(iVelMal);
        }
    }
    
    /** 
     * start
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>Applet</code>
     * 
     */   
    public void start () {
        // Declaración del hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
    }
	
    /** 
     * run
     * 
     * Método sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrÃ¡ las instrucciones
     * de nuestro juego.
     * 
     */
    public void run () {
        /* Mientras dure el juego, se actualizan posiciones de jugadores
           se checa si hubo colisiones para desaparecer jugadores o corregir
           movimientos y se vuelve a pintar todo
        */ 
        
        // Mientras Principal tenga vidas
        while (iVidas > 0) {
            if(!bPause) {
                actualiza();
                checaColision();
            }
            repaint();
            try	{
                // El hilo del juego se duerme.
                Thread.sleep(20);
            }
            catch (InterruptedException iexError) {
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }     
        }
        bGameOver = true;
        // Si se presiona X en la pantalla de Game Over, se reinicia el Juego
        while(bGameOver) {
            if(cTecla == 'X') {
                init();
                bGameOver = false;
                run();
            }
        }
        
    }
    
    /**
     * paint
     * 
     * Método sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este método lo que hace es actualizar el contenedor y 
     * define cuando usar ahora el paint
     * 
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint(Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }
       
        graGraficaApplet.drawImage(imaImagenFondo, 0, 0, 
                getWidth(), getHeight(), this);

        // Actualiza el Foreground.
        graGraficaApplet.setColor(getForeground());
        paint1(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage(imaImagenApplet, 0, 0, this);
    }
    
     /**
     * paint1
     * 
     * Metodo sobrescrito de la clase <code>JFrame</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y 
     * define cuando usar ahora el paint
     * 
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint1(Graphics graDibujo) {
        // Si la imágenes ya se cargaron
        if (basPrincipal != null && imaImagenFondo != null 
                & lklMalos != null) {
            
            // Mientras Principal tenga vidas
            if(iVidas > 0) {
                // Dibuja la imagen de fondo
                graDibujo.drawImage(imaImagenFondo, 0, 0, getWidth(), 
                getHeight(), this);

                // Dibuja a Principal
                basPrincipal.paint(graDibujo, this);
                
                // Dibujo los Disparos
                for(Base basDisparo : lklDisparo){
                    basDisparo.paint(graDibujo, this);
                }    
                
                // Dibujo a los Malos
                for(Malo malMalo : lklMalos){
                    malMalo.paint(graDibujo, this);
                }              
                
                //Dibujo las vidas
                for(int iI = 0; iI < iVidas; iI++) {
                   graDibujo.drawImage(imaImagenVida, 20 + (iI * 30), 30 , this);
                }
                
                graDibujo.setColor(colRojo);
                // Reviso si a Principal aún le quedan Vidas
                if(iVidas > 0){
                    graDibujo.drawString("Score: " + iScore, 40, 90);
                }
                
                if(bPause) {
                   //Cuando el juego está pausado se cambia la imagen de fondo
                    graDibujo.drawImage(imaImagenPaused, 0, 0, getWidth(), 
                    getHeight(), this);
                }
            }
            else {
                // Cuando las vidas son 0, dibuja la imagen de Game Over
                graDibujo.drawImage(imaImagenGameOver, 0, 0, getWidth(), 
                getHeight(), this);
            }
        } 
        // Si no se ha cargado se muestra un mensaje
        else {
                //Da un mensaje mientras se carga el dibujo	
                graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
    }
    
    /** getWidth
    *
    * Método que accede al Width del JFrame
    * 
    **/
    public int getWidth() {
        return iWIDTH;
    }
    
   /** getWidth
    *
    * Método que accede al Height del JFrame
    * 
    **/
    public int getHeight() {
        return iHEIGHT;
    }
        
    /* Métodos del KeyListener */
    @Override
    public void keyTyped(KeyEvent keyEvent) {
        
    }

    // Revisa la tecla presionada y le asigna a la variable cTecla
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        // Si se presiona la tecla izquierda, se mueve a la izquierda
        if(keyEvent.getKeyCode() == keyEvent.VK_LEFT) {
            cTeclaMov = '3';
        }
        // Si se presiona la tecla derecha, se mueve a la derecha
        if(keyEvent.getKeyCode() == keyEvent.VK_RIGHT) {
            cTeclaMov = '4';
        }
        
        // Si se preciona espacio y se puede disparar, se crea un disparo normal
        if(keyEvent.getKeyCode() == keyEvent.VK_SPACE && bDisparo)
        {
            cTecla = 'K';
        }
        
        // Si se preciona A, se crea un disparo direccionado a la izquierda
        if(keyEvent.getKeyCode() == keyEvent.VK_A && bDisparo)
        {
            cTecla = 'A';
        }
        
        // Si se preciona S, se crea un disparo direccionado a la derecha
        if(keyEvent.getKeyCode() == keyEvent.VK_S && bDisparo)
        {
            cTecla = 'S';
        } 
        
        // Si se presiona X en la pantalla de Game Over, se reinicia el Juego
        if(keyEvent.getKeyCode() == keyEvent.VK_X)
        {
            cTecla = 'X';
        }
        
        // Si se presiona P, se pausa el juego
        if(keyEvent.getKeyCode() == keyEvent.VK_P)
        {
            bPause = !bPause;
        } 
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        // Si se suelta la tecla, se le asigna un espacio en blanco
        cTecla = ' ';
        cTeclaMov = ' ';
            
        // Cuando se suelte la tecla, se puede volver a disparar
        bDisparo = true;
     
    }
  
    /**
     * main
     * 
     * Método main en el que se construye el JFrame y el Juego
     */
    public static void main(String [] args) {
        Juego5 Juego = new Juego5();
        Juego.setVisible(true);
    }
}
