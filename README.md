
# Simulador de Sistema de Arquivos com Journaling

## **Metodologia**

Este simulador foi desenvolvido em Java, permitindo a manipulação de arquivos e diretórios de forma semelhante a um sistema operacional real. O sistema utiliza um arquivo chamado `sistema.dat` para armazenar o estado do sistema de arquivos, implementando suporte a **Journaling** para garantir a integridade dos dados.

---

## **Parte 1: Introdução ao Sistema de Arquivos com Journaling**

### **O que é um Sistema de Arquivos?**
Um sistema de arquivos é uma estrutura utilizada por sistemas operacionais para armazenar e organizar dados em dispositivos de armazenamento, como discos rígidos ou SSDs.

### **Journaling**
O journaling registra as operações realizadas no sistema de arquivos para evitar inconsistências em caso de falhas. Este sistema implementa o journaling básico, salvando todas as operações no arquivo `sistema.dat`.

---

## **Parte 2: Arquitetura do Simulador**

### **Estrutura de Dados**
- **Diretórios**: Representados pela classe `Diretorio`.
- **Arquivos**: Representados pela classe `Arquivo`.
- **Mapa Global**: O sistema utiliza um mapa (`HashMap`) para armazenar a estrutura completa de diretórios e arquivos.

### **Journaling**
As operações realizadas, como criar, apagar ou copiar arquivos e diretórios, são registradas no arquivo `sistema.dat`. Esse arquivo também contém o estado completo do sistema de arquivos.

---

## **Parte 3: Funcionalidades Implementadas**

### **Operações Básicas**
- **Criar Diretórios**: `criar_diretorio <nome_do_diretorio>`
- **Criar Arquivos**: `criar_arquivo <path_do_arquivo>`
- **Apagar Arquivos**: `apagar_arquivo <path_do_arquivo>`
- **Copiar Arquivos**: `copiar_arquivo <path_do_arquivo> <diretorio_destino>`
- **Listar Diretórios**: `listar_diretorios <path_do_diretorio>`
- **Listar Arquivos**: `listar_arquivos <path_do_diretorio>`

### **Navegação**
- **Entrar em Diretórios**: `cd <nome_do_diretorio>`
- **Sair de Diretórios**: `cd ..`
- **Exibir Diretório Atual**: O prompt exibe o diretório onde você está localizado.

### **Comandos Adicionais**
- **Limpar Tela**: `clear`
- **Help**: `help` exibe todos os comandos disponíveis.

---

## **Parte 4: Uso do Arquivo `sistema.dat`**

### **Função do Arquivo**
- Armazena a estrutura completa de diretórios e arquivos.
- Registra todas as operações realizadas no sistema (journaling).

### **Como Funciona**
- O arquivo é atualizado automaticamente sempre que uma operação é realizada.
- Ao iniciar o programa, o sistema é restaurado a partir do `sistema.dat`.

### **Formato**
O `sistema.dat` utiliza serialização binária, o que o torna não legível diretamente por humanos.

---

## **Parte 5: Como Utilizar o Programa**

### **Compilação**
1. Compile os arquivos Java:
   ```bash
   javac SimuladorSistemaArquivos.java Diretorio.java Arquivo.java Shell.java
   ```

2. Execute o programa:
   ```bash
   java Shell
   ```

### **Exemplos de Comandos**

#### **Criar Diretórios**
```bash
/ > criar_diretorio teste
```

#### **Criar Arquivos**
```bash
/ > criar_arquivo teste.txt
```

#### **Apagar Arquivos**
```bash
/ > apagar_arquivo teste.txt
```

#### **Copiar Arquivos**
```bash
/ > copiar_arquivo teste.txt /novo_diretorio
```

#### **Listar Diretórios**
```bash
/ > listar_diretorios
```

#### **Navegação**
```bash
/ > cd teste
/teste > cd ..
```

---

## **Parte 6: Resultados Esperados**

- **Operações Intuitivas**: Os comandos funcionam de maneira similar a um terminal de sistema operacional.
- **Persistência**: O estado do sistema é salvo em `sistema.dat` e restaurado automaticamente.
- **Journaling**: Todas as operações são registradas, garantindo a integridade do sistema.

---

