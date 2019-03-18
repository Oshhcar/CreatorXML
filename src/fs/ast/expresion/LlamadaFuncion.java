/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.NodoAST;
import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.FuncionSim;
import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.Simbolos;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class LlamadaFuncion implements Expresion {

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

    private final String id;
    private final LinkedList<Expresion> parametros;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public LlamadaFuncion(String id, LinkedList<Expresion> parametros, int linea, int columna) {
        this.id = id;
        this.parametros = parametros;
        this.linea = linea;
        this.columna = columna;
        this.tipo = null;
    }

    public LlamadaFuncion(String id, int linea, int columna) {
        this.id = id;
        this.parametros = null;
        this.linea = linea;
        this.columna = columna;
        this.tipo = null;
    }

    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida) {
        FuncionSim fun;

        if (this.parametros != null) {
            fun = tabla.getFuncion(id, this.parametros.size());
        } else {
            fun = tabla.getFuncion(id);
        }

        if (fun != null) {
            Simbolos local = new Simbolos();
            if (this.getParametros() != null && fun.getParametros() != null) {
                for (int i = 0; i < this.getParametros().size(); i++) {
                    String idActual = fun.getParametros().get(i);
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
                    local.add(sim);
                }
            }
            tabla.add(local);

            for (NodoAST bloque : fun.getBloques()) {
                if (bloque instanceof Instruccion) {
                    Object o = ((Instruccion) bloque).ejecutar(tabla, salida, true, false);
                    if (o != null) {
                        if (o instanceof Literal) {
                            Literal lit = (Literal) o;
                            Object litVal = ((Literal) o).getValor(tabla, salida);
                            tipo = lit.getTipo(tabla);
                            if (litVal == null) {
                                System.err.println("Error, la funcion \"" + id + "\" no retorna valor. Línea: " + linea);

                            }
                            tabla.pollLast();
                            return litVal;
                        }
                    }
                } else {
                    if (bloque instanceof Retornar) {
                        Retornar ret = (Retornar) bloque;
                        Object valRet = ret.getValor(tabla, salida);
                        tipo = ret.getTipo(tabla);
                        if (valRet == null) {
                            System.err.println("Error, la funcion \"" + id + "\" no retorna valor. Línea: " + linea);
                        }
                        tabla.pollLast();
                        return valRet;
                    }
                }
            }
            tabla.pollLast();
            System.err.println("Error, la funcion \"" + id + "\" no retorna valor. Línea: " + linea);
        } else {
            System.err.println("Error, la funcion \"" + id + "\" no está declarada. Línea: " + linea);
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
