package views.card;

import card.Energy;
import main.Attack;
import main.Requirement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class PokemonView extends CardView {

    private String energyTxt;
    private String attackTxt;
    private JTextArea retreatTxt;

    public PokemonView(ArrayList<Energy> energies, ArrayList<Attack> attacks, int dmgPts, int hp, String stage){

        super();
        String[][] cardInfo = new String[][]{
                {"Dmg Pts:", ""},{"# Energ.: ", "0"},{"HP: ", ""}, {"Stage: ", "Basic"}
        };
        DefaultTableModel infoModel = (DefaultTableModel) this.getInfoTable().getModel();
        for (String[] aCardInfo : cardInfo) {
            infoModel.addRow(aCardInfo);
        }
        this.getInfoTable().setModel(infoModel);

        energyTxt = genEnergyStr(energies);
        attackTxt = genAttackStr(attacks);
        setDmgPts(dmgPts);
        setNoEnergies(energies.size());
        setHP(hp);
        setStage(stage);

    }

    public PokemonView(PokemonView view) {

        super();
        String[][] cardInfo = new String[][]{
                {"Dmg Pts:", ""},{"# Energ.: ", "0"},{"HP: ", ""}, {"Stage: ", "Basic"}
        };
        DefaultTableModel infoModel = (DefaultTableModel) this.getInfoTable().getModel();
        for (String[] aCardInfo : cardInfo) {
            infoModel.addRow(aCardInfo);
        }
        this.getInfoTable().setModel(infoModel);

        energyTxt = view.getEnergyTxt();
        attackTxt = view.getAttackTxt();
        setDmgPts(view.getDmgPts());
        setNoEnergies(view.getNoEnergies());
        setHP(view.getHP());
        setStage(view.getStage());

    }

    @Override
    protected String getCardDesc() {

        return "== POKEMON CARD ==\n\n" +
                "Name: " + getCardName() + "\n" +
                "Type: " + getCardType() + "\n" +
                "Damage Pts.: " + getDmgPts() + "\n" +
                "# Energies: " + getNoEnergies() + "\n" +
                "Energies Desc:\n" + getEnergyTxt() + "\n\n" +
                "HP: " + getHP() + "\n" +
                "Stage: " + getStage() + "\n\n" +
                //TODO: "Retreat: " + this.retreat.getCategoryShort()+ " (x" +this.retreat.getEnergyAmount()+ ")\n\n" +
                getAttackTxt();
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

    public void setEnergyTxt(ArrayList<Energy> energies){ this.energyTxt = genEnergyStr(energies); }

    public void setAttackTxt(ArrayList<Attack> attacks){ this.attackTxt = genAttackStr(attacks); }

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

    public String getEnergyTxt() {
        return energyTxt;
    }

    public String getAttackTxt() {
        return attackTxt;
    }

    private String genEnergyStr(ArrayList<Energy> energies){
        StringBuilder energySb = new StringBuilder();
        if (energies.size() > 0) {
            for(Energy item: energies){
                if(energySb.length() > 0){
                    energySb.append(", ");
                }
                energySb.append(item.getCategory());
            }
        }
        return energySb.toString();
    }

    private String genAttackStr(ArrayList<Attack> attacks){
        StringBuilder attack;
        attack = new StringBuilder("Attacks:\n\n");
        for (Attack aAttackInfo : attacks) {
            if (aAttackInfo.getAbility().getAction().equals("dam")) {
                attack.append(aAttackInfo.getAbility().getName()).append(" (Dmg: ").append(aAttackInfo.getAbility().getDamage()).append(")\n");
            }
            else {
                attack.append(aAttackInfo.getAbility().getName()).append(" (Desc: ").append(aAttackInfo.getAbility().getDescription()).append(")\n");
            }
            attack.append("Req: ");
            for (Requirement aRequirement: aAttackInfo.getRequirement()) {
                attack.append(aRequirement.getCategoryShort()).append("(x").append(aRequirement.getEnergyAmount()).append(") ");
            }

            attack.append("\n\n");

        }
        return attack.toString();
    }

}
