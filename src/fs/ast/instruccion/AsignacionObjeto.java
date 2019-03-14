/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.TablaSimbolo;
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

    public AsignacionObjeto(String id, String clave, Expresion valor, int linea, int columna) {
        super(id, valor, linea, columna);
        this.clave = clave;
        this.posicion = null;
    }

    public AsignacionObjeto(String id, String clave, Expresion posicion, Expresion valor, int linea, int columna) {
        super(id, valor, linea, columna);
        this.clave = clave;
        this.posicion = posicion;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        if (null != valor) {
            Tipo tip = tabla.getTipo(id);
            if (tip != null) {
                if (tip == Tipo.OBJETO) {
                    //hacer try catch
                    Map<String, Object> objeto = (Map<String, Object>) tabla.getValor(id);
                    if (objeto != null) {
                        if (objeto.containsKey(clave)) {
                            Object valorAsignar = valor.getValor(tabla, salida);
                            Tipo tipoAsignar = valor.getTipo(tabla);
                            if (tipoAsignar != null) {
                                if (tipoAsignar != Tipo.VAR) {
                                    if (valorAsignar != null) {
                                        if (posicion == null) {
                                            objeto.replace(clave, valorAsignar);
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
                                                            arreglo.replace(pos, valorAsignar);
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
        }
        return null;
    }

}
