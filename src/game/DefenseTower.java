package game;

import static game.Soldier.FIRE_SCOPE;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author whiteman
 */
public class DefenseTower extends Obj {

    public static ArrayList<Position> towerPosition = new ArrayList<>();
    private int health = 100;
    private int fireRatio = 4;
    public static int FIRE_SCOPE = 3;
    public static int BUILD_COST = 500;
    private int myIndex;
    private Image tower_a;
    private Image tower_b;
    private Position pos;
    private Team team;

    public DefenseTower(Team team, int x, int y) {
        this.pos = new Position(x, y);
        this.team = team;
        this.setOwner(team);
        towerPosition.add(new Position(x, y));
        myIndex = towerPosition.size() - 1;
        System.out.println("index= " + myIndex);
    }
    
    public DefenseTower() {
        
    }

    @Override
    public ImageView setPic() {
        if (this.getOwner() == Team.B) {
            tower_b = new Image("game/img/tower_B.png");
            return new ImageView(tower_b);
        } else {
            tower_a = new Image("game/img/tower_A.png");
            return new ImageView(tower_a);
        }
    }

    public int getX() {
        return this.pos.getX();
    }

    public int getY() {
        return this.pos.getY();
    }

    public int getHealth() {
        return health;
    }

    public void damage(int fireRte) {
        if (this.health >= fireRte) {
            this.health -= fireRte;
        } else {
            this.health = 0;
        }
    }

    public void fire(Obj hubs[][]) {
        if (health > 0) {
            for (int row = -FIRE_SCOPE; row < FIRE_SCOPE; row++) {
                for (int column = -FIRE_SCOPE; column < FIRE_SCOPE; column++) {
                    if ((this.getX() + row >= 0 && this.getX() + row <= 9) && (this.getY() + column >= 0 && this.getY() + column <= 9)) {
                        if (hubs[this.getX() + row][this.getY() + column].getOwner() != this.getOwner() && hubs[this.getX() + row][this.getY() + column].destructible() && hubs[this.getX() + row][this.getY() + column].getOwner() !=Team.NO_TEAM) {
                            hubs[this.getX() + row][this.getY() + column].damage(fireRatio);
                            System.out.println("Tower Defense is firing at Row = "+row+"\tColumn = "+ column);
                        }
                    }
                }
            }
        }
    }

    public boolean canPass() {
        return false;
    }

    public boolean destructible() {
        return true;
    }

    public boolean isDead() {
        if (health == 0) {
            return true;
        } else {
            return false;
        }
    }

}
