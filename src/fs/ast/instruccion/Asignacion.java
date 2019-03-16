/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.expresion.Literal;
import fs.ast.expresion.operacion.Aritmetica;
import fs.ast.expresion.operacion.Operacion;
import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Asignacion implements Instruccion {

    protected final String id;
    protected final String op_asignacion;
    protected final Expresion valor;
    private final int linea;
    private final int columna;

    public Asignacion(String id, String op_asignacion, Expresion valor, int linea, int columna) {
        this.id = id;
        this.op_asignacion = op_asignacion;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    public Asignacion(String id, int linea, int columna) {
        this.id = id;
        this.op_asignacion = "";
        this.valor = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel) {
        if (null != valor) {
            Simbolo s = tabla.getSimbolo(id);
            if (s != null) {
                Object val = valor.getValor(tabla, salida);
                Tipo tip = valor.getTipo(tabla);
                if (tip != null) {
                    if (tip != Tipo.VAR) {
                        if (val != null) {
                            if ("=".equals(op_asignacion)) {
                                s.setValor(val);
                            } else {
                                Object val2 = s.getValor();
                                Tipo tip2 = s.getTipo();
                                Literal exp2 = new Literal(tip, val, linea, columna);
                                Literal exp1 = new Literal(tip2, val2, linea, columna);

                                Operacion.Operador op = Operacion.Operador.MAS;
                                switch (op_asignacion) {
                                    case "+=":
                                        op = Operacion.Operador.MAS;
                                        break;
                                    case "-=":
                                        op = Operacion.Operador.MENOS;
                                        break;
                                    case "*=":
                                        op = Operacion.Operador.ASTERISCO;
                                        break;
                                    case "/=":
                                        op = Operacion.Operador.BARRA;
                                        break;
                                }

                                Aritmetica operacion = new Aritmetica(exp1, exp2, op, linea, columna);

                                Object valAsignar = operacion.getValor(tabla, salida);
                                if (valAsignar != null) {
                                    s.setValor(valAsignar);
                                }
                            }
                        }
                    } else {
                        System.err.println("Error, Variable indefinida. Linea:" + linea);
                    }
                }
            } else {
                System.err.println("Error, Variable \"" + id + "\" no encontrada. Línea: " + linea);
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

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
}
