package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Participante;
import model.Palestra;
import repository.PalestraRepository;

public class TelaListarPalestrasParticipante extends JFrame {

    private JList<String> listaPalestras;
    private JButton btnInscrever, btnVerInfo, btnFechar;
    private DefaultListModel<String> modeloLista;
    private List<Palestra> palestras;
    private Participante participante;
    private PalestraRepository repoPalestra;

    public TelaListarPalestrasParticipante(Participante participante) {
        this.participante = participante;
        this.repoPalestra = new PalestraRepository();

        setTitle("Palestras Disponíveis");
        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        modeloLista = new DefaultListModel<>();
        listaPalestras = new JList<>(modeloLista);
        JScrollPane scroll = new JScrollPane(listaPalestras);

        btnInscrever = new JButton("Inscrever-se");
        btnVerInfo = new JButton("Ver Informações");
        btnFechar = new JButton("Fechar");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnInscrever);
        painelBotoes.add(btnVerInfo);
        painelBotoes.add(btnFechar);

        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarPalestras();

        btnInscrever.addActionListener(e -> {
            int index = listaPalestras.getSelectedIndex();
            if (index >= 0) {
                Palestra p = repoPalestra.buscarPorIdComParticipantes(palestras.get(index).getId());
                if (!participante.getPalestras().contains(p)) {
                    participante.inscrever(p); // adiciona participante na palestra
                    repoPalestra.atualizar(p); // atualiza a palestra no banco
                    JOptionPane.showMessageDialog(this, "Inscrição realizada com sucesso!");
                    carregarPalestras();
                } else {
                    JOptionPane.showMessageDialog(this, "Você já está inscrito nesta palestra.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma palestra para se inscrever.");
            }
        });

        btnVerInfo.addActionListener(e -> {
            int index = listaPalestras.getSelectedIndex();
            if (index >= 0) {
                Palestra p = repoPalestra.buscarPorIdComParticipantes(palestras.get(index).getId());
                // Passa participante e palestra na ordem correta
                TelaInformacoesParticipante telaInfo = new TelaInformacoesParticipante(p, participante);
                telaInfo.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma palestra para ver informações.");
            }
        });

        btnFechar.addActionListener(e -> dispose());
    }

    private void carregarPalestras() {
        modeloLista.clear();
        // Carrega todas as palestras com participantes para evitar LazyInitialization
        palestras = repoPalestra.listarTodas();

        for (Palestra p : palestras) {
            String inscrito = participante.getPalestras().contains(p) ? "(Inscrito)" : "";
            modeloLista.addElement("ID: " + p.getId() + " | " + p.getNome() + " - " + p.getData() + " " + inscrito);
        }
    }
}
