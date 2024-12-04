import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {
        SimuladorSistemaArquivos fs = new SimuladorSistemaArquivos();
        Scanner scanner = new Scanner(System.in);
        String comando;

        System.out.println("Bem-vindo ao Shell do Sistema de Arquivos. Digite 'sair' para encerrar.");

        while (true) {
            System.out.print("> ");
            comando = scanner.nextLine();

            if (comando.equalsIgnoreCase("sair")) break;

            String[] partes = comando.split(" ");
            switch (partes[0].toLowerCase()) {
                case "criar_diretorio":
                    fs.criarDiretorio(partes[1]);
                    break;
                case "apagar_diretorio":
                    fs.apagarDiretorio(partes[1]);
                    break;
                case "listar_arquivos":
                    fs.listarArquivos(partes[1]);
                    break;
                case "criar_arquivo":
                    fs.criarArquivo(partes[1], partes[2]);
                    break;
                case "apagar_arquivo":
                    fs.apagarArquivo(partes[1], partes[2]);
                    break;
                case "copiar_arquivo":
                    fs.copiarArquivo(partes[1], partes[2], partes[3]);
                    break;
                case "renomear_arquivo":
                    fs.renomearArquivo(partes[1], partes[2], partes[3]);
                    break;
                case "renomear_diretorio":
                    fs.renomearDiretorio(partes[1], partes[2]);
                    break;
                case "listar_diretorios":
                    fs.listarDiretorios();
                    break;
                default:
                    System.out.println("Comando inválido!");
            }
        }
        scanner.close();
    }
}
