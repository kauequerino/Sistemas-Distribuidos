/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author kaueq
 */

import model.Response;

// Classe utilitária para padronizar respostas
public class ResponseUtil {

    public static Response erro(String mensagem) {
        Response r = new Response();
        r.resposta = "401";
        r.mensagem = mensagem;
        return r;
    }

    public static Response sucesso(String mensagem) {
        Response r = new Response();
        r.resposta = "200";
        r.mensagem = mensagem;
        return r;
    }
}
