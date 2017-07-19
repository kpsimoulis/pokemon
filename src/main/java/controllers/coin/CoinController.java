package controllers.coin;

import player.Coin;
import views.coin.CoinView;

public class CoinController {

    private Coin coin;
    private CoinView view;

    public CoinController(Coin coinObj, CoinView viewObj){

        coin = coinObj;
        view = viewObj;

    }

    public Coin getCoin() {
        return coin;
    }

    public CoinView getView() {
        return view;
    }

    /**
     * Head = 1, Tails = 2
     * @return
     */
    public int flipCoin(){
        coin.flip();
        if (coin.isHead()){
            view.showHead();
            return 1;
        }else{
            view.showTail();
            return 2;
        }
    }

}
