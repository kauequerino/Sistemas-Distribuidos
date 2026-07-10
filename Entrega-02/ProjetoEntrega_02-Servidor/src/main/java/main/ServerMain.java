package main;

import network.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// Classe principal que inicia o servidor
public class ServerMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Solicita a porta ao usuário
        System.out.print("Digite a porta do servidor: ");
        int porta = scanner.nextInt();

        // Validação simples
        if (porta < 20000 || porta > 25000) {

            System.out.println(
                    "Porta inválida. Use entre 20000 e 25000."
            );

            return;
        }

        try (ServerSocket serverSocket =
                     new ServerSocket(porta)) {

            System.out.println(
                    "Servidor rodando na porta " + porta
            );

            while (true) {

                // Aguarda cliente
                Socket socket = serverSocket.accept();

                System.out.println(
                        "Cliente conectado: "
                        + socket.getInetAddress()
                        + ":" + socket.getPort()
                );

                // Cria handler da conexão
                ClientHandler handler =
                        new ClientHandler(socket);

                // THREAD PARA O CLIENTE
                Thread thread = new Thread(() -> {
                    handler.handle();
                });

                // inicia thread
                thread.start();
            }

        } catch (Exception e) {

            System.out.println(
                    "Erro ao iniciar servidor: "
                    + e.getMessage()
            );
        }
    }
}