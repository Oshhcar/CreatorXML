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
import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class AsignacionObjeto extends Asignacion implements Instruccion {

    private final String clave;
    private final Expresion posicion;

    public AsignacionObjeto(String id, String clave, String op_asignacion, Expresion valor, int linea, int columna) {
        super(id, op_asignacion, valor, linea, columna);
        this.clave = clave;
        this.posicion = null;
    }

    public AsignacionObjeto(String id, String clave, Expresion posicion, String op_asignacion, Expresion valor, int linea, int columna) {
        super(id, op_asignacion, valor, linea, columna);
        this.clave = clave;
        this.posicion = posicion;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel) {
        if (null != valor) {
            Simbolo s = tabla.getSimbolo(id);
            if (s != null) {
                Tipo tip = s.getTipo();
                if (tip != null) {
                    if (tip == Tipo.OBJETO) {
                        //hacer try catch
                        Map<String, Object> objeto = (Map<String, Object>) s.getValor();
                        if (objeto != null) {
                            if (objeto.containsKey(clave)) {
                                Object valorAsignar = valor.getValor(tabla, salida);
                                Tipo tipoAsignar = valor.getTipo(tabla);
                                if (tipoAsignar != null) {
                                    if (tipoAsignar != Tipo.VAR) {
                                        if (valorAsignar != null) {
                                            if (posicion == null) {
                                                if ("=".equals(op_asignacion)) {
                                                    objeto.replace(clave, valorAsignar);
                                                } else {
                                                    Object val2 = objeto.get(clave);
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

                                                    Literal exp2 = new Literal(tipoAsignar, valorAsignar, super.getLinea(), super.getColumna());
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
                                                        objeto.replace(clave, valAsignar);
                                                    }
                                                }
                                            } else {
                                                Object valPosicion = posicion.getValor(tabla, salida);
                                                Tipo tipPosicion = posicion.getTipo(tabla);

                                                if (valPosicion != null && tipPosicion != null) {
                                                    if (tipPosicion == Tipo.ENTERO) {
                                                        Object arr = objeto.get(clave);
                                                        if (arr instanceof Arreglo) {
                                                            Integer pos = Integer.valueOf(valPosicion.toString());
                                                            Map<Integer, Object> arreglo = (Map<Integer, Object>) arr;
                                                            if (arreglo.containsKey(pos)) {
                                                                if ("=".equals(op_asignacion)) {
                                                                    arreglo.replace(pos, valorAsignar);
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

                                                                    Literal exp1 = new Literal(tipoAsignar, valorAsignar, super.getLinea(), super.getColumna());
                                                                    Literal exp2 = new Literal(tip2, val2, super.getLinea(), super.getColumna());

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
                                                            } else {
                                                                System.err.println("Error, no se puede asignar el valor en la posicion " + pos + " del arreglo. Línea:" + super.getLinea());
                                                            }
                                                        } else {
                                                            System.err.println("Error, La clave \"" + clave + "\" no es un arreglo. Línea:" + super.getLinea());
                                                        }
                                                    } else {
                                                        System.err.println("Error, la posición en el arreglo debe ser entero. Línea:" + super.getLinea());
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        System.err.println("Error, Variable indefinida. Linea:" + super.getLinea());
                                    }
                                }
                            } else {
                                System.err.println("Error, La clave \"" + clave + "\" no está en el objeto. Línea:" + super.getLinea());
                            }
                        } else {
                            System.err.println("Error, objeto \"" + id + "\" indefinido. Línea:" + super.getLinea());
                        }
                    } else {
                        System.err.println("Error, variable \"" + id + "\" no es un objeto. Línea:" + super.getLinea());
                    }
                }
            } else {
                System.err.println("Error, Variable \"" + id + "\" no encontrada. Línea: " + super.getLinea());
            }
        }
        return null;
    }

}
