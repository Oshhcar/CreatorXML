/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.TablaSimbolos;
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
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel) {
        getAsignaciones().forEach((asignacion) -> {
            if (!tabla.existeLocal(asignacion.getId())) {
                Simbolo sim = new Simbolo(Tipo.VAR, asignacion.getId());
                tabla.addSimbolo(sim);
                asignacion.ejecutar(tabla, salida, fun, sel);
            } else {
                System.err.println("Error, Ya existe una variable con el nombre de \"" + asignacion.getId() + "\". Línea: " + linea);
            }
        });
        return null;
    }

    @Override
    public int getLinea() {
        return linea;
    }

    @Override
    public int getColumna() {
        return columna;
    }

    /**
     * @return the asignaciones
     */
    public LinkedList<Asignacion> getAsignaciones() {
        return asignaciones;
    }

}
