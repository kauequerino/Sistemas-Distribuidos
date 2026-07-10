/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author kaueq
 */

import network.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
// Classe principal que inicia o servidorzao
public class ServerMain {
   public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Solicita a porta ao usuário
        System.out.print("Digite a porta do servidor: ");
        int porta = scanner.nextInt();

        // Validação simples (evita portas inválidas)
        if (porta < 20000 || porta > 25000) {
            System.out.println("Porta inválida. Use entre 20000 e 25000.");
            return;
        }

        try (ServerSocket serverSocket = new ServerSocket(porta)) {

            System.out.println("Servidor rodando na porta " + porta);

            while (true) {

                // Aguarda conexão de cliente (bloqueante)
                Socket socket = serverSocket.accept();

                System.out.println("Cliente conectado: " + socket.getInetAddress());

                // sem threads (alterar para as proximas entregas):
                // trata um cliente por vez
                ClientHandler handler = new ClientHandler(socket);
                handler.handle();
            }

        } catch (Exception e) {
            System.out.println("Erro ao iniciar servidor: " + e.getMessage());
        }
    }
}
