import javax.swing.*;
import java.util.ArrayList;

public class Deque<T> implements Iterable<T>{
    private ArrayList<T> list;

    Deque(){
        list = new ArrayList<>();
    }
    public int size(){
        return list.size();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public void clean(){
        list.clear();
    }

    public boolean equals(Deque<T> dq) {
        return list.equals(dq.list);
    }

    public String toString() {
            StringBuilder str = new StringBuilder();
            for (T t : list) {
                str.append(t).append(" ");
            }
        return String.valueOf(str);
    }

    public T front(){return list.get(0);}
    public T back(){return list.get(list.size()-1);}
    public void pushFront(T el){list.add(0, el);}
    public void pushBack(T el){list.add(el);}
    public void popFront(){
        list.remove(0);
    }
    public void popBack(){list.remove(list.size()-1);}

    public void pushBackAll(Deque<T> dq) {
        Iteratos<T> it = dq.createIterator();
        while(it.hasNext()) {
            pushBack(it.next());
        }
    }
    public void pushFrontAll(Deque<T> dq){
        Iteratos<T> it = dq.createIterator();
        while(it.hasPrev()) {
            pushFront(it.previous());
        }
    }

    @Override
    public Iteratos<T> createIterator() {
        Iteratos<T> it = new Iteratos<T>() {
            private int currentIndex = 0;
            private int indToRev = list.size()-1;

            @Override
            public boolean hasNext() {
                return currentIndex < size() && list.get(currentIndex) != null;
            }

            @Override
            public boolean hasPrev() {
                return indToRev >= 0 && list.get(indToRev) != null;
            }

            @Override
            public T next() {
                return list.get(currentIndex++);
            }


            @Override
            public T previous() {
                return list.get(indToRev--);
            }

            @Override
            public T currentElement(){return list.get(currentIndex);}
        };
        return it;
    }



    interface Iteratos<T>{
        boolean hasNext();
        boolean hasPrev();
        T next();
        T previous();
        T currentElement();
    }

    public final DefaultListModel<T> getListModel() {
        DefaultListModel<T> mod = new DefaultListModel<>();
        mod.addAll(list);
        return mod;
    }
}
