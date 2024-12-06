import java.io.*;
import java.util.*;

public class SimuladorSistemaArquivos {
    private static final String ARQUIVO_DAT = "sistema.dat";

    private Map<String, Diretorio> sistemaArquivos;
    private List<String> journal; // Lista de operações realizadas
    private String diretorioAtual;

    public SimuladorSistemaArquivos() {
        this.sistemaArquivos = new HashMap<>();
        this.journal = new ArrayList<>();
        this.diretorioAtual = "/";
        carregarSistema();
    }

    // Criar Diretório
    public void criarDiretorio(String nomeDiretorio) {
    // Normaliza o caminho do diretório
    String caminhoCompleto = getCaminhoCompleto(nomeDiretorio);

    // Divide o caminho em partes para verificar cada diretório pai
    String[] partes = caminhoCompleto.split("/");
    String caminhoAtual = "/";

    for (int i = 1; i < partes.length - 1; i++) { // Ignora o primeiro vazio e o último diretório
        caminhoAtual = caminhoAtual.equals("/") ? "/" + partes[i] : caminhoAtual + "/" + partes[i];
        if (!sistemaArquivos.containsKey(caminhoAtual)) {
            System.out.println("Erro: O diretório pai não existe para o caminho: " + caminhoAtual);
            return;
        }
    }

    // Verifica se o diretório já existe
    if (sistemaArquivos.containsKey(caminhoCompleto)) {
        System.out.println("O diretório já existe!");
        return;
    }

    // Cria o diretório
    sistemaArquivos.put(caminhoCompleto, new Diretorio(caminhoCompleto));
    registrarOperacao("CRIAR_DIRETORIO " + caminhoCompleto);
    System.out.println("Diretório criado: " + caminhoCompleto);
    salvarSistema();
}

// Método para normalizar o caminho e remover barras duplicadas
private String normalizarCaminho(String caminho) {
    if (caminho == null || caminho.isEmpty()) {
        return "/";
    }
    return caminho.replaceAll("/{2,}", "/");
}

    // Apagar Diretório
    public void apagarDiretorio(String nomeDiretorio) {
    String caminhoCompleto = getCaminhoCompleto(nomeDiretorio);
    if (!sistemaArquivos.containsKey(caminhoCompleto)) {
        System.out.println("O diretório não existe!");
        return;
    }

    // Remover o diretório e todos os seus subdiretórios
    List<String> diretoriosParaRemover = new ArrayList<>();
    for (String dir : sistemaArquivos.keySet()) {
        if (dir.startsWith(caminhoCompleto)) {
            diretoriosParaRemover.add(dir);
        }
    }
    for (String dir : diretoriosParaRemover) {
        sistemaArquivos.remove(dir);
        registrarOperacao("APAGAR_DIRETORIO " + dir);
    }

    System.out.println("Diretório apagado e todos os seus subdiretórios: " + caminhoCompleto);
    salvarSistema();
}


    // Listar Diretórios
    public void listarDiretorios(String nomeDiretorio) {
    String caminhoCompleto = nomeDiretorio != null ? getCaminhoCompleto(nomeDiretorio) : diretorioAtual;

    Diretorio diretorio = sistemaArquivos.get(caminhoCompleto);
    if (diretorio == null) {
        System.out.println("Diretório não encontrado: " + caminhoCompleto);
        return;
    }

    System.out.println("Subdiretórios no diretório " + caminhoCompleto + ":");
    sistemaArquivos.keySet().stream()
        .filter(dir -> isSubdiretorioImediato(caminhoCompleto, dir))
        .map(dir -> dir.substring(caminhoCompleto.length() + (caminhoCompleto.endsWith("/") ? 0 : 1))) // Remove o prefixo do caminho pai, garantindo que o nome esteja correto
        .forEach(System.out::println);
}

// Verifica se um diretório é um subdiretório direto
private boolean isSubdiretorioImediato(String diretorioPai, String candidato) {
    if (!candidato.startsWith(diretorioPai) || candidato.equals(diretorioPai)) {
        return false;
    }

    // Remove o prefixo do caminho pai para analisar o restante
    String resto = candidato.substring(diretorioPai.length());
    if (resto.startsWith("/")) {
        resto = resto.substring(1); // Remove a barra inicial
    }

    // Verifica se o restante contém mais de uma barra, indicando que não é direto
    return !resto.contains("/");
}

    // Listar Arquivos
    public void listarArquivos(String nomeDiretorio) {
        String caminhoCompleto = nomeDiretorio != null ? getCaminhoCompleto(nomeDiretorio) : diretorioAtual;

        Diretorio diretorio = sistemaArquivos.get(caminhoCompleto);
        if (diretorio == null) {
            System.out.println("Diretório não encontrado: " + caminhoCompleto);
            return;
        }

        System.out.println("Arquivos no diretório " + caminhoCompleto + ": " + diretorio.getArquivos());
    }

