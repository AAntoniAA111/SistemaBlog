package DAO;

import Model.Post;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Conexao.ConexaoDB;

public class PostDAO {

    //nota: ainda tenho dificuldade para entender algumas funcinalidades e como encaixar elas em códigos


    //VISÃO GERAL: ESSA CLASSE É RESPONSÁVEL POS FAZER INSERTs (post + vínculo com categoria)
    // E CONSULTAS COM 4 TABELAS (para trazer título, categoria e contagem de comentários.)
    //RECEBE UM Post E O idCategoria QUE O USUÁRIO ESCOLHE
    public void cadastrarPost(Post post, int idCategoria) {
        String sqlPost = "INSERT INTO Posts (titulo, conteudo, dataPublicacao) VALUES (?, ?, ?)";
        String sqlPC = "INSERT INTO PostsCategoria (idPosts, idCategorias) VALUES (?, ?)";

        try (Connection conn = ConexaoDB.getConexao()) {
            //SUBSTITUI OS ? PELOS VALORES DO OBJETO E EXECUTA O INSERT
            PreparedStatement stmtPost = conn.prepareStatement(sqlPost, Statement.RETURN_GENERATED_KEYS);
            stmtPost.setString(1, post.getTitulo());
            stmtPost.setString(2, post.getConteudo());
            stmtPost.setDate(3, Date.valueOf(post.getDataPublicacao()));
            stmtPost.executeUpdate();

            //PEGA O ID GERADO PARA O PROXIMO INSERT
            ResultSet rs = stmtPost.getGeneratedKeys();
            if (rs.next()) {
                int idPost = rs.getInt(1);

                //VINCULA O idPost À CATEGORIA NA TABELA PostsCategoria
                PreparedStatement stmtPC = conn.prepareStatement(sqlPC);
                stmtPC.setInt(1, idPost);
                stmtPC.setInt(2, idCategoria);
                stmtPC.executeUpdate();
            }

            System.out.println("Post cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar post: " + e.getMessage());
        }
    }


    //RECEBE O NOME DA CATEGORIA E RETORNA UMA LISTA
    //CADA LISTA POSSUE 3 VALORES: TÍTULO, CATEGORIA E TOTAL DE COMENTARIOS
    public List<String[]> listarPostsPorCategoria(String nomeCategoria) {
        List<String[]> lista = new ArrayList<>();
        //Posts → PostsCategoria → Categorias PARA CHEGAR NO NOME DA CATEGORIA
        //LEFT JOIN Comentarios PARA CONTAR COMENTARIOS --> O LEFT GARANTE QUE TODO COMENTARIO VAZIO APAREÇAM COM 0
        //COUNT + GROUP BY FAZEM A CONTAGEM POR POST
        String sql = """
                SELECT p.titulo, c.nome, COUNT(cm.idComentarios) AS totalComentarios
                FROM Posts p
                JOIN PostsCategoria pc ON p.idPosts = pc.idPosts
                JOIN Categorias c ON pc.idCategorias = c.idCategorias
                LEFT JOIN Comentarios cm ON p.idPosts = cm.idPosts
                WHERE c.nome = ?
                GROUP BY p.idPosts, p.titulo, c.nome
                """;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            //SUBSTITUI OS ? PELOS VALORES DO OBJETO E EXECUTA A CONSULTA
            stmt.setString(1, nomeCategoria);
            ResultSet rs = stmt.executeQuery();

            //AQUI VAI SER CRIADO UM ARRAY COM OS 3 VALORES E ADICIONADOS A LISTA PARA CADA LINHA RETORNADA
            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("titulo"),
                        rs.getString("nome"),
                        rs.getString("totalComentarios")
                });
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar posts: " + e.getMessage());
        }
        return lista;
    }
}