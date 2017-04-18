package mainPack;

import java.util.ArrayList;

/**
 * Created by Solush on 12.02.2017.
 */
public class Logic {
    private String dayOfPayment;
    private String suplier;
    private int amount;
    private String payFor;
    public Logic(){

    }
    public void addPay(){
        dayOfPayment = String.valueOf(AddMoney.getDayComboBox().getSelectedItem()).replace(" ", "");
        suplier = String.valueOf(AddMoney.getSuplierComboBox().getSelectedItem());
        amount = Integer.valueOf(AddMoney.getAmountTextField().getText());
        payFor = AddMoney.getPayForTextField().getText();
        int pay;
        ArrayList<String> nameMan = new ArrayList<String>();
        InteractWithDB interactWithDB = new InteractWithDB();
        int amountDrink = 0;
        for (int i = 0; i < AddMoney.getUserCheckBox().length; i++) {
            if (AddMoney.getUserCheckBox()[i].isSelected() == true) {
                amountDrink++;
                nameMan.add(AddMoney.getUserCheckBox()[i].getText());
            }
        }
        pay = amount/amountDrink;
        //расчет суммы каждого учасника и добавление ее в БД
        for (int i = 0; i < AddMoney.getUserCheckBox().length; i++) {
            if (AddMoney.getUserCheckBox()[i].isSelected() == true) {
                if (AddMoney.getUserCheckBox()[i].getText() == suplier) {
                    int spentMoney = amount - pay;
                    int oldMoney = interactWithDB.getMoney(PartyInfo.getNameParty(), dayOfPayment,
                            AddMoney.getUserCheckBox()[i].getText());
                    int newMoney = oldMoney + spentMoney;
                    interactWithDB.addMoneyToCommonDB(PartyInfo.getNameParty(), dayOfPayment,
                            AddMoney.getUserCheckBox()[i].getText(), newMoney, suplier, pay, payFor, amount);
                } else {
                    int spentMoney = -pay;
                    int oldMoney = interactWithDB.getMoney(PartyInfo.getNameParty(), dayOfPayment,
                            AddMoney.getUserCheckBox()[i].getText());
                    int newMoney = oldMoney + spentMoney;
                    interactWithDB.addMoneyToCommonDB(PartyInfo.getNameParty(), dayOfPayment,
                            AddMoney.getUserCheckBox()[i].getText(), newMoney, suplier, pay, payFor, amount);
                }
            }else if (AddMoney.getUserCheckBox()[i].getText() == suplier) {
                int oldMoney = interactWithDB.getMoney(PartyInfo.getNameParty(), dayOfPayment,
                        AddMoney.getUserCheckBox()[i].getText());
                int newMoney = oldMoney + amount;
                interactWithDB.addMoneyToCommonDB(PartyInfo.getNameParty(), dayOfPayment,
                        AddMoney.getUserCheckBox()[i].getText(), newMoney, suplier, pay, payFor, amount);
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
