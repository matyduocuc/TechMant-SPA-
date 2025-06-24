import com.example.gestion_de_servicios.model.Solicitud;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolicitudConUsuarioDTO {
    private Solicitud solicitud;
    private UsuarioResponseDTO usuario;
}