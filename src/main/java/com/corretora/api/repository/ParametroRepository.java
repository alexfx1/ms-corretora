package com.corretora.api.repository;

import com.corretora.api.entity.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ParametroRepository extends JpaRepository<Parametro, Long> {
    Page<Parametro> findByDsParametroOrVlParametro(String dsParametro, String vlParametro, Pageable pageable);
    Optional<Parametro> findByDsChaveParametro(String dsChaveParametro);
}
