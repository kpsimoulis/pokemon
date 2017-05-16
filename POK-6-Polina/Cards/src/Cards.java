
public class Cards {
	
//class variables
	private String name;
	private int index;
	private String category;
	
//Constructor
	
	public Cards(String name, int index, String category ){
		this.name = name;
		this.index = index;
		this.category = category;
	}
	
//Get methods
	
	public String getName (){
		return name;
	}
	public int getIndex(){
		return index;
	}

	public String getCategory(){
		return category;
	}
	
//Set methods
	
	public void setName(String name){
		this.name = name;
	}
	public void setIndex(int index){
		this.index = index;
	}
	public void setCategory(String category){
		this.category = category;
	}
}
