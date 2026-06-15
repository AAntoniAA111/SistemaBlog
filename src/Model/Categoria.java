package Model;

public class Categoria {
    private int idCategoria;
    private String nome;

    public Categoria(int idCategoria, String nome){
        this.idCategoria = idCategoria;
        this.nome = nome;
    }

    //AOENAS GETs POIS NÃO É PRECISO ALTERAR NOME DE CATEGORIAS
    public int getIdCategoria(){return idCategoria;}
    public String getNome(){return nome;}

}
