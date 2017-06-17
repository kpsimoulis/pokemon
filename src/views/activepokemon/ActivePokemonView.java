package views.activepokemon;

import views.card.PokemonView;

import javax.swing.*;
import java.awt.*;

public class ActivePokemonView extends JPanel {

    private PokemonView pokemonView;

    public ActivePokemonView(){

        this.setPreferredSize(new Dimension(150,160));
        pokemonView = null;

    }

    public ActivePokemonView(PokemonView pokemonView){

        this.setPreferredSize(new Dimension(150,160));
        this.pokemonView = pokemonView;
        add(pokemonView);
        revalidate();

    }

    public PokemonView getPokemonView() {
        return pokemonView;
    }

    public void setPokemonView(PokemonView pokemonView) {
        if (pokemonView != null) {
            this.pokemonView = pokemonView;
            //TODO: Enable button on pokemon card
            add(pokemonView);
        }
    }

    public void removePokemonView(){
        remove(pokemonView);
        revalidate();
        this.pokemonView = null;
    }

    public void zoomPokemon(){
        //TODO: Zoom on click
    }
}
