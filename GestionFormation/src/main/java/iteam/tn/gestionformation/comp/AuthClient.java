package iteam.tn.gestionformation.comp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;


@FeignClient(
        name = "auth-service",
        url = "http://localhost:8082" // ⚠️ port du microservice auth
)
public interface AuthClient {

    @GetMapping("agents/idConnecte")
    Map<String, Object> getUserIdConnecte(@RequestHeader("Authorization") String token);
}



