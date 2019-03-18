/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.AccesoArreglo;
import fs.ast.expresion.AccesoObjeto;
import fs.ast.expresion.Expresion;
import fs.ast.expresion.Identificador;
import fs.ast.expresion.Literal;
import fs.ast.expresion.operacion.Operacion;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Unario implements Instruccion {

    private final Expresion exp;
    private final Operacion.Operador operador;
    private final int linea;
    private final int columna;

    public Unario(Expresion exp, Operacion.Operador operador, int linea, int columna) {
        this.exp = exp;
        this.operador = operador;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel, String dirActual) {
        Object valExp = exp.getValor(tabla, salida, dirActual);
        if (valExp != null) {
            Tipo tipoExp = exp.getTipo(tabla);
            if (tipoExp != null) {
                if (tipoExp.isNumero()) {
                    Object val;
                    if (tipoExp == Tipo.ENTERO) {
                        if (operador == Operacion.Operador.AUMENTO) {
                            val = new Integer(valExp.toString()) + 1;
                        } else {
                            val = new Integer(valExp.toString()) - 1;
                        }
                    } else {
                        if (operador == Operacion.Operador.AUMENTO) {
                            val = new Double(valExp.toString()) + 1;
                        } else {
                            val = new Double(valExp.toString()) - 1;
                        }
                    }

                    Literal valor = new Literal(tipoExp, val, linea, columna);

                    if (exp instanceof Identificador) {
                        Identificador id = (Identificador) exp;
                        Asignacion asigna = new Asignacion(id.getId(), "=", valor, linea, columna);
                        return asigna.ejecutar(tabla, salida, fun, sel, dirActual);
                    } else if (exp instanceof AccesoArreglo) {
                        AccesoArreglo ar = (AccesoArreglo) exp;
                        AsignacionArreglo asigna = new AsignacionArreglo(ar.getId(), ar.getPosicion(), "=", valor, linea, columna);
                        return asigna.ejecutar(tabla, salida, fun, sel, dirActual);
                    } else if (exp instanceof AccesoObjeto) {
                        AccesoObjeto obj = (AccesoObjeto) exp;
                        AsignacionObjeto asigna = new AsignacionObjeto(obj.getId(), obj.getClave(), obj.getPosicion(), "=", valor, linea, columna);
                        return asigna.ejecutar(tabla, salida, fun, sel, dirActual);
                    } else {
                        if (operador == Operacion.Operador.AUMENTO) {
                            System.err.println("Error, se necesita de una variable para aumentar. Línea: " + linea);
                        } else {
                            System.err.println("Error, se necesita de una variable para decrementar. Línea: " + linea);

                        }
                        return null;
                    }

                } else {
                    if (operador == Operacion.Operador.AUMENTO) {
                        System.err.println("Error de tipos, solo los números se pueden aumentar. Línea: " + linea);
                    } else {
                        System.err.println("Error de tipos, solo los números se pueden decrementar. Línea: " + linea);
                    }
                    return null;
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

}
