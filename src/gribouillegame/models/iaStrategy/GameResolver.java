/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.models.iaStrategy;

import gribouillegame.models.GameModel;

/**
 * Classe abstraite permettant de représenter un résolveur de jeu (intelligence artificielle).
 * Il prend en paramètre l'instance de jeu qu'il doit résoudre.
 */
public abstract class GameResolver {
    /**
     * Instance de jeu à résoudre.
     */
    protected GameModel gameModel;
    /**
     * Constructeur de classe.
     * @param gm 
     */
    public GameResolver(GameModel gm){
        this.gameModel = gm;
    }
    
    /**
     * Méthode qui résout l'instance de jeu.
     * On trouvera différentes implémentations en fonction de la stratégie que 
     * l'on souhaite mettre en place pour la résolution du jeu.
     * @param isSimulation Boolean permettant de préciser si il s'agit 
     * d'une simulation ou non.
     * @return le score obtenu par l'IA. 
     */
    public abstract double solve(boolean isSimulation);
}
