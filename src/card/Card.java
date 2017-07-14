package card;

public class Card {


    private String name;
    private int index;
    private String category;


    public Card(String name, int index, String category) {
        this.name = name;
        this.index = index;
        this.category = category;
    }

    public Card(Card card) {
        this.name = card.name;
        this.index = card.index;
        this.category = card.category;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (name != null ? !name.equals(card.name) : card.name != null) return false;
        return category != null ? category.equals(card.category) : card.category == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
