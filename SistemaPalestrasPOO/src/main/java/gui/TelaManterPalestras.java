package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;
import model.Palestra;
import model.Palestrante;
import model.Participante; 
import repository.PalestraRepository;
import repository.PalestranteRepository;
import repository.ParticipanteRepository;

public class TelaManterPalestras extends JFrame {
    private JButton btnListar, btnBuscar, btnCadastrar, btnSelecionar, btnVoltar;
    private PalestraRepository repo;
    private PalestranteRepository repoPalestrante;
    private ParticipanteRepository repoParticipante; 

    public TelaManterPalestras() {
        setTitle("Manter Palestras");
        setSize(400, 350);
        setLayout(new GridLayout(6, 1, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JLabel lblTitulo = new JLabel("Gerenciamento de Palestras", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo);

        btnListar = new JButton("Listar todas");
        btnBuscar = new JButton("Buscar (ID, Palestrante, Evento, Data)");
        btnCadastrar = new JButton("Cadastrar nova");
        btnSelecionar = new JButton("Selecionar palestra");
        btnVoltar = new JButton("Voltar");

        add(btnListar);
        add(btnBuscar);
        add(btnCadastrar);
        add(btnSelecionar);
        add(btnVoltar);

        repo = new PalestraRepository();
        repoPalestrante = new PalestranteRepository();
        repoParticipante = new ParticipanteRepository(); // Inicialização do novo Repositório

        btnSelecionar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Digite o ID da palestra para selecionar:");
            if (input == null || input.trim().isEmpty()) {
                return;
            }

            try {
                Long id = Long.parseLong(input.trim());
                Palestra p = repo.buscarPorIdComParticipantes(id); // Usa o método com FETCH

                if (p != null) {
                    String[] opcoes = {"Visualizar Info", "Atualizar Nome/Data/Evento", "Vincular Palestrante", "Desvincular Palestrante", "Gerenciar Participantes", "Excluir"};
                    int escolha = JOptionPane.showOptionDialog(this, "Selecione a operação para a palestra " + p.getNome() + ":", "Operações", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

                    switch (escolha) {
                        case 0: // Visualizar Info
                            StringBuilder info = new StringBuilder();
                            info.append("ID: ").append(p.getId()).append("\n");
                            info.append("Nome: ").append(p.getNome()).append("\n");
                            info.append("Data: ").append(p.getData()).append("\n");
                            info.append("Evento: ").append(p.getEvento()).append("\n");
                            info.append("Palestrante: ").append(p.getPalestrante() != null ? p.getPalestrante().getNome() : "N/A").append("\n");
                            info.append("Participantes: ").append(p.getParticipantes() != null ? p.getParticipantes().size() : 0).append(" inscritos\n");
                            JOptionPane.showMessageDialog(this, info.toString());
                            break;
                        case 1: // Atualizar Nome/Data/Evento
                            break;
                        case 2: // Vincular Palestrante
                            break;
                        case 3: // Desvincular Palestrante
                            if (p.getPalestrante() != null) {
                                repo.desvincularPalestrante(p);
                                JOptionPane.showMessageDialog(this, "Palestrante desvinculado!");
                            } else {
                                JOptionPane.showMessageDialog(this, "Palestra não tem palestrante vinculado.");
                            }
                            break;
                        case 4: // Gerenciar Participantes
                            gerenciarParticipantes(p);
                            break;
                        case 5: // Excluir 
                            if ((p.getParticipantes() != null && !p.getParticipantes().isEmpty()) || p.getPalestrante() != null) {
                                JOptionPane.showMessageDialog(this, "Não é possível excluir, há palestrante ou participantes vinculados!");
                            } else {
                                repo.remover(p);
                                JOptionPane.showMessageDialog(this, "Palestra removida!");
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "ID inválido!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }
    
    private void gerenciarParticipantes(Palestra palestra) {
        
        // Garante que a palestra tem os participantes carregados
        Palestra p = repo.buscarPorIdComParticipantes(palestra.getId()); 
        
        if (p.getParticipantes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum participante inscrito nesta palestra.");
            return;
        }

        // Mostrar lista de participantes
        StringBuilder lista = new StringBuilder("Participantes Inscritos (ID - Nome):\n");
        p.getParticipantes().forEach(part -> 
            lista.append(part.getId()).append(" - ").append(part.getNome()).append("\n")
        );
        
        // Opções de Gerenciamento
        String input = JOptionPane.showInputDialog(this, lista.toString() + "\nDigite o ID do participante para REMOVER a inscrição:");

        if (input == null || input.trim().isEmpty()) {
            return; // Usuário cancelou
        }
        
        try {
            Long idParticipante = Long.parseLong(input.trim());
            
            // Buscar e validar se o ID existe na lista da palestra
            Optional<Participante> participanteOpt = p.getParticipantes().stream()
                .filter(part -> part.getId().equals(idParticipante))
                .findFirst();

            if (participanteOpt.isPresent()) {
                Participante participanteParaRemover = participanteOpt.get();
                
                //Efetuar a remoção usando o repositório de Palestra
                repo.cancelarInscricao(p, participanteParaRemover);
                
                JOptionPane.showMessageDialog(this, "Participante " + participanteParaRemover.getNome() + " removido da palestra!");
            } else {
                JOptionPane.showMessageDialog(this, "Participante com ID " + idParticipante + " não está inscrito nesta palestra.");
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID de Participante inválido!");
        }
    }
}