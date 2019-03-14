/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
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
public class AsignacionObjeto extends Asignacion implements Instruccion {

    private final String clave;

    public AsignacionObjeto(Tipo tipo, String id, String clave, Expresion valor, int linea, int columna) {
        super(tipo, id, valor, linea, columna);
        this.clave = clave;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        if (null != valor) {
            Object valorAsignar = valor.getValor(tabla, salida);
            Tipo tipoAsignar = valor.getTipo(tabla);
            if (tipoAsignar != null) {
                Tipo tip = tabla.getTipo(id);
                if (tip != null) {
                    if (tip == Tipo.OBJETO) {
                        Map<String, Object> valores = (Map<String, Object>) tabla.getValor(id);
                        if (valores != null) {
                            switch (tipoAsignar) {
                                case OBJETO:
                                    if (valorAsignar != null) {
                                        if (!(valorAsignar instanceof Objeto)) {
                                            Map<String, Expresion> actual = (Map<String, Expresion>) valorAsignar;
                                            Map<String, Object> valoresAsignar = new Objeto();

                                            actual.keySet().forEach((claveActual) -> {
                                                Expresion expActual = actual.get(claveActual);
                                                Object valActual = expActual.getValor(tabla, salida);
                                                if (valActual != null) {
                                                    valoresAsignar.put(claveActual, valActual);
                                                }
                                            });

                                            if (valores.containsKey(clave)) {
                                                valores.replace(clave, valoresAsignar);
                                                tabla.setValor(id, valores);
                                            } else {
                                                System.err.println("Error, La clave \"" + clave + "\" no está en el objeto. Línea:" + super.getLinea());
                                            }

                                        } else {
                                            if (valores.containsKey(clave)) {
                                                valores.replace(clave, valorAsignar);
                                                tabla.setValor(id, valores);
                                            } else {
                                                System.err.println("Error, La clave \"" + clave + "\" no está en el objeto. Línea:" + super.getLinea());
                                            }
                                        }
                                    } else {
                                        System.err.println("Errror, Objeto indefinido. Linea:" + super.getLinea());
                                    }
                                    break;
                                case ARREGLO:
                                    if (valorAsignar != null) {
                                        if (!(valorAsignar instanceof Arreglo)) {
                                            LinkedList<Expresion> arrActual = (LinkedList<Expresion>) valorAsignar;
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
                                            if (valAsignar.size() > 0) {
                                                if (valores.containsKey(clave)) {
                                                    valores.replace(clave, valAsignar);
                                                    tabla.setValor(id, valores);
                                                } else {
                                                    System.err.println("Error, La clave \"" + clave + "\" no está en el objeto. Línea:" + super.getLinea());
                                                }
                                            }
                                        } else {
                                            if (valores.containsKey(clave)) {
                                                valores.replace(clave, valorAsignar);
                                                tabla.setValor(id, valores);
                                            } else {
                                                System.err.println("Error, La clave \"" + clave + "\" no está en el objeto. Línea:" + super.getLinea());
                                            }
                                        }
                                    } else {
                                        System.err.println("Errror, Arreglo indefinido. Linea:" + super.getLinea());
                                    }
                                    break;
                                case VAR:
                                    System.err.println("Errror, Variable indefinida. Linea:" + super.getLinea());
                                    break;
                                default:
                                    if (valorAsignar != null) {
                                        if (valores.containsKey(clave)) {
                                            valores.replace(clave, valorAsignar);
                                            tabla.setValor(id, valores);
                                        } else {
                                            System.err.println("Error, La clave \"" + clave + "\" no está en el objeto. Línea:" + super.getLinea());
                                        }
                                    }
                                    break;
                            }
                        } else {
                            System.err.println("Error, objeto \"" + id + "\" indefinido. Línea:" + super.getLinea());
                        }
                    } else {
                        System.err.println("Error, variable \"" + id + "\" no es un objeto. Línea:" + super.getLinea());
                    }
                }
            }
        }
        return null;
    }

}
