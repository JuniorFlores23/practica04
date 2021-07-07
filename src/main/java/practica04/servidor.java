/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica04;
import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;
/**
 *
 * @author junior
 */
public class servidor 
{
    private static ServerSocket serverSocket;
    private static Socket clientSocket = null;

    public static void main(String[] args) throws IOException {

        try {
            serverSocket = new ServerSocket(5003);//creando socket del server
            JOptionPane.showMessageDialog(null,"Servidor iniciado.");
        } catch (Exception e) {
            System.err.println("error al iniciar el servidor.");
            System.exit(1);
        }
   
  
        while (true) {
            try {
               clientSocket = serverSocket.accept();//aceptar conexiones
               System.out.println("Conectado");

                Thread t = new Thread(new Servicio(clientSocket));//crear un hilo

                t.start();

            } catch (Exception e) {
                System.err.println("Error de conexion.");
            }
        }
    }
}
