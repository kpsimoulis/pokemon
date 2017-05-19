 import java.util.ArrayList;
 
public class Pokemon extends Card {
	
   private int healthPoints;
   private int damagePoints;  //
   private ArrayList<Energy> energy;
   private String stage;
   private String evolvesFrom;
   
   //Constructor
   
   public Pokemon(String name, int index, String category, int hp, int dp, ArrayList<Energy> energy,String stage, String evolves_from){
	   super(name, index, category);
	   this.healthPoints = hd;
	   this.damagePoints = dp;
	   this.energy = energy;
	   this.stage = stage;
	   this.evolvesFrom = evolves_from;
   }
   
 //Get methods
	
 	public int getHealthPoints (){
 		return healthPoints;
 	}
 	public int getDamagePoints(){
 		return ddamagePoints;
 	}

 	public ArrayList<Energy> getEnergy(){
 		return energy;
 	}
 	
 	public String getStage(){
 		return stage;
 	}
 	
 	public String getEvolvesFrom(){
 		return evolvesFrom;
 	}
 	
 //Set methods
 	
 	public void setHealthPoints(int healthPoints){
 		this.healthPoints = healthPoints;
 	}
 	public void setDamagePoints(int damagePoints){
 		this.damagePoints = damagePoints;
 	}
 	public void setCategory(ArrayList<Energy> energy){
 		this.energy = energy;
 	}
 	public void setStage( String stage){
 		this.stage = stage;
 	}
 	public void setEvolvesFrom( String evolvesFrom){
 		this.evolvesFrom = evolvesFrom;
 	}
   
}
