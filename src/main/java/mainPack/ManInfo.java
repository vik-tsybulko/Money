package mainPack;

import manInfoPanels.MyDebtorsPanel;
import manInfoPanels.MyDebtsPanel;
import manInfoPanels.MyPaymentPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Solush on 03.03.2017.
 */
public class ManInfo {
    ManInfo(String name){
        nameSelected = name;
    }
    private JFrame frame;
    private JButton okButton;
    private JButton cancelButton;
    private Box myPaymentBox = Box.createHorizontalBox();
    private Box myDebtsBox = Box.createHorizontalBox();
    private Box myDebtorsBox = Box.createHorizontalBox();
    private Box buttonBox = Box.createHorizontalBox();
    private Box mainBox = Box.createVerticalBox();
    public String nameSelected;

    public void start(){
        frame = new JFrame("Info about the " + nameSelected);
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setBounds(50, 50, 250, 300);

        MyPaymentPanel paymentPanel = new MyPaymentPanel(nameSelected);
        paymentPanel.start();
        myPaymentBox.add(paymentPanel);
        MyDebtsPanel debtsPanel = new MyDebtsPanel(nameSelected);
        debtsPanel.start();
        myDebtsBox.add(debtsPanel);
        MyDebtorsPanel debtorsPanel = new MyDebtorsPanel();
        debtorsPanel.start();
        myDebtorsBox.add(debtorsPanel);

        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(okButton);
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(cancelButton);
        buttonBox.add(Box.createHorizontalGlue());

        mainBox.add(myPaymentBox);
        mainBox.add(Box.createVerticalStrut(0));
        mainBox.add(myDebtsBox);
        mainBox.add(Box.createVerticalStrut(0));
        mainBox.add(myDebtorsBox);
        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(buttonBox);
        frame.add(mainBox);

        frame.setVisible(true);
        frame.pack();
    }

}
