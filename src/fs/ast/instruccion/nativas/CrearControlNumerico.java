/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion.nativas;

import fs.ast.expresion.Expresion;
import fs.ast.instruccion.Instruccion;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author oscar
 */
public class CrearControlNumerico implements Instruccion {

    private final Object cont;
    private final LinkedList<Expresion> parametros;
    private final int linea;
    private final int columna;

    public CrearControlNumerico(Object cont, LinkedList<Expresion> parametros, int linea, int columna) {
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
                    Integer maximo = null;
                    Integer minimo = null;
                    Integer x = null;
                    Integer y = null;
                    Integer defecto = null;
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
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                maximo = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                maximo = d.intValue();
                                            }
                                        }
                                        break;
                                    case 3:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                minimo = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                minimo = d.intValue();
                                            }
                                        }
                                        break;
                                    case 4:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                x = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                x = d.intValue();
                                            }
                                        }
                                        break;
                                    case 5:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                y = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                y = d.intValue();
                                            }
                                        }
                                        break;
                                    case 6:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                defecto = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                defecto = d.intValue();
                                            }
                                        }
                                        break;
                                    case 7:
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
                    
                    if(alto != null && ancho != null && maximo != null && minimo != null && x != null && y != null 
                            && defecto != null && nombre != null){
                        
                        JPanel panel = (JPanel) cont;

                        JSpinner control = new JSpinner();
                        control.setName(nombre);
                        
                        try{
                            SpinnerNumberModel model = new SpinnerNumberModel();
                            model.setMaximum(maximo);
                            model.setMinimum(minimo);
                            
                            control.setModel(model);
                            control.setBounds(x, y, ancho, alto);
                            control.setValue(defecto);                            
                            
                        } catch (Exception ex){
                        }
                        panel.add(control);
                        
                    } else {
                        System.err.println("Error, hacen falta parametros para crear el control numérico. Línea: " + linea);
                    }

                } else {
                    System.err.println("Error, se necesitan parametros para crear el control numérico. Línea: " + linea);

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
