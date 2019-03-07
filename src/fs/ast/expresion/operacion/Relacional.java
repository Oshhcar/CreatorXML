/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.operacion;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.Objects;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Relacional extends Operacion implements Expresion {

    public Relacional(Expresion op1, Expresion op2, Operador operador, int linea, int columna) {
        super(op1, op2, operador, linea, columna);
    }

    @Override
    public Tipo getTipo(TablaSimbolo tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolo tabla, JTextArea salida) {
        Object val1 = op1.getValor(tabla, salida);
        Object val2 = op2.getValor(tabla, salida);
        Tipo tip1 = op1.getTipo(tabla);
        Tipo tip2 = op2.getTipo(tabla);

        if (tip1 != null && tip2 != null) {
            tipo = Tipo.BOOLEANO;
            if (tip1.isNumero() && tip2.isNumero()) {
                switch (operador) {
                    case MAYORQUE:
                        return (new Double(val1.toString()) > new Double(val2.toString())) ? "verdadero" : "falso";
                    case MENORQUE:
                        return (new Double(val1.toString()) < new Double(val2.toString())) ? "verdadero" : "falso";
                    case MAYORIGUALQUE:
                        return (new Double(val1.toString()) >= new Double(val2.toString())) ? "verdadero" : "falso";
                    case MENORIGUALQUE:
                        return (new Double(val1.toString()) <= new Double(val2.toString())) ? "verdadero" : "falso";
                    case IGUAL:
                        return (Objects.equals(new Double(val1.toString()), new Double(val2.toString()))) ? "verdadero" : "falso";
                    case DIFERENTE:
                        return (!Objects.equals(new Double(val1.toString()), new Double(val2.toString()))) ? "verdadero" : "falso";
                }

            } else if (tip1 == Tipo.CADENA && tip2 == Tipo.CADENA) {
                int valCad1 = getValorCadena(val1.toString());
                int valCad2 = getValorCadena(val2.toString());
                switch (operador) {
                    case MAYORQUE:
                        return (valCad1 > valCad2) ? "verdadero" : "falso";
                    case MENORQUE:
                        return (valCad1 < valCad2) ? "verdadero" : "falso";
                    case MAYORIGUALQUE:
                        return (valCad1 >= valCad2) ? "verdadero" : "falso";
                    case MENORIGUALQUE:
                        return (valCad1 <= valCad2) ? "verdadero" : "falso";
                    case IGUAL:
                        return (valCad1 == valCad2) ? "verdadero" : "falso";
                    case DIFERENTE:
                        return (valCad1 != valCad2) ? "verdadero" : "falso";
                }
            } else if (tip1 == Tipo.BOOLEANO && tip2 == Tipo.BOOLEANO) {
                int valBool1 = val1.equals("verdadero") ? 1 : 0;
                int valBool2 = val2.equals("verdadero") ? 1 : 0;
                switch (operador) {
                    case MAYORQUE:
                        return (valBool1 > valBool2) ? "verdadero" : "falso";
                    case MENORQUE:
                        return (valBool1 < valBool2) ? "verdadero" : "falso";
                    case MAYORIGUALQUE:
                        return (valBool1 >= valBool2) ? "verdadero" : "falso";
                    case MENORIGUALQUE:
                        return (valBool1 <= valBool2) ? "verdadero" : "falso";
                    case IGUAL:
                        return (valBool1 == valBool2) ? "verdadero" : "falso";
                    case DIFERENTE:
                        return (valBool1 != valBool2) ? "verdadero" : "falso";
                }
            } else {
                System.err.println("Error de tipos! No se pudo realizar la operacion relacional. Linea:" + linea);
                tipo = null;
                return null;
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

    public int getValorCadena(String cad) {
        int res = 0;
        for (int i = 0; i < cad.length(); i++) {
            res = res + cad.charAt(i);
        }
        return res;
    }

}
