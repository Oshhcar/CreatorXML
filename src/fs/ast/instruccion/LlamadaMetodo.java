/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.NodoAST;
import fs.ast.expresion.Expresion;
import fs.ast.expresion.Literal;
import fs.ast.expresion.Retornar;
import fs.ast.simbolos.FuncionSim;
import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class LlamadaMetodo implements Instruccion {

    private final String id;
    private final LinkedList<Expresion> parametros;
    private final int linea;
    private final int columna;

    public LlamadaMetodo(String id, LinkedList<Expresion> parametros, int linea, int columna) {
        this.id = id;
        this.parametros = parametros;
        this.linea = linea;
        this.columna = columna;
    }

    public LlamadaMetodo(String id, int linea, int columna) {
        this.id = id;
        this.parametros = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida) {
        FuncionSim fun = tabla.getFuncion(getId());
        if (fun != null) {
            if (this.getParametros() != null && fun.getParametros() != null) {
                if (this.getParametros().size() == fun.getParametros().size()) {
                    TablaSimbolos local = new TablaSimbolos();
                    local.addAll(tabla);

                    for (int i = 0; i < this.getParametros().size(); i++) {
                        String idActual = fun.getParametros().get(i);
                        Simbolo sim = new Simbolo(Tipo.VAR, idActual);
                        local.addSimbolo(sim);
                        Expresion expActual = this.getParametros().get(i);
                        Object valActual = expActual.getValor(tabla, salida);
                        Tipo tipActual = expActual.getTipo(tabla);

                        if (tipActual != null && valActual != null) {
                            sim.setValor(valActual);
                            local.addSimbolo(sim);
                        } else {
                            System.err.println("Error, no se puede asignar el parametro. Linea:" + linea);
                            return null;
                        }

                    }

                    for (NodoAST bloque : fun.getBloques()) {
                        if (bloque instanceof Instruccion) {
                            Object o = ((Instruccion) bloque).ejecutar(local, salida);
                            
                        } else {
                            if (bloque instanceof Retornar) {
                                return null;
                            }
                        }
                    }

                } else {
                    System.err.println("Error, los parametros no son los mismos en la funcion. Linea:" + linea);
                }
            } else {
                TablaSimbolos local = new TablaSimbolos();
                local.addAll(tabla);

                for (NodoAST bloque : fun.getBloques()) {
                    if (bloque instanceof Instruccion) {
                        Object o = ((Instruccion) bloque).ejecutar(local, salida);
                        if (o != null) {
                        }
                    } else {
                        if (bloque instanceof Retornar) {
                            return null;
                        }
                    }
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

    /**
     * @return the parametros
     */
    public LinkedList<Expresion> getParametros() {
        return parametros;
    }

}
