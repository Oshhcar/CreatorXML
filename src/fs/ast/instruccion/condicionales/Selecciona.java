/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion.condicionales;

import fs.ast.expresion.Expresion;
import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.TablaSimbolos;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Selecciona implements Instruccion {

    private final Expresion expSiwtch;
    private final LinkedList<Caso> casos;
    private final int linea;
    private final int columna;
    private boolean isContinuar;
    private boolean ejecutarDefecto;
    
    public Selecciona(Expresion expSiwtch, LinkedList<Caso> casos, int linea, int columna) {
        this.expSiwtch = expSiwtch;
        this.casos = casos;
        this.linea = linea;
        this.columna = columna;
        this.isContinuar = false;
        this.ejecutarDefecto = true;
        
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel, String dirActual) {
        for (Caso caso : casos) {
            caso.setExpSwitch(expSiwtch);
            
            if (isContinuar) {
                caso.setContinuar(isContinuar);
            }
            
            if(!ejecutarDefecto){
                caso.setEjecutarDefecto(ejecutarDefecto);
            }

            Object o = caso.ejecutar(tabla, salida, fun, true, dirActual);
            if(o != null){
                return o;
            }
            isContinuar = caso.isContinuar();
            ejecutarDefecto = caso.isEjecutarDefecto();
            
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
