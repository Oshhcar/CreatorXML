/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.expresion.nativas.Maximo;
import fs.ast.expresion.nativas.Minimo;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class LlamadaFunciones implements Expresion {

    private final String id;
    private LinkedList<LlamadaFuncion> funciones;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public LlamadaFunciones(String id, int linea, int columna) {
        this.id = id;
        this.funciones = new LinkedList<>();
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
        Object arreglo = tabla.getValor(id);
        if (arreglo != null) {
            for (int i = 0; i < this.funciones.size(); i++) {
                LlamadaFuncion llamada = this.funciones.get(i);

                switch (llamada.getId().toLowerCase()) {
                    case "maximo":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion maximo no necesita parámetros. Linea: " + linea);
                        }
                        Maximo maximo = new Maximo(id, linea, columna);
                        arreglo = maximo.getValor(tabla, salida);
                        tipo = maximo.getTipo(tabla);
                        break;
                    case "minimo":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion maximo no necesita parámetros. Linea: " + linea);
                        }
                        Minimo minimo = new Minimo(id, linea, columna);
                        arreglo = minimo.getValor(tabla, salida);
                        tipo = minimo.getTipo(tabla);
                        break;
                }
            }
            return arreglo;
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
        this.funciones.add(new LlamadaFuncion(id, parametros, linea, columna));
    }
}
