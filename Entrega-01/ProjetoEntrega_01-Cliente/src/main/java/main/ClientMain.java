/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author kaueq
 */
import model.Response;
import network.ClientConnection;
import service.ClientService;

import java.util.Scanner;

// Classe principal (menu)
public class ClientMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Digite o IP do servidor: ");
            String ip = scanner.nextLine();

            System.out.print("Porta: ");
            int porta = Integer.parseInt(scanner.nextLine());

            ClientConnection connection = new ClientConnection(ip, porta);
            ClientService service = new ClientService(connection);

            while (true) {

                System.out.println("\n1-Login 2-Logout 3-Cadastrar Usuário 4-Consultar Usuário 5-Atualizar Dados 6-Deletar Usuário 0-Sair");
                int op = Integer.parseInt(scanner.nextLine());

                Response res = null;

                switch (op) {

                    case 1:
                        System.out.print("Usuario:");;;
                        String u = scanner.nextLine();
                        System.out.print("Senha: ");
                        String s = scanner.nextLine();
                        res = service.login(u, s);
                        break;

                    case 2:
    res = service.logout();

    if (res != null && "200".equals(res.resposta)) {
        connection.close();
        System.out.println("Conexão encerrada.");
        return;
    }
    break;

                    case 3:
                        System.out.print("Nome: ");
                        String n = scanner.nextLine();
                        System.out.print("Usuario (sem espaços e caracteres especiais, entre 5 à 20 caracteres): ");
                        String u2 = scanner.nextLine();
                        System.out.print("Senha: (apenas numeros e com 6 digitos:");
                        String s2 = scanner.nextLine();
                        res = service.cadastrar(n, u2, s2);
                        break;

                    case 4:
                        res = service.consultar();
                        break;

                    case 5:
                        System.out.print("Novo nome: ");
                        String n2 = scanner.nextLine();
                        System.out.print("Nova senha: ");
                        String s3 = scanner.nextLine();
                        res = service.atualizar(n2, s3);
                        break;

                    case 6:
                        res = service.deletar();
                        break;

                    case 0:
                        connection.close();
                        return;
                }

                // Exibe resposta
                if (res != null) {
                    System.out.println("Status: " + res.resposta);
                    if (res.mensagem != null) {
                        System.out.println(res.mensagem);
                    }
                    if (res.token != null) {
                        System.out.println("Token: " + res.token);
                    }
                    if (res.nome != null) {
                        System.out.println("Nome: " + res.nome);
                    }
                    if (res.usuario != null) {
                        System.out.println("Usuario: " + res.usuario);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
