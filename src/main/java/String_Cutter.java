/**
 * Created by rodya on 13.10.16.
 */
public class String_Cutter {
    private String[] discharges;
    private int kolDigits;
    public String_Cutter(String number)
    {
    kolDigits = number.length();

    }

    public String[] createDisCharges(String number, String[] discharges)
    {
        int ost = number.length()%3;
        String numberRev=new String();

        switch(ost)
        {
            case 0: {discharges = new String[(number.length()/3)];
                numberRev = revertString(number)+"0";
                break;}
            case 1: {discharges = new String[(number.length()/3)+1];
                numberRev = revertString(number)+"000";
                break;}
            case 2: {
                discharges = new String[(number.length()/3)+1];
                numberRev = revertString(number)+"00";break;}
        }
    int k=0;
        for (int i=0; i<discharges.length;i++)
        {
            discharges[i]=numberRev.substring(i*3,k+3);
            k=k+3;
        }
        k=0;
        return (discharges);
    }

    public int getKolDigits() {
        return kolDigits;
    }
    public String revertString(String s)
    {
        String result = new String();
        for (int i = s.length() - 1; i >= 0; i--)
        {
            result = result + s.substring(i, i+1);
        }
        return result;
    }

    public String[] getDischarges() {
        return discharges;
    }
}
