/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.controllers;

import gribouillegame.models.geometricEntities.Point;
import gribouillegame.models.geometricEntities.Shapes.Shape;
import gribouillegame.utils.command.Action;
import gribouillegame.utils.command.RemoveShapeToModel;
import gribouillegame.views.Canvas;
import gribouillegame.views.GUIGame;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Classe représentant le MouseListener utilisé en mode "Supression de forme".
 * Il définit la manière dont les intéractions sur la souris doivent être interpréter lorsque l'on supprime une forme.
 */
public class RemoveController extends Controller{
    /**
     * Forme que l'on souhaite supprimer du model
     */
    Shape selectedShape;
    /**
     * Position de la souris lors du clique.
     */
    private Point currentCursorPosition;
    
    /**
     * Constructeur de la classe.
     * Récupére le nombre de forme maximal depuis le model.
     * @param canvas Référence sur l'espace de dessin.
     */
    public RemoveController(Canvas canvas) {
        super(canvas);
    }

    /**
     * MouseClicked Listener.
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        this.currentCursorPosition = new Point(e.getX(),e.getY());
        //On regarde si on a cliqué sur une forme. 
        this.selectedShape = this.gameModel.getSelectedShape(this.currentCursorPosition);
        if(this.selectedShape != null){
            //On créer et on ajoute une action permmettant de supprimer la forme selectionné du modèle de jeu.
            Action a = new RemoveShapeToModel(this.selectedShape, this.gameModel);
            this.gameModel.getActionHandler().handle(a);
            this.canvas.getGui().getRedo().setEnabled(false);
            this.selectedShape = null;
        }
        this.temporaryView=null;
        this.canvas.repaint();
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        this.currentCursorPosition = new Point(e.getX(),e.getY());
        this.selectedShape = this.gameModel.getSelectedShape(this.currentCursorPosition);
        if(this.selectedShape!=null){
            // Utilisation d'un classe Loader
            this.temporaryView =this.getView(this.selectedShape);
            // on met en surbrillance les formes si on passe dessus avec la souris.
            this.temporaryView.setBold(true);
        }else{
            this.temporaryView = null;
        }
        this.canvas.repaint();
    }

    @Override
    public void paint(Graphics2D g) {
        if(this.temporaryView != null){
            temporaryView.paint(g);
        }
    }
}
