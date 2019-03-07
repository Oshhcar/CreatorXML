/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion.condicionales;

import fs.ast.NodoAST;
import fs.ast.expresion.Expresion;
import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class SubSi implements Instruccion {

    private final Expresion condicion;
    private final LinkedList<NodoAST> bloques;
    private final boolean isSino;
    private final int linea;
    private final int columna;

    public SubSi(Expresion condicion, LinkedList<NodoAST> bloques, int linea, int columna) {
        this.condicion = condicion;
        this.bloques = bloques;
        this.isSino = false;
        this.linea = linea;
        this.columna = columna;
    }

    public SubSi(LinkedList<NodoAST> bloques, int linea, int columna) {
        this.condicion = null;
        this.bloques = bloques;
        this.isSino = true;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        if (!isSino) {
            Object valorCondicion = condicion.getValor(tabla);
            Object tipoCondicion = condicion.getTipo(tabla);

            if (tipoCondicion != null) {
                if (tipoCondicion == Tipo.BOOLEANO) {
                    boolean cond = valorCondicion.equals("verdadero");

                    if (cond) {
                        for (NodoAST bloque : bloques) {
                            if (bloque instanceof Instruccion) {
                                ((Instruccion) bloque).ejecutar(tabla, salida);
                            }
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    System.err.println("Error, la condicion debe ser booleana. Linea:" + linea);
                }
            }
        } else {
            for (NodoAST bloque : bloques) {
                if (bloque instanceof Instruccion) {
                    ((Instruccion) bloque).ejecutar(tabla, salida);
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

}
