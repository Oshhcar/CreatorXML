/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Ternario implements Expresion {

    private final Expresion condicion;
    private final Expresion verdadero;
    private final Expresion falso;
    private final int linea;
    private final int columna;
    private Tipo tipo;

    public Ternario(Expresion condicion, Expresion verdadero, Expresion falso, int linea, int columna) {
        this.condicion = condicion;
        this.verdadero = verdadero;
        this.falso = falso;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida) {
        Object condicionVal = condicion.getValor(tabla, salida);
        Object verdaderoVal = verdadero.getValor(tabla, salida);
        Object falsoVal = falso.getValor(tabla, salida);

        Tipo condicionTip = condicion.getTipo(tabla);
        Tipo verdaderoTip = condicion.getTipo(tabla);
        Tipo falsoTip = condicion.getTipo(tabla);

        if (condicionTip != null) {
            if (condicionTip == Tipo.BOOLEANO) {
                if ("verdadero".equals(condicionVal.toString())) {
                    if (verdaderoTip != null) {
                        tipo = verdaderoTip;
                        return verdaderoVal;
                    }
                } else {
                    if (falsoTip != null) {
                        tipo = falsoTip;
                        return falsoVal;
                    }
                }
            } else {
                System.err.println("Error en op ternario, la condicion debe ser booleana. Linea:" + linea);
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
