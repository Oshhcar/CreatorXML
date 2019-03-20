/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.nativas;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class CrearContenedor implements Expresion {

    private final Object ven;
    private final LinkedList<Expresion> parametros;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public CrearContenedor(Object ven, LinkedList<Expresion> parametros, int linea, int columna) {
        this.ven = ven;
        this.parametros = parametros;
        this.tipo = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida, String dirActual) {
        if (ven != null) {
            if (ven instanceof JFrame) {
                if (this.parametros != null) {
                    Integer alto = null;
                    Integer ancho = null;
                    String color = null;
                    Boolean borde = null;
                    Integer x = null;
                    Integer y = null;

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
                                        if (tipActual == Tipo.CADENA) {
                                            color = (String) valActual;
                                        }
                                        break;
                                    case 3:
                                        if (tipActual == Tipo.BOOLEANO) {
                                            String b = (String) valActual;
                                            borde = b.equals("verdadero");
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
                                }
                            }
                        } else {
                            System.err.println("Error, no se puede asignar el parametro. Linea:" + linea);
                        }
                    }

                    if (alto != null && ancho != null && color != null && x != null && y != null) {
                        Color c = Color.WHITE;

                        if (color.charAt(0) == '#') {
                            color = color.replace("#", "");
                            try {
                                switch (color.length()) {
                                    case 6:
                                        c = new Color(
                                                Integer.valueOf(color.substring(0, 2), 16),
                                                Integer.valueOf(color.substring(2, 4), 16),
                                                Integer.valueOf(color.substring(4, 6), 16));
                                    case 8:
                                        c = new Color(
                                                Integer.valueOf(color.substring(0, 2), 16),
                                                Integer.valueOf(color.substring(2, 4), 16),
                                                Integer.valueOf(color.substring(4, 6), 16),
                                                Integer.valueOf(color.substring(6, 8), 16));
                                }
                            } catch (Exception ex) {
                            }
                        }
                        
                        JFrame ventana = (JFrame) ven;
                        //Container contenedor = ventana.getContentPane();

                        JPanel panel = new JPanel();
                        panel.setLayout(null);
                        //panel.setPreferredSize(new Dimension(ancho, alto));
                        panel.setBounds(x,y,ancho, alto);
                        //panel.setLocation(x, y);
                        panel.setBackground(c);
                        if(borde){
                            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        }
                        
                        //panel.add(new JButton("uno"));
                        
                        ventana.getContentPane().add(panel);

                        tipo = Tipo.CONTENEDOR;
                        return panel;
                    } else {
                        System.err.println("Error, hacen falta parametros para crear el contenedor. Línea: " + linea);

                    }
                } else {
                    System.err.println("Error, se necesitan parametros para crear el contenedor. Línea: " + linea);
                }
            } else {
                System.err.println("Error, la variable no es una ventana. Línea: " + linea);
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
