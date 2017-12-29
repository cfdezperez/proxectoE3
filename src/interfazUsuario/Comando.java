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
import excepciones.edificio.EdificioException;
import excepciones.edificio.NoNecRepararException;
import excepciones.personaje.EstarEnGrupoException;
import excepciones.personaje.InsuficientesRecException;
import excepciones.personaje.NoAgrupableException;
import excepciones.personaje.PersonajeLlenoException;
import excepciones.personaje.SolConstruirException;
import excepciones.personaje.SolRepararException;
import excepciones.personaje.SoldadoRecException;
import excepciones.recursos.RecursosException;

/**
 *
 * @author tomas
 */
public interface Comando {
    public String mover(String nPersonaje, String direccion, int distancia) throws EstarEnGrupoException, NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException;
    public String listar(String tipo) throws ParametroIncorrectoException;
    public String describir(String nombre) throws ParametroIncorrectoException;
    public String describir(String nombre, String Civilizacion) throws ParametroIncorrectoException;
    public String mirar(String coordenadasCelda) throws NumberFormatException, FueraDeMapaException;
    public void construir(String nPersonaje, String nEdificio, String direccion) throws EstarEnGrupoException, InsuficientesRecException, ParametroIncorrectoException, CeldaOcupadaException, FueraDeMapaException, CeldaEnemigaException, SolConstruirException;
    public void crear(String nEdificio, String tipoPersonaje);
    public void recolectar(String nPersonaje, String direccion) throws 
            EstarEnGrupoException, PersonajeLlenoException, SoldadoRecException, RecursosException, FueraDeMapaException, 
            ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException;
    public void almacenar(String nPersonaje, String direccion) throws EstarEnGrupoException, NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException;
    public void cambiarCivilizacion(String nCivilizacion) throws ParametroIncorrectoException;
    public void imprimirCivilizacion() throws ParametroIncorrectoException;
    public void imprimirCivilizacion(String nCivilizacion) throws ParametroIncorrectoException;
    public String agrupar(String coordenadasCelda) throws NumberFormatException, FueraDeMapaException, 
            ParametroIncorrectoException, NoAgrupableException, CeldaEnemigaException;
    public void desligar(String nPersonaje, String nGrupo);
    public void desagrupar(String nGrupo);
    public void reparar(String nPersonaje, String direccion) throws EstarEnGrupoException, SolRepararException, FueraDeMapaException, 
            ParametroIncorrectoException, NoNecRepararException, InsuficientesRecException, EdificioException;    
    public void defender(String nPersonaje, String direccion) throws EstarEnGrupoException;
    public void atacar(String nPersonaje, String direccion) throws EstarEnGrupoException;
}
