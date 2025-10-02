package main;

import gui.TelaCadastroParticipante;
import gui.TelaLoginParticipante;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EscolherForma extends JFrame {

    private JButton btnCadastro;
    private JButton btnLogin;
    private JButton btnVoltar;

    public EscolherForma() {
        setTitle("Escolha de Ação");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        btnCadastro = new JButton("Cadastrar Participante");
        btnCadastro.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(Box.createVerticalStrut(20));
        add(btnCadastro);

        btnLogin = new JButton("Fazer Login");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(15));
        add(btnLogin);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(15));
        add(btnVoltar);

        add(Box.createVerticalStrut(20));

        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCadastroParticipante telaCadastro = new TelaCadastroParticipante();
                telaCadastro.setVisible(true);
                dispose();
            }
        });

        btnLogin.addActionListener(e -> {
            TelaLoginParticipante telaLogin = new TelaLoginParticipante();
            telaLogin.setVisible(true);
            dispose();
        });

        btnVoltar.addActionListener(e -> {
            EscolherEntrada escolherEntrada = new EscolherEntrada();
            escolherEntrada.setVisible(true);
            dispose();
        });
    }
}
