package game;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GoldMine extends Obj {
    
    public static ArrayList<Position> GoldMinePos=new ArrayList<>();///negahdari makan haye gold mine dar map
    private int goldMieCapacity=500; 
    public static int MAX_GOLD = 100;
    private int GOLD_EXTRACT_RATIO = 1;
    public static int UPDATE_COST=200;
    private int balance;
    private int health;
    public int level=0;
    private final Image goldmine_0;
    private Image goldmine_1;
    private Team team;
    private ImageView goldImg;
    private Position pos; //*
    public final static int GOLDMINE_NUMBERS=8;
    private int myIndex;
    public static Image GOLDMINE_0_A=new Image("game/img/GoldMine_0_A.png");
    public static Image GOLDMINE_0_B=new Image("game/img/GoldMine_0_B.png");
    public static Image GOLDMINE_1_A=new Image("game/img/GoldMine_1_A.png");
    public static Image GOLDMINE_1_B=new Image("game/img/GoldMine_1_B.png");
    
    public GoldMine(){
        goldmine_0 = new Image("game/img/GoldMine_0.png");
        goldImg = new ImageView(goldmine_0);
        balance = 0;
        level=0;
        health=100;
    }

    public GoldMine(Team team,int x,int y) { //*
        goldmine_0 = new Image("game/img/GoldMine_0.png");
        goldImg = new ImageView(goldmine_0);
        balance = 0;
        level=0;
        pos=new Position(x, y); //*
        health=100;
        this.setOwner(team);
        GoldMinePos.add(new Position(x, y));
        myIndex=GoldMinePos.size()-1;
    }
    
    @Override
    public ImageView setPic() {

        return goldImg;
    }

    public void upgradePic(Image img) {
        goldImg = new ImageView(img);
    }

    public void charge() {
        if(goldMieCapacity>=GOLD_EXTRACT_RATIO && health>0){
        balance += GOLD_EXTRACT_RATIO;
        goldMieCapacity-=GOLD_EXTRACT_RATIO;
        }
    }

    public void deCharge() {
        balance = 0;
    }

    public boolean spend(int price) {
        if (balance >= price) {
            balance -= price;
            return true;
        } else {
            return false;
        }
    }

    public int getBalance() {
        return balance;
    }

    public boolean canPass() {
        return false;
    }

    public void upgrade(Image img) {
        goldmine_1 =img;
        this.upgradePic(goldmine_1);
        this.GOLD_EXTRACT_RATIO = 5;
        this.goldMieCapacity+=500;
        this.level=1;
    }

    public void setTeam(Team T) {
        this.setOwner(T);
    }
    
    public int getLevel(){
        return this.level;
    }

    public Team getTeam() {
        return this.team;
    }
    
    public int getIndex(){
      return myIndex;
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
