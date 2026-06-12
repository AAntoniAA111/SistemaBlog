package DAO;

import Model.Post;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Conexao.ConexaoDB;

public class PostDAO {

    public void cadastrarPost(Post post, int idCategoria) {
        String sqlPost = "INSERT INTO Posts (titulo, conteudo, dataPublicacao) VALUES (?, ?, ?)";
        String sqlPC = "INSERT INTO PostsCategoria (idPosts, idCategorias) VALUES (?, ?)";

        try (Connection conn = ConexaoDB.getConexao()) {
            // Inserir o post
            PreparedStatement stmtPost = conn.prepareStatement(sqlPost, Statement.RETURN_GENERATED_KEYS);
            stmtPost.setString(1, post.getTitulo());
            stmtPost.setString(2, post.getConteudo());
            stmtPost.setDate(3, Date.valueOf(post.getDataPublicacao()));
            stmtPost.executeUpdate();

            // Pegar o ID gerado
            ResultSet rs = stmtPost.getGeneratedKeys();
            if (rs.next()) {
                int idPost = rs.getInt(1);

                // Vincular à categoria
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

    public List<String[]> listarPostsPorCategoria(String nomeCategoria) {
        List<String[]> lista = new ArrayList<>();
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
            stmt.setString(1, nomeCategoria);
            ResultSet rs = stmt.executeQuery();
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