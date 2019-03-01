/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericxml;

/**
 *
 * @author oscar
 */
public class Elemento {

    public static enum Tipo {
        ID{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof String;
            }
            
        },
        TIPO{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof String;
            }
        
        },
        COLOR{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof String;
            }
        
        },
        ACCIONINICIAL{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof String;
            }
        
        },
        ACCIONFINAL{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof String;
            }
        
        },
        X{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof Integer || valor instanceof Double;
            }
            
        },
        Y{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof Integer || valor instanceof Double;
            }
        
        },
        ALTO{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof Integer || valor instanceof Double;
            }
            
        },
        ANCHO{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof Integer || valor instanceof Double;
            }
        
        },
        BORDE{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof Boolean;
            }
        
        },
        NOMBRE{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof String;
            }
        
        },
        FUENTE{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof String;
            }
        
        },
        TAM{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof Integer || valor instanceof Double;
            }
        
        },
        NEGRITA{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof Boolean;
            }
            
        },
        CURSIVA{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof Boolean;
            }
        
        },
        MAXIMO{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof Integer || valor instanceof Double;
            }
        
        },
        MINIMO{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof Integer || valor instanceof Double;
            }
        
        },
        ACCION{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof String;
            }
        },
        REFERENCIA{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof String;
            }
        
        },
        PATH{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof String;
            }
        
        },
        AUTO_REPRODUCCION{
            @Override
            public boolean comprobarTipo(Object valor) {
                return valor instanceof Boolean;
            }
            
        };
        
        public abstract boolean comprobarTipo(Object valor);
    }

    private Tipo tipo;
    private Object valor;
    private int linea;
    private int columna;

    public Elemento(Tipo tipo, Object valor, int linea, int columna) {
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }
    
    public boolean comprobarTipo(){
        return this.tipo.comprobarTipo(this.valor);
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
     * @return the valor
     */
    public Object getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Object valor) {
        this.valor = valor;
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

}
