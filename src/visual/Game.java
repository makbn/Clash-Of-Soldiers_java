package visual;

import game.Bank;
import game.GoldMine;
import game.Green;
import game.King;
import game.Obj;
import game.Position;
import game.Soldier;
import game.Team;
import game.DefenseTower;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sun.nio.cs.ext.GB18030;

public class Game {

    private static final int SCREEN_SIZE = 600;
    private static final int BORDER_SIZE = 40;
    private static final int HUB_SIZE = 60;
    private static final int COLUMN = SCREEN_SIZE / HUB_SIZE;
    private static final int ROW = COLUMN;
    private Obj[][] hubs = new Obj[COLUMN][ROW];
    private Soldier soldierCheck;
    public Soldier soldier_A;
    public Soldier soldier_B;
    private GoldMine goldMineChecker;
    private King kingChechker;
    private Soldier soldierChecker;
    private Bank bankChecker;
    private Green greenChecker;
    private DefenseTower defenseTowerChecker;

    public Game() {
        soldier_A = new Soldier(Team.A, 0, 5);
        soldier_B = new Soldier(Team.B, 9, 5);
        goldMineChecker = new GoldMine();
        soldierChecker = new Soldier(Team.NO_TEAM, 10, 10);
        bankChecker = new Bank(Team.NO_TEAM, 10, 10);
        greenChecker = new Green(Team.NO_TEAM);
        defenseTowerChecker=new DefenseTower();
        kingChechker = new King(Team.NO_TEAM);
        hubs = fillMap();
    }

