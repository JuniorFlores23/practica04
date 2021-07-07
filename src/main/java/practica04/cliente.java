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
public class cliente 
{
    private static Socket sock; //socket del cliente
    private static String file;//nombre del archivo a descargar
    
    public static void main(String[] args) throws IOException 
    {
        final int puertoServidor = 5003;//puerto del servidor
        String ip = JOptionPane.showInputDialog("Ingrese la ip del servidor: ");
        try{
            sock = new Socket(ip, puertoServidor);//conexion al servidor
            JOptionPane.showMessageDialog(null, "Conectado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al conectar con el servidor");
            System.exit(1);
        }

        file = JOptionPane.showInputDialog("Ingrese el nombre del archivo con su extension: ");
        DataOutputStream out = new DataOutputStream(sock.getOutputStream());
        out.writeUTF(file);//enviamos al servidor el nombre del archivo
        receiveFile(file);//funcion para recibir el archivo
       
    }

    public static void receiveFile(String fileName) {
        try {
            int bytesRead;//bytes leidos en el archivo a recibir
            InputStream in = sock.getInputStream();

            DataInputStream clientData = new DataInputStream(in);//recibir lo que envia el server

            if((fileName = clientData.readUTF()).equals("NO")){
            JOptionPane.showMessageDialog(null,"ARCHIVO NO ENCONTRADO");
            sock.close();
            }else{//recibiendo el archivo
            OutputStream output = new FileOutputStream("/home/junior/Documentos/"+fileName);//creando el archivo que guardara los datos
            long size = clientData.readLong();//longitud del archivo
            byte[] buffer = new byte[(int)size];
            //mientras la longitud sea mayor a 0 y los datos leidos sean diferente de 0 escribir los datos del archivo
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;//decrementar a la longitud del archivo los bytes leidos
            }
            
            output.close();
            in.close();
            JOptionPane.showMessageDialog(null,"Archivo "+fileName+" descargado.");
            }
        } catch (IOException ex) {
            System.out.println("Exception: "+ex);
        }
    
    }

    
}
