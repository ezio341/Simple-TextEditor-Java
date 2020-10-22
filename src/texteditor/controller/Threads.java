/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import texteditor.views.MainEditor;

/**
 *
 * @author ASUS
 */
public class Threads extends Thread{
    MainEditor view;
    public Threads(MainEditor view){
        this.view=view;
    }
    @Override
    public void run(){
        while(true){
        
        }
    }
}
