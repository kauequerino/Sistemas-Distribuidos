package service;

import model.Request;
import model.Response;
import storage.DataStore;
import util.ResponseUtil;

public class UserService {

    // CREATE
    public Response create(Request req) {

        String erro;

        erro = ValidationService.camposObrigatorios(req.nome, req.usuario, req.senha);
        if (erro != null) return ResponseUtil.erro(erro);

        erro = ValidationService.validarUsuario(req.usuario);
        if (erro != null) return ResponseUtil.erro(erro);

        erro = ValidationService.validarSenha(req.senha);
        if (erro != null) return ResponseUtil.erro(erro);

        if (DataStore.usuarios.containsKey(req.usuario)) {
            return ResponseUtil.erro("Usuário já cadastrado.");
        }

        DataStore.usuarios.put(req.usuario, req.senha);
        DataStore.nomes.put(req.usuario, req.nome);

        return ResponseUtil.sucesso("Cadastrado com sucesso");
    }

    // READ
    public Response read(Request req) {

        String erro;

        erro = ValidationService.validarToken(req.token);
        if (erro != null) return ResponseUtil.erro(erro);

        if (!DataStore.tokens.containsKey(req.token)) {
            return ResponseUtil.erro("Token inválido");
        }

        String usuario = DataStore.tokens.get(req.token);

        Response res = new Response();
        res.resposta = "200";
        res.usuario = usuario;
        res.nome = DataStore.nomes.get(usuario);

        return res;
    }

    // UPDATE
    public Response update(Request req) {

        String erro;

        erro = ValidationService.camposObrigatorios(req.nome, req.senha, req.token);
        if (erro != null) return ResponseUtil.erro(erro);

        erro = ValidationService.validarToken(req.token);
        if (erro != null) return ResponseUtil.erro(erro);

        if (!DataStore.tokens.containsKey(req.token)) {
            return ResponseUtil.erro("Token inválido");
        }

        erro = ValidationService.validarSenha(req.senha);
        if (erro != null) return ResponseUtil.erro(erro);

        String usuario = DataStore.tokens.get(req.token);

        DataStore.usuarios.put(usuario, req.senha);
        DataStore.nomes.put(usuario, req.nome);

        return ResponseUtil.sucesso("Atualizado com sucesso");
    }

    // DELETE
    public Response delete(Request req) {

        String erro;

        erro = ValidationService.validarToken(req.token);
        if (erro != null) return ResponseUtil.erro(erro);

        if (!DataStore.tokens.containsKey(req.token)) {
            return ResponseUtil.erro("Token inválido");
        }

        String usuario = DataStore.tokens.get(req.token);

        DataStore.usuarios.remove(usuario);
        DataStore.nomes.remove(usuario);
        DataStore.tokens.remove(req.token);

        return ResponseUtil.sucesso("Deletado com sucesso");
    }
}