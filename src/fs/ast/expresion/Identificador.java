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
public class Identificador implements Expresion{
    private String id;
    private final int linea;
    private final int columna;

    public Identificador(String id, int linea, int columna) {
        this.id = id;
        this.linea = linea;
        this.columna = columna;
    }

    
    @Override
    public Tipo getTipo(TablaSimbolo tabla) {
       return tabla.getTipo(id);
    }

    @Override
    public Object getValor(TablaSimbolo tabla, JTextArea salida) {
        return tabla.getValor(id);
    }

    @Override
    public int getLinea() {
        return linea;
    }

    @Override
    public int getColumna() {
        return columna;
    }
    
    public String getId() {
        return id;
    }
}
