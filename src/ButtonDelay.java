
public class ButtonDelay extends Thread{
    BattleSystem b;
    
    public ButtonDelay(BattleSystem battle){
        b = battle;
    }   
    public void run(){
        boolean successfulWait = true;

            try{
                Thread.sleep(5000);
            }catch(Exception e){    
                System.out.println("Error occured");
                successfulWait = false;
                b.updateText("Error with active battle system");
                b.finishBattle(false);
            }
            if(successfulWait){
                b.enableButtons();
            }
        
     }
}
