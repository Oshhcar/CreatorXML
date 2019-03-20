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
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class CrearTexto implements Instruccion {

    private final Object cont;
    private final LinkedList<Expresion> parametros;
    private final int linea;
    private final int columna;

    public CrearTexto(Object cont, LinkedList<Expresion> parametros, int linea, int columna) {
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
                    String fuente = null;
                    Integer tam = null;
                    String color = null;
                    Integer x = null;
                    Integer y = null;
                    Boolean negrilla = null;
                    Boolean cursiva = null;
                    String texto = null;

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
                                        if (tipActual == Tipo.BOOLEANO) {
                                            String b = (String) valActual;
                                            negrilla = b.equals("verdadero");
                                        }
                                        break;
                                    case 6:
                                        if (tipActual == Tipo.BOOLEANO) {
                                            String b = (String) valActual;
                                            cursiva = b.equals("verdadero");
                                        }
                                        break;
                                    case 7:
                                        if (tipActual == Tipo.CADENA) {
                                            texto = (String) valActual;
                                        }
                                        break;
                                }
                            }
                        } else {
                            System.err.println("Error, no se puede asignar el parametro. Linea:" + linea);
                        }
                    }

                    if (fuente != null && tam != null && color != null && x != null && y != null
                            && negrilla != null && cursiva != null && texto != null) {
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
                        JLabel label = new JLabel(texto);
                        
                        try{
                            int estilo = 0;
                            if(negrilla){
                                estilo = 1;
                            }
                            if (cursiva){
                                estilo = 2;
                            }
                            if(negrilla && cursiva){
                                estilo = 3;
                            }
                            
                            label.setFont(new Font(fuente, estilo, tam));
                            label.setBounds(x, y, (texto.length()+tam)*10, (int) ((tam*0.9)+texto.length()/7));
                        }catch (Exception ex){
                        }
                        
                        label.setForeground(c);
                        panel.add(label);
                        
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
