import javax.swing.*;

public class View {
    DefaultListModel<MyInteger> listModel;
    Deque<MyInteger> deque;

    View(Deque<MyInteger> d, DefaultListModel<MyInteger> l){
        deque = d;
        listModel = l;
    }
    public void viewUpdate(){
        listModel.clear();
        Deque.Iteratos<MyInteger> it = deque.createIterator();
        while(it.hasNext())
            listModel.addElement(it.next());
    }
}
