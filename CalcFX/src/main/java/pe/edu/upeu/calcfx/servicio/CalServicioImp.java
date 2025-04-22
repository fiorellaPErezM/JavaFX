package pe.edu.upeu.calcfx.servicio;

import org.springframework.stereotype.Service;
import pe.edu.upeu.calcfx.modelo.CalcTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalServicioImp implements CalcServicioI {

    List<CalcTO> datos=new ArrayList<>();


    @Override
    public void  save(CalcTO calcT0) {
         datos.add(calcT0);

    }

    @Override
    public List<CalcTO> findAll() {
        return datos;
    }

    @Override
    public CalcTO findById(int index) {
        return datos.get(index);
    }

    @Override
    public void update(CalcTO calcT0, int index) {
    datos.set(index, calcT0);
    }


    @Override
    public void delete(CalcTO calcT0) {
        datos.remove(calcT0);

    }

    @Override
    public void deleteById(int index) {
        datos.remove(index);

    }
}
