/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.frames;

/**
 *
 * @author kaueq
 */

import gui.panels.AdminPanel;
import gui.panels.ChatPanel;
import gui.panels.ContaPanel;

import network.ClientConnection;
import service.ClientService;

import javax.swing.*;

public class ChatFrame extends JFrame {

    private ClientService service;

    private ClientConnection connection;

    private JTabbedPane abas;

    public ChatFrame(
            ClientService service,
            ClientConnection connection
    ) {

        this.service = service;
        this.connection = connection;

        setTitle("Sistema de Chat");

        setSize(800, 600);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        abas = new JTabbedPane();

        ChatPanel chatPanel =
                new ChatPanel(service);

        // liga o listener
        connection.setListener(chatPanel);

        abas.addTab(
                "Chat",
                chatPanel
        );

        abas.addTab(
                "Minha Conta",
                new ContaPanel(service)
        );

        abas.addTab(
                "Administração",
                new AdminPanel(service)
        );

        add(abas);

        setVisible(true);
    }
}