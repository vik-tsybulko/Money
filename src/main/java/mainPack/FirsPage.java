package mainPack;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * Created by viktor on 03.02.17.
 */
public class FirsPage {

    private static FirsPage instance;
    public static FirsPage getInstance(){
        if (instance == null)
            instance = new FirsPage();
        return instance;
    }
    private FirsPage(){
    }
    private JFrame jFrame = new JFrame("Money");
    private JLabel jLabel;
    private JScrollPane scrollPane;
    private JList jList;
    private JButton createButton;

    public JList getjList() {
        return jList;
    }

    public DefaultListModel defaultListModel = new DefaultListModel();

    public void start(){
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setBounds(50, 50, 250, 300);
        Box labelBox = Box.createHorizontalBox();
        jLabel = new JLabel("Your Party");
        labelBox.add(jLabel);

        whichOpenStartPage();
        Box listBox = Box.createHorizontalBox();
        jList = new JList(defaultListModel);
        jList.setSelectedIndex(0);
        if (!defaultListModel.contains("No Party")){
            jList.addMouseListener(new MouseListener() {
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
        scrollPane = new JScrollPane(jList);
        scrollPane.setPreferredSize(new Dimension(100, 200));
        listBox.add(scrollPane);

        Box buttonBox = Box.createHorizontalBox();
        createButton = new JButton("Create Party");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            CreateParty createParty = new CreateParty();
            createParty.start();
            }
        });


        buttonBox.add(createButton);
        buttonBox.add(Box.createHorizontalStrut(15));


        Box mainBox = Box.createVerticalBox();
        mainBox.add(labelBox);
        mainBox.add(Box.createVerticalStrut(5));
        mainBox.add(listBox);
        mainBox.add(Box.createVerticalStrut(5));
        mainBox.add(buttonBox);
        mainBox.setBorder(new EmptyBorder(5, 5, 5, 5));

        jFrame.setContentPane(mainBox);
        jFrame.setVisible(true);
    }
    private void whichOpenStartPage(){
        OpenStartPage openStartPage = new OpenStartPage();
        if (openStartPage.
                findFiles()){
            for (File f : openStartPage.listFile){
                defaultListModel.addElement(f.getName().replaceAll(".sqlite", ""));
            }
        }else defaultListModel.addElement("No Party");
    }
    public void rePaint(){
        jFrame.repaint();
    }
    public String getSelectParty(){
        return String.valueOf(jList.getSelectedValue());


    }


}
