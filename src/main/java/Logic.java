import java.util.Map;

/**
 * Created by Solush on 12.02.2017.
 */
public class Logic {
    private String dayOfPayment;
    private String suplier;
    private int amount;
    private String payFor;
    private String[] isSelectedMan;
    public Logic(){

    }
    public void addPay(){
        dayOfPayment = String.valueOf(AddMoney.getDayComboBox().getSelectedItem()).replace(" ", "");
        System.out.println(dayOfPayment);
        suplier = String.valueOf(AddMoney.getSuplierComboBox().getSelectedItem());
        amount = Integer.valueOf(AddMoney.getAmountTextField().getText());
        payFor = AddMoney.getPayForTextField().getText();
        isSelectedMan = new String[SelectPeople.quantityTrue];
        int quant = 0;
        for (Map.Entry entry : SelectPeople.getIsParticipant().entrySet()){
            if (entry.getValue().equals(true)){
                isSelectedMan[quant] = String.valueOf(entry.getKey());
                quant++;
            }
        }
        int sumPayer;
        int sumOther;
        sumOther = amount / isSelectedMan.length;
        System.out.println(sumOther);
        sumPayer = amount - sumOther;
        System.out.println(sumPayer);

        InteractWithDB interactWithDB = new InteractWithDB();
        interactWithDB.addMoney(PartyInfo.getNameParty(), dayOfPayment, isSelectedMan, suplier, sumOther, sumPayer);
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
