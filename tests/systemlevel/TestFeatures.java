package systemlevel;

import main.Main;
import org.assertj.swing.core.ComponentMatcher;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.*;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import views.ChoiceDialog;
import views.card.CardView;
import views.card.EnergyView;
import views.card.PokemonView;
import views.cardcontainer.BenchView;
import views.cardcontainer.HandView;
import views.game.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Objects;

import static java.awt.event.KeyEvent.*;
import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.timing.Pause.pause;
import static org.junit.Assert.*;

/**
 * Created by mikce_000 on 02-Jul-2017.
 */
@FixMethodOrder(MethodSorters.JVM)
public class TestFeatures extends AssertJSwingJUnitTestCase {

    private FrameFixture frame;
    private JPanel cardHolder;

    @Override
    protected void onSetUp() {
        application(Main.class).start();
        frame = findFrame(ChoiceDialog.class).using(robot());
        frame.button(withText("Load Game")).click();
        frame = findFrame(GameView.class).using(robot());
    }

    private void setActive(String pokType) {
        // Get Hand and pokemons
        JScrollPaneFixture hand = frame.panel("pnlPlayerHand").panel(new GenericTypeMatcher<HandView>(HandView.class) {
            @Override
            protected boolean isMatching(HandView hand) {
                return hand.getCardViews().size() == 7;
            }
        }).scrollPane("cardScrolls");

        if (!(hand.target().getViewport().getView() instanceof JPanel)) {
            fail();
        }

        cardHolder = (JPanel) hand.target().getViewport().getView();

        Collection<Component> pokemonCards = getAllPokemonCards(pokType);

        // Get first pokemon
        PokemonView active = (PokemonView) pokemonCards.iterator().next();

        JTableFixture activeFixture = new JTableFixture(robot(), active.getInfoTable());

        // Choose Active Pokemon
        activeFixture.click().pressKey(VK_ENTER);

    }

    @Test
    public void selectActiveBench() {

        setActive("psychic");

        // Select bench pokemon if remaining in hand
        boolean setBench = false;
        String expectingText = "You can now do the following:\n" +
                "1. Add Pokemon to your bench\n" +
                "2. End Turn";
        while (frame.textBox("txtCommand").text().equals(expectingText)) {
            frame.pressKey(VK_1);
            Collection<Component> remainingCards = getAllPokemonCards("");
            JTableFixture nextCard = new JTableFixture(robot(), ((PokemonView) remainingCards.iterator().next()).getInfoTable());
            nextCard.click().pressKey(VK_ENTER);
            setBench = true;
        }

        pause(2000);
        if (setBench) {
            JPanelFixture bench = frame.panel("pnlPlayerBench").panel(new GenericTypeMatcher<BenchView>(BenchView.class) {
                @Override
                protected boolean isMatching(BenchView bench) {
                    return bench.getCardViews().size() > 0;
                }
            });
            pause(2000);
            assertNotNull(bench);
        }
        assertEquals(1, frame.panel("pnlPlayerActive").target().getComponentCount());

    }

    @Test
    public void endTurn() {

        setActive("psychic");

        frame.pressKey(VK_2);

        JPanelFixture bench = frame.panel("pnlPlayerBench").panel(new GenericTypeMatcher<BenchView>(BenchView.class) {
            @Override
            protected boolean isMatching(BenchView bench) {
                return bench.getCardViews().size() == 0;
            }
        });
        assertNotNull(bench);
        assertEquals(1, frame.panel("pnlPlayerActive").target().getComponentCount());

    }

    @Test
    public void attachEnergy() {

        // Choose active and bench and start game
        setActive("psychic");
        String expectingText = "You can now do the following:\n" +
                "1. Add Pokemon to your bench\n" +
                "2. End Turn";
        while (frame.textBox("txtCommand").text().equals(expectingText)) {
            frame.pressKey(VK_1);
            Collection<Component> remainingCards = getAllPokemonCards("");
            JTableFixture nextCard = new JTableFixture(robot(), ((PokemonView) remainingCards.iterator().next()).getInfoTable());
            nextCard.click().pressKey(VK_ENTER);
        }

        //Press Energy Option
        frame.pressKey(VK_ENTER);
        pause(2000);
        if (frame.textBox("txtCommand").text().contains("E. Add Energy to a pokemon")) {

            frame.pressKey(VK_E);
            Component component = robot().finder().findByType(frame.panel("pnlPlayerActive").target(), PokemonView.class);
            JTableFixture activePok = new JTableFixture(robot(), ((PokemonView) component).getInfoTable());

            // Pick active pokemon
            activePok.click().pressKey(VK_ENTER);

            pause(1000);

            // Testing if clicking on other cards trigger event
            Collection<Component> possibleCards = robot().finder().findAll(cardHolder, card -> card instanceof CardView && !(card instanceof EnergyView));

            JTableFixture cardTest = new JTableFixture(robot(), ((CardView) possibleCards.iterator().next()).getInfoTable());
            cardTest.click().pressKey(VK_ENTER);

            pause(1000);
            assertEquals(0, ((PokemonView) component).getNoEnergies());

            // Testing adding correct energy
            Collection<Component> energiesInHand = robot().finder().findAll(cardHolder, energy -> energy instanceof EnergyView);

            JTableFixture energyChosen = new JTableFixture(robot(), ((EnergyView) energiesInHand.iterator().next()).getInfoTable());
            energyChosen.click().pressKey(VK_ENTER);

            activePok.click();

            pause(2000);

            assertEquals(1, ((PokemonView) component).getNoEnergies());

        }

    }

    @Test
    public void pokAttack() {

        setActive("fight");
        frame.pressKey(VK_2);

        frame.pressKey(VK_ENTER);

        JTextComponentFixture cmdPanel = frame.textBox("txtCommand");

        pause(2000);

        frame.pressKey(VK_E);

        Component component = robot().finder().findByType(frame.panel("pnlPlayerActive").target(), PokemonView.class);
        JTableFixture activePok = new JTableFixture(robot(), ((PokemonView) component).getInfoTable());

        // Pick active pokemon
        activePok.click().pressKey(VK_ENTER);

        Collection<Component> energiesInHand = robot().finder().findAll(cardHolder, energy -> energy instanceof EnergyView && Objects.equals(((EnergyView) energy).getCardType(), ((PokemonView) component).getCardType()));
        pause(1000);
        // Choose Energy
        JTableFixture energyChosen = new JTableFixture(robot(), ((EnergyView) energiesInHand.iterator().next()).getInfoTable());
        energyChosen.click().pressKey(VK_ENTER);

        pause(2000);

        if (cmdPanel.text().contains("A. Attack with Active Pokemon")){

            frame.pressKey(VK_A);

            int attackIdx = 1;
            while (cmdPanel.text().contains("Press the corresponding number for the attacks:")){
                frame.pressKey(0x30 + attackIdx);
            }

            pause(2000);
            Component oppActivePok = robot().finder().findByType(frame.panel("pnlOppActive").target(), PokemonView.class);

            assertTrue(((PokemonView) oppActivePok).getDmgPts() > 0);
        }

        //assertTrue(true);

    }

    private Collection<Component> getAllPokemonCards(String pokType) {
        return robot().finder().findAll(cardHolder, new ComponentMatcher() {
            @Override
            public boolean matches(Component pokemon) {
                if (pokType.equals(""))
                    return pokemon instanceof PokemonView;
                else
                    return pokemon instanceof PokemonView && ((PokemonView) pokemon).getCardType().equals(pokType);
            }
        });
    }

}
