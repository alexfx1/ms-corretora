package com.corretora.api.controller;

import com.corretora.api.dto.CorretorDto;
import com.corretora.api.entity.Corretor;
import com.corretora.api.service.CorretorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CorretorController")
@RestController
@RequestMapping("/corretor")
public class CorretorController {
    @Autowired
    private CorretorService corretorService;

    @PostMapping
    public CorretorDto save(@RequestBody @Valid CorretorDto corretorDto) {
        return corretorService.save(corretorDto);
    }

    @GetMapping
    public List<Corretor> findAll() {
        return corretorService.findAll();
    }
}
