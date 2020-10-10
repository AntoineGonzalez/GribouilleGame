/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.controllers;

import gribouillegame.models.GameModel;
import gribouillegame.models.geometricEntities.Shapes.Shape;
import gribouillegame.utils.ViewLoader;
import gribouillegame.views.Canvas;
import gribouillegame.views.geometricEntitiesViews.View;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;

/**
 * Classe abstraite représentant les divers modes de dessins (les différentes manières d'interpréter les intéractions utilisateurs dans le canvas.)
 */
public abstract class Controller extends MouseAdapter{
    /**
     * Référence sur l'espace de dessin.
     */
    protected Canvas canvas;
    /**
     * Référence sur le model de jeu.
     */
    protected GameModel gameModel;
    /**
     * Vue temporaire permettant au controller de peindre les formes non définitives crées lors des actions souris.
     */
    protected View temporaryView;
        
    /**
     * Constructeur de classe
     * @param canvas Référence sur l'espace de dessin.
     */
    public Controller(Canvas canvas){
        this.canvas = canvas;
        this.gameModel = this.canvas.getModel();
    }
        
    /**
     * Méthode permettant de peindre les vues intermédiaires générées lors des actions souris.
     * @param g 
     */
    public abstract void paint(Graphics2D g);
    
    /**
     * Méthode permettant de générer une vue représentant la forme passée en argument.
     * @param s
     * @return 
     */
    public View getView(Shape s){
        View res = null;
        try {
                //Utilisation d'un classe Loader pour savoir qu'elle type de vue générer en fonction de la forme passé en argument
                res = ViewLoader.getInstance().loadView(s);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                System.err.println(ex);
            } 
        return res;
    }
}
