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
public class TablaSimbolos extends LinkedList<Simbolos> {

    public TablaSimbolos() {
        super();
    }

    public void addSimbolo(Simbolo s) {
        int i = this.size() - 1;
        Simbolos sims = this.get(i);
        sims.add(s);
    }

    public Simbolo getSimbolo(String id) {
        for (int i = this.size() - 1; i >= 0; i--) {
            Simbolos sims = this.get(i);
            Simbolo s = sims.getSimbolo(id);
            if (s != null) {
                return s;
            }
        }
        return null;
    }

    public void setValor(String id, Object valor) {
        for (int i = this.size() - 1; i >= 0; i--) {
            Simbolos sims = this.get(i);
            Simbolo s = sims.getSimbolo(id);
            if (s != null) {
                s.setValor(valor);
            }
        }
    }

    public FuncionSim getFuncion(String id) {
        Simbolos sims = this.get(0);
        Simbolo s = sims.getSimbolo(id);
        if(s != null){
            if(s instanceof FuncionSim){
                return (FuncionSim) s;
            }
        }
        return null;
    }

}
