/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.controllers;
import gribouillegame.models.geometricEntities.Shapes.Circle;
import gribouillegame.models.geometricEntities.Point;
import java.awt.event.MouseEvent;
import gribouillegame.utils.Calcul;
import gribouillegame.utils.command.Action;
import gribouillegame.utils.command.AddShapeToModel;
import gribouillegame.views.Canvas;
import gribouillegame.views.geometricEntitiesViews.CircleView;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;


/**
 * Classe représentant le MouseListener utilisé en mode "création de cercle".
 * Il définit la manière dont les intéractions sur la souris doivent être interprétes lorsque l'on créé un cercle.
 */
public class CircleController extends Controller {
    
    /**
     * Position initial de la souris lors du mousePressed.
     */
    private Point initialCursorPosition;
     /**
     * Position de la souris courant lors des autres événement.
     */
    private Point currentCursorPosition;
    /**
     * Référence sur le cercle en cour de création.
     */
    private Circle circle;
    /**
     * Référence qui garde en mémoire l'état du cercle lors du mouseDragged précédant.
     */
    private Circle previous;
    /**
     * Booleen qui détermine si une création de cercle est en cours.
     */
    private Boolean creationInProgress;
    /**
     * Le nombre de forme maximal que l'on peut déssiner.
     */
    private final int maxNbShape;
    
    /**
     * Constructeur de la classe.
     * Récupére le nombre de forme maximal depuis le model.
     * @param canvas Référence sur l'espace de dessin.
     */
    public CircleController(Canvas canvas) {
        super(canvas);
        this.creationInProgress = false;
        this.maxNbShape = this.gameModel.getNbMaxShape();
    }

    /**
     * MousePressed Listener.
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(this.maxNbShape > this.gameModel.getCurrentNbShape()){
            this.initialCursorPosition = new Point(e.getX(), e.getY());
            this.creationInProgress = true;
        }
    }

    /**
     * MouseRealesed Listener
     * @param e 
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if(this.creationInProgress == true && this.circle !=null){
            this.creationInProgress = false;
            //création et ajout de l'action, permettant d'ajouter le cercle au model.
            Action a = new AddShapeToModel(this.circle,this.gameModel);
            this.gameModel.getActionHandler().handle(a);
            this.canvas.getGui().getUndo().setEnabled(true);
            this.canvas.getGui().getRedo().setEnabled(false);
            this.temporaryView = null;
            this.circle = null;
        }
    }

    /**
     * MouseDragged Listener
     * @param e 
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        this.currentCursorPosition = new Point(e.getX(),e.getY());
        if(this.creationInProgress){
            int ray = Calcul.distance(this.currentCursorPosition,this.initialCursorPosition);
            this.circle = new Circle(this.initialCursorPosition, ray);
            // si le cercle dépasse du canvas on revient à l'état d'avant.
            if(circle.isOverflowing(gameModel)){
                this.circle = this.previous;
            }else{
                this.previous = this.circle;
            }
            // on créer une vue temporaire.
            this.temporaryView = new CircleView(this.circle);
            // on peint
            this.canvas.repaint();
        }
    }
    
    
    /**
     * Méthode permettant de peindre les vues intermédiaires générées lors de la création d'un cercle.
     * @param g 
     */
    @Override
    public void paint(Graphics2D g) {
        if(temporaryView != null){
            float dash1[] = {10.0f};
            Stroke stroke = new BasicStroke(1.5f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,
            10.0f, dash1, 0.0f);
            g.setStroke(stroke);
            temporaryView.paint(g);
        }
    }
}
    
