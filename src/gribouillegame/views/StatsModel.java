/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.views;
import gribouillegame.models.geometricEntities.Shapes.Shape;
import gribouillegame.models.GameModel;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;

/**
 * Table modèle permettant de représenter les données présentes dans notre tableaux de stats/scores.
 */
public class StatsModel extends AbstractTableModel {
    /**
     * Noms des colonnes.
     */
    private final String[] headers = {"Shapes Name", "Area(in percent)"};
    /**
     * Instance du modèle de jeu dans lequel nous allons lire les données à afficher.
     */
    private GameModel gameModel;
    /**
     * Listes des formes à afficher.
     */
    private ArrayList<Shape> shapes;
    /**
     * Map contenant les aires occupées par chaque forme.
     */
    private HashMap<Shape, Double> areas;

    /**
     * Constructeur.
     * @param gameModel Instance du modèle de jeu dans lequel nous allons lire les données à afficher.
     */
    public StatsModel(GameModel gameModel) {
        super();
        this.gameModel = gameModel;
        this.shapes = this.gameModel.getShapes();
        this.areas = this.gameModel.getAreas();
    }
    
    
    @Override
    public int getRowCount() {
        return this.gameModel.getNbMaxShape()+1;
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                if(rowIndex<this.shapes.size()){
                    return this.shapes.get(rowIndex).getName();
                }
                if(rowIndex == this.gameModel.getNbMaxShape()){
                    return "Score Total:";
                }
            case 1:
                if(rowIndex<this.shapes.size()){
                    return this.areas.get(this.shapes.get(rowIndex));
                }
                if(rowIndex == this.gameModel.getNbMaxShape()){
                    return this.gameModel.getScore();
                }
        }
        return null;
    }
    
    @Override
    public Class getColumnClass(int columnIndex){
        switch(columnIndex){
            default : 
                return Object.class;
        }
    }
}
