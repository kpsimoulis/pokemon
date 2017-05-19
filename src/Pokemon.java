 import java.util.ArrayList;
 
public class Pokemon extends Card {
	
   private int hd;  //health points
   private int dp;  //damage points
   private ArrayList<Energy> energy;
   private String stage;
   private String evolves_from;
   
   //Constructor
   
   public Pokemon(String name, int index,String category, int hd, int dp, ArrayList<Energy> energy,String stage, String evolves_from){
	   super(name, index, category);
	   this.hd = hd;
	   this.dp = dp;
	   this.energy = energy;
	   this.stage = stage;
	   this.evolves_from = evolves_from;
   }
   
 //Get methods
	
 	public int getHD (){
 		return hd;
 	}
 	public int getDP(){
 		return dp;
 	}

 	public ArrayList<Energy> getEnergy(){
 		return energy;
 	}
 	
 	public String getStage(){
 		return stage;
 	}
 	
 	public String getEvolves_From(){
 		return evolves_from;
 	}
 	
 //Set methods
 	
 	public void setHD(int hd){
 		this.hd = hd;
 	}
 	public void setIndex(int dp){
 		this.dp = dp;
 	}
 	public void setCategory(ArrayList<Energy> energy){
 		this.energy = energy;
 	}
 	public void setStage( String stage){
 		this.stage = stage;
 	}
 	public void setEvolves_From( String evolves_from){
 		this.evolves_from = evolves_from;
 	}
   
}
