/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion.nativas;

import fs.ast.expresion.Expresion;
import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import genericxml.Arbol;
import genericxml.Lexico;
import genericxml.Sintactico;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class LeerGxml implements Expresion {

    private Lexico lexicoGenericxml;
    private Sintactico sintacticoGenericxml;

    private final LinkedList<Expresion> parametros;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public LeerGxml(LinkedList<Expresion> parametros, int linea, int columna) {
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
                            String rutaGxml = valParm.toString();
                            String ext = rutaGxml.substring(rutaGxml.lastIndexOf('.'));
                                                        
                            if (ext.toLowerCase().equals(".gxml")) {
                                File archivo;
                                FileReader fr;
                                BufferedReader br;

                                File actual;

                                try {

                                    actual = new File(dirActual);
                                    if (rutaGxml.charAt(0) == '/') {
                                        rutaGxml = rutaGxml.replaceFirst("/", "");
                                    }
                                    rutaGxml = rutaGxml.replaceAll("/", "\\\\");

                                    String dirGxml = actual.getParent() + "\\" + rutaGxml;

                                    archivo = new File(dirGxml);
                                    fr = new FileReader(archivo);
                                    br = new BufferedReader(fr);

                                    String texto = "";
                                    String line;
                                    while ((line = br.readLine()) != null) {
                                        texto += line + "\n";
                                    }

                                    lexicoGenericxml = new Lexico(new BufferedReader(new StringReader(texto)));
                                    sintacticoGenericxml = new Sintactico(lexicoGenericxml);
                                    Arbol arbol = null;

                                    try {
                                        sintacticoGenericxml.parse();
                                        arbol = sintacticoGenericxml.getArbol();

                                        if (arbol != null) {
                                            if (arbol.getEtiquetas() == null && arbol.getImports() == null) {
                                                System.err.println("Error, el archivo \"" + rutaGxml + "\" está vacío. Línea: " + linea);
                                            } else {
                                                Map<Integer, Object> arreglo = (Arreglo) arbol.traducir("", dirGxml, false);
                                                
                                                if(arreglo != null){
                                                    tipo = Tipo.ARREGLO;
                                                    return arreglo;
                                                }
                                            }
                                        } else {
                                            System.err.println("Error, el archivo \"" + rutaGxml + "\" contiene errores. Línea: " + linea);

                                        }
                                    } catch (Exception e) {
                                        System.err.println("Error, el archivo \"" + rutaGxml + "\" contiene errores. Línea: " + linea);
                                    }
                                } catch (Exception e) {
                                    System.err.println("Error, intentando abrir el archivo \"" + rutaGxml + "\". Línea: " + linea);
                                }
                            } else {
                                System.err.println("Error, la extensión debe ser \".gxml\". Línea: " + linea);

                            }
                        } else {
                            System.err.println("Error, se esperaba una cadena de ruta. Línea: " + linea);

                        }
                    }
                }
            } else {
                System.err.println("Error, se esperaba ruta como parámetro. Línea: " + linea);
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
