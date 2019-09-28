/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsockets;

/**
 *
 * @author isaac
 */
public class Mensaje {
    
    private String nombre;
    private String msj;
    private boolean first;
    private int cont;
    
    static int contador;

    public Mensaje(){}

    public Mensaje(String nombre, String msj) {
        this.nombre = nombre;
        this.setMsj(msj);
    }
    
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        
        this.msj = msj;
    }

    public boolean isFirst() {
        return first;
    }

    public void setIsNew(boolean first) {
        this.first = first;
    }

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }
    
    public void isMine(){
        
    }
}
