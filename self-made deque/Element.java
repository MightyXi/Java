public abstract class Element {
    void accept(Visitor v) {
        v.visit( this );
    }
}
