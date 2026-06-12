# SistemaBlog

Sistema de gerenciamento de blog desenvolvido em Java com banco de dados MySQL. Permite cadastrar posts, comentários e listar posts por categoria via menu no terminal.

---

## Tecnologias

- Java 17+
- MySQL 8+
- MySQL Connector/J (JDBC)
- IntelliJ IDEA

---

## Estrutura do Projeto

```
SistemaBlog/
├── src/
│   ├── Main.java
│   ├── Conexao/
│   │   └── ConexaoDB.java
│   ├── Model/
│   │   ├── Post.java
│   │   ├── Categoria.java
│   │   └── Comentario.java
│   └── DAO/
│       ├── PostDAO.java
│       ├── CategoriaDAO.java
│       └── ComentarioDAO.java
```

---

## Banco de Dados

### Criação do Schema

```sql
CREATE DATABASE SistemaBlog;
USE SistemaBlog;

CREATE TABLE Posts (
    idPosts INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    conteudo VARCHAR(200) NOT NULL,
    dataPublicacao DATE
);

CREATE TABLE Categorias (
    idCategorias INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(200)
);

CREATE TABLE Comentarios (
    idComentarios INT PRIMARY KEY AUTO_INCREMENT,
    autor VARCHAR(200),
    conteudo VARCHAR(200) NOT NULL,
    dataComentario DATE,
    idPosts INT,
    FOREIGN KEY (idPosts) REFERENCES Posts(idPosts)
);

CREATE TABLE PostsCategoria (
    idPosts INT,
    idCategorias INT,
    FOREIGN KEY (idPosts) REFERENCES Posts(idPosts),
    FOREIGN KEY (idCategorias) REFERENCES Categorias(idCategorias)
);
```

### Dados de Exemplo

```sql
INSERT INTO Categorias (nome)
VALUES ('Tecnologia'), ('Culinária'), ('Esporte'), ('Relacionamentos'), ('Humor');

INSERT INTO Posts (titulo, conteudo, dataPublicacao)
VALUES ('Receita de bolo de chocolate fresquinho',
        'Este bolo é fácil e delicioso, perfeito para o fim de semana.',
        '2026-06-01');

INSERT INTO Comentarios (autor, conteudo, dataComentario, idPosts)
VALUES ('Ana Silva', 'Amei a receita, fiz ontem e ficou incrível!', '2026-06-02', 1),
       ('Carlos Souza', 'Muito fácil de fazer, recomendo!', '2026-06-03', 1),
       ('Maria Oliveira', 'Ficou um pouco seco, mas o sabor foi ótimo.', '2026-06-04', 1);

INSERT INTO PostsCategoria (idPosts, idCategorias)
VALUES (1, 2);
```

---

## Configuração

1. Clone ou baixe o projeto no IntelliJ IDEA
2. Adicione o **MySQL Connector/J** como dependência (arquivo `.jar` nas configurações do projeto)
3. Em `ConexaoDB.java`, configure sua senha do MySQL:

```java
private static final String PASSWORD = "sua_senha";
```

---

## Funcionalidades

| Opção | Descrição |
|-------|-----------|
| 1 | Cadastrar novo post vinculado a uma categoria |
| 2 | Cadastrar comentário em um post existente |
| 3 | Listar posts de uma categoria com total de comentários |
| 0 | Sair do sistema |

---

## Consulta Principal

Lista todos os posts de uma categoria com o número de comentários de cada um:

```sql
SELECT p.titulo, c.nome, COUNT(cm.idComentarios) AS totalComentarios
FROM Posts p
JOIN PostsCategoria pc ON p.idPosts = pc.idPosts
JOIN Categorias c ON pc.idCategorias = c.idCategorias
LEFT JOIN Comentarios cm ON p.idPosts = cm.idPosts
WHERE c.nome = 'Culinária'
GROUP BY p.idPosts, p.titulo, c.nome;
```

---

## Autora

Desenvolvido por Antonia — Curso de Programação de Aplicativos · Senai CTTI
