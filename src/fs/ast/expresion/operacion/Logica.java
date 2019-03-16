/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.operacion;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Logica extends Operacion implements Expresion {

    public Logica(Expresion op1, Expresion op2, Operador operador, int linea, int columna) {
        super(op1, op2, operador, linea, columna);
    }

    public Logica(Expresion op1, int linea, int columna) {
        super(op1, null, Operacion.Operador.NOT, linea, columna);
    }

    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida) {
        Object val1 = op1.getValor(tabla, salida);
        Object val2 = op2 == null ? null : op2.getValor(tabla, salida);
        Tipo tip1 = op1.getTipo(tabla);
        Tipo tip2 = op2 == null ? null : op2.getTipo(tabla);
        
        if (operador == Operacion.Operador.NOT) {
            if(tip1 != null){
                if(tip1 == Tipo.BOOLEANO) {
                    tipo = Tipo.BOOLEANO;
                    return val1.equals("verdadero") ? "falso" : "verdadero"; 
                } else {
                    System.err.println("Error de tipos! No se pudo realizar la operacion lógica. Linea:" + linea);
                }
               
            }
        } else {
            if (tip1 != null && tip2 != null) {
                boolean valBool1 = val1.equals("verdadero") ? true : false;
                boolean valBool2 = val2.equals("verdadero") ? true : false;
                if (tip1 == Tipo.BOOLEANO && tip2 == Tipo.BOOLEANO) {
                    switch (operador) {
                        case AND:
                            tipo = Tipo.BOOLEANO;
                            return valBool1 && valBool2 ? "verdadero" : "falso";
                        case OR:
                            tipo = Tipo.BOOLEANO;
                            return valBool1 || valBool2 ? "verdadero" : "falso";
                    }
                } else {
                    System.err.println("Error de tipos! No se pudo realizar la operacion lógica. Linea:" + linea);
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
