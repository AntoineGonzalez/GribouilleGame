/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.models;

import gribouillegame.models.generateStrategy.PointGenerator;
import gribouillegame.models.generateStrategy.RandomPointGenerator;
import gribouillegame.models.iaStrategy.GameResolver;
import gribouillegame.models.iaStrategy.KmeansGameResolver;
import gribouillegame.models.geometricEntities.GeometricEntity;
import gribouillegame.models.geometricEntities.Point;
import gribouillegame.models.geometricEntities.Shapes.Shape;
import gribouillegame.utils.command.ActionHandler;
import java.util.ArrayList;
import gribouillegame.utils.mvc.GameModelObservable;
import gribouillegame.utils.mvc.GameModelObserver;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *Classe représenant notre modèle de jeu, c'est à dire l'ensemble des données 
 * permmettant de représenter un état du jeu à un instant t.
 */
public class GameModel implements GameModelObservable {
    /**
     * Largeur de l'espace de dessin (i.e le Canvas).
     */
    private final int width;
    /**
     * Hauteur de l'espace de dessin (i.e le Canvas).
     */
    private final int height;
    /**
     * L'air occupé par l'espace de dessin.
     */
    private int area;
    /**
     * Nombre de point à générer et recouvrir pour gagner la partie.
     */
    private int nbPoint;
    /**
     * Nombre de forme maximum autorisé pour résoudre le "puzzle" / jeu. 
     */
    private int nbMaxShape;
    /**
     * Gestionnaire des actions sur le modèle ( permet de garder en mémoire 
     * toutes les actions réalisées pour pouvoir réaliser des undo/redo ).
     */
    private ActionHandler actionHandler;
    /**
     * Liste d'instances de formes, contient la liste des formes peintes par l'utilisateur. 
     */
    private ArrayList<Shape> shapes;
    /**
     * Liste d'instance de points, contient la liste des points générés. 
     */
    private ArrayList<Point> points;
    /**
     * Dictionnaire/map liant chaque forme à la valeur de son aire occupée.
     */
    private HashMap<Shape,Double> areas;
    /**
     * Générateur de points.
     */
    private PointGenerator pointGenerator;
    /**
     * Résolveur du jeu / Intelligence artificielle.
     */
    private GameResolver gameResolver;
    /**
     * Listes des Objets écoutant le modèle de jeu.
     */
    private ArrayList<GameModelObserver> listObserver;
    
    /**
     * Constructeur de Classe permettant d'initialiser une instance de jeu.
     * @param w Largeur de l'espace de dessin.
     * @param h Hauteur de l'espace de dessin. 
     * @param nbPoint Nombre de Point à générer.
     * @param nbMaxShape Nombre de Forme autorisé.
     */
    public GameModel(int w, int h, int nbPoint, int  nbMaxShape){
        this.width = w;
        this.height = h;
        this.computeArea();
        this.listObserver = new ArrayList<>();
        this.nbPoint = nbPoint;
        this.nbMaxShape =  nbMaxShape;
        this.pointGenerator = new RandomPointGenerator(this);
        this.gameResolver = new KmeansGameResolver(this);
        this.actionHandler = new ActionHandler();
        this.initComponent();
    }
    
    /**
     * Méthode initialisant nos conteneurs d'entités géométriques (i.e Formes et Points).
     */
    public void initComponent(){
        this.areas = new HashMap();
        this.shapes = new ArrayList();
        this.points = new ArrayList();
        this.notifyReset();
    }
    
    /**
     * Méthode résolvant l'instance de jeu ( appel à l'IA ).
     */
    public void solve(){
        this.getGameResolver().solve(false);
    }
    
    /**
     * Méthode résolvant l'instance de jeu ( appel à l'IA ).
     */
    public double simulate(){
        return this.getGameResolver().solve(true);
    }

    /**
     * Méthode nommant chaque forme en fonction de sa position dans la liste de 
     * formes ( Permet un nommage unique ) .
     */
    public void namedShapes(){
        for(int i = 0; i < this.shapes.size(); i++){
            this.shapes.get(i).setName(i);
        }
    }
    
    /**
     * Méthode permettant d'ajouter une forme au modèle.
     * @param s La forme à ajouter.
     * @param resize Boolean indiquer si l'ajout est une copie ( c'est le cas lors d'un resize ).
     */
    public void addShape(Shape s, boolean resize){
        this.shapes.add(s);
        // pas besoin de renommer les formes lors d'un resize.
        if(!resize){
            this.namedShapes();
        }
        double areaInPercent = this.computeAreaInPercent(s.getArea());
        this.areas.put(s, areaInPercent);
        // notifie le(s) observeur(s) de l'ajout.
        this.notifyAddGeometricEntity(s);
    }
    
    /**
     * Méthode permettant de supprimer une forme au modèle.
     * @param s La forme à suprimmer.
     */
    public void removeShape(Shape s) {
        this.shapes.remove(s);
        this.getAreas().remove(s);
        // notifie le(s) observeur(s) de la suppression.
        this.notifyRemoveGeometricEntity(s);
    }
    
    /**
     * Méthode permettant de supprimer toutes les formes du modèle.
     */
    public void removeAllShapes(){
        while(!this.shapes.isEmpty()){
            this.removeShape(this.shapes.get(this.shapes.size()-1));
        }
    }
    
