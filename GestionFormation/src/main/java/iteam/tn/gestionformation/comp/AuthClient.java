package iteam.tn.gestionformation.comp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;



@FeignClient(
        name = "auth-service",
        url = "http://localhost:8081" // ⚠️ port du microservice auth
)
public interface AuthClient {

    @PostMapping("/idConnecte")
    Integer getUserIdConnecte();
}



