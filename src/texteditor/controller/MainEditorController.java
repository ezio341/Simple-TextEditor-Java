/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
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
    public int getLineOfOffset(JTextComponent comp, int offset) throws BadLocationException {
        Document doc = comp.getDocument();
        if (offset < 0) {
            throw new BadLocationException("Can't translate offset to line", -1);
        } else if (offset > doc.getLength()) {
            throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
        } else {
            Element map = doc.getDefaultRootElement();
            return map.getElementIndex(offset);
        }
    }

    public int getLineStartOffset(JTextComponent comp, int line) throws BadLocationException {
        Element map = comp.getDocument().getDefaultRootElement();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line >= map.getElementCount()) {
            throw new BadLocationException("No such line", comp.getDocument().getLength() + 1);
        } else {
            Element lineElem = map.getElement(line);
            return lineElem.getStartOffset();
        }
    }
    public int getWordsNumber(JTextComponent comp) throws BadLocationException{
        Document doc = comp.getDocument();
        String par = doc.getText(0, doc.getLength());
        String[] words = par.split("\\s+");
        int countWord=0;
        if(doc.getLength()<=1){
            countWord=0;
        }else{
            for(String s : words)countWord++;
        }
        return countWord;
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
                JOptionPane.showMessageDialog(view, "File Saved.", 
                    "Information", JOptionPane.INFORMATION_MESSAGE);
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
                HTMLEditorKit htmledit = new HTMLEditorKit();
                HTMLDocument doc2 = new HTMLDocument(new HTMLDocument().getStyleSheet());
                reader = new PushbackReader(new InputStreamReader(new FileInputStream(loadFile.getSelectedFile())));
                htmledit.read(reader, doc2, 0);
                doc1.insertString(0, doc2.getText(0, doc2.getLength()), null);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainEditorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainEditorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