    // Criar Arquivo
    public void criarArquivo(String caminhoArquivo) {
    // Normaliza o caminho fornecido
    caminhoArquivo = normalizarCaminho(caminhoArquivo);

    // Divide o caminho em diretório e nome do arquivo
    int ultimaBarra = caminhoArquivo.lastIndexOf("/");
    String caminhoDiretorio;
    String nomeArquivo;

    if (ultimaBarra == -1) {
        // Se não há barra, o arquivo será criado no diretório atual
        caminhoDiretorio = diretorioAtual;
        nomeArquivo = caminhoArquivo;
    } else {
        // Divide o caminho em diretório e nome do arquivo
        caminhoDiretorio = caminhoArquivo.substring(0, ultimaBarra);
        nomeArquivo = caminhoArquivo.substring(ultimaBarra + 1);
    }

    if (nomeArquivo.isEmpty()) {
        System.out.println("Erro: Nome de arquivo inválido.");
        return;
    }

    // Verifica se o diretório existe
    String caminhoCompletoDiretorio = caminhoDiretorio.startsWith("/")
        ? normalizarCaminho(caminhoDiretorio) // Caminho absoluto
        : getCaminhoCompleto(caminhoDiretorio); // Caminho relativo

    Diretorio diretorio = sistemaArquivos.get(caminhoCompletoDiretorio);
    if (diretorio == null) {
        System.out.println("Erro: Diretório pai não existe para o caminho: " + caminhoDiretorio);
        return;
    }

    // Verifica se o arquivo já existe
    for (Arquivo arquivo : diretorio.getArquivos()) {
        if (arquivo.getNome().equals(nomeArquivo)) {
            System.out.println("Erro: O arquivo já existe no diretório " + caminhoCompletoDiretorio);
            return;
        }
    }

    // Cria o arquivo no diretório especificado
    diretorio.adicionarArquivo(new Arquivo(nomeArquivo));
    registrarOperacao("CRIAR_ARQUIVO " + caminhoCompletoDiretorio + " " + nomeArquivo);
    System.out.println("Arquivo criado: " + caminhoCompletoDiretorio + "/" + nomeArquivo);
    salvarSistema();
}


    // Apagar Arquivo
    public void apagarArquivo(String caminhoArquivo) {
    // Normaliza o caminho fornecido
    caminhoArquivo = normalizarCaminho(caminhoArquivo);

    // Resolve o caminho completo se for relativo
    if (!caminhoArquivo.startsWith("/")) {
        caminhoArquivo = normalizarCaminho(diretorioAtual + "/" + caminhoArquivo);
    }

    // Divide o caminho em diretório e nome do arquivo
    int ultimaBarra = caminhoArquivo.lastIndexOf("/");
    if (ultimaBarra == -1) {
        System.out.println("Erro: Caminho inválido.");
        return;
    }
    String caminhoDiretorio = (ultimaBarra == 0) ? "/" : caminhoArquivo.substring(0, ultimaBarra);
    String nomeArquivo = caminhoArquivo.substring(ultimaBarra + 1);

    if (nomeArquivo.isEmpty()) {
        System.out.println("Erro: Nome de arquivo inválido.");
        return;
    }

    // Verifica se o diretório existe
    Diretorio diretorio = sistemaArquivos.get(caminhoDiretorio);
    if (diretorio == null) {
        System.out.println("Erro: Diretório não existe para o caminho: " + caminhoDiretorio);
        return;
    }

    // Verifica se o arquivo existe e remove
    boolean removido = diretorio.getArquivos().removeIf(arquivo -> arquivo.getNome().equals(nomeArquivo));
    if (removido) {
        sistemaArquivos.put(caminhoDiretorio, diretorio); // Atualiza o diretório no mapa global
        registrarOperacao("APAGAR_ARQUIVO " + caminhoArquivo);
        System.out.println("Arquivo apagado: " + caminhoArquivo);
        salvarSistema();
    } else {
        System.out.println("Erro: Arquivo não encontrado no diretório " + caminhoDiretorio);
    }
}

