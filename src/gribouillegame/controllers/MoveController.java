/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.controllers;

import gribouillegame.models.geometricEntities.Point;
import gribouillegame.models.geometricEntities.Shapes.Shape;
import gribouillegame.utils.command.Action;
import gribouillegame.utils.command.TranslateShape;
import gribouillegame.views.Canvas;
import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

/**
 * Classe représentant le MouseListener utilisé en mode "Déplacement de formes".
 * Il définit la manière dont les intéractions sur la souris doivent être interpréter lorsque l'on déplace une forme.
 */
public class MoveController extends Controller{
    /**
     * La forme que l'on souhaite déplacer.
     */
    private Shape selectedShape;
    /**
     * La forme temporairement créer pendant le déplacement.
     */
    private Shape temporaryShape;
    /**
     * Position initial de la souris lors du mousePressed.
     */
    private Point initialCursorPosition;
    /**
     * Position de la souris courant lors des autres événement.
     */
    private Point currentCursorPosition;
    /**
     * Constructeur de la classe.
     * @param canvas Référence sur l'espace de dessin.
     */
    public MoveController(Canvas canvas){
       super(canvas);
    }

    /**
     * MousePressed Listener.
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e){
        this.initialCursorPosition = new Point(e.getX(),e.getY());
        // on récupère la forme que l'on souhaite déplacer (null  si on presse la souris sur aucune forme).
        this.selectedShape = this.gameModel.getSelectedShape(this.initialCursorPosition);
        Cursor cursor = new Cursor(Cursor.MOVE_CURSOR);
        this.canvas.setCursor(cursor);
    }

    /**
     * MouseReleased Listener.
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e){
        this.currentCursorPosition = new Point(e.getX(),e.getY());
        // calcul des distances entre nouvelles et anciennes coordonnées.
        int dx = this.currentCursorPosition.getX() - this.initialCursorPosition.getX();
        int dy = this.currentCursorPosition.getY() - this.initialCursorPosition.getY();
        if( this.selectedShape != null && !this.temporaryShape.isOverflowing(gameModel) ){
            // création et ajout d'une action premettant de translater la forme.
            Action a = new TranslateShape(selectedShape,dx,dy);
            this.gameModel.getActionHandler().handle(a);
            this.canvas.getGui().getRedo().setEnabled(false);
            this.selectedShape = null;
        }
        this.temporaryView = null;
        Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
        this.canvas.setCursor(cursor);
        //this.canvas.repaint();
    }
    
    /**
     * MouseDragged Listener.
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if(this.selectedShape != null){
            // on récupére une copie de la forme sélectionnée.
            this.temporaryShape = this.selectedShape.getClone();
            // calcul des distances entre nouvelles et anciennes coordonnées.
            this.currentCursorPosition = new Point(e.getX(),e.getY());
            int dx = this.currentCursorPosition.getX() - this.initialCursorPosition.getX();
            int dy = this.currentCursorPosition.getY() - this.initialCursorPosition.getY();
            // On translate la copie.
            this.temporaryShape.translate(dx, dy);           
            boolean overflow = this.temporaryShape.isOverflowing(this.gameModel);
            // on lui créer une vue temporaire.
            this.temporaryView =this.getView(this.temporaryShape);
            this.temporaryView.setOverflow(overflow);
            // on peint
            this.canvas.repaint();
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e){
        this.currentCursorPosition = new Point(e.getX(), e.getY());
        this.selectedShape = this.gameModel.getSelectedShape(this.currentCursorPosition);
            if(selectedShape!=null){
                // Utilisation d'un classe Loader
                this.temporaryView =this.getView(this.selectedShape);
                // on met en surbrillance les formes si on passe dessus avec la souris.
                this.temporaryView.setBold(true);
            }else{
                this.temporaryView = null;
            }
        this.canvas.repaint();
    }
    
    /**
     * Méthode permettant de peindre les vues intermédiaires générées lorsque l'on déplace la forme.
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