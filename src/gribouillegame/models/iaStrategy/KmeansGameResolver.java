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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Classe permettant de représenter un résolveur de jeu de haut niveau utilisant l'algorithme des k-moyennes (intelligence artificielle de niveau Eleve).
 * Il prend en paramètre l'instance de jeu qu'il doit résoudre.
 */
public class KmeansGameResolver extends GameResolver{
    /**
     * Constructeur de classe.
     * @param gm 
     */
    public KmeansGameResolver(GameModel gm) {
        super(gm);
    }

    /**
     * Méthode qui résout l'instance de jeu.
     * Dans cette implémentation l'IA détermine des clusters de points et les recouvres par des rectangles.
     * @param isSimulation Boolean permettant de préciser si il s'agit 
     * d'une simulation de résolution ou non.
     * @return le score obtenu par l'IA. 
     */
    @Override
    public double solve(boolean isSimulation) {
        if(!isSimulation){
            this.gameModel.removeAllShapes();
        }
        double score = 0;
        int k = this.gameModel.getNbMaxShape();
        ArrayList<Point> population = this.gameModel.getPoints();
        int xMin = 0;
        int xMax = this.gameModel.getWidth();
        int yMin = 0;
        int yMax = this.gameModel.getHeight();
        // Appel à l'algorithme k-mean pour avoir la liste des clusters à recouvrir.
        ArrayList<ArrayList<Point>> clusters = kMeans(k, population, xMin, xMax, yMin, yMax );
        // on itére sur les clusters déterminés par l'algo K-mean.
        for(ArrayList<Point> cluster : clusters ){
            if(!cluster.isEmpty()){
                // on récupére les deux points les plus opposés du cluster.
                Point[] borderPoints = Calcul.getBorderPoint(cluster);
                Point pointUpLeft =  borderPoints[0];
                Point pointDownRight =  borderPoints[1];
                // On dessine un rectangle à partir de ces deux points. ( la droite entre les deux points est une diagonale du rectangle ).
                Rectangle r = new Rectangle(pointUpLeft,pointDownRight);
                if(!isSimulation){
                    // ajout du rectangle au modèle de jeu.
                    Action a = new AddShapeToModel(r,this.gameModel);
                    this.gameModel.getActionHandler().handle(a);
                }else{
                    // calcul du score
                    score += this.gameModel.computeAreaInPercent(r.getArea());
                }
            }
        }
        return score;
    }
    
    /**
     * @param k nombre de centroid(centre d'un cluster) à générer.
     * @param xMin valeur minimal pour les abscisses.
     * @param xMax valeur maximal pour les abscisses.
     * @param yMin valeur minimal pour les ordonnées.
     * @param yMax valeur maximal pour les ordonnées.
     * @return la liste des centroids.
     */
    public ArrayList<Point> pullCentroids(int k, int xMin, int xMax,int yMin, int yMax ){
        ArrayList<Point> centroids = new ArrayList();
        Point centroid;
        boolean addFlag = false;
        int x = xMin + (int)(Math.random()*((xMax - xMin) + 1));
        int y = yMin + (int)(Math.random()*((yMax - yMin) + 1));
        centroid = new Point(x,y);
        centroids.add(centroid);
        while(centroids.size() != k){
            x = xMin + (int)(Math.random()*((xMax - xMin) + 1));
            y = yMin + (int)(Math.random()*((yMax - yMin) + 1));
            centroid = new Point(x,y);
            for(Point p : centroids){
                if( p.getX() != centroid.getX() || p.getY() != centroid.getY()){
                    addFlag = true;
                }else{
                    addFlag = false;
                }
            }
            if(addFlag){
                centroids.add(centroid);
            }
        }
        return centroids;
    }
    
