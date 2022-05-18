package br.com.uniamerica.api.controller;

import br.com.uniamerica.api.entity.Agenda;
import br.com.uniamerica.api.entity.Especialidade;
import br.com.uniamerica.api.entity.Paciente;
import br.com.uniamerica.api.repository.AgendaRepository;
import br.com.uniamerica.api.repository.PacienteRepository;
import br.com.uniamerica.api.service.EspecialidadeService;
import br.com.uniamerica.api.service.PacienteService;
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
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    public PacienteService pacienteService;

    @GetMapping("/{idPaciente}")
    public ResponseEntity<Paciente> findById(
            @PathVariable("idPaciente") Long idPaciente
    ) {
        return ResponseEntity.ok().body(this.pacienteService.findById(idPaciente).get());
    }

    @GetMapping
    public ResponseEntity<Page<Paciente>> listByAllPages(Pageable pageable) {
        return ResponseEntity.ok().body(this.pacienteService.listAll(pageable));
    }


    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Paciente paciente) {
        try {
            this.pacienteService.insert(paciente);
            return ResponseEntity.ok().body("Paciente cadastrada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/{idPaciente}")
    public ResponseEntity<?> update
            (@RequestBody Paciente paciente,
             @PathVariable Long idPaciente) {
        try {
            this.pacienteService.update(idPaciente ,paciente);
            return ResponseEntity.ok().body("Paciente atualizada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/status/{idPaciente}")
    public ResponseEntity<?> updateStatus
            (@RequestBody Paciente paciente,
             @PathVariable Long idPaciente) {
        try {
            this.pacienteService.updateStatus(idPaciente ,paciente);
            return ResponseEntity.ok().body("Paciente desativada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
