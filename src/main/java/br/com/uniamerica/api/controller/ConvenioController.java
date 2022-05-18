package br.com.uniamerica.api.controller;

import br.com.uniamerica.api.entity.Convenio;
import br.com.uniamerica.api.repository.ConvenioRepository;
import br.com.uniamerica.api.service.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/convenios")
public class ConvenioController {

    @Autowired
    public ConvenioService convenioService;

    @GetMapping("{idConvenio}")
    public ResponseEntity<Convenio> findById(
            @PathVariable("idConvenio") Long idConvenio
    ) {
        return ResponseEntity.ok().body(this.convenioService.findById(idConvenio).get());
    }


    @GetMapping
    public ResponseEntity<Page<Convenio>> listByAllPages(Pageable pageable) {
        return ResponseEntity.ok().body(this.convenioService.listAll(pageable));
    }


    @PostMapping //Cadastrar
    public ResponseEntity<?> insert(@RequestBody Convenio convenio) {
        try {
            this.convenioService.insert(convenio);
            return ResponseEntity.ok().body("Convenio cadastrado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{idConvenio}") //Atualizar
    public ResponseEntity update(
            @RequestBody Convenio convenio,
            @PathVariable Long idConvenio
    ) {
        try {
            this.convenioService.update(idConvenio);
            return ResponseEntity.ok().body("Convenio atualizado com sucesso");
        } catch (RuntimeException e) {
            ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/status/{idConvenio}") //Desativar
    public ResponseEntity<?> updateStatus(
            @RequestBody Convenio convenio,
            @PathVariable Long idConvenio
    ) {
        try {
            this.convenioService.updateStatus(idConvenio);
            return ResponseEntity.ok().body("Convenio desativado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
