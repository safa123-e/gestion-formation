package iteam.tn.gestionformation.service;

import iteam.tn.gestionformation.Repository.AffectationFormationServiceRepository;
import iteam.tn.gestionformation.model.AffectationFormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final AffectationFormationServiceRepository affectationRepo;

    public List<AffectationFormationService> formationsParService(Long serviceId) {
        return affectationRepo.findByServiceId(serviceId);
    }
}

