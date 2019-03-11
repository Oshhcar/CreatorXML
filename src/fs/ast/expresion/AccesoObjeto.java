/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class AccesoObjeto implements Expresion {

    private final String id;
    private final String clave;
    private final Expresion posicion;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public AccesoObjeto(String id, String clave, int linea, int columna) {
        this.id = id;
        this.clave = clave;
        this.posicion = null;
        this.tipo = null;
        this.linea = linea;
        this.columna = columna;
    }

    public AccesoObjeto(String id, String clave, Expresion posicion, int linea, int columna) {
        this.id = id;
        this.clave = clave;
        this.posicion = posicion;
        this.tipo = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Tipo getTipo(TablaSimbolo tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolo tabla, JTextArea salida) {
        Tipo tip = tabla.getTipo(id);
        if (tip != null) {
            if (tip == Tipo.OBJETO) {
                Map<String, Object> valores = (Map<String, Object>) tabla.getValor(id, Tipo.OBJETO);
                if (valores != null) {
                    Object valor = valores.get(clave);

                    if (valor != null) {
                        if (null == posicion) {
                            if (valor instanceof Double) {
                                tipo = Tipo.DECIMAL;
                            } else if (valor instanceof Integer) {
                                tipo = Tipo.ENTERO;
                            } else if (valor.equals("verdadero") || valor.equals("falso")) {
                                tipo = Tipo.BOOLEANO;
                            } else if (valor.equals("nulo")) {
                                tipo = Tipo.NULL;
                            } else {
                                tipo = Tipo.CADENA;
                            }
                            return valor;
                        } else {
                            Object valPosicion = posicion.getValor(tabla, salida);
                            Tipo tipPosicion = posicion.getTipo(tabla);

                            if (valPosicion != null && tipPosicion != null) {
                                if (tipPosicion == Tipo.ENTERO) {
                                    if (valor instanceof HashMap) {
                                        Integer pos = Integer.valueOf(valPosicion.toString());
                                        Map<Integer, Object> arreglo = (Map<Integer, Object>) valor;
                                        Object valor2 = arreglo.get(pos);

                                        if (valor2 instanceof Double) {
                                            tipo = Tipo.DECIMAL;
                                        } else if (valor2 instanceof Integer) {
                                            tipo = Tipo.ENTERO;
                                        } else if (valor2.equals("verdadero") || valor2.equals("falso")) {
                                            tipo = Tipo.BOOLEANO;
                                        } else if (valor2.equals("nulo")) {
                                            tipo = Tipo.NULL;
                                        } else {
                                            tipo = Tipo.CADENA;
                                        }
                                        return valor2;
                                    } else {
                                        System.err.println("Error, La clave \"" + clave + "\" no es un arreglo. Línea:" + linea);
                                    }
                                } else {
                                    System.err.println("Error, la posición en el arreglo debe ser entero. Línea:" + linea);
                                }
                            }
                        }
                    } else {
                        System.err.println("Error, La clave \"" + clave + "\" no está en el objeto. Línea:" + linea);
                    }
                } else {
                    System.err.println("Error, objeto \"" + id + "\" indefinido. Línea:" + linea);
                }
            } else {
                System.err.println("Error, variable \"" + id + "\" no es un objeto. Línea:" + linea);
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

}
