package pe.edu.upeu.calcfx.servicio;

import org.springframework.stereotype.Service;
import pe.edu.upeu.calcfx.modelo.ResultadoMichi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ResultadoMichiServicioImp implements ResultadoMichiServicio {

    private final List<ResultadoMichi> datos = new ArrayList<>();


    @Override
    public void save(ResultadoMichi resultadoMichi) {

        if (resultadoMichi != null) {

            datos.add(resultadoMichi);
        }
    }


    @Override
    public List<ResultadoMichi> findAll() {

        return Collections.unmodifiableList(datos);
    }

}

