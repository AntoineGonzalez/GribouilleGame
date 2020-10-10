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
 * Classe permettant de représenter un rectangle.
 * Un rectangle est caractérisé par ses quatres sommets (Points).
 */
public class Rectangle extends Shape{
    // sommet haut gauche
    private Point pointUpLeft;
    // sommet bas droit
    private Point pointDownRight;
    // sommet haut droit
    private Point pointUpRight;
    // sommet bas gauche
    private Point pointDownLeft;
    
    /**
     * Constructeur de classe.
     * @param startDiagonal 
     * @param endDiagonal 
     */
    public Rectangle(Point startDiagonal, Point endDiagonal) {
        super();
        int minX = Math.min(startDiagonal.getX(), endDiagonal.getX());
        int minY = Math.min(startDiagonal.getY(), endDiagonal.getY());
        int maxX = Math.max(startDiagonal.getX(), endDiagonal.getX());
        int maxY = Math.max(startDiagonal.getY(), endDiagonal.getY());
                
        this.pointUpLeft = new Point(minX, minY);
        this.pointDownRight = new Point(maxX, maxY);
        this.pointUpRight = new Point(maxX, minY);
        this.pointDownLeft = new Point(minX, maxY);
        this.color = new Color((float) 0.9,(float) 0.4,(float) 0.4, (float)0.2);
    }

    /**
     * Regarde si le rectangle contient le Point passé en paramètre.
     * @param p
     * @return 
     */
    @Override
    public boolean contain(Point p) {
        int x =(int) p.getX();
        int y = (int) p.getY();
        int x1 =(int) this.getPointUpLeft().getX();
        int y1 =(int) this.getPointUpLeft().getY();
        int x2 =(int) this.getPointDownRight().getX();
        int y2 =(int) this.getPointDownRight().getY();
        int minX = Math.min(x1, x2); 
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);
        
        return (minX< x && x < maxX && minY < y && y < maxY );
    }

    /**
     * Translate le rectangle en fonction des distances passées en paramétre.
     * @param dx
     * @param dy 
     */
    @Override
    public void translate(int dx, int dy) {
        this.getPointUpLeft().translate(dx, dy);
        this.getPointDownRight().translate(dx, dy);
        this.getPointDownLeft().translate(dx, dy);
        this.getPointUpRight().translate(dx, dy);
        this.notifyModification();
    }
    
    @Override
    public Point getCenter(){
        int x = (this.getPointUpLeft().getX() + this.getPointDownRight().getX())/2;
        int y = (this.getPointUpLeft().getY() + this.getPointDownRight().getY())/2;
        return new Point(x, y);
    }

    /**
     * Récupére l'aire du rectangle.
     * @return 
     */
    @Override
    public int getArea() {
        int width = Calcul.distance(this.getPointUpLeft(), this.getPointUpRight());
        int height = Calcul.distance(this.getPointUpLeft(), this.getPointDownLeft());
        return width*height;
    }
    
    /**
     * Renvoi un clone du rectangle.
     * @return 
     */
    @Override
    public Shape getClone() {
        Point pointUpLeft_ = (Point)this.getPointUpLeft().getLocation();
        Point pointDownRight_ = (Point)this.getPointDownRight().getLocation();
        Rectangle clone = new Rectangle(pointUpLeft_, pointDownRight_);
        clone.setName(this.name);
        return clone;
    }
    
    /**
     * Méthode qui définit si les mesures du rectangle sont conformes aux limites fixées par le model de jeu.
     * Concrétement elle permet de savoir si le rectangle sors ou non de l'espace de dessin.
     * 
     * @param model model de jeu.
     * @return true si la forme dépasse false sinon.
     */
    @Override
    public boolean isOverflowing(GameModel model) {
        return  (pointUpLeft.isOverflowing(model) ||
                pointDownLeft.isOverflowing(model) ||
                pointUpRight.isOverflowing(model) ||
                pointDownRight.isOverflowing(model));
    }

   
    public int getHeight(){
        return Calcul.distance(getPointUpLeft(), getPointDownLeft());
    }
    
    public int getWidth(){
        return Calcul.distance(getPointUpLeft(), getPointUpRight());
    }

    /**
     * @return the pointUpLeft
     */
    public Point getPointUpLeft() {
        return pointUpLeft;
    }

    /**
     * @return the pointDownRight
     */
    public Point getPointDownRight() {
        return pointDownRight;
    }

    /**
     * @return the pointUpRight
     */
    public Point getPointUpRight() {
        return pointUpRight;
    }

    /**
     * @return the pointDownLeft
     */
    public Point getPointDownLeft() {
        return pointDownLeft;
    }    

    /**
     * @param pointUpLeft the pointUpLeft to set
     */
    public void setPointUpLeft(Point pointUpLeft) {
        this.pointUpLeft = pointUpLeft;
    }

    /**
     * @param pointDownRight the pointDownRight to set
     */
    public void setPointDownRight(Point pointDownRight) {
        this.pointDownRight = pointDownRight;
    }

    /**
     * @param pointUpRight the pointUpRight to set
     */
    public void setPointUpRight(Point pointUpRight) {
        this.pointUpRight = pointUpRight;
    }

    /**
     * @param pointDownLeft the pointDownLeft to set
     */
    public void setPointDownLeft(Point pointDownLeft) {
        this.pointDownLeft = pointDownLeft;
    }

    /**
     * Renvoi une copie du rectangle modifier en fonction du point passé en paramètre. 
     * Le Point passé en paramètre remplace l'un des somméts du rectangle en fonction de son positionnement avec le sommet haut gauche. 
     * @param p
     * @return 
     */
    @Override
    public Shape getResized(Point p) {
        Rectangle r = (Rectangle) this.getClone();
        int deltaX = p.getX() - this.pointUpLeft.getX();
        int deltaY = p.getY() - this.pointUpLeft.getY();
        
        if(deltaX > 0 && deltaY > 0) {
            r.setPointDownRight(p);
            r.setPointDownLeft(new Point(r.getPointDownLeft().getX(), p.getY()));
            r.setPointUpRight(new Point(p.getX(), r.getPointUpRight().getY()));
        } else if(deltaX < 0 && deltaY < 0) {
            r.setPointUpRight(new Point(r.getPointUpLeft().getX(), p.getY()));
            r.setPointDownLeft(new Point(p.getX(), r.getPointUpLeft().getY()));
            r.setPointDownRight(r.getPointUpLeft());
            r.setPointUpLeft(p);
        } else if(deltaX > 0 && deltaY < 0) {
            r.setPointDownLeft(r.getPointUpLeft());
            r.setPointDownRight(new Point(p.getX(), r.getPointUpLeft().getY()));
            r.setPointUpLeft(new Point(r.getPointUpLeft().getX(), p.getY()));            
            r.setPointUpRight(p);
        } else if(deltaX < 0 && deltaY > 0) {
            r.setPointUpRight(r.getPointUpLeft());
            r.setPointDownRight(new Point(r.getPointUpLeft().getX(), p.getY()));
            r.setPointUpLeft(new Point(p.getX(), r.getPointUpLeft().getY()));            
            r.setPointDownLeft(p);
        }           
        return r;
    }
}