    // Copiar Arquivo
    public void copiarArquivo(String caminhoOrigem, String diretorioDestino) {
    // Normaliza os caminhos fornecidos
    caminhoOrigem = normalizarCaminho(caminhoOrigem);
    diretorioDestino = normalizarCaminho(diretorioDestino);

    // Resolve o caminho de origem, adicionando o diretório atual se necessário
    if (!caminhoOrigem.startsWith("/")) {
        caminhoOrigem = normalizarCaminho(diretorioAtual + "/" + caminhoOrigem);
    }

    // Divide o caminho de origem em diretório e nome do arquivo
    int ultimaBarraOrigem = caminhoOrigem.lastIndexOf("/");
    if (ultimaBarraOrigem == -1) {
        System.out.println("Erro: Caminho de origem inválido.");
        return;
    }
    String caminhoDiretorioOrigem = caminhoOrigem.substring(0, ultimaBarraOrigem);
    String nomeArquivoOrigem = caminhoOrigem.substring(ultimaBarraOrigem + 1);

    if (nomeArquivoOrigem.isEmpty()) {
        System.out.println("Erro: Nome de arquivo inválido na origem.");
        return;
    }

    // Resolve o caminho do destino
    String caminhoCompletoDestino = diretorioDestino.startsWith("/")
        ? diretorioDestino
        : normalizarCaminho(diretorioAtual + "/" + diretorioDestino);

    // Verifica se os diretórios de origem e destino existem
    Diretorio origem = sistemaArquivos.get(caminhoDiretorioOrigem);
    Diretorio destino = sistemaArquivos.get(caminhoCompletoDestino);

    if (origem == null) {
        System.out.println("Erro: Diretório de origem não existe para o caminho: " + caminhoDiretorioOrigem);
        return;
    }
    if (destino == null) {
        System.out.println("Erro: Diretório de destino não existe para o caminho: " + caminhoCompletoDestino);
        return;
    }

    // Verifica se o arquivo existe no diretório de origem
    Arquivo arquivoParaCopiar = null;
    for (Arquivo arquivo : origem.getArquivos()) {
        if (arquivo.getNome().equals(nomeArquivoOrigem)) {
            arquivoParaCopiar = arquivo;
            break;
        }
    }

    if (arquivoParaCopiar == null) {
        System.out.println("Erro: Arquivo não encontrado no diretório de origem.");
        return;
    }

    // Adiciona o arquivo ao diretório de destino
    destino.adicionarArquivo(new Arquivo(nomeArquivoOrigem));
    sistemaArquivos.put(caminhoCompletoDestino, destino); // Atualiza o mapa global com o diretório de destino
    registrarOperacao("COPIAR_ARQUIVO " + caminhoOrigem + " " + diretorioDestino);
    System.out.println("Arquivo copiado de " + caminhoOrigem + " para " + caminhoCompletoDestino);

    // Salva o estado do sistema
    salvarSistema();
}


    // Navegar Diretórios
    public void navegarDiretorio(String caminho) {
        if (caminho.equals("..")) {
            if (!diretorioAtual.equals("/")) {
                int lastSlashIndex = diretorioAtual.lastIndexOf("/");
                diretorioAtual = diretorioAtual.substring(0, lastSlashIndex == 0 ? 1 : lastSlashIndex);
            }
        } else {
            String caminhoCompleto = getCaminhoCompleto(caminho);
            if (!sistemaArquivos.containsKey(caminhoCompleto)) {
                System.out.println("Diretório não encontrado: " + caminhoCompleto);
                return;
            }
            diretorioAtual = caminhoCompleto;
        }
    }

    // Exibir Diretório Atual
    public String getDiretorioAtual() {
        return diretorioAtual;
    }

    // Ler Journal
    public void lerJournal() {
        System.out.println("Operações no journal:");
        if (journal.isEmpty()) {
            System.out.println("Nenhuma operação registrada.");
        } else {
            journal.forEach(System.out::println);
        }
    }

    // Registrar Operação no Journal
    private void registrarOperacao(String operacao) {
        journal.add(operacao);
    }

    // Obter o caminho completo, garantindo que seja normalizado
private String getCaminhoCompleto(String nome) {
    String caminho = diretorioAtual.equals("/") ? "/" + nome : diretorioAtual + "/" + nome;
    return normalizarCaminho(caminho);
}


    // Salvar Sistema no Arquivo .dat
    private void salvarSistema() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DAT))) {
            oos.writeObject(sistemaArquivos);
            oos.writeObject(journal);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o sistema: " + e.getMessage());
        }
    }

    // Carregar Sistema a Partir do Arquivo .dat
    @SuppressWarnings("unchecked")
    private void carregarSistema() {
        File datFile = new File(ARQUIVO_DAT);
        if (!datFile.exists()) {
            sistemaArquivos.put("/", new Diretorio("/"));
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(datFile))) {
            sistemaArquivos = (Map<String, Diretorio>) ois.readObject();
            journal = (List<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar o sistema: " + e.getMessage());
        }
    }
}
