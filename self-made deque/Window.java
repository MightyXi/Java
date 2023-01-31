import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Window extends JFrame {
    View view;
    Controller controller;
    DefaultListModel<MyInteger> model;
    Deque<MyInteger> deque;
    Deque<MyInteger> dequeToPush;
    JList<MyInteger> list;

    JButton visitButton;

    Window(String str) {
        super(str);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        setLayout(new BorderLayout());
        model = new DefaultListModel<>();
        list = new JList<>(model);
        deque = new Deque<>();
        view = new View(deque, model);

        controller = new Controller(deque, view);
        dequeToPush = new Deque<>();

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        JTextField textField = new JTextField();
        JTextField field = new JTextField();
        textField.setPreferredSize(new Dimension(150, 20));
        field.setPreferredSize(new Dimension(35, 20));
        northPanel.add(textField);
        northPanel.add(field);
        add(northPanel, BorderLayout.NORTH);
        add(list, BorderLayout.SOUTH);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        JButton front = new JButton("Front");
        JButton back = new JButton("Back");
        JButton pushFront = new JButton("PushFront");
        JButton pushBack = new JButton("PushBack");
        JButton popFront = new JButton("PopFront");
        JButton popBack = new JButton("popBack");
        JButton pushFrontAll = new JButton("PushFrontAll");
        JButton pushBackAll = new JButton("PushBackAll");
        visitButton = new JButton("Visitor");
        centerPanel.add(front);
        centerPanel.add(back);
        centerPanel.add(pushBack);
        centerPanel.add(pushFront);
        centerPanel.add(popBack);
        centerPanel.add(popFront);
        centerPanel.add(pushBackAll);
        centerPanel.add(pushFrontAll);
        centerPanel.add(visitButton);
        add(centerPanel, BorderLayout.CENTER);

        front.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!deque.isEmpty())
                    field.setText(String.valueOf(deque.front()));
                else
                    JOptionPane.showMessageDialog(null,
                            "Deque is empty!");
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!deque.isEmpty())
                    field.setText(String.valueOf(deque.back()));
                else
                    JOptionPane.showMessageDialog(null,
                            "Deque is empty!");
            }
        });

        pushBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    MyInteger i = new MyInteger(Integer.parseInt(textField.getText()));
                    controller.pushBack(i);
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,
                            "Wrong data format!");
                }
            }
        });
        pushFront.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    MyInteger i = new MyInteger(Integer.parseInt(textField.getText()));
                    controller.pushFront(i);
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,
                            "Wrong data format!");
                }
            }
        });

        popBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (deque.isEmpty())
                        throw new ArrayIndexOutOfBoundsException("Array is empty");
                    controller.popBack();
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage());
                }
            }
        });

        popFront.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (deque.isEmpty())
                        throw new ArrayIndexOutOfBoundsException("Array is empty");
                    controller.popFront();
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage());
                }
            }
        });

        pushBackAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Scanner scan = new Scanner(textField.getText());
                    while (scan.hasNext()) {
                        dequeToPush.pushBack(new MyInteger(Integer.parseInt(scan.next())));
                    }
                    controller.pushBackAll(dequeToPush);
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,
                            "Wrong data format!");
                }
            }
        });

        pushFrontAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Scanner scan = new Scanner(textField.getText());
                    while (scan.hasNext()) {
                        dequeToPush.pushBack(new MyInteger(Integer.parseInt(scan.next())));
                    }
                    controller.pushFrontAll(dequeToPush);
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,
                            "Wrong data format!");
                }
            }
        });

        visitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IntegerVisitor v = new IntegerVisitor();
                Deque.Iteratos<MyInteger> iter = deque.createIterator();
                while(iter.hasNext()){
                    iter.currentElement().accept(v);
                    iter.next();
                }
                field.setText(String.valueOf(v.getSize()));
            }
        });
    }
}
