package views.card;

import javax.swing.table.DefaultTableModel;

/**
 * Created by edwinyachoui on 2017-05-30.
 */
public class TrainerView extends CardView {

    public TrainerView() {

        super();
        String[][] cardInfo = new String[][]{
                {"Ability", ""}, {"Textbox: ", ""}
        };
        DefaultTableModel infoModel = (DefaultTableModel) this.getInfoTable().getModel();
        for (String[] aCardInfo : cardInfo) {
            infoModel.addRow(aCardInfo);
        }
        this.getInfoTable().setModel(infoModel);
    }



    public String getAbility(){
        return (String) this.getInfoTable().getModel().getValueAt(1, 1);
    }

    public String getTextbox(){
        return (String) this.getInfoTable().getModel().getValueAt(2, 1);
    }

}