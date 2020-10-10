package gribouillegame.utils;

import gribouillegame.models.geometricEntities.GeometricEntity;
import gribouillegame.views.geometricEntitiesViews.View;

/**
 * Singleton retournant une instance de View Loader (chargeur de vue).
 */
public class ViewLoader {
    
    private static ViewLoader LOADER = new ViewLoader();
    // Singleton
    private ViewLoader() {
        
    }
    
    /**
     * Renvoie la vue correspondant au type d'entité fournit en paramètre.
     * @param p
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException 
     */
    public View loadView(GeometricEntity p) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader loader = p.getClass().getClassLoader();
        Class currentViewClass = loader.loadClass("gribouillegame.views.geometricEntitiesViews."+p.getClass().getSimpleName()+"View");
        View v = (View) currentViewClass.newInstance();
        v.setGeometricEntity(p);
        return v;
    }

    /**
     * Acces à linstance (singleton).
     * @return 
     */
    public static ViewLoader getInstance()
    {   
        return LOADER;
    }
}
