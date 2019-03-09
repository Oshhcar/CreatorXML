/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.simbolos;

/**
 *
 * @author oscar
 */
public enum Tipo {
    CADENA{
        @Override
        public boolean isNumero() {
            return false;
        }
    
    },
    ENTERO{
        @Override
        public boolean isNumero() {
            return true;
        }
        
    },
    DECIMAL{
        @Override
        public boolean isNumero() {
            return true;
        }
    
    },
    BOOLEANO{
        @Override
        public boolean isNumero() {
            return false;
        }
    
    },
    NULL{
        @Override
        public boolean isNumero() {
            return false;
        }
    
    },
    IDENTIFICADOR{
        @Override
        public boolean isNumero() {
            return false;
        }
    
    },
    VAR{
        @Override
        public boolean isNumero() {
            return false;
        }
        
    }, 
    FUNCION{
        @Override
        public boolean isNumero() {
            return false;
        }
    
    }, 
    OBJETO{
        @Override
        public boolean isNumero() {
            return false;
        }
    
    };
    
    public abstract boolean isNumero();
}
