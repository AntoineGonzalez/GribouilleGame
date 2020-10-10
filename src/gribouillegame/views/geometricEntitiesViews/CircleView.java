/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.views.geometricEntitiesViews;

import gribouillegame.models.geometricEntities.GeometricEntity;
import gribouillegame.models.geometricEntities.Shapes.Circle;
import gribouillegame.models.geometricEntities.Point;
import gribouillegame.models.geometricEntities.Shapes.Shape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * Classe permettant de représenter la vue graphique associée à un cercle.
 */
public class CircleView extends View{
    /**
     * Le cercle associé.
     */
    Circle circle;
    
    /**
     * Constructeur.
     * @param c Le cercle associé.
     */
    public CircleView(Circle c){
        super(c);
        this.circle = c;
        if(!this.circle.getListObserver().contains(this)){
            this.circle.addObserver(this); 
        }
    }

    /**
     * constructeur.
     */
    public CircleView(){
    }
    
    /**
     * Méthode permettant de peindre le cercle.
     * @param g 
     */
    @Override
    public void paint(Graphics2D g) {       
        Color defaultColor = g.getColor();
        Stroke defaultSroke = g.getStroke();
        Color fontColor = defaultColor;
        Color backgroundColor = defaultColor;
        Color borderColor = defaultColor;
        Stroke borderSroke = defaultSroke;
        
        if(this.overflow){
            fontColor = Color.RED;
            backgroundColor = new Color((float) 0.6,(float) 0.6,(float) 0.6, (float)0.2);
        }else{
            backgroundColor = this.circle.getColor();
        }
        
        if(this.bold){
            borderSroke = new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
        }
        
        g.setColor(backgroundColor);
        this.fillCircle(g, this.circle);
        
        g.setColor(borderColor);
        g.setStroke(borderSroke);
        this.drawCircle(g, this.circle);
        
        g.setColor(fontColor);
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        String text = this.circle.getName();
        int dx = metrics.stringWidth(text)/2;
        Point center = this.circle.getCenter();
        g.drawString(this.circle.getName(), center.getX()-dx, center.getY());
        
        g.setColor(defaultColor);
        g.setStroke(defaultSroke);
    }
    
    /**
     * Méthode qui permet de dessiner un cercle (contours).
     * @param g
     * @param circle 
     */
    public void drawCircle(Graphics2D g, Circle circle) {
        Point c = circle.getCenter();
        int r = circle.getRay();
        int x = c.getX();
        int y = c.getY();
        g.drawOval(x-r,y-r,r*2,r*2);
    }

    /**
     * Méthode qui permet de dessiner un cercle (contours).
     * @param g
     * @param circle 
     */
    public void fillCircle(Graphics g, Circle circle) {
        Point c = circle.getCenter();
        int r = circle.getRay();
        int x = c.getX();
        int y = c.getY();
        g.fillOval(x-r,y-r,r*2,r*2);
    }
    
    /**
     * Méthode de mise à jour.
     * @param observable 
     */
    @Override
    public void update(Object observable) {
        this.circle = (Circle) observable;
        this.notifyModification();
    }
    
    @Override
    public Shape getGeometricEntity() {
        return this.circle;
    }

    
    @Override
    public void setGeometricEntity(GeometricEntity geometricEntity) {
        this.circle = (Circle) geometricEntity;    
        if(!this.circle.getListObserver().contains(this)){
            this.circle.addObserver(this); 
        }
    }
}    
