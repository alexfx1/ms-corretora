package com.corretora.api.repository;


import com.corretora.api.entity.Corretor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorretorRepository extends JpaRepository<Corretor, Long> {
}
