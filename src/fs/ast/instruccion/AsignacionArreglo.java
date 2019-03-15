/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.expresion.Literal;
import fs.ast.expresion.operacion.Aritmetica;
import fs.ast.expresion.operacion.Operacion;
import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.Objeto;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class AsignacionArreglo extends Asignacion implements Instruccion {

    private final Expresion posicion;

    public AsignacionArreglo(String id, Expresion posicion, String op_asignacion, Expresion valor, int linea, int columna) {
        super(id, op_asignacion, valor, linea, columna);
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
                                        if (tipExp != Tipo.VAR) {
                                            if (valExp != null) {
                                                if ("=".equals(op_asignacion)) {
                                                    arreglo.replace(pos, valExp);
                                                } else {
                                                    Object val2 = arreglo.get(pos);
                                                    Tipo tip2 = null;
                                                    if (val2 instanceof Double) {
                                                        tip2 = Tipo.DECIMAL;
                                                    } else if (val2 instanceof Integer) {
                                                        tip2 = Tipo.ENTERO;
                                                    } else if (val2.equals("verdadero") || val2.equals("falso")) {
                                                        tip2 = Tipo.BOOLEANO;
                                                    } else if (val2.equals("nulo")) {
                                                        tip2 = Tipo.NULL;
                                                    } else if (val2 instanceof String) {
                                                        tip2 = Tipo.CADENA;
                                                    } else if (val2 instanceof Objeto) {
                                                        tip2 = Tipo.OBJETO;
                                                    } else if (val2 instanceof Arreglo) {
                                                        tip2 = Tipo.ARREGLO;
                                                    }

                                                    Literal exp2 = new Literal(tipExp, valExp, super.getLinea(), super.getColumna());
                                                    Literal exp1 = new Literal(tip2, val2, super.getLinea(), super.getColumna());

                                                    Operacion.Operador op = Operacion.Operador.MAS;
                                                    switch (op_asignacion) {
                                                        case "+=":
                                                            op = Operacion.Operador.MAS;
                                                            break;
                                                        case "-=":
                                                            op = Operacion.Operador.MENOS;
                                                            break;
                                                        case "*=":
                                                            op = Operacion.Operador.ASTERISCO;
                                                            break;
                                                        case "/=":
                                                            op = Operacion.Operador.BARRA;
                                                            break;
                                                    }
                                                    Aritmetica operacion = new Aritmetica(exp1, exp2, op, super.getLinea(), super.getColumna());

                                                    Object valAsignar = operacion.getValor(tabla, salida);
                                                    if (valAsignar != null) {
                                                        arreglo.replace(pos, valAsignar);
                                                    }
                                                }
                                            }
                                        } else {
                                            System.err.println("Error, Variable indefinida. Linea:" + super.getLinea());
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
