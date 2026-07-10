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

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

// Classe padrão de resposta do servidor
public class Response {

    public String resposta;
    public String mensagem;
    public String token;
   
    public String nome;
    public String usuario;

    public List<UsuarioLista> lista_usuarios;
}
