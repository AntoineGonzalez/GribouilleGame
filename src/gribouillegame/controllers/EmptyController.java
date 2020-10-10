/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.controllers;

import gribouillegame.views.Canvas;
import java.awt.Graphics2D;

/**
 * Controller par défault définissant que les actions souris de l'utilisateur n'ont aucun action sur le jeu.
 */
public class EmptyController extends Controller {

    /**
     * 
     * @param canvas Référence sur l'espace de dessin.
     */
    public EmptyController(Canvas canvas) {
        super(canvas);
    }
    
    @Override
    public void paint(Graphics2D g) {
    }
}
