/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.utils.mvc;


/**
 * Interface permettant de définir un écouteur.
 */
public interface Observer {
    /**
     * Méthode de mise à jour.
     * @param observable 
     */
    public void update(Object observable);
}