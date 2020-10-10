/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.controllers;

import gribouillegame.models.geometricEntities.Point;
import gribouillegame.models.geometricEntities.Shapes.Shape;
import gribouillegame.utils.command.Action;
import gribouillegame.utils.command.ReplaceShape;
import gribouillegame.views.Canvas;
import gribouillegame.views.GUIGame;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Classe représentant le MouseListener utilisé en mode "Retouche/resize de formes".
 * Il définit la manière dont les intéractions sur la souris doivent être interpréter lorsque l'on retouche/resize une forme.
 */
public class ResizeController extends Controller{
    /**
     * Forme du model que l'on souhaite retoucher.
     */
    private Shape selectedShape;
    /**
     * Copie de la forme que l'on souhaite retoucher ( forme que l'on va modifier et qui remplacera la forme original dans le model ).
     */
    private Shape temporaryShape;
    /**
     * Position initial de la souris lors du mouseClick.
     */
    private Point initialCursorPosition;
    /**
     * Position de la souris courant lors des autres événement.
     */
    private Point currentCursorPosition;
    /**
     * Booleen qui détermine si une retouche est en cours.
     */
    private boolean modificationInProgress;
    
    /**
     * Constructeur de la classe.
     * Récupére le nombre de forme maximal depuis le model.
     * @param canvas Référence sur l'espace de dessin.
     */
    public ResizeController(Canvas canvas) {
        super(canvas);
        this.modificationInProgress = false;
    }

    /**
     * MouseClicked Listener
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // premier clique lorsque l'on n'est pas déjà en train de modifier une forme.
        if(!this.modificationInProgress){
            this.initialCursorPosition = new Point(e.getX(),e.getY());
            //On parcours chaque forme pour savoir si on en a sélectionné une.
            this.selectedShape = this.gameModel.getSelectedShape(this.initialCursorPosition);
            // Si il y en a une de selectionné.
            if(this.selectedShape != null){
                Cursor cursor = new Cursor(Cursor.SE_RESIZE_CURSOR);
                this.canvas.setCursor(cursor);
                this.modificationInProgress =true;
                //On récupére la copie de la forme ( Cette copie a subit les retouches ).
                this.temporaryShape = this.selectedShape.getResized(this.initialCursorPosition);
                //Class Loader qui créer l'instance de View correspondant à la classe de notre forme temporaire.
                this.temporaryView = this.getView(this.temporaryShape);
            }
        // deuxieme clique lorsque l'on est déjà en train de modifier une forme.
        } else {
            Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
            this.canvas.setCursor(cursor);
            // si la forme modifier ne dépasse pas du canvas 
            if(!this.temporaryShape.isOverflowing(this.gameModel)){
                // On créer une action ReplaceShape qui permutera la forme sélectionner par sa copie retouché au sein du model.
                Action a = new ReplaceShape(this.temporaryShape, this.selectedShape, this.gameModel);
                this.gameModel.getActionHandler().handle(a);
                this.canvas.getGui().getRedo().setEnabled(false);
            }
            this.modificationInProgress = false;
            this.temporaryShape = null;
            this.temporaryView = null;
        }
        this.canvas.repaint();
    }

    /**
     * MouseMouved Listener
     * @param e 
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        this.currentCursorPosition = new Point(e.getX(),e.getY());
        // Si une forme est sélectionnée pour être retouchée.
        if(this.modificationInProgress){
            //On récupére la copie de la forme (Cette copie a subit les retouches).
            this.temporaryShape = this.selectedShape.getResized(this.currentCursorPosition);
            //Class Loader qui créer l'instance de View correspondant à la classe de notre forme temporaire.
            boolean overflow = this.temporaryShape.isOverflowing(this.gameModel);
            this.temporaryView = this.getView(this.temporaryShape);
            this.temporaryView.setOverflow(overflow);
        }else{
            this.selectedShape = this.gameModel.getSelectedShape(this.currentCursorPosition);
            if(this.selectedShape!=null){
                // Utilisation d'un classe Loader
                this.temporaryView =this.getView(this.selectedShape);
                // on met en surbrillance les formes si on passe dessus avec la souris.
                this.temporaryView.setBold(true);
            }else{
                this.temporaryView = null;
            }
        }
        this.canvas.repaint();
    }
    
    /**
     * Méthode permettant de peindre les vues intermédiaires générées lors de la retouche d'une forme.
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
