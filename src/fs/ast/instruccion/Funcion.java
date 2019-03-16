/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.NodoAST;
import fs.ast.simbolos.FuncionSim;
import fs.ast.simbolos.TablaSimbolos;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Funcion implements Instruccion {

    private final String id;
    private final LinkedList<String> parametros;
    private final LinkedList<NodoAST> bloques;
    private final int linea;
    private final int columna;

    public Funcion(String id, LinkedList<NodoAST> bloques, int linea, int columna) {
        this.id = id;
        this.parametros = null;
        this.bloques = bloques;
        this.linea = linea;
        this.columna = columna;
    }

    public Funcion(String id, LinkedList<String> parametros, LinkedList<NodoAST> bloques, int linea, int columna) {
        this.id = id;
        this.parametros = parametros;
        this.bloques = bloques;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel) {
        FuncionSim tmp;
        
        if(this.parametros != null){
            tmp = tabla.getFuncion(id, this.parametros.size());
        } else {
            tmp = tabla.getFuncion(id);
        }

        if (tmp != null) {
            System.err.println("Error, Ya existe una funcion definida con el nombre de \"" + id + "\". Línea: " + linea);
            return null;
        }

        FuncionSim funcion = new FuncionSim(parametros, bloques, id);
        tabla.addSimbolo(funcion);

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
