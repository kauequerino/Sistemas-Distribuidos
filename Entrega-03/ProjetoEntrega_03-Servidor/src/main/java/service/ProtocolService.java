/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author kaueq
 */
import model.Request;
import model.Response;

public class ProtocolService {

    private AuthService authService
            = new AuthService();

    private UserService userService
            = new UserService();

    private AdminService adminService
            = new AdminService();

    private MessageService messageService
            = new MessageService();

    public Response process(
            Request req,
            String ip,
            int porta
    ) {

        switch (req.op) {

            case "login":

                return authService.login(
                        req,
                        ip,
                        porta
                );

            case "logout":

                return authService.logout(req);

            case "cadastrarUsuario":

                return userService.create(req);

            case "consultarUsuario":

                return userService.read(
                        req,
                        ip,
                        porta
                );

            case "atualizarUsuario":

                return userService.update(
                        req,
                        ip,
                        porta
                );

            case "deletarUsuario":

                return userService.delete(
                        req,
                        ip,
                        porta
                );

            // ENTREGA 3
            case "listarUsuariosLogados":

                return userService.listarUsuariosLogados();

            // ADMIN
            case "consultarUsuariosAdmin":

                return adminService.consultarUsuarios(
                        req.token
                );

            case "consultarUsuarioAdmin":

                return adminService.consultarUsuario(
                        req.token,
                        req.usuario
                );

            case "atualizarUsuarioAdmin":

                return adminService.atualizarUsuario(
                        req.token,
                        req.usuario,
                        req.nome,
                        req.senha
                );

            case "deletarUsuarioAdmin":

                return adminService.deletarUsuario(
                        req.token,
                        req.usuario
                );

            case "enviarMensagem":

                return messageService.enviarMensagem(
                        req,
                        ip,
                        porta
                );

            default:

                Response res = new Response();

                res.resposta = "401";
                res.mensagem = "Operação inválida";

                return res;
        }
    }
}
