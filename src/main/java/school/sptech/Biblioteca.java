package school.sptech;

import school.sptech.exception.ArgumentoInvalidoException;
import school.sptech.exception.LivroNaoEncontradoException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {

    private String nome;
    private List<Livro> livros;

    public Biblioteca(String nome) {
        this.nome = nome;
        livros = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void adicionarLivro(Livro livro){
        if (livro == null){
            throw new ArgumentoInvalidoException();
        }
        if (livro.getDataPublicacao() == null) {
            throw new ArgumentoInvalidoException();
        }
        if (livro.getAutor() == null) {
            throw new ArgumentoInvalidoException();
        }
        if (livro.getTitulo() == null) {
            throw new ArgumentoInvalidoException();
        }
        if (livro.getTitulo().isBlank()) {
            throw new ArgumentoInvalidoException();
        }
        if (livro.getAutor().isBlank()) {
            throw new ArgumentoInvalidoException();
        }

        livros.add(livro);
    }

    public Livro buscarLivroPorTitulo(String titulo){
        if(titulo == null || titulo.isBlank()){
            throw new ArgumentoInvalidoException();
        }
        for (Livro livroDaVez : livros) {
            if (livroDaVez.getTitulo().equalsIgnoreCase(titulo)){
                return livroDaVez;
            }
        }
        throw new LivroNaoEncontradoException();
    }

    public void removerLivroPorTitulo(String titulo){
        Livro livro = buscarLivroPorTitulo(titulo);
        livros.remove(livro);
    }

    public Integer contarLivros(){
        return  livros.size();
    }

    public List<Livro> obterLivrosAteAno(Integer ano){
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (int i = 0; i < livros.size(); i++) {
            Livro livroDaVez = livros.get(i);
            LocalDate dataDaVez = livroDaVez.getDataPublicacao();
            if (dataDaVez.getYear() <= ano){
                livrosEncontrados.add(livroDaVez);
            }
        }
        return livrosEncontrados;
    }

    public List<Livro> retornarTopCincoLivros() {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livroDaVez : livros) {
            Boolean foiInserido = false;
            for (int i = 0; i < livrosEncontrados.size(); i++) {
                if (livroDaVez.calcularMediaAvaliacoes() > livrosEncontrados.get(i).calcularMediaAvaliacoes()) {
                    livrosEncontrados.add(i, livroDaVez);
                    foiInserido = true;
                    break;
                }
            }
            if (!foiInserido) {
                livrosEncontrados.add(livroDaVez);
            }
            if (livrosEncontrados.size() > 5) {
                livrosEncontrados.remove(livrosEncontrados.size() - 1);
            }
        }
        return livrosEncontrados;
    }

}
