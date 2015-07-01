package game;

import javafx.scene.image.ImageView;

public class Obj{
    private Team owner;
    private int health=100;
    
    public ImageView setPic(){
        return new ImageView("game/img/Obj.png");
    }
    
    public Team getOwner(){
        return this.owner;
    }
    public void setOwner(Team T){
        owner=T;
    }
    public boolean canPass(){
        return false;
    }
    public boolean destructible(){
        return false;
    }
    
    public int getHealth() {
        return health;
    }
    
    public void damage(int fireRte){
        if(this.health>=fireRte)
            this.health-=fireRte;
        else
            this.health=0;
    }
    
    public boolean isDead(){
        if(health==0)
            return true;
        else
            return false;
    }
}
