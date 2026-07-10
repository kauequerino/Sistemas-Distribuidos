package service;

import model.Request;
import model.Response;
import model.Sessao;
import storage.DataStore;
import util.ResponseUtil;

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

        // token obrigatório
        erro = ValidationService.validarToken(req.token);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // token existe?
        if (!DataStore.tokens.containsKey(req.token)) {
            return ResponseUtil.erro("Token inválido");
        }

        // sessão do token
        Sessao sessao
                = DataStore.tokens.get(req.token);

        // IP e porta devem coincidir
        if (!sessao.ip.equals(ip)
                || sessao.porta != porta) {

            return ResponseUtil.erro(
                    "Sessão inválida"
            );
        }

        // resposta
        Response res = new Response();

        res.resposta = "200";
        res.usuario = sessao.usuario;
        res.nome
                = DataStore.nomes.get(sessao.usuario);

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

        // token existe?
        if (!DataStore.tokens.containsKey(req.token)) {
            return ResponseUtil.erro("Token inválido");
        }

        // sessão
        Sessao sessao
                = DataStore.tokens.get(req.token);

        // IP + porta
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

        // atualiza
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

        // token existe?
        if (!DataStore.tokens.containsKey(req.token)) {
            return ResponseUtil.erro("Token inválido");
        }

        // sessão
        Sessao sessao
                = DataStore.tokens.get(req.token);

        // IP + porta
        if (!sessao.ip.equals(ip)
                || sessao.porta != porta) {

            return ResponseUtil.erro(
                    "Acesso negado"
            );
        }

        // ADMIN NÃO PODE SER DELETADO
        if (sessao.usuario.equals("admin")) {

            return ResponseUtil.erro(
                    "Administrador não pode ser deletado"
            );
        }

        // remove usuário
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
}
