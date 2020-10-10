/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.utils.command;
import gribouillegame.models.GameModel;
import gribouillegame.models.geometricEntities.Shapes.Shape;

/**
 * Action permettant de supprimer une forme du modèle de jeu.
 */
public class RemoveShapeToModel implements Action{
    // Forme que l'on souhaite ajouter.
    private Shape shape;
    // Intance de modèle cible.
    private GameModel gameModel;
    
    /**
     * Constructeur.
     * @param s
     * @param gm 
     */
    public RemoveShapeToModel(Shape s, GameModel gm){
        this.shape = s;
        this.gameModel = gm;        
    }
    /**
     * Execute l'action (retire la forme du modèle).
     */
    @Override
    public void execute() {
        this.gameModel.removeShape(shape);
    }
    /**
     * Annule l'action (rajoute la forme au modèle).
     */
    @Override
    public void compensate() {
        this.gameModel.addShape(shape, false);
    }
    
}
