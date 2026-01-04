package com.corretora.api.repository;

import com.corretora.api.entity.Mercadoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MercadoriaRepository extends JpaRepository<Mercadoria, Long> {
    Optional<Mercadoria> findByDsMercadoria(String dsMercadoria);
}
