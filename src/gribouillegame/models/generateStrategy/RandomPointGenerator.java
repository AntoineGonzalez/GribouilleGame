/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.models.generateStrategy;

import gribouillegame.models.GameModel;
import gribouillegame.models.geometricEntities.Point;

/**
 * Classe représentant un générateur de point, pour notre modèle de jeu, utilisant une génération aléatoire. 
 * Il prend en paramètre une instance de jeu : GameModel
 */
public class RandomPointGenerator extends PointGenerator {
    /**
     * Décalage minimum entre les points générés et le bord du canvas.
     */
    public final int OFFSET;
    
    /**
     * Constructeur de classe.
     * @param gm 
     */
    public RandomPointGenerator(GameModel gm) {
        super(gm);
        //Calcul des offsets en fonction des limites fixées par le modèle de jeu.
        int minDim = Math.min(this.gameModel.getWidth(), this.gameModel.getHeight());
        OFFSET = 3*minDim/100;
    }

    @Override
    public void generate() {
        // pour chaque point à générer on tire aléatoirement une coordonnée x et une coordonnée y de l'espace de dessin.
        for(int i = 0; i < this.gameModel.getNbPoint(); i++){
            int x = OFFSET + (int)(Math.random() * ((this.gameModel.getWidth() - 2*OFFSET) + 1));
            int y = OFFSET + (int)(Math.random() * ((this.gameModel.getHeight() - 2*OFFSET) + 1));
            Point p = new Point(x,y);
            // on ajoute le point au modèle de jeu.
            this.gameModel.addPoint(p);
        }    
    }
}
