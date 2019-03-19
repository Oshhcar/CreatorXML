/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericxml;

import fs.ast.simbolos.Arreglo;
import fs.ast.simbolos.Objeto;
import genericxml.Etiqueta.Tipo;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author oscar
 */
public class Arbol {

    private LinkedList<Etiqueta> imports;
    private LinkedList<Etiqueta> etiquetas;

    public Arbol(LinkedList<Etiqueta> imports, LinkedList<Etiqueta> etiquetas) {
        this.imports = imports;
        this.etiquetas = etiquetas;
    }

    public String traducir(String name, String rutaActual){
        String cad = "";
        name = "_" + name;
        //name = "";
        
        Map<Integer, Object> archivo = new Arreglo();
        
        if(imports != null){
            for(Etiqueta i: imports){
                cad = cad + i.traducir(null, name, "", "", "", rutaActual, 1, null, false);
            }
        }
        
        cad += "\n";
               
        if(etiquetas != null){
            for(Etiqueta e: etiquetas){
                
                cad = cad + e.traducir(null, name, "", "", "", rutaActual, 1, archivo, false);
            }
        }
        
        if(imports != null){
            for(Etiqueta i: imports){
                cad = cad + i.traducir(null, name, "", "", "", rutaActual, 1, archivo, false);
            }
        }
        
        cad += "\n";
        
        if(etiquetas != null){
            for(Etiqueta e: etiquetas){
                if(e.getTipo() == Tipo.VENTANA){
                    cad = cad + e.traducir(null, name, "", "", "", rutaActual, 1, archivo, true);
                }
            }
        }
        
        
        return cad;
    }
    
    public void recorrer(){
        if(etiquetas != null){
            for (Etiqueta e: etiquetas){
                e.recorrer();
            }
        }
    }
    
    /**
     * @return the imports
     */
    public LinkedList<Etiqueta> getImports() {
        return imports;
    }

    /**
     * @param imports the imports to set
     */
    public void setImports(LinkedList<Etiqueta> imports) {
        this.imports = imports;
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

}
