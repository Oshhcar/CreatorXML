/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.expresion.Literal;
import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import fs.ast.simbolos.Objeto;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Asignacion implements Instruccion {

    protected final String id;
    protected final Expresion valor;
    private final int linea;
    private final int columna;

    public Asignacion(String id, Expresion valor, int linea, int columna) {
        this.id = id;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    public Asignacion(String id, int linea, int columna) {
        this.id = id;
        this.valor = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        if (null != valor) {
            Object val = valor.getValor(tabla, salida);
            Tipo tip = valor.getTipo(tabla);
            if (tip != null) {
                if (tip != Tipo.VAR) {
                    if (val != null) {
                        tabla.setValor(getId(), val);
                    }
                } else {
                    System.err.println("Error, Variable indefinida. Linea:" + linea);
                }
            }
        }
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
     * @return the id
     */
    public String getId() {
        return id;
    }
}
