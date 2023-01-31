public class IntegerVisitor implements Visitor{
    int size;

    public IntegerVisitor(){
        size = 0;
    }
    @Override
    public void visit(Element element) {
        size++;
    }

    int getSize(){
        return size;
    }
}

