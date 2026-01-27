package iteam.tn.gestionformation.service;

import iteam.tn.gestionformation.Repository.FeedbackRepository;
import iteam.tn.gestionformation.Repository.ParticipationRepository;
import iteam.tn.gestionformation.model.Feedback;
import iteam.tn.gestionformation.model.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository repository;
    private final ParticipationRepository participationRepository;

    public Feedback ajouterFeedback(Long participationId, Integer note, String commentaire) {

        if (repository.existsByParticipationId(participationId)) {
            throw new RuntimeException("Feedback déjà soumis");
        }

        Participation p = participationRepository.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation introuvable"));

        Feedback f = Feedback.builder()
                .participation(p)
                .note(note)
                .commentaire(commentaire)
                .dateCreation(LocalDateTime.now())
                .build();

        return repository.save(f);
    }
}