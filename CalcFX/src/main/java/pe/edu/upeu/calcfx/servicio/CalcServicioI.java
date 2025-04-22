package pe.edu.upeu.calcfx.servicio;

import pe.edu.upeu.calcfx.modelo.CalcTO;

import java.util.List;

public interface CalcServicioI {
    //C
    public void save(CalcTO calcTO);
    //R
    public List<CalcTO> findAll();
    public CalcTO findById(int index);
    //
    public void update(CalcTO calcTO, int index);
    public void delete(CalcTO calcTO);
    public void deleteById(int index);


}
