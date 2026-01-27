package iteam.tn.gestionformation.Repository;

import iteam.tn.gestionformation.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository
        extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findByParticipationId(Long participationId);

    boolean existsByParticipationId(Long participationId);
}

