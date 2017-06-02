package views.card;

import card.Energy;
import main.Attack;
import main.Requirement;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class PokemonView extends CardView {

    private ArrayList<Attack> attacks;
    private ArrayList<Energy> energies;

    public PokemonView(){

        super();
        String[][] cardInfo = new String[][]{
                {"Dmg Pts:", ""},{"# Energ.: ", "0"},{"HP: ", ""}, {"Stage: ", "Basic"}
        };
        DefaultTableModel infoModel = (DefaultTableModel) this.getInfoTable().getModel();
        for (String[] aCardInfo : cardInfo) {
            infoModel.addRow(aCardInfo);
        }
        this.getInfoTable().setModel(infoModel);

    }

    @Override
    protected String getCardDesc() {

        String attack;
        attack = "Attacks:\n\n";
        for (Attack aAttackInfo : this.attacks) {
            if (aAttackInfo.getAbility().getAction().equals("dam")) {
                attack += aAttackInfo.getAbility().getName() + " (Dmg: " + aAttackInfo.getAbility().getDamage()+ ")\n";
            }
            else {
                attack += aAttackInfo.getAbility().getName() + " (Desc: " + aAttackInfo.getAbility().getDescription()+ ")\n";
            }
            attack += "Req: ";
            for (Requirement aRequirement: aAttackInfo.getRequirement()) {
                attack += aRequirement.getCategoryShort() + "(x" + aRequirement.getEnergyAmount() + ") ";
            }

            attack += "\n\n";

        }

        StringBuilder energySb = new StringBuilder();
        if (this.energies.size() > 0) {
            for(Energy item: this.energies){
                if(energySb.length() > 0){
                    energySb.append(',');
                }
                energySb.append(item.getCategory());
            }
        }

        return "=== POKEMON CARD ===\n\n" +
                "Name: " + getCardName() + "\n" +
                "Type: " + getCardType() + "\n" +
                "Damage Pts.: " + getDmgPts() + "\n" +
                "# Energies: " + getNoEnergies() + "\n" +
                energySb.toString() + "\n" +
                "HP: " + getHP() + "\n" +
                "Stage: " + getStage() + "\n\n" +
                attack;
    }

    public void addAttack(int idx, String name) {
        DefaultTableModel infoModel = (DefaultTableModel) this.getInfoTable().getModel();
        String[] cardInfo = new String[]{"Attack:", name};
        infoModel.addRow(cardInfo);
    }

    public void setDmgPts(int dmgPts){
        this.getInfoTable().getModel().setValueAt(String.valueOf(dmgPts),2, 1);
    }

    public void setNoEnergies(int noEnergies){
        this.getInfoTable().getModel().setValueAt(String.valueOf(noEnergies), 3, 1);
    }

    public void setHP(int newHP){
        this.getInfoTable().getModel().setValueAt(String.valueOf(newHP), 4, 1);
    }

    public void setStage(String pokStage){
        this.getInfoTable().getModel().setValueAt(pokStage, 5, 1);
    }

    public int getDmgPts(){
        return Integer.valueOf( this.getInfoTable().getModel().getValueAt(2, 1).toString());
    }

    public int getNoEnergies(){
        return Integer.valueOf( this.getInfoTable().getModel().getValueAt(3, 1).toString());
    }

    public int getHP(){

        return Integer.valueOf( this.getInfoTable().getModel().getValueAt(4, 1).toString());
    }

    public String getStage(){
        return (String) this.getInfoTable().getModel().getValueAt(5, 1);
    }

    public void setAttacks(ArrayList<Attack> attacks){
        this.attacks = new ArrayList<>(attacks);
    }

    public void setEnergies(ArrayList<Energy> energies) {
        this.energies = new ArrayList<>(energies);
    }

    public void addAbilityBtns(){



    }

}
