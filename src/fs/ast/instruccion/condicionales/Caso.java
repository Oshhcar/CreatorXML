/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion.condicionales;

import fs.ast.NodoAST;
import fs.ast.expresion.Expresion;
import fs.ast.instruccion.Detener;
import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Caso implements Instruccion {

    private final Expresion expresion;
    private Expresion expSwitch;
    private final LinkedList<NodoAST> bloques;
    private final int linea;
    private final int columna;
    private final boolean isDefecto;
    private boolean continuar;

    public Caso(LinkedList<NodoAST> bloques, int linea, int columna) {
        this.expresion = null;
        this.expSwitch = null;
        this.bloques = bloques;
        this.linea = linea;
        this.columna = columna;
        this.isDefecto = true;
        this.continuar = false;
    }

    public Caso(Expresion expresion, LinkedList<NodoAST> bloques, int linea, int columna) {
        this.expresion = expresion;
        this.bloques = bloques;
        this.linea = linea;
        this.columna = columna;
        this.isDefecto = false;
        this.continuar = false;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        if (isContinuar()) {
            for (NodoAST bloque : bloques) {
                if (bloque instanceof Instruccion) {
                    if (bloque instanceof Detener) {
                        this.continuar = false;
                    } else {
                        ((Instruccion) bloque).ejecutar(tabla, salida);
                    }
                }
            }
        } else {
            if (!isDefecto) {
                if (expSwitch != null) {
                    Object valExpSwitch = expSwitch.getValor(tabla);
                    Object valExpresion = expresion.getValor(tabla);
                    Object tipExpSwitch = expSwitch.getTipo(tabla);
                    Object tipExpresion = expresion.getTipo(tabla);

                    if (tipExpSwitch != null && tipExpresion != null) {
                        if (tipExpSwitch == tipExpresion) {
                            if (valExpresion.equals(valExpSwitch)) {
                                this.continuar = true;

                                for (NodoAST bloque : bloques) {
                                    if (bloque instanceof Instruccion) {
                                        if (bloque instanceof Detener) {
                                            this.continuar = false;
                                        } else {
                                            ((Instruccion) bloque).ejecutar(tabla, salida);
                                        }
                                    }
                                }

                                return null;
                            }
                        } else {
                            //System.err.println("Error, las expresiones no son del mismo tipo. Linea:"+linea);
                        }
                    }

                }

            } else {
                for (NodoAST bloque : bloques) {
                    if (bloque instanceof Instruccion) {
                        ((Instruccion) bloque).ejecutar(tabla, salida);
                    }
                }
                return null;
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
     * @param expSwitch the expSwitch to set
     */
    public void setExpSwitch(Expresion expSwitch) {
        this.expSwitch = expSwitch;
    }

    /**
     * @param continuar the continuar to set
     */
    public void setContinuar(boolean continuar) {
        this.continuar = continuar;
    }

    /**
     * @return the continuar
     */
    public boolean isContinuar() {
        return continuar;
    }

}
