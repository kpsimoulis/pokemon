package views.card;

import javax.swing.table.DefaultTableModel;

/**
 * Created by edwinyachoui on 2017-05-30.
 */
public class TrainerView extends CardView {

    public TrainerView() {

        super();
        String[][] cardInfo = new String[][]{
                {"Ability", ""}, {"Desc.: ", ""}
        };
        DefaultTableModel infoModel = (DefaultTableModel) this.getInfoTable().getModel();
        for (String[] aCardInfo : cardInfo) {
            infoModel.addRow(aCardInfo);
        }
        this.getInfoTable().setModel(infoModel);
    }

    @Override
    protected String getCardDesc() {
        return "=== TRAINER CARD ===\n\n" +
                "Name: " + getCardName() + "\n" +
                "Type: " + getCardType() + "\n" +
                "Ability: " + getAbility() + "\n" +
                "Description: " + getDescription() + "\n";
    }


    public String getAbility(){
        return (String) this.getInfoTable().getModel().getValueAt(2, 1);
    }

    public String getDescription() {
        return (String) this.getInfoTable().getModel().getValueAt(3, 1);
    }

    public void setAbility(String name){
        this.getInfoTable().getModel().setValueAt(name, 2, 1);
    }

    public void setDescription(String description){
        this.getInfoTable().getModel().setValueAt(description, 3, 1);
    }


}