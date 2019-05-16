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
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author John
 */
public class EchoClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Simple Echo Client");
        
        try {
            System.out.println("Waiting for connection.....");
            InetAddress localAddress = InetAddress.getLocalHost();
            
            try (Socket clientSocket = new Socket(localAddress, 6000);
                PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                    clientSocket.getInputStream()))) {
                        System.out.println("Connected to server");
                        Scanner scanner = new Scanner(System.in);
                        
                        Supplier<String> scannerInput = () -> scanner.nextLine();
                        System.out.print("Enter text: ");
                        Stream.generate(scannerInput)
                            .map(s -> {
                                out.println(s);
                                System.out.println("Server response: " + s);
                                System.out.print("Enter text: ");
                            return s;
                        })
                        .allMatch(s -> !"quit".equalsIgnoreCase(s));
                    }
        } catch (IOException ex) {
            System.out.println("!!!Failed to connect to server!!!");
        }
        
    }
    
}
