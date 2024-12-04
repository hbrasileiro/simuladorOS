import java.io.*;
import java.util.*;

public class SimuladorSistemaArquivos {
    private static final String ARQUIVO_JOURNAL = "journal.log";

    private Map<String, Diretorio> sistemaArquivos;

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
        sistemaArquivos.put(nomeDiretorio, new Diretorio(nomeDiretorio));
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
        Diretorio diretorio = sistemaArquivos.get(nomeDiretorio);
        if (diretorio == null) {
            System.out.println("O diretório não existe!");
            return;
        }
        diretorio.adicionarArquivo(new Arquivo(nomeArquivo));
        registrarOperacao("CRIAR_ARQUIVO " + nomeDiretorio + " " + nomeArquivo);
        System.out.println("Arquivo criado: " + nomeArquivo);
    }

    // Apagar Arquivo
    public void apagarArquivo(String nomeDiretorio, String nomeArquivo) {
        Diretorio diretorio = sistemaArquivos.get(nomeDiretorio);
        if (diretorio == null) {
            System.out.println("O diretório não existe!");
            return;
        }
        diretorio.removerArquivo(nomeArquivo);
        registrarOperacao("APAGAR_ARQUIVO " + nomeDiretorio + " " + nomeArquivo);
        System.out.println("Arquivo apagado: " + nomeArquivo);
    }

    // Listar Arquivos
    public void listarArquivos(String nomeDiretorio) {
        Diretorio diretorio = sistemaArquivos.get(nomeDiretorio);
        if (diretorio == null) {
            System.out.println("O diretório não existe!");
            return;
        }
        System.out.println("Arquivos no diretório " + nomeDiretorio + ": " + diretorio.getArquivos());
    }

    // Copiar Arquivo
    public void copiarArquivo(String dirOrigem, String nomeArquivo, String dirDestino) {
        Diretorio origem = sistemaArquivos.get(dirOrigem);
        Diretorio destino = sistemaArquivos.get(dirDestino);

        if (origem == null || destino == null) {
            System.out.println("Diretório de origem ou destino inválido!");
            return;
        }

        for (Arquivo arquivo : origem.getArquivos()) {
            if (arquivo.getNome().equals(nomeArquivo)) {
                destino.adicionarArquivo(new Arquivo(nomeArquivo));
                registrarOperacao("COPIAR_ARQUIVO " + dirOrigem + " " + nomeArquivo + " " + dirDestino);
                System.out.println("Arquivo copiado para " + dirDestino);
                return;
            }
        }
        System.out.println("O arquivo não existe no diretório de origem!");
    }

    // Renomear Arquivo
    public void renomearArquivo(String nomeDiretorio, String nomeAntigo, String nomeNovo) {
        Diretorio diretorio = sistemaArquivos.get(nomeDiretorio);
        if (diretorio == null) {
            System.out.println("O diretório não existe!");
            return;
        }
        for (Arquivo arquivo : diretorio.getArquivos()) {
            if (arquivo.getNome().equals(nomeAntigo)) {
                arquivo.setNome(nomeNovo);
                registrarOperacao("RENOMEAR_ARQUIVO " + nomeDiretorio + " " + nomeAntigo + " " + nomeNovo);
                System.out.println("Arquivo renomeado para: " + nomeNovo);
                return;
            }
        }
        System.out.println("O arquivo não existe!");
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
        Diretorio diretorio = sistemaArquivos.remove(nomeAntigo);
        diretorio.setNome(nomeNovo);
        sistemaArquivos.put(nomeNovo, diretorio);
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
        java.io.File journal = new java.io.File(ARQUIVO_JOURNAL);
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
                sistemaArquivos.put(partes[1], new Diretorio(partes[1]));
                break;
            case "APAGAR_DIRETORIO":
                sistemaArquivos.remove(partes[1]);
                break;
            case "CRIAR_ARQUIVO":
                sistemaArquivos.get(partes[1]).adicionarArquivo(new Arquivo(partes[2]));
                break;
            case "APAGAR_ARQUIVO":
                sistemaArquivos.get(partes[1]).removerArquivo(partes[2]);
                break;
            case "COPIAR_ARQUIVO":
                sistemaArquivos.get(partes[3]).adicionarArquivo(new Arquivo(partes[2]));
                break;
            case "RENOMEAR_ARQUIVO":
                for (Arquivo arquivo : sistemaArquivos.get(partes[1]).getArquivos()) {
                    if (arquivo.getNome().equals(partes[2])) {
                        arquivo.setNome(partes[3]);
                        break;
                    }
                }
                break;
            case "RENOMEAR_DIRETORIO":
                Diretorio diretorio = sistemaArquivos.remove(partes[1]);
                diretorio.setNome(partes[2]);
                sistemaArquivos.put(partes[2], diretorio);
                break;
        }
    }
}
