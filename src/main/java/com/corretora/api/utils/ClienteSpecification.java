package com.corretora.api.utils;

import com.corretora.api.dto.ClienteDto;
import com.corretora.api.entity.Cliente;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteSpecification {
    public static Specification<Cliente> filter(ClienteDto dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(dto.getDsNome() != null) {
                predicates.add(cb.like(cb.lower(root.get("dsNome")), "%" + dto.getDsNome().toLowerCase() + "%"));
            }
            if(dto.getCdCpfCnpj() != null) {
                predicates.add(cb.like(cb.lower(root.get("cdCpfCnpj")), "%" + dto.getCdCpfCnpj().toLowerCase() + "%"));
            }
            if(dto.getDsCidade() != null) {
                predicates.add(cb.like(cb.lower(root.get("dsCidade")), "%" + dto.getDsCidade().toLowerCase() + "%"));
            }
            if(dto.getDsEstado() != null) {
                predicates.add(cb.like(cb.lower(root.get("dsEstado")), "%" + dto.getDsEstado().toLowerCase() + "%"));
            }
            if(dto.getDsEndereco() != null) {
                predicates.add(cb.like(cb.lower(root.get("dsEndereco")), "%" + dto.getDsEndereco().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
