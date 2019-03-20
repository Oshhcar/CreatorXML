/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.nativas;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.Objeto;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Obtener implements Expresion {
    
    private final Object array;
    private final LinkedList<Expresion> parametros;
    private Tipo tipo;
    private int modo;
    private final int linea;
    private final int columna;
    
    public Obtener(Object array, LinkedList<Expresion> parametros, int modo, int linea, int columna) {
        this.array = array;
        this.parametros = parametros;
        this.tipo = null;
        this.modo = modo;
        this.linea = linea;
        this.columna = columna;
    }
    
    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        return tipo;
    }
    
    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida, String dirActual) {
        if (array instanceof Arreglo) {
            if (parametros.size() >= 1) {
                Expresion parm = this.parametros.getFirst();
                Object valParm = parm.getValor(tabla, salida, dirActual);
                if (valParm != null) {
                    Tipo tipParm = parm.getTipo(tabla);
                    if (tipParm == Tipo.CADENA) {
                        String id = (String) valParm;
                        String ventana = "";
                        
                        if (modo == 3) {
                            if (parametros.size() >= 2) {
                                Expresion parm2 = this.parametros.get(1);
                                Object valParm2 = parm2.getValor(tabla, salida, dirActual);
                                if (valParm2 != null) {
                                    Tipo tipParm2 = parm2.getTipo(tabla);
                                    if (tipParm2 == Tipo.CADENA) {
                                        ventana = (String) valParm2;
                                    } else {
                                        System.err.println("Error, el parametro debe ser cadena. Linea: " + linea);
                                        return null;
                                    }
                                } else {
                                    return null;
                                }
                            } else {
                                System.err.println("Error, la funcion Obtener debe recibir parámetros. Linea: " + linea);
                                return null;
                            }
                        }
                        
                        Map<Integer, Object> arreglo = (Map<Integer, Object>) array;
                        Map<Integer, Object> obtener = new Arreglo();
                        int j = 0;
                        
                        if (arreglo != null) {
                            for (int i = 0; i < arreglo.size(); i++) {
                                Objeto obj = null;
                                
                                try {
                                    obj = (Objeto) arreglo.get(i);
                                } catch (Exception e) {
                                    
                                }
                                
                                if (obj != null) {
                                    switch (modo) {
                                        case 1: //porEtiqueta
                                            if (obj.containsKey("etiqueta")) {
                                                if (obj.get("etiqueta").toString().equals(id)) {
                                                    obtener.put(j++, obj);
                                                }
                                            }
                                            break;
                                        case 2: //porId
                                            if (obj.containsKey("id")) {
                                                if (obj.get("id").toString().equals(id)) {
                                                    tipo = Tipo.OBJETO;
                                                    return obj;
                                                }
                                            }
                                            break;
                                        case 3:
                                            if (obj.containsKey("nombre")) {
                                                if (obj.get("nombre").toString().equals(id)) {
                                                    tipo = Tipo.OBJETO;
                                                    return obj;
                                                }
                                            }
                                            break;
                                    }
                                    if (obj.containsKey("etiquetas")) {
                                        try {
                                            Map<Integer, Object> etiquetas = (Arreglo) obj.get("etiquetas");
                                            if (etiquetas != null) {
                                                for (int k = 0; k < etiquetas.size(); k++) {
                                                    Objeto obj2 = null;
                                                    
                                                    try {
                                                        obj2 = (Objeto) etiquetas.get(k);
                                                    } catch (Exception e) {
                                                    }
                                                    
                                                    if (obj2 != null) {
                                                        switch (modo) {
                                                            case 1: //porEtiqueta
                                                                if (obj2.containsKey("etiqueta")) {
                                                                    if (obj2.get("etiqueta").toString().equals(id)) {
                                                                        obtener.put(j++, obj2);
                                                                    }
                                                                }
                                                                break;
                                                            case 2: //porId
                                                                if (obj2.containsKey("id")) {
                                                                    if (obj.get("id").toString().equals(id)) {
                                                                        tipo = Tipo.OBJETO;
                                                                        return obj2;
                                                                    }
                                                                }
                                                                break;
                                                            case 3:
                                                                if (obj2.containsKey("nombre") && obj.containsKey("id")) {
                                                                    if (obj2.get("nombre").toString().equals(id) && obj.get("id").equals(ventana)) {
                                                                        tipo = Tipo.OBJETO;
                                                                        return obj2;
                                                                    }
                                                                }
                                                                break;
                                                        }
                                                    }
                                                }
                                                
                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                            }
                            
                            if (obtener.size() > 0) {
                                tipo = Tipo.ARREGLO;
                                return obtener;
                            } else {
                                tipo = Tipo.NULL;
                                return "nulo";
                            }
                        } else {
                            System.err.println("Error, arreglo indefinido. Línea:" + linea);
                        }
                    } else {
                        System.err.println("Error, el parametro debe ser cadena. Linea: " + linea);
                    }
                }
            } else {
                System.err.println("Error, la funcion Obtener debe recibir parámetros. Linea: " + linea);
            }
        } else {
            System.err.println("Error, se necesita un arreglo para ejecutar Obtener. Línea:" + linea);
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
