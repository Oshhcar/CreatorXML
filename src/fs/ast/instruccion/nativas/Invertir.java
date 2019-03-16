/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion.nativas;

import fs.ast.instruccion.Instruccion;
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
public class Invertir implements Instruccion {

    private final String id;
    private final int linea;
    private final int columna;

    public Invertir(String id, int linea, int columna) {
        this.id = id;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel) {
        Simbolo s = tabla.getSimbolo(id);
        if (s != null) {
            Tipo tip = s.getTipo();
            if (tip != null) {
                if (tip == Tipo.ARREGLO) {
                    Map<Integer, Object> arreglo = (Map<Integer, Object>) s.getValor();
                    if (arreglo != null) {
                        Map<Integer, Object> invertido = new Arreglo();

                        int j = arreglo.size() - 1;
                        for (int i = 0; i < arreglo.size(); i++) {
                            invertido.put(j--, arreglo.get(i));
                        }

                        s.setValor(invertido);

                    } else {
                        System.err.println("Error, arreglo \"" + id + "\" indefinido. Línea:" + linea);
                    }
                } else {
                    System.err.println("Error, variable \"" + id + "\" no es un arreglo. Línea:" + linea);
                }
            }
        } else {
            System.err.println("Error, Variable \"" + id + "\" no encontrada. Línea: " + linea);

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
