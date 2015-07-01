package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author whiteman
 */
public class Soldier extends Obj {

    private Team team;
    private int health = 100;
    private int fireRatio = 3;
    private final Image Soldier_0;
    private Image Soldier_1;
    private ImageView soldierImg;
    public Position pos;
    public static int FIRE_SCOPE=1;

    public Soldier(Team team, int x, int y) {
        
        this.pos = new Position(x, y);
        

        this.team = team;
        if (team == Team.A) {
            Soldier_0 = new Image("game/img/Soldier_0_A.png");
        } else {
            Soldier_0 = new Image("game/img/Soldier_0_B.png");
        }
        soldierImg = new ImageView(Soldier_0);
        this.setOwner(team);
    }

    @Override
    public ImageView setPic() {
        soldierImg = new ImageView(Soldier_0);
        return soldierImg;
    }

    private void upgradePic(Image img) {
        soldierImg = new ImageView(img);
    }

    public void Upgrade() {
        if (health > 10) {
            if (this.team == Team.A) {
                Soldier_1 = new Image("game/img/GoldMine_1_A.png");
            } else {
                Soldier_1 = new Image("game/img/GoldMine_1_B.png");
            }
            soldierImg = new ImageView(Soldier_1);
            fireRatio = 5;
        }
    }
    
    public void damage(int fireRate){
        if(this.health>=fireRate)
            health-=fireRate;
        else
            health=0;
    }
    
    public void fire(Obj hubs[][]){
        if(health>0){
        for(int row=-FIRE_SCOPE;row<FIRE_SCOPE;row++){
            for(int column=-FIRE_SCOPE;column<FIRE_SCOPE;column++){
                if((this.getX()+row>=0 && this.getX()+row<=9) && (this.getY()+column>=0 && this.getY()+column <=9)){
                    if(hubs[this.getX()+row][this.getY()+column].getOwner() != this.getOwner() && hubs[this.getX()+row][this.getY()+column].destructible() && hubs[this.getX()+row][this.getY()+column].getOwner() !=Team.NO_TEAM){
                        hubs[this.getX()+row][this.getY()+column].damage(fireRatio);
                        
                    }
                }
            }    
        }
        }
    }
    
    public boolean destructible(){
        return true;
    }
    
    public int getX() {
        return pos.getX();
    }

    public int getY() {
        return pos.getY();
    }

    public void setX(int x) {
        this.pos.setX(x);
    }

    public void setY(int y) {
        this.pos.setY(y);
    }

    public int getHealth() {
        return health;
    }
  
    public boolean isDead(){
        if(health==0)
            return true;
        else
            return false;
    }

    
}
