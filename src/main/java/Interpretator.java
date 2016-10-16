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
            //String [] numberSeg = new String[amount.toString().length()%3+1];
            for (int i=numberBySeg.length;i>0;i--)
            {
                segments.add(Long.parseLong(numberBySeg[i-1]));
            }

        }/*else{
            long number = amount.longValue();
            long number_temp = number;
            while(number_temp>999) {
                long segment = number_temp/1000;
                segments.add( number_temp-(segment*1000) );
                number_temp=segment;
            }
            segments.add( number_temp );
            Collections.reverse(segments);
            if (number== 0) {// если Ноль
                verbalNumber = "ноль";
                return verbalNumber;
            }
        }*/


        // Разбиватель суммы на сегменты по 3 цифры с конца



        // Анализируем сегменты


        // Больше нуля
        int lev = segments.size();
        for (int i= 0; i<segments.size(); i++ ) {// перебираем сегменты
            int rodDefSlova = (int)Integer.valueOf( forms[lev-1][3].toString() );// определяем род
            int ri = (int)Integer.valueOf( segments.get(i).toString() );// текущий сегмент
            if (ri== 0 && lev>1) {// если сегмент ==0 И не последний уровень(там Units)
                lev--;
                continue;
            }
            String rs = String.valueOf(ri); // число в строку
            // нормализация
            if (rs.length()==1) rs = "00"+rs;// два нулика в префикс?
            if (rs.length()==2) rs = "0"+rs; // или лучше один?
            // получаем циферки для анализа
            int r1 = (int)Integer.valueOf( rs.substring( 0,1) ); //первая цифра
            int r2 = (int)Integer.valueOf( rs.substring(1,2) ); //вторая
            int r3 = (int)Integer.valueOf( rs.substring(2,3) ); //третья
            int r22= (int)Integer.valueOf( rs.substring(1,3) ); //вторая и третья
            // analuzing dgigts
            if (ri>99) verbalNumber += str100[r1]+" "; // Сотни
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

        return formatStr(verbalNumber);
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
    /**
     * Склоняем словоформу
     * @param n Long количество объектов
     * @param f1 String вариант словоформы для одного объекта
     * @param f2 String вариант словоформы для двух объектов
     * @param f5 String вариант словоформы для пяти объектов
     * @return String правильный вариант словоформы для указанного количества объектов
     */
    public static String morph(long n, String f1, String f2, String f5) {
        n = Math.abs(n) % 100;
        long n1 = n % 10;
        if (n > 10 && n < 20) return f5;
        if (n1 > 1 && n1 < 5) return f2;
        if (n1 == 1) return f1;
        return f5;
    }
}