    /**
     * Méthode permettant d'ajouter un Point au modèle.
     * @param p Le point à ajouter.
     */
    public void addPoint(Point p){
        this.getPoints().add(p);
        // notifie le(s) observeur(s) de l'ajout.
        this.notifyAddGeometricEntity(p);
    }
     
    /**
     * Méthode permettant de supprimer un Point au modèle.
     * @param p Le point à ajouter.
     */
    public void removePoint(Point p){
        this.getPoints().remove(p);
        // notifie le(s) observeur(s) de la suppression.
        this.notifyRemoveGeometricEntity(p);
    }
    
    /**
     * Méthode qui renvoie true si le "puzzle" / jeu est résolu et false sinon.
     * @return un boolean (true = résolu / false  = pas résolu ).
     */
    public boolean isSolve(){
        ArrayList pointsToEvaluate = (ArrayList) this.getPoints().clone();
        for(Shape shape : this.shapes){
            Iterator it = this.getPoints().iterator();
            while(it.hasNext()){
               Point p = (Point) it.next();
               if(shape.contain(p)){
                   pointsToEvaluate.remove(p);
               }
            }
        }
        return pointsToEvaluate.isEmpty();
    }
    
    /**
     * Ajoute un observer/écouteur à l'instance de jeu.
     * @param obs 
     */
    @Override
    public void addObserver(GameModelObserver obs){
        this.listObserver.add(obs);
    }

    /**
     * Supprime un observer/écouteur à l'instance de jeu.
     * @param obs 
     */
    @Override
    public void removeObserver() {
        this.listObserver=new ArrayList<>();
    }
    
    /**
     * Notifie les écouteurs que l'instance de jeu est remis à l'état initiale (nouvelle partie).
     */
    @Override
    public void notifyReset() {
        for(GameModelObserver o : this.listObserver){
            o.updateReset();
        }
    }
    
    /**
     * Notifie les écouteurs qu'une entité géométrique ( Point ou Shapes ) est ajouté au modèle. 
     */
    @Override
    public void notifyRemoveGeometricEntity(GeometricEntity s) {
        for(GameModelObserver o : this.listObserver){
            o.updateElementRemoved(s,this);
        }
    }
    
    /**
     * Notifie les écouteurs qu'une entité géométrique  ( Point ou Shapes ) est supirmé du modèle.
     * @param s
     */
    @Override
    public void notifyAddGeometricEntity(GeometricEntity s) {
        for(GameModelObserver o : this.listObserver){
            o.updateElementAdded(s,this);
        }
    }
    
    /**
     * Retourne la forme pointée par le point p passé en parametre. 
     * @param p
     * @return retourne la forme pointée par le point p et null si aucune forme n'est pointée.
     */
    public Shape getSelectedShape(Point p){
        Shape res = null;
        for(Shape shape : this.getShapes()){
            if(shape.contain(p)){ 
                res = shape;
            }
        }
        return res;
    }
    
    /**
     *  Méthode générant les points ( appel au générateur de points ).
     */
    public void generatePoints(){
        this.getPointGenerator().generate();
    }
    /**
     * @return the shapes
     */
    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }



    private void computeArea() {
        this.setArea(this.height*this.width);
    }

    /**
     * @return the nbPoint
     */
    public int getNbPoint() {
        return nbPoint;
    }

    /**
     * @param nbPoint the nbPoint to set
     */
    public void setNbPoint(int nbPoint) {
        this.nbPoint = nbPoint;
    }

    /**
     * @return the areas
     */
    public HashMap<Shape,Double> getAreas() {
        return areas;
    }

    /**
     * @param areas the areas to set
     */
    public void setAreas(HashMap<Shape,Double> areas) {
        this.areas = areas;
    }

    public double computeAreaInPercent(int area) {
        return (double) area*100 / this.getArea();
    }

    /**
     * @return the nbShape
     */
    public int getNbMaxShape() {
        return nbMaxShape;
    }

    /**
     * @param nbShape the nbShape to set
     */
    public void setNbMaxShape(int nbShape) {
        this.nbMaxShape = nbShape;
    }

    public double getScore() {
        double res = 0;
        for (Map.Entry<Shape,Double> e : this.areas.entrySet()){
           res+= e.getValue();
        }
        return res;
    }

    public int getCurrentNbShape() {
        return this.areas.size();
    }

    /**
     * @return the area
     */
    public int getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(int area) {
        this.area = area;
    }

    /**
     * @return the points
     */
    public ArrayList<Point> getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    /**
     * @return the actionHandler
     */
    public ActionHandler getActionHandler() {
        return actionHandler;
    }

    /**
     * @param actionHandler the actionHandler to set
     */
    public void setActionHandler(ActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    /**
     * @return the pointGenerator
     */
    public PointGenerator getPointGenerator() {
        return pointGenerator;
    }

    /**
     * @param pointGenerator the pointGenerator to set
     */
    public void setPointGenerator(PointGenerator pointGenerator) {
        this.pointGenerator = pointGenerator;
    }

    /**
     * @return the gameResolver
     */
    public GameResolver getGameResolver() {
        return gameResolver;
    }

    /**
     * @param gameResolver the gameResolver to set
     */
    public void setGameResolver(GameResolver gameResolver) {
        this.gameResolver = gameResolver;
    }
}
