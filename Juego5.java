
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

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
    }

    /* Objetos para manejar el buffer del Applet y la Imagen no parpadee */
     
    /* Objetos Base */
    private Base     basPrincipal;      // Objeto Principal
    private LinkedList<Base> lklMalos;  // Lista de objetos de Malos
    private LinkedList<Base> lklBuenos; // Lista de objetos de Buenos
    
    /* Objetos gráficos */
    private Graphics graGraficaApplet;  // Objeto gráfico de la Imagen
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet
    private Image    imaImagenFondo;    // Para dibujar la imagen de Fondo
    private Image    imaImagenGameOver; // Para dibujar la imagen de Game Over
    private Image    imaImagenPrincipal;// Imagen Principal
    private Image    imaImagenMalo;     // Imagen Malo
    private Image    imaImagenBueno;    // Imagen Bueno   
    private Color    colRojo;             // Color del Puntaje y Vidas
    
    /* Objetos de audio */
    private SoundClip audPuntos;        // Colisión de Principal con Bueno
    private SoundClip audPierdeVida;    // Principal pierde una vida
    
    /* Variables enteras del Juego */  
    private int      iScore;            // Puntaje del juego
    private int      iVidas;            // Vidas de Principal
    private int      iContMalos;        // Contador de colisiones con Malos
    
    /* Variables de movimiento de Principal */
    private char     cTecla;            // Dirección de movimiento de Principal
    
    /* Variables string para guardar archivos */
    private String sNombreArchivo;
    
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
        // así como crear y reposicionar a Principal, Malos y Buenos
        incializaEscenario();
        creaPrincipal();
        creaMalos();
        creaBuenos();
    }
    
    /**
     * inicializaEscenario
     * 
     * Metodo que inicializo las imágenes de Fondo, los Sonidos y el texto 
     * a mostrar en pantalla
     */
    public void incializaEscenario() {
        // Añado los escuchadores
        addKeyListener(this);
        
        // Declaro el color de la fuente de color rojo
        colRojo = new Color (200, 0, 30);
        
        // Inicializo las vidas de Principal al azar entre 3 y 5
        iVidas = ((int) (Math.random() * 3 + 3));
        
        // Inicializo el contador de Malos que han colisionado con Principal
        iContMalos = 0;
        
        // Inicializo el puntaje del Juego
        iScore = 0;
        
        // Incializo la tecla de movimiento en espacio en blanco
        cTecla = ' ';
                           
        // Defino el Sonido para cuando se pierde una vida
        audPierdeVida = new SoundClip ("Explosion.wav"); 
        
        // Defino el Sonido para cuando se ganan puntos
        audPuntos = new SoundClip ("mice.wav"); 
		
        // Creo la Imagen de Fondo
        URL urlImagenFondo = this.getClass().getResource("ciudad.png");
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        
        // Creo la Imagen de Game Over
        URL urlImagenGameOver = this.getClass().getResource("gameover.jpg");
        imaImagenGameOver = Toolkit.getDefaultToolkit()
                .getImage(urlImagenGameOver);
        
        // Inicializo el nombre del archivo para guardar los datos
        sNombreArchivo = " ";
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
        
        // Reposiciona a Principal exactamente en el centro del Applet
        basPrincipal.setX(getWidth() / 2 - basPrincipal.getAncho() / 2);
        basPrincipal.setY(getHeight() / 2 - basPrincipal.getAlto() / 2);
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
        lklMalos = new LinkedList<Base>();
        
        // Declaro el número al azar entre 8 y 10 para crear a los Malos
        int iRanMalos = ((int) (Math.random() * 3 + 8));
        
        // Creo a los Malos
        for(int iI = 0; iI < iRanMalos; iI++){
            // Creo a un Malo            
            Base basMalo = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenMalo));
            
            // Añado a Malo a la Lista
            lklMalos.add(basMalo);
        } 
        
        // Reposiciono a todos los Malos en la parte derecha del Applet
        for(Base basMalo : lklMalos) {           
            reposicionaMalo(basMalo); 
        }
    }
    
    /** 
     * reposicionaMalo
     * Reposiciona a Malo de manera random en la parte derecha de la Pantalla
     * @param basMalo
     * 
     */    
    public void reposicionaMalo(Base basMalo){
        // Reposiciono a cada Malo de la Lista de manera random para que 
            // entre por la parte derecha del Applet
        basMalo.setX(getWidth() + (int) (Math.random() 
            * (getWidth() - basMalo.getAncho())));
        basMalo.setY(30 + (int) (Math.random() 
            * (getHeight() - basMalo.getAlto())));      
    }
    
    /** creaBuenos()
    *
    * Crea la Imagen y la lista de Objetos para Buenos
    *
    * 
    **/
    public void creaBuenos() {
        // Defino la Imagen de Bueno
	URL urlImagenBueno = this.getClass().getResource("coin.gif");
        imaImagenBueno = Toolkit.getDefaultToolkit().getImage(urlImagenBueno);
        
        // Creo la lista de los Buenos
        lklBuenos = new LinkedList<Base>();
                
        // Declaro el número al azar entre 10 y 15 para crear a los Buenos
        int iRanBuenos =  ((int) (Math.random() * 6 + 10));
        
        // Creo a los Buenos
        for(int iI = 0; iI < iRanBuenos; iI++){
            // Creo a un Bueno           
            Base basBueno = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenBueno));
            
            // Añado a Bueno a la Lista
            lklBuenos.add(basBueno);
        }
        
        // Reposiciono a todos los Buenos en la parte izquierda del Applet
        for(Base basBueno : lklBuenos) {           
            reposicionaBueno(basBueno); 
        }
    }
    
    /** 
     * reposicionaBueno
     * Reposiciona a Bueno de manera random en la parte izquierda de la Pantalla
     * @param basBueno
     * 
     */    
    public void reposicionaBueno(Base basBueno){
        // Reposiciono a cada Bueno de la Lista de manera random para que 
            // entre por la parte izquierda del Applet
        basBueno.setX(0 - (int) (Math.random() 
            * (getWidth() - basBueno.getAncho())));
        basBueno.setY(30 + (int) (Math.random() 
            * (getHeight() - basBueno.getAlto())));      
    }
    
    /**
     * checaColision
     * 
     * Método que manda a llamar a los métodos de colisión de Principal, 
     * Malos y Buenos
     * 
     */
    public void checaColision(){
        checaBordes();
        checaColisionBuenos();
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
     * checaColisionMalos
     * 
     * Método para revisar la colisión de los Malos
     * 
     */
    public void checaColisionMalos() {
        // Si un Malo colisiona con Principal, aumenta el contador de colisiones
        // y reposiciono a Malo para que vuelve entrar por la derecha del Applet
        for(Base basMalo : lklMalos){
            if(basMalo.colisiona(basPrincipal)) {
                iContMalos++;
                reposicionaMalo(basMalo);
            }               
        }
        
        // Si un Malo llega al extremo izquierdo del Applet, lo reposiciona
        for(Base basMalo : lklMalos) {
            if(basMalo.getX() <= 0) {
                reposicionaMalo(basMalo);
            }               
        }     
    }
    
    /**
     * checaColisionBuenos
     * 
     * Método para revisar la colisión de los Buenos
     * 
     */
    public void checaColisionBuenos() { 
        // Si un Bueno colisiona con Principal, reproduce un sonido,
        // aumenta el puntaje y reposiciona a Bueno para que vuelve a entrar
        // por la parte izquierda de la pantalla
        for(Base basBueno : lklBuenos){
            if(basBueno.colisiona(basPrincipal)) {
                audPuntos.play();
                iScore += 10;
                reposicionaBueno(basBueno);
            }               
        }            
             
        // Si un Bueno llega al extremo derecho del Applet, lo reposiciona
        for(Base basBueno : lklBuenos) {
            if(basBueno.getX() >= getWidth()) {
                reposicionaBueno(basBueno);
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
        actualizaArchivo();
        // Actualizo la posición de Principal según la tecla presionada
        actualizaPrincipal(cTecla);
        actualizaBuenos();
        actualizaMalos();
    }
    
    /**
     * actualizaArchivo
     * 
     * Método que revisa si se ha guardado o cargado un archivo
     */
    public void actualizaArchivo() {
        // Si se presiona G, se guarda el archivo
        if(cTecla == 'G') {
                try {
                    grabaArchivo();
                }   catch(IOException e) {
                System.out.println("Error en " + e.toString());
                }
            }
        
        // Si se presiona C, se carga el archivo
        if(cTecla == 'C') {
        try {
            leeArchivo();
        }   catch(IOException e) {
            System.out.println("Error en " + e.toString());
            }
        }
    }
    
    /** actualizaPrincipal
    *
    * Actualiza las coordenadas de Principal según la tecla presionada
    *
    * @param char tecla que el usuario tiene presionada
    **/
    public void actualizaPrincipal(char cChar) {
        switch(cChar){
            // Si se presiona Q, se mueve hacia la esquina superior izquierda
            case 'Q':
                basPrincipal.setX(basPrincipal.getX() - 3);
                basPrincipal.setY(basPrincipal.getY() - 3);
                break;
            
            // Si se presiona P, se mueve hacia la esquina superior derecha
            case 'P':
                basPrincipal.setX(basPrincipal.getX() + 3);
                basPrincipal.setY(basPrincipal.getY() - 3);
                break;
            
            // Si se presiona A, se mueve hacia la esquina inferior izquierda
            case 'A':
                basPrincipal.setX(basPrincipal.getX() - 3);
                basPrincipal.setY(basPrincipal.getY() + 3);
                break;
            
            // Si se presiona L, se mueve hacia la esquina inferior derecha
            case 'L':
                basPrincipal.setX(basPrincipal.getX() + 3);
                basPrincipal.setY(basPrincipal.getY() + 3);
                break;
        }
        
        // Cuando Principal haya colisionado 5 veces con un Malo, disminuye
        // reproduce un sonido, disminuye una vida y reinicia el contador de 
        // colisiones
        if(iContMalos >= 5){
            audPierdeVida.play();
            iVidas--;
            iContMalos = 0;
        }
    }
    
    /**
     * actualizaMalos
     * Método que actualiza la posición de los Malos
     */
    public void actualizaMalos() {
        // Actualizo la posición de cada Malo en X para que avance hacia
        // la parte izquierda de la pantalla al azar de 3 a 5 pixeles
        for(Base basMalo : lklMalos){
            int iAvanceMalo = ((int) (Math.random() * 3 + 3));
            basMalo.setX(basMalo.getX() - iAvanceMalo);
        }
    }
    
    /**
     * actualizaBuenos
     * Método que actualiza la posición de los Buenos
     */
    public void actualizaBuenos() {
        // Actualizo la posición de cada Bueno en X para que avance hacia
        // la parte derecha de la pantalla al azar de 1 a 3 pixeles
        for(Base basBueno : lklBuenos) {
            int iAvanceBueno = ((int) (Math.random() * 3 + 1));
            basBueno.setX(basBueno.getX() + iAvanceBueno);
        }
    }
    
    /** grabaArchivo
     * 
     * Método que mandar a llamar a los métodos para grabar la información
     * del juego, a Principal, Malos y Buenos
     * 
     * @throws IOException 
     */
    public void grabaArchivo() throws IOException {
        grabaVidasScore();
        grabaPrincipal();
        grabaMalos();
        grabaBuenos();
        
    }
    
    /**
     * leeArchivo
     * 
     * Método que mandar a llamar a los métodos para leer la información
     * del juego, a Principal, Malos y Buenos
     * @throws IOException 
     */
    public void leeArchivo() throws IOException {
        leeVidasScore();
        leePrincipal();
        leeMalos();
        leeBuenos();
    }
    
    /**
     * grabaVidasScore
     * 
     * Método que guarda en un archivo de texto la información del Juego 
     * como vidas y puntaje
     * @throws IOException 
     */
    public void grabaVidasScore() throws IOException {
        // Creo el archivo de texto para guardar la información
        sNombreArchivo = "VidasScore.txt";
        PrintWriter fileOut = new PrintWriter(new FileWriter(sNombreArchivo));
        // Guardo las Vidas y el Puntaje
        fileOut.println(iVidas);
        fileOut.println(iScore);
        // Cierro el archivo de texto
        fileOut.close();
    }
    
    /**
     * grabaPrincipal
     * 
     * Método que graba en un archivo de texto la posición de Principal
     * @throws IOException 
     */
    public void grabaPrincipal() throws IOException {
        // Creo el archivo de texto para guardar la información de Principal
        sNombreArchivo = "Principal.txt";
        PrintWriter fileOut = new PrintWriter(new FileWriter(sNombreArchivo));
        // Grabo la X y Y de Principal en un archivo de texto
        fileOut.println(basPrincipal.getX());
        fileOut.println(basPrincipal.getY());
        // Cierro el archivo de texto
        fileOut.close();
    }
    
    /**
     * grabaMalos
     * 
     * Método que graba en un archivo de texto la cantidad de Malos y sus 
     * posiciones dentro del JFrame
     * @throws IOException 
     */
    public void grabaMalos() throws IOException {   
        // Creo el archivo de texto para guardar la información de los Malos
        sNombreArchivo = "Malos.txt";
        PrintWriter fileOut = new PrintWriter(new FileWriter(sNombreArchivo));
        
        // Guardo la cantidad de Malos
        fileOut.println(lklMalos.size());
        
        // Para cada Malo, guardo su X y su Y
        for(int i = 0; i < lklMalos.size(); i++) {
            fileOut.println(lklMalos.get(i).getX());
            fileOut.println(lklMalos.get(i).getY());
        }
        // Cierro el archivo de texto
        fileOut.close();
    }
    
    /**
     * grabaBuenos
     * 
     * Método que graba en un archivo de texto la cantidad de Buenos y sus 
     * posiciones dentro del JFrame
     * @throws IOException 
     */
    public void grabaBuenos() throws IOException {   
        // Creo el archivo de texto para guardar la información de los Malos
        sNombreArchivo = "Buenos.txt";
        PrintWriter fileOut = new PrintWriter(new FileWriter(sNombreArchivo));
        
        // Guardo la cantidad de Buenos
        fileOut.println(lklBuenos.size());
        // Para cada Bueno, guardo su X y su Y
        for(int i = 0; i < lklBuenos.size(); i++) {
            fileOut.println(lklBuenos.get(i).getX());
            fileOut.println(lklBuenos.get(i).getY());
        }
        // Cierro el archivo de texto
        fileOut.close();
    }
    
    /** leeVidasScore
    *
    * Método que lee las Vidas y Score de un archivo de texto
    * 
    **/
    public void leeVidasScore() throws IOException {
        // Para acceder a la información de Vidas y Score
        sNombreArchivo = "VidasScore.txt";
        BufferedReader fileIn;
        
        // Intenta acceder el archivo de texto, si no existe, lo crea
        try {
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        } catch (FileNotFoundException e){
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        }
        
        // Creo la variable String para lectura de datos y la variable
        // temporal para lectura de Vidas y Score
        String sDato;
        int iTemp;
        
        // Leo la primera línea del archivo de texto y paso las Vidas
        sDato = fileIn.readLine();
        iTemp = (Integer.parseInt(sDato));
        iVidas = iTemp;
        
        // Leo la primera línea del archivo de texto y paso el Score
        sDato = fileIn.readLine();
        iTemp = (Integer.parseInt(sDato));
        iScore = iTemp;
        
        // Cierro el arcvhivo de texto
        fileIn.close();
    }
    
    /** leePrincipal
    *
    * Método que lee las posiciones en X y Y del Principal
    * 
    **/
    public void leePrincipal() throws IOException {
        // Para acceder a la información de Principal
        sNombreArchivo = "Principal.txt";
        BufferedReader fileIn;
        
        // Intenta acceder el archivo de texto, si no existe, lo crea
        try {
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        } catch (FileNotFoundException e){
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        }
        
        // Creo la variable String para lectura de datos y la variable temporal
        // entera para pasar las posiciones
        String sDato;
        int iPos;
        
        // Leo la primera línea del archivo de texto y paso la posición en X
        sDato = fileIn.readLine();
        iPos = (Integer.parseInt(sDato));
        basPrincipal.setX(iPos);
        
        // Leo la siguiente línea del archivo de texto y paso la posición en Y
        sDato = fileIn.readLine();
        iPos = (Integer.parseInt(sDato));
        basPrincipal.setY(iPos);
        
        // Cierro el archivo de texto
        fileIn.close();
    }
    
    /** leeMalos
    *
    * Método que lee la cantidad de Buenos
    * 
    **/
    public void leeMalos() throws IOException {
        // Para acceder a la información de Malos
        sNombreArchivo = "Malos.txt";
        BufferedReader fileIn;
        // Intenta acceder el archivo de texto, si no existe, lo crea
        try {
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        } catch (FileNotFoundException e){
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        }
        
        // Creo la variable String para lectura de datos y la variable temporal
        // entera para pasar las posiciones
        String sDato;
        int iPos;
        
        // Leo la cantidad de Buenos y la paso a una variable entera
        sDato = fileIn.readLine();
        int iMalos = (Integer.parseInt(sDato));
        
        // Defino la Imagen de Malos
	URL urlImagenMalo = this.getClass().getResource("goomba.gif");
        imaImagenMalo = Toolkit.getDefaultToolkit().getImage(urlImagenMalo);
        
        // Creo la lista de Malos
        lklMalos = new LinkedList<Base>();
        
        // Creo a los Malos
        for(int iI = 0; iI < iMalos; iI++){
            // Creo a un Malo            
            Base basMalo = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenMalo));
            
            // Añado a Malo a la Lista
            lklMalos.add(basMalo);
        } 
        
        // Leo el siguiente dato del archivo
        sDato = fileIn.readLine();
        // Mientras no se llegue al final del archivo
        while(sDato != null) {  
            // Para cada Bueno, leo sus posiciones en X y Y
            for(int i = 0; i < iMalos; i++) {
                iPos = (Integer.parseInt(sDato));
                lklMalos.get(i).setX(iPos);
                sDato = fileIn.readLine();
                iPos = (Integer.parseInt(sDato));
                lklMalos.get(i).setY(iPos);
                sDato = fileIn.readLine();
            }
        }
        // Cierro el archivo de texto
        fileIn.close();
    }
    
    /**
     * leeBuenos
     * 
     * Método que lee la información del archivo de texto de Buenos y los crea
     * y ajusta sus posiciones en X y Y
     * @throws IOException 
     */
    public void leeBuenos() throws IOException {
        // Para acceder a la información de Buenos
        sNombreArchivo = "Buenos.txt";
        BufferedReader fileIn;
        // Intenta acceder el archivo de texto, si no existe, lo crea
        try {
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        } catch (FileNotFoundException e){
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        }
        
        // Creo la variable String para lectura de datos y la variable temporal
        // entera para pasar las posiciones
        String sDato;
        int iPos;
        
        // Leo la cantidad de Buenos y la paso a una variable entera
        sDato = fileIn.readLine();
        int iBuenos = (Integer.parseInt(sDato));
        
        // Defino la Imagen de Bueno
	URL urlImagenBueno = this.getClass().getResource("coin.gif");
        imaImagenBueno = Toolkit.getDefaultToolkit().getImage(urlImagenBueno);
        
        // Creo la lista de Buenos
        lklBuenos = new LinkedList<Base>();
        
        // Creo a los Buenos
        for(int iI = 0; iI < iBuenos; iI++){
            // Creo a un Bueno           
            Base basBueno = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenBueno));
            
            // Añado a Malo a la Lista
            lklBuenos.add(basBueno);
        } 
        
        // Leo el siguiente dato del archivo
        sDato = fileIn.readLine();
        // Mientras no se llegue al final del archivo
        while(sDato != null) {  
            // Para cada Bueno, leo sus posiciones en X y Y
            for(int i = 0; i < iBuenos; i++) {
                iPos = (Integer.parseInt(sDato));
                lklBuenos.get(i).setX(iPos);
                sDato = fileIn.readLine();
                iPos = (Integer.parseInt(sDato));
                lklBuenos.get(i).setY(iPos);
                sDato = fileIn.readLine();
            }
        }
        // Cierro el archivo de texto
        fileIn.close();
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
            actualiza();
            checaColision();
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
                & lklMalos != null && lklBuenos != null) {
            
            // Mientras Principal tenga vidas
            if(iVidas > 0) {
                // Dibuja la imagen de fondo
                graDibujo.drawImage(imaImagenFondo, 0, 0, getWidth(), 
                getHeight(), this);

                // Dibuja a Principal
                basPrincipal.paint(graDibujo, this);

                // Dibujo a los Malos
                for(Base basMalo : lklMalos){
                    basMalo.paint(graDibujo, this);
                }        

                // Dibujo a los Buenos
                for(Base basBueno : lklBuenos){
                    basBueno.paint(graDibujo, this);
                }          

                graDibujo.setColor(colRojo);
                // Reviso si a Principal aún le quedan Vidas
                if(iVidas > 0){
                    graDibujo.drawString("Vidas: " + iVidas, 50, 50);
                    graDibujo.drawString("Score: " + iScore, 100, 50);
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
        // Si se presiona Q, se mueve hacia la esquina superior izquierda
        if(keyEvent.getKeyCode() == keyEvent.VK_Q) {
            cTecla = 'Q';
        }
        // Si se presiona p, se mueve hacia la esquina superior izquierda
        if(keyEvent.getKeyCode() == keyEvent.VK_P) {
            cTecla = 'P';
        }
        // Si se presiona Q, se mueve hacia la esquina inferior izquierda
        if(keyEvent.getKeyCode() == keyEvent.VK_A) {
            cTecla = 'A';
        }
        // Si se presiona Q, se mueve hacia la esquina inferior derecha
        if(keyEvent.getKeyCode() == keyEvent.VK_L) {
            cTecla = 'L';
        } 
        
        // Si se presiona G, se guarda el archivo
        if(keyEvent.getKeyCode() == keyEvent.VK_G) {
            cTecla = 'G';
        } 
        
        // Si se presiona C, se carga el archivo
        if(keyEvent.getKeyCode() == keyEvent.VK_C) {
            cTecla = 'C';
        } 
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        // Si se suelta la tecla, se le asigna un espacio en blanco
        cTecla = ' ';
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
