package views.cardcontainer;

public class HandView extends ContainerView {

    public HandView(){
        super(7);
    }

    public void removeAllCardViews(){
        getPanel().removeAll();
        getPanel().revalidate();
    }

}
