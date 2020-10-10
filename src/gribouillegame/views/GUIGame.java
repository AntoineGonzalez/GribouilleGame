/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.views;

import gribouillegame.controllers.CircleController;
import gribouillegame.controllers.RectangleController;
import gribouillegame.models.GameModel;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToggleButton;
import gribouillegame.controllers.EmptyController;
import gribouillegame.controllers.MoveController;
import gribouillegame.controllers.RemoveController;
import gribouillegame.controllers.ResizeController;
import gribouillegame.models.generateStrategy.ClusterPointGenerator;
import gribouillegame.models.generateStrategy.PointGenerator;
import gribouillegame.models.generateStrategy.RandomPointGenerator;
import gribouillegame.models.iaStrategy.DumbGameResolver;
import gribouillegame.models.iaStrategy.GameResolver;
import gribouillegame.models.iaStrategy.KmeansGameResolver;
import gribouillegame.utils.command.ActionHandler;
import static java.awt.BorderLayout.CENTER;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;


/**
 * Classe représentant la fenetre principale de l'application.
 */
public class GUIGame extends JFrame{
    /**
     * Le modèle de jeu à afficher.
     */
    private GameModel model;
    /**
     * Liste des Toggles buttons.
     */
    private ArrayList<JToggleButton> menuToggleList;
    /**
     * Espace de dessin (JPanel).
     */
    private Canvas canvas;
    /**
     * Table affichant les statistiques sur les formes et le score actuel.
     */
    private JTable statsTable;
    /**
     * Conteneur scrollable de la table de statistique.
     */ 
    private JScrollPane scrollPane;
    /**
     * Boutton undo.
     */
    private JButton undo;
    /**
     * Boutton redo.
     */
    private JButton redo;
    /**
     * Menu permettant de selectionner le génrateur aléatoire de point.
     */
    private JRadioButtonMenuItem randomPointGenerator;
    /**
     * Menu permettant de selectionner le génrateur clusterisé de point.
     */
    private JRadioButtonMenuItem clusterPointGenerator;
    /**
     * Menu permettant de selectionner l'IA faible.
     */
    private JRadioButtonMenuItem kmeanGameResolver;
    /**
     * Menu permettant de selectionner l'IA complexe.
     */
    private JRadioButtonMenuItem dumbGameResolver;
    
