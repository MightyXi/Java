import java.util.Comparator;

public class Student{
    private int number;
    private String surname;
    private String[] subjects;
    private int[] marks;

    public Student(int number, String surname, String[] subjects, int[] marks){
        this.number = number;
        this.surname = surname;
        this.subjects = subjects;
        this.marks = marks;
        subjects = null;
        marks = null;
    }

    public static Student createExcellentStudent(){
        int numOfStudentCard = 1;
        String surname = "";
        int[] tempMarks = new int[2];
        String[] subjects = new String[2];
        for (int i = 0 ; i < 2 ; i++){
            tempMarks[i] = 10;
            subjects[i] = "GA";
        }
        return new Student(numOfStudentCard, surname, subjects, tempMarks);
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public String[] getSubjects(){
        return subjects;
    }

    public void setSubjects(String[] subjects){
        this.subjects = subjects;
    }

    public int[] getMarks(){
        return marks;
    }

    public void setMarks(int[] marks){
        this.marks = marks;
    }

    public boolean isExcellent(){
        for (int mark : this.marks){
            if (mark < 9)
                return false;
        }
        return true;
    }

    @Override
    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append(getNumber()).append(" ").append(getSurname()).append(" ");
        for (int i = 0; i < marks.length; i++)
        {
            buf.append(this.subjects[i]).append(" ").append(this.marks[i]).append(" ");
        }
        buf.append("\n");
        return buf.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Student st))
            return false;

        return this.surname.equals(st.surname) && this.number == st.number;
    }

}
