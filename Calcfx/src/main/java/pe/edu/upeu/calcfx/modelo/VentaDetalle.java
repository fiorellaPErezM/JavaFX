package pe.edu.upeu.calcfx.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "venta_detalle")
public class VentaDetalle {
    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    Long idVentaDetalle;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    Venta venta;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    Producto producto;
}
