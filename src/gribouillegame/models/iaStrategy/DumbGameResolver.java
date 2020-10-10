/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.models.iaStrategy;

import gribouillegame.models.GameModel;
import gribouillegame.models.geometricEntities.Point;
import gribouillegame.models.geometricEntities.Shapes.Rectangle;
import gribouillegame.utils.Calcul;
import gribouillegame.utils.command.Action;
import gribouillegame.utils.command.AddShapeToModel;

/**
 * Classe permettant de représenter un résolveur de jeu de bas niveau (intelligence artificielle de niveau Faible).
 * Il prend en paramètre l'instance de jeu qu'il doit résoudre.
 */
public class DumbGameResolver extends GameResolver{

    /**
     * Constructeur de classe.
     * @param gm 
     */
    public DumbGameResolver(GameModel gm) {
        super(gm);
    }

    /**
     * Méthode qui résout l'instance de jeu.
     * Dans cette implémentation l'Ia déssine un grand rectangle recooouvrant tout les points à recouvrir.
     * @param isSimulation Boolean permettant de préciser si il s'agit 
     * d'une simulation de résolution ou non.
     * @return le score obtenu par l'IA. 
     */
    @Override
    public double solve(boolean isSimulation) {
        if(!isSimulation){
            // supression des formes éxistantes.
            this.gameModel.removeAllShapes();
        }
        // Récupére les deux points les plus opposés.
        Point[] borderPoints = Calcul.getBorderPoint(this.gameModel.getPoints());
        Point pointUpLeft =  borderPoints[0];
        Point pointDownRight =  borderPoints[1];
        // On dessine un rectangle à partir de ces deux points. ( la droite entre les deux points est une diagonale du rectangle ).
        Rectangle r = new Rectangle(pointUpLeft,pointDownRight);
        if(!isSimulation){
            // ajout du rectangle au modèle de jeu.
            Action a = new AddShapeToModel(r,this.gameModel);
            this.gameModel.getActionHandler().handle(a);       
        }
        // calcul du score
        double score = this.gameModel.computeAreaInPercent(r.getArea());
        return score;
    }
}
