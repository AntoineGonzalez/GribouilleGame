/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.views.geometricEntitiesViews;

import gribouillegame.models.geometricEntities.GeometricEntity;
import gribouillegame.models.geometricEntities.Point;
import gribouillegame.utils.mvc.Observer;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Classe permettant de représenter la vue graphique associée à un point.
 */
public class PointView extends View implements Observer{
    /**
     * Le Point associé.
     */
    Point point;
    /**
     * Constructeur.
     * @param p Le Point associé.
     */
    public PointView(Point p){ 
        super(p);
        this.point = p;
    }
    /**
     * Constructeur.
     */
    public PointView(){
    }

    /**
     * Méthode de mise à jour.
     * @param observable 
     */
    @Override
    public void update(Object observable) {
        this.point = (Point) observable;
    }
    /**
     * Méthode permettant de peindre le point.
     * @param g 
     */
    @Override
    public void paint(Graphics2D g) {
        Color defaultColor = g.getColor();
        int x = this.point.getX(); 
        int y = this.point.getY();
        g.setColor(this.point.getColor());
        g.drawLine(x-5, y, x+5, y);
        g.drawLine(x, y-5, x, y+5);
        g.setColor(defaultColor);
    }

    @Override
    public GeometricEntity getGeometricEntity() {
        return this.point;
    }

    @Override
    public void  setGeometricEntity(GeometricEntity geometricEntity) {
        this.point = (Point) geometricEntity;
        this.point.addObserver(this);
    }
}
