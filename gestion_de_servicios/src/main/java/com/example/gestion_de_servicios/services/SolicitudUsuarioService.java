import com.example.gestion_de_servicios.dto.SolicitudConUsuarioDTO;
import com.example.gestion_de_servicios.dto.UsuarioResponseDTO;
import com.example.gestion_de_servicios.model.Solicitud;
import com.example.gestion_de_servicios.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SolicitudUsuarioService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_USUARIOS = "http://localhost:8081/usuarios";

    public List<SolicitudConUsuarioDTO> listarSolicitudesConUsuario() {
        List<Solicitud> solicitudes = solicitudRepository.findAll();
        List<SolicitudConUsuarioDTO> resultado = new ArrayList<>();

        for (Solicitud s : solicitudes) {
            UsuarioResponseDTO usuario = null;
            if (s.getClienteId() != null) {
                usuario = restTemplate.getForObject(URL_USUARIOS + "/" + s.getClienteId(), UsuarioResponseDTO.class);
            }
            resultado.add(new SolicitudConUsuarioDTO(s, usuario));
        }

        return resultado;
    }
}
