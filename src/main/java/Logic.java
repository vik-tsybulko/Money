import java.util.ArrayList;
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
    List<String> isSelectedManArr;
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

        for (Map.Entry entry : SelectPeople.getIsParticipant().entrySet()){
            if (entry.getValue().equals(false)){
                money.remove(entry.getKey());
            }
        }
        newMoney = new HashMap<String, Integer>();
        for (Map.Entry entry : money.entrySet()){
            if (entry.getKey().toString().equals(suplier)) {
                int sumSuplier = (amount - (amount / money.size())) + Integer.parseInt(entry.getValue().toString());
                newMoney.put(entry.getKey().toString(), sumSuplier);
                System.out.println(entry.getKey() + " sum " + sumSuplier);
            }else {
                int sumOther = -(amount / money.size()) + Integer.parseInt(entry.getValue().toString());
                newMoney.put(entry.getKey().toString(), sumOther);
                System.out.println(entry.getKey() + " sum " + sumOther);
            }
        }
//        System.out.println(-5 - -3 + " - - -");//-2
//        System.out.println(-5 + -3 + " - + -");//-8
//        System.out.println(-5 - 3 + " - -");//-8
//        System.out.println(-5 + 3 + " - +");//-2



        interactWithDB.addMoney(PartyInfo.getNameParty(), dayOfPayment, newMoney);
        /*
        * amount = 1000
        * день 1
        * бухающие ксю, вик, андрей, толя
        * плательщик ксю
        * isSelectedMan.length() = 4
        * закидон делим на количество бухающих
        * amount / isSelectedMan.length() = 1000 / 4 = 250
        * всем кроме ксю = -250
        * ксю = 100 - 20 = +750
        * квери апдейт 1 = -250 день 1 вик, андей, толя
        * квери апдейт 2 = 750 день 1 ксю
         */

    }
}
