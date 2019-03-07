/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.operacion;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.Tipo;


/**
 *
 * @author oscar
 */
public class Operacion{
    Expresion op1;
    Expresion op2;
    Operador operador;
    int linea;
    int columna;
    Tipo tipo;

    public Operacion(Expresion op1, Expresion op2, Operador operador, int linea, int columna) {
        this.op1 = op1;
        this.op2 = op2;
        this.operador = operador;
        this.linea = linea;
        this.columna = columna;
    }
    
    
    
    public static enum Operador{
        MAS,
        MENOS,
        ASTERISCO,
        BARRA,
        INTERCALACION,
        AUMENTO,
        DECREMENTO,
        MAYORQUE,
        MENORQUE,
        MAYORIGUALQUE,
        MENORIGUALQUE,
        IGUAL,
        DIFERENTE,
        AND,
        OR,
        NOT
        
    }
}
