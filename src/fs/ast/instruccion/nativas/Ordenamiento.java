/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion.nativas;

import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Ordenamiento implements Instruccion {

    private final String id;
    private final String ord;
    private final int linea;
    private final int columna;

    public Ordenamiento(String id, String ord, int linea, int columna) {
        this.id = id;
        this.ord = ord;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        Tipo tip = tabla.getTipo(id);
        if (tip != null) {
            if (tip == Tipo.ARREGLO) {
                Map<Integer, Object> arreglo = (Map<Integer, Object>) tabla.getValor(id);
                if (arreglo != null) {

                    Object exp0 = arreglo.get(0);

                    if (exp0 instanceof Integer) {
                        Map<Integer, Integer> arregloInt = new HashMap<>();

                        for (int i = 0; i < arreglo.size(); i++) {
                            Object exp = arreglo.get(i);
                            if (!(exp instanceof Integer)) {
                                System.err.println("Error, los valores deben ser del mismo tipo para ordenar. Línea: " + linea);
                                return null;
                            }
                            arregloInt.put(i, (Integer) exp);
                        }

                        List<Map.Entry<Integer, Integer>> list
                                = new LinkedList<>(arregloInt.entrySet());

                        Collections.sort(list, (Map.Entry<Integer, Integer> t, Map.Entry<Integer, Integer> t1) -> {
                            if(ord.equals("desc")){
                                return (t1.getValue()).compareTo(t.getValue());
                            } else {
                                return (t.getValue()).compareTo(t1.getValue());
                            }
                        });

                        Map<Integer, Integer> sortedMap = new Arreglo();
                        int i = 0;
                        for (Map.Entry<Integer, Integer> entry : list) {
                            sortedMap.put(i, entry.getValue());
                            i++;
                        }

                        tabla.setValor(id, sortedMap);

                    } else if (exp0 instanceof Double) {
                        Map<Integer, Double> arregloInt = new HashMap<>();

                        for (int i = 0; i < arreglo.size(); i++) {
                            Object exp = arreglo.get(i);
                            if (!(exp instanceof Double)) {
                                System.err.println("Error, los valores deben ser del mismo tipo para ordenar. Línea: " + linea);
                                return null;
                            }
                            arregloInt.put(i, (Double) exp);
                        }

                        List<Map.Entry<Integer, Double>> list
                                = new LinkedList<>(arregloInt.entrySet());

                        Collections.sort(list, (Map.Entry<Integer, Double> t, Map.Entry<Integer, Double> t1) -> {
                            if(ord.equals("desc")){
                                return (t1.getValue()).compareTo(t.getValue());
                            } else {
                                return (t.getValue()).compareTo(t1.getValue());
                            }
                        });

                        Map<Integer, Double> sortedMap = new Arreglo();
                        int i = 0;
                        for (Map.Entry<Integer, Double> entry : list) {
                            sortedMap.put(i, entry.getValue());
                            i++;
                        }

                        tabla.setValor(id, sortedMap);
                    } else if (exp0 instanceof String) {
                        Map<Integer, String> arregloInt = new HashMap<>();

                        for (int i = 0; i < arreglo.size(); i++) {
                            Object exp = arreglo.get(i);
                            if (!(exp instanceof String)) {
                                System.err.println("Error, los valores deben ser del mismo tipo para ordenar. Línea: " + linea);
                                return null;
                            }
                            arregloInt.put(i, (String) exp);
                        }

                        List<Map.Entry<Integer, String>> list
                                = new LinkedList<>(arregloInt.entrySet());

                        Collections.sort(list, (Map.Entry<Integer, String> t, Map.Entry<Integer, String> t1) -> {
                            if(ord.equals("desc")){
                                return (t1.getValue()).compareTo(t.getValue());
                            } else {
                                return (t.getValue()).compareTo(t1.getValue());
                            }
                        });

                        Map<Integer, String> sortedMap = new Arreglo();
                        int i = 0;
                        for (Map.Entry<Integer, String> entry : list) {
                            sortedMap.put(i, entry.getValue());
                            i++;
                        }

                        tabla.setValor(id, sortedMap);
                    } else {
                        System.err.println("Error, no se puede ordenar el arreglo. Línea: " + linea);
                    }
                } else {
                    System.err.println("Error, arreglo \"" + id + "\" indefinido. Línea:" + linea);
                }
            } else {
                System.err.println("Error, variable \"" + id + "\" no es un arreglo. Línea:" + linea);
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
