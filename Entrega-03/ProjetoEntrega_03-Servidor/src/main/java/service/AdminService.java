/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author kaueq
 */
import model.Response;
import model.UsuarioLista;
import storage.DataStore;
import util.ResponseUtil;

import java.util.ArrayList;
import java.util.List;


public class AdminService {

    // CONSULTAR TODOS USUÁRIOS
    public Response consultarUsuarios(String token) {

        // 1. Token obrigatório
        String erro = ValidationService.validarToken(token);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // 2. Apenas ADM pode acessar
        if (!token.equals("adm")
                || !DataStore.tokens.containsKey("adm")) {

            return ResponseUtil.erro("Deve ser ADM para consultar a lista");
        }

        // 3. Monta lista
        List<UsuarioLista> lista = new ArrayList<>();

        for (String usuario : DataStore.usuarios.keySet()) {

            // NÃO mostrar admin
            if (usuario.equals("admin")) {
                continue;
            }

            UsuarioLista u = new UsuarioLista();

            u.usuario = usuario;
            u.nome = DataStore.nomes.get(usuario);

            lista.add(u);
        }

        // 4. Retorno
        Response res = new Response();

        res.resposta = "200";
        res.lista_usuarios = lista;

        return res;
    }

    // CONSULTAR USUÁRIO (ADMIN)
    public Response consultarUsuario(String token, String usuario) {

        String erro;

        // 1. Token obrigatório
        erro = ValidationService.validarToken(token);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // 2. Apenas ADM
        if (!token.equals("adm")
                || !DataStore.tokens.containsKey("adm")) {

            return ResponseUtil.erro("Token inválido");
        }

        // 3. Campo usuário obrigatório
        erro = ValidationService.camposObrigatorios(usuario);

        if (erro != null) {
            return ResponseUtil.erro("Campo usuario ausente");
        }

        // 4. Usuário existe?
        if (!DataStore.usuarios.containsKey(usuario)) {
            return ResponseUtil.erro("Usuario nao encontrado");
        }

        // 5. Monta resposta
        Response res = new Response();

        res.resposta = "200";
        res.usuario = usuario;
        res.nome = DataStore.nomes.get(usuario);

        return res;
    }

    // UPDATE USER (ADMIN)
    public Response atualizarUsuario(
            String token,
            String usuario,
            String nome,
            String senha
    ) {

        String erro;

        // 1. Token obrigatório
        erro = ValidationService.validarToken(token);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // 2. Apenas ADM
        if (!token.equals("adm")
                || !DataStore.tokens.containsKey("adm")) {

            return ResponseUtil.erro("Token inválido");
        }

        // 3. Campos obrigatórios
        erro = ValidationService.camposObrigatorios(
                usuario,
                nome,
                senha
        );

        if (erro != null) {
            return ResponseUtil.erro(
                    "Todos os campos devem estar preenchidos"
            );
        }

        // 4. Usuário existe?
        if (!DataStore.usuarios.containsKey(usuario)) {
            return ResponseUtil.erro("Usuario nao encontrado");
        }

        // 5. Validar senha
        erro = ValidationService.validarSenha(senha);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // 6. Atualiza dados
        DataStore.nomes.put(usuario, nome);
        DataStore.usuarios.put(usuario, senha);

        // 7. Sucesso
        return ResponseUtil.sucesso(
                "Usuario atualizado com sucesso"
        );
    }

    // DELETE USER (ADMIN)
    public Response deletarUsuario(
            String token,
            String usuario
    ) {

        // 1. Token obrigatório
        String erro = ValidationService.validarToken(token);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // 2. Apenas ADM
        if (!token.equals("adm")
                || !DataStore.tokens.containsKey("adm")) {

            return ResponseUtil.erro("Apenas ADM pode deletar usuários");
        }

        // 3. Usuário obrigatório
        erro = ValidationService.camposObrigatorios(usuario);

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // 4. Não permitir deletar admin
        if (usuario.equals("admin")) {
            return ResponseUtil.erro("Administrador não pode ser deletado");
        }

        // 5. Usuário existe?
        if (!DataStore.usuarios.containsKey(usuario)) {
            return ResponseUtil.erro("Usuário não encontrado");
        }

        // 6. Remove usuário
        DataStore.usuarios.remove(usuario);

        // 7. Remove nome
        DataStore.nomes.remove(usuario);

        // 8. Remove token ativo do usuário
        String tokenRemover = "usr_" + usuario;

        DataStore.tokens.remove(tokenRemover);

        // 9. Sucesso
        return ResponseUtil.sucesso("Usuario deletado com sucesso");
    }
}
