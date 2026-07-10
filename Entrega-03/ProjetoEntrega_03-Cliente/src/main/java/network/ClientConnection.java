/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

/**
 *
 * @author kaueq
 */
//responsavel pelo socket

import com.fasterxml.jackson.databind.ObjectMapper;

import gui.listeners.MessageListener;

import model.Request;
import model.Response;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientConnection {

    private Socket socket;

    private BufferedReader in;

    private PrintWriter out;

    private ObjectMapper mapper;

    private MessageListener listener;

    private Response ultimaResposta;

    public ClientConnection(
            String host,
            int porta
    ) throws IOException {

        socket = new Socket(host, porta);

        in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(),
                        StandardCharsets.UTF_8
                )
        );

        out = new PrintWriter(
                new OutputStreamWriter(
                        socket.getOutputStream(),
                        StandardCharsets.UTF_8
                ),
                true
        );

        mapper = new ObjectMapper();

        iniciarRecebedor();
    }

    public void setListener(
            MessageListener listener
    ) {

        this.listener = listener;
    }

    public synchronized Response send(
            Request req
    ) throws Exception {

        String json =
                mapper.writeValueAsString(req);

        System.out.println(
                "\nENVIADO: " + json
        );

        ultimaResposta = null;

        out.println(json);

        while (ultimaResposta == null) {

            wait();
        }

        return ultimaResposta;
    }

    private void iniciarRecebedor() {

        Thread thread = new Thread(() -> {

            try {

                while (true) {

                    String json =
                            in.readLine();

                    if (json == null) {
                        break;
                    }

                    System.out.println(
                            "RECEBIDO: " + json
                    );

                    Response res =
                            mapper.readValue(
                                    json,
                                    Response.class
                            );

                    // mensagens assíncronas
                    if ("receberMensagem".equals(
                            res.op
                    )) {

                        if (listener != null) {

                            listener.novaMensagem(
                                    res.remetente,
                                    res.mensagem
                            );
                        }

                        continue;
                    }

                    // respostas normais
                    synchronized (this) {

                        ultimaResposta = res;

                        notify();
                    }
                }

            } catch (Exception e) {

                System.out.println(
                        "Conexão encerrada."
                );
            }
        });

        thread.setDaemon(true);

        thread.start();
    }

    public void close()
            throws IOException {

        socket.close();
    }
}