/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.NodoAST;
import fs.ast.simbolos.TablaSimbolo;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public interface Instruccion extends NodoAST {
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida);
}
