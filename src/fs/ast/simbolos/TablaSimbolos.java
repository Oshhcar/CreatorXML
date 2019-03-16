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
        this.add(new Simbolos());
    }

    public boolean existeLocal(String id) {
        int i = this.size() - 1;
        Simbolos sims = this.get(i);
        return null != sims.getSimbolo(id);
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

    public FuncionSim getFuncion(String id) {
        Simbolos sims = this.get(0);
        for (int i = sims.size() - 1; i >= 0; i--) {
            Simbolo s = sims.get(i);
            if (s.getId().equals(id)) {
                if (s instanceof FuncionSim) {
                    FuncionSim f = (FuncionSim) s;
                    if(f.getParametros() == null)
                        return f;
                }
            }
        }
        return null;
    }

    public FuncionSim getFuncion(String id, int p) {
        Simbolos sims = this.get(0);
        for (int i = sims.size() - 1; i >= 0; i--) {
            Simbolo s = sims.get(i);
            if (s.getId().equals(id)) {
                if (s instanceof FuncionSim) {
                    FuncionSim f = (FuncionSim) s;
                    if (f.getParametros() != null) {
                        if (f.getParametros().size() == p) {
                            return (FuncionSim) s;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public void nuevoAmbito(){
        this.add(new Simbolos());
    }
    
    public void salirAmbito(){
        this.removeLast();
    }
}
