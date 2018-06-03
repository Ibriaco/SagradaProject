package it.polimi.se2018.Model;
/**Window Card Association class of the game. Rappresents two coupled Window cards, front and back side.
 * @author Marco Gasperini
 */
public class WindowCardAssociation {
    private WindowCard front;
    private WindowCard back;

    public WindowCardAssociation(WindowCard front, WindowCard back) throws WindowCardAssociationException {

        if (front == null)
            throw new WindowCardAssociationException();
        else if (back == null)
            throw new WindowCardAssociationException();
        else {
            this.front = front;
            this.back = back;
        }
    }


    public void setFront(WindowCard front) {

        this.front = front;
    }

    public void setBack(WindowCard back) {

        this.back = back;
    }

    public WindowCard getFront() {

        return front;
    }

    public WindowCard getBack() {

        return back;
    }

    // da testare
    public WindowCard extractAssociation()
    {

        return front;
    }
}
