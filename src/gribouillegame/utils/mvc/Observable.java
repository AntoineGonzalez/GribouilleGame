/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.utils.mvc;

/**
 * Interface permettant de définir un modèle écoutable.
 */
public interface Observable {
    /**
     * Ajoute un écouteur au modèle écoutable.
     * @param obs 
     */
    public void addObserver(Observer obs);
    /**
     * Supprime tous les écouteurs.
     */
    public void removeObserver();
    /**
     * Avertit les écouteurs d'une modification.
     */
    public void notifyModification();
}
