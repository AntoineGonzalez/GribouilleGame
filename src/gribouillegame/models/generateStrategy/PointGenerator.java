/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.models.generateStrategy;

import gribouillegame.models.GameModel;

/**
 * Classe abstraite représentant un générateur de Point pour notre modèle de jeu.
 */
public abstract class PointGenerator {
    /**
     * L'instance de jeu dans lequel on souhaite générer les points.
     */
    protected GameModel gameModel; 
    /**
     * Constructeur de classe.
     * @param gm 
     */
    public PointGenerator(GameModel gm){
        this.gameModel = gm;
    }
    
    /**
     * Méthode permettant de générer les points. 
     * On trouvera différentes implémentations en fonction de la stratégie que 
     * l'on souhaite mettre en place pour la génération.
     */
    public abstract void generate();
}
