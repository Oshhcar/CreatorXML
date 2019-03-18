/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.nativas;

import fs.ast.expresion.Expresion;
import fs.ast.expresion.Identificador;
import fs.ast.expresion.Literal;
import fs.ast.expresion.LlamadaFuncion;
import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.FuncionSim;
import fs.ast.simbolos.Objeto;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Reduce implements Expresion {

    private final Object array;
    private final LinkedList<Expresion> parametros;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public Reduce(Object array, LinkedList<Expresion> parametros, int linea, int columna) {
        this.array = array;
        this.parametros = parametros;
        this.tipo = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida, String dirActual) {
        if (array instanceof Arreglo) {
            if (parametros.size() == 1) {
                Expresion ident = parametros.getFirst();
                if (ident instanceof Identificador) {
                    String id = ((Identificador) ident).getId();
                    FuncionSim fun = tabla.getFuncion(id, 2);
                    if (fun != null) {
                        java.util.Map<Integer, Object> arreglo = (java.util.Map<Integer, Object>) array;
                        if (arreglo != null) {
                            tipo = Tipo.NULL;
                            Object reduce = "nulo";

                            Object exp0 = arreglo.get(0);
                            if (exp0 != null) {
                                if (exp0 instanceof Double) {
                                    tipo = Tipo.DECIMAL;
                                    reduce = 0.0;
                                } else if (exp0 instanceof Integer) {
                                    tipo = Tipo.ENTERO;
                                    reduce = 0;
                                } else if (exp0.equals("verdadero") || exp0.equals("falso")) {
                                    tipo = Tipo.BOOLEANO;
                                    reduce = "falso";
                                } else if (exp0.equals("nulo")) {
                                    tipo = Tipo.NULL;
                                    reduce = "nulo";
                                } else if (exp0 instanceof String) {
                                    tipo = Tipo.CADENA;
                                    reduce = "";
                                } else if (exp0 instanceof Objeto) {
                                    tipo = Tipo.OBJETO;
                                    reduce = new Objeto();
                                } else if (exp0 instanceof Arreglo) {
                                    tipo = Tipo.ARREGLO;
                                    reduce = new Arreglo();
                                }
                            }

                            for (int i = 0; i < arreglo.size(); i++) {
                                Object exp = arreglo.get(i);
                                Tipo tipExp = null;

                                if (exp instanceof Double) {
                                    tipExp = Tipo.DECIMAL;
                                } else if (exp instanceof Integer) {
                                    tipExp = Tipo.ENTERO;
                                } else if (exp.equals("verdadero") || exp.equals("falso")) {
                                    tipExp = Tipo.BOOLEANO;
                                } else if (exp.equals("nulo")) {
                                    tipExp = Tipo.NULL;
                                } else if (exp instanceof String) {
                                    tipExp = Tipo.CADENA;
                                } else if (exp instanceof Objeto) {
                                    tipExp = Tipo.OBJETO;
                                } else if (exp instanceof Arreglo) {
                                    tipExp = Tipo.ARREGLO;
                                }
                                Literal total = new Literal(tipo, reduce, linea, columna);
                                total.setSetearValor(false);
                                Literal lit = new Literal(tipExp, exp, linea, columna);
                                lit.setSetearValor(false);
                                LinkedList<Expresion> parms = new LinkedList<>();
                                parms.add(total);
                                parms.add(lit);

                                LlamadaFuncion llamada = new LlamadaFuncion(id, parms, linea, columna);
                                llamada.setMostrarError(false);
                                Object ret = llamada.getValor(tabla, salida, dirActual);
                                Tipo tipRet = llamada.getTipo(tabla);

                                if (tipRet != null) {
                                    if (ret != null) {
                                        tipo = tipRet;
                                        reduce = ret;
                                    }
                                }

                            }
                            return reduce;
                        } else {
                            System.err.println("Error, arreglo indefinido. Línea:" + linea);
                        }
                    } else {
                        System.err.println("Error, la funcion \"" + id + "\" no está declarada. Línea: " + linea);

                    }
                } else {
                    System.err.println("Error, el parametro debe ser el id de la funcion. Linea: " + linea);
                }
            } else {
                System.err.println("Error, la funcion reduce solo recibe un parametro. Linea: " + linea);
            }
        } else {
            System.err.println("Error, se necesita un arreglo para ejecutar reduce. Línea:" + linea);
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