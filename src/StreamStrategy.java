import java.util.ArrayList;
import java.util.stream.Collectors;

public class StreamStrategy implements Strategy{

    @Override
    public ArrayList<Student> execute(ArrayList<Student> studentList) {
        StudentComparator studentComparator = new StudentComparator();
        return studentList.stream().filter(Student::isExcellent)
                .sorted(studentComparator).collect(Collectors.toCollection(ArrayList::new));
    }
}
