package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardsFileParser {

    private List<String> itemList;

    private String name;
    private String category;
    private String stage;
    private String evolvesFrom;
    private String cardType;
    private int healthPoints;
    private int abilityLineNum;
    private Retreat retreat;
    private ArrayList<Attack> attack;

    public ArrayList<Attack> getAttack() {
        return attack;
    }

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

    public CardsFileParser(String[] items) {
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
            this.retreat = new Retreat("colorless",0);
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
            String abilityLine;
            AbilitiesFileParser abilitiesParser;
            Ability tmpAbility;

            for (int i = 0; i < attackItems.length; i++)
            {
                String[] attackVariables = attackItems[i].split(":");
                if (attackVariables.length == 4) {
                    requirement.add(new Requirement(attackVariables[1], Integer.parseInt(attackVariables[2])));
//                    System.out.println(this.getName()+attackVariables[2]+attackVariables[1]+attackVariables[3]);
                    try {
                        abilityLine = Files.readAllLines(Paths.get("src/main/resources/deck/abilities.txt")).get(Integer.parseInt(attackVariables[3]) - 1);
                        String[] abilityLineVariables = abilityLine.split(":");

                        abilitiesParser = new AbilitiesFileParser(abilityLineVariables);
                        abilitiesParser.parseName();
//                        abilitiesParser.parseAction();
                        abilitiesParser.parseDescription();
                        abilitiesParser.parseLogic();
//                        System.out.println("ACTION: " + abilitiesParser.getAction() + ", LOGIC: " + abilitiesParser.getLogic());
//                        abilitiesParser.print();
                        tmpAbility = new Ability(abilitiesParser.getName(), abilitiesParser.getDescription(), abilitiesParser.getLogic());

                        attack.add(new Attack(requirement, tmpAbility));
                        // Reset Requirement Arraylist
                        requirement = new ArrayList<Requirement>();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else if (attackVariables.length == 3) {
                    requirement.add(new Requirement(attackVariables[1], Integer.parseInt(attackVariables[2])));
//                    System.out.println(this.getName()+attackVariables[2]+attackVariables[1]);

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

    /**
     * print remaining and unprocessed itemList
     */
    public void print(){
        System.out.println(String.join(":", itemList));
    }

}
