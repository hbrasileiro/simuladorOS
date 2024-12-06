
# Simulador de Sistema de Arquivos com Journaling

### Francisco Hugo B. H. Fernandes
### Marcelo Feliciano Figueiredo

## **Metodologia**

O simulador será desenvolvido em linguagem de programação Java. Ele receberá as chamadas de métodos com os devidos parâmetros. Em seguida, serão implementados os métodos correspondentes aos comandos de um SO.

O programa executará cada funcionalidade e exibirá o resultado na tela quando necessário.

---

## **Parte 1: Introdução ao Sistema de Arquivos com Journaling**

### **Descrição do Sistema de Arquivos**
Um sistema de arquivos é uma estrutura utilizada para armazenar e organizar dados em dispositivos de armazenamento, como discos rígidos e SSDs. Ele permite que os dados sejam recuperados, manipulados e gerenciados de forma eficiente.

O **Journaling** é uma técnica usada para registrar operações realizadas no sistema de arquivos. Isso ajuda a prevenir inconsistências causadas por falhas de energia ou erros no sistema.

### **Journaling**
O journaling registra as operações realizadas antes que sejam aplicadas ao sistema. Isso garante que, em caso de falha, as mudanças possam ser revertidas ou aplicadas corretamente.

#### **Tipos de Journaling**:
1. **Write-Ahead Logging**: Registra as alterações antes de aplicá-las ao sistema.
2. **Log-Structured**: Mantém logs como parte integrante da estrutura do sistema de arquivos.

---

## **Parte 2: Arquitetura do Simulador**

### **Estrutura de Dados**
- **Classes Java**: Foram utilizadas para representar os elementos do sistema de arquivos.
    - **Classe Arquivo**: Representa os arquivos no sistema, com atributos como nome e conteúdo.
    - **Classe Diretorio**: Representa os diretórios no sistema, incluindo subdiretórios e arquivos contidos.
    - **Classe SimuladorSistemaArquivos**: Gerencia todas as operações do sistema de arquivos, como criação, exclusão, e navegação.
    - **Classe Shell**: Simula o ambiente de terminal, processando os comandos do usuário.

### **Journaling**
O sistema implementa um log que registra todas as operações, como criar, apagar e copiar arquivos/diretórios. Isso é armazenado em um arquivo binário chamado `sistema.dat`.

#### **Operações Registradas no Log**
- Criação de arquivos/diretórios.
- Exclusão de arquivos/diretórios.
- Movimentação ou cópia de arquivos/diretórios.

---

## **Parte 3: Implementação em Java**

### **Classe `SimuladorSistemaArquivos`**
Implementa o simulador do sistema de arquivos, contendo métodos para:
- Criar, apagar e copiar arquivos.
- Criar e apagar diretórios.
- Listar o conteúdo de diretórios.

### **Classes `Arquivo` e `Diretorio`**
- **Classe Arquivo**: Representa os arquivos no sistema, com atributos como nome e conteúdo.
- **Classe Diretorio**: Representa os diretórios, incluindo subdiretórios e arquivos contidos.

### **Classe `Shell`**
Simula um terminal interativo onde o usuário pode executar comandos como `criar_diretorio`, `criar_arquivo`, `cd`, e outros.

---

## **Parte 4: Como Executar o Código**

### **1. Compilação**
1. Certifique-se de que todos os arquivos Java (`SimuladorSistemaArquivos.java`, `Diretorio.java`, `Arquivo.java`, e `Shell.java`) estão na mesma pasta.
2. Compile os arquivos com o seguinte comando:
   ```bash
   javac SimuladorSistemaArquivos.java Diretorio.java Arquivo.java Shell.java
   ```

### **2. Execução**
Execute o programa com o seguinte comando:
```bash
java Shell
```

---

## **Parte 5: Exemplos de Comandos**

### **Criar Diretórios**
```bash
/ > criar_diretorio teste
```
Cria um diretório chamado `teste`.

### **Criar Arquivos**
```bash
/ > criar_arquivo teste.txt
```
Cria um arquivo chamado `teste.txt` no diretório atual.

### **Apagar Arquivos**
```bash
/ > apagar_arquivo teste.txt
```
Apaga o arquivo `teste.txt` do diretório atual.

### **Copiar Arquivos**
```bash
/ > copiar_arquivo teste.txt /outro_diretorio
```
Copia o arquivo `teste.txt` para o diretório `/outro_diretorio`.

### **Listar Diretórios**
```bash
/ > listar_diretorios
```
Lista todos os subdiretórios do diretório atual.

### **Navegar Entre Diretórios**
```bash
/ > cd teste
/teste > cd ..
```
Permite entrar no diretório `teste` e retornar ao diretório anterior.

### **Limpar Tela**
```bash
/ > clear
```
Limpa a tela do terminal.

---

## **Resultados Esperados**

Espera-se que o simulador forneça insights sobre o funcionamento de um sistema de arquivos, como:
- Gerenciamento de diretórios e arquivos.
- Garantia de integridade dos dados com o uso de journaling.
- Navegação e manipulação de arquivos de forma prática.

Com base nos resultados obtidos, poderemos avaliar e entender melhor como funciona esse elemento essencial de um sistema operacional.

---
