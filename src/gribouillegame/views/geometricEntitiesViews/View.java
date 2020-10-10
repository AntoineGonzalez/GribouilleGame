/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.views.geometricEntitiesViews;
import gribouillegame.models.geometricEntities.GeometricEntity;
import gribouillegame.utils.mvc.Observable;
import gribouillegame.utils.mvc.Observer;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Classe abstraite représentant les vues associées aux entitées géométrique du modèle.
 */
public abstract class View implements Observer, Observable{
    /**
     * Boolean qui vaut true si la vue sors du canvas.
     */
    protected boolean overflow;
    /**
     * Boolean qui vaut true si la vue doit être mis en surlignage.
     */
    protected boolean bold;
    /**
     * L'entité géométrique représentée.
     */
    protected GeometricEntity geometricEntity;
    /**
     * Liste d'écouteurs.
     */
    protected ArrayList<Observer> listObserver;
    
    /**
     * Constructeur de classe.
     * @param ge L'entité géométrique a représentée.
     */
    public View(GeometricEntity ge){
        this.listObserver = new ArrayList();
        this.geometricEntity = ge;
        this.overflow=false;
        this.bold = false;
    }
    /**
     * Constructeur de classe.
     */
    public View() {
        this.listObserver = new ArrayList();
        this.overflow=false;
        this.bold = false;
    }
    /**
     * Méthode qui permet de peindre la vue.
     * @param g 
     */
    public abstract void paint(Graphics2D g);
    public abstract GeometricEntity getGeometricEntity();
    public abstract void  setGeometricEntity(GeometricEntity geometricEntity);

    /**
     * 
     * @return 
     */
    public boolean getOverflow() {
        return this.overflow;
    }

    /**
     * 
     * @param isOverflowing 
     */
    public void setOverflow(boolean isOverflowing) {
        this.overflow = isOverflowing;
    }

    /**
     * 
     * @return 
     */
    public boolean getBold() {
        return this.bold;
    }

    /**
     * 
     * @param isBold 
     */
    public void setBold(boolean isBold) {
        this.bold = isBold;
    }
    
    /**
     * Ajout d'un écouteur.
     * @param obs 
     */
    @Override
    public void addObserver(Observer obs) {
        this.listObserver.add(obs);
    }

    /**
     * Supression des écouteurs.
     */
    @Override
    public void removeObserver() {
        this.listObserver.removeAll(listObserver);
    }

    /**
     * Notifie les écouteurs d'un changement.
     */
    @Override
    public void notifyModification() {
        for(Observer o : this.listObserver){
            o.update(this);
        }
    }
}
