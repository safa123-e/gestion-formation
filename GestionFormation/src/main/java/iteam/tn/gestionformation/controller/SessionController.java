package iteam.tn.gestionformation.controller;

import iteam.tn.gestionformation.Dto.SessionCreateDto;
import iteam.tn.gestionformation.comp.ApiResponse;
import iteam.tn.gestionformation.model.SessionFormation;
import iteam.tn.gestionformation.service.SessionFormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionFormationService sessionService;

    @PostMapping
    public ApiResponse<SessionFormation> creerSession(
            @RequestBody SessionCreateDto dto) {

        try {
            SessionFormation session = sessionService.creerSession(dto);

            return
                    new ApiResponse<>(1, session);


        } catch (Exception e) {
            return
                    new ApiResponse<>(0, null);
        }
    }

        @GetMapping("/notifications")
        public List<SessionFormation> getNotifications(@RequestHeader("Authorization") String authHeader) {
            return sessionService.getNotifications(authHeader);
        }
}
