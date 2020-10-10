/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.models.geometricEntities.Shapes;

import gribouillegame.models.geometricEntities.GeometricEntity;
import gribouillegame.models.geometricEntities.Point;
import gribouillegame.utils.mvc.Observable;
import gribouillegame.utils.mvc.Observer;
import java.util.ArrayList;


/**
 *Classe abstraite représentant une forme géométrique.
 */
public abstract class Shape extends GeometricEntity implements Observable {
    // listes des entités qui vont écouter la forme.
    protected ArrayList<Observer> listObserver;
    // nom de la forme
    protected String name = "";
    
    // constructeur
    public Shape(){
        this.listObserver = new ArrayList();
    }
    /**
     * Méthode qui détermine si une forme contient le point passé en parametre.
     * @param p
     * @return 
     */
    public abstract boolean contain(Point p);
    /**
     * Méthode qui calcul l'aire occupé par la forme.
     * @return 
     */
    public abstract int getArea();
    /**
     * Méthode qui renvoie le centre de la forme sous forme de Point.
     * @return 
     */
    public abstract Point getCenter();
    /**
     * Méthode qui renvoie une copie modifié de la forme ( la modification se fait par rapport à un point ). 
     * @param p
     * @return 
     */
    public abstract Shape getResized(Point p);
    /**
     * Méthode qui renvoie une copie de la forme.
     * @return 
     */
    public abstract Shape getClone();


    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Nomme la forme et lui attribut le numéro n.
     * @param n
    */
    public void setName(int n) {
        this.name = this.getClass().getSimpleName()+ " " + n;
    }
}
