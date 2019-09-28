/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsockets;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 *
 * @author isaac
 */
public class Cliente {

    private InetAddress grupo;
    private int puerto;
    private MulticastSocket cliente;
    private Mensaje mensaje;
    private byte[] buffer;
    private DatagramPacket p;
    private String nombre;

    public Cliente() {
    }

    public Cliente(InetAddress grupo, int puerto, MulticastSocket cliente, Mensaje mensaje, byte[] buffer, DatagramPacket p, String nombre) {
        this.setGrupo(grupo);
        this.setPuerto(puerto);
        this.setCliente(cliente);
        this.setMensaje(mensaje);
        this.setBuffer(buffer);
        this.setP(p);
        this.setNombre(nombre);
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

    public MulticastSocket getCliente() {
        return cliente;
    }

    public void setCliente(MulticastSocket cliente) {
        this.cliente = cliente;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void creaCliente() throws IOException {
        MulticastSocket cliente = new MulticastSocket(this.getPuerto());
        System.out.println("Cliente escuchando puerto " + cliente.getLocalPort());
        this.setCliente(cliente);
        this.getCliente().setReuseAddress(true);
    }

    public void uneAGrupo(String nombre) throws IOException {
        this.setNombre(nombre);
        this.getCliente().joinGroup(this.getGrupo());
        System.out.println(this.getNombre() + " Te has unido al grupo");
    }

    public void recibeMensaje() throws IOException, ClassNotFoundException {
        /*DatagramPacket p = new DatagramPacket(new byte[2000], 2000);
        this.setP(p);
        this.getCliente().receive(p);
        ByteArrayInputStream bais2 = new ByteArrayInputStream(this.getP().getData());
        ObjectInputStream ois2 = new ObjectInputStream(bais2);
        Mensaje m = (Mensaje) ois2.readObject();
        String mensaje = m.getMsj();
        System.out.println("Datagrama recibido..");

        //String mensaje = new String(this.getP().getData());

        System.out.println("Mensaje: " + mensaje);
         */
        DatagramPacket p = new DatagramPacket(new byte[2000], 2000);
        this.setP(p);
        this.getCliente().receive(p);
        System.out.println("Datagrama recibido..");
        String mensaje = new String(this.getP().getData());

        this.mensaje = new Mensaje(this.getNombre(), mensaje);

        System.out.println("Mensaje: " + this.getMensaje().getMsj());
    }

    public void enviaMensaje(String mensaje) throws IOException {
        System.out.println("1");
        Mensaje m1 = new Mensaje(this.getNombre(), mensaje);
        this.setMensaje(m1);
        System.out.println("2");
//Flujo de salida de Arreglo de bytes 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.out.println("3");
//Flujo de salida de objetos el cual estara escribiendo sobre el flujo de salida de arreglo de bytes
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        System.out.println("4");
//En el flujo de salida de objetos escribes al objeto Archivo 
        oos.writeObject(m1);
        System.out.println("5");
//Haces un flush a los flujos de salida 
        oos.flush();
        System.out.println("6");
        baos.flush();
        System.out.println("7");
        byte[] buffer = baos.toByteArray();
        System.out.println("8");
        //Creamos un DatagramPacket el cual como parametros es el arreglo de bytes que le estas mandando, 
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length, this.getGrupo(), this.getPuerto());
        System.out.println("9");
        this.setP(dp);
        System.out.println("10");
        this.getCliente().send(dp);
    }

    public static void main(String[] args) {
        Cliente c = new Cliente();
        //InetAddress grupo = null;
        c.setGrupo(null);
        //int puerto = 9999;
        c.setPuerto(9999);
        try {
            c.creaCliente();
            try {
                c.setGrupo(InetAddress.getByName("228.1.1.1"));
            } catch (UnknownHostException u) {
                System.err.println("Direccion no valida");
            }//catch

            System.out.println("Ingrese su nombre: ");
            BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
            String nombre = entrada.readLine();

            c.uneAGrupo(nombre);

            //c.enviaMensaje(nombre);
            byte[] buffer = nombre.getBytes();

            DatagramPacket envio = new DatagramPacket(buffer, buffer.length, c.getGrupo(), c.getPuerto());
            c.getCliente().send(envio);

            for (;;) {
                c.recibeMensaje();
                System.out.println("Servidor descubierto: " + c.getP().getAddress());

            }//for

        } catch (Exception e) {

        }//catch
    }//main
}
