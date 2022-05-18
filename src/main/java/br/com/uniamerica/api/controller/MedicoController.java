package br.com.uniamerica.api.controller;

import br.com.uniamerica.api.entity.Agenda;
import br.com.uniamerica.api.entity.Especialidade;
import br.com.uniamerica.api.entity.Medico;
import br.com.uniamerica.api.repository.AgendaRepository;
import br.com.uniamerica.api.repository.MedicoRepository;
import br.com.uniamerica.api.service.EspecialidadeService;
import br.com.uniamerica.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Eduardo Sganderla
 *
 * @since 1.0.0, 07/04/2022
 * @version 1.0.0
 */
@Controller
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    public MedicoService medicoService;

    @GetMapping("/{idMedico}")
    public ResponseEntity<Medico> findById(
            @PathVariable("idMedico") Long idMedico
    ) {
        return ResponseEntity.ok().body(this.medicoService.findById(idMedico).get());
    }

    @GetMapping
    public ResponseEntity<Page<Medico>> listByAllPages(Pageable pageable) {
        return ResponseEntity.ok().body(this.medicoService.listAll(pageable));
    }


    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Medico medico) {
        try {
            this.medicoService.insert(medico);
            return ResponseEntity.ok().body("Medico cadastrada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/{idMedico}")
    public ResponseEntity<?> update
            (@RequestBody Medico medico,
             @PathVariable Long idMedico) {
        try {
            this.medicoService.update(idMedico ,medico);
            return ResponseEntity.ok().body("Medico atualizada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/status/{idMedico}")
    public ResponseEntity<?> updateStatus
            (@RequestBody Medico medico,
             @PathVariable Long idMedico) {
        try {
            this.medicoService.updateStatus(idMedico ,medico);
            return ResponseEntity.ok().body("Medico desativada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}}
