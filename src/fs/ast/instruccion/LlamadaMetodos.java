/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.expresion.nativas.Alguno;
import fs.ast.expresion.nativas.Buscar;
import fs.ast.expresion.nativas.Filtrar;
import fs.ast.expresion.nativas.Map;
import fs.ast.expresion.nativas.Maximo;
import fs.ast.expresion.nativas.Minimo;
import fs.ast.expresion.nativas.Obtener;
import fs.ast.expresion.nativas.Reduce;
import fs.ast.expresion.nativas.Todos;
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
    private final LinkedList<LlamadaMetodo> metodos;
    private final int linea;
    private final int columna;

    public LlamadaMetodos(String id, int linea, int columna) {
        this.id = id;
        this.metodos = new LinkedList<>();
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel, String dirActual) {
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
                        arreglo = desc.ejecutar(tabla, salida, fun, sel, dirActual);
                        break;
                    case "ascendente":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion ascendente no necesita parámetros. Linea: " + linea);
                        }
                        Ordenamiento asc = new Ordenamiento(id, "asc", linea, columna);
                        arreglo = asc.ejecutar(tabla, salida, fun, sel, dirActual);
                        break;
                    case "invertir":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion invertir no necesita parámetros. Linea: " + linea);
                        }
                        Invertir inv = new Invertir(id, linea, columna);
                        arreglo = inv.ejecutar(tabla, salida, fun, sel, dirActual);
                        break;
                    case "maximo":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion maximo no necesita parámetros. Linea: " + linea);
                        }
                        Maximo maximo = new Maximo(id, linea, columna);
                        arreglo = maximo.getValor(tabla, salida, dirActual);
                        System.out.println("el max es " + arreglo);
                        break;
                    case "minimo":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion maximo no necesita parámetros. Linea: " + linea);
                        }
                        Minimo minimo = new Minimo(id, linea, columna);
                        arreglo = minimo.getValor(tabla, salida, dirActual);
                        System.out.println("el min es " + arreglo);
                        break;
                    case "filtrar":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Filtrar filtrar = new Filtrar(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = filtrar.getValor(tabla, salida, dirActual);
                        }
                        break;
                    case "buscar":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Buscar buscar = new Buscar(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = buscar.getValor(tabla, salida, dirActual);
                        }
                        break;
                    case "map":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Map map = new Map(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = map.getValor(tabla, salida, dirActual);
                        }
                        break;
                    case "reduce":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Reduce reduce = new Reduce(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = reduce.getValor(tabla, salida, dirActual);
                        }
                        break;
                    case "todos":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Todos todos = new Todos(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = todos.getValor(tabla, salida, dirActual);
                        }
                        break;
                    case "alguno":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Alguno alguno = new Alguno(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = alguno.getValor(tabla, salida, dirActual);
                        }
                        break;
                    case "obtenerporetiqueta":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, ObtenerPorEtiqueta necesita el nombre de la etiqueta. Linea: " + linea);
                        } else {
                            Obtener porEtiqueta = new Obtener(arreglo, llamada.getParametros(), 1, linea, columna);
                            arreglo = porEtiqueta.getValor(tabla, salida, dirActual);
                        }
                        break;
                    case "obtenerporid":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, ObtenerPorId necesita el nombre de la etiqueta. Linea: " + linea);
                        } else {
                            Obtener porId = new Obtener(arreglo, llamada.getParametros(), 2, linea, columna);
                            arreglo = porId.getValor(tabla, salida, dirActual);
                        }
                        break;
                    case "obtenerpornombre":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, ObtenerPorNombre necesita el nombre de la etiqueta. Linea: " + linea);
                        } else {
                            Obtener porNombre = new Obtener(arreglo, llamada.getParametros(), 3, linea, columna);
                            arreglo = porNombre.getValor(tabla, salida, dirActual);
                        }
                        break;
                    default:
                        System.err.println("Error, metodo nativo \"" + llamada.getId() + "\" no definida. Línea: " + linea);
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
