/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.simbolos;

import fs.ast.NodoAST;
import fs.ast.instruccion.Declaracion;
import java.util.LinkedList;

/**
 *
 * @author oscar
 */
public class FuncionSim extends Simbolo{
    private LinkedList<String> parametros;
    private LinkedList<NodoAST> bloques;

    public FuncionSim(LinkedList<String> parametros, LinkedList<NodoAST> bloques, String id) {
        super(Tipo.FUNCION, id);
        this.parametros = parametros;
        this.bloques = bloques;
    }
    
        /**
     * @return the parametros
     */
    public LinkedList<String> getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(LinkedList<String> parametros) {
        this.parametros = parametros;
    }

    /**
     * @return the bloques
     */
    public LinkedList<NodoAST> getBloques() {
        return bloques;
    }

    /**
     * @param bloques the bloques to set
     */
    public void setBloques(LinkedList<NodoAST> bloques) {
        this.bloques = bloques;
    }
}
