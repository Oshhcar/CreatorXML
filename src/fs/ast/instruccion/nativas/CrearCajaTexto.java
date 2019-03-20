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
import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author oscar
 */
public class CrearCajaTexto implements Instruccion {

    private final Object cont;
    private final LinkedList<Expresion> parametros;
    private final int tipo;
    private final int linea;
    private final int columna;

    public CrearCajaTexto(Object cont, LinkedList<Expresion> parametros, int tipo, int linea, int columna) {
        this.cont = cont;
        this.parametros = parametros;
        this.tipo = tipo;
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
                    String fuente = null;
                    Integer tam = null;
                    String color = null;
                    Integer x = null;
                    Integer y = null;
                    Boolean negrilla = null;
                    Boolean cursiva = null;
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
                                        if (tipActual == Tipo.CADENA) {
                                            fuente = (String) valActual;
                                        }
                                        break;
                                    case 3:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                tam = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                tam = d.intValue();
                                            }
                                        }
                                        break;
                                    case 4:
                                        if (tipActual == Tipo.CADENA) {
                                            color = (String) valActual;
                                        }
                                        break;
                                    case 5:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                x = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                x = d.intValue();
                                            }
                                        }
                                        break;
                                    case 6:
                                        if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                            if (tipActual == Tipo.ENTERO) {
                                                y = (Integer) valActual;
                                            } else {
                                                Double d = (Double) valActual;
                                                y = d.intValue();
                                            }
                                        }
                                        break;
                                    case 7:
                                        if (tipActual == Tipo.BOOLEANO) {
                                            String b = (String) valActual;
                                            negrilla = b.equals("verdadero");
                                        }
                                        break;
                                    case 8:
                                        if (tipActual == Tipo.BOOLEANO) {
                                            String b = (String) valActual;
                                            cursiva = b.equals("verdadero");
                                        }
                                        break;
                                    case 9:
                                        if (tipActual == Tipo.CADENA) {
                                            defecto = (String) valActual;
                                        }
                                        break;
                                    case 10:
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

                    if (alto != null && ancho != null && fuente != null && tam != null && color != null && x != null && y != null
                            && negrilla != null && cursiva != null && defecto != null && nombre != null) {
                        Color c = Color.BLACK;

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

                        if (tipo == 1) {
                            JTextField texto = new JTextField(defecto);
                            texto.setName(nombre);

                            try {
                                int estilo = 0;
                                if (negrilla) {
                                    estilo = 1;
                                }
                                if (cursiva) {
                                    estilo = 2;
                                }
                                if (negrilla && cursiva) {
                                    estilo = 3;
                                }

                                texto.setFont(new Font(fuente, estilo, tam));
                                texto.setBounds(x, y, ancho, alto);
                            } catch (Exception ex) {
                            }

                            texto.setBackground(c);
                            panel.add(texto);
                        } else {
                            JTextArea area = new JTextArea(defecto);
                            area.setName(nombre);
                            
                            try {
                                int estilo = 0;
                                if (negrilla) {
                                    estilo = 1;
                                }
                                if (cursiva) {
                                    estilo = 2;
                                }
                                if (negrilla && cursiva) {
                                    estilo = 3;
                                }

                                area.setFont(new Font(fuente, estilo, tam));
                                area.setBounds(x, y, ancho, alto);
                            } catch (Exception ex) {
                            }
                            
                            area.setBackground(c);
                            panel.add(area);
                        }

                    } else {
                        System.err.println("Error, hacen falta parametros para crear el texto. Línea: " + linea);
                    }

                } else {
                    System.err.println("Error, se necesitan parametros para crear el texto. Línea: " + linea);

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
