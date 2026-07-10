/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package storage;

/**
 *
 * @author kaueq
 */
import model.Sessao;
import java.util.concurrent.ConcurrentHashMap;
import network.ClientHandler;

// é pra simular um "banco de dados"
public class DataStore {

    public static ConcurrentHashMap<String, String> usuarios
            = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, String> nomes
            = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Sessao> tokens
            = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, ClientHandler> usuariosConectados
            = new ConcurrentHashMap<>();

    static {

        usuarios.put("admin", "123456");

        nomes.put("admin", "Administrador");
    }
}
