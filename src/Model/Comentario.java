package Model;

import java.time.LocalDate;

public class Comentario {
    private int idComentario;
    private String autor;
    private String conteudo;
    private LocalDate dataComentario;
    private int idPosts;

    public Comentario(String autor, String conteudo, LocalDate dataComentario, int idPost){
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataComentario = dataComentario;
        this.idPosts = idPosts;
    }

    public String getAutor(){return autor;}
    public String getConteudo(){return conteudo;}
    public LocalDate getDataComentario(){return dataComentario;}
    public int getIdPosts(){return idPosts;}
}
