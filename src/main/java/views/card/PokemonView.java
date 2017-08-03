package views.card;

import ability.*;
import card.Energy;
import parser.Amount;
import parser.Attack;
import parser.Requirement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class PokemonView extends CardView {

    private String energyTxt;
    private String attackTxt;
    private JTextArea retreatTxt;

    public PokemonView(ArrayList<Energy> energies, ArrayList<Attack> attacks, int dmgPts, int hp, String stage, int retreatRequirement){

        super();
        String[][] cardInfo = new String[][]{
                {"Dmg Pts:", ""},{"# Energ.: ", "0"},{"HP: ", ""}, {"Stage: ", "Basic"}
                ,{"Retreat Req: ", ""}
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
        setRetreatRequirement(retreatRequirement);

    }



    public PokemonView(PokemonView view) {

        super();
        String[][] cardInfo = new String[][]{
                {"Dmg Pts:", ""},{"# Energ.: ", "0"},{"HP: ", ""}, {"Stage: ", "Basic"}
                ,{"Retreat Req:", ""}
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
        setRetreatRequirement(view.getRetreatRequirement());


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
                "Stage: " + getStage() + "\n" +
                //TODO: "Retreat: " + this.retreat.getCategoryShort()+ " (x" +this.retreat.getEnergyAmount()+ ")\n\n" +
                "Retreat: Colorless "+ getRetreatRequirement() + "\n\n" +
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

    private void setRetreatRequirement(int retreatRequirment) {this.getInfoTable().getModel().setValueAt(String.valueOf(retreatRequirment), 6, 1);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    private int getRetreatRequirement() {return  Integer.valueOf( this.getInfoTable().getModel().getValueAt(6, 1).toString());
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
            boolean attackHasAmount = false;
            String  dmg = "";
            Amount amount = new Amount();

            if (aAttackInfo.getAbility().getLogic().get(0) instanceof Dam && aAttackInfo.getAbility().getLogic().size() == 1) {
                attackHasAmount = true;
                amount = ((Dam) aAttackInfo.getAbility().getLogic().get(0)).getAmount();
            }
            if (attackHasAmount && !amount.isCalculated()) {
                dmg = Integer.toString(amount.getAmount());
                attack.append(aAttackInfo.getAbility().getName()).append(" (" + aAttackInfo.getAbility().getLogic().get(0).getType()+ ": ").append(dmg).append(")\n");
            }
            else if (aAttackInfo.getAbility().getLogic().get(0) instanceof Reenergize) {
                dmg =  Integer.toString(((Reenergize) aAttackInfo.getAbility().getLogic().get(0)).getSrcAmount()) + " -> " + Integer.toString(((Reenergize) aAttackInfo.getAbility().getLogic().get(0)).getDestAmount());
                attack.append(aAttackInfo.getAbility().getName()).append(" (Energize: ").append(dmg).append(")\n");
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