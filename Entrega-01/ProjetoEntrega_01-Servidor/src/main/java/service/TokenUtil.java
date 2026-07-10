/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author kaueq
 */


public class TokenUtil {

    public static boolean tokenValido(String token) {

        if (token == null || token.isEmpty())
            return false;

        String[] partes = token.split("_");

        // precisa ser usr_nome
        if (partes.length != 2)
            return false;

        String role = partes[0];
        String usuario = partes[1];

        if (!role.equals("usr"))
            return false;

        return usuario.matches("[a-zA-Z0-9]{5,20}");
    }

    public static String extrairUsuario(String token) {
        return token.split("_")[1];
    }
}