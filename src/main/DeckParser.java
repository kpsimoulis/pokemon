package main;

import card.Card;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeckParser {

    private static final String CARDS_FILE = "res/deck/cards.txt";
    private static final String ABILITIES_FILE = "res/deck/abilities.txt";

    private String fileName;

    private String name;
    private String category;
    private String stage;
    private String evolvesFrom;
    private String cardType;
    private int healthPoints;
    private int abilityLineNum;
    private Retreat retreat;

    public ArrayList<Attack> getAttack() {
        return attack;
    }

    private ArrayList<Attack> attack;
    private List<String> itemList;
    private Card tmpCard;


    public Retreat getRetreat() {
        return retreat;
    }

    public String getName() {
        return name;
    }

    public int getAbilityLineNum() {
        return abilityLineNum;
    }

    public String getCategory() {
        return category;
    }

    public String getStage() {
        return stage;
    }

    public String getEvolvesFrom() {
        return evolvesFrom;
    }


    public String getCardType() {
        return cardType;
    }


    public int getHealthPoints() {
        return healthPoints;
    }

    public DeckParser(String[] items) {
        this.itemList = new ArrayList<String>(Arrays.asList(items));
    }

    public void parseName() {
        this.name = itemList.get(0);
        itemList.remove(0);
    }

    public void parseCardType() {
        this.cardType = itemList.get(0);
        itemList.remove(0);
    }

    public void parseStage() {
        if (!this.itemList.get(0).equals("cat")) {
            throw new IllegalArgumentException("Expecting word 'cat'");
        }
        itemList.remove(0);
        this.stage = itemList.get(0);
        itemList.remove(0);
    }

    public void parseEvolvesFrom() {
        this.evolvesFrom = itemList.get(0);
        itemList.remove(0);
    }

    public void parseCategory() {
        if (!this.itemList.get(0).equals("cat")) {
            throw new IllegalArgumentException("Expecting word 'cat'");
        }
        itemList.remove(0);
        this.category = itemList.get(0);
        itemList.remove(0);
    }

    public void parseHealthPoints() {
        this.healthPoints = Integer.parseInt(itemList.get(0));
        itemList.remove(0);
    }

    public void parseAbilityLineNum() {
        this.abilityLineNum = Integer.parseInt(itemList.get(0));
        itemList.remove(0);
    }

    public void parseRetreat() {
        if (this.itemList.get(0).equals("attacks")) {
            return;
        }
        else if (this.itemList.get(0).equals("retreat") && this.itemList.get(1).equals("cat")) {
            itemList.remove(0);
            itemList.remove(0);
            this.retreat = new Retreat(itemList.get(0), Integer.parseInt(itemList.get(1)));
            itemList.remove(0);
            itemList.remove(0);
        }
        else {
            throw new IllegalArgumentException("Expecting word 'retreat' or 'attacks' followed by 'cat'");
        }
    }

    public void parseAttacks() {
        if (this.itemList.get(0).equals("attacks") && this.itemList.get(1).equals("cat")) {
            itemList.remove(0);
            String attackLine = String.join(":", itemList);
            String[] attackItems = attackLine.split(",");
            this.attack = new ArrayList<Attack>();
            ArrayList<Requirement> requirement = new ArrayList<Requirement>();

            for (int i = 0; i < attackItems.length; i++)
            {
                String[] attackVariables = attackItems[i].split(":");
                if (attackVariables.length == 4) {
                    requirement.add(new Requirement(attackVariables[1], Integer.parseInt(attackVariables[2])));
                    attack.add(new Attack(requirement, Integer.parseInt(attackVariables[3])));
                    // Reset Requirement Arraylist
                    requirement = new ArrayList<Requirement>();
                }
                else if (attackVariables.length == 3) {
                    requirement.add(new Requirement(attackVariables[1], Integer.parseInt(attackVariables[2])));
                }
                else {
                    throw new IllegalArgumentException("Attack should contain 3 or 4 items");
                }
            }

        }
        else {
            throw new IllegalArgumentException("Expecting 'attacks' followed by 'cat'");
        }
    }

    public void print(){
        System.out.println(String.join(":", itemList));
    }


}
