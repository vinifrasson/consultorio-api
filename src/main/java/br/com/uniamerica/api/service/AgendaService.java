package br.com.uniamerica.api.service;
import br.com.uniamerica.api.entity.Agenda;
import br.com.uniamerica.api.entity.StatusAgenda;
import br.com.uniamerica.api.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
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



    public boolean DataMenorAtual(LocalDateTime localDateTimeDe, LocalDateTime localDateTimeAte){
        return localDateTimeDe.compareTo(LocalDateTime.now()) <= 0 && localDateTimeAte.compareTo(LocalDateTime.now()) <= 0;
    }

    public boolean dataAteMaior(LocalDateTime localDateTimeDe, LocalDateTime localDateTimeAte){
        return localDateTimeAte.compareTo(localDateTimeDe) >= 0;
    }

    public boolean horarioAtendimento(LocalDateTime localDateTimeDe, LocalDateTime localDateTimeAte){
        return localDateTimeDe.getHour() < 8 || localDateTimeDe.getHour() > 11 &&
        localDateTimeAte.getHour() < 9 || localDateTimeAte.getHour() > 12 ||
        localDateTimeDe.getHour()  < 14 || localDateTimeDe.getHour() > 17 &&
        localDateTimeAte.getHour() < 15 || localDateTimeAte.getHour() > 18;
    }

    public boolean fimDeSemana(LocalDateTime localDateTimeDe){
        return localDateTimeDe.getDayOfWeek() != DayOfWeek.SATURDAY && localDateTimeDe.getDayOfWeek() != DayOfWeek.SUNDAY;
    }

    public boolean DataDisponivel(LocalDateTime localDateTimeDe, LocalDateTime localDateTimeAte){
        return agendaRepository.DataDisponivel(localDateTimeDe, localDateTimeAte);
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
    @Transactional
    public void updateStatusAgenda(Agenda agenda, StatusAgenda statusAgenda){
        agenda.setStatus(statusAgenda);
        agendaRepository.save(agenda);
    }

/*    public void validaDisponibilidade(Agenda newAgenda, Agenda oldAgenda){
        if(newAgenda.getDataDe()  && newAgenda.getDataAte() == oldAgenda.getDataDe() && oldAgenda.getDataAte()){
            throw new RuntimeException("Horário não disponível");
        }
    }*/

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
