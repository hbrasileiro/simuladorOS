import java.io.*;
import java.util.*;

public class SimuladorSistemaArquivos {
    private static final String ARQUIVO_JOURNAL = "journal.log";

    private Map<String, List<String>> sistemaArquivos;

    public SimuladorSistemaArquivos() {
        this.sistemaArquivos = new HashMap<>();
        carregarJournal();
    }

    // Criar Diretório
    public void criarDiretorio(String nomeDiretorio) {
        if (sistemaArquivos.containsKey(nomeDiretorio)) {
            System.out.println("O diretório já existe!");
            return;
        }
        sistemaArquivos.put(nomeDiretorio, new ArrayList<>());
        registrarOperacao("CRIAR_DIRETORIO " + nomeDiretorio);
        System.out.println("Diretório criado: " + nomeDiretorio);
    }

    // Apagar Diretório
    public void apagarDiretorio(String nomeDiretorio) {
        if (!sistemaArquivos.containsKey(nomeDiretorio)) {
            System.out.println("O diretório não existe!");
            return;
        }
        sistemaArquivos.remove(nomeDiretorio);
        registrarOperacao("APAGAR_DIRETORIO " + nomeDiretorio);
        System.out.println("Diretório apagado: " + nomeDiretorio);
    }

    // Listar Arquivos em um Diretório
    public void listarArquivos(String nomeDiretorio) {
        if (!sistemaArquivos.containsKey(nomeDiretorio)) {
            System.out.println("O diretório não existe!");
            return;
        }
        System.out.println("Arquivos no diretório " + nomeDiretorio + ": " + sistemaArquivos.get(nomeDiretorio));
    }

    // Listar Diretórios
    public void listarDiretorios() {
        if (sistemaArquivos.isEmpty()) {
            System.out.println("Não há diretórios no sistema.");
            return;
        }
        System.out.println("Diretórios no sistema: " + sistemaArquivos.keySet());
    }

    // Criar Arquivo
    public void criarArquivo(String nomeDiretorio, String nomeArquivo) {
        if (!sistemaArquivos.containsKey(nomeDiretorio)) {
            System.out.println("O diretório não existe!");
            return;
        }
        sistemaArquivos.get(nomeDiretorio).add(nomeArquivo);
        registrarOperacao("CRIAR_ARQUIVO " + nomeDiretorio + " " + nomeArquivo);
        System.out.println("Arquivo criado: " + nomeArquivo);
    }

    // Apagar Arquivo
    public void apagarArquivo(String nomeDiretorio, String nomeArquivo) {
        if (!sistemaArquivos.containsKey(nomeDiretorio) || !sistemaArquivos.get(nomeDiretorio).contains(nomeArquivo)) {
            System.out.println("O arquivo não existe!");
            return;
        }
        sistemaArquivos.get(nomeDiretorio).remove(nomeArquivo);
        registrarOperacao("APAGAR_ARQUIVO " + nomeDiretorio + " " + nomeArquivo);
        System.out.println("Arquivo apagado: " + nomeArquivo);
    }

    // Copiar Arquivo
    public void copiarArquivo(String dirOrigem, String nomeArquivo, String dirDestino) {
        if (!sistemaArquivos.containsKey(dirOrigem) || !sistemaArquivos.get(dirOrigem).contains(nomeArquivo) || !sistemaArquivos.containsKey(dirDestino)) {
            System.out.println("Diretório de origem ou destino inválido!");
            return;
        }
        sistemaArquivos.get(dirDestino).add(nomeArquivo);
        registrarOperacao("COPIAR_ARQUIVO " + dirOrigem + " " + nomeArquivo + " " + dirDestino);
        System.out.println("Arquivo copiado para " + dirDestino);
    }

    // Renomear Arquivo
    public void renomearArquivo(String nomeDiretorio, String nomeAntigo, String nomeNovo) {
        if (!sistemaArquivos.containsKey(nomeDiretorio) || !sistemaArquivos.get(nomeDiretorio).contains(nomeAntigo)) {
            System.out.println("O arquivo não existe!");
            return;
        }
        sistemaArquivos.get(nomeDiretorio).remove(nomeAntigo);
        sistemaArquivos.get(nomeDiretorio).add(nomeNovo);
        registrarOperacao("RENOMEAR_ARQUIVO " + nomeDiretorio + " " + nomeAntigo + " " + nomeNovo);
        System.out.println("Arquivo renomeado para " + nomeNovo);
    }

    // Renomear Diretório
    public void renomearDiretorio(String nomeAntigo, String nomeNovo) {
        if (!sistemaArquivos.containsKey(nomeAntigo)) {
            System.out.println("O diretório não existe!");
            return;
        }
        if (sistemaArquivos.containsKey(nomeNovo)) {
            System.out.println("O novo nome já está em uso!");
            return;
        }
        List<String> conteudo = sistemaArquivos.remove(nomeAntigo);
        sistemaArquivos.put(nomeNovo, conteudo);
        registrarOperacao("RENOMEAR_DIRETORIO " + nomeAntigo + " " + nomeNovo);
        System.out.println("Diretório renomeado para: " + nomeNovo);
    }

    // Registrar Operação no Journal
    private void registrarOperacao(String operacao) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_JOURNAL, true))) {
            writer.write(operacao);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao registrar operação: " + e.getMessage());
        }
    }

    // Carregar Journal
    private void carregarJournal() {
        File journal = new File(ARQUIVO_JOURNAL);
        if (!journal.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(journal))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                processarEntradaJournal(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar journal: " + e.getMessage());
        }
    }

    private void processarEntradaJournal(String entrada) {
        String[] partes = entrada.split(" ");
        String comando = partes[0];

        switch (comando) {
            case "CRIAR_DIRETORIO":
                sistemaArquivos.put(partes[1], new ArrayList<>());
                break;
            case "APAGAR_DIRETORIO":
                sistemaArquivos.remove(partes[1]);
                break;
            case "CRIAR_ARQUIVO":
                sistemaArquivos.get(partes[1]).add(partes[2]);
                break;
            case "APAGAR_ARQUIVO":
                sistemaArquivos.get(partes[1]).remove(partes[2]);
                break;
            case "COPIAR_ARQUIVO":
                sistemaArquivos.get(partes[3]).add(partes[2]);
                break;
            case "RENOMEAR_ARQUIVO":
                sistemaArquivos.get(partes[1]).remove(partes[2]);
                sistemaArquivos.get(partes[1]).add(partes[3]);
                break;
            case "RENOMEAR_DIRETORIO":
                List<String> conteudo = sistemaArquivos.remove(partes[1]);
                sistemaArquivos.put(partes[2], conteudo);
                break;
        }
    }
}
