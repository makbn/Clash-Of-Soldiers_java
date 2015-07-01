package game;

import javafx.scene.image.ImageView;

/**
 *
 * @author whiteman
 */
public class Bank extends Obj {
    private int health;
    private int bank;
    private Position pos;
    public static int MAX_CAPACITY = 500;

    public Bank(Team t, int x, int y) {
        this.setOwner(t);
        pos = new Position(x, y);
        int bank = 0;
        health=100;

    }

    @Override
    public ImageView setPic() {
        if (this.getOwner() == Team.A) {
            return new ImageView("game/img/Bank_A.png");
        } else {
            return new ImageView("game/img/Bank_B.png");
        }
    }

    public void bankCharge(int balance) {
        int freeSpace=MAX_CAPACITY-bank;
        if(bank<MAX_CAPACITY && health>0)
            bank += Integer.min(balance,freeSpace);
    }
    
    public boolean isFull(){
        if(bank==MAX_CAPACITY)
            return true;
        else
            return false;
                   
                    
    }

    public int getCharge() {
        if(health>0)
            return bank;
        else{
            this.deCharge();
            return 0;
        }
    }

    public boolean canBuy(int cost) {
        if (cost <= this.bank && health>0) {
            return true;
        } else {
            return false;
        }

    }

    public void buy(int cost) {
        bank -= cost;
    }

    public void deCharge() {
        bank = 0;
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
