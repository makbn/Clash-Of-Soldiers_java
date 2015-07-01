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
 * @author whiteman
 */
public class King extends Obj {
    private int health;
    private int bank;
    private int bankCounter;
    private Image king;
    private ImageView kingView;
    private Position[] BankPos=new Position[2];

    public King(Team T) {
        bank = 0;
        bankCounter=0;
        this.setOwner(T);
        health=100;
    }

    @Override
    public ImageView setPic() {

        if (this.getOwner() == Team.A) {
            king = new Image("game/img/king_A.png");

        }
        if (this.getOwner() == Team.B) {
            king = new Image("game/img/king_B.png");
        }
        kingView = new ImageView(king);
        return kingView;
    }
   
    public void setBankPos(int x , int y){
        if(BankPos[0]==null){
            BankPos[0]=new Position(x, y);
            bankCounter=1;
        }
        else if(BankPos[1]==null){
            System.out.println("raft to BAnk pos 1");
            BankPos[1]=new Position(x, y);
            bankCounter=2;
        }
    }

    public int getBankCounter() {
        return bankCounter;
    }
    
    public Position getBankPos(int bankNumber){
       
        return BankPos[bankNumber];
        
    }

    public int getHealth() {
        return health;
    }
    
    public boolean destructible(){
        return true;
    }
    
    public void damage(int fireRate){
        if(this.health>=fireRate)
            health-=fireRate;
        else
            health=0;
    }

    public boolean isDead(){
        if(health==0)
            return true;
        else
            return false;
    }

}
