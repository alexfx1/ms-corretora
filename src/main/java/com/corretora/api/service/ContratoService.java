package com.corretora.api.service;

import com.corretora.api.dto.CorretorDto;
import com.corretora.api.dto.contrato.*;
import com.corretora.api.entity.Cliente;
import com.corretora.api.entity.Contrato;
import com.corretora.api.entity.Mercadoria;
import com.corretora.api.entity.Motorista;
import com.corretora.api.enums.StatusEnum;
import com.corretora.api.records.MercadoriaRecord;
import com.corretora.api.repository.ContratoRepository;
import com.corretora.api.utils.ContratoSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.io.ByteArrayOutputStream;

@Slf4j
@Service
public class ContratoService {
    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MotoristaService motoristaService;

    @Autowired
    private MercadoriaService mercadoriaService;

    @Autowired
    private ParametroService parametroService;

    private static final String DESCRIPTION_KEY = "DESCRICAO_CONTRATO";
    private static final String POLITICA_VALORES = "POLITICA_CONTRATO";


    @Transactional
    public ContratoResponseDto save(ContratoDto contratoDto) {
        var contrato = new Contrato();
        return update(contratoDto, contrato);
    }

    @Transactional
    public ContratoResponseDto put(ContratoDto contratoDto, Long cdContrato) {
        var contrato = contratoRepository.findById(cdContrato).orElseThrow();
        return update(contratoDto, contrato);
    }

    public List<ContratoResponseDto> getAll(Long cdCorretor) {
        var contratos = contratoRepository.findByCdCorretor(cdCorretor);
        List<ContratoResponseDto> responseDtoList = new ArrayList<>();
        for(Contrato contrato : contratos) {
            var dto = new ContratoResponseDto();
            copyPropertiesContrato(contrato,dto);
            responseDtoList.add(dto);
        }
        return responseDtoList;
    }

    public Page<ContratoResponseDto> getAllPageable(Long cdCorretor, int page, int pageSize, FilterDto filter) {
        var specification = ContratoSpecification.filter(filter, cdCorretor);
        var pageable = PageRequest.of(page, pageSize);
        var contratos = contratoRepository.findAll(specification, pageable);

        return contratos.map(contrato -> {
            var dto = new ContratoResponseDto();
            copyPropertiesContrato(contrato,dto);
            return dto;
        });
    }

