criar baseado no banco de dados
CREATE DATABASE SistemaBlog;
USE SistemaBlog;
CREATE TABLE Posts(
    idPosts INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    conteudo VARCHAR(200) NOT NULL,
    dataPublicacao DATE
);
CREATE TABLE Categorias(
    idCategorias INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(200)
);
CREATE TABLE Comentarios(
     idComentarios INT PRIMARY KEY AUTO_INCREMENT,
     autor VARCHAR(200),
     conteudo VARCHAR (200) NOT NULL,
     dataComentario DATE,
     idPosts INT,
     FOREIGN KEY (idPosts) REFERENCES Posts(idPosts)
);
CREATE TABLE PostsCategoria(
     idPosts INT,
     idCategorias INT,
     FOREIGN KEY (idPosts) REFERENCES Posts(idPosts),
     FOREIGN KEY (idCategorias) REFERENCES Categorias (idCategorias)
);

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- INSERTs DE EXEMPLOS
INSERT INTO Categorias (nome)
VALUES
    ("Tecnologia"),
    ("Culinária"),
    ("Esporte"),
    ("Relacionamentos"),
    ("Humor");

INSERT INTO Posts (titulo, conteudo, dataPublicacao)
VALUES ("Receita de bolo de chocolate fresquinho","Este bolo é fácil e delicioso, perfeito para o fim de semana.", 2026-06-01);

INSERT INTO Comentarios (autor, conteudo, dataComentario, idPosts)
VALUES
    ("Ana Silva", "Amei a receita, fiz ontem e ficou incrível!", "2026-06-02", 1),
    ("Carlos Souza", "Muito fácil de fazer, recomendo!", "2026-06-03", 1),
    ("Maria Oliveira", "Ficou um pouco seco, mas o sabor foi ótimo.", "2026-06-04", 1);

INSERT INTO PostsCategoria (idPosts, idCategorias)
VALUES
    (1, 2);

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- JOIN PARA OS EXEMPLOS ACIMA
SELECT
    p.titulo AS Post,
    c.nome AS Categoria,
    COUNT(cm.idComentarios) AS TotalComentarios
FROM Posts p
         JOIN PostsCategoria pc ON p.idPosts = pc.idPosts
         JOIN Categorias c ON pc.idCategorias = c.idCategorias
         LEFT JOIN Comentarios cm ON p.idPosts = cm.idPosts
WHERE c.nome = "Culinária"
GROUP BY p.idPosts, p.titulo, c.nome;