package com.example.A.Biblioteca.Digital.repositories;

import com.example.A.Biblioteca.Digital.models.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivroRepository extends JpaRepository<LivroModel, Long> {
    List<LivroModel> findByTituloContainingIgnoreCase(String titulo);
    List<LivroModel> findByAutorContainingIgnoreCase(String autor);
    boolean existsByIsbn(String isbn);
}
