package unitTest;

import org.junit.Test;
import views.ChoiceDialog;

import static org.junit.Assert.assertEquals;

/**
 * Created by luckyfang0601 on 2017-06-01.
 */
public class ChoiceDialogTest {
    private ChoiceDialog choiceDialog;
    @Test
    public void findFilesinDir() throws Exception {
        choiceDialog = new ChoiceDialog();
        assertEquals("deck1.txt",choiceDialog.findFilesinDir()[0]);
        assertEquals("deck2.txt",choiceDialog.findFilesinDir()[1]);

    }

}