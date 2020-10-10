/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame.utils.command;

/**
 * Interface définissant une action dans le cadre du pattern Command.
 * Un action peut être éxécutée ou annulée (i.e compensée).
 */
public interface Action {
    /**
     * Méthode qui éxécute l'action.
     */
    public abstract void execute();
    /**
     * Méthode qui annule l'action.
     */
    public abstract void compensate();
}
