package iteam.tn.gestionformation.service;

import iteam.tn.gestionformation.Repository.FeedbackRepository;
import iteam.tn.gestionformation.Repository.ParticipationRepository;
import iteam.tn.gestionformation.model.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ParticipationRepository participationRepository;
    private final FeedbackRepository feedbackRepository;

    public long nombreParticipantsParService(Long serviceId) {
        return participationRepository.findByServiceId(serviceId).size();
    }

    public double satisfactionMoyenne() {
        return feedbackRepository.findAll()
                .stream()
                .mapToInt(Feedback::getNote)
                .average()
                .orElse(0.0);
    }
}

