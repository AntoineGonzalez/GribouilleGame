/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.models.geometricEntities;
import gribouillegame.models.GameModel;
import java.awt.Color;

/**
 * Classe représentant un point.
 */
public class Point extends GeometricEntity{
    private int x;
    private int y;
    
    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.color=new Color(1, 2, 92);    
    }
    
    /**
     * Translate le point en fonction des distances passées en paramétre.
     * @param dx
     * @param dy 
     */
    @Override
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Renvoie une nouvelle instance de point possédant les mêmes coordonnées.
     * @return 
     */
    public Point getLocation(){
        return new Point(this.x, this.y);
    }
    
    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Méthode qui définit si les coordonnées du point sont conformes aux limites fixées par le model de jeu.
     * Concrétement elle permet de savoir si l'entité sors ou non de l'espace de dessin.
     * 
     * @param model model de jeu.
     * @return true si le point dépasse false sinon.
     */
    @Override
    public boolean isOverflowing(GameModel model) {
        return (this.x>model.getWidth() ||
                this.x<0 ||
                this.y<0 ||
                this.y>model.getHeight());
    }    
}
