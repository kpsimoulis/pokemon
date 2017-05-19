
public class Trainer extends Card {

	private String textbox;
	private enum Ability{ONE, TWO, THREE, FOUR, ETC};
	private Ability ability;
	

	
	public Trainer (String name, int index,String category,String textbox, Ability ability){
		super(name, index, category);
		this.textbox = textbox;
		this.ability = ability;
	}
	

	
	public String getTextbox(){
		return textbox;
	}
	
	public Ability getAbility(){
		return ability;
	}
	

	
	public void setTextbox(String textbox){
		this.textbox = textbox;
	}
	
	public void setAbility(Ability ability){
		this.ability = ability;
	}
	

	
	public void applyRule(Trainer obj){
		
	}
		
	
}
