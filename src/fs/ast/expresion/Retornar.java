/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Retornar implements Expresion{
    private final Expresion expresion;
    private final int linea;
    private final int columna;

    public Retornar(Expresion expresion, int linea, int columna) {
        this.expresion = expresion;
        this.linea = linea;
        this.columna = columna;
    }
    
    @Override
    public Tipo getTipo(TablaSimbolo tabla) {
        return expresion.getTipo(tabla);
    }

    @Override
    public Object getValor(TablaSimbolo tabla, JTextArea salida) {
        return expresion.getValor(tabla, salida);
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
