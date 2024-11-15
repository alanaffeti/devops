package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

import java.util.List;
@AllArgsConstructor
@Service
public class PisteServicesImpl implements  IPisteServices{

    private IPisteRepository pisteRepository;

    @Override
    public List<Piste> retrieveAllPistes() {
        return pisteRepository.findAll();
    }

    @Override
    public Piste addPiste(Piste piste) {
        return pisteRepository.save(piste);
    }

    @Override
    public void removePiste(Long numPiste) {
        pisteRepository.deleteById(numPiste);
    }

    @Override
    public Piste retrievePiste(Long numPiste) {
        return pisteRepository.findById(numPiste).orElse(null);
    }

    public Piste updatePiste(Long id, Piste newPisteData) {
        return pisteRepository.findById(id)
                .map(piste -> {
                    piste.setNamePiste(newPisteData.getNamePiste());
                    piste.setLength(newPisteData.getLength());
                    piste.setSlope(newPisteData.getSlope());
                    return pisteRepository.save(piste);
                })
                .orElse(null);
    }
    @Override
    public List<Piste> retrievePistesByColor(Color color) {
        return pisteRepository.findByColor(color);
    }

    @Override
    public double calculateAverageSlope() {
        List<Piste> pistes = pisteRepository.findAll();
        return pistes.stream()
                .mapToInt(Piste::getSlope)
                .average()
                .orElse(0.0);
    }
}
