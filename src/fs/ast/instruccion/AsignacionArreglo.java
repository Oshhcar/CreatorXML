/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.expresion.Literal;
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
public class AsignacionArreglo extends Asignacion implements Instruccion {

    private final Expresion posicion;

    public AsignacionArreglo(String id, Expresion posicion, Expresion valor, int linea, int columna) {
        super(id, valor, linea, columna);
        this.posicion = posicion;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        if (null != valor) {
            Tipo tip = tabla.getTipo(id);
            if (tip != null) {
                if (tip == Tipo.ARREGLO) {
                    Map<Integer, Object> arreglo = (Map<Integer, Object>) tabla.getValor(id);
                    if (arreglo != null) {
                        Object valPosicion = posicion.getValor(tabla, salida);
                        Tipo tipPosicion = posicion.getTipo(tabla);
                        if (valPosicion != null && tipPosicion != null) {
                            if (tipPosicion == Tipo.ENTERO) {
                                Integer pos = Integer.valueOf(valPosicion.toString());
                                if (arreglo.containsKey(pos)) {
                                    Object valExp = valor.getValor(tabla, salida);
                                    Tipo tipExp = valor.getTipo(tabla);
                                    if (tipExp != null) {
                                        switch (tipExp) {
                                            case OBJETO:
                                                if (valExp != null) {
                                                    if (!(valExp instanceof Objeto)) {
                                                        Map<String, Expresion> actual = (Map<String, Expresion>) valExp;
                                                        Map<String, Object> valoresAsignar = new Objeto();

                                                        actual.keySet().forEach((claveActual) -> {
                                                            Expresion expActual = actual.get(claveActual);
                                                            Object valActual = expActual.getValor(tabla, salida);
                                                            if (valActual != null) {
                                                                valoresAsignar.put(claveActual, valActual);
                                                            }
                                                        });

                                                        arreglo.replace(pos, valoresAsignar);

                                                    } else {
                                                        arreglo.replace(pos, valExp);
                                                    }
                                                } else {
                                                    if (valor instanceof Literal) {
                                                        arreglo.replace(pos, new Objeto());
                                                    } else {
                                                        System.err.println("Error, Objeto indefinido. Linea:" + super.getLinea());
                                                    }
                                                }
                                                break;
                                            case ARREGLO:
                                                if (valExp != null) {
                                                    if (!(valExp instanceof Arreglo)) {
                                                        LinkedList<Expresion> arrActual = (LinkedList<Expresion>) valExp;
                                                        Map<Integer, Object> valAsignar = new Arreglo();

                                                        for (int i = 0; i < arrActual.size(); i++) {
                                                            Expresion expActual = arrActual.get(i);
                                                            Object valActual = expActual.getValor(tabla, salida);
                                                            Tipo tipActual = expActual.getTipo(tabla);

                                                            if (valActual != null && tipActual != null) {
                                                                if (tipActual != Tipo.VAR) {
                                                                    valAsignar.put(i, valActual);
                                                                } else {
                                                                    System.err.println("Error! Variable indefinida. Linea:" + super.getLinea());

                                                                }
                                                            }
                                                        }

                                                        arreglo.replace(pos, valAsignar);

                                                    } else {
                                                        arreglo.replace(pos, valExp);
                                                    }
                                                } else {
                                                    if (valor instanceof Literal) {
                                                        arreglo.replace(pos, new Arreglo());
                                                    } else {
                                                        System.err.println("Error, Arreglo indefinido. Linea:" + super.getLinea());
                                                    }
                                                }
                                                break;
                                            case VAR:
                                                System.err.println("Error! Variable indefinida. Linea:" + super.getLinea());
                                                break;
                                            default:
                                                if (valExp != null) {
                                                    arreglo.replace(pos, valExp);
                                                }
                                                break;
                                        }
                                    }
                                } else {
                                    System.err.println("Error, no se puede asignar el valor en la posicion " + pos + " del arreglo. Línea:" + super.getLinea());
                                }
                            } else {
                                System.err.println("Error, la posición en el arreglo debe ser entero. Línea:" + super.getLinea());
                            }
                        }
                    } else {
                        System.err.println("Error, arreglo \"" + id + "\" indefinido. Línea:" + super.getLinea());
                    }

                } else {
                    System.err.println("Error, variable \"" + id + "\" no es un arreglo. Línea:" + super.getLinea());
                }
            }
        }
        return null;
    }
}
