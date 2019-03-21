/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.ast.NodoAST;
import fs.ast.expresion.Expresion;
import fs.ast.expresion.Literal;
import fs.ast.expresion.Retornar;
import fs.ast.expresion.nativas.CrearArray;
import fs.ast.expresion.nativas.CrearVentana;
import fs.ast.expresion.nativas.LeerGxml;
import fs.ast.simbolos.FuncionSim;
import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.Simbolos;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class LlamadaMetodo implements Instruccion {

    private final String id;
    private final LinkedList<Expresion> parametros;
    private final int linea;
    private final int columna;

    public LlamadaMetodo(String id, LinkedList<Expresion> parametros, int linea, int columna) {
        this.id = id;
        this.parametros = parametros;
        this.linea = linea;
        this.columna = columna;
    }

    public LlamadaMetodo(String id, int linea, int columna) {
        this.id = id;
        this.parametros = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel, String dirActual) {
        if (!id.toLowerCase().equals("creararraydesdearchivo") && !id.toLowerCase().equals("leergxml")
                && !id.toLowerCase().equals("crearventana")) {
            FuncionSim funcion;

            if (this.parametros != null) {
                funcion = tabla.getFuncion(id, this.parametros.size());
            } else {
                funcion = tabla.getFuncion(id);
            }

            if (funcion != null) {
                Simbolos local = new Simbolos();
                if (this.getParametros() != null && funcion.getParametros() != null) {
                    for (int i = 0; i < this.getParametros().size(); i++) {
                        String idActual = funcion.getParametros().get(i);
                        Simbolo sim = new Simbolo(Tipo.VAR, idActual);
                        Expresion expActual = this.getParametros().get(i);
                        Object valActual = expActual.getValor(tabla, salida, dirActual);
                        Tipo tipActual = expActual.getTipo(tabla);

                        if (tipActual != null) {
                            if (valActual != null) {
                                sim.setValor(valActual);
                            } else {
                                sim.setTipo(tipActual);
                                sim.setValor(valActual);
                            }
                        } else {
                            System.err.println("Error, no se puede asignar el parametro. Linea:" + linea);
                            return null;
                        }
                        local.add(sim);
                    }
                }
                tabla.add(local);

                for (NodoAST bloque : funcion.getBloques()) {
                    if (bloque instanceof Instruccion) {
                        Object o = ((Instruccion) bloque).ejecutar(tabla, salida, true, false, dirActual);
                        if (o != null) {
                            if (o instanceof Literal) {
                                Literal lit = (Literal) o;
                                Object litVal = ((Literal) o).getValor(tabla, salida, dirActual);
                                if (litVal != null) {
                                    //System.err.println("Error, El método \"" + id + "\" retorna valor. Línea: " + linea);

                                }
                                tabla.pollLast();
                                return litVal;
                            }
                        }
                    } else {
                        if (bloque instanceof Retornar) {
                            Retornar ret = (Retornar) bloque;
                            Object valRet = ret.getValor(tabla, salida, dirActual);
                            if (valRet != null) {
                                //System.err.println("Error, El método \"" + id + "\" retorna valor. Línea: " + linea);
                            }
                            tabla.pollLast();
                            return valRet;
                        }
                    }
                }
                tabla.pollLast();
            } else {
                System.err.println("Error, el método \"" + id + "\" no está declarado. Línea: " + linea);

            }
        } else {
            if (id.toLowerCase().equals("creararraydesdearchivo")) {
                CrearArray crearArray = new CrearArray(this.parametros, linea, columna);
                Object valRet = crearArray.getValor(tabla, salida, dirActual);
                return valRet;
            } else if (id.toLowerCase().equals("leergxml")){
                LeerGxml leerGxml = new LeerGxml(this.parametros, linea, columna);
                Object valRet = leerGxml.getValor(tabla, salida, dirActual);
                return valRet;
            } else if(id.toLowerCase().equals("crearventana")){
                CrearVentana ventana = new CrearVentana(this.parametros, linea, columna);
                Object valRet = ventana.getValor(tabla, salida, dirActual);
                return valRet;
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
     * @return the parametros
     */
    public LinkedList<Expresion> getParametros() {
        return parametros;
    }

}
