/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.controllers;

import gribouillegame.models.geometricEntities.Point;
import gribouillegame.models.geometricEntities.Shapes.Rectangle;
import gribouillegame.utils.command.Action;
import gribouillegame.utils.command.AddShapeToModel;
import gribouillegame.views.Canvas;
import gribouillegame.views.geometricEntitiesViews.RectangleView;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

import java.awt.event.MouseEvent;

/**
 * Classe représentant le MouseListener utilisé en mode "création de rectangle".
 * Il définit la manière dont les intéractions sur la souris doivent être interprétes lorsque l'on créé un rectangle.
 */
public class RectangleController extends Controller{
    
    /**
     * Position initial de la souris lors du mousePressed.
     */
    private Point initialCursorPosition;
    /**
     * Position de la souris courant lors des autres événement.
     */
    private Point currentCursorPosition;
    /**
     * Booleen qui détermine si une création de rectangle est en cours.
     */
    private Boolean creationInProgress;
    /**
     * Référence sur le rectangle en cour de création.
     */
    private Rectangle rectangle;
    /**
     * Référence qui garde en mémoire l'état du rectangle lors du mouseDragged précédant.
     */
    private Rectangle previous;
    /**
     * Le nombre de forme maximal que l'on peut déssiner.
     */
    private final int maxNbShape;        
    
    /**
     * Constructeur de la classe.
     * Récupére le nombre de forme maximal depuis le model.
     * @param canvas Référence sur l'espace de dessin.
     */
    public RectangleController(Canvas canvas){
        super(canvas);
        this.creationInProgress = false;
        this.maxNbShape = this.gameModel.getNbMaxShape();
    }
    
    /**
     * MousePressed Listener.
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(this.maxNbShape > this.gameModel.getCurrentNbShape()){
            this.initialCursorPosition = new Point(e.getX(),e.getY());
            this.creationInProgress = true;
        }        
    }

    /**
     * MouseReleased Listener.
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if(this.creationInProgress == true && this.rectangle !=null){
            this.creationInProgress = false;
            //création et ajout de l'action, permettant d'ajouter le rectangle au model.
            Action a = new AddShapeToModel(this.rectangle,this.gameModel);
            this.gameModel.getActionHandler().handle(a);
            this.canvas.getGui().getUndo().setEnabled(true);
            this.canvas.getGui().getRedo().setEnabled(false);
            this.temporaryView = null;
            this.rectangle = null;
        }
    }
    
    /**
     * MouseDragged Listener.
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        this.currentCursorPosition = new Point(e.getX(),e.getY());
        if(this.creationInProgress){
            this.rectangle = new Rectangle(this.currentCursorPosition, this.initialCursorPosition);
            // si le rectangle dépasse du canvas on revient à l'état d'avant.
            if(this.rectangle.isOverflowing(this.gameModel)){
                this.rectangle = this.previous;
            }else{
                this.previous = this.rectangle;
            }
            // on créer une vue temporaire.
            this.temporaryView = new RectangleView(rectangle);
            // on peint
            this.canvas.repaint();
        }
    }

    /**
     * Méthode permettant de peindre les vues intermédiaires générées lors de la création d'un rectangle.
     * @param g 
     */
    @Override
    public void paint(Graphics2D g) {
        if(temporaryView != null){
            float dash1[] = {10.0f};
            Stroke stroke = new BasicStroke(1.5f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,
            10.0f, dash1, 0.0f);
            g.setStroke(stroke);
            temporaryView.paint(g);
        }
    }
}
