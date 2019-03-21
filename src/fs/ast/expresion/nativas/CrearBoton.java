/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.nativas;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class CrearBoton implements Expresion {

    private final Object cont;
    private final LinkedList<Expresion> parametros;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public CrearBoton(Object cont, LinkedList<Expresion> parametros, int linea, int columna) {
        this.cont = cont;
        this.tipo = null;
        this.parametros = parametros;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida, String dirActual) {
        if (cont != null) {
            if (cont instanceof JPanel) {
                if (this.parametros != null) {
                    String fuente = null;
                    Integer tam = null;
                    String color = null;
                    Integer x = null;
                    Integer y = null;
                    String referencia = null;
                    String texto = null;
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
                                            fuente = (String) valActual;
                                        }
                                        break;
                                    case 1:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                tam = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                tam = d.intValue();
                                            }
                                        }
                                        break;
                                    case 2:
                                        if (tipActual == Tipo.CADENA) {
                                            color = (String) valActual;
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
                                        if (tipActual == Tipo.CADENA || tipActual == Tipo.NULL) {
                                            referencia = (String) valActual;
                                        }
                                        break;
                                    case 6:
                                        if (tipActual == Tipo.CADENA) {
                                            texto = (String) valActual;
                                        }
                                        break;
                                    case 7:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                alto = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                alto = d.intValue();
                                            }
                                        }
                                        break;
                                    case 8:
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

                    if (fuente != null && tam != null && color != null && x != null && y != null
                            && referencia != null && texto != null && alto != null && ancho != null) {
                        Color c = Color.GRAY;

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

                        JPanel panel = (JPanel) cont;

                        JButton boton = new JButton(texto);
                        boton.setName(referencia);

                        try {
                            boton.setFont(new Font(fuente, 0, tam));
                            boton.setBackground(c);
                            boton.setContentAreaFilled(false);
                            boton.setOpaque(true);
                            boton.setBounds(x, y, ancho, alto);
                        } catch (Exception ex) {
                        }

                        if (!"".equals(referencia) && !"nulo".equals(referencia)) {

                            Simbolo refSimbolo = tabla.getSimbolo(referencia);

                            if (refSimbolo != null) {
                                if (refSimbolo.getTipo() == Tipo.VENTANA) {
                                    JFrame refVentana = (JFrame) refSimbolo.getValor();
                                    if (refVentana != null) {
                                        Simbolo actSimbolo = tabla.getSimbolo(panel.getName());
                                        if (actSimbolo != null) {
                                            if (actSimbolo.getTipo() == Tipo.VENTANA) {
                                                JFrame actVentana = (JFrame) actSimbolo.getValor();

                                                if (actVentana != null) {
                                                    boton.addActionListener(new ActionListener() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent ae) {
                                                            actVentana.setVisible(false);
                                                            refVentana.setVisible(true);
                                                        }
                                                    });
                                                }
                                                
                                            }
                                        }
                                    }
                                } else {
                                    System.err.println("Error, referencia no es una ventana. Línea: " + linea);

                                }
                            } else {
                                System.err.println("Error, referencia no encontrada. Línea: " + linea);

                            }
                        }
                        panel.add(boton);
                        tipo = Tipo.BOTON;
                        return boton;

                    } else {
                        System.err.println("Error, hacen falta parametros para crear el boton. Línea: " + linea);
                    }

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
