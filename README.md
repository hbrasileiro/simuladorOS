
# Simulador de Sistema de Arquivos com Journaling

## **Metodologia**

O simulador foi desenvolvido em linguagem de programação Java. Ele utiliza chamadas de métodos com os devidos parâmetros para executar operações relacionadas a um sistema de arquivos. Cada método implementa uma funcionalidade equivalente a comandos básicos de sistemas operacionais, como criar, apagar, renomear e listar arquivos e diretórios.

O programa executa cada funcionalidade e exibe o resultado na tela quando necessário, fornecendo uma experiência interativa e simulada de um sistema de arquivos.

---

## **Parte 1: Introdução ao Sistema de Arquivos com Journaling**

### **Descrição do Sistema de Arquivos**
Um **sistema de arquivos** é uma estrutura lógica que organiza e gerencia os dados armazenados em dispositivos de armazenamento, como discos rígidos e SSDs. Ele permite que arquivos sejam criados, organizados em diretórios, renomeados, movidos e excluídos, além de oferecer métodos para proteger a integridade dos dados.

### **Journaling**
O **journaling** é uma técnica usada em sistemas de arquivos modernos para garantir a integridade dos dados. Ele registra todas as operações em um log antes de aplicá-las ao sistema real, prevenindo corrupção de dados em caso de falhas, como quedas de energia.

Existem diferentes tipos de journaling:
- **Write-ahead logging**: Registra operações antes de aplicá-las.
- **Log-structured**: Utiliza uma abordagem de escrita sequencial para otimizar o desempenho em discos.
- **Metadata-only journaling**: Registra apenas alterações na estrutura de metadados, como criação de diretórios.

Neste simulador, implementamos um journaling simplificado para registrar operações como criação, renomeação e exclusão de arquivos e diretórios.

---

## **Parte 2: Arquitetura do Simulador**

### **Estrutura de Dados**
Para representar o sistema de arquivos, utilizamos as seguintes estruturas de dados:
- Um `HashMap` em memória, onde:
  - A **chave** representa o nome de um diretório.
  - O **valor** é uma lista de arquivos contidos no diretório.
- Classes Java para abstrair os conceitos de arquivos e diretórios.

### **Journaling**
O journaling foi implementado utilizando um arquivo de log chamado `journal.log`. Esse arquivo armazena cada operação realizada no sistema, garantindo que as mudanças possam ser reprocessadas ou verificadas em caso de falhas.

As operações registradas no log incluem:
- Criação e exclusão de arquivos e diretórios.
- Renomeação de arquivos e diretórios.
- Cópia de arquivos.

---

## **Parte 3: Implementação em Java**

### **Classe `SimuladorSistemaArquivos`**
Esta classe principal implementa o simulador do sistema de arquivos. Ela gerencia as operações básicas, como:
- Criar, apagar, renomear e listar arquivos e diretórios.
- Copiar arquivos entre diretórios.

Também gerencia o carregamento e a persistência do arquivo de journaling.

### **Classes `File` e `Directory`**
Embora não sejam utilizadas explicitamente neste simulador, esses conceitos são representados através de:
- Diretórios como **chaves** do `HashMap`.
- Arquivos como **valores** na lista associada a cada diretório.

### **Classe `Journal`**
O arquivo de log registra todas as operações realizadas no simulador. As entradas seguem o formato:
```
COMANDO [ARGUMENTOS]
```
Por exemplo:
```
CRIAR_DIRETORIO /diretorio1
CRIAR_ARQUIVO /diretorio1 arquivo1.txt
RENOMEAR_ARQUIVO /diretorio1 arquivo1.txt arquivo2.txt
```

---

## **Resultados Esperados**

Espera-se que o simulador forneça insights sobre o funcionamento interno de um sistema de arquivos, incluindo:
- Como arquivos e diretórios são gerenciados.
- Como o journaling garante a integridade dos dados em cenários de falhas.

Este simulador também serve como uma introdução prática aos conceitos de sistemas operacionais, permitindo experimentar operações básicas de um sistema de arquivos e entender os princípios do journaling.
