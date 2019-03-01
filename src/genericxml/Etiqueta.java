/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericxml;

import java.util.LinkedList;

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
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre) {
                String cad = "";
                if (e.getPlano() != null) {
                    cad = "Importar(\"" + e.getPlano().replaceAll(" ", "") + "\");\n";
                } else {
                    System.out.println("Error! importar");
                }
                return cad;
            }
        },
        VENTANA {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre) {
                String cad = "";
                if (p == null) {
                    String id = "";
                    String tipo = "";
                    String color = "#ffffff";
                    String accioninicial = "";
                    String accionfinal = "";

                    if (e.getElementos() != null) {
                        for (Elemento elemento : e.getElementos()) {
                            if (elemento.getTipo() == Elemento.Tipo.ID) {
                                id = elemento.getValor().toString();
                            } else if (elemento.getTipo() == Elemento.Tipo.TIPO) {
                                tipo = elemento.getValor().toString();
                            } else if (elemento.getTipo() == Elemento.Tipo.COLOR) {
                                color = elemento.getValor().toString();
                            } else if (elemento.getTipo() == Elemento.Tipo.ACCIONINICIAL) {
                                accioninicial = elemento.getValor().toString();
                            } else if (elemento.getTipo() == Elemento.Tipo.ACCIONFINAL) {
                                accionfinal = elemento.getValor().toString();
                            } else {
                                System.out.println("Error! Elemento incorrecto en ventana");
                            }
                        }

                        if (!"".equals(id) && !"".equals(tipo)) {
                            cad = "Var " + id + "_" + name + " = CrearVentana(\"" + color + "\");\n";

                            if (e.getEtiquetas() != null) {
                                for (Etiqueta et : e.getEtiquetas()) {
                                    cad = cad + et.traducir(e, name, id + "_" + name, color);
                                }
                            }
                            cad = cad + "\n";
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
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre) {
                String cad = "";

                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.VENTANA) {
                        String id = "";
                        int x = -1;
                        int y = -1;

                        int alto = 500;
                        int ancho = 500;
                        String color = colorPadre;
                        boolean borde = false;

                        if (e.getElementos() != null) {
                            for (Elemento elemento : e.getElementos()) {
                                if (elemento.getTipo() == Elemento.Tipo.ID) {
                                    id = elemento.getValor().toString();
                                } else if (elemento.getTipo() == Elemento.Tipo.X) {
                                    x = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.Y) {
                                    y = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.ALTO) {
                                    alto = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.ANCHO) {
                                    ancho = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.COLOR) {
                                    color = elemento.getValor().toString();
                                } else if (elemento.getTipo() == Elemento.Tipo.BORDE) {
                                    borde = Boolean.valueOf(elemento.getValor().toString());
                                } else {
                                    System.out.println("Error! Elemento incorrecto en contenedor");
                                }
                            }

                            if (!"".equals(id) && x != -1 && y != -1) {
                                String b = (borde) ? "verdadero" : "falso";

                                cad = "Var " + id + "_" + name + " = " + padre + ".CrearContenedor(" + alto + ", " + ancho + ", \""
                                        + color + "\", " + b + ", " + x + ", " + y + ");\n";

                                if (e.getEtiquetas() != null) {
                                    for (Etiqueta et : e.getEtiquetas()) {
                                        cad = cad + et.traducir(e, name, id + "_" + name, color);
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
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre) {
                String cad = "";

                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.CONTENEDOR) {
                        String nombre = "";
                        int x = -1;
                        int y = -1;

                        String fuente = "Arial";
                        int tam = 14;
                        String color = "#000000";
                        boolean negrita = false;
                        boolean cursiva = false;

                        if (e.getElementos() != null) {
                            for (Elemento elemento : e.getElementos()) {
                                if (elemento.getTipo() == Elemento.Tipo.NOMBRE) {
                                    nombre = elemento.getValor().toString();
                                } else if (elemento.getTipo() == Elemento.Tipo.X) {
                                    x = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.Y) {
                                    y = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.FUENTE) {
                                    fuente = elemento.getValor().toString();
                                } else if (elemento.getTipo() == Elemento.Tipo.TAM) {
                                    tam = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.COLOR) {
                                    color = elemento.getValor().toString();
                                } else if (elemento.getTipo() == Elemento.Tipo.NEGRITA) {
                                    negrita = Boolean.valueOf(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.CURSIVA) {
                                    cursiva = Boolean.valueOf(elemento.getValor().toString());
                                } else {
                                    System.out.println("Error! Elemento incorrecto en texto");
                                }
                            }

                            if (!"".equals(nombre) && x != -1 && y != -1 && e.plano != null) {
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
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre) {
                String cad = "";
                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.CONTENEDOR) {
                        String tipo = "";
                        String nombre = "";
                        int x = -1;
                        int y = -1;

                        int alto = 50;
                        int ancho = 50;
                        String fuente = "Arial";
                        int tam = 14;
                        String color = "#000000";
                        boolean negrita = false;
                        boolean cursiva = false;
                        int maximo = -1;
                        int minimo = -1;
                        String accion = "";

                        if (e.getElementos() != null) {
                            for (Elemento elemento : e.getElementos()) {
                                if (elemento.getTipo() == Elemento.Tipo.TIPO) {
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
                                } else if (elemento.getTipo() == Elemento.Tipo.FUENTE) {
                                    fuente = elemento.getValor().toString();
                                } else if (elemento.getTipo() == Elemento.Tipo.TAM) {
                                    tam = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.COLOR) {
                                    color = elemento.getValor().toString();
                                } else if (elemento.getTipo() == Elemento.Tipo.NEGRITA) {
                                    negrita = Boolean.valueOf(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.CURSIVA) {
                                    cursiva = Boolean.valueOf(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.MAXIMO) {
                                    maximo = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.MINIMO) {
                                    minimo = new Integer(elemento.getValor().toString());
                                } else if (elemento.getTipo() == Elemento.Tipo.ACCION) {
                                    accion = elemento.getValor().toString();
                                } else {
                                    System.out.println("Error! Elemento incorrecto en texto");
                                }
                            }

                            String n = negrita ? "verdadero" : "falso";
                            String c = cursiva ? "verdadero" : "falso";

                            if (!"".equals(tipo) && !"".equals(nombre) && x != -1 && y != -1) {
                                if (tipo.toLowerCase().equals("texto")) {
                                    cad = padre + ".CrearCajaTexto(" + alto + ", " + ancho + ", \"" + fuente + "\", "
                                            + tam + ", " + "\"" + color + "\", " + x + ", " + y + ", " + n + ", " + c + ");\n";
                                } else if (tipo.toLowerCase().equals("numérico") || tipo.toLowerCase().equals("numerico")) {
                                    cad = padre + ".CrearControlNumerico(" + alto + ", " + ancho + ", " + maximo + ", "
                                            + minimo + ", " + x + ", " + y + ");\n";
                                } else if (tipo.toLowerCase().equals("textoarea")) {
                                    cad = padre + ".CrearAreaTexto(" + alto + ", " + ancho + ", \"" + fuente + "\", "
                                            + tam + ", " + "\"" + color + "\", " + x + ", " + y + ", " + n + ", " + c + ");\n";
                                } else if (tipo.toLowerCase().equals("desplegable")) {
                                    if (e.getEtiquetas() != null) {
                                        String res = "";
                                        for (Etiqueta et : e.getEtiquetas()) {
                                            if(et.getTipo() == Etiqueta.Tipo.LISTADATOS){
                                                if("".equals(res))
                                                    res = et.traducir(e, name, nombre, colorPadre);
                                                else
                                                    System.out.println("Error! Ya se definio una lista. Linea:"+et.getLinea());
                                            } else {
                                                System.out.println("Error! Etiqueta incorrecta en control. Linea:"+et.getLinea());
                                            }
                                        }
                                        if(!"".equals(res)){
                                            cad = res + padre +".CrearDesplegable(" + alto + ", " + ancho + ", " + nombre + "_lista, "
                                                    + x + ", " + y + ", " + nombre + "_lista[0]);\n";
                                        } else {
                                            System.out.println("Error! No se definio una listadatos. Linea:"+e.getLinea());
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
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre) {
                String cad = "";

                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.CONTROL) {
                        if (e.getEtiquetas() != null) {
                            boolean bandera = false;
                            cad = "Var " + padre + "_lista = [";

                            for (Etiqueta et : e.getEtiquetas()) {
                                if (et.tipo == Etiqueta.Tipo.DATO) {
                                    if (!"".equals(et.traducir(e, name, padre, colorPadre))) {
                                        if(bandera)
                                            cad = cad +", ";
                                        bandera = true;
                                        cad = cad + "\"" + et.traducir(e, name, padre, colorPadre) + "\"";
                                    }
                                } else {
                                    System.out.println("Error! Etiqueta incorrecta, lista datos. Linea:" + et.getLinea());
                                }
                            }

                            if (bandera) {
                                cad = cad + "];\n";
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
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre) {
                String cad = "";

                if (p != null && !"".equals(padre)) {
                    if (p.getTipo() == Etiqueta.Tipo.LISTADATOS) {
                        if (e.plano != null) {
                            if (e.getElementos() != null) {
                                System.out.println("Error! Elementos incorrectos en Dato. Linea:" + e.getLinea());
                            }
                            if (e.getEtiquetas() != null) {
                                System.out.println("Error! Etiquetas incorrectas en Dato. Linea:" + e.getLinea());
                            }
                            cad = e.plano;
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
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre) {
                return "";
            }
        },
        MULTIMEDIA {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre) {
                return "";
            }

        },
        BOTON {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre) {
                return "";
            }
        },
        ENVIAR {
            @Override
            public String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre) {
                return "";
            }
        };

        /**
         *
         * @param e Etiqueta actual
         * @param p Etiqueta padre
         * @param name nombre del archivo
         * @param padre nombre del padre
         * @param colorPadre codigo de color del padre
         * @return codigo traducido
         */
        public abstract String traducir(Etiqueta e, Etiqueta p, String name, String padre, String colorPadre);
    }

    private Tipo tipo;
    private String plano;
    private int linea;
    private int columna;
    private LinkedList<Etiqueta> etiquetas;
    private LinkedList<Elemento> elementos;

    public Etiqueta(Tipo tipo, String plano, int linea, int columna, LinkedList<Etiqueta> etiquetas, LinkedList<Elemento> elementos) {
        this.tipo = tipo;
        this.plano = plano;
        this.linea = linea;
        this.columna = columna;
        this.etiquetas = etiquetas;
        this.elementos = elementos;
    }

    public String traducir(Etiqueta p, String name, String padre, String colorPadre) {
        return this.tipo.traducir(this, p, name, padre, colorPadre);
    }

    public void recorrer() {
        System.out.println(this.getTipo().toString()+": "+((this.plano!=null)?this.plano:""));
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

}
