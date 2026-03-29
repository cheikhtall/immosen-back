package sn.dev.ct.immosen.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dev.ct.immosen.dto.MensualiteDTO;
import sn.dev.ct.immosen.entity.Contrat;
import sn.dev.ct.immosen.entity.Mensualite;
import sn.dev.ct.immosen.entity.enums.StatutMensualite;
import sn.dev.ct.immosen.entity.enums.TypeContrat;
import sn.dev.ct.immosen.entity.enums.TypeDureeContrat;
import sn.dev.ct.immosen.mapper.MensualiteMapper;
import sn.dev.ct.immosen.repository.ContratRepository;
import sn.dev.ct.immosen.repository.MensualiteRepository;
import sn.dev.ct.immosen.service.MensualiteService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class MensualiteServiceImpl implements MensualiteService {

    private final MensualiteRepository mensualiteRepository;
    private final ContratRepository contratRepository;

    public MensualiteServiceImpl(MensualiteRepository mensualiteRepository, ContratRepository contratRepository) {
        this.mensualiteRepository = mensualiteRepository;
        this.contratRepository = contratRepository;
    }

    @Override
    public void genererMensualite(Contrat contrat) {

        if(!mensualiteRepository.findByContratId(contrat.getId()).isEmpty()){
            throw new RuntimeException("Les mensualités ont déjà été générées pour ce contrat");
        }
        LocalDate debut = contrat.getDateDebut();
        LocalDate fin = contrat.getDateFin();

        List<Mensualite> mensualites = new ArrayList<>();
        LocalDate current = debut;

        while (current.isAfter(fin)) {
            Mensualite mensualite = Mensualite.builder()
                    .mois(current.getMonthValue())
                    .annee(current.getYear())
                    .montantDu(contrat.getMontantMensuel())
                    .montantPaye(0.0)
                    .dateEcheance(current.withDayOfMonth(10))
                    .penalite(0.0)
                    .statut(StatutMensualite.EN_ATTENTE)
                    .contrat(contrat)
                    .build();

            mensualites.add(mensualite);
            current = current.plusMonths(1);
        }
        mensualiteRepository.saveAll(mensualites);
    }

    @Override
    public void genererMensualiteV2(Contrat contrat) {
        if (contrat.getTypeContrat() == TypeContrat.VENTE) {
            return;
        }

        if (!mensualiteRepository.findByContratId(contrat.getId()).isEmpty()) {
            throw new RuntimeException("Les mensualités ont déjà été générées pour ce contrat");
        }

        LocalDate debut = contrat.getDateDebut();
        LocalDate current = debut;

        List<Mensualite> mensualites = new ArrayList<>();
        int nombreMois;
        if(contrat.getTypeDureeContrat() == TypeDureeContrat.DETERMINE){
            nombreMois = (int) ChronoUnit.MONTHS.between(
                    contrat.getDateDebut(), contrat.getDateFin()
            ) +1;
        }else{
            nombreMois = 12;
        }

        for(int i = 0; i < nombreMois; i++){
            boolean existe = mensualiteRepository.existsByContratIdAndMoisAndAnnee(
                    contrat.getId(), current.getMonthValue(), current.getYear()
            );
            if(!existe){
                Mensualite mensualite = Mensualite.builder()
                        .mois(current.getMonthValue())
                        .annee(current.getYear())
                        .montantDu(contrat.getMontantMensuel())
                        .montantPaye(0.0)
                        .dateEcheance(current.withDayOfMonth(5))
                        .penalite(0.0)
                        .statut(StatutMensualite.EN_ATTENTE)
                        .contrat(contrat)
                        .build();

                mensualites.add(mensualite);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MensualiteDTO> getByContrat(Long contratId) {
        return mensualiteRepository.findByContratId(contratId)
                .stream()
                .map(MensualiteMapper::toDto)
                .toList();
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void generateMensualitesMensuelles() {

        List<Contrat> contrats = contratRepository
                .findByTypeContratAndTypeDureeContratAndActifTrue(
                        TypeContrat.LOCATION,
                        TypeDureeContrat.INDETERMINE
                );

        for (Contrat contrat : contrats) {

            LocalDate nextMonth = LocalDate.now().plusMonths(1);

            boolean existe = mensualiteRepository.existsByContratIdAndMoisAndAnnee(
                    contrat.getId(),
                    nextMonth.getMonthValue(),
                    nextMonth.getYear()
            );

            if (!existe) {

                Mensualite mensualite = Mensualite.builder()
                        .mois(nextMonth.getMonthValue())
                        .annee(nextMonth.getYear())
                        .montantDu(contrat.getMontantMensuel())
                        .montantPaye(0.0)
                        .dateEcheance(nextMonth.withDayOfMonth(5))
                        .statut(StatutMensualite.EN_ATTENTE)
                        .contrat(contrat)
                        .build();

                mensualiteRepository.save(mensualite);
            }
        }
    }
}
