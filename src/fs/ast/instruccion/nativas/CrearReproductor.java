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
import java.io.File;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class CrearReproductor implements Instruccion {

    private final Object cont;
    private final LinkedList<Expresion> parametros;
    private final int linea;
    private final int columna;

    public CrearReproductor(Object cont, LinkedList<Expresion> parametros, int linea, int columna) {
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
                    String ruta = null;
                    Integer x = null;
                    Integer y = null;
                    Boolean auto = null;
                    Integer alto = null;
                    Integer ancho = null;

                    for (int i = 0; i < this.parametros.size(); i++) {
                        Expresion expActual = this.parametros.get(i);
                        Object valActual = expActual.getValor(tabla, salida, dirActual);
                        Tipo tipActual = expActual.getTipo(tabla);

                        if (tipActual != null) {
                            if (valActual != null) {
                                switch (i) {
                                    case 0:
                                        if (tipActual == Tipo.CADENA) {
                                            ruta = (String) valActual;
                                        }
                                        break;
                                    case 1:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                x = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                x = d.intValue();
                                            }
                                        }
                                        break;
                                    case 2:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                y = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                y = d.intValue();
                                            }
                                        }
                                        break;
                                    case 3:
                                        if (tipActual == Tipo.BOOLEANO) {
                                            String b = (String) valActual;
                                            auto = b.equals("verdadero");
                                        }
                                        break;
                                    case 4:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                alto = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                alto = d.intValue();
                                            }
                                        }
                                        break;
                                    case 5:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                ancho = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                ancho = d.intValue();
                                            }
                                        }
                                        break;
                                }
                            }
                        } else {
                            System.err.println("Error, no se puede asignar el parametro. Linea:" + linea);
                        }
                    }

                    if (ruta != null && x != null && y != null && alto != null && ancho != null) {
                        JPanel panel = (JPanel) cont;

                        File actual;
                                              
                        try {
                            actual = new File(dirActual);

                            String path = actual.getParent() + "\\" + ruta;
                            
                            
                        } catch (Exception ex) {
                        }
                                            }
                } else {
                    System.err.println("Error, se necesitan parametros para crear la imagen. Línea: " + linea);
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
