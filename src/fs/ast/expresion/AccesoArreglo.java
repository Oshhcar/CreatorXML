/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class AccesoArreglo implements Expresion {

    private final String id;
    private final Expresion posicion;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public AccesoArreglo(String id, Expresion posicion, int linea, int columna) {
        this.id = id;
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
        Object valPosicion = posicion.getValor(tabla, salida);

        if (valPosicion != null) {
            Tipo tipPosicion = posicion.getTipo(tabla);

            if (tipPosicion != null) {

                if (tipPosicion == Tipo.ENTERO) {
                    Integer pos = Integer.valueOf(valPosicion.toString());
                    Object oArreglo = tabla.getValor(id, Tipo.ARREGLO);

                    if (oArreglo != null) {
                        Map<Integer, Object> arreglo = (Map<Integer, Object>) oArreglo;
                        Object valor = arreglo.get(pos);

                        if (valor != null) {
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
                            System.err.println("Error, el arreglo no tiene valor en la posición " + pos + ". Línea:" + linea);
                        }
                    } else {
                        System.err.println("Error, arreglo \"" + id + "\" indefinido. Línea:" + linea);
                    }

                } else {
                    System.err.println("Error, la posición en el arreglo debe ser entero. Línea:" + linea);
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

}