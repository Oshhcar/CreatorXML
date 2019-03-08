/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion.condicionales;

import fs.ast.NodoAST;
import fs.ast.expresion.Expresion;
import fs.ast.expresion.Retornar;
import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.TablaSimbolo;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Si implements Instruccion{

    private final LinkedList<SubSi> subSis;
    private final int linea;
    private final int columna;
    
    public Si(Expresion condicion, LinkedList<NodoAST> bloques, int linea, int columna) {
        this.subSis = new LinkedList<>();
        this.subSis.add(new SubSi(condicion,bloques, linea, columna));
        this.linea = linea;
        this.columna = columna;
    }
    
    public Si(Expresion condicion, LinkedList<NodoAST> bloques, int linea, int columna, SubSi sino) {
        this.subSis = new LinkedList<>();
        this.subSis.add(new SubSi(condicion,bloques, linea, columna));
        this.subSis.add(sino);
        this.linea = linea;
        this.columna = columna;
    }
    
    public Si(Expresion condicion, LinkedList<NodoAST> bloques, int linea, int columna, LinkedList<SubSi> subSis) {
        this.subSis = new LinkedList<>();
        this.subSis.add(new SubSi(condicion,bloques, linea, columna));
        this.subSis.addAll(subSis);
        this.linea = linea;
        this.columna = columna;
    }
    
    public Si(Expresion condicion, LinkedList<NodoAST> bloques, int linea, int columna, LinkedList<SubSi> subSis, SubSi sino) {
        this.subSis = new LinkedList<>();
        this.subSis.add(new SubSi(condicion,bloques, linea, columna));
        this.subSis.addAll(subSis);
        this.subSis.add(sino);
        this.linea = linea;
        this.columna = columna;
    }
    
    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        for(SubSi si: subSis){
            Object r = si.ejecutar(tabla, salida);
            if(r != null){
                if(r instanceof Boolean){
                    if((boolean)r){
                        return null;
                    }
                } else{
                    return r;
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
