package Model;
import java.time.LocalDate;

public class Post {
    private int idPosts;
    private String titulo;
    private String conteudo;
    private LocalDate dataPublicacao;

    public Post(String titulo, String conteudo, LocalDate dataPublicacao){
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
    }

    public Post(int idPosts, String titulo, String conteudo, LocalDate dataPublicacao){
        this.idPosts = idPosts;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
    }

    public int getIdPosts(){return idPosts;}
    public String getTitulo(){return titulo;}
    public String getConteudo(){return conteudo;}
    public LocalDate getDataPublicacao(){return dataPublicacao;}


}
