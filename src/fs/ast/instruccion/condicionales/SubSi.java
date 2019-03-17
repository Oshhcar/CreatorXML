/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion.condicionales;

import fs.ast.NodoAST;
import fs.ast.expresion.Expresion;
import fs.ast.expresion.Literal;
import fs.ast.expresion.Retornar;
import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.Simbolos;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class SubSi implements Instruccion {

    private final Expresion condicion;
    private final LinkedList<NodoAST> bloques;
    private final boolean isSino;
    private final int linea;
    private final int columna;

    public SubSi(Expresion condicion, LinkedList<NodoAST> bloques, int linea, int columna) {
        this.condicion = condicion;
        this.bloques = bloques;
        this.isSino = false;
        this.linea = linea;
        this.columna = columna;
    }

    public SubSi(LinkedList<NodoAST> bloques, int linea, int columna) {
        this.condicion = null;
        this.bloques = bloques;
        this.isSino = true;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel) {
        if (!isSino) {
            Object valorCondicion = condicion.getValor(tabla, salida);
            Object tipoCondicion = condicion.getTipo(tabla);

            if (tipoCondicion != null) {
                if (tipoCondicion == Tipo.BOOLEANO) {
                    boolean cond = valorCondicion.equals("verdadero");

                    if (cond) {
                        tabla.add(new Simbolos());
                        for (NodoAST bloque : bloques) {
                            if (bloque instanceof Instruccion) {
                                Object o = ((Instruccion) bloque).ejecutar(tabla, salida, fun, sel);

                                if (o != null) {
                                    if (o instanceof Literal) {
                                        Literal lit = (Literal) o;
                                        if (fun) {
                                            tabla.pollLast();
                                            return lit;
                                        } else {
                                            System.err.println("Error, Sentencia Retornar no se encuentra dentro de un método o función. Línea :" + lit.getLinea());

                                        }
                                    }
                                }
                            } else {
                                if (bloque instanceof Retornar) {
                                    Retornar ret = (Retornar) bloque;
                                    if (fun) {
                                        Object valor = ret.getValor(tabla, salida);
                                        Tipo tipo = ret.getTipo(tabla);

                                        if (valor == null) {
                                            tipo = Tipo.NULL;
                                            valor = "nulo";
                                        }

                                        Literal l = new Literal(tipo, valor, linea, columna);
                                        tabla.pollLast();
                                        return l;
                                    } else {
                                        System.err.println("Error, Sentencia Retornar no se encuentra dentro de un método o función. Línea :" + ret.getLinea());
                                    }
                                }
                            }
                        }
                        tabla.pollLast();
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    System.err.println("Error, la condicion debe ser booleana. Linea:" + linea);
                }
            }
        } else {
            tabla.add(new Simbolos());
            for (NodoAST bloque : bloques) {
                if (bloque instanceof Instruccion) {
                    Object o = ((Instruccion) bloque).ejecutar(tabla, salida, fun, sel);
                    
                    if (o != null) {
                        if (o instanceof Literal) {
                            Literal lit = (Literal) o;
                            if (fun) {
                                tabla.pollLast();
                                return lit;
                            } else {
                                System.err.println("Error, Sentencia Retornar no se encuentra dentro de un método o función. Línea :" + lit.getLinea());

                            }
                        }
                    }
                } else {
                    if (bloque instanceof Retornar) {
                        Retornar ret = (Retornar) bloque;
                        if (fun) {
                            Object valor = ret.getValor(tabla, salida);
                            Tipo tipo = ret.getTipo(tabla);

                            if (valor == null) {
                                tipo = Tipo.NULL;
                                valor = "nulo";
                            }

                            Literal l = new Literal(tipo, valor, linea, columna);
                            tabla.pollLast();
                            return l;
                        } else {
                            System.err.println("Error, Sentencia Retornar no se encuentra dentro de un método o función. Línea :" + ret.getLinea());
                        }
                    }
                }
            }
            tabla.pollLast();
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
