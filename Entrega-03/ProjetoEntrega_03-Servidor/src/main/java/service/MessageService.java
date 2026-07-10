/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author kaueq
 */


import com.fasterxml.jackson.databind.ObjectMapper;

import model.Request;
import model.Response;
import model.Sessao;

import network.ClientHandler;

import storage.DataStore;

import util.ResponseUtil;

public class MessageService {

    private ObjectMapper mapper =
            new ObjectMapper();

    public Response enviarMensagem(
            Request req,
            String ip,
            int porta
    ) {

        String erro;

        // Token obrigatório
        erro = ValidationService.validarToken(
                req.token
        );

        if (erro != null) {
            return ResponseUtil.erro(erro);
        }

        // Campos obrigatórios
        erro = ValidationService.camposObrigatorios(
                req.destinatario,
                req.mensagem
        );

        if (erro != null) {

            return ResponseUtil.erro(
                    "Destinatário e mensagem são obrigatórios"
            );
        }

        // Token existe?
        if (!DataStore.tokens.containsKey(
                req.token
        )) {

            return ResponseUtil.erro(
                    "Token inválido"
            );
        }

        // Recupera sessão
        Sessao sessao =
                DataStore.tokens.get(
                        req.token
                );

        // Valida IP e porta
        if (!sessao.ip.equals(ip)
                || sessao.porta != porta) {

            return ResponseUtil.erro(
                    "Acesso negado"
            );
        }

        try {

            Response msg =
                    new Response();

            msg.op =
                    "receberMensagem";

            msg.remetente =
                    sessao.usuario;

            msg.mensagem =
                    req.mensagem;

            String json =
                    mapper.writeValueAsString(
                            msg
                    );

            // =====================
            // BROADCAST
            // =====================

            if ("/todos".equals(
                    req.destinatario
            )) {

                for (ClientHandler cliente
                        : DataStore.usuariosConectados.values()) {

                    // não envia para si mesmo
                    if (cliente.getUsuario()
                            .equals(sessao.usuario)) {

                        continue;
                    }

                    cliente.enviarMensagem(
                            json
                    );
                }

                return ResponseUtil.sucesso(
                        "Broadcast enviado"
                );
            }

            // =====================
            // MENSAGEM PRIVADA
            // =====================

            ClientHandler destino =
                    DataStore.usuariosConectados.get(
                            req.destinatario
                    );

            if (destino == null) {

                return ResponseUtil.erro(
                        "Destinatário não está online"
                );
            }

            destino.enviarMensagem(
                    json
            );

            return ResponseUtil.sucesso(
                    "Mensagem enviada"
            );

        } catch (Exception e) {

            return ResponseUtil.erro(
                    "Erro ao enviar mensagem"
            );
        }
    }
}
