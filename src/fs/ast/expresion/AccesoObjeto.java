/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class AccesoObjeto  implements Expresion{
    private final String id;
    private final String clave;
    private Tipo tipo;
    private final int linea;
    private final int columna;    

    public AccesoObjeto(String id, String clave, int linea, int columna) {
        this.id = id;
        this.clave = clave;
        this.tipo = null;
        this.linea = linea;
        this.columna = columna;
    }
    
    @Override
    public Tipo getTipo(TablaSimbolo tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolo tabla, JTextArea salida) {
        Tipo tip = tabla.getTipo(id);
        if(tip != null){
            if(tip == Tipo.OBJETO){
                Object val = tabla.getValorObjeto(id);
                if(val != null){
                    Map<String, Expresion> valores = (Map<String, Expresion>) val;
                    Object valor = valores.get(clave);
                    if(valor != null){
                        this.tipo = Tipo.CADENA;
                        return valor;
                    } else {
                        System.err.println("Error, La clave \"" + clave + "\" no está en el objeto. Línea:"+linea);
                    }
                } else {
                    System.err.println("Error, objeto \"" + id + "\" indefinido. Línea:"+linea);
                }
            } else {
                System.err.println("Error, variable \"" + id + "\" no es un objeto. Línea:"+linea);
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
