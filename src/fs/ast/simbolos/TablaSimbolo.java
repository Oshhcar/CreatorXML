/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.simbolos;

import java.util.LinkedList;

/**
 *
 * @author oscar
 */
public class TablaSimbolo extends LinkedList<Simbolo> {

    public TablaSimbolo() {
        super();
    }

    public FuncionSim getFuncion(String id){
        for(int i = 0; i < this.size(); i++){
            Object s = this.get(i);
            
            if(s instanceof FuncionSim){
                FuncionSim fun = (FuncionSim) s;
                
                if(fun.getId().equals(id)){
                    return fun;
                }
            }
        }
        System.err.println("Error, funcion "+id+" no encontrada.");
        return null;
    }
    
    public Object getValor(String id) {
        for (int i = this.size() - 1; i >= 0; i--) {
            Simbolo s = this.get(i);

            if (s.getId().equals(id)) {
                Object aux = s.getValor();
                return aux;
            }

        }
        System.err.println("Error, no existe la variable " + id + ".");
        return null;
    }

    public void setValor(String id, Object valor) {
        for (int i = this.size() - 1; i >= 0; i--) {
            Simbolo s = this.get(i);
            if (s.getId().equals(id)) {
                s.setValor(valor);
                return;
            }
        }
        System.out.println("Error, no existe la variable " + id + ", "
                + "no se puede asignar el valor.");
    }

    public Tipo getTipo(String id) {
        for (int i = this.size() - 1; i >= 0; i--) {
            Simbolo s = this.get(i);

            if (s.getId().equals(id)) {
                return s.getTipo();
            }

        }
        System.err.println("Error, no existe la variable " + id + ".");
        return null;
    }

    public void setTipo(String id, Tipo tipo) {
        for (int i = this.size() - 1; i >= 0; i--) {
            Simbolo s = this.get(i);

            if (s.getId().equals(id)) {
                s.setTipo(tipo);
                return;
            }

        }
        System.err.println("Error, no existe la variable " + id + ".");
    }

}
