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
        Simbolos sims = this.peekLast();
        return null != sims.getSimbolo(id.toLowerCase());
    }

    public void addSimbolo(Simbolo s) {
        Simbolos sims = this.peekLast();
        sims.add(s);
    }

    public Simbolo getSimbolo(String id) {
        for (int i = this.size() - 1; i >= 0; i--) {
            Simbolos sims = this.get(i);
            Simbolo s = sims.getSimbolo(id.toLowerCase());
            if (s != null) {
                return s;

            }
        }
        return null;
    }

    public FuncionSim getFuncion(String id) {
        Simbolos sims = this.peekFirst();
        for (int i = sims.size() - 1; i >= 0; i--) {
            Simbolo s = sims.get(i);
            if (s.getId().equals(id.toLowerCase())) {
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
        Simbolos sims = this.peekFirst();
        for (int i = sims.size() - 1; i >= 0; i--) {
            Simbolo s = sims.get(i);
            if (s.getId().equals(id.toLowerCase())) {
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
    
}
