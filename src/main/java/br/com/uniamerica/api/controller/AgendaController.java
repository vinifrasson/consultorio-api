package br.com.uniamerica.api.controller;

import br.com.uniamerica.api.entity.Agenda;
import br.com.uniamerica.api.repository.AgendaRepository;
import br.com.uniamerica.api.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/agendas")
public class AgendaController {

    @Autowired
    public AgendaService agendaService;


    @GetMapping("/{idAgenda}")
    public ResponseEntity<Agenda> findByID(
            @PathVariable("idAgenda") Long idAgenda
    ){
        return ResponseEntity.ok().body(this.agendaService.findById(idAgenda).get());
    }

    @GetMapping
    public ResponseEntity<Page<Agenda>> listByAllPages(Pageable pageable){
        return ResponseEntity.ok().body(this.agendaService.listAll(pageable));

    }


    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Agenda agenda){
        try{
            this.agendaService.insert(agenda);
            return ResponseEntity.ok().body("Agenda cadastrada com sucesso!");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("{idAgenda}")
    public ResponseEntity<?> update(
            @RequestBody Agenda agenda,
            @PathVariable Long idAgenda
    ){
        try {
            this.agendaService.update(idAgenda, agenda);
            return ResponseEntity.ok().body("Agenda atualizada com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/status/{idAgenda}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Agenda agenda,
            @PathVariable Long idAgenda
    ){
        try {
            this.agendaService.updateStatus(idAgenda, agenda);
            return ResponseEntity.ok().body("Agenda desativada com sucesso");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
