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
import model.Request;
import model.Response;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.net.Socket;

// Classe responsável pela comunicação TCP
public class ClientConnection {
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectMapper mapper;
    
    public ClientConnection(String host, int porta) throws IOException {

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
}
     public Response send(Request req) throws IOException {

    String json = mapper.writeValueAsString(req);

    // JSON enviado
    System.out.println("\nENVIADO: " + json);

    out.println(json);

    String resposta = in.readLine();

    // JSON recebido
    System.out.println("RECEBIDO: " + resposta);

    return mapper.readValue(resposta, Response.class);
}

    public void close() throws IOException {
        socket.close();
    }
}
