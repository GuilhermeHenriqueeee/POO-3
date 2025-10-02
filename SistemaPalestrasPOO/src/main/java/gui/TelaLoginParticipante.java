package gui;

import javax.swing.*;
import main.EscolherForma;

public class TelaLoginParticipante extends JFrame {
    JTextField txtLogin;
    JPasswordField txtSenha;
    JButton btnLogin, btnCancelar;

    public TelaLoginParticipante() {
        setTitle("Login - Participante");
        setSize(300,200);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(20,20,80,25);
        add(lblLogin);

        txtLogin = new JTextField();
        txtLogin.setBounds(100,20,160,25);
        add(txtLogin);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(20,60,80,25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(100,60,160,25);
        add(txtSenha);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(40,110,100,30);
        add(btnLogin);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(160,110,100,30);
        add(btnCancelar);

        btnLogin.addActionListener(e -> {
            dispose();
            TelaInicialParticipante telaInicial = new TelaInicialParticipante();
            telaInicial.setVisible(true);
        });

        btnCancelar.addActionListener(e -> {
            EscolherForma escolherForma = new EscolherForma();
            escolherForma.setVisible(true);
            dispose();
        });
    }
}
