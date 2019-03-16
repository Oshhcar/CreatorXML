/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Imprimir implements Instruccion {

    private final Expresion exp;
    private int linea;
    private int columna;

    public Imprimir(Expresion exp, int linea, int columna) {
        this.exp = exp;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida) {
        Object valExp = exp.getValor(tabla, salida);
        Object tipExp = exp.getTipo(tabla);

        if (tipExp != null) {
            if (tipExp != Tipo.VAR) {
                if (tipExp != Tipo.OBJETO) {
                    if (tipExp != Tipo.ARREGLO) {
                        if (valExp != null) {
                            //System.out.println(String.valueOf(valExp));
                            salida.append(String.valueOf(valExp) + "\n");
                        }
                    } else {
                        System.err.println("Error! Variable de tipo arreglo. Linea:" + linea);
                    }
                } else {
                    System.err.println("Error! Variable de tipo objeto. Linea:" + linea);
                }
            } else {
                System.err.println("Error! Variable indefinida. Linea:" + linea);
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

}
