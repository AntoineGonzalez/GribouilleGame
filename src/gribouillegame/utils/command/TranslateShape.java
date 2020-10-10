/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.utils.command;

import gribouillegame.models.geometricEntities.Shapes.Shape;

/**
 * Action permmettant de translater une forme.
 */
public class TranslateShape implements Action{
    // décalage sur l'axe des abscisses.
    int dx;
    // décalage sur l'axe des ordonnnées.
    int dy;
    // forme translatée.
    Shape shape;

    /**
     * Constructeur de classe.
     * @param s
     * @param dx
     * @param dy 
     */
    public TranslateShape(Shape s, int dx, int dy) {
        this.shape = s;
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Réalise la translation.
     */
    @Override
    public void execute() {
        this.shape.translate(dx, dy);
    }

    /**
     * Réalisa la translation inverse.
     */
    @Override
    public void compensate() {
        this.shape.translate(-dx, -dy);
    }
}
