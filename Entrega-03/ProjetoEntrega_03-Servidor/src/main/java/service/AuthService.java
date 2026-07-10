package service;

import model.Request;
import model.Response;
import model.Sessao;
import storage.DataStore;
import util.ResponseUtil;

// Essa classe faz autenticação
public class AuthService {

    // LOGIN
    public Response login(
            Request req,
            String ip,
            int porta
    ) {

        String erro;

        // 1. Campos obrigatórios
        erro = ValidationService.camposObrigatorios(
                req.usuario,
                req.senha
        );

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // 2. Valida usuário
        erro = ValidationService.validarUsuario(
                req.usuario
        );

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // 3. Valida senha
        erro = ValidationService.validarSenha(
                req.senha
        );

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // 4. Verifica login
        if (!DataStore.usuarios.containsKey(req.usuario)
                || !DataStore.usuarios.get(req.usuario)
                        .equals(req.senha)) {

            return ResponseUtil.erro(
                    "Usuário ou senha inválidos"
            );
        }

        // 5. Gera token
        String token;

        if (req.usuario.equals("admin")) {
            token = "adm";
        } else {
            token = "usr_" + req.usuario;
        }

        // 6. Cria sessão
        Sessao sessao = new Sessao();

        sessao.usuario = req.usuario;
        sessao.ip = ip;
        sessao.porta = porta;

        // 7. Salva sessão
        DataStore.tokens.put(
                token,
                sessao
        );

        // 8. Resposta
        Response res = new Response();

        res.resposta = "200";
        res.token = token;

        return res;
    }

    // LOGOUT
    public Response logout(Request req) {

        String erro;

        // 1. Token obrigatório
        erro = ValidationService.validarToken(
                req.token
        );

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // 2. Sessão existe?
        if (!DataStore.tokens.containsKey(
                req.token
        )) {

            return ResponseUtil.erro(
                    "Erro ao efetuar logout"
            );
        }

        // 3. Remove sessão
        DataStore.tokens.remove(
                req.token
        );

        return ResponseUtil.sucesso(
                "Logout efetuado"
        );
    }
}