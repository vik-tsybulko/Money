import java.util.ArrayList;
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
    public Logic(){

    }
    public void addPay(){
        dayOfPayment = String.valueOf(AddMoney.getDayComboBox().getSelectedItem()).replace(" ", "");
        System.out.println(dayOfPayment);
        suplier = String.valueOf(AddMoney.getSuplierComboBox().getSelectedItem());
        amount = Integer.valueOf(AddMoney.getAmountTextField().getText());
        payFor = AddMoney.getPayForTextField().getText();
        isSelectedManArr = new ArrayList<String>();

        for (Map.Entry entry : SelectPeople.getIsParticipant().entrySet()){
            if (entry.getValue().equals(true)){
                isSelectedManArr.add(String.valueOf(entry.getKey()));
            }
        }
        int sumPayer;
        int sumOther;
        sumOther = amount / isSelectedManArr.size();
        System.out.println(sumOther);
        sumPayer = amount - sumOther;
        System.out.println(sumPayer);

        InteractWithDB interactWithDB = new InteractWithDB();
        interactWithDB.addMoney(PartyInfo.getNameParty(), dayOfPayment, isSelectedManArr, suplier, sumOther, sumPayer);
        /*
        * amount = 100
        * день 1
        * бухающие ксю, вик, андрей, толя, игорь
        * плательщик ксю
        * isSelectedMan.length() = 5
        * закидон делим на количество бухающих
        * amount / isSelectedMan.length() = 100 / 5 = 20
        * всем кроме ксю = -20
        * ксю = 100 - 20 = +80
        * квери апдейт 1 = -20 день 1 вик, андей, толя, игорь
        * квери апдейт 2 = -80 день 1 ксю
         */

    }
}
