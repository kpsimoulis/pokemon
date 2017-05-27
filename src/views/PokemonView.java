package views;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class PokemonView extends CardView {

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

}