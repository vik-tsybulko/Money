package mainPack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Solush on 12.02.2017.
 */
public class Logic {
    private String dayOfPayment;
    private String suplier;
    private int amount;
    private String payFor;
    Map<String, Integer> money;
    Map<String, Integer> newMoney;
    public Logic(){

    }
    public void addPay(){
        dayOfPayment = String.valueOf(AddMoney.getDayComboBox().getSelectedItem()).replace(" ", "");
        System.out.println(dayOfPayment);
        suplier = String.valueOf(AddMoney.getSuplierComboBox().getSelectedItem());
        amount = Integer.valueOf(AddMoney.getAmountTextField().getText());
        payFor = AddMoney.getPayForTextField().getText();


        money = new HashMap<String, Integer>();
        InteractWithDB interactWithDB = new InteractWithDB();
        money = interactWithDB.getMoneyInMan(PartyInfo.getNameParty(), dayOfPayment);
        int amountDrink = 0;
        for (int i = 0; i < AddMoney.getUserCheckBox().length; i++) {
            if (AddMoney.getUserCheckBox()[i].isSelected() == true) {
                amountDrink++;
            }
        }
        for (int i = 0; i < AddMoney.getUserCheckBox().length; i++) {
            if (AddMoney.getUserCheckBox()[i].isSelected() == true) {
                if (AddMoney.getUserCheckBox()[i].getText() == suplier) {
                    int spentMoney = amount - (amount / amountDrink);
                    int oldMoney = interactWithDB.getMoney(PartyInfo.getNameParty(), dayOfPayment, AddMoney.getUserCheckBox()[i].getText());
                    int newMoney = oldMoney + spentMoney;
                    interactWithDB.addMoney(PartyInfo.getNameParty(), dayOfPayment, AddMoney.getUserCheckBox()[i].getText(), newMoney);
                } else {
                    int spentMoney = -(amount / amountDrink);
                    int oldMoney = interactWithDB.getMoney(PartyInfo.getNameParty(), dayOfPayment, AddMoney.getUserCheckBox()[i].getText());
                    int newMoney = oldMoney + spentMoney;
                    interactWithDB.addMoney(PartyInfo.getNameParty(), dayOfPayment, AddMoney.getUserCheckBox()[i].getText(), newMoney);
                }
            }else if (AddMoney.getUserCheckBox()[i].getText() == suplier) {
                int oldMoney = interactWithDB.getMoney(PartyInfo.getNameParty(), dayOfPayment, AddMoney.getUserCheckBox()[i].getText());
                int newMoney = oldMoney + amount;
                interactWithDB.addMoney(PartyInfo.getNameParty(), dayOfPayment, AddMoney.getUserCheckBox()[i].getText(), newMoney);
            }

        }


//        System.out.println(-5 - -3 + " - - -");//-2
//        System.out.println(-5 + -3 + " - + -");//-8
//        System.out.println(-5 - 3 + " - -");//-8
//        System.out.println(-5 + 3 + " - +");//-2


        /*
        * amount = 1000
        * день 1
        * бухающие ксю, вик, андрей, толя
        * плательщик ксю
        * isSelectedMan.length() = 4
        * закидон делим на количество бухающих
        * amount / isSelectedMan.length() = 1000 / 4 = 250
        * всем кроме ксю = -250
        * ксю = 1000 - 250 = +750
        * квери апдейт 1 = -250 день 1 вик, андей, толя
        * квери апдейт 2 = 750 день 1 ксю
         */

    }
}
