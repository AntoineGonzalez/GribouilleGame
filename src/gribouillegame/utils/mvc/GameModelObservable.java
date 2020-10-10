package gribouillegame.utils.mvc;

import gribouillegame.models.geometricEntities.GeometricEntity;

/**
 * Interface permettant de définir un model écoutable à listes.
 */
public interface GameModelObservable{
    /**
     * Ajoute un écouteur.
     * @param obs 
     */
    public void addObserver(GameModelObserver obs);
    /**
     * Supprime tout les ecouteurs.
     */
    public void removeObserver();
    /**
     * Notifie les ecouteurs d'un ajout.
     * @param s 
     */
    public abstract void notifyRemoveGeometricEntity(GeometricEntity s);
    /**
     * Notifie les ecouteurs d'un retrait.
     * @param s 
     */
    public abstract void notifyAddGeometricEntity(GeometricEntity s);
    /**
     * Notifie les écouteurs d'une remise à zéros.
     */
    public abstract void notifyReset();
}
