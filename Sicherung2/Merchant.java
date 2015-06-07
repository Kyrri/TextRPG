
public class Merchant extends Character{
	
	private static final int	initialMaxInventory	= 200;
	private String name;
	private int ratio;
	
	
	

	public Merchant(String name, int gold, int ratio){
		super(name,  gold, ratio);
		//System.out.println("bla: "+ratio);
		for (int i = 0; i < ((int) (((double) initialMaxInventory) * Math.random())); i++) {
			addToInventory(new Item(ratio));
		}
		
	}

	public void setName(String name){
		this.name=name;
	}


	public String getName() {
		return name;
	}

	public void setRatio(int ratio){
		this.ratio = ratio;
	}
	
	public int getRatio() {
		return ratio;
	}
	//Bla
}
