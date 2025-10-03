package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Participante;
import model.Palestra;
import repository.ParticipanteRepository;
import repository.PalestraRepository;
import java.time.format.DateTimeFormatter; 

public class TelaParticipantePalestras extends JFrame {

    private Participante participante;
    private ParticipanteRepository participanteRepo = new ParticipanteRepository();
    private PalestraRepository palestraRepo = new PalestraRepository();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JList<String> listaPalestras;
    private DefaultListModel<String> modelPalestras;
    private JButton btnVisualizar, btnInscrever, btnCancelar, btnFechar;

    public TelaParticipantePalestras(Participante participante) {
        this.participante = participanteRepo.buscarPorIdComPalestras(participante.getId());

        setTitle("Minhas Palestras - " + this.participante.getNome());
        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        modelPalestras = new DefaultListModel<>();
        listaPalestras = new JList<>(modelPalestras);
        JScrollPane scroll = new JScrollPane(listaPalestras);
        add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 4, 10, 10));
        btnVisualizar = new JButton("Visualizar");
        btnInscrever = new JButton("Inscrever");
        btnCancelar = new JButton("Cancelar Inscrição");
        btnFechar = new JButton("Fechar");

        painelBotoes.add(btnVisualizar);
        painelBotoes.add(btnInscrever);
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);

        atualizarLista();

        // Lógica de visualização completa da palestra selecionada (com opção de Cancelar)
        btnVisualizar.addActionListener(e -> visualizarPalestra()); 
        
        // Lógica para listar palestras não inscritas e se inscrever nelas
        btnInscrever.addActionListener(e -> inscreverPalestra()); 
        
        // Mantém a ação de cancelar inscrição como um botão direto para conveniência
        btnCancelar.addActionListener(e -> cancelarInscricao()); 
        
        btnFechar.addActionListener(e -> dispose());
    }

    // Atualiza lista da JList
    private void atualizarLista() {
        // Recarrega o participante com suas palestras para refletir o estado atual
        participante = participanteRepo.buscarPorIdComPalestras(participante.getId()); 
        modelPalestras.clear();

        if (participante.getPalestras().isEmpty()) {
            modelPalestras.addElement("Você não está inscrito em nenhuma palestra.");
        } else {
            for (Palestra p : participante.getPalestras()) {
                // Formata a data para dd/MM/yyyy
                String dataFormatada = p.getData().format(formatter); 
                modelPalestras.addElement(p.getNome() + " - " + dataFormatada);
            }
        }
    }

    // Lógica para palestra JÁ INSCRITA 
    private void visualizarPalestra() {
        int index = listaPalestras.getSelectedIndex();
        if (index == -1 || participante.getPalestras().isEmpty() || index >= participante.getPalestras().size()) {
            JOptionPane.showMessageDialog(this, "Selecione uma palestra válida na lista!");
            return;
        }

        // Obtém a palestra da lista local (inscrita)
        Palestra p = participante.getPalestras().get(index);
        
        // Recarrega com todos os detalhes e o COUNT de participantes (evita LazyInitializationException e garante dados)
        p = palestraRepo.buscarPorIdComParticipantes(p.getId()); 

        if (p == null) {
            JOptionPane.showMessageDialog(this, "Erro: Palestra não encontrada no banco de dados!");
            return;
        }

        String palestranteNome = (p.getPalestrante() != null) ? p.getPalestrante().getNome() : "Não definido";
        String dataFormatada = p.getData().format(formatter);
        
        String detalhes = String.format(
            "--- Detalhes da Palestra ---\n" +
            "ID: %d\n" +
            "Nome: %s\n" +
            "Evento: %s\n" +
            "Data: %s\n" +
            "Palestrante: %s\n" +
            "Participantes Inscritos: %d\n",
            p.getId(), p.getNome(), p.getEvento(), dataFormatada, palestranteNome, p.getParticipantes().size()
        );

        // Opção de Cancelar Inscrição no diálogo
        String[] opcoes = {"Cancelar Inscrição", "Fechar"};
        int escolha = JOptionPane.showOptionDialog(
            this,
            detalhes,
            "Detalhes da Palestra Inscrita",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            opcoes,
            opcoes[1] 
        );

        if (escolha == 0) { 
            // Cancela a inscrição
            participanteRepo.cancelarInscricao(participante, p);
            JOptionPane.showMessageDialog(this, "Inscrição cancelada com sucesso!");
            atualizarLista();
        }
    }

    // Lógica para palestra NÃO INSCRITA (botão Inscrever)
    private void inscreverPalestra() {
        List<Palestra> todas = palestraRepo.listarTodas();
        StringBuilder sb = new StringBuilder();

        //Lista apenas palestras ainda não inscritas
        for (Palestra p : todas) {
            if (!participante.getPalestras().contains(p)) {
                String dataFormatada = p.getData().format(formatter);
                sb.append(p.getId()).append(" - ").append(p.getNome())
                  .append(" (").append(dataFormatada).append(")\n");
            }
        }

        if (sb.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Você já está inscrito em todas as palestras!");
            return;
        }

        //Seleciona a palestra pelo ID
        String idStr = JOptionPane.showInputDialog(this, "Escolha a palestra pelo ID:\n" + sb);
        if (idStr == null || idStr.trim().isEmpty()) return; // Usuário cancelou ou deixou vazio
        
        try {
            Long id = Long.parseLong(idStr);
            // Recarrega com todos os detalhes e o COUNT de participantes
            Palestra p = palestraRepo.buscarPorIdComParticipantes(id); 
            
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Palestra não encontrada!");
                return;
            }

            // Mostra os detalhes e pergunta se quer se inscrever
            String palestranteNome = (p.getPalestrante() != null) ? p.getPalestrante().getNome() : "Não definido";
            String dataFormatada = p.getData().format(formatter);

            String detalhes = String.format(
                "--- Detalhes da Palestra ---\n" +
                "ID: %d\n" +
                "Nome: %s\n" +
                "Evento: %s\n" +
                "Data: %s\n" +
                "Palestrante: %s\n" +
                "Participantes Inscritos: %d\n",
                p.getId(), p.getNome(), p.getEvento(), dataFormatada, palestranteNome, p.getParticipantes().size()
            );

            String[] opcoesConfirmacao = {"Sim", "Não"}; 

            int confirm = JOptionPane.showOptionDialog(
                this,
                detalhes + "\nDeseja se inscrever nesta palestra?",
                "Inscrever-se na Palestra",
                JOptionPane.YES_NO_OPTION, // Mantém o tipo de opção para o ícone
                JOptionPane.QUESTION_MESSAGE,
                null, 
                opcoesConfirmacao, // Passa o array de rótulos
                opcoesConfirmacao[1] // Seleção inicial padrão: "Não" (índice 1)
            );

            // Efetua a inscrição, se confirmado (botão "Sim" está no índice 0)
            if (confirm == 0) { 
                if (!participante.getPalestras().contains(p)) {
                    participanteRepo.inscrever(participante, p);
                    JOptionPane.showMessageDialog(this, "Inscrição realizada com sucesso!");
                    atualizarLista();
                } else {
                    JOptionPane.showMessageDialog(this, "Você já está inscrito nessa palestra!");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido!");
        }
    }

    // Mantido o método original para o botão "Cancelar Inscrição"
    private void cancelarInscricao() {
        int index = listaPalestras.getSelectedIndex();
        if (index == -1 || participante.getPalestras().isEmpty() || index >= participante.getPalestras().size()) {
            JOptionPane.showMessageDialog(this, "Selecione uma palestra válida para cancelar!");
            return;
        }

        Palestra p = participante.getPalestras().get(index);
        participanteRepo.cancelarInscricao(participante, p);
        JOptionPane.showMessageDialog(this, "Inscrição cancelada!");
        atualizarLista();
    }
}