import javax.swing.*;
import java.awt.*;

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

    private JButton okButton;
    private JButton cancelButton;

    private JPanel namePanel;
    private JPanel qManPanel;
    private JPanel qDayPanel;
    private JPanel buttonPanel;

    public void start(){
        frame = new JFrame("Create Party");
        frame.setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
        frame.setBounds(50, 50, 200, 300);
        frame.setLayout(new GridBagLayout());


    }



}
