/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creatorxml;

import java.util.ArrayList;

/**
 *
 * @author oscar
 */
public class CreatorXML {

    /**
     * @param args the command line arguments
     */
    
    public static ArrayList<AnalizadorError> errores;
    
    public static void main(String[] args) {
        errores = new ArrayList<>();
        // TODO code application logic here
        Editor e = new Editor();
        e.show();
    }
    
}
