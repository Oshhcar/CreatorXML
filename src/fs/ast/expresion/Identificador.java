/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Identificador implements Expresion {

    private final String id;
    private final int linea;
    private final int columna;

    public Identificador(String id, int linea, int columna) {
        this.id = id;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        Simbolo s = tabla.getSimbolo(id);
        return s != null ? s.getTipo() : null;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida) {
        Simbolo s = tabla.getSimbolo(id);
        if(s == null){
            System.err.println("Error, variable \""+id+"\" no encontrada. Línea: "+linea);
        }   
        
        return s != null ? s.getValor() : null;
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
