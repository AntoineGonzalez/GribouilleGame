/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.models.generateStrategy;

import gribouillegame.models.GameModel;
import gribouillegame.models.geometricEntities.Point;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe représentant un générateur de point, pour notre modèle de jeu, utilisant des clusteurs. 
 * Il prend en paramètre une instance de jeu : GameModel
 */
public class ClusterPointGenerator extends PointGenerator{
    /**
     * Décalage minimum entre les points générés et le bord du canvas.
     */
    public final int OFFSET;
    /**
     * Décalage minimum entre les points générés et le centre du cluster.
     */
    public final int MAX_OFFSET_POINT_TO_CLUSTER;
    
    public ClusterPointGenerator(GameModel gm) {
        super(gm);
        //Calcul des offsets en fonction des limites fixées par le modèle de jeu.
        int minDim = Math.min(this.gameModel.getWidth(), this.gameModel.getHeight());
        OFFSET = 3*minDim/100;
        MAX_OFFSET_POINT_TO_CLUSTER = 10*minDim/100;
    }

    @Override
    public void generate() {
        Point cluster = null;
        // liste de clusters
        ArrayList<Point> clusters = new ArrayList();
        // on tire aléatoirement N Point aléatoire, chacun d'eux représente le centre d'un clusters.
        for(int i = 0; i < this.gameModel.getNbMaxShape() ; i++ ){
                int Min = MAX_OFFSET_POINT_TO_CLUSTER + OFFSET;
                int xMax = this.gameModel.getWidth()- Min;
                int yMax = this.gameModel.getHeight()- Min;
                int x = Min + (int)(Math.random()*((xMax - Min) + 1));
                int y = Min + (int)(Math.random()*((yMax - Min) + 1));
                cluster = new Point(x,y);
                // on ajoute le cluster tiré aléatoirement à la liste de clusters.
                clusters.add(cluster);
        }
        // Pour chaque point que l'on doit générer on tire aléatoirement un cluster et on génére un point environnant.
        for(int y = 0; y < this.gameModel.getNbPoint(); y++){
            // on mélange les clusters et on en selectionne un.
            Collections.shuffle(clusters);
            Point referencePoint = clusters.get(0);
            int Min = -MAX_OFFSET_POINT_TO_CLUSTER;
            int Max = +MAX_OFFSET_POINT_TO_CLUSTER;
            // on tire un point aléatoire environnant du cluster.
            int dx = Min + (int)(Math.random()*((Max - Min) + 1));
            int dy = Min + (int)(Math.random()*((Max - Min) + 1));
            Point pullPoint = new Point(referencePoint.getX()+dx, referencePoint.getY()+dy);
            // on ajoute ce point au modèle.
            this.gameModel.addPoint(pullPoint);
        }
    }
}
