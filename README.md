
# Simulador de Sistema de Arquivos com Journaling

## Francisco Hugo B. H. Fernandes
## Marcelo Feliciano Figueiredo

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
    - **Classe Arquivo**: Representa os arquivos no sistema.
    - **Classe Diretorio**: Representa os diretórios no sistema.
    - **Mapa de Diretórios**: Utilizado para organizar e navegar pelos diretórios e arquivos.

### **Journaling**
O sistema implementa um log que registra todas as operações, como criar, apagar e copiar arquivos/diretórios. Isso é armazenado em um arquivo binário chamado `sistema.dat`.

#### **Operações Registradas no Log**
- Criação de arquivos/diretórios.
- Exclusão de arquivos/diretórios.
- Movimentação ou cópia de arquivos/diretórios.

---

## **Parte 3: Implementação em Java**

### **Classe `FileSystemSimulator`**
Implementa o simulador do sistema de arquivos, contendo métodos para:
- Criar, apagar e copiar arquivos.
- Criar e apagar diretórios.
- Listar o conteúdo de diretórios.

### **Classes `Arquivo` e `Diretorio`**
- **Classe Arquivo**: Representa os arquivos no sistema, com atributos como nome e conteúdo.
- **Classe Diretorio**: Representa os diretórios, incluindo subdiretórios e arquivos contidos.

### **Classe Journal**
Gerencia o log de operações, garantindo que o sistema possa ser restaurado a partir do estado armazenado no `sistema.dat`.

---

## **Resultados Esperados**

Espera-se que o simulador forneça insights sobre o funcionamento de um sistema de arquivos, como:
- Gerenciamento de diretórios e arquivos.
- Garantia de integridade dos dados com o uso de journaling.
- Navegação e manipulação de arquivos de forma prática.

Com base nos resultados obtidos, poderemos avaliar e entender melhor como funciona esse elemento essencial de um sistema operacional.

---

## **Entrega**

A entrega no AVA deve conter:
1. **Relatório em PDF**:
   - Descreva o funcionamento do projeto e o propósito de cada funcionalidade.
   - Explique as bibliotecas utilizadas e como elas contribuem para o sistema.

2. **Link para o Projeto no GitHub**:
   - Certifique-se de que o repositório contenha instruções claras para execução.
