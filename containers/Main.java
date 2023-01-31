import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.xml.parsers.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main extends JFrame {
    ArrayList<Student> students = new ArrayList<>();
    File file;
    Strategy strategy;

    Main() {
        super("Students");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel panelForText = new JPanel();
        JPanel panelForButtons = new JPanel();
        add(panelForText, BorderLayout.CENTER);
        add(panelForButtons, BorderLayout.SOUTH);
        CheckboxGroup strategyChooser = new CheckboxGroup();
        Checkbox streamStrategy = new Checkbox("Stream", strategyChooser, true);
        Checkbox anotherStrategy = new Checkbox("Not stream", strategyChooser, false);
        JTextArea allStudents = new JTextArea();
        JTextArea excellentStudents = new JTextArea();
        allStudents.setPreferredSize(new Dimension(300, 200));
        excellentStudents.setPreferredSize(new Dimension(300, 200));
        panelForText.setLayout(new FlowLayout());
        panelForText.add(allStudents);
        panelForText.add(excellentStudents);
        JButton showExcellent = new JButton("Excellent");
        JButton addStudent = new JButton("Add");
        addStudent.setEnabled(false);
        JButton openFile = new JButton("Open");
        JButton openDOM = new JButton("Open XML DOM");
        JButton openSAX = new JButton("Open XML SAX");
        //JButton saveXML = new JButton("Save in XML");
        panelForButtons.setLayout(new FlowLayout());
        panelForButtons.add(showExcellent);
        panelForButtons.add(addStudent);
        panelForButtons.add(openFile);
        panelForButtons.add(streamStrategy);
        panelForButtons.add(anotherStrategy);
        panelForButtons.add(openDOM);
        panelForButtons.add(openSAX);
        //panelForButtons.add(saveXML);

        showExcellent.addActionListener(e -> {
            StudentComparator studentComparator = new StudentComparator();


            /////////////Lab 11
            StringBuilder build = new StringBuilder();
            if (streamStrategy.getState())
                strategy = new StreamStrategy();
            else if (anotherStrategy.getState())
                strategy = new AnotherStrategy();
            for (Student student : strategy.execute(students)) {
                build.append(student.toString());
            }
            /////////////////////


            excellentStudents.setText(String.valueOf(build));
        });

        openFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./src"));
            int returnValue = fileChooser.showOpenDialog(Main.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                try {
                    readStudents(file, students);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Wrong data format!!!");
                }
                StringBuilder build = new StringBuilder();
                for (Student student : students) {
                    build.append(student.toString());
                }
                allStudents.setText(String.valueOf(build));
                addStudent.setEnabled(true);
            }
        });

        addStudent.addActionListener(e -> {
            addStudent.setEnabled(true);
            JDialog dialog = new JDialog();
            dialog.setLayout(new BorderLayout());
            JTextField text = new JTextField();
            dialog.setBounds(600, 450, 230, 100);
            JButton button = new JButton("Add");
            dialog.add(button, BorderLayout.SOUTH);
            dialog.add(text, BorderLayout.CENTER);
            dialog.setVisible(true);

            button.addActionListener(e1 -> {
                Scanner sc = new Scanner(text.getText());
                try {
                    int number = Integer.parseInt(sc.next());
                    if (number < 100000 || number > 1000000)
                        throw new NumberFormatException();
                    String surname = sc.next();
                    String[] subjects = new String[3];
                    int[] marks = new int[3];
                    for (int i = 0; i < 3; i++) {
                        subjects[i] = sc.next();
                        marks[i] = Integer.parseInt(sc.next());
                        if (marks[i] > 10 || marks[i] < 0)
                            throw new NumberFormatException();
                    }
                    if (sc.hasNext())
                        throw new NumberFormatException();
                    if (!Objects.equals(subjects[0], "GA") || !Objects.equals(subjects[1], "DU") ||
                            !Objects.equals(subjects[2], "LinAl"))
                        throw new NumberFormatException();
                    Student st = new Student(number, surname, subjects, marks);
                    boolean temp = true;
                    if (students.size() == 0)
                        students.add(st);
                    else {
                        for (Student value : students) {
                            if (value.equals(st)) {
                                temp = false;
                                break;
                            }
                        }
                        if (temp)
                            students.add(st);
                        else
                            JOptionPane.showMessageDialog(null,
                                "Error: This student already exists");
                        StringBuilder build = new StringBuilder();
                        for (Student student : students) {
                            build.append(student.toString());
                        }
                        allStudents.setText(String.valueOf(build));
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Wrong data format!");
                }
            });
        });


        openDOM.addActionListener(e -> {
            addStudent.setEnabled(true);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./src"));
            int returnValue = fileChooser.showOpenDialog(Main.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
            }
            students.clear();
            DocumentBuilder documentBuilder;
            try {
                documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                throw new RuntimeException(ex);
            }
            Document doc;
            try {
                doc = documentBuilder.parse(file);
            } catch (SAXException | IOException ex) {
                throw new RuntimeException(ex);
            }

            NodeList nodeList = doc.getElementsByTagName("student");
            NodeList tempNodeList;
            int size = nodeList.getLength();
            for (int i = 0; i < size; i++) {
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    int number = Integer.parseInt(element.getAttribute("number"));
                    String surname = element.getElementsByTagName("surname").item(0).getTextContent();
                    tempNodeList = element.getElementsByTagName("subject");
                    int tempSize = tempNodeList.getLength();
                    int[] marks = new int[tempSize];
                    String[] subjects = new String[tempSize];
                    for (int j = 0; j < tempSize; j++) {
                        Element tempElem = (Element) tempNodeList.item(j);
                        subjects[j] = tempElem.getAttribute("name");
                        marks[j] = Integer.parseInt(tempNodeList.item(j).getTextContent());
                    }
                    Student st = new Student(number, surname, subjects, marks);

                    if(!students.contains(st))
                        students.add(st);
                }
            }
            StringBuilder build = new StringBuilder();
            for(Student s : students)
                build.append(s);
            allStudents.setText(String.valueOf(build));
        });

        openSAX.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./src"));
            int returnValue = fileChooser.showOpenDialog(Main.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
            }
            students.clear();
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                DefaultHandler handler = new DefaultHandler() {
                    boolean surnameFlag = false;
                    boolean subjectFlag = false;
                    int ind = 0;
                    int tempNumber = 0;
                    String tempSurname = null;
                    String[] tempSubjects = new String[3];
                    int[] tempMarks = new int[3];
                    @Override
                    public void startElement(String uri, String localName, String qName, Attributes attributes) {

                        if (qName.equalsIgnoreCase("student")) {
                            ind = 0;
                            tempSubjects = new String[3];
                            tempMarks = new int[3];
                            tempNumber = Integer.parseInt(attributes.getValue("number"));
                        }
                        else if(qName.equalsIgnoreCase("surname")){
                            surnameFlag = true;
                        } else if (qName.equalsIgnoreCase("subject")) {
                            tempSubjects[ind] =  attributes.getValue("name");
                            subjectFlag = true;
                        }
                    }

                    @Override
                    public void characters(char[] ch, int start, int length) {
                        if (surnameFlag) {
                            tempSurname = new String(ch, start, length);
                            surnameFlag = false;
                        } else if (subjectFlag) {
                            tempMarks[ind] = Integer.parseInt(new String(ch, start, length));
                            ind++;
                            subjectFlag = false;
                        }
                    }

                    @Override
                    public void endElement(String uri, String localName, String qName){
                        if (qName.equalsIgnoreCase("student")) {
                            Student st = new Student(tempNumber, tempSurname, tempSubjects, tempMarks);
                            if (!students.contains(st))
                                students.add(st);
                        }
                    }

                };
                saxParser.parse(file, handler);

            } catch (Exception exc) {
                exc.printStackTrace();
            }
            StringBuilder build = new StringBuilder();
            for(Student s : students)
                build.append(s);
            allStudents.setText(String.valueOf(build));
        });
    }


    public static void readStudents(File file, ArrayList<Student> students)
            throws IOException, NoSuchElementException, NumberFormatException {
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            int number;
            String surname;
            String[] subjects = new String[3];
            int[] marks = new int[3];

            number = Integer.parseInt(scanner.next());
            surname = scanner.next();
            for (int j = 0; j < marks.length; j++) {
                subjects[j] = scanner.next();
                marks[j] = Integer.parseInt(scanner.next());
            }
            Student st = new Student(number, surname, subjects, marks);
            if (!students.contains(st))
                students.add(st);
        }
    }


    public static void main(String[] args){
        Main window = new Main();
        window.setSize(new Dimension(800, 300));
        window.setVisible(true);
    }
}