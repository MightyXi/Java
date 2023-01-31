import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {
    public int compare(Student a, Student b) {
        if (a.getSurname().equals(b.getSurname()))
            return Integer.compare(a.getNumber(), b.getNumber());
        return a.getSurname().compareTo(b.getSurname());
    }
}
