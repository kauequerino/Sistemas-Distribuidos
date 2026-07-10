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
import network.ClientConnection;


// Camada para montar as requisições 
public class ClientService {
     private ClientConnection connection;
    private String token;

    public ClientService(ClientConnection connection) {
        this.connection = connection;
    }

    public Response login(String usuario, String senha) throws Exception {
        Request req = new Request();

        req.op = "login";
        req.usuario = usuario;
        req.senha = senha;

        Response res = connection.send(req);

        // Guarda token se login OK
        if ("200".equals(res.resposta)) {
            token = res.token;
        }

        return res;
    }

    public Response logout() throws Exception {
        Request req = new Request();

        req.op = "logout";
        req.token = token;

        return connection.send(req);
    }

    public Response cadastrar(String nome, String usuario, String senha) throws Exception {
        Request req = new Request();

        req.op = "cadastrarUsuario";
        req.nome = nome;
        req.usuario = usuario;
        req.senha = senha;

        return connection.send(req);
    }

    public Response consultar() throws Exception {
        Request req = new Request();

        req.op = "consultarUsuario";
        req.token = token;

        return connection.send(req);
    }

    public Response atualizar(String nome, String senha) throws Exception {
        Request req = new Request();

        req.op = "atualizarUsuario";
        req.token = token;
        req.nome = nome;
        req.senha = senha;

        return connection.send(req);
    }

    public Response deletar() throws Exception {
        Request req = new Request();

        req.op = "deletarUsuario";
        req.token = token;

        return connection.send(req);
    }
}
