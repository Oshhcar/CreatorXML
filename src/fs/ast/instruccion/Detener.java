/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.TablaSimbolos;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Detener implements Instruccion{
    private final int linea;
    private final int columna;

    public Detener(int linea, int columna) {
        this.linea = linea;
        this.columna = columna;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel, String dirActual) {
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
}
