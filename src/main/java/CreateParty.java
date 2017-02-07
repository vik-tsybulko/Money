
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by viktor on 06.02.17.
 */
public class CreateParty {
    CreateParty(){

    }
    private JFrame frame;

    private JLabel namePartyLabel;
    private JTextField namePartyTextField;

    private JLabel qManLabel;
    private JSlider qManSlider;

    private JLabel qDayLabel;
    private JSlider qDaySlider;

    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;



    public void start(){
        frame = new JFrame("Create Party");
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setBounds(50, 50, 300, 500);
        frame.setLayout(new GridBagLayout());



        namePartyLabel = new JLabel("Enter name of Party");
        namePartyTextField = new JTextField(10);

        frame.add(namePartyLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        frame.add(namePartyTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));

        qManLabel = new JLabel("Enter quantity man");
        qManSlider = new JSlider(2, 10, 5);
        qManSlider.setMajorTickSpacing(1);
        qManSlider.setPaintTicks(true);
        qManSlider.setPaintLabels(true);


        frame.add(qManLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        frame.add(qManSlider, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));

        qDayLabel = new JLabel("Enter quantity day");
        qDaySlider = new JSlider(1, 5, 2);
        qDaySlider.setMajorTickSpacing(1);
        qDaySlider.setPaintLabels(true);
        qDaySlider.setPaintTicks(true);

        frame.add(qDayLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        frame.add(qDaySlider, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));

        buttonPanel = new JPanel(new GridLayout());
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameParty = namePartyTextField.getText();
                int qMan = qManSlider.getValue();
                int qDay = qDaySlider.getValue();
                InteractWithDB inWDB = new InteractWithDB();
                inWDB.addPartyInfoToDB(nameParty, qMan, qDay);
                InitMan initMan = new InitMan();
                initMan.start(nameParty, qMan, qDay);
                frame.setVisible(false);
            }
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        buttonPanel.add(okButton);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));


        frame.setVisible(true);
        frame.pack();

    }





}
