# 🏋️‍♂️ Sistema de Gerenciamento de Academia

## 📋 Descrição Geral
Este projeto é um **sistema de controle e gerenciamento de academia**, desenvolvido em **Java** com **conexão JDBC ao banco de dados MySQL**.  
O sistema permite o cadastro e controle de **alunos, instrutores, planos de treino e frequência**, além de módulos administrativos como **financeiro, modalidades e operacional**.

---

## ⚙️ Tecnologias Utilizadas
- **Java (JDK 17+)**
- **JDBC (Java Database Connectivity)**
- **MySQL**
- **Maven** (opcional para gerenciamento de dependências)
- **Padrão de arquitetura MVC**

---

## 🚀 Funcionalidades Implementadas
✔ CRUD completo (Create, Read, Update, Delete) para:
  - Aluno
  - Instrutor
  - Plano de Treino
  - Frequência
    
✔ Tratamento de exceções com SQLException
✔ Conexão persistente via ConnectionFactory
✔ Integridade referencial entre tabelas no MySQL
✔ Arquitetura limpa baseada em camadas
✔ Código padronizado, organizado e modular

---


## 🧠 Boas Práticas Adotadas

- DAO Pattern para acesso a dados
- Camada Service contendo regras de negócio e validações
- Fechamento seguro de recursos com try-with-resources
- Prepared Statements para proteção contra SQL Injection


---

## 🗃️ Modelagem do Banco de Dados (MySQL)

### Banco: `academia`

```sql
CREATE DATABASE academia;
USE academia;

CREATE TABLE instrutor (
    id_instrutor INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(100)
);

-- Tabela de alunos
CREATE TABLE aluno (
    id_aluno INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_ingresso DATE
);

-- Tabela de planos de treino
CREATE TABLE plano_treino (
    id_plano INT AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_instrutor INT NOT NULL,
    descricao VARCHAR(255),
    duracao_semanas INT,
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno),
    FOREIGN KEY (id_instrutor) REFERENCES instrutor(id_instrutor)
);

-- Tabela de frequência dos alunos
CREATE TABLE frequencia (
    id_frequencia INT AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT NOT NULL,
    data DATE NOT NULL,
    presenca BOOLEAN NOT NULL,
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno)
);
```
---

## 🏗️ Como Executar o Projeto

### 1.Clone o repositório:

git clone (https://github.com/anakarolinasf/SistemaDeGestaoDeAcademia.git)
cd sistema-academia


### 2. Configure o banco de dados MySQL
- Crie o banco academia e execute o script SQL acima.

### 3. Atualize as credenciais de conexão
- Edite o arquivo ConnectionFactory.java com seu usuário e senha do MySQL.

### 4. Compile e execute
- javac -d bin src/**/*.java
- java -cp bin Main

---

## 👨‍💻 Autoras:
Desenvolvido por: Ana Karolina e Lídia Araújo



