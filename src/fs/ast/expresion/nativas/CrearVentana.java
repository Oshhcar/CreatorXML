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
import java.awt.FlowLayout;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class CrearVentana implements Expresion {

    private final LinkedList<Expresion> parametros;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public CrearVentana(LinkedList<Expresion> parametros, int linea, int columna) {
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
        if (this.parametros != null) {
            String color = null;
            Integer alto = null;
            Integer ancho = null;
            String id = null;

            for (int i = 0; i < this.parametros.size(); i++) {
                Expresion expActual = this.parametros.get(i);
                Object valActual = expActual.getValor(tabla, salida, dirActual);
                Tipo tipActual = expActual.getTipo(tabla);

                if (tipActual != null) {
                    if (valActual != null) {
                        switch (i) {
                            case 0:
                                if (tipActual == Tipo.CADENA) {
                                    color = (String) valActual;
                                }
                                break;
                            case 1:
                                if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                    if (tipActual == Tipo.ENTERO) {
                                        alto = (Integer) valActual;
                                    } else {
                                        Double d = (Double) valActual;
                                        alto = d.intValue();
                                    }
                                }
                                break;
                            case 2:
                                if (tipActual == Tipo.DECIMAL || tipActual == Tipo.ENTERO) {
                                    if (tipActual == Tipo.ENTERO) {
                                        ancho = (Integer) valActual;
                                    } else {
                                        Double d = (Double) valActual;
                                        ancho = d.intValue();
                                    }
                                }
                                break;
                            case 3:
                                if (tipActual == Tipo.CADENA) {
                                    id = (String) valActual;
                                }
                                break;
                        }
                    }
                } else {
                    System.err.println("Error, no se puede asignar el parametro. Linea:" + linea);
                }
            }

            if (color != null && alto != null && ancho != null && id != null) {
                Color c = Color.WHITE;
                
                if (color.charAt(0) == '#') {
                    color = color.replace("#", "");
                    try{
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
                    } catch (Exception ex){
                    }
                } 

                JFrame ventana = new JFrame(id);
                ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
                ventana.setName(id);
                ventana.setSize(ancho, alto);
                //ventana.setBackground(c);
                //ventana.setLayout(new FlowLayout());
                ventana.setLayout(null);
                ventana.getContentPane().setBackground(c);
                //ventana.setVisible(true);
                tipo = Tipo.VENTANA;
                return ventana;
            } else {
                System.err.println("Error, hacen falta parametros para crear la ventana. Línea: " + linea);

            }
        } else {
            System.err.println("Error, se necesitan parametros para crear la ventana. Línea: " + linea);
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
