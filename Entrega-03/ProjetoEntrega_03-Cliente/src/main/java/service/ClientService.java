package service;

import model.Request;
import model.Response;
import network.ClientConnection;

public class ClientService {

    private ClientConnection connection;

    private String token;

    public ClientService(
            ClientConnection connection
    ) {

        this.connection = connection;
    }

    public String getToken() {

        return token;
    }

    // LOGIN
    public Response login(
            String usuario,
            String senha
    ) throws Exception {

        Request req = new Request();

        req.op = "login";
        req.usuario = usuario;
        req.senha = senha;

        Response res =
                connection.send(req);

        if ("200".equals(res.resposta)) {

            token = res.token;

            
        }

        return res;
    }

    // LOGOUT
    public Response logout()
            throws Exception {

        Request req = new Request();

        req.op = "logout";
        req.token = token;

        Response res =
                connection.send(req);

        if ("200".equals(res.resposta)) {

            token = null;
        }

        return res;
    }

    // CADASTRAR USUÁRIO
    public Response cadastrar(
            String nome,
            String usuario,
            String senha
    ) throws Exception {

        Request req = new Request();

        req.op = "cadastrarUsuario";
        req.nome = nome;
        req.usuario = usuario;
        req.senha = senha;

        return connection.send(req);
    }

    // CONSULTAR PRÓPRIO USUÁRIO
    public Response consultar()
            throws Exception {

        Request req = new Request();

        req.op = "consultarUsuario";
        req.token = token;

        return connection.send(req);
    }

    // ATUALIZAR USUÁRIO
    public Response atualizar(
            String nome,
            String senha
    ) throws Exception {

        Request req = new Request();

        req.op = "atualizarUsuario";
        req.token = token;
        req.nome = nome;
        req.senha = senha;

        return connection.send(req);
    }

    // DELETAR USUÁRIO
    public Response deletar()
            throws Exception {

        Request req = new Request();

        req.op = "deletarUsuario";
        req.token = token;

        return connection.send(req);
    }

    // ADMIN
    public Response consultarUsuariosAdmin()
            throws Exception {

        Request req = new Request();

        req.op = "consultarUsuariosAdmin";
        req.token = token;

        return connection.send(req);
    }

    public Response consultarUsuarioAdmin(
            String usuario
    ) throws Exception {

        Request req = new Request();

        req.op = "consultarUsuarioAdmin";
        req.token = token;
        req.usuario = usuario;

        return connection.send(req);
    }

    public Response atualizarUsuarioAdmin(
            String usuario,
            String nome,
            String senha
    ) throws Exception {

        Request req = new Request();

        req.op = "atualizarUsuarioAdmin";
        req.token = token;
        req.usuario = usuario;
        req.nome = nome;
        req.senha = senha;

        return connection.send(req);
    }

    public Response deletarUsuarioAdmin(
            String usuario
    ) throws Exception {

        Request req = new Request();

        req.op = "deletarUsuarioAdmin";
        req.token = token;
        req.usuario = usuario;

        return connection.send(req);
    }

    // LISTAR LOGADOS
    public Response listarUsuariosLogados()
            throws Exception {

        Request req = new Request();

        req.op = "listarUsuariosLogados";
        req.token = token;

        return connection.send(req);
    }

    // ENVIAR MENSAGEM
    public Response enviarMensagem(
            String destinatario,
            String mensagem
    ) throws Exception {

        Request req = new Request();

        req.op = "enviarMensagem";
        req.token = token;
        req.destinatario = destinatario;
        req.mensagem = mensagem;

        return connection.send(req);
    }
}