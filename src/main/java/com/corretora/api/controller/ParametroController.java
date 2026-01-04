package com.corretora.api.controller;

import com.corretora.api.entity.Parametro;
import com.corretora.api.records.ParametroRecord;
import com.corretora.api.service.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parametro")
public class ParametroController {
    @Autowired
    private ParametroService parametroService;

    @PostMapping
    public ResponseEntity<Parametro> save(@RequestBody ParametroRecord request) {
        return ResponseEntity.ok(parametroService.save(request));
    }

    @GetMapping("/{cdParametro}")
    public ResponseEntity<Parametro> getById(@PathVariable(name = "cdParametro") Long cdParametro) {
        return ResponseEntity.ok(parametroService.getById(cdParametro));
    }

    @PutMapping("/{dsChaveParametro}")
    public ResponseEntity<Parametro> update(@PathVariable(name = "dsChaveParametro") String dsChaveParametro, @RequestBody ParametroRecord request) {
        return ResponseEntity.ok(parametroService.updateById(request, dsChaveParametro));
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Parametro>> getAll(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(parametroService.getAll(page, pageSize));
    }

    @GetMapping
    public ResponseEntity<List<Parametro>> getAll() {
        return ResponseEntity.ok(parametroService.getAll());
    }

    @GetMapping("/search/{term}")
    public ResponseEntity<Page<Parametro>> search(@PathVariable(name = "term") String term,
                                                  @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(parametroService.search(term, page, pageSize));
    }

    @GetMapping("/chave/{dsChaveParametro}")
    public ResponseEntity<Parametro> search(@PathVariable(name = "dsChaveParametro") String dsChaveParametro) {
        return ResponseEntity.ok(parametroService.findByKey(dsChaveParametro));
    }

    @DeleteMapping("/{cdParametro}")
    public void delete(@PathVariable(name = "cdParametro") Long cdParametro) {
        parametroService.delete(cdParametro);
    }

}
