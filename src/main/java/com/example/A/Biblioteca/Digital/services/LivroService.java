package com.example.A.Biblioteca.Digital.services;

import com.example.A.Biblioteca.Digital.models.LivroModel;
import com.example.A.Biblioteca.Digital.repositories.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    private final LivroRepository repo;

    public LivroService(LivroRepository repo) { this.repo = repo; }

    public LivroModel criar(LivroModel l) { return repo.save(l); }
    public List<LivroModel> listar() { return repo.findAll(); }
    public List<LivroModel> buscarPorTitulo(String t){ return repo.findByTituloContainingIgnoreCase(t); }
    public Optional<LivroModel> buscarPorId(Long id){ return repo.findById(id); }
    public LivroModel atualizar(LivroModel l){ return repo.save(l); }
    public void excluir(Long id){ repo.deleteById(id); }
    public boolean isbnExiste(String isbn){ return repo.existsByIsbn(isbn); }
}
