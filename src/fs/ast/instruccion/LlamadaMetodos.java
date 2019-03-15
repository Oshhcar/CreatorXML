/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.instruccion.nativas.Ordenamiento;
import fs.ast.simbolos.TablaSimbolo;
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
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        Object arreglo = tabla.getValor(id);

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
