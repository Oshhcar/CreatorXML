/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Declaracion implements Instruccion {

    private final LinkedList<Asignacion> asignaciones;
    private final int linea;
    private final int columna;

    public Declaracion(LinkedList<Asignacion> asignaciones, int linea, int columna) {
        this.asignaciones = asignaciones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        getAsignaciones().forEach((asignacion) -> {
            Simbolo sim = new Simbolo(asignacion.getTipo(), asignacion.getId());
            tabla.add(sim);
            asignacion.ejecutar(tabla, salida);
        });
        return null;
    }

    @Override
    public int getLinea() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumna() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the asignaciones
     */
    public LinkedList<Asignacion> getAsignaciones() {
        return asignaciones;
    }

}
