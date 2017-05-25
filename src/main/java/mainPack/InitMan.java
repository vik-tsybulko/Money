package mainPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by viktor on 06.02.17.
 */
public class InitMan implements Runnable{
    Thread thread;
    private boolean isActiceThread;
    InitMan(){
        isActiceThread = true;
        thread = new Thread(this, "new Thread");
    }
    private JFrame frame;

    private JLabel[] nameManLabel;
    private JTextField[] nameManTextField;

    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private String nameParty;

    public void start(final String nameParty, int qMan, final int qDay){
        frame = new JFrame("Initialization Your Man");
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setBounds(50, 50, 300, 500);
        frame.setLayout(new GridBagLayout());
        this.nameParty = nameParty;

        nameManLabel = new JLabel[qMan];
        nameManTextField = new JTextField[qMan];
        for (int i = 0; i < qMan; i++){
            nameManLabel[i] = new JLabel("Enter name Man " + (i + 1));
            frame.add(nameManLabel[i], new GridBagConstraints(0, i, 1, 1, 0.0, 0.9,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(15, 15, 15, 15), 0, 0));
        }
        for (int i = 0; i < qMan; i++){
            nameManTextField[i] = new JTextField(10);
            frame.add(nameManTextField[i], new GridBagConstraints(1, i, 1, 1, 0.0, 0.9,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(15, 15, 15, 15), 0, 0));
        }

        buttonPanel = new JPanel(new GridBagLayout());
        okButton = new JButton("OK");
        okButton.setEnabled(false);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                InteractWithDB inWDB = new InteractWithDB();
                inWDB.addManInfoToDB(nameParty, getMenName(nameManTextField), qDay);
                addToListNameParty(nameParty);
                frame.setVisible(false);
                disableThread();
            }
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                CreateParty createParty = new CreateParty();
                cancelCreateParty();
                disableThread();
                createParty.start();
            }
        });
        buttonPanel.add(okButton);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, new GridBagConstraints(0, qMan, 2, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));


        frame.setVisible(true);
        frame.pack();
        thread.start();
    }
    private void cancelCreateParty(){
        String pathFile = "src/main/resources/" + nameParty + ".sqlite";
        File file = new File(pathFile);
        file.delete();
    }
    private String[] getMenName(JTextField[] textFields){
        String[] nameMen = new String[textFields.length];
        for (int i = 0; i < textFields.length; i++){
            nameMen[i] = textFields[i].getText();
        }
        return nameMen;
    }
    private void addToListNameParty(String nameParty){
        FirsPage firsPage = FirsPage.getInstance();
        firsPage.defaultListModel.addElement(nameParty);
        if (firsPage.defaultListModel.contains("No Party")){
            firsPage.defaultListModel.removeElement("No Party");
            firsPage.getjList().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2){
                        PartyInfo partyInfo = new PartyInfo();
                        partyInfo.start();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        firsPage.rePaint();

    }

    private boolean nameManIsCorrect(JTextField[] name){
        Pattern patternWord = Pattern.compile("^\\D[a-zа-яA-ZА-Я0-9 ]{3,15}$");
        Matcher m;
        int countCorrectNameMan = 0;
        boolean[] isCorrectWord = new boolean[name.length]; //проверка на содержание разрешенных символов
        boolean[] isCorrectEquals = new boolean[name.length];
        for (int i = 0; i < name.length; i++) {
            for (int j = 0; j < name.length; j++) {
                if (i == j) {
                    continue;
                }
                if (!name[i].getText().equals(name[j].getText())) {
                    isCorrectEquals[i] = true;
                } else {
                    isCorrectEquals[i] = false;
                    break;
                }
            }
        }

        for (int i = 0; i < name.length; i++) {
            m = patternWord.matcher(name[i].getText());
            isCorrectWord[i] = m.matches();
        }
        for (int i = 0; i <isCorrectWord.length; i++) {
            if (isCorrectWord[i] == true && isCorrectEquals[i] == true) {
                countCorrectNameMan++;
            }
        }
        if (countCorrectNameMan == isCorrectWord.length) {
            return true;
        }else return false;
    }
    void disableThread() {
        isActiceThread = false;
    }

    int countCycle = 0;
    @Override
    public void run() {
        while (isActiceThread) {
            if (nameManIsCorrect(nameManTextField) == true) {
                okButton.setEnabled(true);
            } else {
                okButton.setEnabled(false);
            }
            countCycle++;
            System.out.println("a " + countCycle);
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
