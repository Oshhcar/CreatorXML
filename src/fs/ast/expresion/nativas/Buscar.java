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
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Buscar implements Expresion {

    private final Object array;
    private final LinkedList<Expresion> parametros;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public Buscar(Object array, LinkedList<Expresion> parametros, int linea, int columna) {
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
    public Object getValor(TablaSimbolos tabla, JTextArea salida) {
        if (array instanceof Arreglo) {
            if (parametros.size() == 1) {
                Expresion ident = parametros.getFirst();
                if (ident instanceof Identificador) {
                    String id = ((Identificador) ident).getId();
                    FuncionSim fun = tabla.getFuncion(id, 1);
                    if (fun != null) {
                        Map<Integer, Object> arreglo = (Map<Integer, Object>) array;
                        if (arreglo != null) {
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
                                Literal lit = new Literal(tipExp, exp, linea, columna);
                                LinkedList<Expresion> parms = new LinkedList<>();
                                parms.add(lit);

                                LlamadaFuncion llamada = new LlamadaFuncion(id, parms, linea, columna);
                                Object ret = llamada.getValor(tabla, salida);
                                Tipo tipRet = llamada.getTipo(tabla);

                                if (tipRet != null) {
                                    if (tipRet == Tipo.BOOLEANO) {
                                        if (ret.toString().equals("verdadero")) {
                                            tipo = tipExp;
                                            return exp;
                                        }
                                    }
                                }

                            }
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
                System.err.println("Error, la funcion filtrar solo recibe un parametro. Linea: " + linea);
            }
        } else {
            System.err.println("Error, se necesita un arreglo para ejecutar filtrar. Línea:" + linea);
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
