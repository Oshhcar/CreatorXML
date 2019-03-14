/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import fs.ast.simbolos.Objeto;
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
        Tipo tip = tabla.getTipo(getId());
        if (tip != null) {
            if (tip == Tipo.OBJETO) {
                Map<String, Object> valores = (Map<String, Object>) tabla.getValor(getId());
                if (valores != null) {
                    Object valor = valores.get(getClave());
                    if (valor != null) {
                        if (null == getPosicion()) {
                            if (valor instanceof Double) {
                                tipo = Tipo.DECIMAL;
                            } else if (valor instanceof Integer) {
                                tipo = Tipo.ENTERO;
                            } else if (valor.equals("verdadero") || valor.equals("falso")) {
                                tipo = Tipo.BOOLEANO;
                            } else if (valor.equals("nulo")) {
                                tipo = Tipo.NULL;
                            } else if (valor instanceof String) {
                                tipo = Tipo.CADENA;
                            } else if (valor instanceof Objeto) {
                                tipo = Tipo.OBJETO;
                            } else if (valor instanceof Arreglo) {
                                tipo = Tipo.ARREGLO;
                            }
                            return valor;
                        } else {
                            Object valPosicion = getPosicion().getValor(tabla, salida);
                            Tipo tipPosicion = getPosicion().getTipo(tabla);

                            if (valPosicion != null && tipPosicion != null) {
                                if (tipPosicion == Tipo.ENTERO) {
                                    if (valor instanceof Arreglo) {
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
                                        } else if (valor2 instanceof String){
                                            tipo = Tipo.CADENA;
                                        } else if (valor2 instanceof Objeto){
                                            tipo = Tipo.OBJETO;
                                        } else if (valor2 instanceof Arreglo){
                                            tipo = Tipo.ARREGLO;
                                        }
                                        return valor2;
                                    } else {
                                        System.err.println("Error, La clave \"" + getClave() + "\" no es un arreglo. Línea:" + linea);
                                    }
                                } else {
                                    System.err.println("Error, la posición en el arreglo debe ser entero. Línea:" + linea);
                                }
                            }
                        }
                    } else {
                        System.err.println("Error, La clave \"" + getClave() + "\" no está en el objeto. Línea:" + linea);
                    }
                } else {
                    System.err.println("Error, objeto \"" + getId() + "\" indefinido. Línea:" + linea);
                }
            } else {
                System.err.println("Error, variable \"" + getId() + "\" no es un objeto. Línea:" + linea);
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

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @return the posicion
     */
    public Expresion getPosicion() {
        return posicion;
    }

}
