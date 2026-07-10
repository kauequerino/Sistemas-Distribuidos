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

import java.nio.charset.StandardCharsets;
import java.io.*;
import java.net.Socket;

// Classe que trata UMA conexão TCP
public class ClientHandler {

    private Socket socket;

    private ProtocolService protocolService =
            new ProtocolService();

    private ObjectMapper mapper =
            new ObjectMapper();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void handle() {

        try (

                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream(),
                                        StandardCharsets.UTF_8
                                )
                        );

                PrintWriter out =
                        new PrintWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream(),
                                        StandardCharsets.UTF_8
                                ),
                                true
                        );

        ) {

            String linha;

            while ((linha = in.readLine()) != null) {

                System.out.println(
                        "\nRECEBIDO: " + linha
                );

                // Converte JSON -> Request
                Request req =
                        mapper.readValue(
                                linha,
                                Request.class
                        );

                System.out.println(
                        "[OP] " + req.op
                );

                // Dados da conexão TCP
                String ip =
                        socket.getInetAddress()
                                .toString();

                int porta =
                        socket.getPort();

                // Processa operação
                Response res =
                        protocolService.process(
                                req,
                                ip,
                                porta
                        );

                // Response -> JSON
                String jsonResposta =
                        mapper.writeValueAsString(res);

                System.out.println(
                        "ENVIADO: " + jsonResposta
                );

                out.println(jsonResposta);
            }

        } catch (Exception e) {

            System.out.println(
                    "Cliente desconectado."
            );
        }
    }
}
