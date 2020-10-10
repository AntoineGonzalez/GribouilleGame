/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.utils;

import gribouillegame.models.geometricEntities.Point;
import java.util.ArrayList;

/**
 * Classe abstraite définissant des méthodes de calcul. 
 */
public abstract class Calcul {
    /**
     * Donne la distance entre deux points.
     */ 
    public static int distance(Point p1, Point p2){
        int deltaX = (int) p1.getX() - (int) p2.getX();
        int deltaY = (int) p1.getY() - (int) p2.getY();
        int squareDistance = (int) Math.abs(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        int distance = (int) Math.sqrt(squareDistance);
        return distance;
    }
    /**
     * retourne les deux extrémitées d'un ensemble de points (les deux points les plus opposés).
     * @param cluster
     * @return 
     */
    public static Point[] getBorderPoint(ArrayList<Point> cluster){
        Point[] res = new Point[2];
        int xMin = -1;
        int xMax = -1;
        int yMin = -1;
        int yMax= -1;
        for(int i = 0; i < cluster.size(); i++){
            if(i == 0){
                xMin = xMax = cluster.get(i).getX();
                yMin = yMax = cluster.get(i).getY();  
            }else{
                xMin = Math.min(xMin, cluster.get(i).getX());
                xMax = Math.max(xMax, cluster.get(i).getX());
                yMin = Math.min(yMin, cluster.get(i).getY());
                yMax = Math.max(yMax, cluster.get(i).getY());
            }
        }
        res[0] = new Point(xMin-10,yMin-10);
        res[1] = new Point(xMax+10,yMax+10);
        return res;
    }
}