    public Page<ContratoCardDto> getAllByCard(Long cdCorretor, int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Direction.DESC, "dtUpdate"));
        Page<Contrato> resultQuery;

        if(search == null || search.isEmpty()) {
            resultQuery = contratoRepository.findByCdCorretorOrderByDtUpdateDesc(cdCorretor, pageable);
        } else {
            var specification = ContratoSpecification.filterByTerm(search);
            resultQuery = contratoRepository.findAll(specification, pageable);
        }

        return resultQuery.map(contrato -> {
            var card = new ContratoCardDto();
            BeanUtils.copyProperties(contrato, card);
            card.setComprador(contrato.getCdComprador() == null ? null : clienteService.findById(contrato.getCdComprador()).getDsNome());
            card.setMercadoria(contrato.getCdMercadoria() == null ? null : mercadoriaService.findById(contrato.getCdMercadoria()).getDsMercadoria());
            return card;
        });
    }

    public ContratoResponseDto getById(Long cdContrato) {
        var contrato = contratoRepository.findById(cdContrato).orElseThrow();
        var dto = new ContratoResponseDto();
        copyPropertiesContrato(contrato, dto);
        return dto;
    }

    public byte[] generatePdf(Long cdContract, CorretorDto corretor) throws JRException {
        var contrato = contratoRepository.findById(cdContract)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found"));

        // Map entity -> DTO (or use entity directly if fields match)
        var dto = copyPropertiesContratoJasper(contrato, corretor);

        // Compile JRXML (only needed once; in prod you’d precompile to .jasper)
        JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/contract-report.jrxml")
        );

        // Wrap DTO in a collection
        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(Collections.singletonList(dto));

        // Extra parameters if needed
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reportTitle", "Contrato de Compra e Venda");

        // Fill report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export to PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

        return baos.toByteArray();
    }

    public void deleteContract(Long id) {
        var contrato = contratoRepository.findById(id).orElseThrow();
        contratoRepository.delete(contrato);
    }

    private void copyPropertiesContrato(Contrato contrato, ContratoResponseDto dto) {
        BeanUtils.copyProperties(contrato, dto);
        dto.setDtContrato(contrato.getDtContrato() == null ? null : contrato.getDtContrato().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dto.setComprador(contrato.getCdComprador() == null ? null : clienteService.findById(contrato.getCdComprador()));
        dto.setVendedor(contrato.getCdVendedor() == null ? null : clienteService.findById(contrato.getCdVendedor()));
        dto.setMotorista(contrato.getCdMotorista() == null ? null : motoristaService.findById(contrato.getCdMotorista()));
        dto.setMercadoria(contrato.getCdMercadoria() == null ? null : mercadoriaService.findById(contrato.getCdMercadoria()));
    }

    private ContratoResponseDto update(ContratoDto contratoDto, Contrato contrato) {
        /*
          Na origem / No destino peso / qualidade
          Quantidade: 37500 Kg sempre dividido por 60 pra dar qntd de sacos e kg
        */

        Cliente comprador = null;
        Cliente vendedor = null;
        Motorista motorista = null;
        Mercadoria mercadoria = null;

        if(contratoDto.getComprador() != null) {
            comprador = clienteService.saveEntity(contratoDto.getComprador());
        }

        if(contratoDto.getVendedor() != null) {
            vendedor = clienteService.saveEntity(contratoDto.getVendedor());
        }

        if(contratoDto.getMotorista() != null) {
            motorista = motoristaService.saveEntity(contratoDto.getMotorista());
        }

        if(contratoDto.getMercadoria() != null && !Objects.equals(contrato.getMercadoria().getDsMercadoria(), "")) {
            mercadoria = mercadoriaService.save(new MercadoriaRecord(contratoDto.getMercadoria().getDsMercadoria(), true));
        }

        contrato.setDtInicial(contrato.getDtInicial() == null ? LocalDateTime.now(ZoneId.of("America/Sao_Paulo")) : contrato.getDtInicial());
        contrato.setDtUpdate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        contrato.setDtContrato(contratoDto.getDtContrato() != null ?
                LocalDate.parse(resolveDtContrato(contratoDto.getDtContrato()), DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null);

        BeanUtils.copyProperties(contratoDto, contrato);

        contrato.setCdCorretor(contratoDto.getCdCorretor());

        if(contratoDto.getComprador() == null || contratoDto.getVendedor() == null || contratoDto.getMotorista() == null || contratoDto.getMercadoria() == null) {
            contrato.setDsStatus(StatusEnum.RASCUNHO.getStatus());
        } else {
            contrato.setDsStatus(StatusEnum.OK.getStatus());
        }

        contrato.setCdComprador(comprador == null ? null :  comprador.getCdCliente());
        contrato.setComprador(comprador);

        contrato.setCdVendedor(vendedor == null ? null : vendedor.getCdCliente());
        contrato.setVendedor(vendedor);

        contrato.setCdMotorista(motorista == null ? null : motorista.getCdMotorista());
        contrato.setMotorista(motorista);

        contrato.setCdMercadoria(mercadoria == null ? null : mercadoria.getCdMercadoria());
        contrato.setMercadoria(mercadoria);

        contrato.setDsDescricao(contratoDto.getDsDescricao());
        contrato.setDsPesoQualidade(contratoDto.getDsPesoQualidade());
        contrato.setDsPadraoTolerancia(contratoDto.getDsPadraoTolerancia());
        contrato.setDsArmazenagem(contratoDto.getDsArmazenagem());
        contrato.setDsEnderecoEntrega(contratoDto.getDsEnderecoEntrega());
        contrato.setDsEmbalagem(contratoDto.getDsEmbalagem());
        contrato.setDsPagamento(contratoDto.getDsPagamento());

        if(contratoDto.getDsFormaPagamento().equalsIgnoreCase("pix")) {
            contrato.setDsFormaPagamento(contratoDto.getDsFormaPagamento().toUpperCase());
        } else {
            contrato.setDsFormaPagamento(contratoDto.getDsFormaPagamento());
        }

        // Preco mercadoria - quantidade
        contrato.setPrecoSaco(contratoDto.getPrecoSaco());
        contrato.setVlQuantidadeSaco(contratoDto.getVlQuantidadeSaco());
        contrato.setVlKilo(contratoDto.getVlKilo());
        if(contratoDto.getVlQuantidadeSaco() != null && contratoDto.getVlKilo() != null) {
            contrato.setVlQuantidade(contrato.getVlKilo().multiply(BigDecimal.valueOf(contrato.getVlQuantidadeSaco())));
        }

        // Save db
        var salvo = contratoRepository.save(contrato);

        var responseDto = new ContratoResponseDto();
        copyPropertiesContrato(salvo, responseDto);
        return responseDto;
    }

    private ContratoJasperDto copyPropertiesContratoJasper(Contrato contrato, CorretorDto corretorDto) {
        var dto = new ContratoJasperDto();
        dto.setIdContrato(contrato.getCdContrato().toString());

        // Comprador
        if(contrato.getComprador() != null) {
            dto.setCompradorNome(contrato.getComprador().getDsNome());
            dto.setCompradorCpfCnpj(contrato.getComprador().getCdCpfCnpj());
            dto.setCompradorEndereco(contrato.getComprador().getDsEndereco());
            dto.setCompradorInsc(contrato.getComprador().getDsIns());
            dto.setCompradorCidade(contrato.getComprador().getDsCidade());
            dto.setCompradorEstado(contrato.getComprador().getDsEstado());
        }

        // Vendedor
        if(contrato.getVendedor() != null) {
            dto.setVendedorNome(contrato.getVendedor().getDsNome());
            dto.setVendedorCpfCnpj(contrato.getVendedor().getCdCpfCnpj());
            dto.setVendedorEndereco(contrato.getVendedor().getDsEndereco());
            dto.setVendedorCidade(contrato.getVendedor().getDsCidade());
            dto.setVendedorEstado(contrato.getVendedor().getDsEstado());
            dto.setFavorecido(contrato.getVendedor().getDsNome());
            dto.setVendedorBanco(contrato.getVendedor().getDsBanco());
            dto.setVendedorAgencia(contrato.getVendedor().getCdAgencia());
            dto.setVendedorConta(contrato.getVendedor().getCdConta());
            dto.setVendedorInsc(contrato.getVendedor().getDsIns());
            dto.setVendedorChavePix(contrato.getVendedor().getDsChavePix() == null ? "" : contrato.getVendedor().getDsChavePix());
        }

        // Motorista
        if(contrato.getMotorista() != null) {
            dto.setMotoristaNome(contrato.getMotorista().getDsNome());
            dto.setMotoristaCpf(contrato.getMotorista().getCdCpf());
            dto.setMotoristaCidade(contrato.getMotorista().getDsCidade() + " - " + contrato.getMotorista().getDsEstado());
            dto.setMotoristaPlaca(contrato.getMotorista().getDsPlaca());
        }

        // Mercadoria
        if(contrato.getMercadoria() != null) {
            dto.setMercadoria(contrato.getMercadoria().getDsMercadoria());
        }

        dto.setQuantidade(contrato.getVlQuantidade() == null ? "0" : contrato.getVlQuantidade().toString());
        dto.setQuantidadeSaco(contrato.getVlQuantidadeSaco() == null ? "0" : contrato.getVlQuantidadeSaco().toString());
        dto.setKiloSaco(contrato.getVlKilo() == null ? "0" : contrato.getVlKilo().toString());
        if(contrato.getVlComissao() != null) {
            dto.setCorretorComissao(contrato.getVlComissao().toString());
        }

        dto.setPadraoTolerancia(contrato.getDsPadraoTolerancia());
        dto.setArmazenagem(contrato.getDsArmazenagem());
        dto.setEntrega(contrato.getDsEnderecoEntrega());
        dto.setEmbalagem(contrato.getDsEmbalagem());
        dto.setPreco(contrato.getPrecoSaco().toString());
        dto.setFormaPagamento(contrato.getDsFormaPagamento());
        dto.setPagamento(contrato.getDsPagamento());
        dto.setPesoQualidade(contrato.getDsPesoQualidade());

        // Corretor
        dto.setCorretorNome(corretorDto.getDsNome());
        dto.setCorretorCidade(corretorDto.getDsCidade());
        dto.setCorretorCpf(corretorDto.getCdCpf());
        dto.setCorretorEstado(corretorDto.getDsEstado());
        dto.setCorretorBanco(corretorDto.getDsBanco());
        dto.setCorretorAgencia(corretorDto.getCdAgencia());
        dto.setCorretorConta(corretorDto.getCdConta());
        dto.setCorretorPix(corretorDto.getDsChavePix());

        // Final Obs
        var politicaValores = parametroService.findByKey(POLITICA_VALORES);
        dto.setPoliticaValores(politicaValores.getVlParametro());
        dto.setObservacoes(contrato.getDsDescricao());

        if(contrato.getDtContrato() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("pt-BR"));
            String formatted = contrato.getDtContrato().format(formatter);
            dto.setCidadeData(corretorDto.getDsCidade() + " - " + corretorDto.getDsEstado() + ", " + formatted);
        }
        return dto;
    }

    private String resolveDtContrato(String dtContrato) {
        if(dtContrato == null || dtContrato.equals("")) {
            return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return dtContrato;
    }

}
