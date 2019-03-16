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
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel) {
        FuncionSim funcion;

        if (this.parametros != null) {
            funcion = tabla.getFuncion(id, this.parametros.size());
        } else {
            funcion = tabla.getFuncion(id);
        }

        if (funcion != null) {
            tabla.nuevoAmbito();
            if (this.getParametros() != null && funcion.getParametros() != null) {
                for (int i = 0; i < this.getParametros().size(); i++) {
                    String idActual = funcion.getParametros().get(i);
                    Simbolo sim = new Simbolo(Tipo.VAR, idActual);

                    Expresion expActual = this.getParametros().get(i);
                    Object valActual = expActual.getValor(tabla, salida);
                    Tipo tipActual = expActual.getTipo(tabla);

                    if (tipActual != null) {
                        if (valActual != null) {
                            sim.setValor(valActual);
                        } else {
                            sim.setTipo(tipActual);
                            sim.setValor(valActual);
                        }
                    } else {
                        System.err.println("Error, no se puede asignar el parametro. Linea:" + linea);
                        return null;
                    }
                    tabla.addSimbolo(sim);
                }
            }

            for (NodoAST bloque : funcion.getBloques()) {
                if (bloque instanceof Instruccion) {
                    Object o = ((Instruccion) bloque).ejecutar(tabla, salida, true, false);
                    if (o != null) {
                        if (o instanceof Literal) {
                            tabla.salirAmbito();
                            return null;
                        }
                    }
                } else {
                    if (bloque instanceof Retornar) {
                        tabla.salirAmbito();
                        return null;
                    }
                }
            }
            tabla.salirAmbito();
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
