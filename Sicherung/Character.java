/** The type Character. */
public class Character {

	private int				gold;
	/** The Max hp. */
	private int				maxHp;
	/** The Hp. */
	private int				hp;
	/** The Atk. */
	private int				atk;
	/** The Hit chance. */
	private double			hitChance;
	private int 			ratio;
	private Inventory		inventory;
	
	private Item currentItem;
	private String name;

	/** The constant ATTACK_NORMAL. */
	public static final int	ATTACK_NORMAL	= 0;
	/** The constant ATTACK_SPECIAL. */
	public static final int	ATTACK_SPECIAL	= 1;

	/** Instantiates a new Character.
	 *
	 * @param maxHp the max hp
	 * @param atk the atk
	 * @param hitChance the hit chance */
	public Character(int gold, int maxHp, int atk, double hitChance) {
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.atk = atk;
		this.hitChance = hitChance;
		this.gold = gold;
		this.currentItem = null;
		this.inventory = new Inventory();
	}
	public Character(String name, int gold, int ratio){
		this.setName(name);
		this.gold = gold;
		this.ratio = ratio;
		this.inventory = new Inventory();
		
	}
	
	public int getGold() { // THIS IS NEWLY ADDED SO DON'T RE-ADD IT
		return this.gold;
	}

	/** Gets hit chance.
	 *
	 * @return the hit chance */
	public double getHitChance() {
		return hitChance;
	}

	/** Sets hit chance.
	 *
	 * @param hitChance the hit chance */
	public void setHitChance(double hitChance) {
		if (hitChance >= 0 && hitChance <= 1) {
			this.hitChance = hitChance;
		}
	}

	/** Gets hp.
	 *
	 * @return the hp */
	public int getHp() {
		return hp;
	}

	/** Sets hp.
	 *
	 * @param hp the hp */
	public void setHp(int hp) {
		if (hp > maxHp) {
			this.hp = maxHp;
		} else if (hp < 0) {
			this.hp = 0;
		} else {
			this.hp = hp;
		}
	}

	/** Gets max hp.
	 *
	 * @return the max hp */
	public int getMaxHp() {
		return maxHp;
	}

	/** Gets atk.
	 *
	 * @return the atk */
	public int getAtk() {
		return atk;
	}

	/** Sets atk.
	 *
	 * @param atk the atk */
	public void setAtk(int atk) {
		this.atk = atk;
	}

	/** Take damage.
	 *
	 * @param damage the damage
	 * @return the int */
	public int takeDamage(int damage) {
		return takeDamage(damage, ATTACK_NORMAL);
	}

	/** Take damage.
	 *
	 * @param damage the damage
	 * @param attackType the attack type
	 * @return the damage */
	public int takeDamage(int damage, int attackType) {
		setHp(getHp() - damage);
		return damage;
	}

	/** Is defeated.
	 *
	 * @return true, wenn man besiegt ist */
	public boolean isDefeated() {
		return getHp() == 0;
	}

	/** Attack int.
	 *
	 * @param c the enemy
	 * @return -1, für Verfehlt, sonst den angerichteten Schaden */
	public int attack(Character c) {
		if (Math.random() <= hitChance) {
			int damage = (int) (atk * (Math.random() + 1.0));
			return c.takeDamage(damage);
		} else {
			return -1;
		}
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void addToInventory(Item i) {
		inventory.append(i);
	}
	
	public Item getItem(String name) {
		for (int i = 0; i < inventory.length(); i++) {
			Item it = inventory.getItem(i);
			if (it.getName().equals(name)) { return it; }
		}
		return null;
	}
	
	public Item getItem(int number) {
		for (int i = 0; i < inventory.length(); i++) {
			Item it = inventory.getItem(i);
			if (number == i) { return it; }
		}
		return null;
	}
	
	public int getVerkaufspreis(int number){
		for (int i = 0; i < inventory.length(); i++) {
			Item it = inventory.getItem(i);
			if (number == i) { 
				int preis = it.getVPreis();
				return preis; 
			}
		}
		return 0;
	}
	
	public int getAnkaufspreis(int number){
		for (int i = 0; i < inventory.length(); i++) {
			Item it = inventory.getItem(i);
			if (number == i) { 
				int preis = it.getAPreis();
				return preis; 
			}
		}
		return 0;
	}
	
	public void deleteItem(int number){
		for (int i = 0; i < inventory.length(); i++) {
			Item it = inventory.getItem(i);
			
			if (number == i) {
				//System.out.println("Hier bin ich!!");
				inventory.delete(it);
				//System.out.println("Hier bin ich auch noch!!");
			}
		}
			
	}
	
	public void addItem(Item it){
		inventory.append(it);
	}
	
	public void addMoreGold(int amount) {
		gold += amount;
	}
	
	public void deductGold(int amount) {
		gold -= amount;
	}
	
	
	public void setCurrentItem(Item i) {
		currentItem = i;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