    public Obj[][] fillMap() {

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                hubs[i][j] = new Green(Team.NO_TEAM);
            }
        }
        //Kings
        hubs[0][0] = new King(Team.A);
        hubs[9][9] = new King(Team.B);
        for (int i = 0; i < GoldMine.GOLDMINE_NUMBERS; i++) {
            Random rand = new Random();
            int tempX = 2 + rand.nextInt(ROW - 3);
            int tempY = 2 + rand.nextInt(COLUMN - 3);
            hubs[tempX][tempY] = new GoldMine(Team.NO_TEAM, tempX, tempY);
            GoldMine.GoldMinePos.add(new Position(tempX, tempY));
            System.out.println("tempX= " + tempX + "\tempY= " + tempY);
        }

        hubs[0][5] = soldier_A;
        hubs[9][5] = soldier_B;

        return hubs;
    }

    public void update(int cicle) {
        // try {
        this.goldMineAmountUpdate();
        this.goldMineLevelUpgrade(cicle);
        this.bankChecker(Team.B, cicle);
        this.bankChecker(Team.A, cicle);
        this.bankAmountUpdate(cicle);
        this.moveSoldiers();
        this.addTower(Team.A);
        this.addTower(Team.B);
        soldier_A.fire(hubs);
        soldier_B.fire(hubs);
        this.towerAttack();
        this.PS();
        this.mapCleaner();
        System.gc();

        // } catch (NullPointerException e) {
//            System.out.println("null pointer! we should Exit sorry for this but i cant fix all bugs!"
//                    + "\nyou should get this Expetion when Soldier A try to build his second Bank "
//                    + "\nim checked everything but i cant figur out this..."
//                    + "\nbut its not important you can run again and enjoy my game!");
//            System.exit(0);
        //}
    }

    public ImageView getHubView(int row, int column) {
        if (hubs[row][column].getClass().getSimpleName().equals(Soldier.class.getClass().getSimpleName())) {
            return ((Soldier) hubs[row][column]).setPic();
        } else {
            return hubs[row][column].setPic();
        }

    }

    public int NumberOfHubs() {
        return COLUMN * ROW;
    }

    public int getColumn() {
        return COLUMN;
    }

    public int getRow() {
        return ROW;
    }

    public int getScreenSize() {
        return SCREEN_SIZE + BORDER_SIZE;
    }

    private void bankAmountUpdate(int cicle) {
        if (cicle > 2) {
            for (Position pos : GoldMine.GoldMinePos) {
                //System.out.println("x=" + pos.getX());
                if (hubs[pos.getX()][pos.getY()].getOwner().equals(Team.A) && this.isGoldMine(pos.getX(),pos.getY())) {
                    //System.out.println("goldmine balance=" + ((GoldMine) hubs[pos.getX()][pos.getY()]).getBalance());
                    if (!(this.getBank(Team.A, 0).isFull())) {
                        this.getBank(Team.A, 0).bankCharge(this.getGoldMine(pos.getX(), pos.getY()).getBalance());
                    } else {
                        this.getBank(Team.A, 1).bankCharge(this.getGoldMine(pos.getX(), pos.getY()).getBalance());
                    }
                }
                if (hubs[pos.getX()][pos.getY()].getOwner().equals(Team.B) &&this.isGoldMine(pos.getX(),pos.getY())) {
                    //System.out.println("goldmine balance=" + ((GoldMine) hubs[pos.getX()][pos.getY()]).getBalance());
                    if (!(this.getBank(Team.B, 0).isFull())) {
                        this.getBank(Team.B, 0).bankCharge(this.getGoldMine(pos.getX(), pos.getY()).getBalance());
                    } else {
                        this.getBank(Team.B, 1).bankCharge(this.getGoldMine(pos.getX(), pos.getY()).getBalance());
                    }
                }
                if(this.isGoldMine(pos.getX(),pos.getY())){
                ((GoldMine) hubs[pos.getX()][pos.getY()]).deCharge();
                ((GoldMine) hubs[pos.getX()][pos.getY()]).deCharge();
                }
            }
        }
    }

    private void goldMineAmountUpdate() {
        for (Position pos : GoldMine.GoldMinePos) {
            if (this.isGoldMine(pos.getX(), pos.getY())) {
                ((GoldMine) hubs[pos.getX()][pos.getY()]).charge();
            }
        }
    }

    private void goldMineLevelUpgrade(int cicle) {
        for (Position pos : GoldMine.GoldMinePos) {
            if (cicle > 6) {
                if (this.isGoldMine(pos.getX(), pos.getY())) {
                    if (((this.getGoldMine(pos.getX(), pos.getY()).getOwner().equals(Team.A) && this.getBank(Team.A, 0).getCharge() >= GoldMine.UPDATE_COST)
                            || (hubs[pos.getX()][pos.getY()].getOwner().equals(Team.B) && this.getBank(Team.B, 0).getCharge() >= GoldMine.UPDATE_COST))
                            && (this.getGoldMine(pos.getX(), pos.getY()).getLevel() == 0)) {
                        if (this.getGoldMine(pos.getX(), pos.getY()).getOwner().equals(Team.A)) {
                            this.getBank(Team.A, 0).buy(GoldMine.UPDATE_COST);
                            (this.getGoldMine(pos.getX(), pos.getY())).upgrade(GoldMine.GOLDMINE_1_A);
                            // System.out.println("A upgrade shodeeeeeeeeeee");
                        }
                        if (this.getGoldMine(pos.getX(), pos.getY()).getOwner().equals(Team.B)) {
                            this.getBank(Team.B, 0).buy(GoldMine.UPDATE_COST);
                            (this.getGoldMine(pos.getX(), pos.getY())).upgrade(GoldMine.GOLDMINE_1_B);
                            //System.out.println("B upgrade shodeeeeeeeeeee");
                        }

                    }
                }
            }
        }
    }

    private void moveSoldiers() {
        this.playerMove(soldier_A);
        this.playerMove(soldier_B);
    }

    public void playerMove(Soldier s) {
        int sol_x, sol_y, sol_new_x, sol_new_y;
        int[] a = {-1, 0, 1};
        Random rnd = new Random();
        int new_x = s.getX() + a[rnd.nextInt(3)];
        int new_y = s.getY() + a[rnd.nextInt(3)];
        sol_x = s.getX();
        sol_y = s.getY();
        sol_new_x = new_x;
        sol_new_y = new_y;
        if (this.isInHubs(new_x, new_y) && this.isMoved(s, new_x, new_y) && s.getHealth()>0) {
            if ((this.isGreen(new_x, new_y))) {
                hubs[new_x][new_y] = hubs[s.getX()][s.getY()];
                hubs[s.getX()][s.getY()] = new Green(s.getOwner());
                s.pos.setLast_x(sol_x);
                s.pos.setLast_y(sol_y);
                s.pos.setX(new_x);
                s.pos.setY(new_y);
                s.setX(new_x);
                s.setY(new_y);
            } else if ((this.isGoldMine(new_x, new_y))) {
                hubs[new_x][new_y].setOwner(s.getOwner());
                if (hubs[new_x][new_y].getOwner().equals(Team.A)) {
                    if (this.getGoldMine(new_x, new_y).getLevel() == 0) {
                        this.getGoldMine(new_x, new_y).upgradePic(GoldMine.GOLDMINE_0_A);
                        this.getGoldMine(new_x, new_y).setTeam(Team.A);
                    }

                    if (this.getGoldMine(new_x, new_y).getLevel() == 1) {
                        this.getGoldMine(new_x, new_y).upgradePic(GoldMine.GOLDMINE_1_A);
                        this.getGoldMine(new_x, new_y).setTeam(Team.A);
                    }
                }
                if (hubs[new_x][new_y].getOwner().equals(Team.B)) {
                    if (this.getGoldMine(new_x, new_y).getLevel() == 0) {
                        this.getGoldMine(new_x, new_y).upgradePic(GoldMine.GOLDMINE_0_B);
                        this.getGoldMine(new_x, new_y).setTeam(Team.B);
                    }
                    if (this.getGoldMine(new_x, new_y).getLevel() == 1) {
                        this.getGoldMine(new_x, new_y).upgradePic(GoldMine.GOLDMINE_1_B);
                        this.getGoldMine(new_x, new_y).setTeam(Team.B);
                    }
                }
            }
        }
    }

    public void bankChecker(Team T, int cicle) {
        if (cicle < 2) {
            return;
        }
        if (this.getKing(T).getBankCounter() == 0 && this.isGreen(this.getSoldier(T).pos.getLast_x(), this.getSoldier(T).pos.getLast_y())) {
            this.getKing(T).setBankPos(this.getSoldier(T).pos.getLast_x(), this.getSoldier(T).pos.getLast_y());
            hubs[this.getSoldier(T).pos.getLast_x()][this.getSoldier(T).pos.getLast_y()] = new Bank(this.getSoldier(T).getOwner(), this.getKing(T).getBankPos(0).getX(), this.getKing(T).getBankPos(0).getY());
        } 
        
        else if (this.getKing(T).getBankCounter() == 1) {
            if (this.getBank(T, 0).getCharge() >= Bank.MAX_CAPACITY && this.isGreen(this.getSoldier(T).pos.getLast_x(), this.getSoldier(T).pos.getLast_y())) {
                    this.getKing(T).setBankPos(this.getSoldier(T).pos.getLast_x(), this.getSoldier(T).pos.getLast_y());
                    hubs[this.getSoldier(T).pos.getLast_x()][this.getSoldier(T).pos.getLast_y()] = new Bank(T, this.getSoldier(T).pos.getLast_x(), this.getSoldier(T).pos.getLast_y());
            }
        }
    }

    public Soldier getSoldier(Team T) {
        if (T == Team.A) {
            return soldier_A;
        } else {
            return soldier_B;
        }
    }

    public King getKing(Team T) {
        if (T == Team.A) {
            return (King) hubs[0][0];
        } else {
            return (King) hubs[9][9];
        }
    }

    public Bank getBank(Team T, int number) {
        int bank_1_X = this.getKing(T).getBankPos(number).getX();
        int bank_1_Y = this.getKing(T).getBankPos(number).getY();
        return ((Bank) hubs[bank_1_X][bank_1_Y]);
    }

    public DefenseTower getDefenseTower(int x, int y) {
        return (DefenseTower) hubs[x][y];
    }

    public int getAllBankAmount(Team T) {
        int allAmount = 0;
        if (this.getKing(T).getBankCounter() > 0) {
            for (int i = 0; i < this.getKing(T).getBankCounter(); i++) {
                allAmount += this.getBank(T, i).getCharge();
            }
        }
        return allAmount;
    }

    public void addTower(Team T) {
        if (this.getKing(T).getBankCounter() == 2) {
            if (this.getBank(T, 1).canBuy(DefenseTower.BUILD_COST)) {
                this.getBank(T, 1).buy(DefenseTower.BUILD_COST);
                hubs[this.getSoldier(T).pos.getLast_x()][this.getSoldier(T).pos.getLast_y()] = new DefenseTower(T, this.getSoldier(T).pos.getLast_x(), this.getSoldier(T).pos.getLast_y());

            }
        }
    }

    public GoldMine getGoldMine(int x, int y) {
        return ((GoldMine) hubs[x][y]);
    }

    public boolean isGoldMine(int x, int y) {
        return (hubs[x][y].getClass().getSimpleName().equals(goldMineChecker.getClass().getSimpleName()));
    }

    public boolean isKing(int x, int y) {
        return (hubs[x][y].getClass().getSimpleName().equals(kingChechker.getClass().getSimpleName()));
    }

    public boolean isBank(int x, int y) {
        return (hubs[x][y].getClass().getSimpleName().equals(bankChecker.getClass().getSimpleName()));
    }

    public boolean isSoldier(int x, int y) {
        return (hubs[x][y].getClass().getSimpleName().equals(soldierChecker.getClass().getSimpleName()));
    }

    public boolean isInHubs(int new_x, int new_y) {
        return ((new_x >= 0 && new_x <= 9) && (new_y >= 0 && new_y <= 9));
    }

    public boolean isGreen(int x, int y) {
        return (hubs[x][y].getClass().getSimpleName().equals(greenChecker.getClass().getSimpleName()));
    }

    public boolean isMoved(Soldier s, int new_x, int new_y) {
        return !(s.getX() == new_x && s.getY() == new_y);
    }

    public boolean isDefenseTower(int x,int y){
         return (hubs[x][y].getClass().getSimpleName().equals(defenseTowerChecker.getClass().getSimpleName()));
    }
    
    public void PS() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (!(this.isGreen(i, j))) {
                    System.out.println("Row = "+i+"\tColumn = "+j+"\tType= " + hubs[i][j].getClass().getSimpleName() +"\thealth = "+hubs[i][j].getHealth() +"\tTeam = "+hubs[i][j].getOwner());
                }
        
                }
            }
        System.out.println("===============================================================");
        
    }

    public void towerAttack() {
        for (Position pos : DefenseTower.towerPosition) {
            if(this.isDefenseTower(pos.getX(), pos.getY()))
                this.getDefenseTower(pos.getX(), pos.getY()).fire(hubs);
        }
    }

    public void mapCleaner() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (hubs[i][j].isDead()) {
                    if (this.isKing(i, j))
                        System.out.println("Game Over! king " + i + " " + j + "is dead!");
                    if (this.isGoldMine(i, j) && this.isDefenseTower(i, j)) 
                        hubs[i][j] = new Green(Team.NO_TEAM);
                    if(this.isBank(i, j))
                        ((Bank) hubs[i][j]).deCharge();
                }
            }
        }
    }
}
