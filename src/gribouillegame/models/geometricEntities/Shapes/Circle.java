/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.models.geometricEntities.Shapes;
import gribouillegame.models.GameModel;
import gribouillegame.models.geometricEntities.Point;
import gribouillegame.utils.Calcul;
import java.awt.Color;


/**
 * Classe permettant de représenter un cercle.
 * Un cercle est caractérisé par deux attributs, sont centre (Point) et son rayon (entier).
 */
public class Circle extends Shape{
    // centre du cercle
    private Point center;
    // rayon du cercle
    private int ray;

    // constructeur de classe
    public Circle(Point c, int r){
       super(); 
       this.center=c;
       this.ray=r;
       this.color = new Color((float) 0.4,(float) 0.9,(float) 0.4, (float)0.2);
    }
    
    /**
     * Méthode qui définit si les mesures du cercle sont conformes aux limites fixées par le model de jeu.
     * Concrétement elle permet de savoir si le cercle sors ou non de l'espace de dessin.
     * 
     * @param model model de jeu.
     * @return true si la forme dépasse false sinon.
     */
    @Override
    public boolean isOverflowing(GameModel model){
        Point center = this.center;
        int ray = this.ray;
        Point top = new Point(center.getX(),0);
        Point bottom = new Point(center.getX(),model.getHeight());
        Point right = new Point(model.getWidth(),center.getY());
        Point left =  new Point(0,center.getY());
        int topDist = Calcul.distance(top, center);
        int bottomDist = Calcul.distance(bottom, center);
        int rightDist = Calcul.distance(right, center);
        int leftDist = Calcul.distance(left, center);
        return (topDist<ray || bottomDist<ray || rightDist<ray || leftDist<ray);
    }
    
    /**
     * Récupére l'aire du cercle.
     * @return 
     */
    @Override
    public int getArea() {
        return (int) (Math.pow(this.ray, 2)*Math.PI);
    }
    
    /**
     * Regarde si le cercle contient le point passé en paramètre.
     * @param p
     * @return 
     */
    @Override
    public boolean contain(Point p){
        int distance = Calcul.distance(p, this.center);
        return this.ray > distance;
    }
    
    /**
     * Translate le cercle en fonction des distances passées en paramétre.
     * @param dx
     * @param dy 
     */
    @Override
    public void translate(int dx , int dy){
        this.center.translate(dx, dy);
        this.notifyModification();
    }
    
    @Override
    public Point getCenter() {
        return center;
    }

    /**
     * Renvoi un clone du cercle.
     * @return 
     */
    @Override
    public Shape getClone() {
        Point center = (Point)this.center.getLocation();
        Circle clone = new Circle(center, this.ray);
        clone.setName(this.name);
        return clone;
    }
    
    /**
     * @param c the c to set
     */
    public void setCenter(Point c) {
        this.center = c;
    }

    /**
     * @return the r
     */
    public int getRay() {
        return ray;
    }

    /**
     * @param r the r to set
     */
    public void setRay(int r) {
        this.ray = r;
    }

    /**
     * Renvoi une copie du cercle modifier en fonction du point passé en paramètre. 
     * La droite entre le rayon et ce point définit le nouveau rayon. 
     * @param p
     * @return 
     */
    @Override
    public Shape getResized(Point p) {
        Circle c = (Circle) this.getClone();
        int r = Calcul.distance(this.getCenter(), p);
        c.setRay(r);
        return c;
    }
}
