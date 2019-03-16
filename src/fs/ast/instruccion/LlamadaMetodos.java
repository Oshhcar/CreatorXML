/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.expresion.nativas.Maximo;
import fs.ast.expresion.nativas.Minimo;
import fs.ast.instruccion.nativas.Invertir;
import fs.ast.instruccion.nativas.Ordenamiento;
import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.TablaSimbolos;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class LlamadaMetodos implements Instruccion {

    private final String id;
    private LinkedList<LlamadaMetodo> metodos;
    private final int linea;
    private final int columna;

    public LlamadaMetodos(String id, int linea, int columna) {
        this.id = id;
        this.metodos = new LinkedList<>();
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida) {
        Simbolo s = tabla.getSimbolo(id);
        Object arreglo = s.getValor();

        if (arreglo != null) {
            for (int i = 0; i < this.metodos.size(); i++) {
                LlamadaMetodo llamada = this.metodos.get(i);

                switch (llamada.getId().toLowerCase()) {
                    case "descendente":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion descendente no necesita parámetros. Linea: " + linea);
                        }
                        Ordenamiento desc = new Ordenamiento(id, "desc", linea, columna);
                        arreglo = desc.ejecutar(tabla, salida);
                        break;
                    case "ascendente":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion ascendente no necesita parámetros. Linea: " + linea);
                        }
                        Ordenamiento asc = new Ordenamiento(id, "asc", linea, columna);
                        arreglo = asc.ejecutar(tabla, salida);
                        break;
                    case "invertir":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion invertir no necesita parámetros. Linea: " + linea);
                        }
                        Invertir inv = new Invertir(id, linea, columna);
                        arreglo = inv.ejecutar(tabla, salida);
                        break;
                    case "maximo":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion maximo no necesita parámetros. Linea: " + linea);
                        }
                        Maximo maximo = new Maximo(id, linea, columna);
                        arreglo = maximo.getValor(tabla, salida);
                        System.out.println("el max es " + arreglo);
                        break;
                    case "minimo":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion maximo no necesita parámetros. Linea: " + linea);
                        }
                        Minimo minimo = new Minimo(id, linea, columna);
                        arreglo = minimo.getValor(tabla, salida);
                        System.out.println("el min es " + arreglo);
                        break;
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

    public void add(String id, LinkedList<Expresion> parametros) {
        this.metodos.add(new LlamadaMetodo(id, parametros, linea, columna));
    }

}