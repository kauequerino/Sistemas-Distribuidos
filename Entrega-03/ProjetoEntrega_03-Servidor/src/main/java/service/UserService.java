package service;

import model.Request;
import model.Response;
import model.Sessao;
import storage.DataStore;
import util.ResponseUtil;
import java.util.ArrayList;

public class UserService {

    // CREATE
    public Response create(Request req) {

        String erro;

        erro = ValidationService.camposObrigatorios(
                req.nome,
                req.usuario,
                req.senha
        );

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        erro = ValidationService.validarUsuario(req.usuario);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        erro = ValidationService.validarSenha(req.senha);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        if (DataStore.usuarios.containsKey(req.usuario)) {
            return ResponseUtil.erro(
                    "Usuário já cadastrado."
            );
        }

        DataStore.usuarios.put(
                req.usuario,
                req.senha
        );

        DataStore.nomes.put(
                req.usuario,
                req.nome
        );

        return ResponseUtil.sucesso(
                "Cadastrado com sucesso"
        );
    }

    // READ
    public Response read(
            Request req,
            String ip,
            int porta
    ) {

        String erro;

        erro = ValidationService.validarToken(req.token);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        if (!DataStore.tokens.containsKey(req.token)) {
            return ResponseUtil.erro("Token inválido");
        }

        Sessao sessao
                = DataStore.tokens.get(req.token);

        if (!sessao.ip.equals(ip)
                || sessao.porta != porta) {

            return ResponseUtil.erro(
                    "Sessão inválida"
            );
        }

        Response res = new Response();

        res.resposta = "200";
        res.usuario = sessao.usuario;
        res.nome =
                DataStore.nomes.get(sessao.usuario);

        return res;
    }

    // UPDATE
    public Response update(
            Request req,
            String ip,
            int porta
    ) {

        String erro;

        erro = ValidationService.camposObrigatorios(
                req.nome,
                req.senha,
                req.token
        );

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        erro = ValidationService.validarToken(req.token);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        if (!DataStore.tokens.containsKey(req.token)) {
            return ResponseUtil.erro("Token inválido");
        }

        Sessao sessao =
                DataStore.tokens.get(req.token);

        if (!sessao.ip.equals(ip)
                || sessao.porta != porta) {

            return ResponseUtil.erro(
                    "Acesso negado"
            );
        }

        erro = ValidationService.validarSenha(req.senha);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        DataStore.usuarios.put(
                sessao.usuario,
                req.senha
        );

        DataStore.nomes.put(
                sessao.usuario,
                req.nome
        );

        return ResponseUtil.sucesso(
                "Atualizado com sucesso"
        );
    }

    // DELETE
    public Response delete(
            Request req,
            String ip,
            int porta
    ) {

        String erro;

        erro = ValidationService.validarToken(req.token);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        if (!DataStore.tokens.containsKey(req.token)) {
            return ResponseUtil.erro("Token inválido");
        }

        Sessao sessao =
                DataStore.tokens.get(req.token);

        if (!sessao.ip.equals(ip)
                || sessao.porta != porta) {

            return ResponseUtil.erro(
                    "Acesso negado"
            );
        }

        if (sessao.usuario.equals("admin")) {

            return ResponseUtil.erro(
                    "Administrador não pode ser deletado"
            );
        }

        DataStore.usuarios.remove(
                sessao.usuario
        );

        DataStore.nomes.remove(
                sessao.usuario
        );

        DataStore.tokens.remove(req.token);

        return ResponseUtil.sucesso(
                "Deletado com sucesso"
        );
    }

    public Response listarUsuariosLogados() {

        Response res = new Response();

        res.resposta = "200";

        res.usuariosLogados =
                new ArrayList<>(
                        DataStore.usuariosConectados.keySet()
                );

        return res;
    }
}
