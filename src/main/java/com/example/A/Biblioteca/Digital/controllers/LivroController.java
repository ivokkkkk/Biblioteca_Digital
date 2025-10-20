package com.example.A.Biblioteca.Digital.controllers;

import com.example.A.Biblioteca.Digital.models.LivroModel;
import com.example.A.Biblioteca.Digital.services.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {
    private final LivroService service;

    public LivroController(LivroService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<LivroModel> criar(@RequestBody LivroModel body){
        if (body.getIsbn()!=null && service.isbnExiste(body.getIsbn())) {
            return ResponseEntity.badRequest().build();
        }
        LivroModel salvo = service.criar(body);
        return ResponseEntity.created(URI.create("/api/livros/" + salvo.getId())).body(salvo);
    }

    @GetMapping
    public List<LivroModel> listar(@RequestParam(required=false) String titulo){
        return (titulo==null || titulo.isBlank()) ? service.listar() : service.buscarPorTitulo(titulo);
    }

    @GetMapping("{id}")
    public ResponseEntity<LivroModel> buscar(@PathVariable Long id){
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<LivroModel> atualizar(@PathVariable Long id, @RequestBody LivroModel body){
        return service.buscarPorId(id).map(ex -> {
            ex.setTitulo(body.getTitulo());
            ex.setAutor(body.getAutor());
            ex.setIsbn(body.getIsbn());
            ex.setDataPublicacao(body.getDataPublicacao());
            ex.setQuantidade(body.getQuantidade());
            return ResponseEntity.ok(service.atualizar(ex));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        if (service.buscarPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
