package gribouillegame.views;

import gribouillegame.controllers.Controller;
import gribouillegame.controllers.EmptyController;
import gribouillegame.views.geometricEntitiesViews.View;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import gribouillegame.models.GameModel;
import gribouillegame.models.geometricEntities.GeometricEntity;
import gribouillegame.models.geometricEntities.Shapes.Shape;
import gribouillegame.utils.ViewLoader;
import java.awt.Color;
import java.util.ArrayList;
import gribouillegame.utils.mvc.GameModelObserver;
import gribouillegame.utils.mvc.Observer;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashMap;
import javax.swing.BorderFactory;


/**
 * Classe représentant l'espace de dessin (JPanel).
 */
public class Canvas extends JPanel implements MouseListener, MouseMotionListener, GameModelObserver, Observer{
    /**
     * Conteneur Parent.
     */
    private GUIGame gui;
    /**
     * HashMap associant une formes du modèle à sa vue graphique.
     */
    private HashMap<Shape,View> viewMap;
    /**
     * Liste des vues à peindre.
     */
    private ArrayList<View> viewList;
    /**
     * Controller il permet de savoir comment les intéractions souris doivent être interprétées. (~ mode d'édition).
     */
    private Controller controller;
    
    public Canvas(GUIGame gui, int width, int height){
        // Dimensionnage du canvas.
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        this.setPreferredSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        this.gui = gui;
        this.controller = new EmptyController(this);
        this.viewMap = new HashMap();
        this.viewList = new ArrayList(); 
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    /**
     * Méthode permettant de peindre le contenu du canvas.
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Color defaultColor = g.getColor();
        g.setColor(defaultColor);
        //
        for (View v : this.getViewList()){
           v.paint(g2);
        }
        this.controller.paint(g2);
    }

    /**
     * Délégation de l'evenement mouseClicked au controller.
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        this.controller.mouseClicked(e);
    }
    
    /**
     * Délégation de l'evenement mousePressed au controller.
     * @param e 
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.controller.mousePressed(e);
    }
    
    /**
     * Délégation de l'evenement mouseReleased au controller.
     * @param e 
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.controller.mouseReleased(e);
    }
    
    /**
     * Délégation de l'evenement mouseEntered au controller.
     * @param e 
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        this.controller.mouseEntered(e);
    }
    
    /**
     * Délégation de l'evenement mouseExited au controller.
     * @param e 
     */
    @Override
    public void mouseExited(MouseEvent e) {
        this.controller.mouseExited(e);
    }

    /**
     * Délégation de l'evenement mouseDragged au controller.
     * @param e 
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        this.controller.mouseDragged(e);
    }

    /**
     * Délégation de l'evenement mouseMoved au controller.
     * @param e 
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        this.controller.mouseMoved(e);
    }
    
  
    /**
     * Méthode de mise à jour appelée lorsqu'une nouvelle entité géométrique est ajoutée au modèle.
     * @param geometricEntity
     * @param gm 
     */
    @Override
    public void updateElementAdded(GeometricEntity geometricEntity, GameModel gm ) {
        View view = null;
        try {
            view = ViewLoader.getInstance().loadView(geometricEntity);
            view.addObserver(this);
            if(geometricEntity instanceof Shape) {
                this.getViewMap().put((Shape) geometricEntity, view); 
            } 
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            System.err.println(ex);
        }
        
        this.getViewList().add(view);
        this.setModel(gm);
        this.getGui().getStatsTable().updateUI();
        this.repaint();
    }

     /**
     * Méthode de mise à jour appelée lorsqu'une entité géométrique est supprimée du modèle.
     * @param geometricEntity
     * @param gm 
     */
    @Override
    public void updateElementRemoved(GeometricEntity geometricEntity, GameModel gm) {
        this.getViewList().remove(this.getViewMap().get(geometricEntity));
        this.getViewMap().remove(geometricEntity);
        this.setModel(gm);
        this.getGui().getStatsTable().updateUI();
        this.repaint();
    }

     /**
     * Méthode de mise à jour appelée lorsque le modèle est remis à zéros.
     */
    @Override
    public void updateReset() {
        this.viewMap = new HashMap();
        this.viewList = new ArrayList();
        this.getGui().rebuild();
    }

    /**
     * @return the viewMap
     */
    public HashMap<Shape,View> getViewMap() {
        return viewMap;
    }

    /**
     * @param viewMap the viewMap to set
     */
    public void setViewMap(HashMap<Shape,View> viewMap) {
        this.viewMap = viewMap;
    }

    /**
     * @return the viewList
     */
    public ArrayList<View> getViewList() {
        return viewList;
    }

    /**
     * @param viewList the viewList to set
     */
    public void setViewList(ArrayList<View> viewList) {
        this.viewList = viewList;
    }

     /**
     * Méthode de mise à jour appelé lorsqu'un objet oberservé est modifié.
     * @param geometricEntity
     * @param gm 
     */
    @Override
    public void update(Object observable) {
        this.repaint();
    }
    
     /**
     * @return the currentController
     */
    public Controller getController() {
        return this.controller;
    }

    /**
     * @param c
     */
    public void setController(Controller c) {
        this.controller = c;
    }
    
    public GameModel getModel(){
        return this.getGui().getModel();
    }
    
    public void setModel(GameModel model){
        this.getGui().setModel(model);
    }

    /**
     * @return the gui
     */
    public GUIGame getGui() {
        return gui;
    }

    /**
     * @param gui the gui to set
     */
    public void setGui(GUIGame gui) {
        this.gui = gui;
    }
}
