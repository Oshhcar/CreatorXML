/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Asignacion implements Instruccion {

    private final Tipo tipo;
    private final String id;
    private final Object valor;
    private Map<String, Expresion> elementos;
    private final int linea;
    private final int columna;

    public Asignacion(Tipo tipo, String id, Object valor, int linea, int columna) {
        this.tipo = tipo;
        this.id = id;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    public Asignacion(Tipo tipo, String id, int linea, int columna) {
        this.tipo = tipo;
        this.id = id;
        this.valor = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolo tabla, JTextArea salida) {
        
        if (tipo == Tipo.VAR) {
            if (valor != null) {
                Expresion exp = (Expresion) valor;
                Object val = exp.getValor(tabla, salida);
                if (val != null) {
                    tabla.setValor(getId(), val);
                }
            }
        } else if(tipo == Tipo.OBJETO){
            if(valor != null) {
                Map<String, Expresion> actual = (Map<String, Expresion>) valor;
                Map<String, Object> valores = new HashMap<String, Object>();
                
                Iterator i = actual.keySet().iterator();
                while(i.hasNext()){
                    String claveActual = (String) i.next();
                    Expresion expActual = actual.get(claveActual);
                    Object valActual = expActual.getValor(tabla, salida);
                    
                    if(valActual != null){
                        valores.put(claveActual, valActual);
                    }
                }
                if(valores.size() > 0){
                    tabla.setValor(getId(), valores);
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

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

}
