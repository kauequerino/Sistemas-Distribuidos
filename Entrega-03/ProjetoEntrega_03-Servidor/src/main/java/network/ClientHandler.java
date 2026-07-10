/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

/**
 *
 * @author kaueq
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Request;
import model.Response;
import service.ProtocolService;
import storage.DataStore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.Socket;

import java.nio.charset.StandardCharsets;

public class ClientHandler {

    private Socket socket;

    private ProtocolService protocolService
            = new ProtocolService();

    private ObjectMapper mapper
            = new ObjectMapper();

    // usado para enviar mensagens ao cliente
    private PrintWriter out;

    // usuário associado à conexão
    private String usuario;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void handle() {

        try {

            BufferedReader in
                    = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream(),
                                    StandardCharsets.UTF_8
                            )
                    );

            this.out
                    = new PrintWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream(),
                                    StandardCharsets.UTF_8
                            ),
                            true
                    );

            String linha;

            while ((linha = in.readLine()) != null) {

                System.out.println(
                        "\nRECEBIDO: " + linha
                );

                Request req
                        = mapper.readValue(
                                linha,
                                Request.class
                        );

                System.out.println(
                        "[OP] " + req.op
                );

                String ip
                        = socket.getInetAddress()
                                .toString();

                int porta
                        = socket.getPort();

                Response res
                        = protocolService.process(
                                req,
                                ip,
                                porta
                        );

                // LOGIN
                if ("login".equals(req.op)
                        && "200".equals(res.resposta)) {

                    usuario = req.usuario;

                    DataStore.usuariosConectados.put(
                            usuario,
                            this
                    );

                    System.out.println(
                            "[ONLINE] " + usuario
                    );

                    System.out.println(
                            DataStore.usuariosConectados.keySet()
                    );
                }

                // LOGOUT
                if ("logout".equals(req.op)
                        && "200".equals(res.resposta)
                        && usuario != null) {

                    DataStore.usuariosConectados.remove(
                            usuario
                    );

                    System.out.println(
                            "[OFFLINE] " + usuario
                    );

                    System.out.println(
                            DataStore.usuariosConectados.keySet()
                    );

                    usuario = null;
                }

                String jsonResposta
                        = mapper.writeValueAsString(res);

                System.out.println(
                        "ENVIADO: "
                        + jsonResposta
                );

                out.println(jsonResposta);
            }

        } catch (Exception e) {

            System.out.println(
                    "Cliente desconectado."
            );

        } finally {

            if (usuario != null) {

                DataStore.usuariosConectados.remove(
                        usuario
                );

                System.out.println(
                        "[OFFLINE] " + usuario
                );

                System.out.println(
                        DataStore.usuariosConectados.keySet()
                );
            }

            try {
                socket.close();
            } catch (Exception e) {
            }
        }
    }

    public void enviarMensagem(String json) {

      

        if (out != null) {

            out.println(json);
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public Socket getSocket() {
        return socket;
    }
}
