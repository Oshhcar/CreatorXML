/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.TablaSimbolo;
import fs.ast.simbolos.Tipo;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Asignacion implements Instruccion {

    protected final Tipo tipo;
    protected final String id;
    protected final Object valor;
    protected Map<String, Expresion> elementos;
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

        if (null != valor) {
            switch (tipo) {
                case VAR:
                    Expresion exp = (Expresion) valor;
                    Object val = exp.getValor(tabla, salida);
                    if (val != null) {
                        tabla.setValor(getId(), val);
                    }

                    break;
                default:
                    break;
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
