/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion.condicionales;

import fs.ast.expresion.Expresion;
import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.TablaSimbolo;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Selecciona implements Instruccion{
    private final Expresion expSiwtch;
    private final LinkedList<Caso> casos;
    private final int linea;
    private final int columna;
    private boolean isContinuar;
    
    public Selecciona(Expresion expSiwtch, LinkedList<Caso> casos, int linea, int columna) {
        this.expSiwtch = expSiwtch;
        this.casos = casos;
        this.linea = linea;
        this.columna = columna;
        this.isContinuar = false;
    }
    
    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        for(Caso caso: casos){
            caso.setExpSwitch(expSiwtch);
            if(isContinuar)
                caso.setContinuar(isContinuar);
            
            caso.ejecutar(tabla, salida);
            isContinuar = caso.isContinuar();
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
