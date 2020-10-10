/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.views.geometricEntitiesViews;

import gribouillegame.models.geometricEntities.GeometricEntity;
import gribouillegame.models.geometricEntities.Point;
import gribouillegame.models.geometricEntities.Shapes.Rectangle;
import gribouillegame.models.geometricEntities.Shapes.Shape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * Classe permettant de représenter la vue graphique associée à un rectangle.
 */
public class RectangleView extends View {
    /**
     * Le rectangle associé.
     */
    Rectangle rectangle;
    
    
    /**
     * Constructeur.
     * @param r Le rectangle associé.
     */
    public RectangleView(Rectangle r){
        super(r);
        this.rectangle = r;
        if(!this.rectangle.getListObserver().contains(this)){
            this.rectangle.addObserver(this); 
        }
    }
    /**
     * constructeur.
     */
    public RectangleView(){
    }
    
    /**
     * Méthode permettant de peindre le rectangle.
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
            backgroundColor = this.rectangle.getColor();
        }
        
        if(this.bold){
            borderSroke = new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
        }
        
        g.setColor(backgroundColor);
        this.fillRectangle(g, this.rectangle);
        
        g.setColor(borderColor);
        g.setStroke(borderSroke);
        this.drawRectangle(g, this.rectangle);
        
        g.setColor(fontColor);
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        String text = this.rectangle.getName();
        int dx = metrics.stringWidth(text)/2;
        Point center = this.rectangle.getCenter();
        g.drawString(this.rectangle.getName(), center.getX()-dx, center.getY());
        
        g.setColor(defaultColor);
        g.setStroke(defaultSroke);
    }

    /**
     * Méthode qui permet de dessiner un rectangle (contours).
     * @param g
     * @param rectangle 
     */
    public void drawRectangle(Graphics2D g, Rectangle rectangle) {
        Point upLeftPoint = rectangle.getPointUpLeft();
        g.drawRect(upLeftPoint.getX(), upLeftPoint.getY(), rectangle.getWidth() , rectangle.getHeight());
    }
    
    /**
     * Méthode qui permet de dessiner un rectangle (plein).
     * @param g
     * @param rectangle 
     */
    public void fillRectangle(Graphics2D g, Rectangle rectangle) {
        Point upLeftPoint = rectangle.getPointUpLeft();
        g.fillRect(upLeftPoint.getX(), upLeftPoint.getY(), rectangle.getWidth() , rectangle.getHeight());
    }
    
    /**
     * Méthode de mise à jour.
     * @param observable 
     */
    @Override
    public void update(Object observable) {
        this.rectangle = (Rectangle) observable;
        this.notifyModification();
    }

    @Override
    public void setGeometricEntity(GeometricEntity geometricEntity) {
        this.rectangle = (Rectangle) geometricEntity;
        if(!this.rectangle.getListObserver().contains(this)){
            this.rectangle.addObserver(this); 
        } 
    }
    
    @Override
    public Shape getGeometricEntity() {
        return this.rectangle;
    }
}
