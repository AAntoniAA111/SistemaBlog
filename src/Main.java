import DAO.CategoriaDAO;
import DAO.ComentarioDAO;
import DAO.PostDAO;
import Model.Categoria;
import Model.Comentario;
import Model.Post;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static PostDAO postDAO = new PostDAO();
    static ComentarioDAO comentarioDAO = new ComentarioDAO();
    static CategoriaDAO categoriaDAO = new CategoriaDAO();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n===== SISTEMA BLOG =====");
            System.out.println("1. Cadastrar Post");
            System.out.println("2. Cadastrar Comentário");
            System.out.println("3. Listar Posts por Categoria");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarPost();
                case 2 -> cadastrarComentario();
                case 3 -> listarPostsPorCategoria();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static void cadastrarPost() {
        System.out.print("Título: ");
        String titulo = sc.nextLine();

        System.out.print("Conteúdo: ");
        String conteudo = sc.nextLine();

        //LISTAR AS CATEGORIAS DEFINIDAS
        List<Categoria> categorias = categoriaDAO.listarCategorias();
        System.out.println("Categorias disponíveis:");
        for (Categoria c : categorias) {
            System.out.println(c.getIdCategoria() + " - " + c.getNome());
        }
        System.out.print("ID da Categoria: ");
        int idCategoria = sc.nextInt();
        sc.nextLine();

        Post post = new Post(titulo, conteudo, LocalDate.now());
        postDAO.cadastrarPost(post, idCategoria);
    }

    static void cadastrarComentario() {

        List<String[]> posts = postDAO.listarTodosPosts();
        if(posts.isEmpty()){
            System.out.println("Nenhum post cadastrado ainda!");
            return;
        }
        System.out.println("Posts diponíveis: ");
        for(String[] p : posts){
            System.out.println(p[0] + " - "  + p[1]);
        }
        System.out.print("Autor: ");
        String autor = sc.nextLine();

        System.out.print("Conteúdo: ");
        String conteudo = sc.nextLine();

        System.out.print("ID do Post: ");
        int idPost = sc.nextInt();
        sc.nextLine();

        Comentario comentario = new Comentario(autor, conteudo, LocalDate.now(), idPost);
        comentarioDAO.cadastrarComentario(comentario);
    }

    //LISTAR AS CATEGORIAS DEFINIDAS
    static void listarPostsPorCategoria() {
        List<Categoria> categorias = categoriaDAO.listarCategorias();
        System.out.println("Categorias disponíveis:");
        for (Categoria c : categorias) {
            System.out.println("- " + c.getNome());
        }
        System.out.print("Digite o nome da categoria: ");
        String nome = sc.nextLine();

        List<String[]> posts = postDAO.listarPostsPorCategoria(nome);
        if (posts.isEmpty()) {
            System.out.println("Nenhum post encontrado para essa categoria.");
        } else {
            System.out.println("\n--- Posts em " + nome + " ---");
            for (String[] row : posts) {
                System.out.println("Post: " + row[0] + " | Categoria: " + row[1] + " | Comentários: " + row[2]);
            }
        }
    }
}