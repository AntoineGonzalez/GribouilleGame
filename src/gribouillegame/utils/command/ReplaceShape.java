/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.utils.command;

import gribouillegame.models.GameModel;
import gribouillegame.models.geometricEntities.Shapes.Shape;

/**
 * Action permettant de remplacer une instance de forme du modèle de jeu par une autres instance de forme. 
 */
public class ReplaceShape implements Action {
    // Nouvelle forme (forme ajoutée).
    private Shape newS;
    // Ancienne forme (forme retirée).
    private Shape oldS;
    // Modèle dans lequel la permutation à lieu (contexte).
    private GameModel gm;
    
    /**
     * Constructeur 
     * @param newS
     * @param oldS
     * @param gm 
     */
    public ReplaceShape(Shape newS, Shape oldS, GameModel gm){
        this.newS = newS;
        this.oldS = oldS;
        this.gm = gm;
    }
    
    /**
     * Echange l'ancienne instance avec la nouvelle.
     */
    @Override
    public void execute() {
        this.gm.removeShape(oldS);
        this.gm.addShape(newS, true);
    }

    /**
     * Echange la nouvelle instance avec l'ancienne.
     */
    @Override
    public void compensate() {
        this.gm.removeShape(newS);
        this.gm.addShape(oldS, true);
    }
}
