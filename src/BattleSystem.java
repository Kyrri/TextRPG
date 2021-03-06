import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class BattleSystem extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    private JFrame frame;
    Player p;
    Character m;
    JLabel playerHP;
    JLabel playerAP;
    JLabel playerAtt;
    JButton healItemUses;
    JButton attack;
    JButton hs;
    JButton fb;
    JButton ak;
    JLabel enemyHP;
    JLabel prevMessage1;
    JLabel prevMessage2;
    JLabel prevMessage3;
    JLabel prevMessage4;
    boolean allowClose = false;
    
    PlayerBattleThread playerBattleThread;
    MonsterBattleThread monsterBattleThread;

    public BattleSystem(Player player, Character monster){
        p = player;
        m = monster;
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        
        //Initialize Frame
        frame = new JFrame("Battle Screen");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setTitle("Battle!");
        frame.setLocation(((int)Math.floor(dimension.getWidth()-frame.getWidth())/2), (int)Math.floor((dimension.getHeight() - frame.getHeight())/2));
        
        frame.getContentPane().setBackground(Color.DARK_GRAY);
   
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(allowClose){
                    frame.dispose();
                    Sync.battleFinished();
                }
                else{
                    updateText("You must finish the battle!");
                }
            }
        });
        //Set Labels
        GridBagConstraints c = new GridBagConstraints();
        //Generic Labels/Buttons that don't change
        JLabel label = new JLabel();
        JButton button = new JButton();
        Border paddingBorder = BorderFactory.createEmptyBorder(0,30,0,30);
        Border border = BorderFactory.createMatteBorder(0,0,1,0, Color.BLACK);
        Font labelFont = label.getFont();
        
        //   Labels   //
        //Enemy
        label = new JLabel("Genger");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 15;
        c.weightx = 0.5;
        c.gridwidth=1;
        c.gridx=0;
        c.gridy=0;
        label.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.setFont(new Font(labelFont.getName(), Font.BOLD, 16));
        frame.add(label, c);
        label = new JLabel("ATK:"+m.getAtk());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 0.5;
        c.gridwidth=2;
        c.gridx=1;
        c.gridy=0;
        label.setBorder(border);
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        frame.add(label, c);
        enemyHP = new JLabel("HP:"+m.getHp()+"/"+m.getMaxHp());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weightx = 0.5;
        c.gridwidth=1;
        c.gridx=0;
        c.gridy=1;
        enemyHP.setBorder(paddingBorder);
        enemyHP.setForeground(Color.WHITE);
        frame.add(enemyHP, c);
        //Character Portraits
            String monstersrc;
            if(m instanceof ResistantMonster){
                monstersrc="src/resistantMonster.jpeg";
            }
            else if(m instanceof WaitingMonster){
                monstersrc="src/waitingMonster.jpg";
            }
            else{
                monstersrc="src/Monster.jpeg";
            }
                ImageIcon icon = new ImageIcon(monstersrc);
                JLabel portrait = new JLabel();
                portrait.setIcon(icon);
                c.ipady = 20;
                c.ipadx = 20;
                c.weightx = 0.5;
                c.gridwidth=1;
                c.gridx=0;
                c.gridy=2;
                //BorderFactory.createCompoundBorder(border,paddingBorder)
                portrait.setBorder(paddingBorder);
                frame.add(portrait, c);

                icon = new ImageIcon("src/Player.jpg");
                portrait = new JLabel("",SwingConstants.RIGHT);
                portrait.setIcon(icon);
                c.ipady = 20;
                c.ipadx = 20;
                c.weightx = 0.5;
                c.gridwidth=1;
                c.gridx=2;
                c.gridy=2;
                portrait.setBorder(paddingBorder);
                frame.add(portrait, c);
                
        
        
        //Player
        label = new JLabel("Spieler", SwingConstants.RIGHT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 15;
        c.weightx = 0.5;
        c.gridwidth=1;
        c.gridx=2;
        c.gridy=3;
        label.setFont(new Font(labelFont.getName(), Font.BOLD, 16));
        label.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        frame.add(label, c);
        playerAtt = new JLabel("ATK:"+p.getAtk(),SwingConstants.RIGHT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 0.5;
        c.gridwidth=2;
        c.gridx=0;
        c.gridy=3;
        playerAtt.setBorder(border);
        playerAtt.setOpaque(true);
        playerAtt.setBackground(Color.LIGHT_GRAY);
        frame.add(playerAtt, c);
        playerAP = new JLabel("AP:"+p.getAp()+"/"+p.getMaxAp(), SwingConstants.RIGHT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 0.5;
        c.gridwidth=1;
        c.gridx=1;
        c.gridy=4;
        playerAP.setForeground(Color.WHITE);
        frame.add(playerAP, c);
        playerHP = new JLabel("HP:"+p.getHp()+"/"+p.getMaxHp(), SwingConstants.RIGHT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 0.5;
        c.gridwidth=1;
        c.gridx=2;
        c.gridy=4;
        playerHP.setForeground(Color.WHITE);
        playerHP.setBorder(paddingBorder);
        frame.add(playerHP, c);
        
   //   Buttons   //
        /*
         * Angriff
         */
        
        attack = new JButton("Angriff");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weightx = 0.5;
        c.gridwidth=1;
        c.gridx=0;
        c.gridy=5;
        attack.addActionListener(this);
        attack.setActionCommand("attack");
        attack.setBackground(Color.LIGHT_GRAY);      
        frame.add(attack, c);
 
        /**
         * Heilen
         */
        
        healItemUses = new JButton("Heals - Nutzen:" + p.getRemainingItemUses());
        c.weightx = 0.5;
        c.gridwidth=1;
        c.gridx=1;
        c.gridy=5;
        healItemUses.addActionListener(this);
        healItemUses.setActionCommand("heal");
        healItemUses.setBackground(Color.LIGHT_GRAY);
        healItemUses.setFocusPainted(false);
        frame.add(healItemUses, c);
        
        /**
         * ATK auswürfeln
         */
        
        ak = new JButton("ATK auswürfeln - AP:" + Player.REROLL_COST);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth=1;
        c.gridx=2;
        c.gridy=5;
        ak.addActionListener(this);
        ak.setActionCommand("reRoll");
        ak.setBackground(Color.LIGHT_GRAY);
        
        frame.add(ak, c);
        
        /**
         * Harter Schlag
         */
        hs = new JButton("Harter Schlag - AP:" + Player.HARD_HIT_COST + " / Selbstschaden:" + Player.HARD_HIT_SELF_DAMAGE_PERCENT+"%");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth=2;
        c.gridx=0;
        c.gridy=6;
        hs.addActionListener(this);
        hs.setActionCommand("hardHit");
        hs.setBackground(Color.LIGHT_GRAY);
        
        frame.add(hs, c);
        
        /**
         * Feuerball
         */
        
        fb = new JButton("Feuerball - AP:" + Player.FIREBALL_COST);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth=1;
        c.gridx=2;
        c.gridy=6;
        fb.addActionListener(this);
        fb.setActionCommand("fireBall");
        fb.setBackground(Color.LIGHT_GRAY);
        
        frame.add(fb, c);
        
        //   Combat Log   //
        prevMessage1 = new JLabel("", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weightx = 0.5;
        c.gridwidth=3;
        c.gridx=0;
        c.gridy=7;
        prevMessage1.setForeground(Color.WHITE);
        frame.add(prevMessage1, c);
        prevMessage2 = new JLabel("", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weightx = 0.5;
        c.gridwidth=3;
        c.gridx=0;
        c.gridy=8;
        prevMessage2.setForeground(Color.WHITE);
        frame.add(prevMessage2, c);
        prevMessage3 = new JLabel("", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weightx = 0.5;
        c.gridwidth=3;
        c.gridx=0;
        c.gridy=9;
        prevMessage3.setForeground(Color.WHITE);
        frame.add(prevMessage3, c);
        prevMessage4 = new JLabel("", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weightx = 0.5;
        c.gridwidth=3;
        c.gridx=0;
        c.gridy=10;
        prevMessage4.setForeground(Color.WHITE);
        frame.add(prevMessage4, c);
              
        //Show Frame
        frame.setVisible(true);
        
        playerBattleThread = new PlayerBattleThread(this);
        monsterBattleThread = new MonsterBattleThread(this);
        playerBattleThread.start();
        monsterBattleThread.start();
    }
    public void actionPerformed(ActionEvent arg0) {
        String action = arg0.getActionCommand();
        int playerDamage;
        switch(action){
            case "attack":
                playerDamage = p.attack(m);
                if (playerDamage == -1) {
                    updateText("Spieler verfehlt!");
                } else {
                    updateText("Spieler trifft und macht Schaden!");
                }
                disableButtons();
                break;
            case "heal":
                if (p.heal()) {
                    updateText("Spieler heilt sich!");
                } else {
                    updateText("Nicht genügend Heiltränke!");
                }
                break;
            case "hardHit":
                playerDamage = p.hardHit(m);
                if (playerDamage != -1) {
                    updateText("Spieler schlägt hart zu! Spieler verursacht "+playerDamage+" Schaden!");
                    updateText("Spieler verursacht "+(int) (Player.HARD_HIT_SELF_DAMAGE_PERCENT / 100.0 * playerDamage)+" Selbstschaden!");
                    disableButtons();
                } else {
                    updateText("Nicht genügend AP!");
                }
                break;
            case "fireBall":
                playerDamage = p.fireball(m);
                if (playerDamage != -1) {
                    updateText("Spieler schießt einen Feuerball!%nSpieler verursacht "+playerDamage+" Schaden!");
                    disableButtons();
                } else {
                    updateText("Nicht genügend AP!");
                }
                break;
            case "reRoll":
                if (p.reroll()) {
                    updateText("ATK neu ausgewürfelt!");
                    playerAtt.setText("ATK:"+p.getAtk());
                    disableButtons();
                } else {
                    updateText("Nicht genügend AP!");
                }
                break;
            case "gameOver":
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));        
                System.exit(0);
                break;
            case "continue":
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                return;
            default:
                updateText("Fehlerhafte Aktion!");
        }
        if (p.isDefeated()) {
            System.out.println("Game Over!");
            finishBattle(true);
            return;
        } else if (m.isDefeated()) {
            System.out.println("Spieler gewinnt!");
            p.addMoreGold(m.getGold());
            AVLTree<Item> mInv = m.getInventory();
            int length = mInv.length();
            for (int i = 0; i < length; i++) {
                Item temp = mInv.firstItem();
                p.addToInventory(temp);
                mInv.delete(temp);
            }
            finishBattle(false);
            return;
        }
        this.updateValues();

    }
    public void updateText(String str){
        prevMessage4.setText(prevMessage3.getText());
        prevMessage3.setText(prevMessage2.getText());
        prevMessage2.setText(prevMessage1.getText());
        prevMessage1.setText(str);
    }
    public void updateValues(){
        playerHP.setText("HP:"+p.getHp()+"/"+p.getMaxHp());
        playerAP.setText("AP:"+p.getAp()+"/"+p.getMaxAp());
        enemyHP.setText("HP:"+m.getHp()+"/"+m.getMaxHp());
        healItemUses.setText("Heals - Nutzen:" + p.getRemainingItemUses());
        frame.repaint();
        frame.revalidate();
    }
    private void disableButtons() {
        attack.setEnabled(false);
        fb.setEnabled(false);
        hs.setEnabled(false);
        ak.setEnabled(false);
        updateValues();
        ButtonDelay buttonDelay = new ButtonDelay(this);       
        buttonDelay.start();
    }
    public void enableButtons(){
        attack.setEnabled(true);
        fb.setEnabled(true);
        hs.setEnabled(true);
        ak.setEnabled(true); 
        updateValues();
    }
    public void finishBattle(boolean gameOver){
        playerBattleThread.finish();
        monsterBattleThread.finish();
        JButton end;
        this.frame.getContentPane().removeAll();
        frame.repaint();
        if(gameOver){ 
            end = new JButton("Game Over");
            end.addActionListener(this);
            end.setActionCommand("gameOver");   
        }else{
            end = new JButton("Spieler Gewinnt! Fortsetzen");
            end.addActionListener(this);
            end.setActionCommand("continue");
        }
        allowClose = true;
        frame.add(end);
        frame.revalidate();
    }
}
