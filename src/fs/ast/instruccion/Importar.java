/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.instruccion;

import fs.LexicoFs;
import fs.SintacticoFs;
import fs.ast.AST;
import fs.ast.NodoAST;
import fs.ast.expresion.Expresion;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Importar implements Instruccion {

    private LexicoFs lexicoFs;
    private SintacticoFs sintacticoFs;

    private final Expresion ruta;
    private String dirActual;
    private final int linea;
    private final int columna;

    public Importar(Expresion ruta, int linea, int columna) {
        this.ruta = ruta;
        this.dirActual = null;
        this.linea = linea;
        this.columna = columna;
    }

    public Importar(int linea, int columna) {
        this.ruta = null;
        this.dirActual = null;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(TablaSimbolos tabla, JTextArea salida, boolean fun, boolean sel, String dirActual) {
        if (dirActual != null) {
            if (ruta != null) {
                Object valRuta = ruta.getValor(tabla, salida, dirActual);
                if (valRuta != null) {
                    String rutaImport = valRuta.toString();
                    Tipo tipRuta = ruta.getTipo(tabla);
                    if (tipRuta == Tipo.CADENA) {
                        String ext = rutaImport.substring(rutaImport.lastIndexOf('.'));

                        if (ext.toLowerCase().equals(".fs")) {

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

                                lexicoFs = new LexicoFs(new BufferedReader(new StringReader(texto)));
                                sintacticoFs = new SintacticoFs(lexicoFs);

                                AST ast = null;

                                try {
                                    sintacticoFs.parse();
                                    ast = sintacticoFs.getAST();
                                    if (ast != null) {
                                        for (NodoAST nodo : ast.getNodos()) {
                                            if (nodo instanceof Instruccion) {
                                                if (nodo instanceof Funcion) {
                                                    ((Funcion) nodo).ejecutar(tabla, salida, false, false, dirActual);
                                                }
                                            }
                                        }
                                        
                                        for (NodoAST nodo : ast.getNodos()) {
                                            if (nodo instanceof Instruccion) {
                                                if (nodo instanceof Importar) {
                                                    Importar imp = (Importar) nodo;
                                                    imp.setDirActual(dirActual);
                                                    imp.ejecutar(tabla, salida, false, false, dirActual);
                                                }
                                            }
                                        }

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
                            System.err.println("Error, la extensión debe ser \".fs\". Línea: " + linea);
                        }
                    } else {
                        System.err.println("Error, se esperaba una cadena de ruta. Línea: " + linea);
                    }
                }

            } else {
                System.err.println("Error, se debe definir una ruta. Línea: " + linea);
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
     * @param dirActual the dirActual to set
     */
    public void setDirActual(String dirActual) {
        this.dirActual = dirActual;
    }

}
