/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.cr;

import elementos.ContRecurso;
import elementos.Recurso;
import excepciones.recursos.NoProcesableException;
import excepciones.recursos.RecursosException;
import interfazUsuario.Juego;

/**
 *
 * @author celia y maria
 */
public class Cantera extends ContRecurso {

    public Cantera(Recurso rec) {
        super(rec);
    }

    @Override
    public String toString() {
        return ("\tContenedor de recursos de tipo Cantera\n\tCantidad de piedra " + this.getRecurso().getCapacidad());
    }

    @Override
    public Recurso procesar() throws NoProcesableException, RecursosException {
        double max = this.getCapInicial();
        double cap = this.getRecurso().getCapacidad();
        Recurso r = new Recurso(Juego.TCANTERA, cap);

        for (int i = 1; i <= 5; i++) {
            if (cap < (max - 0.2 * i * max)) {
                r.setCapacidad(0.9 * r.getCapacidad());
            }
        }

        if (r.getCapacidad() == 0) {
            r.setCapacidad(1);
        }

        return r;
    }

}
