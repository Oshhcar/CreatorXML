/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.simbolos.Tipo;
import fs.ast.NodoAST;
import fs.ast.simbolos.TablaSimbolo;

/**
 *
 * @author oscar
 */
public interface Expresion extends NodoAST{
    public Tipo getTipo(TablaSimbolo tabla);
    public Object getValor(TablaSimbolo tabla);
}
