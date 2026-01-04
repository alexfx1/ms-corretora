package com.corretora.api.utils;

import com.corretora.api.dto.contrato.FilterDto;
import com.corretora.api.entity.Contrato;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

import java.time.format.DateTimeFormatter;

@Component
public class ContratoSpecification {
    public static Specification<Contrato> filterByTerm(String term) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (term != null && !term.isEmpty()) {
                String searchTerm = "%" + term.toLowerCase() + "%";

                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("dsStatus")), searchTerm));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("dsFormaPagamento")), searchTerm));

                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("CONCAT", String.class, root.get("cdContrato"))), searchTerm));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.function("CONCAT", String.class, root.get("precoSaco"))), searchTerm));

                Join<Object, Object> compradorJoin = root.join("comprador", JoinType.LEFT);
                Join<Object, Object> vendedorJoin = root.join("vendedor", JoinType.LEFT);
                Join<Object, Object> mercadoriaJoin = root.join("mercadoria", JoinType.LEFT);

                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(compradorJoin.get("dsNome")), searchTerm));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(vendedorJoin.get("dsNome")), searchTerm));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(mercadoriaJoin.get("dsMercadoria")), searchTerm));

                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.function("DATE_FORMAT", String.class, root.get("dtUpdate"), criteriaBuilder.literal("%Y-%m-%d %H:%i:%s")),
                        searchTerm
                ));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Contrato> filter(FilterDto filter, Long cdCorretor) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Required
            predicates.add(cb.equal(root.get("cdCorretor"), cdCorretor));

            // Optional filters
            if (filter.cod() != null) {
                predicates.add(cb.equal(root.get("cdContrato"), filter.cod()));
            }
            if (filter.status() != null) {
                predicates.add(cb.equal(root.get("dsStatus"), filter.status().getStatus()));
            }
            if (filter.client() != null) {
                Join<Object, Object> compradorJoin = root.join("comprador", JoinType.LEFT);
                predicates.add(cb.like(cb.lower(compradorJoin.get("dsNome")), "%" + filter.client().toLowerCase() + "%"));
            }
            if (filter.mercadoria() != null) {
                Join<Object, Object> mercadoriaJoin = root.join("mercadoria", JoinType.LEFT);
                predicates.add(cb.like(cb.lower(mercadoriaJoin.get("dsMercadoria")), "%" + filter.mercadoria().toLowerCase() + "%"));
            }
            if (filter.preco() != null) {
                predicates.add(cb.equal(root.get("precoSaco"), filter.preco()));
            }
            if (filter.dtPeriod() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                String[] dates = filter.dtPeriod().split("-");

                LocalDate startDate = LocalDate.parse(dates[0].trim(), formatter);
                LocalDate endDate   = LocalDate.parse(dates[1].trim(), formatter);

                predicates.add(cb.between(root.get("dtContrato"), startDate, endDate));
            }
            if (filter.vendedor() != null) {
                Join<Object, Object> vendedorJoin = root.join("vendedor", JoinType.LEFT);
                predicates.add(cb.like(cb.lower(vendedorJoin.get("dsNome")), "%" + filter.vendedor().toLowerCase() + "%"));
            }

            if(filter.ascDesc()) {
                query.orderBy(cb.desc(root.get("dtUpdate")));
            }
            else {
                query.orderBy(cb.asc(root.get("dtUpdate")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
