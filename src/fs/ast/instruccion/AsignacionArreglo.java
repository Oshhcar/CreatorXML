/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class AsignacionArreglo extends Asignacion implements Instruccion {

    private final Expresion posicion;

    public AsignacionArreglo(Tipo tipo, String id, Expresion posicion, Expresion valor, int linea, int columna) {
        super(tipo, id, valor, linea, columna);
        this.posicion = posicion;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        if (null != valor) {
            Tipo tip = tabla.getTipo(id);
            if (tip != null) {
                if (tip == Tipo.ARREGLO) {
                    Map<Integer, Object> arreglo = (Map<Integer, Object>) tabla.getValor(id);
                    if (arreglo != null) {
                        Object valPosicion = posicion.getValor(tabla, salida);
                        Tipo tipPosicion = posicion.getTipo(tabla);
                        if (valPosicion != null && tipPosicion != null) {
                            if (tipPosicion == Tipo.ENTERO) {
                                Integer pos = Integer.valueOf(valPosicion.toString());
                                Expresion exp = (Expresion) valor;
                                Object valExp = exp.getValor(tabla, salida);
                                Tipo tipExp = exp.getTipo(tabla);

                                if (valExp != null && tipExp != null) {
                                    if (tipExp != Tipo.VAR) {
                                        arreglo.replace(pos, valExp);
                                    } else {
                                        System.err.println("Error! Variable indefinida. Linea:" + super.getLinea());
                                    }
                                }
                            } else {
                                System.err.println("Error, la posición en el arreglo debe ser entero. Línea:" + super.getLinea());
                            }
                        }
                    } else {
                        System.err.println("Error, arreglo \"" + id + "\" indefinido. Línea:" + super.getLinea());
                    }

                } else {
                    System.err.println("Error, variable \"" + id + "\" no es un arreglo. Línea:" + super.getLinea());
                }
            }
        }
        return null;
    }
}
