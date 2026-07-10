package service;

public class ValidationService {

    // CAMPOS OBRIGATÓRIOS
    public static String camposObrigatorios(String... campos) {

        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return "Todos os campos devem estar preenchidos.";
            }
        }

        return null;
    }

    // USUÁRIO
    public static String validarUsuario(String usuario) {

        if (usuario.length() < 5 || usuario.length() > 20) {
            return "Usuário com nome inválido (5 à 20 caracteres).";
        }

        if (!usuario.matches("^[a-zA-Z0-9]+$")) {
            return "Usuário com nome inválido (sem espaços ou caracteres especiais).";
        }

        return null;
    }

    // SENHA
    public static String validarSenha(String senha) {

        if (!senha.matches("^\\d{6}$")) {
            return "Senha inválida. Use apenas números e exatamente 6 dígitos.";
        }

        return null;
    }

    // Validação do TOKEN
    // TOKEN
    public static String validarToken(String token) {

        // vazio
        if (token == null || token.trim().isEmpty()) {
            return "Token não pode ser vazio";
        }

        // token ADM
        if (token.equals("adm")) {
            return null;
        }

        // token usuário
        if (!token.matches("^usr_[a-zA-Z0-9]{5,20}$")) {
            return "Token inválido";
        }

        return null;
    }
}
