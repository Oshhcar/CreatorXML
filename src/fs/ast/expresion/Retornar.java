/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Retornar implements Expresion {

    private final Expresion expresion;
    private final int linea;
    private final int columna;

    public Retornar(Expresion expresion, int linea, int columna) {
        this.expresion = expresion;
        this.linea = linea;
        this.columna = columna;
    }

    public Retornar(int linea, int columna) {
        this.expresion = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        return expresion != null ? expresion.getTipo(tabla) : null;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida, String dirActual) {
        return expresion != null ? expresion.getValor(tabla, salida, dirActual) : null;
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
