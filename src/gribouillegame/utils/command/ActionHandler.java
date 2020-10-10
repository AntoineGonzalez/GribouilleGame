/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.utils.command;
import java.util.Stack;

/**
 * Gestionnaire des actions dans le cadre de la mise ne place d'intéraction Undo/Redo.
 * On y trouve deux piles d'actions, une pile d'actions annulables (undo stack) et une pile d'actions rétablisables (reso stack).
 */
public class ActionHandler {
    /**
     * Pile d'actions annulables (undo stack).
     */
    private Stack<Action> undo;
    /**
     * pile d'actions rétablisables (reso stack).
     */
    private Stack<Action> redo;
    
    /**
     * Constructeur de classe.
     */
    public ActionHandler(){
        this.undo = new Stack();
        this.redo = new Stack();
    }
    
    /**
     * Prend en charge une nouvelle action.
     * @param a 
     */
    public void handle(Action a){
        getUndo().push(a);
        a.execute();
        this.getRedo().removeAllElements();
    }
    
    /**
     * Annule la dernière action.
     */
    public void undo(){
        Action a = this.getUndo().peek();
        a.compensate();
        this.getUndo().pop();
        this.getRedo().push(a);
    }
    
    /**
     * Rétablit la dernière action.
     */
    public void redo(){
        Action a = this.getRedo().peek();
        a.execute();
        this.getRedo().pop();
        this.getUndo().push(a);
    }
    
    /**
     * @return the undo
     */
    public Stack<Action> getUndo() {
        return undo;
    }

    /**
     * @param undo the undo to set
     */
    public void setUndo(Stack<Action> undo) {
        this.undo = undo;
    }

    /**
     * @return the redo
     */
    public Stack<Action> getRedo() {
        return redo;
    }

    /**
     * @param redo the redo to set
     */
    public void setRedo(Stack<Action> redo) {
        this.redo = redo;
    }
    
}
