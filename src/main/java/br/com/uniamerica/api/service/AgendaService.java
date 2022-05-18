package br.com.uniamerica.api.service;

import br.com.uniamerica.api.entity.Agenda;
import br.com.uniamerica.api.entity.Especialidade;
import br.com.uniamerica.api.entity.StatusAgenda;
import br.com.uniamerica.api.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public Optional<Agenda> findById(Long id){
        return this.agendaRepository.findById(id);
    }

    public Page<Agenda> listAll(Pageable pageable){
        return this.agendaRepository.findAll(pageable);
    }

    @Transactional
    public void updateStatus(Long id, Agenda agenda) {
        if (id == agenda.getId()) {
            this.agendaRepository.updateStatus(agenda.getId());
        } else {
            throw new RuntimeException();
        }
    }

    public void updateStatusAgendaAprovado(Agenda agenda) {
        if(agenda.getStatus().equals(StatusAgenda.pendente)){
            updateStatusAgenda(agenda, StatusAgenda.aprovado);
        }
    }

    public void updateStatusAgendaCancelado(Agenda agenda) {
        if(agenda.getStatus().equals(StatusAgenda.aprovado)){
            updateStatusAgenda(agenda, StatusAgenda.cancelado);
        }
    }

    public void updateStatusAgendaCompareceu(Agenda agenda) {
        if(agenda.getStatus().equals(StatusAgenda.aprovado)){
            updateStatusAgenda(agenda, StatusAgenda.compareceu);
        }
    }

    public void updateStatusAgendaNaoCompareceu(Agenda agenda) {
        if(agenda.getStatus().equals(StatusAgenda.aprovado)){
            updateStatusAgenda(agenda, StatusAgenda.nao_compareceu);
        }
    }

    public void updateStatusAgendaRejeitado(Agenda agenda) {
        if(agenda.getStatus().equals(StatusAgenda.pendente)){
            updateStatusAgenda(agenda, StatusAgenda.rejeitado);
        }
    }

    public void updateStatusAgenda(Agenda agenda, StatusAgenda statusAgenda){
        agenda.setStatus(statusAgenda);
        agendaRepository.save(agenda);
    }
    
    public void validaDisponibilidade(Agenda newAgenda, Agenda oldAgenda){
        if(newAgenda.getDataDe()  && newAgenda.getDataAte() == oldAgenda.getDataDe() && oldAgenda.getDataAte()){
            throw new RuntimeException("Horário não disponível");
        }
    }

    @Transactional
    public void insert(Agenda agenda){
        this.agendaRepository.save(agenda);
    }

    @Transactional
    public void update(Long id, Agenda agenda){
        if (id == agenda.getId()) {
            this.agendaRepository.save(agenda);
        }
        else {
            throw new RuntimeException();
        }
    }
}
