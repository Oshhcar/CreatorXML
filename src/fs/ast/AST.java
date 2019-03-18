/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast;

import fs.ast.instruccion.Funcion;
import fs.ast.instruccion.Importar;
import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.TablaSimbolos;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class AST {    
    private LinkedList<NodoAST> nodos;
    
    public AST(LinkedList<NodoAST> nodos) {
        this.nodos = nodos;
    }

    public void ejecutar(JTextArea salida, String dirActual) {
        TablaSimbolos global = new TablaSimbolos();
        //hacer varias pasadas por las funciones

        for (NodoAST nodo : getNodos()) {
            if (nodo instanceof Instruccion) {
                if (nodo instanceof Funcion) {
                    ((Funcion) nodo).ejecutar(global, salida, false, false, dirActual);
                }
            }
        }

        for (NodoAST nodo : getNodos()) {
            if (nodo instanceof Instruccion) {
                if (nodo instanceof Importar) {
                    Importar imp = (Importar) nodo;
                    imp.ejecutar(global, salida, false, false, dirActual);
                }
            }
        }

        for (NodoAST nodo : getNodos()) {
            if (nodo instanceof Instruccion) {
                if (!(nodo instanceof Funcion) && !(nodo instanceof Importar)) {
                    ((Instruccion) nodo).ejecutar(global, salida, false, false, dirActual);
                }
            }
        }
    }

    /**
     * @return the nodos
     */
    public LinkedList<NodoAST> getNodos() {
        return nodos;
    }
}
