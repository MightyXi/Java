import java.util.ArrayList;

public class AnotherStrategy implements Strategy{
    @Override
    public ArrayList<Student> execute(ArrayList<Student> studentList) {
        ArrayList<Student> temporaroryContaine = new ArrayList<>();

        for (Student s : studentList){
            if(s.isExcellent())
                temporaroryContaine.add(s);
        }
            temporaroryContaine.sort(new StudentComparator());
        return temporaroryContaine;
    }
}