    /**
     * Méthode qui associe chaque point d'une population de point à son cluster le plus proche,
     * i.e au centroid le plus proche.
     * @param population population de point à traiter.
     * @param centroids centroids auquelles associés les différents points.
     * @param map hashmap associant le point à un nom de cluster ( numéro identifiant -> id ).
     * @return la listes des clusters. 
     */
    public ArrayList<ArrayList<Point>> computeClusters(ArrayList<Point> population, ArrayList<Point>centroids, HashMap<Point,Integer> map ){
        ArrayList<ArrayList<Point>> clusters = new ArrayList();
        for(int i=0; i < centroids.size(); i++){
            clusters.add(new ArrayList());
        }
        Point nearestCentroid = null;
        int minDistance = 0;
        int distance = 0;
        for(Point p : population){
            for(int i =0; i < centroids.size(); i++){
                if( i == 0 ){
                    nearestCentroid = centroids.get(0);
                    minDistance = Calcul.distance(p, nearestCentroid);
                }else{
                    distance = Calcul.distance(p, centroids.get(i));
                    minDistance = Math.min(minDistance, distance);
                    if(minDistance == distance){
                        nearestCentroid = centroids.get(i);
                    }
                }    
            }
            clusters.get(centroids.indexOf(nearestCentroid)).add(p);
            map.put(p,centroids.indexOf(nearestCentroid));
        }
        return clusters;
    }
    
    /**
     * Méthode permettant de recalculer la position des centroids à chaque itération de l'algorithme des k-moyennes pour 
     * que celui-ci reste au milieu de sa population/de son cluster.
     * @param clusters
     * @param centroids 
     */
    public void centroidsRecompute(ArrayList<ArrayList<Point>> clusters, ArrayList<Point> centroids){
        for(int i = 0; i < clusters.size(); i++){
            Point newCentroid = means(clusters.get(i));
            if(newCentroid != null){
                centroids.set(i, newCentroid);
            }
        }
    }
    
    /**
     * Calcul la position moyenne d'une liste de points (Point au milieur de la populaiton / cluster).
     * @param population le groupe de point / la population du cluster.
     * @return Un point centré par rapport à la population.
     */
    public Point means(ArrayList<Point> population){
        if(!population.isEmpty()){
            int x = 0;
            int y = 0;
            for(Point p: population){
                x += p.getX();
                y += p.getY();
            }
            x = x/population.size(); 
            y = y/population.size(); 
            return new Point(x,y);
        }
        return null;
    }
    
    /**
     * Algorithme des k-moyennes qui détermine des cluster de points pour une population de points données.
     * @param k nombre de cluster.
     * @param population population de point à traiter.
     * @param xMin valeur minimal pour les abscisses.
     * @param xMax valeur maximal pour les abscisses.
     * @param yMin valeur minimal pour les ordonnées.
     * @param yMax valeur maximal pour les ordonnées.
     * @return une liste de listes de points (une liste de point correspond à un cluster).
     */
    public ArrayList<ArrayList<Point>> kMeans(int k, ArrayList<Point> population, int xMin, int xMax,int yMin, int yMax){
        boolean convergence = false;
        HashMap<Point,Integer> map =  new HashMap();
        ArrayList<Point> centroids = new ArrayList();
        ArrayList<ArrayList<Point>> clusters;
        // on tire aléatoirement k centroids (centre de cluster).
        centroids = pullCentroids(k, xMin, xMax, yMin, yMax);
        // on associe chaque point de la population à son centroid le plus proche.
        clusters = computeClusters(population, centroids, map);
        // tant qu'il n'y a pas convergence ( i.e les clusters ne sont pas fixes).
        while(!convergence){
            // on recalcule la position des centroids.
            centroidsRecompute(clusters,centroids);
            HashMap<Point,Integer> oldMap= (HashMap<Point,Integer>) map.clone();
            // on re-associe chaque point de la population à son centroid le plus proche.
            clusters = computeClusters(population, centroids, map);
            // on regarde si les populations des clusters ont changées. Si oui alors il n'y a pas convergence sinon il y a converfence.
            for(Entry<Point, Integer> entry : map.entrySet()) {
                convergence = true;
                Point Point = entry.getKey();
                Integer centroidsId = entry.getValue();
                if(oldMap.get(Point) != centroidsId){
                    convergence =false;
                    break;
                }
            }
        }
        return clusters;
    }
}
