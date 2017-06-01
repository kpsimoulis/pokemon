package card;

public class Card {


    String name;
    private int index;
    private String category;


    public Card(String name, int index, String category) {
        this.name = name;
        this.index = index;
        this.category = category;
    }


    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public String getCategory() {
        return category;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean equals(Card anotherCard) {

        return this.name.equals(anotherCard.name) && this.index == anotherCard.index && this.category.equals(anotherCard.category);

    }

}
