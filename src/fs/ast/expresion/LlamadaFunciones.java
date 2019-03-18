/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.expresion.nativas.Alguno;
import fs.ast.expresion.nativas.Buscar;
import fs.ast.expresion.nativas.Filtrar;
import fs.ast.expresion.nativas.Maximo;
import fs.ast.expresion.nativas.Minimo;
import fs.ast.expresion.nativas.Map;
import fs.ast.expresion.nativas.Reduce;
import fs.ast.expresion.nativas.Todos;
import fs.ast.instruccion.nativas.Invertir;
import fs.ast.instruccion.nativas.Ordenamiento;
import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.TablaSimbolos;
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
    public Tipo getTipo(TablaSimbolos tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida) {
        Simbolo s = tabla.getSimbolo(id);
        Object arreglo = s.getValor();
        if (arreglo != null) {
            for (int i = 0; i < this.funciones.size(); i++) {
                LlamadaFuncion llamada = this.funciones.get(i);

                switch (llamada.getId().toLowerCase()) {
                    case "descendente":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion descendente no necesita parámetros. Linea: " + linea);
                        }
                        Ordenamiento desc = new Ordenamiento(id, "desc", linea, columna);
                        arreglo = desc.ejecutar(tabla, salida, false, false);
                        break;
                    case "ascendente":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion ascendente no necesita parámetros. Linea: " + linea);
                        }
                        Ordenamiento asc = new Ordenamiento(id, "asc", linea, columna);
                        arreglo = asc.ejecutar(tabla, salida, false, false);
                        break;
                    case "invertir":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion invertir no necesita parámetros. Linea: " + linea);
                        }
                        Invertir inv = new Invertir(id, linea, columna);
                        arreglo = inv.ejecutar(tabla, salida, false, false);
                        break;
                    case "maximo":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion maximo no necesita parámetros. Linea: " + linea);
                        }
                        Maximo maximo = new Maximo(arreglo, linea, columna);
                        arreglo = maximo.getValor(tabla, salida);
                        tipo = maximo.getTipo(tabla);
                        break;
                    case "minimo":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion maximo no necesita parámetros. Linea: " + linea);
                        }
                        Minimo minimo = new Minimo(arreglo, linea, columna);
                        arreglo = minimo.getValor(tabla, salida);
                        tipo = minimo.getTipo(tabla);
                        break;
                    case "filtrar":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Filtrar filtrar = new Filtrar(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = filtrar.getValor(tabla, salida);
                            tipo = filtrar.getTipo(tabla);
                        }
                        break;
                    case "buscar":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Buscar buscar = new Buscar(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = buscar.getValor(tabla, salida);
                            tipo = buscar.getTipo(tabla);
                        }
                        break;
                    case "map":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Map map = new Map(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = map.getValor(tabla, salida);
                            tipo = map.getTipo(tabla);
                        }
                        break;
                    case "reduce":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Reduce reduce = new Reduce(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = reduce.getValor(tabla, salida);
                            tipo = reduce.getTipo(tabla);
                        }
                        break;
                    case "todos":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Todos todos = new Todos(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = todos.getValor(tabla, salida);
                            tipo = todos.getTipo(tabla);
                        }
                        break;
                    case "alguno":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Alguno alguno = new Alguno(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = alguno.getValor(tabla, salida);
                            tipo = alguno.getTipo(tabla);
                        }
                        break;
                    default:
                        System.err.println("Error, funcion nativa \"" + llamada.getId() + "\" no definida. Línea: " + linea);

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
