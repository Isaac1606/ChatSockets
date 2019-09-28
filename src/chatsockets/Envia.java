/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isaac
 */
public class Envia implements Runnable {
    
    MulticastSocket servidor;
    InetAddress grupo;
    byte[] buffer;
    String mensaje;
    
    Envia(){}
    
    Envia(MulticastSocket servidor,InetAddress grupo, byte[] buffer, String mensaje){
        this.servidor = servidor;
        this.grupo = grupo;
        this.buffer = buffer;
        this.mensaje = mensaje;
    }
    
    @Override
    public void run() {
        for (;;) {
            DatagramPacket p = new DatagramPacket(buffer, buffer.length, grupo, 9999);
            try {
                servidor.send(p);
            } catch (IOException ex) {
                Logger.getLogger(Envia.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                System.out.println("Enviando mensaje " + mensaje + " con un TTL= " + servidor.getTimeToLive());
            } catch (IOException ex) {
                Logger.getLogger(Envia.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ie) {
            }
        }//for
    }

}
