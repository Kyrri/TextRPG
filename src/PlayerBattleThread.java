
public class PlayerBattleThread extends Thread{
    boolean battleActive;
    BattleSystem b;
    
    public PlayerBattleThread(BattleSystem battle){
        battleActive = true;
        b = battle;
    }   
    public void run(){
        int failurecount = 0;
        boolean successfulWait;
        while(battleActive){
            successfulWait = true;
            try{
                Thread.sleep(5000);
            }catch(Exception e){    
                System.out.println("Error, will retry");
                failurecount++;
                successfulWait = false;
                if(failurecount>=5){
                    battleActive=false;
                    b.updateText("Error with active battle system");
                    b.finishBattle(false);
                }
            }
            if(successfulWait){
                b.p.regenerateAp();
                b.updateValues();
            }
        }
        
     }
    public void finish(){
        battleActive = false;
    }
}
