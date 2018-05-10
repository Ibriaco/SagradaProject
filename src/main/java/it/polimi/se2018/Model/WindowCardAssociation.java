package it.polimi.se2018.Model;

public class WindowCardAssociation {
    private WindowCard front;
    private WindowCard back;

    public WindowCardAssociation(WindowCard front, WindowCard back) {
        this.front = front;
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
