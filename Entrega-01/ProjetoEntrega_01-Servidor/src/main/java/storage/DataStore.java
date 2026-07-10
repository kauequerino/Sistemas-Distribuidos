/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package storage;

/**
 *
 * @author kaueq
 */

import java.util.HashMap;
import java.util.Map;

// é pra simular um "banco de dados"
public class DataStore {

    // usuario -> senha
    public static Map<String, String> usuarios = new HashMap<>();

    // usuario -> nome
    public static Map<String, String> nomes = new HashMap<>();

    // token -> usuario
    public static Map<String, String> tokens = new HashMap<>();
}
