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
    
    private AuthService authService = new AuthService();
    private UserService userService = new UserService();
    
    public Response process(Request req) {

        switch (req.op) {

            case "login":
                return authService.login(req);

            case "logout":
                return authService.logout(req);

            case "cadastrarUsuario":
                return userService.create(req);

            case "consultarUsuario":
                return userService.read(req);

            case "atualizarUsuario":
                return userService.update(req);

            case "deletarUsuario":
                return userService.delete(req);

            default:
                Response res = new Response();
                res.resposta = "401";
                res.mensagem = "Operação inválida";
                return res;
        }
    }
}
