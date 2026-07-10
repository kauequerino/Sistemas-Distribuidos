/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author kaueq
 */
import network.ClientHandler;

import javax.swing.*;
import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import storage.DataStore;
import javax.swing.Timer;
import java.util.ArrayList;

public class ServerGui extends JFrame {

    private JTextArea areaLog;

    private DefaultListModel<String> modeloUsuarios;

    private JList<String> listaUsuarios;
    private Timer timer;

    public ServerGui() {

        setTitle("Servidor de Chat");

        setSize(700, 500);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        areaLog = new JTextArea();

        areaLog.setEditable(false);

        JScrollPane scrollLog
                = new JScrollPane(areaLog);

        modeloUsuarios
                = new DefaultListModel<>();

        listaUsuarios
                = new JList<>(modeloUsuarios);

        JScrollPane scrollUsuarios
                = new JScrollPane(listaUsuarios);

        scrollUsuarios.setPreferredSize(
                new Dimension(200, 0)
        );

        JSplitPane split
                = new JSplitPane(
                        JSplitPane.HORIZONTAL_SPLIT,
                        scrollUsuarios,
                        scrollLog
                );

        split.setDividerLocation(200);

        add(split);

        timer = new Timer(
                2000,
                e -> atualizarUsuarios(
                        new ArrayList<>(
                                DataStore.usuariosConectados.keySet()
                        )
                )
        );

        timer.start();

        setVisible(true);
    }

    public void adicionarLog(String texto) {

        SwingUtilities.invokeLater(() -> {

            areaLog.append(texto + "\n");
        });
    }

    public void atualizarUsuarios(
            java.util.List<String> usuarios
    ) {

        SwingUtilities.invokeLater(() -> {

            modeloUsuarios.clear();

            for (String usuario : usuarios) {

                modeloUsuarios.addElement(usuario);
            }
        });
    }

    public void iniciarServidor(int porta) {

        Thread servidor = new Thread(() -> {

            try (ServerSocket serverSocket
                    = new ServerSocket(porta)) {

                adicionarLog(
                        "Servidor iniciado na porta "
                        + porta
                );

                while (true) {

                    Socket socket
                            = serverSocket.accept();

                    adicionarLog(
                            "Cliente conectado: "
                            + socket.getInetAddress()
                            + ":"
                            + socket.getPort()
                    );

                    ClientHandler handler
                            = new ClientHandler(socket);

                    Thread thread
                            = new Thread(() -> {

                                handler.handle();
                            });

                    thread.start();
                }

            } catch (Exception e) {

                adicionarLog(
                        "Erro: "
                        + e.getMessage()
                );
            }

        });

        servidor.start();
    }
}
