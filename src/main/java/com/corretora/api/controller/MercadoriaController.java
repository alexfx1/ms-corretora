package com.corretora.api.controller;

import com.corretora.api.entity.Mercadoria;
import com.corretora.api.records.MercadoriaRecord;
import com.corretora.api.service.MercadoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaController {
    @Autowired
    private MercadoriaService mercadoriaService;

    @PostMapping
    public Mercadoria save(@RequestBody MercadoriaRecord mercadoriaRecord) {
        return mercadoriaService.save(mercadoriaRecord);
    }

    @GetMapping
    public List<MercadoriaRecord> findAll() {
        return mercadoriaService.findAll();
    }
}
