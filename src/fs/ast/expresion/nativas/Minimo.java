/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.nativas;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Minimo implements Expresion {

    private final Object array;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public Minimo(Object array, int linea, int columna) {
        this.array = array;
        this.tipo = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida, String dirActual) {
        if (array instanceof Arreglo) {
            Map<Integer, Object> arreglo = (Map<Integer, Object>) array;
            if (arreglo != null) {
                Object exp0 = arreglo.get(0);
                if (exp0 instanceof Integer) {
                    Integer max = (Integer) exp0;

                    for (int i = 0; i < arreglo.size(); i++) {
                        Object exp = arreglo.get(i);
                        if (!(exp instanceof Integer)) {
                            System.err.println("Error, los valores deben ser del mismo tipo para encontrar el máximo. Línea: " + linea);
                            return null;
                        } else {
                            Integer tmp = (Integer) exp;
                            if (tmp < max) {
                                max = tmp;
                            }
                        }
                    }

                    tipo = Tipo.ENTERO;
                    return max;

                } else if (exp0 instanceof Double) {
                    Double max = (Double) exp0;

                    for (int i = 0; i < arreglo.size(); i++) {
                        Object exp = arreglo.get(i);
                        if (!(exp instanceof Double)) {
                            System.err.println("Error, los valores deben ser del mismo tipo para encontrar el máximo. Línea: " + linea);
                            return null;
                        } else {
                            Double tmp = (Double) exp;
                            if (tmp < max) {
                                max = tmp;
                            }
                        }
                    }

                    tipo = Tipo.DECIMAL;
                    return max;
                } else if (exp0 instanceof String) {
                    boolean isBoleano = false;
                    String max = (String) exp0;

                    if (max.equals("verdadero") || max.equals("falso")) {
                        isBoleano = true;
                    }

                    for (int i = 0; i < arreglo.size(); i++) {
                        Object exp = arreglo.get(i);
                        if (!(exp instanceof String)) {
                            System.err.println("Error, los valores deben ser del mismo tipo para encontrar el máximo. Línea: " + linea);
                            return null;
                        } else {
                            String tmp = (String) exp;
                            if (tmp.compareTo(max) < 0) {
                                max = tmp;
                            }

                            isBoleano = isBoleano && (tmp.equals("verdadero") || tmp.equals("falso"));
                        }
                    }
                    if (!isBoleano) {
                        tipo = Tipo.CADENA;
                    } else {
                        tipo = Tipo.BOOLEANO;
                    }
                    return max;
                } else {
                    System.err.println("Error, el arreglo debe ser númerico. Línea: " + linea);
                }
            } else {
                System.err.println("Error, arreglo indefinido. Línea:" + linea);
            }
        } else {
            System.err.println("Error, variable no es un arreglo. Línea:" + linea);
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
