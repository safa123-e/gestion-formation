package iteam.tn.gestionformation.comp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;        // 1 = succès, 0 = erreur
     private T response;
}
