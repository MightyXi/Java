import javax.swing.*;
import java.util.Scanner;

public class Controller {
    private Deque<MyInteger> deque;
    private View view;

    Controller(Deque<MyInteger> d, View v){
        view = v;
        deque = d;
    }

    public void popBack(){
        deque.popBack();
        view.viewUpdate();
    }


    public void popFront(){
        deque.popFront();
        view.viewUpdate();
    }

    public void pushBack(MyInteger i){
        deque.pushBack(i);
        view.viewUpdate();
    }

    public void pushFront(MyInteger i){
        deque.pushFront(i);
        view.viewUpdate();
    }

    public void pushFrontAll(Deque<MyInteger> d){
        deque.pushFrontAll(d);
        d.clean();
        view.viewUpdate();
    }

    public void pushBackAll(Deque<MyInteger> d){
        deque.pushBackAll(d);
        d.clean();
        view.viewUpdate();
    }
}
