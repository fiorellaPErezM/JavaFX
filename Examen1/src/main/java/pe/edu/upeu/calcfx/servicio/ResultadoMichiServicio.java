package pe.edu.upeu.calcfx.servicio;

import pe.edu.upeu.calcfx.modelo.ResultadoMichi;

import java.util.List;

public interface ResultadoMichiServicio {

    void save(ResultadoMichi resultadoMichi);

    List<ResultadoMichi> findAll();

}
