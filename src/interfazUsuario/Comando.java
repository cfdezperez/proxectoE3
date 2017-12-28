/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.celda.FueraDeMapaException;
import excepciones.celda.NoTransitablebleException;
import excepciones.ParametroIncorrectoException;
import excepciones.personaje.InsuficientesRecException;
import excepciones.personaje.PersonajeLlenoException;
import excepciones.personaje.SolConstruirException;
import excepciones.personaje.SoldadoRecException;
import excepciones.recursos.RecursosException;

/**
 *
 * @author tomas
 */
public interface Comando {
    public void mover(String nPersonaje, String direccion) throws NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException;
    public void listar(String tipo) throws ParametroIncorrectoException;
    public String describir(String nombre) throws ParametroIncorrectoException;
    public String describir(String nombre, String Civilizacion) throws ParametroIncorrectoException;
    public String mirar(String coordenadasCelda) throws NumberFormatException, FueraDeMapaException;
    public void construir(String nPersonaje, String nEdificio, String direccion) throws InsuficientesRecException, ParametroIncorrectoException, CeldaOcupadaException, FueraDeMapaException, CeldaEnemigaException, SolConstruirException;
    public void crear(String nEdificio, String tipoPersonaje);
    public void recolectar(String nPersonaje, String direccion) throws 
            PersonajeLlenoException, SoldadoRecException, RecursosException, FueraDeMapaException, 
            ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException;
    public void almacenar(String nPersonaje, String direccion) throws NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException;
    public void cambiarCivilizacion(String nCivilizacion) throws ParametroIncorrectoException;
    public void imprimirCivilizacion() throws ParametroIncorrectoException;
    public void imprimirCivilizacion(String nCivilizacion) throws ParametroIncorrectoException;
    public void agrupar(String coordenadasCelda) throws NumberFormatException, FueraDeMapaException;
    public void desligar(String nPersonaje, String nGrupo);
    public void desagrupar(String nGrupo);
    public void reparar(String nPersonaje, String direccion);    
    public void defender(String nPersonaje, String direccion);
    public void atacar(String nPersonaje, String direccion);
}
