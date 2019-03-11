/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fs.ast.simbolos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author oscar
 */
public class Objeto extends Simbolo{
    
    
    public Objeto(String id, Map<String, Object> valor) {
        super(Tipo.OBJETO, id);
        super.setValor(valor);
    }
    
    public Objeto(String id) {
        super(Tipo.OBJETO, id);
        super.setValor(null);
    }
}
