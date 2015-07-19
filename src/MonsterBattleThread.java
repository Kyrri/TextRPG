
public class MonsterBattleThread extends Thread{
    boolean battleActive;
    BattleSystem b;
    
    public MonsterBattleThread(BattleSystem battle){
        battleActive = true;
        b = battle;
    }   
    public void run(){
        int failurecount = 0;
        int monsterDamage;
        boolean successfulWait;
        while(battleActive){
            successfulWait = true;
            try{
                Thread.sleep(5000);
            }catch(Exception e){    
                System.out.println("Error: will retry");
                failurecount++;
                successfulWait = false;
                if(failurecount>=5){
                    battleActive=false;
                    b.updateText("Error with active battle system");
                    b.finishBattle(false);
                }
            }
            if(successfulWait){
                b.updateText("Monster greift an!");
                monsterDamage = b.m.attack(b.p);
                if (monsterDamage == -1) {
                   b.updateText("Monster verfehlt!");
                } else if (monsterDamage == -2) {
                    b.updateText("Monster tut nichts.");
                } else {
                    b.updateText("Monster trifft und macht "+monsterDamage+" Schaden!");
                }
                if (b.p.isDefeated()) {
                    System.out.println("Game Over!");
                    b.finishBattle(true);
                }
            }
        }
        
     }
    public void finish(){
        battleActive = false;
    }
}