    /**
     * Constructeur qui permet d'initialiser la feunêtre.
     * @param m l'instance de jeu à afficher.
     */
    public GUIGame(GameModel m){     
        //instanciation model de jeu
        this.model = m;
        
        //instanciation canvas
        this.canvas = new Canvas(this,m.getWidth(), m.getHeight());    
        this.model.addObserver(canvas);
        
        //generation menu bar 
        this.menuToggleList = new ArrayList();
        this.setJMenuBar(this.createMenuBar());
       
        //scrollPane et Jtable
        this.scrollPane = this.createStatsTable();
        
        //ajout a la fenetre
        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
        this.add(this.canvas,CENTER);
        this.add(this.scrollPane);
        
        // Génération des points par le modèle.
        this.model.generatePoints();
        
        //parametrage feunetre
        this.setTitle("Gribouille Game !");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    /**
     * Méthode permettant de générer la barre de menu.
     * @return 
     */
    public JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JToggleButton circleMenu = new JToggleButton("Circle");
        circleMenu.setIcon(new ImageIcon("ressources/cercle.png"));
        circleMenu.setToolTipText("Draw circle");
        menuBar.add(circleMenu);
        circleMenu.addActionListener( this::circleMenuListener );
        this.menuToggleList.add(circleMenu);
        
        JToggleButton rectangleMenu = new JToggleButton("Rectangle");
        rectangleMenu.setIcon(new ImageIcon("ressources/rectangle.png"));
        rectangleMenu.setToolTipText("Draw rectangle");
        menuBar.add(rectangleMenu);
        rectangleMenu.addActionListener( this::rectangleMenuListener );
        this.menuToggleList.add(rectangleMenu);
        
        JToggleButton removeMenu = new JToggleButton("Remove");
        removeMenu.setIcon(new ImageIcon("ressources/rubber.png"));
        removeMenu.setToolTipText("Remove shape");
        menuBar.add(removeMenu);
        removeMenu.addActionListener( this::removeMenuListener );
        this.menuToggleList.add(removeMenu);
        
        JToggleButton moveMenu = new JToggleButton("Move");
        moveMenu.setIcon(new ImageIcon("ressources/move.png"));
        moveMenu.setToolTipText("Move shape");
        menuBar.add(moveMenu);
        moveMenu.addActionListener( this::moveMenuListener );
        this.menuToggleList.add(moveMenu);
        
        JToggleButton resizeMenu = new JToggleButton("Resize");
        resizeMenu.setIcon(new ImageIcon("ressources/resize.png"));
        resizeMenu.setToolTipText("Resize shape");
        menuBar.add(resizeMenu);
        resizeMenu.addActionListener( this::resizeMenuListener );
        this.menuToggleList.add(resizeMenu);
        
        this.setUndo(new JButton("undo"));
        this.getUndo().setIcon(new ImageIcon("ressources/undo.png"));
        this.getUndo().setToolTipText("Undo action");
        menuBar.add(this.getUndo());
        this.getUndo().setEnabled(false);
        this.getUndo().addActionListener( this::undo );
        
        this.setRedo(new JButton("redo"));
        this.getRedo().setIcon(new ImageIcon("ressources/redo.png"));
        this.getRedo().setToolTipText("Redo action");
        menuBar.add(this.getRedo());
        this.getRedo().setEnabled(false);
        this.getRedo().addActionListener( this::redo );
        
        JButton submit = new JButton("Submit");
        submit.setIcon(new ImageIcon("ressources/submit.png"));
        submit.setToolTipText("Submit your solution");
        menuBar.add(submit);
        submit.addActionListener( this::checkVictory );
       
        JButton solve = new JButton("Solve");
        solve.setIcon(new ImageIcon("ressources/solve.png"));
        solve.setToolTipText("Let IA solve the Puzzle");
        menuBar.add(solve);
        solve.addActionListener( this::solveGame );
               
        JButton replay = new JButton("Replay");
        replay.setIcon(new ImageIcon("ressources/replay.png"));
        replay.setToolTipText("Made a new puzzle");
        menuBar.add(replay);
        replay.addActionListener( this::createNewGame );
        
        menuBar.add(Box.createHorizontalGlue());

        JMenu menuSettings = new JMenu("Settings");
        
        
        // groupe de bouton Point Generator
        ButtonGroup buttonGroupPoint = new ButtonGroup();
        JMenu pointsSettings = new JMenu("Point Generation");
        
        this.randomPointGenerator = new JRadioButtonMenuItem("Random genarator");
        pointsSettings.add(this.randomPointGenerator );
        buttonGroupPoint.add(this.randomPointGenerator );
 
        this.clusterPointGenerator = new JRadioButtonMenuItem("Cluster Generator ");
        pointsSettings.add(this.clusterPointGenerator );
        buttonGroupPoint.add(this.clusterPointGenerator );
        menuSettings.add(pointsSettings);
        
        PointGenerator pointGenerator = this.model.getPointGenerator();
        if(pointGenerator instanceof RandomPointGenerator){
            this.randomPointGenerator.setSelected(true);
        }else if(pointGenerator instanceof ClusterPointGenerator){
            this.clusterPointGenerator.setSelected(true);
        }
     
        // groupe de bouton IA Settings
        ButtonGroup buttonGroupIA = new ButtonGroup();
        JMenu iaSettings = new JMenu("IA Type");
        
        this.dumbGameResolver = new JRadioButtonMenuItem("Dumb IA");
        iaSettings.add(this.dumbGameResolver );
        buttonGroupIA.add(this.dumbGameResolver );
         this.dumbGameResolver.addActionListener( this::setDumbResolver );
 
        this.kmeanGameResolver = new JRadioButtonMenuItem("Kmean IA ");
        iaSettings.add(kmeanGameResolver);
        buttonGroupIA.add(kmeanGameResolver);
        this.kmeanGameResolver.addActionListener( this::setKmeanResolver );
        menuSettings.add(iaSettings);
        
        GameResolver gameResolver = this.model.getGameResolver();
        if(gameResolver instanceof KmeansGameResolver){
            this.kmeanGameResolver .setSelected(true);
        }else if(gameResolver instanceof DumbGameResolver){
            this.dumbGameResolver .setSelected(true);
        }
        
        menuBar.add(menuSettings);
        return menuBar;
    }
    
    /**
     * Méthode générant la table de statistiques.
     * @return 
     */
    public JScrollPane createStatsTable(){
        JScrollPane res;
        this.statsTable = new JTable(new StatsModel(this.model));
        this.statsTable.setDefaultRenderer(Object.class, new JtableRenderer(this.model.getNbMaxShape()));
        res = new JScrollPane(this.statsTable);
        res.setPreferredSize(new Dimension(this.model.getWidth(),200));
        res.setMaximumSize(new Dimension(this.model.getWidth(),200));
        return res;
    }
    
    /**
     * Méthode permettant de remettre la feunêtre dans son etat initial.
     */
    public void rebuild(){
        this.setJMenuBar(this.createMenuBar());
        this.remove(this.scrollPane);
        this.scrollPane = this.createStatsTable();
        this.add(this.scrollPane);
        this.canvas.setController(new EmptyController(this.canvas));
        this.getRedo().setEnabled(false);
        this.getUndo().setEnabled(false);
        this.repaint();
        this.revalidate();
    }
    
    
    
    /**
     * Evenement appelé lorque l'on sélectionne l'option Dumb IA.
     * @param event 
     */
    public void setDumbResolver( ActionEvent event ){
        this.model.setGameResolver(new DumbGameResolver(this.model));
    }
    
    /**
     * Evenement appelé lorque l'on selectionne l'option Kmean IA.
     * @param event 
     */
    public void setKmeanResolver( ActionEvent event ){
        this.model.setGameResolver(new KmeansGameResolver(this.model));
    }
    
