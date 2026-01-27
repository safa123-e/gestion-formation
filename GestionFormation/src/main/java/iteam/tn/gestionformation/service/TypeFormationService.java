package iteam.tn.gestionformation.service;

import iteam.tn.gestionformation.Repository.TypeFormationRepository;
import iteam.tn.gestionformation.model.TypeFormation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeFormationService {

    private final TypeFormationRepository repository;

    public TypeFormation creerType(String nom, String description) {
        if (repository.existsByNom(nom)) {
            throw new RuntimeException("Type déjà existant");
        }
        return repository.save(
                TypeFormation.builder()
                        .nom(nom)
                        .description(description)
                        .build()
        );
    }

    public List<TypeFormation> listerTypes() {
        return repository.findAll();
    }
}
