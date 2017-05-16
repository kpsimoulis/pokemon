
public class Trainer extends Cards {

	private String textbox;
	private enum Ability{one, two, three, four, etc};
	private Ability ability;
	
  //Constructor
	
	public Trainer (String name, int index,String category,String textbox, Ability ability){
		super(name, index, category);
		this.textbox = textbox;
		this.ability = ability;
	}
	
 // Get Methods
	
	public String getTextbox(){
		return textbox;
	}
	
	public Ability getAbility(){
		return ability;
	}
	
//Set Methods
	
	public void setTextbox(String textbox){
		this.textbox = textbox;
	}
	
	public void setAbility(Ability ability){
		this.ability = ability;
	}
	
 //applyRule Method
	
	public void applyRule(Trainer obj){
		
	}
		
	
}
