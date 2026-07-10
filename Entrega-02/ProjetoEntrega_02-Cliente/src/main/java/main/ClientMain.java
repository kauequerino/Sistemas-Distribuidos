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
import model.UsuarioLista;

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

                System.out.println("\n1-Login 2-Logout 3-Cadastrar Usuário 4-Consultar Usuário 5-Atualizar Dados 6-Deletar Usuário 7-Consultar Lista de Usuários Adm 8-Consultar Usuário ADM 9-Atualizar Usuário ADM 10-Deletar Usuário ADM 0-Sair");
                int op = Integer.parseInt(scanner.nextLine());

                Response res = null;

                switch (op) {

                    case 1:
                        System.out.print("Usuario:");
                        ;
                        ;
                        String u = scanner.nextLine();
                        System.out.print("Senha: ");
                        String s = scanner.nextLine();
                        res = service.login(u, s);
                        break;

                    case 2:
                        res = service.logout();
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

                        System.out.print("Token: ");
                        String Mandatoken = scanner.nextLine();

                        res = service.atualizar(
                                n2,
                                s3,
                                Mandatoken
                        );

                        break;

                    case 6:

                        System.out.print("Token: ");
                        String tokenDeletar = scanner.nextLine();

                        res = service.deletar(tokenDeletar);

                        break;

                    case 7:
                        res = service.consultarUsuariosAdmin();
                        break;

                    case 8:

                        System.out.print("Usuario para consultar: ");
                        String usuarioConsulta = scanner.nextLine();

                        res = service.consultarUsuarioAdmin(usuarioConsulta);
                        break;

                    case 9:

                        System.out.print("Usuario que será atualizado: ");
                        String usuarioUpdate = scanner.nextLine();

                        System.out.print("Novo nome: ");
                        String nomeUpdate = scanner.nextLine();

                        System.out.print("Nova senha: ");
                        String senhaUpdate = scanner.nextLine();

                        res = service.atualizarUsuarioAdmin(
                                usuarioUpdate,
                                nomeUpdate,
                                senhaUpdate
                        );

                        break;

                    case 10:

                        System.out.print("Usuario para deletar: ");
                        String usuarioDelete = scanner.nextLine();

                        res = service.deletarUsuarioAdmin(usuarioDelete);
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

                    // Lista de usuários (ADM)
                    if (res.lista_usuarios != null) {

                        System.out.println("\n=== LISTA DE USUÁRIOS ===");

                        for (var usuario : res.lista_usuarios) {

                            System.out.println("Usuario: " + usuario.usuario);
                            System.out.println("Nome: " + usuario.nome);
                            System.out.println("-------------------------");
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
