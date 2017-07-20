package systemlevel;

import comp354.Main;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import views.ChoiceDialog;
import views.game.GameView;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

/**
 * Created by mikce_000 on 02-Jul-2017.
 */
public class StartGameTest extends AssertJSwingJUnitTestCase {

    private FrameFixture frame;

    @Override
    protected void onSetUp() {
        application(Main.class).start();
        frame = findFrame(ChoiceDialog.class).using(robot());
    }

    @Test
    public void checkStartGame(){
        frame.show();
        frame.button(withText("Load Game")).click();

        frame = findFrame(GameView.class).using(robot());

        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        frame.textBox("txtCommand").requireText("Choose Active Pokemon (Click on a pokemon and hit enter)");
    }


}
