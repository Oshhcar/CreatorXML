/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericxml;

import fs.ast.simbolos.Arreglo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author oscar
 */
/**
 *
 * Todo elemento generado sera una etiqueta
 *
 *
 */
public class Etiqueta {

    public static enum Tipo {
        IMPORTAR {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
                String cad = "";
                if (e.getPlano() != null) {
                    String rutaImport = e.getPlano();
                    String ext = rutaImport.substring(rutaImport.lastIndexOf('.'));

                    if (ext.toLowerCase().equals(".fs") || ext.toLowerCase().equals(".gxml")) {

                        if (ext.toLowerCase().equals(".fs")) {
                            if (archivo == null) {
                                cad = "Importar(\"" + e.getPlano() + "\");\n";
                                return cad;
                            }
                        } else {
                            File file;
                            FileReader fr;
                            BufferedReader br;

                            File actual;

                            try {
                                actual = new File(rutaActual);
                                if (rutaImport.charAt(0) == '/') {
                                    rutaImport = rutaImport.replaceFirst("/", "");
                                }
                                rutaImport = rutaImport.replaceAll("/", "\\\\");

                                String dirImport = actual.getParent() + "\\" + rutaImport;

                                file = new File(dirImport);
                                fr = new FileReader(file);
                                br = new BufferedReader(fr);

                                String texto = "";
                                String line;
                                while ((line = br.readLine()) != null) {
                                    texto += line + "\n";
                                }

                                Lexico lexicoGenericxml = new Lexico(new BufferedReader(new StringReader(texto)));
                                Sintactico sintacticoGenericxml = new Sintactico(lexicoGenericxml);

                                Arbol arbol = null;

                                try {
                                    sintacticoGenericxml.parse();
                                    arbol = sintacticoGenericxml.getArbol();

                                    if (arbol != null) {
                                        if (arbol.getEtiquetas() == null && arbol.getImports() == null) {
                                            System.out.println("Error al generar el arbol.");
                                        } else {
                                            //cad += arbol.traducir(name, rutaActual, archivo);

                                            if (archivo == null) {
                                                if (arbol.getImports() != null) {
                                                    for (Etiqueta i : arbol.getImports()) {
                                                        cad += i.traducir(null, name, "", "", "", rutaActual, 1, archivo, false);
                                                    }
                                                }
                                            } else {

                                                if (arbol.getEtiquetas() != null) {
                                                    for (Etiqueta et : arbol.getEtiquetas()) {
                                                        cad += et.traducir(null, name, "", "", "", rutaActual, 1, archivo, false);
                                                    }
                                                }

                                                if (arbol.getImports() != null) {
                                                    for (Etiqueta i : arbol.getImports()) {
                                                        cad += i.traducir(null, name, "", "", "", rutaActual, 1, archivo, false);
                                                    }
                                                }
                                                
                                                cad += "\n";
                                                
                                                if (arbol.getEtiquetas() != null) {
                                                    for (Etiqueta et : arbol.getEtiquetas()) {
                                                        if (et.getTipo() == Tipo.VENTANA) {
                                                            cad += et.traducir(null, name, "", "", "", rutaActual, 1, archivo, true);
                                                        }
                                                    }
                                                }

                                            }

                                            return cad;
                                        }
                                    } else {
                                        System.out.println("No se pudo generar el arbol");
                                    }

                                } catch (Exception exc) {
                                    System.err.println("Error, el archivo \"" + rutaImport + "\" contiene errores.");

                                }

                            } catch (IOException exc) {
                                System.err.println("Error, intentando abrir el archivo \"" + rutaImport + "\".");
                            }
                        }
                    } else {
                        System.err.println("Error, la extension del arhcivo a importar debe ser \".fs\" o \".gxml\"");
                    }
                } else {
                    System.out.println("Error! importar");
                }
                return cad;
            }
        },
        VENTANA {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
                String cad = "";
                if (p == null) {
                    String id = null;
                    String tipo = null;
                    String color = "#ffffff";
                    int alto = 500;
                    int ancho = 500;
                    String accioninicial = null;
                    String accionfinal = null;

                    if (e.getElementos() != null) {
                        for (Elemento elemento : e.getElementos()) {
                            if (null == elemento.getTipo()) {
                                System.out.println("Error! Elemento incorrecto en ventana");
                            } else {
                                switch (elemento.getTipo()) {
                                    case ID:
                                        id = elemento.getValor().toString();
                                        break;
                                    case TIPO:
                                        tipo = elemento.getValor().toString();
                                        break;
                                    case COLOR:
                                        color = elemento.getValor().toString();
                                        break;
                                    case ALTO:
                                        alto = new Integer(elemento.getValor().toString());
                                        break;
                                    case ANCHO:
                                        ancho = new Integer(elemento.getValor().toString());
                                        break;
                                    case ACCIONINICIAL:
                                        accioninicial = elemento.getValor().toString().replaceAll("\\{", "").replaceAll("\\}", "");
                                        break;
                                    case ACCIONFINAL:
                                        accionfinal = elemento.getValor().toString().replaceAll("\\{", "").replaceAll("\\}", "");
                                        break;
                                    default:
                                        System.out.println("Error! Elemento incorrecto en ventana");
                                        break;
                                }
                            }
                        }

                        if (id != null && tipo != null) {

                            if (!func) {
                                cad = "Var " + id + name + " = CrearVentana(\"" + color + "\", "
                                        + alto + ", " + ancho + ");\n\n";

                                if (e.getEtiquetas() != null) {
                                    for (Etiqueta et : e.getEtiquetas()) {
                                        cad = cad + et.traducir(e, name, id + name, color, id, rutaActual, t, archivo, func);
                                    }
                                }

                                if (accioninicial != null) {
                                    cad += id + name + ".AlCargar(" + accioninicial + ");\n";
                                }

                                if (accionfinal != null) {
                                    cad += id + name + ".AlCargar(" + accionfinal + ");\n";
                                }
                            } else {

                                cad = cad + "funcion Guardar_" + id + name + "(){\n\t" + id + name
                                        + ".crearArrayDesdeArchivo();\n}\n\n";

                                cad = cad + "funcion CargarVentana_" + id + name + "(){\n\t" + id + name
                                        + ".AlCargar();\n}\n";
                                
                                if(tipo.toLowerCase().equals("principal") || tipo.toLowerCase().equals("secundaria")){
                                    if(tipo.toLowerCase().equals("principal")){
                                        cad += "\n"+id+name+".AlCargar();\n";
                                    }
                                } else {
                                    System.err.println("Error, tipo debe ser principal o secundaria. ");
                                }
                                
                            }
                        } else {
                            System.out.println("Error! Faltan elementos ventana");
                        }
                    } else {
                        System.out.println("Error! Etiqueta sin elementos");
                    }

                } else {
                    System.out.println("Error! Ventana en otro lado");
                }
                return cad;
            }

        },
        CONTENEDOR {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
                String cad = "";

                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.VENTANA) {
                        String id = null;
                        Integer x = null;
                        Integer y = null;

                        int alto = 500;
                        int ancho = 500;
                        String color = colorPadre;
                        boolean borde = false;

                        if (e.getElementos() != null) {
                            for (Elemento elemento : e.getElementos()) {
                                if (null == elemento.getTipo()) {
                                    System.out.println("Error! Elemento incorrecto en contenedor");
                                } else {
                                    switch (elemento.getTipo()) {
                                        case ID:
                                            id = elemento.getValor().toString();
                                            break;
                                        case X:
                                            x = new Integer(elemento.getValor().toString());
                                            break;
                                        case Y:
                                            y = new Integer(elemento.getValor().toString());
                                            break;
                                        case ALTO:
                                            alto = new Integer(elemento.getValor().toString());
                                            break;
                                        case ANCHO:
                                            ancho = new Integer(elemento.getValor().toString());
                                            break;
                                        case COLOR:
                                            color = elemento.getValor().toString();
                                            break;
                                        case BORDE:
                                            borde = Boolean.valueOf(elemento.getValor().toString());
                                            break;
                                        default:
                                            System.out.println("Error! Elemento incorrecto en contenedor");
                                            break;
                                    }
                                }
                            }

                            if (id != null && x != null && y != null) {
                                String b = (borde) ? "verdadero" : "falso";

                                cad = "Var " + id + name + " = " + padre + ".CrearContenedor(" + alto + ", " + ancho + ", \""
                                        + color + "\", " + b + ", " + x + ", " + y + ");\n";

                                if (e.getEtiquetas() != null) {
                                    for (Etiqueta et : e.getEtiquetas()) {
                                        cad = cad + et.traducir(e, name, id + name, color, ventana, rutaActual, t, archivo, func);
                                    }
                                }
                                cad = cad + "\n";
                            } else {
                                System.out.println("Error! Faltan elementos contenedor Linea: " + e.getLinea());
                            }
                        } else {
                            System.out.println("Error! Contenedor sin elementos");
                        }
                    } else {
                        System.out.println("Error! Contenedor no viene dentro de ventana");
                    }

                } else {
                    System.out.println("Error! Contenedor no viene de forma correcta");
                }

                return cad;
            }

        },
        TEXTO {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
                String cad = "";

                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.CONTENEDOR) {
                        String nombre = null;
                        Integer x = null;
                        Integer y = null;

                        String fuente = "Arial";
                        int tam = 14;
                        String color = "#000000";
                        boolean negrita = false;
                        boolean cursiva = false;

                        if (e.getElementos() != null) {
                            for (Elemento elemento : e.getElementos()) {
                                if (null == elemento.getTipo()) {
                                    System.out.println("Error! Elemento incorrecto en texto");
                                } else {
                                    switch (elemento.getTipo()) {
                                        case NOMBRE:
                                            nombre = elemento.getValor().toString();
                                            break;
                                        case X:
                                            x = new Integer(elemento.getValor().toString());
                                            break;
                                        case Y:
                                            y = new Integer(elemento.getValor().toString());
                                            break;
                                        case FUENTE:
                                            fuente = elemento.getValor().toString();
                                            break;
                                        case TAM:
                                            tam = new Integer(elemento.getValor().toString());
                                            break;
                                        case COLOR:
                                            color = elemento.getValor().toString();
                                            break;
                                        case NEGRITA:
                                            negrita = Boolean.valueOf(elemento.getValor().toString());
                                            break;
                                        case CURSIVA:
                                            cursiva = Boolean.valueOf(elemento.getValor().toString());
                                            break;
                                        default:
                                            System.out.println("Error! Elemento incorrecto en texto");
                                            break;
                                    }
                                }
                            }

                            if (nombre != null && x != null && y != null && e.plano != null) {
                                String n = negrita ? "verdadero" : "falso";
                                String c = cursiva ? "verdadero" : "falso";

                                cad = padre + ".CrearTexto(\"" + fuente + "\", " + tam + ", \"" + color + "\", " + x + ", "
                                        + y + ", " + n + ", " + c + ", \"" + e.plano + "\");\n";

                                if (e.getEtiquetas() != null) {
                                    System.out.println("Error! Etiquetas dentro de Texto Linea:" + e.getLinea());
                                }
                            } else {
                                System.out.println("Error! Faltan elementos en Texto Linea:" + e.getLinea());
                            }

                        } else {
                            System.out.println("Error! Texto sin elementos Linea:" + e.getLinea());
                        }

                    } else {
                        System.out.println("Error! Texto no viene dentro de contenedor Linea:" + e.getLinea());
                    }
                } else {
                    System.out.println("Error! Texto no viene de forma correcta Linea:" + e.getLinea());
                }

                return cad;
            }
        },
        CONTROL {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
                String cad = "";
                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.CONTENEDOR) {
                        String tipo = null;
                        String nombre = null;
                        Integer x = null;
                        Integer y = null;

                        int alto = 50;
                        int ancho = 50;
                        String fuente = "Arial";
                        int tam = 14;
                        String color = "#000000";
                        boolean negrita = false;
                        boolean cursiva = false;
                        Integer maximo = null;
                        Integer minimo = null;
                        String accion = "";

                        if (e.getElementos() != null) {
                            for (Elemento elemento : e.getElementos()) {
                                if (null == elemento.getTipo()) {
                                    System.out.println("Error! Elemento incorrecto en texto");
                                } else {
                                    switch (elemento.getTipo()) {
                                        case TIPO:
                                            tipo = elemento.getValor().toString();
                                            break;
                                        case NOMBRE:
                                            nombre = elemento.getValor().toString();
                                            break;
                                        case X:
                                            x = new Integer(elemento.getValor().toString());
                                            break;
                                        case Y:
                                            y = new Integer(elemento.getValor().toString());
                                            break;
                                        case ALTO:
                                            alto = new Integer(elemento.getValor().toString());
                                            break;
                                        case ANCHO:
                                            ancho = new Integer(elemento.getValor().toString());
                                            break;
                                        case FUENTE:
                                            fuente = elemento.getValor().toString();
                                            break;
                                        case TAM:
                                            tam = new Integer(elemento.getValor().toString());
                                            break;
                                        case COLOR:
                                            color = elemento.getValor().toString();
                                            break;
                                        case NEGRITA:
                                            negrita = Boolean.valueOf(elemento.getValor().toString());
                                            break;
                                        case CURSIVA:
                                            cursiva = Boolean.valueOf(elemento.getValor().toString());
                                            break;
                                        case MAXIMO:
                                            maximo = new Integer(elemento.getValor().toString());
                                            break;
                                        case MINIMO:
                                            minimo = new Integer(elemento.getValor().toString());
                                            break;
                                        case ACCION:
                                            accion = elemento.getValor().toString();
                                            break;
                                        default:
                                            System.out.println("Error! Elemento incorrecto en texto");
                                            break;
                                    }
                                }
                            }

                            String n = negrita ? "verdadero" : "falso";
                            String c = cursiva ? "verdadero" : "falso";

                            String defecto = null;

                            if (e.getEtiquetas() != null) {
                                for (Etiqueta et : e.getEtiquetas()) {
                                    if (et.getTipo() == Etiqueta.Tipo.DEFECTO) {
                                        if (defecto == null) {
                                            defecto = (String) et.traducir(e, name, padre, colorPadre, ventana, rutaActual, t, archivo, func);
                                        } else {
                                            System.out.println("Error! Definicion de mas de un defecto. Control. Línea:" + et.getLinea());
                                        }
                                    }
                                }
                            }

                            if (tipo.toLowerCase().equals("numérico") || tipo.toLowerCase().equals("numerico")) {
                                if (defecto != null) {
                                    try {
                                        Integer val = new Integer(defecto.replaceAll(" ", ""));
                                        defecto = val.toString();
                                    } catch (Exception ex) {
                                        defecto = "nulo";
                                        System.out.println("Error! Valor defecto en númerico no es numérico. Linea:" + e.getLinea());
                                    }
                                } else {
                                    defecto = "nulo";
                                }
                            } else if (!tipo.toLowerCase().equals("desplegable")) {
                                defecto = defecto == null ? "nulo" : "\"" + defecto + "\"";
                            }

                            if (tipo != null && nombre != null && x != null && y != null) {
                                if (tipo.toLowerCase().equals("texto")) {
                                    cad = padre + ".CrearCajaTexto(" + alto + ", " + ancho + ", \"" + fuente + "\", "
                                            + tam + ", " + "\"" + color + "\", " + x + ", " + y + ", " + n + ", " + c
                                            + ", " + defecto + ", \"" + nombre + "\");\n";
                                } else if (tipo.toLowerCase().equals("numérico") || tipo.toLowerCase().equals("numerico")) {
                                    String max = maximo != null ? maximo.toString() : "nulo";
                                    String min = minimo != null ? minimo.toString() : "nulo";

                                    cad = padre + ".CrearControlNumerico(" + alto + ", " + ancho + ", " + max + ", "
                                            + min + ", " + x + ", " + y + ", " + defecto + ", \"" + nombre + "\");\n";
                                } else if (tipo.toLowerCase().equals("textoarea")) {
                                    cad = padre + ".CrearAreaTexto(" + alto + ", " + ancho + ", \"" + fuente + "\", "
                                            + tam + ", " + "\"" + color + "\", " + x + ", " + y + ", " + n + ", " + c + ", "
                                            + defecto + ", \"" + nombre + "\");\n";
                                } else if (tipo.toLowerCase().equals("desplegable")) {
                                    if (e.getEtiquetas() != null) {
                                        String res = "";
                                        String defect = "";

                                        for (Etiqueta et : e.getEtiquetas()) {
                                            if (et.getTipo() == Etiqueta.Tipo.LISTADATOS) {
                                                if ("".equals(res)) {
                                                    res = (String) et.traducir(e, name, nombre, defecto, ventana, rutaActual, t, archivo, func);
                                                } else {
                                                    System.out.println("Error! Ya se definio una lista. Linea:" + et.getLinea());
                                                }
                                            } else if (et.getTipo() != Etiqueta.Tipo.DEFECTO) {
                                                System.out.println("Error! Etiqueta incorrecta en control. Linea:" + et.getLinea());
                                            }
                                        }

                                        if (!"".equals(res)) {
                                            String arr[] = res.split("#");
                                            res = arr[0];
                                            defect = arr[1];

                                            if (defecto != null) {
                                                if (!"-1".equals(defect)) {
                                                    defecto = nombre + "_lista[" + defect + "]";
                                                } else {
                                                    defecto = "nulo";
                                                    System.out.println("Error! Se definio un defecto que no esta en la lista. Linea:" + e.getLinea());
                                                }
                                            } else {
                                                defecto = "nulo";
                                            }

                                            cad = res + padre + ".CrearDesplegable(" + alto + ", " + ancho + ", " + nombre + "_lista, "
                                                    + x + ", " + y + ", " + defecto + ", \"" + nombre + "\");\n";
                                        } else {
                                            System.out.println("Error! No se definio una listadatos. Linea:" + e.getLinea());
                                        }

                                    } else {
                                        System.out.println("Error! Desplegable sin listadatos. Linea:" + e.getLinea());
                                    }
                                } else {
                                    System.out.println("Error! Tipo incorrecto en control Línea:" + e.getLinea());
                                }
                            } else {
                                System.out.println("Error! Control sin elementos obligatorios Línea:" + e.getLinea());
                            }

                        } else {
                            System.out.println("Error! Control sin elementos Linea:" + e.getLinea());
                        }

                    } else {
                        System.out.println("Error! Control no viene dentro de contenedor Linea:" + e.getLinea());
                    }
                } else {
                    System.out.println("Error! Control no viene de forma correcta Linea:" + e.getLinea());
                }
                return cad;
            }
        },
        LISTADATOS {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
                String cad = "";

                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.CONTROL) {
                        if (e.getEtiquetas() != null) {
                            boolean bandera = false;
                            cad = "Var " + padre + "_lista = [";
                            int pos = -1;
                            int i = 0;
                            for (Etiqueta et : e.getEtiquetas()) {
                                if (et.tipo == Etiqueta.Tipo.DATO) {
                                    if (!"".equals(et.traducir(e, name, padre, colorPadre, ventana, rutaActual, t, archivo, func))) {
                                        if (bandera) {
                                            cad = cad + ", ";
                                        }
                                        bandera = true;
                                        String valor = (String) et.traducir(e, name, padre, colorPadre, ventana, rutaActual, t, archivo, func);
                                        cad = cad + "\"" + valor + "\"";

                                        if (colorPadre != null) {
                                            if (colorPadre.equals(valor)) {
                                                pos = i;
                                            }
                                        }
                                        i++;
                                    }
                                } else {
                                    System.out.println("Error! Etiqueta incorrecta, lista datos. Linea:" + et.getLinea());
                                }
                            }

                            if (bandera) {
                                cad = cad + "];\n#" + pos;
                            } else {
                                System.out.println("Error! No se encontraron datos. Lista datos. Linea:" + e.getLinea());
                            }

                        } else {
                            System.out.println("Error! ListaDatos sin datos. Linea:" + e.getLinea());
                        }
                    } else {
                        System.out.println("Error! ListaDatos no viene dentro de control. Linea:" + e.getLinea());
                    }
                } else {
                    System.out.println("Error! ListaDatos no viene de forma correcta. Linea:" + e.getLinea());
                }
                return cad;
            }
        },
        DATO {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
                String cad = "";

                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.LISTADATOS) {
                        if (e.getPlano() != null) {
                            if (e.getElementos() != null) {
                                System.out.println("Error! Elementos incorrectos en Dato. Linea:" + e.getLinea());
                            }
                            if (e.getEtiquetas() != null) {
                                System.out.println("Error! Etiquetas incorrectas en Dato. Linea:" + e.getLinea());
                            }
                            cad = e.getPlano();
                        } else {
                            System.out.println("Error! Dato sin valor. Linea:" + e.getLinea());
                        }
                    } else {
                        System.out.println("Error! Dato no viene dentro de ListaDatos. Linea:" + e.getLinea());
                    }
                } else {
                    System.out.println("Error! Dato no viene de forma correcta. Linea:" + e.getLinea());
                }
                return cad;
            }
        },
        DEFECTO {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
                String cad = "";

                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.CONTROL) {
                        if (e.getPlano() != null) {
                            if (e.getElementos() != null) {
                                System.out.println("Error! Elementos incorrectos en Defecto. Linea:" + e.getLinea());
                            }
                            if (e.getEtiquetas() != null) {
                                System.out.println("Error! Etiquetas incorrectas en Defecto. Linea:" + e.getLinea());
                            }
                            cad = e.getPlano();
                        } else {
                            System.out.println("Error! Defecto sin valor. Linea:" + e.getLinea());
                        }
                    } else {
                        System.out.println("Error! Defecto no viente dentro de control. Linea:" + e.getLinea());
                    }
                } else {
                    System.out.println("Error! Etiqueta defecto no viene de forma correcta. Linea:" + e.getLinea());
                }
                return cad;
            }
        },
        MULTIMEDIA {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
                String cad = "";

                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.CONTENEDOR) {
                        String path = null;
                        String tipo = null;
                        String nombre = null;
                        Integer x = null;
                        Integer y = null;

                        int alto = 500;
                        int ancho = 500;
                        boolean auto_reproduccion = false;

                        if (e.getElementos() != null) {
                            for (Elemento elemento : e.getElementos()) {
                                if (elemento.getTipo() == Elemento.Tipo.PATH) {
                                    path = elemento.getValor().toString();
                                } else if (elemento.getTipo() == Elemento.Tipo.TIPO) {
                                    tipo = elemento.getValor().toString();
                                } else if (elemento.getTipo() == Elemento.Tipo.NOMBRE) {
                                    nombre = elemento.getValor().toString();
                                } else if (elemento.getTipo() == Elemento.Tipo.X) {
                                    x = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.Y) {
                                    y = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.ALTO) {
                                    alto = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.ANCHO) {
                                    ancho = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.AUTO_REPRODUCCION) {
                                    auto_reproduccion = Boolean.valueOf(elemento.getValor().toString());
                                } else {
                                    System.out.println("Error! Elemento incorrecto en texto");
                                }
                            }

                            if (path != null && nombre != null && tipo != null && x != null && y != null) {
                                String auto = auto_reproduccion ? "verdadero" : "falso";
                                if (tipo.toLowerCase().equals("imagen")) {
                                    cad = padre + ".CrearImagen(\"" + path + "\", " + x + ", " + y + ", "
                                            + auto + ", " + alto + ", " + ancho + ");\n";
                                } else if (tipo.toLowerCase().equals("musica") || tipo.toLowerCase().equals("música")) {
                                    cad = padre + ".CrearReproductor(\"" + path + "\", " + x + ", " + y + ", "
                                            + auto + ", " + alto + ", " + ancho + ");\n";
                                } else if (tipo.toLowerCase().equals("video")) {
                                    cad = padre + ".CrearVideo(\"" + path + "\", " + x + ", " + y + ", "
                                            + auto + ", " + alto + ", " + ancho + ");\n";
                                } else {
                                    System.out.println("Error! Tipo incorrecto en multimedia. Línea:" + e.getLinea());
                                }
                            } else {
                                System.out.println("Error! Multimedia sin elementos obligatorios Línea:" + e.getLinea());
                            }

                        } else {
                            System.out.println("Error! Multimedia sin elementos Linea:" + e.getLinea());
                        }
                    } else {
                        System.out.println("Error! Multimedia no viente dentro de contenedor. Linea:" + e.getLinea());
                    }
                } else {
                    System.out.println("Error! Etiqueta multimedia no viene de forma correcta. Linea:" + e.getLinea());
                }

                return cad;
            }

        },
        BOTON {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
                String cad = "";

                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.CONTENEDOR) {
                        String nombre = null;
                        Integer x = null;
                        Integer y = null;

                        String fuente = "Arial";
                        int tam = 14;
                        String color = "#000000";
                        int alto = 500;
                        int ancho = 500;
                        String referencia = null;
                        String accion = null;

                        String plano = e.getPlano();

                        if (e.getElementos() != null) {
                            for (Elemento elemento : e.getElementos()) {
                                if (null == elemento.getTipo()) {
                                    System.out.println("Error! Elemento incorrecto en boton. Línea:" + e.getLinea());
                                } else {
                                    switch (elemento.getTipo()) {
                                        case NOMBRE:
                                            nombre = elemento.getValor().toString();
                                            break;
                                        case X:
                                            x = new Integer(elemento.getValor().toString());
                                            break;
                                        case Y:
                                            y = new Integer(elemento.getValor().toString());
                                            break;
                                        case FUENTE:
                                            fuente = elemento.getValor().toString();
                                            break;
                                        case TAM:
                                            tam = new Integer(elemento.getValor().toString());
                                            break;
                                        case COLOR:
                                            color = elemento.getValor().toString();
                                            break;
                                        case ALTO:
                                            alto = new Integer(elemento.getValor().toString());
                                            break;
                                        case ANCHO:
                                            ancho = new Integer(elemento.getValor().toString());
                                            break;
                                        case REFERENCIA:
                                            referencia = elemento.getValor().toString();
                                            break;
                                        case ACCION:
                                            accion = elemento.getValor().toString().replaceAll("\\{", "").replaceAll("\\}", "");
                                            break;
                                        default:
                                            System.out.println("Error! Elemento incorrecto en boton. Línea:" + e.getLinea());
                                            break;
                                    }
                                }
                            }
                        }

                        if (e.getEtiquetas() != null) {
                            for (Etiqueta et : e.getEtiquetas()) {
                                if (et.tipo == Etiqueta.Tipo.TEXTO) {
                                    plano = et.getPlano();
                                    if (et.getElementos() != null) {
                                        for (Elemento elemento : et.getElementos()) {
                                            if (null == elemento.getTipo()) {
                                                System.out.println("Error! Elemento incorrecto en texto. Línea:" + et.getLinea());
                                            } else {
                                                switch (elemento.getTipo()) {
                                                    case NOMBRE:
                                                        nombre = elemento.getValor().toString();
                                                        break;
                                                    case X:
                                                        x = new Integer(elemento.getValor().toString());
                                                        break;
                                                    case Y:
                                                        y = new Integer(elemento.getValor().toString());
                                                        break;
                                                    case FUENTE:
                                                        fuente = elemento.getValor().toString();
                                                        break;
                                                    case TAM:
                                                        tam = new Integer(elemento.getValor().toString());
                                                        break;
                                                    case COLOR:
                                                        color = elemento.getValor().toString();
                                                        break;
                                                    case ALTO:
                                                        alto = new Integer(elemento.getValor().toString());
                                                        break;
                                                    case ANCHO:
                                                        ancho = new Integer(elemento.getValor().toString());
                                                        break;
                                                    case REFERENCIA:
                                                        referencia = elemento.getValor().toString();
                                                        break;
                                                    case ACCION:
                                                        accion = elemento.getValor().toString().replaceAll("\\{", "").replaceAll("\\}", "");
                                                        break;
                                                    default:
                                                        System.out.println("Error! Elemento incorrecto en texto. Línea:" + et.getLinea());
                                                        break;
                                                }
                                            }
                                        }
                                    }

                                    if (et.getEtiquetas() != null) {
                                        System.out.println("Error! Etiquetas dentro de Texto Linea:" + et.getLinea());
                                    }
                                } else {
                                    System.out.println("Error! Etiqueta incorrecta, boton. Linea:" + et.getLinea());
                                }
                            }
                        }

                        if (nombre != null && x != null && y != null) {
                            plano = plano == null ? "" : plano;
                            referencia = referencia == null ? "nulo" : "CargarVentana_" + referencia + "()";
                            cad = "Var " + nombre + name + " = " + padre + ".CrearBoton(\"" + fuente + "\", " + tam
                                    + ", \"" + color + "\", " + x + ", " + y + ", " + referencia + ", \"" + plano
                                    + "\", " + alto + ", " + ancho + ");\n";

                            if (accion != null) {
                                cad = cad + nombre + name + ".AlClic(" + accion + ");\n";
                            }

                        } else {
                            System.out.println("Error! Etiqueta boton sin elementos obligatorios. Línea:" + e.getLinea());
                        }

                    } else {
                        System.out.println("Error! Boton no viente dentro de contenedor. Línea:" + e.getLinea());
                    }
                } else {
                    System.out.println("Error! Etiqueta boton no viene de forma correcta. Línea:" + e.getLinea());
                }

                return cad;
            }
        },
        ENVIAR {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
                String cad = "";
                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.CONTENEDOR) {
                        String nombre = null;
                        Integer x = null;
                        Integer y = null;

                        String fuente = "Arial";
                        int tam = 14;
                        String color = "#000000";
                        int alto = 500;
                        int ancho = 500;
                        String referencia = null;
                        String accion = null;

                        String plano = e.getPlano();

                        if (e.getElementos() != null) {
                            for (Elemento elemento : e.getElementos()) {
                                if (null == elemento.getTipo()) {
                                    System.out.println("Error! Elemento incorrecto en boton. Línea:" + e.getLinea());
                                } else {
                                    switch (elemento.getTipo()) {
                                        case NOMBRE:
                                            nombre = elemento.getValor().toString();
                                            break;
                                        case X:
                                            x = new Integer(elemento.getValor().toString());
                                            break;
                                        case Y:
                                            y = new Integer(elemento.getValor().toString());
                                            break;
                                        case FUENTE:
                                            fuente = elemento.getValor().toString();
                                            break;
                                        case TAM:
                                            tam = new Integer(elemento.getValor().toString());
                                            break;
                                        case COLOR:
                                            color = elemento.getValor().toString();
                                            break;
                                        case ALTO:
                                            alto = new Integer(elemento.getValor().toString());
                                            break;
                                        case ANCHO:
                                            ancho = new Integer(elemento.getValor().toString());
                                            break;
                                        case REFERENCIA:
                                            referencia = elemento.getValor().toString();
                                            break;
                                        case ACCION:
                                            accion = elemento.getValor().toString().replaceAll("\\{", "").replaceAll("\\}", "");
                                            break;
                                        default:
                                            System.out.println("Error! Elemento incorrecto en boton. Línea:" + e.getLinea());
                                            break;
                                    }
                                }
                            }
                        }

                        if (e.getEtiquetas() != null) {
                            for (Etiqueta et : e.getEtiquetas()) {
                                if (et.tipo == Etiqueta.Tipo.TEXTO) {
                                    plano = et.getPlano();
                                    if (et.getElementos() != null) {
                                        for (Elemento elemento : et.getElementos()) {
                                            if (null == elemento.getTipo()) {
                                                System.out.println("Error! Elemento incorrecto en texto. Línea:" + et.getLinea());
                                            } else {
                                                switch (elemento.getTipo()) {
                                                    case NOMBRE:
                                                        nombre = elemento.getValor().toString();
                                                        break;
                                                    case X:
                                                        x = new Integer(elemento.getValor().toString());
                                                        break;
                                                    case Y:
                                                        y = new Integer(elemento.getValor().toString());
                                                        break;
                                                    case FUENTE:
                                                        fuente = elemento.getValor().toString();
                                                        break;
                                                    case TAM:
                                                        tam = new Integer(elemento.getValor().toString());
                                                        break;
                                                    case COLOR:
                                                        color = elemento.getValor().toString();
                                                        break;
                                                    case ALTO:
                                                        alto = new Integer(elemento.getValor().toString());
                                                        break;
                                                    case ANCHO:
                                                        ancho = new Integer(elemento.getValor().toString());
                                                        break;
                                                    case REFERENCIA:
                                                        referencia = elemento.getValor().toString();
                                                        break;
                                                    case ACCION:
                                                        accion = elemento.getValor().toString().replaceAll("\\{", "").replaceAll("\\}", "");
                                                        break;
                                                    default:
                                                        System.out.println("Error! Elemento incorrecto en texto. Línea:" + et.getLinea());
                                                        break;
                                                }
                                            }
                                        }
                                    }

                                    if (et.getEtiquetas() != null) {
                                        System.out.println("Error! Etiquetas dentro de Texto Linea:" + et.getEtiquetas().get(0).getLinea());
                                    }
                                } else {
                                    System.out.println("Error! Etiqueta incorrecta, Enviar. Linea:" + et.getLinea());
                                }
                            }
                        }

                        if (nombre != null && x != null && y != null) {
                            plano = plano == null ? "" : plano;
                            referencia = referencia == null ? "nulo" : "CargarVentana_" + referencia + "()";
                            cad = "Var " + nombre + name + " = " + padre + ".CrearBoton(\"" + fuente + "\", " + tam
                                    + ", \"" + color + "\", " + x + ", " + y + ", " + referencia + ", \"" + plano
                                    + "\", " + alto + ", " + ancho + ");\n";

                            if (accion != null) {
                                cad += nombre + name + ".AlClic(" + nombre + name + "AlClic());\n";
                                cad += "funcion " + nombre + name + "AlClic(){\n\tGuardar_" + ventana + name + "();\n\t"
                                        + accion + ";\n}\n\n";
                            } else {
                                cad = cad + nombre + name + ".AlClic(Guardar_" + ventana + name + "());\n";
                            }

                        } else {
                            System.out.println("Error! Etiqueta boton sin elementos obligatorios. Línea:" + e.getLinea());
                        }

                    } else {
                        System.out.println("Error! Boton no viente dentro de contenedor. Línea:" + e.getLinea());
                    }
                } else {
                    System.out.println("Error! Etiqueta boton no viene de forma correcta. Línea:" + e.getLinea());
                }
                return cad;
            }
        };

        /**
         *
         * @param e Etiqueta actual
         * @param p Etiqueta padre
         * @param name nombre del archivo
         * @param padre nombre del padre
         * @param colorPadre codigo de color del padre
         * @param ventana
         * @param rutaActual
         * @param t
         * @param archivo
         * @return codigo traducido
         */
        public abstract Object traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func);
    }

    private Tipo tipo;
    private String plano;
    private int linea;
    private int columna;
    private LinkedList<Etiqueta> etiquetas;
    private LinkedList<Elemento> elementos;

    protected Map<Integer, Object> archivo;

    public Etiqueta(Tipo tipo, String plano, int linea, int columna, LinkedList<Etiqueta> etiquetas, LinkedList<Elemento> elementos) {
        this.tipo = tipo;
        this.plano = plano;
        this.linea = linea;
        this.columna = columna;
        this.etiquetas = etiquetas;
        this.elementos = elementos;
        this.archivo = null;
    }

    public Object traducir(Etiqueta p, String name, String padre, String colorPadre, String ventana, String rutaActual, int t, Map<Integer, Object> archivo, boolean func) {
        return this.tipo.traducir(this, p, name, padre, colorPadre, ventana, rutaActual, t, archivo, func);
    }

    public void recorrer() {
        System.out.println(this.getTipo().toString() + ": " + ((this.plano != null) ? this.plano : ""));
        if (etiquetas != null) {
            for (Etiqueta e : etiquetas) {
                e.recorrer();
            }
        }
    }

    /**
     * @return the tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the plano
     */
    public String getPlano() {
        return plano;
    }

    /**
     * @param plano the plano to set
     */
    public void setPlano(String plano) {
        this.plano = plano;
    }

    /**
     * @return the linea
     */
    public int getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(int linea) {
        this.linea = linea;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

    /**
     * @return the etiquetas
     */
    public LinkedList<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    /**
     * @param etiquetas the etiquetas to set
     */
    public void setEtiquetas(LinkedList<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    /**
     * @return the elementos
     */
    public LinkedList<Elemento> getElementos() {
        return elementos;
    }

    /**
     * @param elementos the elementos to set
     */
    public void setElementos(LinkedList<Elemento> elementos) {
        this.elementos = elementos;
    }

    /**
     * @return the archivo
     */
    public Map<Integer, Object> getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(Map<Integer, Object> archivo) {
        this.archivo = archivo;
    }

}
