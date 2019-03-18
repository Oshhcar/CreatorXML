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
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class CrearArray implements Expresion {

    private LexicoGDato lexicoGDato;
    private SintacticoGDato sintacticoGDato;

    private final LinkedList<Expresion> parametros;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public CrearArray(LinkedList<Expresion> parametros, int linea, int columna) {
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
                //hacer lo de sin parametros
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
