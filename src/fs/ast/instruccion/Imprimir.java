/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.TablaSimbolo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Imprimir implements Instruccion{
    private final Expresion exp;
    private int linea;
    private int columna;
    
    public Imprimir(Expresion exp, int linea, int columna) {
        this.exp = exp;
        this.linea = linea;
        this.columna = columna;
    }

    
    
    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        Object valExp = exp.getValor(tabla, salida);
        if(valExp != null){
            System.out.println(String.valueOf(valExp));
            salida.append(String.valueOf(valExp)+"\n");
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
    
}
