package com.corretora.api.controller;

import com.corretora.api.dto.CorretorDto;
import com.corretora.api.dto.contrato.ContratoCardDto;
import com.corretora.api.dto.contrato.ContratoDto;
import com.corretora.api.dto.contrato.ContratoResponseDto;
import com.corretora.api.dto.contrato.FilterDto;
import com.corretora.api.enums.StatusEnum;
import com.corretora.api.service.ContratoService;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/contrato")
public class ContratoController {
    @Autowired
    private ContratoService contratoService;

    @GetMapping("/{cdCorretor}")
    public ResponseEntity<List<ContratoResponseDto>> getAll(@PathVariable(name = "cdCorretor") Long cdCorretor) {
        return ResponseEntity.ok(contratoService.getAll(cdCorretor));
    }

    @GetMapping("/table/{cdCorretor}")
    public ResponseEntity<Page<ContratoResponseDto>> getAll(@PathVariable(name = "cdCorretor") Long cdCorretor,
                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                            @RequestParam(name = "cod", required = false) Integer cod,
                                                            @RequestParam(name = "status", required = false) StatusEnum status,
                                                            @RequestParam(name = "client", required = false) String client,
                                                            @RequestParam(name = "vendedor", required = false) String vendedor,
                                                            @RequestParam(name = "mercadoria", required = false) String mercadoria,
                                                            @RequestParam(name = "preco", required = false) BigDecimal preco,
                                                            @RequestParam(name = "dtPeriod", required = false) String dtPeriod,
                                                            @RequestParam(name = "order", required = false, defaultValue = "true") Boolean order) {
        var filter = new FilterDto(cod, status, client, vendedor, mercadoria, preco, dtPeriod, order);
        return ResponseEntity.ok(contratoService.getAllPageable(cdCorretor, page, pageSize, filter));
    }

    @GetMapping("/cards/{cdCorretor}")
    public ResponseEntity<Page<ContratoCardDto>> getAllByCard(@PathVariable(name = "cdCorretor") Long cdCorretor,
                                                              @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                              @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                              @RequestParam(name = "search", required = false) String search) {
        return ResponseEntity.ok(contratoService.getAllByCard(cdCorretor, page, pageSize, search));
    }

    @GetMapping("/id/{cdContrato}")
    public ResponseEntity<ContratoResponseDto> findById(@PathVariable(name = "cdContrato") Long cdContrato) {
        return ResponseEntity.ok(contratoService.getById(cdContrato));
    }

    @PostMapping
    public ResponseEntity<ContratoResponseDto> save(@RequestBody @Valid ContratoDto contratoDto) {
        return ResponseEntity.ok(contratoService.save(contratoDto));
    }

    @PutMapping("/{cdContrato}")
    public ResponseEntity<ContratoResponseDto> put(@RequestBody @Valid ContratoDto contratoDto, @PathVariable(name = "cdContrato") Long cdContrato) {
        return ResponseEntity.ok(contratoService.put(contratoDto, cdContrato));
    }

    @PostMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadContract(@PathVariable("id") Long id,
                                                   @RequestBody CorretorDto dto) throws JRException {
        var pdf = contratoService.generatePdf(id, dto);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contract_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable("id") Long id) {
        contratoService.deleteContract(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
