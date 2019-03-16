/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.operacion;

import fs.ast.expresion.AccesoArreglo;
import fs.ast.expresion.AccesoObjeto;
import fs.ast.expresion.Expresion;
import fs.ast.expresion.Identificador;
import fs.ast.expresion.Literal;
import fs.ast.instruccion.Asignacion;
import fs.ast.instruccion.AsignacionArreglo;
import fs.ast.instruccion.AsignacionObjeto;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Aritmetica extends Operacion implements Expresion {

    private final boolean unario;

    public Aritmetica(Expresion op1, Expresion op2, Operador operador, int linea, int columna) {
        super(op1, op2, operador, linea, columna);
        unario = false;
    }

    public Aritmetica(Expresion op1, Operador operador, int linea, int columna) {
        super(op1, null, operador, linea, columna);
        unario = true;
    }

    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida) {
        Object val1 = op1.getValor(tabla, salida);
        Tipo tip1 = op1.getTipo(tabla);

        if (unario) {
            if (tip1 != null) {
                switch (operador) {
                    case MAS:
                        switch (tip1) {
                            case ENTERO:
                                tipo = Tipo.ENTERO;
                                return new Integer(val1.toString());
                            case DECIMAL:
                                tipo = Tipo.DECIMAL;
                                return new Double(val1.toString());
                            default:
                                System.err.println("Error de tipos, solo los números pueden tener signo. Linea " + linea);
                                return null;
                        }
                    case MENOS:
                        switch (tip1) {
                            case ENTERO:
                                tipo = Tipo.ENTERO;
                                return new Integer(val1.toString()) * -1;
                            case DECIMAL:
                                tipo = Tipo.DECIMAL;
                                return new Double(val1.toString()) * -1;
                            default:
                                System.err.println("Error de tipos, solo los números pueden tener signo. Linea" + linea);
                                return null;
                        }
                    case AUMENTO:
                    case DECREMENTO:
                        if (tip1.isNumero()) {

                            Object val;
                            if (tip1 == Tipo.ENTERO) {
                                if (operador == Operador.AUMENTO) {
                                    val = new Integer(val1.toString()) + 1;
                                } else {
                                    val = new Integer(val1.toString()) - 1;
                                }
                            } else {
                                if (operador == Operador.AUMENTO) {
                                    val = new Double(val1.toString()) + 1;
                                } else {
                                    val = new Double(val1.toString()) - 1;
                                }
                            }

                            Literal exp = new Literal(tip1, val, linea, columna);

                            if (op1 instanceof Identificador) {
                                Identificador id = (Identificador) op1;
                                Asignacion asigna = new Asignacion(id.getId(), "=", exp, linea, columna);
                                asigna.ejecutar(tabla, salida, false, false);
                            } else if (op1 instanceof AccesoArreglo) {
                                AccesoArreglo ar = (AccesoArreglo) op1;
                                AsignacionArreglo asigna = new AsignacionArreglo(ar.getId(), ar.getPosicion(), "=", exp, linea, columna);
                                asigna.ejecutar(tabla, salida, false, false);
                            } else if (op1 instanceof AccesoObjeto) {
                                AccesoObjeto obj = (AccesoObjeto) op1;
                                AsignacionObjeto asigna = new AsignacionObjeto(obj.getId(), obj.getClave(), obj.getPosicion(), "=", exp, linea, columna);
                                asigna.ejecutar(tabla, salida, false, false);
                            }

                            switch (tip1) {
                                case ENTERO:
                                    tipo = Tipo.ENTERO;
                                    return new Integer(val1.toString());
                                case DECIMAL:
                                    tipo = Tipo.DECIMAL;
                                    return new Double(val1.toString());
                            }
                        } else {
                            if (operador == Operador.AUMENTO) {
                                System.err.println("Error de tipos, solo los números se pueden aumentar. Línea: " + linea);
                            } else {
                                System.err.println("Error de tipos, solo los números se pueden decrementar. Línea: " + linea);
                            }
                            return null;
                        }
                    default:
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
                        System.err.println("Error! Variable indefinida. Linea:" + linea);
                    } else {
                        System.err.println("Error! Variable indefinida. Linea:" + linea);
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
