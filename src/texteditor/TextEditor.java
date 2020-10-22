/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import texteditor.controller.MainEditorController;
import texteditor.views.MainEditor;
/**
 *
 * @author ASUS
 */
public class TextEditor {
    /**
     * @param args the command line arguments
     */
    static MainEditor view;
    public static void main(String[] args){
        // TODO code application logic here
        view = new MainEditor();
        view.setVisible(true);
    }

}
