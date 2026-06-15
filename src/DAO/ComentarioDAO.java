package  DAO;

import Model.Comentario;
import Conexao.ConexaoDB;

import java.sql.*;

public class ComentarioDAO {

    public void cadastrarComentario(Comentario comentario) {
        String sql = "INSERT INTO Comentarios (autor, conteudo, dataComentario, idPosts) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, comentario.getAutor());
            stmt.setString(2, comentario.getConteudo());
            stmt.setDate(3, Date.valueOf(comentario.getDataComentario()));
            stmt.setInt(4, comentario.getIdPosts());
            stmt.executeUpdate();
            System.out.println("Comentário cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar comentário: " + e.getMessage());
        }
    }
}