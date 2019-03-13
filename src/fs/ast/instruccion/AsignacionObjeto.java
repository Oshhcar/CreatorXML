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
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class AsignacionObjeto extends Asignacion implements Instruccion {

    private final String clave;

    public AsignacionObjeto(Tipo tipo, String id, String clave, Object valor, int linea, int columna) {
        super(tipo, id, valor, linea, columna);
        this.clave = clave;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        if (null != valor) {
            switch (tipo) {
                case VAR:
                    Expresion exp = (Expresion) valor;
                    Object valorAsignar = exp.getValor(tabla, salida);
                    if (valorAsignar != null) {
                        Tipo tip = tabla.getTipo(id);
                        if (tip != null) {
                            if (tip == Tipo.OBJETO) {
                                Map<String, Object> valores = (Map<String, Object>) tabla.getValor(id);
                                if (valores != null) {
                                    if (valores.containsKey(clave)) {
                                        valores.replace(clave, valorAsignar);
                                        tabla.setValor(id, valores);
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
                        return null;
                        //tabla.setValor(getId(), val);
                    }
                    break;
                case ARREGLO:
                    Tipo tip = tabla.getTipo(id);
                    if (tip != null) {
                        if (tip == Tipo.OBJETO) {
                            Map<String, Object> valores = (Map<String, Object>) tabla.getValor(id);
                            if (valores != null) {
                                LinkedList<Expresion> arrActual = (LinkedList<Expresion>) valor;
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
                                System.err.println("Error, objeto \"" + id + "\" indefinido. Línea:" + super.getLinea());
                            }
                        } else {
                            System.err.println("Error, variable \"" + id + "\" no es un objeto. Línea:" + super.getLinea());
                        }
                    }
                    break;
            }
        }
        return null;
    }

}
