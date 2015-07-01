/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author UserName
 */
public class Green extends Obj {

    Image Green_A;
    Image Green_B;

    public Green(Team T) {
        Green_A = new Image("game/img/Green_A.png");
        Green_B = new Image("game/img/Green_B.png");
        this.setOwner(T);

    }

    @Override
    public ImageView setPic() {
        if (this.getOwner() == Team.B) {
            return new ImageView(Green_B);
        }
        if (this.getOwner() == Team.A) {
            return new ImageView(Green_A);
        } else {
            return new ImageView("game/img/Green.png");
        }
    }

    public boolean canPass() {
        return true;
    }
    
    @Override
    public boolean destructible(){
        return false;
    }
    

}
