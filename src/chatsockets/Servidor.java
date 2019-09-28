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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isaac
 */
public class Servidor {

    private InetAddress grupo;
    private int puerto;
    private MulticastSocket servidor;
    private Mensaje mensaje;
    private byte[] buffer;
    private DatagramPacket p;

    public Servidor() {
    }

    public Servidor(InetAddress grupo, int puerto, MulticastSocket servidor, Mensaje mensaje, byte[] buffer, DatagramPacket p) {
        this.setGrupo(grupo);
        this.setPuerto(puerto);
        this.setServidor(servidor);
        this.setMensaje(mensaje);
        this.setBuffer(buffer);
        this.setP(p);
    }

    public InetAddress getGrupo() {
        return grupo;
    }

    public void setGrupo(InetAddress grupo) {
        this.grupo = grupo;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public MulticastSocket getServidor() {
        return servidor;
    }

    public void setServidor(MulticastSocket servidor) {
        this.servidor = servidor;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public DatagramPacket getP() {
        return p;
    }

    public void setP(DatagramPacket p) {
        this.p = p;
    }

    public void creaServidor() throws IOException {

        MulticastSocket servidor = new MulticastSocket(this.getPuerto());
        this.setServidor(servidor);
        this.getServidor().setReuseAddress(true);
        this.getServidor().setTimeToLive(128);

    }

    public void recibeMensajes() throws IOException {
        DatagramPacket p = new DatagramPacket(new byte[2000], 2000);
        this.getServidor().receive(p);
        System.out.println("Datagrama recibido..");
    }

    public void uneAGrupo() throws IOException {

        this.getServidor().joinGroup(this.getGrupo());

    }

    public static void main(String args[]) {
        Servidor s = new Servidor();
        //InetAddress grupo = null;
        s.setGrupo(null);
        //int puerto = 9999;
        s.setPuerto(9999);
        try {
            s.creaServidor();
            String mensaje = "hola";
            byte[] buffer = mensaje.getBytes();
            try {
                s.setGrupo(InetAddress.getByName("228.1.1.1"));
            } catch (UnknownHostException u) {
                System.err.println("Direccion no valida");
            }//catch
            s.uneAGrupo();
            System.out.println("Servidor listo!");
            /*
            boolean bandera = true;

            while (bandera) {
                DatagramPacket p = new DatagramPacket(new byte[2000], 2000);
                s.getServidor().receive(p);
                System.out.println("Datagrama recibido..");
                String nomCliente = new String(p.getData(), 0, p.getLength());
                System.out.println("Cliente conectado: " + nomCliente);
                if (!nomCliente.equals("")) {
                    bandera = false;
                }
            }

            Thread envia = new Thread(new Envia(s.getServidor(), s.getGrupo(), buffer, mensaje));
            envia.start();
            */
            while(true){
                DatagramPacket p = new DatagramPacket(new byte[2000],2000);
                s.getServidor().receive(p);
                System.out.println("Datagrama recibido..");
                String nomCliente = new String(p.getData(), 0, p.getLength());
                System.out.println("Cliente conectado: " + nomCliente);
            }
        } catch (Exception e) {

        }//catch
    }//main
}
