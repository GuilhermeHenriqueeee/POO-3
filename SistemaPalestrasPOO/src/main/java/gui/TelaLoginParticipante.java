package gui;

import javax.swing.*;
import model.Participante;
import repository.ParticipanteRepository;
import main.EscolherForma;

public class TelaLoginParticipante extends JFrame {
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnLogin, btnCancelar;

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

        ParticipanteRepository repo = new ParticipanteRepository();

        btnLogin.addActionListener(e -> {
            String login = txtLogin.getText();
            String senha = new String(txtSenha.getPassword());

            Participante participante = repo.buscarPorLogin(login);

            if (participante != null && participante.getSenha().equals(senha)) {
                JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
                // Abre tela inicial do participante passando o objeto participante
                new TelaInicialParticipante(participante).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login ou senha incorretos!");
            }
        });

        btnCancelar.addActionListener(e -> {
            new EscolherForma().setVisible(true);
            dispose();
        });
    }
}
