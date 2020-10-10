/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.utils.command;

import gribouillegame.models.GameModel;
import gribouillegame.models.geometricEntities.Shapes.Shape;

/**
 * Action permettant d'ajouter une forme au modèle de jeu.
 */
public class AddShapeToModel implements Action{
    // Forme que l'on souhaite ajouter.
    private Shape shape;
    // Intance de modèle cible.
    private GameModel gameModel;
    
    /**
     * Constructeur.
     * @param s
     * @param gm 
     */
    public AddShapeToModel(Shape s, GameModel gm){
        this.shape = s;
        this.gameModel = gm;
    }
    
    /**
     * Execute l'action (ajout de la forme).
     */
    @Override
    public void execute() {
        this.gameModel.addShape(this.shape, false);
    }
    
    /**
     * Annule l'action (retire la forme)
     */
    @Override
    public void compensate() {
        this.gameModel.removeShape(this.shape);
    }
    
}
