/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.expresion.Literal;
import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import fs.ast.simbolos.Objeto;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Asignacion implements Instruccion {

    protected final String id;
    protected final Expresion valor;
    private final int linea;
    private final int columna;

    public Asignacion(String id, Expresion valor, int linea, int columna) {
        this.id = id;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    public Asignacion(String id, int linea, int columna) {
        this.id = id;
        this.valor = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {

        if (null != valor) {
            Object val = valor.getValor(tabla, salida);
            Tipo tip = valor.getTipo(tabla);
            if (tip != null) {
                switch (tip) {
                    case OBJETO:
                        if (val != null) {
                            if (!(val instanceof Objeto)) {
                                Map<String, Expresion> actual = (Map<String, Expresion>) val;
                                Map<String, Object> valores = new Objeto();

                                actual.keySet().forEach((claveActual) -> {
                                    Expresion expActual = actual.get(claveActual);
                                    Object valActual = expActual.getValor(tabla, salida);
                                    if (valActual != null) {
                                        valores.put(claveActual, valActual);
                                    }
                                });

                                if (valores.size() > 0) {
                                    tabla.setValor(id, valores);
                                }
                            } else {
                                tabla.setValor(id, val);
                            }
                        } else {
                            if (valor instanceof Literal) {
                                tabla.setTipo(id, tip);
                                tabla.setValor(id, new Objeto());
                            } else {
                                System.err.println("Error, Objeto indefinido. Linea:" + linea);
                            }
                        }
                        break;
                    case ARREGLO:
                        if (val != null) {
                            if (!(val instanceof Arreglo)) {
                                LinkedList<Expresion> arrActual = (LinkedList<Expresion>) val;
                                Map<Integer, Object> valAsignar = new Arreglo();

                                for (int i = 0; i < arrActual.size(); i++) {
                                    Expresion expActual = arrActual.get(i);
                                    Object valActual = expActual.getValor(tabla, salida);
                                    Tipo tipActual = expActual.getTipo(tabla);

                                    if (valActual != null && tipActual != null) {
                                        if (tipActual != Tipo.VAR) {
                                            valAsignar.put(i, valActual);
                                        } else {
                                            System.err.println("Error! Variable indefinida. Linea:" + linea);

                                        }
                                    }
                                }

                                if (valAsignar.size() > 0) {
                                    tabla.setValor(id, valAsignar);
                                }
                            } else {
                                tabla.setValor(id, val);
                            }
                        } else {
                            if (valor instanceof Literal) {
                                tabla.setTipo(id, tip);
                                tabla.setValor(id, new Arreglo());
                            } else {
                                System.err.println("Error, Arreglo indefinido. Linea:" + linea);
                            }
                        }
                        break;
                    case VAR:
                        System.err.println("Error, Variable indefinida. Linea:" + linea);
                        break;
                    default:
                        if (val != null) {
                            tabla.setTipo(getId(), tip);
                            tabla.setValor(getId(), val);
                        }
                        break;
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
}
