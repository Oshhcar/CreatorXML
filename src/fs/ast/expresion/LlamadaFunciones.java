/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.expresion;

import fs.ast.expresion.nativas.Alguno;
import fs.ast.expresion.nativas.Buscar;
import fs.ast.expresion.nativas.CrearArray;
import fs.ast.expresion.nativas.CrearBoton;
import fs.ast.expresion.nativas.CrearContenedor;
import fs.ast.expresion.nativas.Filtrar;
import fs.ast.expresion.nativas.Maximo;
import fs.ast.expresion.nativas.Minimo;
import fs.ast.expresion.nativas.Map;
import fs.ast.expresion.nativas.Obtener;
import fs.ast.expresion.nativas.Reduce;
import fs.ast.expresion.nativas.Todos;
import fs.ast.instruccion.nativas.CrearCajaTexto;
import fs.ast.instruccion.nativas.CrearControlNumerico;
import fs.ast.instruccion.nativas.CrearDesplegable;
import fs.ast.instruccion.nativas.CrearTexto;
import fs.ast.instruccion.nativas.Invertir;
import fs.ast.instruccion.nativas.Ordenamiento;
import fs.ast.simbolos.Simbolo;
import fs.ast.simbolos.TablaSimbolos;
import fs.ast.simbolos.Tipo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class LlamadaFunciones implements Expresion {

    private final String id;
    private LinkedList<LlamadaFuncion> funciones;
    private Tipo tipo;
    private final int linea;
    private final int columna;

    public LlamadaFunciones(String id, int linea, int columna) {
        this.id = id;
        this.funciones = new LinkedList<>();
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
        Simbolo s = tabla.getSimbolo(id);
        Object arreglo = s.getValor();
        if (arreglo != null) {
            for (int i = 0; i < this.funciones.size(); i++) {
                LlamadaFuncion llamada = this.funciones.get(i);

                switch (llamada.getId().toLowerCase()) {
                    case "descendente":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion descendente no necesita parámetros. Linea: " + linea);
                        }
                        Ordenamiento desc = new Ordenamiento(id, "desc", linea, columna);
                        arreglo = desc.ejecutar(tabla, salida, false, false, dirActual);
                        if (arreglo != null) {
                            tipo = Tipo.ARREGLO;
                        }
                        break;
                    case "ascendente":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion ascendente no necesita parámetros. Linea: " + linea);
                        }
                        Ordenamiento asc = new Ordenamiento(id, "asc", linea, columna);
                        arreglo = asc.ejecutar(tabla, salida, false, false, dirActual);
                        if (arreglo != null) {
                            tipo = Tipo.ARREGLO;
                        }
                        break;
                    case "invertir":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion invertir no necesita parámetros. Linea: " + linea);
                        }
                        Invertir inv = new Invertir(id, linea, columna);
                        arreglo = inv.ejecutar(tabla, salida, false, false, dirActual);
                        if (arreglo != null) {
                            tipo = Tipo.ARREGLO;
                        }
                        break;
                    case "maximo":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion maximo no necesita parámetros. Linea: " + linea);
                        }
                        Maximo maximo = new Maximo(arreglo, linea, columna);
                        arreglo = maximo.getValor(tabla, salida, dirActual);
                        tipo = maximo.getTipo(tabla);
                        break;
                    case "minimo":
                        if (llamada.getParametros() != null) {
                            System.err.println("Error, la funcion maximo no necesita parámetros. Linea: " + linea);
                        }
                        Minimo minimo = new Minimo(arreglo, linea, columna);
                        arreglo = minimo.getValor(tabla, salida, dirActual);
                        tipo = minimo.getTipo(tabla);
                        break;
                    case "filtrar":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Filtrar filtrar = new Filtrar(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = filtrar.getValor(tabla, salida, dirActual);
                            tipo = filtrar.getTipo(tabla);
                        }
                        break;
                    case "buscar":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Buscar buscar = new Buscar(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = buscar.getValor(tabla, salida, dirActual);
                            tipo = buscar.getTipo(tabla);
                        }
                        break;
                    case "map":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Map map = new Map(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = map.getValor(tabla, salida, dirActual);
                            tipo = map.getTipo(tabla);
                        }
                        break;
                    case "reduce":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Reduce reduce = new Reduce(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = reduce.getValor(tabla, salida, dirActual);
                            tipo = reduce.getTipo(tabla);
                        }
                        break;
                    case "todos":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Todos todos = new Todos(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = todos.getValor(tabla, salida, dirActual);
                            tipo = todos.getTipo(tabla);
                        }
                        break;
                    case "alguno":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, la funcion filtrar como parámetro el nombre de la funcion. Linea: " + linea);
                            //retornar
                        } else {
                            Alguno alguno = new Alguno(arreglo, llamada.getParametros(), linea, columna);
                            arreglo = alguno.getValor(tabla, salida, dirActual);
                            tipo = alguno.getTipo(tabla);
                        }
                        break;
                    case "obtenerporetiqueta":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, ObtenerPorEtiqueta necesita el nombre de la etiqueta. Linea: " + linea);
                        } else {
                            Obtener porEtiqueta = new Obtener(arreglo, llamada.getParametros(), 1, linea, columna);
                            arreglo = porEtiqueta.getValor(tabla, salida, dirActual);
                            tipo = porEtiqueta.getTipo(tabla);
                        }
                        break;
                    case "obtenerporid":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, ObtenerPorId necesita el nombre de la etiqueta. Linea: " + linea);
                        } else {
                            Obtener porId = new Obtener(arreglo, llamada.getParametros(), 2, linea, columna);
                            arreglo = porId.getValor(tabla, salida, dirActual);
                            tipo = porId.getTipo(tabla);
                        }
                        break;
                    case "obtenerpornombre":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, ObtenerPorNombre necesita el nombre de la etiqueta. Linea: " + linea);
                        } else {
                            Obtener porNombre = new Obtener(arreglo, llamada.getParametros(), 3, linea, columna);
                            arreglo = porNombre.getValor(tabla, salida, dirActual);
                            tipo = porNombre.getTipo(tabla);
                        }
                        break;
                    case "crearcontenedor":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, CrearContenedor necesita parametros. Linea: " + linea);
                        } else {
                            CrearContenedor cont = new CrearContenedor(arreglo, llamada.getParametros(), linea, columna);
                            Object valRet = cont.getValor(tabla, salida, dirActual);
                            tipo = cont.getTipo(tabla);
                            arreglo = valRet;
                        }
                        break;
                    case "creartexto":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, CrearTexto necesita parametros. Linea: " + linea);
                        } else {
                            CrearTexto texto = new CrearTexto(arreglo, llamada.getParametros(), linea, columna);
                            Object valRet = texto.ejecutar(tabla, salida, false, false, dirActual);
                            tipo = Tipo.NULL;
                            arreglo = valRet;
                        }
                        break;
                    case "crearcajatexto":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, CrearCajaTexto necesita parametros. Linea: " + linea);
                        } else {
                            CrearCajaTexto texto = new CrearCajaTexto(arreglo, llamada.getParametros(), 1, linea, columna);
                            Object valRet = texto.ejecutar(tabla, salida, false, false, dirActual);
                            tipo = Tipo.NULL;
                            arreglo = valRet;
                        }
                        break;
                    case "crearareatexto":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, CrearCajaTexto necesita parametros. Linea: " + linea);
                        } else {
                            CrearCajaTexto texto = new CrearCajaTexto(arreglo, llamada.getParametros(), 2, linea, columna);
                            Object valRet = texto.ejecutar(tabla, salida, false, false, dirActual);
                            tipo = Tipo.NULL;
                            arreglo = valRet;
                        }
                        break;
                    case "crearcontrolnumerico":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, CrearControlNumerico necesita parametros. Linea: " + linea);
                        } else {
                            CrearControlNumerico numerico = new CrearControlNumerico(arreglo, llamada.getParametros(), linea, columna);
                            Object valRet = numerico.ejecutar(tabla, salida, false, false, dirActual);
                            tipo = Tipo.NULL;
                            arreglo = valRet;
                        }
                        break;
                    case "creardesplegable":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, CrearDesplegable necesita parametros. Linea: " + linea);
                        } else {
                            CrearDesplegable desplegable = new CrearDesplegable(arreglo, llamada.getParametros(), linea, columna);
                            Object valRet = desplegable.ejecutar(tabla, salida, false, false, dirActual);
                            tipo = Tipo.NULL;
                            arreglo = valRet;
                        }
                        break;
                    case "crearboton":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, CrearBoton necesita parametros. Linea: " + linea);
                        } else {
                            CrearBoton boton = new CrearBoton(arreglo, llamada.getParametros(), linea, columna);
                            Object valRet = boton.getValor(tabla, salida, dirActual);
                            tipo = Tipo.BOTON;
                            arreglo = valRet;
                        }
                        break;
                    case "alcargar":
                        if (llamada.getParametros() != null) {
                            //System.err.println("Error, AlCargar no necesita parametros. Linea: " + linea);
                            if (arreglo instanceof JFrame) {
                                JFrame ven = (JFrame) arreglo;
                                if (llamada.getParametros().size() >= 1) {
                                    Expresion exp = llamada.getParametros().get(0);
                                    if (exp != null) {
                                        if (exp instanceof LlamadaFuncion) {
                                            LlamadaFuncion call = (LlamadaFuncion) exp;

                                            call.setMostrarError(false);

                                            ven.addWindowListener(new WindowAdapter() {
                                                public void windowActivated(WindowEvent e) {
                                                    call.getValor(tabla, salida, dirActual);
                                                }
                                            });

                                        } else {
                                            System.err.println("Error, se esperaba una llamada a un metodol. Línea: " + linea);
                                        }
                                    }
                                } else {
                                    System.err.println("Error, AlClic sin parametros. Línea:" + linea);
                                }
                            }
                        } else {
                            if (arreglo instanceof JFrame) {
                                JFrame ven = (JFrame) arreglo;
                                ven.setVisible(true);
                            } else {
                                System.err.println("Error, variable no es una ventana. Line: " + linea);
                            }
                        }
                        tipo = Tipo.NULL;
                        arreglo = "nulo";
                        break;
                    case "alcerrar":
                        if (llamada.getParametros() != null) {
                            //System.err.println("Error, AlCargar no necesita parametros. Linea: " + linea);
                            if (arreglo instanceof JFrame) {
                                JFrame ven = (JFrame) arreglo;
                                if (llamada.getParametros().size() >= 1) {
                                    Expresion exp = llamada.getParametros().get(0);
                                    if (exp != null) {
                                        if (exp instanceof LlamadaFuncion) {
                                            LlamadaFuncion call = (LlamadaFuncion) exp;

                                            call.setMostrarError(false);

                                            ven.addWindowListener(new WindowAdapter() {
                                                public void windowDeactivated(WindowEvent e) {
                                                    call.getValor(tabla, salida, dirActual);
                                                }
                                            });

                                        } else {
                                            System.err.println("Error, se esperaba una llamada a un metodol. Línea: " + linea);
                                        }
                                    }
                                } else {
                                    System.err.println("Error, AlClic sin parametros. Línea:" + linea);
                                }
                            }
                        } else {
                            if (arreglo instanceof JFrame) {
                                JFrame ven = (JFrame) arreglo;
                                ven.setVisible(false);
                            } else {
                                System.err.println("Error, variable no es una ventana. Line: " + linea);
                            }
                        }
                        tipo = Tipo.NULL;
                        arreglo = "nulo";
                        break;
                    case "alclic":
                        if (llamada.getParametros() == null) {
                            System.err.println("Error, AlClic necesita la funcion a ejecutar. Linea: " + linea);
                        } else {
                            if (arreglo instanceof JButton) {
                                JButton boton = (JButton) arreglo;
                                if (llamada.getParametros().size() >= 1) {
                                    Expresion exp = llamada.getParametros().get(0);
                                    if (exp != null) {
                                        if (exp instanceof LlamadaFuncion) {
                                            LlamadaFuncion call = (LlamadaFuncion) exp;

                                            call.setMostrarError(false);
                                            boton.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent ae) {
                                                    call.getValor(tabla, salida, dirActual);
                                                }
                                            });
                                        } else {
                                            System.err.println("Error, se esperaba una llamada a un metodol. Línea: " + linea);
                                        }
                                    }
                                } else {
                                    System.err.println("Error, AlClic sin parametros. Línea:" + linea);
                                }
                            } else {
                                System.err.println("Error, variable no es un boton. Line: " + linea);
                            }
                        }
                        tipo = Tipo.NULL;
                        arreglo = "nulo";
                        break;
                    case "creararraydesdearchivo":
                        CrearArray crearArray = new CrearArray(llamada.getParametros(), linea, columna);
                        crearArray.setVentana(arreglo);
                        Object valRet = crearArray.getValor(tabla, salida, dirActual);
                        tipo = crearArray.getTipo(tabla);
                        arreglo = valRet;
                        break;
                    default:
                        System.err.println("Error, funcion nativa \"" + llamada.getId() + "\" no definida. Línea: " + linea);

                }
            }
            return arreglo;
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

    public void add(String id, LinkedList<Expresion> parametros) {
        this.funciones.add(new LlamadaFuncion(id, parametros, linea, columna));
    }
}
