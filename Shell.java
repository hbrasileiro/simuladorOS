import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {
        SimuladorSistemaArquivos sistema = new SimuladorSistemaArquivos();
        Scanner scanner = new Scanner(System.in);
        String comando;

        System.out.println("Bem-vindo ao Sistema de Arquivos com Journaling! Digite 'help' para ver os comandos disponíveis.");

        while (true) {
            System.out.print(sistema.getDiretorioAtual() + " > ");
            comando = scanner.nextLine();
            String[] partes = comando.split(" ");
            String cmd = partes[0];

            switch (cmd) {
                case "criar_diretorio":
                    sistema.criarDiretorio(partes[1]);
                    break;
                case "apagar_diretorio":
                    sistema.apagarDiretorio(partes[1]);
                    break;
                case "listar_diretorios":
                    sistema.listarDiretorios(partes.length > 1 ? partes[1] : null);
                    break;
                case "listar_arquivos":
                    sistema.listarArquivos(partes.length > 1 ? partes[1] : null);
                    break;
                case "criar_arquivo":
                    sistema.criarArquivo(partes[1]);
                    break;
                case "apagar_arquivo":
                    sistema.apagarArquivo(partes[1]);
                    break;
                case "copiar_arquivo":
                    if (partes.length < 3) {
                        System.out.println("Uso: copiar_arquivo <nomeArquivo> <diretorioDestino>");
                    } else {
                        sistema.copiarArquivo(partes[1], partes[2]);
                    }
                    break;
                case "cd":
                    sistema.navegarDiretorio(partes[1]);
                    break;
                case "ler_journal":
                    sistema.lerJournal();
                    break;
                case "clear":
                    clearConsole();
                    break;
                case "help":
                    System.out.println("""
                        Comandos disponíveis:
                        criar_diretorio <nome> - Cria um diretório.
                        apagar_diretorio <nome> - Apaga um diretório.
                        listar_diretorios [nomeDiretorio] - Lista subdiretórios.
                        listar_arquivos [nomeDiretorio] - Lista arquivos.
                        criar_arquivo <nome> - Cria um arquivo no diretório atual.
                        apagar_arquivo <nome> - Apaga um arquivo do diretório atual.
                        copiar_arquivo <nomeArquivo> <diretorioDestino> - Copia um arquivo para outro diretório.
                        cd <nome> - Navega para um diretório.
                        cd .. - Retorna ao diretório pai.
                        ler_journal - Lê o conteúdo do journal.
                        clear - Limpa o console.
                        sair - Encerra o sistema.
                    """);
                    break;
                case "sair":
                    System.out.println("Encerrando o sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Comando não reconhecido. Digite 'help' para ver os comandos disponíveis.");
            }
        }
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Erro ao limpar o console.");
        }
    }
}
