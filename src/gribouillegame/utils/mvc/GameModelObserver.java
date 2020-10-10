/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.utils.mvc;

import gribouillegame.models.GameModel;
import gribouillegame.models.geometricEntities.GeometricEntity;

/**
 * Interface permettant de définir un écouteur de modèle à listes.
 */
public interface GameModelObserver{
    /**
     * Méthode de mise à jour quand un élémént ajouté.
     * @param geometricEntity
     * @param gm 
     */
    public abstract void updateElementAdded(GeometricEntity geometricEntity, GameModel gm);
    
    /**
     * Méthode de mise à jour quand un élémént supprimé.
     * @param geometricEntity
     * @param gm 
     */
    public abstract void updateElementRemoved(GeometricEntity geometricEntity, GameModel gm);
    
    /**
     * Méthode de mise à jour quand le modèle est remis à zéros.
     */
    public abstract void updateReset();
}
