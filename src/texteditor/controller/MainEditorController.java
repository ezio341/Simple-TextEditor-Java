/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import texteditor.views.MainEditor;
/**
 *
 * @author ASUS
 */
public class MainEditorController {
    MainEditor view;
//    HTMLEditorKit kit;
    public MainEditorController(MainEditor view) {
        this.view = view;
//        kit = view.getKit();
    }
    
    public void save() throws BadLocationException{
        JFileChooser loadFile = view.getLoadFile();
        StyledDocument doc = (StyledDocument)view.getTextArea().getDocument();
        FileNameExtensionFilter rtfFilter = new FileNameExtensionFilter("rtf files (*.rtf)", "rtf");
        // add filters
        loadFile.addChoosableFileFilter(rtfFilter);
        
        if (loadFile.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            HTMLEditorKit kit = new HTMLEditorKit();
            BufferedOutputStream out;
            try {
                out = new BufferedOutputStream(new FileOutputStream(loadFile.getSelectedFile().getAbsoluteFile()+".rtf"));

                kit.write(out, doc, doc.getStartPosition().getOffset(), doc.getLength());

            } catch (FileNotFoundException e) {
            } catch (IOException e){
            }
        }

    }
    
    public void open() throws BadLocationException{
        view.getTextArea().setText("");
        JFileChooser loadFile = view.getLoadFile();
        FileNameExtensionFilter rtfFilter = new FileNameExtensionFilter("rtf files (*.rtf)", "rtf");
        // add filters
        loadFile.addChoosableFileFilter(rtfFilter);
        StyledDocument doc1 =(StyledDocument) view.getTextArea().getDocument();
        if(JFileChooser.OPEN_DIALOG == loadFile.showOpenDialog(view)){
            PushbackReader reader=null;
            try {
                OutputStream os = new ByteArrayOutputStream();
                HTMLEditorKit htmledit = new HTMLEditorKit();
                HTMLDocument doc2 = new HTMLDocument();
                StyledEditorKit kit =new StyledEditorKit();
                reader = new PushbackReader(new InputStreamReader(new FileInputStream(loadFile.getSelectedFile())));
                htmledit.read(reader, doc2, 0);
                
                doc1.insertString(0, doc2.getText(0, doc2.getLength()), null);

                JOptionPane.showMessageDialog(view, "File berhasil dibaca.", 
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainEditorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainEditorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
