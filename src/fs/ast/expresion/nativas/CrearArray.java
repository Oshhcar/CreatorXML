/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.nativas;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import gdato.LexicoGDato;
import gdato.SintacticoGDato;
import java.awt.Container;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.LinkedList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author oscar
 */
public class CrearArray implements Expresion {

    private LexicoGDato lexicoGDato;
    private SintacticoGDato sintacticoGDato;

    private Object ventana;
    private final LinkedList<Expresion> parametros;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public CrearArray(LinkedList<Expresion> parametros, int linea, int columna) {
        this.parametros = parametros;
        this.tipo = null;
        this.ventana = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Tipo getTipo(TablaSimbolos tabla) {
        return tipo;
    }

    @Override
    public Object getValor(TablaSimbolos tabla, JTextArea salida, String dirActual) {
        if (dirActual != null) {
            if (this.parametros != null) {
                if (this.parametros.size() >= 1) {
                    Expresion parm = this.parametros.get(0);
                    Object valParm = parm.getValor(tabla, salida, dirActual);
                    if (valParm != null) {
                        Tipo tipParm = parm.getTipo(tabla);
                        if (tipParm == Tipo.CADENA) {
                            String rutaImport = valParm.toString();
                            String ext = rutaImport.substring(rutaImport.lastIndexOf('.'));

                            if (ext.toLowerCase().equals(".gdato")) {
                                File archivo;
                                FileReader fr;
                                BufferedReader br;

                                File actual;

                                try {

                                    actual = new File(dirActual);
                                    if (rutaImport.charAt(0) == '/') {
                                        rutaImport = rutaImport.replaceFirst("/", "");
                                    }
                                    rutaImport = rutaImport.replaceAll("/", "\\\\");

                                    String dirImport = actual.getParent() + "\\" + rutaImport;

                                    archivo = new File(dirImport);
                                    fr = new FileReader(archivo);
                                    br = new BufferedReader(fr);

                                    String texto = "";
                                    String line;
                                    while ((line = br.readLine()) != null) {
                                        texto += line + "\n";
                                    }

                                    lexicoGDato = new LexicoGDato(new BufferedReader(new StringReader(texto)));
                                    sintacticoGDato = new SintacticoGDato(lexicoGDato);

                                    try {
                                        sintacticoGDato.parse();

                                        Map<Integer, Object> arreglo = sintacticoGDato.getArreglo();
                                        if (arreglo != null) {
                                            tipo = Tipo.ARREGLO;
                                            return arreglo;
                                        } else {
                                            System.err.println("Error, el archivo \"" + rutaImport + "\" contiene errores. Línea: " + linea);

                                        }
                                    } catch (Exception e) {
                                        System.err.println("Error, el archivo \"" + rutaImport + "\" contiene errores. Línea: " + linea);
                                    }
                                } catch (Exception e) {
                                    System.err.println("Error, intentando abrir el archivo \"" + rutaImport + "\". Línea: " + linea);
                                }
                            } else {
                                System.err.println("Error, la extensión debe ser \".gdato\". Línea: " + linea);
                            }
                        } else {
                            System.err.println("Error, se esperaba una cadena de ruta. Línea: " + linea);
                        }
                    }
                }
            } else {
                if (ventana != null) {
                    if (ventana instanceof JFrame) {
                        JFrame vent = (JFrame) ventana;
                        if (vent != null) {
                            File archivo;

                            FileReader fr;
                            BufferedReader br;
                            FileWriter fw = null;
                            PrintWriter pw = null;

                            File actual;

                            try {
                                actual = new File(dirActual);

                                String dirArray = actual.getParent() + "\\" + vent.getName() + ".gdato";

                                archivo = new File(dirArray);

                                String textArray = "";

                                Container c = vent.getContentPane();

                                for (int i = 0; i < c.getComponentCount(); i++) {
                                    Object p = c.getComponent(i);
                                    if (p instanceof JPanel) {
                                        JPanel panel = (JPanel) p;
                                        for (int j = 0; j < panel.getComponentCount(); j++) {
                                            Object control = panel.getComponent(j);
                                            if (control instanceof JTextField) {
                                                JTextField field = (JTextField) control;
                                                textArray += "\t\t<" + field.getName() + ">\"" + field.getText() + "\"</" + field.getName() + ">\n";
                                            } else if (control instanceof JTextArea) {
                                                JTextArea area = (JTextArea) control;
                                                textArray += "\t\t<" + area.getName() + ">\"" + area.getText() + "\"</" + area.getName() + ">\n";
                                            } else if (control instanceof JSpinner) {
                                                JSpinner spin = (JSpinner) control;
                                                textArray += "\t\t<" + spin.getName() + ">" + spin.getValue() + "</" + spin.getName() + ">\n";
                                            } else if (control instanceof JComboBox) {
                                                JComboBox desp = (JComboBox) control;
                                                textArray += "\t\t<" + desp.getName() + ">\"" + desp.getSelectedItem() + "\"</" + desp.getName() + ">\n";
                                            }
                                        }
                                    }
                                }
                                
                                String text = "";

                                if (archivo.exists()) {
                                    fr = new FileReader(archivo);
                                    br = new BufferedReader(fr);
                                    
                                    String line;
                                    while ((line = br.readLine()) != null) {
                                        text += line + "\n";
                                    }
                                    
                                    text = text.substring(0, text.lastIndexOf("</principal>"));
                                    text += "</principal>\n";
                                    text += "\t<principal>\n" + textArray +"\t</principal>\n</lista>";
                                    
                                } else {
                                    text = "<lista>\n\t<principal>\n" + textArray + "\t</principal>\n</lista>";
                                }

                                try {
                                    fw = new FileWriter(dirArray);
                                    pw = new PrintWriter(fw);

                                    for (String line : text.split("\n")) {
                                        pw.println(line);
                                    }

                                } catch (Exception ex) {
                                } finally {
                                    if (null != fw) {
                                        fw.close();
                                    }
                                }

                            } catch (Exception ex) {

                            }
                        }
                    } else {
                        System.err.println("Error, la variable no es una ventana. Línea: " + linea);
                    }
                } else {
                    System.err.println("Error, se necesita llamara desde una variable ventana. Línea: " + linea);
                }
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
     * @return the ventana
     */
    public Object getVentana() {
        return ventana;
    }

    /**
     * @param ventana the ventana to set
     */
    public void setVentana(Object ventana) {
        this.ventana = ventana;
    }

}
