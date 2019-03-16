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
public class Simbolos extends LinkedList<Simbolo> {
    
    public Simbolos(){
        super();
    }
    
    public Simbolo getSimbolo(String id){
        for(int i = this.size() -1; i >= 0; i--){
            Simbolo s = this.get(i);
            if(s.getId().equals(id)){
                return s;
            }
        }
        return null;
    }
}
