/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast;

import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.Simbolos;
import fs.ast.simbolos.TablaSimbolos;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class AST {
    LinkedList<NodoAST> nodos;
    
    public AST(LinkedList<NodoAST> nodos) {
        this.nodos = nodos;
    }
    
    public void ejecutar(JTextArea salida){
        TablaSimbolos global = new TablaSimbolos();
        //hacer varias pasadas por las funciones
        
        for(NodoAST nodo: nodos){
            if(nodo instanceof Instruccion){
                ((Instruccion) nodo).ejecutar(global, salida, false, false);
            }
        }
    }
}