    /**
     * Evenement appelé lorque l'on clique sur le boutton création de cercle.
     * @param event 
     */
    public void circleMenuListener( ActionEvent event ) {
        Toggle(event);
        this.canvas.setController(new CircleController(this.canvas));
    }
    
    /**
     * Evenement appelé lorque l'on clique sur le boutton création de rectangle.
     * @param event 
     */
    public void rectangleMenuListener( ActionEvent event ) {
        Toggle(event);
        this.canvas.setController(new RectangleController(this.canvas));
    }
    
    /**
     * Evenement appelé lorque l'on clique sur le boutton déplacement de formes.
     * @param event 
     */
    public void moveMenuListener( ActionEvent event ) {
        Toggle(event);
        this.canvas.setController(new MoveController(this.canvas));
    }
    
    /**
     * Evenement appelé lorque l'on clique sur le boutton supression de formes.
     * @param event 
     */
    public void removeMenuListener( ActionEvent event ) {
        Toggle(event);
        this.canvas.setController(new RemoveController(this.canvas));
    }
    
    /**
     * Evenement appelé lorque l'on clique sur le boutton retouche de formes.
     * @param event 
     */
    public void resizeMenuListener( ActionEvent event ) {
        Toggle(event);
        this.canvas.setController(new ResizeController(this.canvas));
    }
    
    /**
     * Evenement appelé lorque l'on clique sur le boutton redo.
     * @param event 
     */
    public void redo( ActionEvent event ){
        ActionHandler actionHandler = this.model.getActionHandler();
        actionHandler.redo();
        this.getUndo().setEnabled(!actionHandler.getUndo().isEmpty());
        this.getRedo().setEnabled(!actionHandler.getRedo().isEmpty());
    }
    
    /**
     * Evenement appelé lorque l'on clique sur le boutton undo.
     * @param event 
     */
    public void undo( ActionEvent event ){
        ActionHandler actionHandler = this.model.getActionHandler();
        actionHandler.undo();
        this.getUndo().setEnabled(!actionHandler.getUndo().isEmpty());
        this.getRedo().setEnabled(!actionHandler.getRedo().isEmpty());
    }
    
    /**
     * Evenement appelé lorque l'on clique sur le boutton Submit.
     * @param event 
     */
    public void checkVictory( ActionEvent event ){
        String message;
        boolean solve = this.model.isSolve();
        if(solve){
            double score = this.model.getScore();
            score = Math.round(score * 1000) / 1000.0;
            double iaScore = this.model.simulate();
            iaScore = Math.round(iaScore * 1000) / 1000.0;
            message = "Well played you have solved the Puzzle !"
                    + "\n" 
                    +" \u27A4 Your score is : "
                    + score 
                    + "\n"
                    +" \u27A4 The IA score is : "
                    + iaScore 
                    +"\n\n Play a new Game ?" ;            
        }else{
            message = "It seems that some points are not cover!\n\nDo you want to continue ?";
        }
        
        int response = JOptionPane.showConfirmDialog(null,message,"Results",JOptionPane.YES_NO_OPTION);
         
        if((response == JOptionPane.YES_OPTION && solve) || (response == JOptionPane.NO_OPTION && !solve)){
            this.createNewGame(null);
        }
    }
    
    /**
     * Evenement appelé lorque l'on clique sur le boutton Solve.
     * @param event 
     */
    public void solveGame( ActionEvent event ) {
        this.model.getActionHandler().getUndo().removeAllElements();
        this.model.getActionHandler().getRedo().removeAllElements();
        this.model.solve();
        this.undo.setEnabled(true);
    }
    
    /**
     * Evenement appelé lorque l'on clique sur le boutton replay.
     * @param event 
     */
    public void createNewGame( ActionEvent event ) {
        if(this.clusterPointGenerator.isSelected()){
            this.model.setPointGenerator(new ClusterPointGenerator(this.model));
        }else if(this.randomPointGenerator.isSelected()){
            this.model.setPointGenerator(new RandomPointGenerator(this.model));
        }
        
        this.model.initComponent();
        this.model.generatePoints();
        this.repaint();
        this.revalidate();
    }
    
    public void Toggle(ActionEvent event){
        for(JToggleButton jb : this.menuToggleList) {
            if( jb != event.getSource()){
                jb.setSelected(false);
            }
        }
    }

    /**
     * @return the model
     */
    public GameModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(GameModel model) {
        this.model = model;
    }

    /**
     * @return the canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * @param canvas the canvas to set
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
    
    /**
     * @return the statsTable
     */
    public JTable getStatsTable() {
        return statsTable;
    }

    /**
     * @param statsTable the statsTable to set
     */
    public void setStatsTable(JTable statsTable) {
        this.statsTable = statsTable;
    }

    /**
     * @return the undo
     */
    public JButton getUndo() {
        return undo;
    }

    /**
     * @param undo the undo to set
     */
    public void setUndo(JButton undo) {
        this.undo = undo;
    }

    /**
     * @return the redo
     */
    public JButton getRedo() {
        return redo;
    }

    /**
     * @param redo the redo to set
     */
    public void setRedo(JButton redo) {
        this.redo = redo;
    }
}
