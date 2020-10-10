/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.views;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe permettant la personnalisation de notre JTable.
 */
public class JtableRenderer extends DefaultTableCellRenderer {
    private int rowToColor;
    
    public JtableRenderer(int row){
        super();
        this.rowToColor = row;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
 
        // On color différament la ligne des scores.
        if(row==rowToColor){
            setBackground(new Color((float) 0.6, (float)0.8,(float) 1,(float) 0.7));
        }else{
          
            setBackground(new Color((float) 0.7, (float)0.77,(float) 0.8,(float) 0.5));
        }
        
        return this;
    }
}