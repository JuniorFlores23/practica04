/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica04;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author junior
 */
public class Servicio implements Runnable
{
    private Socket clientSocket;//socket del cliente
    private DataInputStream in = null;

    public Servicio(Socket client) {
        this.clientSocket = client; //recibiendo el socke cliente del servidor
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            String file = in.readUTF();//recibiendo el nombre del archivo
            System.out.println("Buscando archivo "+file);
            sendFile(file);//funcion que enciara el archivo
        } catch (IOException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }

    public void sendFile(String fileName) throws IOException {
        try {
           
            File myFile = new File("/home/junior/server/"+fileName);//localizando el archivo
            byte[] mybytearray = new byte[(int) myFile.length()];//calculando la longitud del archivo

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            
            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

           
            OutputStream os = clientSocket.getOutputStream();  //enviar al cliente el archivo

            DataOutputStream dos = new DataOutputStream(os); //enviar nombre y longitud del archivo
            dos.writeUTF(myFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();//limpiamos buffer
            System.out.println("Archivo "+fileName+" enviado.");
        } catch (IOException e) {
            OutputStream os = clientSocket.getOutputStream();  //enviar al cliente el archivo

            DataOutputStream dos = new DataOutputStream(os); //enviar nombre y longitud del archivo
            dos.writeUTF("NO");
            System.err.println("Archivo no existe!");
        } 
    }
}
