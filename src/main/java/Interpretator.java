/**
 * Created by rodya on 15.10.16.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.math.BigDecimal;
public class Interpretator {

    public static String numTOstr(String amountIn) {
        DigestReader temp = new DigestReader();
        String[][] rodSlov = {
                {"","один","два","три","четыре","пять","шесть","семь","восемь","девять"},
                {"","одна","две","три","четыре","пять","шесть","семь","восемь","девять"},
        };

        String[] str100= temp.getHundreds();
        String[] str11 = temp.getElevNine();
        String[] str10 = temp.getDickers();
        String[][] forms = temp.getExps();
        String amount = new String("");
        String verbalNumber = "";
        if(amountIn.charAt(0)=='-')
        {
            verbalNumber+="минус ";
            amount = amountIn.substring(1);
        }else{
        amount = amountIn;
        }
        ArrayList segments = new ArrayList();
        if(amount.toString().length()>=0)
        {
            if (amount.equals("0"))
            {
                verbalNumber = "ноль";
                return verbalNumber;
            }
            String[] numberBySeg = new String[amount.length()%3+1];
            numberBySeg = createDisCharges(amount,numberBySeg);
            for (int i=numberBySeg.length;i>0;i--)
            {
                segments.add(Long.parseLong(numberBySeg[i-1]));
            }

        }


        int lev = segments.size();
        for (int i= 0; i<segments.size(); i++ ) {// iterate segments
            int rodDefSlova = (int)Integer.valueOf( forms[lev-1][3].toString() );// identification sex of word
            int ri = (int)Integer.valueOf( segments.get(i).toString() );// present segment
            if (ri== 0 && lev>1) {// if segment equals 000
                lev--;
                continue;
            }
            String rs = String.valueOf(ri);

            if (rs.length()==1) rs = "00"+rs;
            if (rs.length()==2) rs = "0"+rs;

            int r1 = (int)Integer.valueOf( rs.substring( 0,1) ); //first number xNN
            int r2 = (int)Integer.valueOf( rs.substring(1,2) ); //second NxN
            int r3 = (int)Integer.valueOf( rs.substring(2,3) ); //NNx
            int r22= (int)Integer.valueOf( rs.substring(1,3) ); //Nxx
            // analuzing digigts
            if (ri>99) verbalNumber += str100[r1]+" "; // hundreds
            if (r22>=20) {// >20
                    verbalNumber += str10[r2] + " ";
                    verbalNumber += rodSlov[rodDefSlova][r3] + " ";
            }
            else { // <=20
                if (r22 > 9) verbalNumber += str11[r22 - 9] + " "; // 10-20
                else verbalNumber += rodSlov[rodDefSlova][r3] + " "; // 0-9
            }
            lev--;
                verbalNumber += morph(ri, forms[lev ][0], forms[lev][1], forms[lev][2])+" ";

        }

        return formatStr(verbalNumber).substring(0,formatStr(verbalNumber).length()-1);
    }



    private static String[] createDisCharges(String number, String[] discharges)
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
            discharges[i]=revertString(numberRev.substring(i*3,k+3));
            k=k+3;
        }
        k=0;
        return (discharges);
    }

    public static String revertString(String s)
    {
        String result = new String();
        for (int i = s.length() - 1; i >= 0; i--)
        {
            result = result + s.substring(i, i+1);
        }
        return result;
    }

    private static String[] revertSubStrings(String[] temp_in,String[] temp_out)
    {
        for (int i=0;i<temp_in.length;i++)
        {
            temp_out[i]=revertString(temp_in[i]);
        }
        return temp_out;
    }

    private static String formatStr(String s)
    {
        while(s.contains("  ")) {
            String replace = s.replace("  ", " ");
            s=replace;
        }
        return s;
    }



    //creating right form of word
    public static String morph(long n, String f1, String f2, String f5) {
        n = Math.abs(n) % 100;
        long n1 = n % 10;
        if (n > 10 && n < 20) return f5;
        if (n1 > 1 && n1 < 5) return f2;
        if (n1 == 1) return f1;
        return f5;
    }
}