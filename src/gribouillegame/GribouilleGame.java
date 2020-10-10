/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gribouillegame;

import gribouillegame.models.GameModel;
import gribouillegame.views.GUIGame;

/**
 *
 * @author 21504712
 */
public class GribouilleGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here        

        GameModel model = null;
        if(args.length == 0) {
            System.out.println("App launched with default values");
            model = new GameModel(1600,800,40,4);
             
        } else if(args.length != 4) {
            System.err.println("Wrong argument number - WIDTH HEIGHT nbPoint MaxShapes");
        } else {
            model = new GameModel(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
        }
        GUIGame game = new GUIGame(model);
    }
}
