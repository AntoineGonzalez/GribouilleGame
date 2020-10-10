/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.utils.command;

import java.util.ArrayList;

/**
 * Classe permettant de représenté un groupe d'action comme étant une action unique.
 */
public class CompositeAction implements Action {
    // Listes des actions.
    ArrayList<Action> actions;
    
    /**
     * Constructeur.
     */
    public CompositeAction(){
        this.actions = new ArrayList();
    }
    
    /**
     * Ajoute une action au groupe d'action.
     * @param a 
     */
    public void add(Action a){
        this.actions.add(a);
    }
    
    /**
     * Supprime une action du groupe d'action.
     * @param a 
     */
    public void remove(Action a){
        this.actions.remove(a);
    }
    
    /**
     * Vide le groupe d'actions.
     */
    public void removeAll(){
        this.actions.removeAll(actions);
    }
    
    /**
     * Execute le groupe d'action.
     */
    @Override
    public void execute() { 
        for(Action a : actions){
            a.execute();
        }
    }
    /**
     * Annule le groupe d'action.
     */
    @Override
    public void compensate() {
        for(Action a : actions){
            a.compensate();
        }
    }
}
