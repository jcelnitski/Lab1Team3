/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author John
 */
public class EchoServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Simple Echo Server");
        
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Waiting for connection.....");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected to client");
            
            try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true)) {
                Supplier<String> socketInput = () -> {
                    try {
                        return br.readLine();
                    } catch (IOException ex) {
                        return null;
                    }
                };
                
                Stream<String> stream = Stream.generate(socketInput);   //reading from client
                stream
                    .map(s -> {
                        System.out.println("Client request: " + s);     
                        out.println(s); //add .toUpperCase here     //sending back to client
                        return s;
                })
                .allMatch(s -> s != null);
            }
            
        } catch (IOException ex) {
            System.out.println("!!!Failed to start server!!!");
        }
        
    }
    
}