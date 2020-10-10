/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.models.geometricEntities;

import gribouillegame.models.GameModel;
import gribouillegame.utils.mvc.Observable;
import gribouillegame.utils.mvc.Observer;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Classe abstraites permattant de représenter une entité géométrique.
 */
public abstract class GeometricEntity implements Observable{
    // liste des entités qui vont écouté l'entité géométrique.
    private ArrayList<Observer> listObserver;
    // couleur de l'entité.
    protected Color color;
    
    // Constructeur
    public GeometricEntity(){
         this.listObserver = new ArrayList();
    }
    
    /**
     * Méthode qui définit si les mesures de l'entité sont conformes aux limites fixées par le model de jeu.
     * Concrétement elle permet de savoir si l'entité sors ou non de l'espace de dessin.
     * 
     * @param model model de jeu.
     * @return true si l'entité dépasse false sinon.
     */
    public abstract boolean isOverflowing(GameModel model);
    /**
     * Translate l'entité en fonction des distances passées en paramétre.
     * @param dx
     * @param dy 
     */
    public abstract void translate(int dx, int dy);
    
  
  
    @Override
    public void addObserver(Observer obs) {
        this.getListObserver().add(obs);
    }

    @Override
    public void removeObserver() {
        this.setListObserver((ArrayList<Observer>) new ArrayList());
    }

    @Override
    public void notifyModification() {
        for(Observer o : this.getListObserver()){
            o.update(this);
        }
    }
     /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the listObserver
     */
    public ArrayList<Observer> getListObserver() {
        return listObserver;
    }

    /**
     * @param listObserver the listObserver to set
     */
    public void setListObserver(ArrayList<Observer> listObserver) {
        this.listObserver = listObserver;
    }
}
