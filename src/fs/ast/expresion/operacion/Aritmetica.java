/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.operacion;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Aritmetica extends Operacion implements Expresion {

    private boolean unario;

    public Aritmetica(Expresion op1, Expresion op2, Operador operador, int linea, int columna) {
        super(op1, op2, operador, linea, columna);
        unario = false;
    }

    public Aritmetica(Expresion op1, Operador operador, int linea, int columna) {
        super(op1, null, operador, linea, columna);
        unario = true;
    }

    @Override
    public Tipo getTipo(TablaSimbolo tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolo tabla, JTextArea salida) {
        Object val1 = op1.getValor(tabla, salida);
        Tipo tip1 = op1.getTipo(tabla);

        if (unario) {
            if (tip1 != null) {
                switch (operador) {
                    case MAS:
                        if (tip1 == Tipo.ENTERO) {
                            tipo = Tipo.ENTERO;
                            return new Integer(val1.toString());
                        } else if (tip1 == Tipo.DECIMAL) {
                            tipo = Tipo.DECIMAL;
                            return new Double(val1.toString());
                        } else {
                            System.err.println("Error de tipos, solo los números pueden tener signo. Linea" + linea);
                        }
                        return null;
                    case MENOS:
                        if (tip1 == Tipo.ENTERO) {
                            tipo = Tipo.ENTERO;
                            return new Integer(val1.toString()) * -1;
                        } else if (tip1 == Tipo.DECIMAL) {
                            tipo = Tipo.DECIMAL;
                            return new Double(val1.toString()) * -1;
                        } else {
                            System.err.println("Error de tipos, solo los números pueden tener signo. Linea" + linea);
                        }
                        return null;
                    case AUMENTO:
                        //Agregar aumento id
                        return null;
                    case DECREMENTO:
                        return null;
                }
            }
        } else {
            Object val2 = op2.getValor(tabla, salida);
            Tipo tip2 = op2.getTipo(tabla);

            if (tip1 != null && tip2 != null) {
                if (tip1 != Tipo.VAR && tip2 != Tipo.VAR) {
                    if (tip1 != Tipo.NULL && tip2 != Tipo.NULL) {
                        if (tip1 != Tipo.OBJETO && tip2 != Tipo.OBJETO) {
                            if (tip1 != Tipo.ARREGLO && tip2 != Tipo.ARREGLO) {
                                switch (operador) {
                                    case MAS:
                                        if (tip1 == Tipo.CADENA || tip2 == Tipo.CADENA) {
                                            tipo = Tipo.CADENA;
                                            return val1.toString() + val2.toString();
                                        } else if (tip1 != Tipo.BOOLEANO && tip2 != Tipo.BOOLEANO) {
                                            if (tip1 == Tipo.DECIMAL || tip2 == Tipo.DECIMAL) {
                                                tipo = Tipo.DECIMAL;
                                                return new Double(val1.toString()) + new Double(val2.toString());
                                            } else {
                                                tipo = Tipo.ENTERO;
                                                return new Integer(val1.toString()) + new Integer(val2.toString());
                                            }
                                        }
                                        System.err.println("Error de tipos, la suma no se pudo realizar. Línea:" + linea);
                                        return null;
                                    case MENOS:
                                        if (tip1 != Tipo.BOOLEANO && tip2 != Tipo.BOOLEANO) {
                                            if (tip1 != Tipo.CADENA && tip2 != Tipo.CADENA) {
                                                if (tip1 == tipo.DECIMAL || tip2 == tipo.DECIMAL) {
                                                    tipo = Tipo.DECIMAL;
                                                    return new Double(val1.toString()) - new Double(val2.toString());
                                                } else {
                                                    tipo = Tipo.ENTERO;
                                                    return new Integer(val1.toString()) - new Integer(val2.toString());
                                                }
                                            }
                                        }
                                        System.err.println("Error de tipos, la resta no se pudo realizar. Línea:" + linea);
                                        return null;
                                    case ASTERISCO:
                                        if (tip1 != Tipo.BOOLEANO && tip2 != Tipo.BOOLEANO) {
                                            if (tip1 != Tipo.CADENA && tip2 != Tipo.CADENA) {
                                                if (tip1 == Tipo.DECIMAL || tip2 == Tipo.DECIMAL) {
                                                    tipo = Tipo.DECIMAL;
                                                    return new Double(val1.toString()) * new Double(val2.toString());
                                                } else {
                                                    tipo = Tipo.ENTERO;
                                                    return new Integer(val1.toString()) * new Integer(val2.toString());
                                                }
                                            }
                                        }
                                        System.err.println("Error de tipos, la multiplicación no se pudo realizar. Línea:" + linea);
                                        return null;
                                    case BARRA:
                                        if (tip1 != Tipo.BOOLEANO && tip2 != Tipo.BOOLEANO) {
                                            if (tip1 != Tipo.CADENA && tip2 != Tipo.CADENA) {
                                                if (Double.valueOf(val2.toString()) != 0) {
                                                    tipo = Tipo.DECIMAL;
                                                    return new Double(val1.toString()) / new Double(val2.toString());
                                                } else {
                                                    System.err.println("Error! División entre 0. Línea:" + linea);
                                                    return null;
                                                }
                                            }
                                        }
                                        System.err.println("Error de tipos, la división no se pudo realizar. Línea:" + linea);
                                        return null;
                                    case INTERCALACION:
                                        if (tip1 != Tipo.CADENA && tip2 != Tipo.CADENA) {
                                            if (tip1 == Tipo.DECIMAL || tip2 == Tipo.DECIMAL) {
                                                tipo = Tipo.DECIMAL;
                                                if (tip1 != Tipo.BOOLEANO && tip2 != Tipo.BOOLEANO) {
                                                    return Math.pow(new Double(val1.toString()), new Double(val2.toString()));
                                                } else {
                                                    if (tip1 == Tipo.BOOLEANO) {
                                                        int tmp = val1.toString().equals("verdadero") ? 1 : 0;
                                                        return Math.pow(tmp, new Double(val2.toString()));
                                                    } else {
                                                        int tmp = val2.toString().equals("verdadero") ? 1 : 0;
                                                        return Math.pow(new Double(val1.toString()), tmp);
                                                    }
                                                }
                                            } else if (tip1 == Tipo.ENTERO || tip2 == Tipo.ENTERO) {
                                                tipo = Tipo.ENTERO;
                                                if (tip1 != Tipo.BOOLEANO && tip2 != Tipo.BOOLEANO) {
                                                    return (int) Math.pow(new Integer(val1.toString()), new Integer(val2.toString()));
                                                } else {
                                                    if (tip1 == Tipo.BOOLEANO) {
                                                        int tmp = val1.toString().equals("verdadero") ? 1 : 0;
                                                        return (int) Math.pow(tmp, new Double(val2.toString()));
                                                    } else {
                                                        int tmp = val2.toString().equals("verdadero") ? 1 : 0;
                                                        return (int) Math.pow(new Double(val1.toString()), tmp);
                                                    }
                                                }
                                            } else {
                                                int tmp1 = val1.equals("verdadero") ? 1 : 0;
                                                int tmp2 = val2.equals("verdadero") ? 1 : 0;
                                                return (int) Math.pow(tmp1, tmp2);
                                            }
                                        }
                                        System.err.println("Error de tipos, la potencia no se pudo realizar. Línea:" + linea);
                                        return null;
                                }
                            } else {
                                System.err.println("Error! No se puede realizar la operación aritmética con arreglos. Línea:" + linea);
                            }
                        } else {
                            System.err.println("Error! No se puede realizar la operación aritmética con objetos. Línea:" + linea);
                        }
                    } else {
                        System.err.println("Error! No se puede realizar la operación aritmética con valor nulo. Línea:" + linea);
                    }
                } else {
                    if (tip1 == Tipo.VAR) {
                        System.err.println("Error! Variable \"" + val1.toString() + "\" indefinida. Linea:" + linea);
                    } else {
                        System.err.println("Error! Variable \"" + val2.toString() + "\" indefinida. Linea:" + linea);
                    }
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
