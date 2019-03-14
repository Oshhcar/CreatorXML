/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.Objeto;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Literal implements Expresion {

    private final Tipo tipo;
    private final Object valor;
    private final int linea;
    private final int columna;

    public Literal(Tipo tipo, Object valor, int linea, int columna) {
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Tipo getTipo(TablaSimbolo tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolo tabla, JTextArea salida) {
        switch (tipo) {
            case ARREGLO:
                if (valor != null) {
                    LinkedList<Expresion> arrActual = (LinkedList<Expresion>) valor;
                    Map<Integer, Object> valAsignar = new Arreglo();

                    for (int i = 0; i < arrActual.size(); i++) {
                        Expresion expActual = arrActual.get(i);
                        Object valActual = expActual.getValor(tabla, salida);
                        Tipo tipActual = expActual.getTipo(tabla);

                        valAsignar.put(i, "nulo");

                        if (tipActual != null) {
                            if (tipActual != Tipo.VAR) {
                                if (valActual != null) {
                                    valAsignar.put(i, valActual);
                                }
                            } else {
                                System.err.println("Error! Variable indefinida. Linea:" + linea);
                            }
                        }
                    }

                    return valAsignar;
                }
                return new Arreglo();
            case OBJETO:
                if (valor != null) {
                    Map<String, Expresion> actual = (Map<String, Expresion>) valor;
                    Map<String, Object> valores = new Objeto();

                    actual.keySet().forEach((claveActual) -> {
                        Expresion expActual = actual.get(claveActual);
                        Object valActual = expActual.getValor(tabla, salida);
                        Tipo tipActual = expActual.getTipo(tabla);

                        valores.put(claveActual, "nulo");

                        if (tipActual != null) {
                            if (tipActual != Tipo.VAR) {
                                if (valActual != null) {
                                    valores.put(claveActual, valActual);
                                }
                            } else {
                                System.err.println("Error! Variable indefinida. Linea:" + linea);
                            }
                        }
                    });

                    return valores;
                }
                return new Objeto();
            default:
                return valor;
        }
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
