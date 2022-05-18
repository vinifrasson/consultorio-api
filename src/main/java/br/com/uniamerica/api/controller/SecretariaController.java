package br.com.uniamerica.api.controller;

import br.com.uniamerica.api.entity.Agenda;
import br.com.uniamerica.api.entity.Especialidade;
import br.com.uniamerica.api.entity.Secretaria;
import br.com.uniamerica.api.repository.AgendaRepository;
import br.com.uniamerica.api.repository.SecretariaRepository;
import br.com.uniamerica.api.service.EspecialidadeService;
import br.com.uniamerica.api.service.SecretariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/secretarias")
public class SecretariaController {
    @Autowired
    public SecretariaService secretariaService;

    @GetMapping("/{idSecretaria}")
    public ResponseEntity<Secretaria> findById(
            @PathVariable("idSecretaria") Long idSecretaria
    ) {
        return ResponseEntity.ok().body(this.secretariaService.findById(idSecretaria).get());
    }

    @GetMapping
    public ResponseEntity<Page<Secretaria>> listByAllPages(Pageable pageable) {
        return ResponseEntity.ok().body(this.secretariaService.listAll(pageable));
    }


    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Secretaria secretaria) {
        try {
            this.secretariaService.insert(secretaria);
            return ResponseEntity.ok().body("Secretaria cadastrada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/{idSecretaria}")
    public ResponseEntity<?> update
            (@RequestBody Secretaria secretaria,
             @PathVariable Long idSecretaria) {
        try {
            this.secretariaService.update(idSecretaria ,secretaria);
            return ResponseEntity.ok().body("Secretaria atualizada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/status/{idSecretaria}")
    public ResponseEntity<?> updateStatus
            (@RequestBody Secretaria secretaria,
             @PathVariable Long idSecretaria) {
        try {
            this.secretariaService.updateStatus(idSecretaria ,secretaria);
            return ResponseEntity.ok().body("Secretaria desativada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
