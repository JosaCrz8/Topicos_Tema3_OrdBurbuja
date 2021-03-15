package burbuja;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * 
 * @author JOSAFAT
 */
public class BurbujaPanel extends JPanel{
      private Thread hilo;
    private int NUM_BOX                 =       7;//generar 7 numeros
       //variable para generar hasta maximo y minimo de numeros
    private int max                     =       15;//maximo de numero para mostrar
    private int min                     =       1;//minimo de numeros para empezar
    private CajaNumeros[] bNumber;
    
   
    
    @Override
    public void paintComponent(Graphics g){    
        Graphics2D g2 =(Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);    
        g2.setColor( Color.ORANGE );
        g2.fill(new Rectangle2D.Double(0,0,getWidth(),getHeight()));                 
        //pinta numeros y casillas
        if(bNumber!=null)
        for(CajaNumeros b:bNumber){
            b.draw(g2);
        }
        
    }
    
    /**
     * Genera 7 numeros al azar y asigna a casillas
     * posicion las casillas en el panel
     */
    public void generar(){   //metodo generar    
        bNumber = new CajaNumeros[NUM_BOX];
        Random rn = new Random();
        for(int i=0;i<NUM_BOX;i++){
            bNumber[i] = new CajaNumeros();    
            bNumber[i].x= 10 + bNumber[i].WIDTH * i;
            bNumber[i].y = getHeight()/2 - bNumber[i].HEIGHT/2 ;
            int num = rn.nextInt(max - min + 1) + min;
            bNumber[i].setNumber(String.valueOf(num));
        }
        repaint();
    }
    
    /**
     * Comando para ordenar el array de numeros con el metodo de la burbuja
     */
    public void ordenar(){
        if(bNumber!=null)        
            new BurbujaIniciar().execute();//inicia el metodo
      
    }
     
    /**
     * Clase 
     */ 
    public class BurbujaIniciar extends SwingWorker<Void, Void> {
        //variable para el hilo
      
               
        
        @Override
        protected Void doInBackground() throws Exception {
            
         int i, j;
         CajaNumeros aux;
         for(i=0;i<bNumber.length-1;i++)
            for(j=0;j<bNumber.length-i-1;j++)
                if(bNumber[j+1].getValue()<bNumber[j].getValue()){
                      aux=bNumber[j+1];
                      
                      //animar movimiento
                      girar(j,j+1);                      
                      bNumber[j+1]=bNumber[j];
                      bNumber[j]=aux;
                   }         
            return null;
        }
        
        /**
         * 
         */
        private void girar(int a , int b){
            //movimiento vertical
            for(int i=0; i< bNumber[0].HEIGHT;i++){
                bNumber[a].y -= 1;
                bNumber[b].y += 1;                   
                try {
                    hilo.sleep(15);//velocidad de animacion (milisegundos)  
                 } catch (InterruptedException e) {}
                repaint();
            }
            
            //movimiento horizontal
            for(int i=0; i< bNumber[0].WIDTH;i++){
                 bNumber[a].x += 1;
                 bNumber[b].x -= 1;
                 try {
                    hilo.sleep(15);//velocidad de animacion (milisegundos)  
                 } catch (InterruptedException e) {}
                repaint();
            }
            
            //movimiento vertical
            for(int i=0; i< bNumber[0].HEIGHT;i++){
                bNumber[a].y += 1;
                bNumber[b].y -= 1;                   
                try {
                    hilo.sleep(15);//velocidad de animacion (milisegundos)  
                 } catch (InterruptedException e) {}
                repaint();
            }
        }
        
    }
    
}
