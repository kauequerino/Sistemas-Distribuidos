/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author kaueq
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    public String resposta;

    public String mensagem;

    public String token;

    public String nome;

    public String usuario;

    // ENTREGA 2
    public List<UsuarioLista> lista_usuarios;

    // ENTREGA 3
    @JsonProperty("lista_usuarios")
    public List<String> usuariosLogados;

    // Mensagens
    public String op;

    public String remetente;
}