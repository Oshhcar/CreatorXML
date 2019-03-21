/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion.nativas;

import fs.ast.expresion.Expresion;
import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class CrearDesplegable implements Instruccion {

    private final Object cont;
    private final LinkedList<Expresion> parametros;
    private final int linea;
    private final int columna;

    public CrearDesplegable(Object cont, LinkedList<Expresion> parametros, int linea, int columna) {
        this.cont = cont;
        this.parametros = parametros;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel, String dirActual) {
        if (cont != null) {
            if (cont instanceof JPanel) {
                if (this.parametros != null) {
                    Integer alto = null;
                    Integer ancho = null;
                    Map<Integer, Object> lista = null;
                    Integer x = null;
                    Integer y = null;
                    String defecto = null;
                    String nombre = null;

                    for (int i = 0; i < this.parametros.size(); i++) {
                        Expresion expActual = this.parametros.get(i);
                        Object valActual = expActual.getValor(tabla, salida, dirActual);
                        Tipo tipActual = expActual.getTipo(tabla);

                        if (tipActual != null) {
                            if (valActual != null) {
                                switch (i) {
                                    case 0:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                alto = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                alto = d.intValue();
                                            }
                                        }
                                        break;
                                    case 1:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                ancho = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                ancho = d.intValue();
                                            }
                                        }
                                        break;
                                    case 2:
                                        if (tipActual == Tipo.ARREGLO) {
                                            lista = (Arreglo) valActual;
                                        }
                                        break;
                                    case 3:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                x = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                x = d.intValue();
                                            }
                                        }
                                        break;
                                    case 4:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                y = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                y = d.intValue();
                                            }
                                        }
                                        break;
                                    case 5:
                                        if (tipActual == Tipo.CADENA) {
                                            defecto = (String) valActual;
                                        }
                                        break;
                                    case 6:
                                        if (tipActual == Tipo.CADENA) {
                                            nombre = (String) valActual;
                                        }
                                        break;
                                }
                            }
                        } else {
                            System.err.println("Error, no se puede asignar el parametro. Linea:" + linea);
                        }
                    }

                    if (alto != null && ancho != null && lista != null && x != null && y != null && defecto != null && nombre != null) {
                        
                        JPanel panel = (JPanel) cont;
                        
                        JComboBox desplegable = new JComboBox();
                        desplegable.setName(nombre);
                        try{
                            for(int i = 0; i < lista.size(); i++){
                                Object dato = lista.get(i);
                                desplegable.addItem(dato);
                            }
                            desplegable.setSelectedItem(defecto);
                            desplegable.setBounds(x, y, ancho, alto);
                        }catch(Exception ex){
                        
                        }
                        
                        panel.add(desplegable);

                    } else {
                        System.err.println("Error, hacen falta parametros para crear el texto. Línea: " + linea);

                    }

                } else {
                    System.err.println("Error, se necesitan parametros para crear el desplegable. Línea: " + linea);

                }
            } else {
                System.err.println("Error, la variable no es un contenedor. Línea: " + linea);
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
