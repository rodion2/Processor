import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by rodya on 15.10.16.
 */

/*
    Перевод числа в цифровой записи в строковую. Например 134345 будет "сто
тридцать четыре тысячи триста сорок пять". * Учесть склонения - разница
в окончаниях (к примеру, две и два).
    Алгоритм должен работать для сколько угодно большого числа, соответственно,
значения степеней - миллион, тысяча, миллиад и т.д. - должны браться их
справочника, к примеру, текстового файла.
    Обязательно создать Data Driven Test (я, как пользователь, должен иметь
возможность ввести множество наборов 1.число 2.правильный эталонный результат,
 тест самостоятельно проверяет все наборы и говорит, что неверное), который
 доказывает, что Ваш алгоритм работает правильно. Использовать JUnit.
    По возможности, применить ООП.
 */

public class Processor {
    /**
     * Used for a keeping such numbers as 100,200,300 etc.
     */
    private String[] hundreds;
    /**
     * Used for a keeping such numbers as 10,11,12,13,14 etc.
     */
    private String[] tenNineteen;
    /**
     * Used for a keeping such numbers as 10,20,30,40,50 etc.
     */
    private String[] dickers;
    /**
     * Used for a keeping such forms as миллион, миллиард etc.
     */
    private String[][] formsExps;
    /**
     * Contains entered number
     */
    private String amount;
    /**
     * Contains verbal form of number.
     */
    private String verbalNumber;
    /**
     * Contains kinds of numerals.
     */
    private static String[][] kindWord = {
            {"", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"},
            {"", "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"},
    };


    /**
     * Constructor.
     * Initialising the fields with information from files.
     *
     * @Code Processor
     */
    public Processor() {
        DigestReader digest = new DigestReader();
        hundreds = digest.getHundreds();
        tenNineteen = digest.getElevNine();
        dickers = digest.getDickers();
        formsExps = digest.getExps();
        amount = new String("");
        verbalNumber = "";
    }

    /**
     * Checks entered number for a specific symbols and incorrect data
     *
     * @Code checkAmount
     */
    private String checkAmount(String amountIn) {
        if (amountIn.charAt(0) == '-') {
            verbalNumber += "минус ";
            amount = amountIn.substring(1);
        } else {
            amount = amountIn;
        }

        if (amount.equals("0")) {
            verbalNumber = "ноль";
        }
        return amount;
    }

    /**
     * Splits the number to a digits
     *
     * @param amount -number which will be transformed in list od digits.
     * @return segments -list of digits, which will be filled in
     * @code createSegments
     */
    private ArrayList<Long> createSegments(String amount) {
        ArrayList<Long> segments = new ArrayList<Long>();
        if (amount.toString().length() >= 0) {

            String[] numberByDigits = new String[amount.length() % 3 + 1];
            numberByDigits = createDigits(amount, numberByDigits);
            for (int i = numberByDigits.length; i > 0; i--) {
                segments.add(Long.parseLong(numberByDigits[i - 1]));
            }
        }
        return segments;
    }

    /**
     * Transfer the number to a verbal from by digits.
     *
     * @param segments - number by list of digits.
     * @code processing
     */
    private String processing(ArrayList<Long> segments) {
        int lev = segments.size();
        /** iterate segments*/
        for (int i = 0; i < segments.size(); i++) {
            /**identification kind words*/
            int kindDefWord = Integer.valueOf(formsExps[lev - 1][3].toString());
            /** present segment*/
            int digitI = Integer.valueOf(segments.get(i).toString());
            /** if segment equals 000*/
            if (digitI == 0 && lev > 1) {
                lev--;
                continue;
            }
            String rs = String.valueOf(digitI);

            if (rs.length() == 1) rs = "00" + rs;
            if (rs.length() == 2) rs = "0" + rs;
            /**Getting first numeral from digit. Looks like xNN*/
            int numNumerals = Integer.valueOf(rs.substring(0, 1));
            /**Getting second numeral from digit. Looks like NxN*/
            int numDecimals = Integer.valueOf(rs.substring(1, 2));
            /**Getting third numeral from digit. Looks like NNx*/
            int numHundreds = Integer.valueOf(rs.substring(2, 3));
            /**Getting two numerals from digit. Looks like Nxx*/
            int numDickers = Integer.valueOf(rs.substring(1, 3));
            /**Analyzing digits.*/
            if (digitI > 99) verbalNumber += hundreds[numNumerals] + " "; // hundreds
            if (numDickers >= 20) {// >20
                verbalNumber += dickers[numDecimals] + " ";
                verbalNumber += kindWord[kindDefWord][numHundreds] + " ";
            } else { // <=20
                if (numDickers > 9) verbalNumber += tenNineteen[numDickers - 9] + " "; // 10-20
                else verbalNumber += kindWord[kindDefWord][numHundreds] + " "; // 0-9
            }
            lev--;
            verbalNumber += morph(digitI, formsExps[lev][0], formsExps[lev][1], formsExps[lev][2]) + " ";

        }
        return verbalNumber;
    }

    /**
     * Runs algorithm.
     *
     * @param amountIn-entered Number.
     * @return - returns verbal form of number.
     * @code numTOstr.
     */
    public String numTOstr(String amountIn) {

        String amount = checkAmount(amountIn);

        ArrayList<Long> segments = createSegments(amount);

        String verbal = processing(segments);


        return formatStr(verbal).substring(0, formatStr(verbal).length() - 1);
    }


    /**
     * @param number     - entered number in string form.
     * @param discharges - clear array of digits in string form which will be filled in.
     * @code createDigits
     */
    private String[] createDigits(String number, String[] discharges) {
        int ost = number.length() % 3;
        String numberRev = new String();

        switch (ost) {
            case 0: {
                discharges = new String[(number.length() / 3)];
                numberRev = revertString(number) + "0";
                break;
            }
            case 1: {
                discharges = new String[(number.length() / 3) + 1];
                numberRev = revertString(number) + "000";
                break;
            }
            case 2: {
                discharges = new String[(number.length() / 3) + 1];
                numberRev = revertString(number) + "00";
                break;
            }
        }
        int k = 0;
        for (int i = 0; i < discharges.length; i++) {
            discharges[i] = revertString(numberRev.substring(i * 3, k + 3));
            k = k + 3;
        }
        k = 0;
        return (discharges);
    }

    /**
     * Turns over the string.
     *
     * @param straightString - string which needs to be turned off.
     * @code revertString
     */
    private static String revertString(String straightString) {
        String revertString = new String();
        for (int i = straightString.length() - 1; i >= 0; i--) {
            revertString = revertString + straightString.substring(i, i + 1);
        }
        return revertString;
    }

    /**
     * Formatting string for a testing and removing excess spaces.
     *
     * @param stringToFormat - String for a formatting.
     * @code formatStr
     */
    private static String formatStr(String stringToFormat) {
        while (stringToFormat.contains("  ")) {
            String replace = stringToFormat.replace("  ", " ");
            stringToFormat = replace;
        }
        return stringToFormat;
    }

    /**
     * Gets right form.
     *
     * @param genitiveForm - form of word Ex:тысяч, миллионов etc.
     * @param initialForm  - form of word Ex.тысяча,миллион:
     * @param pluralForm-  plural form of word Ex.: тысячи,миллиона etc.
     * @param n            - number, for which form will be selected.
     */
    public static String morph(long n, String initialForm, String pluralForm, String genitiveForm) {
        n = Math.abs(n) % 100;
        long n1 = n % 10;
        if (n > 10 && n < 20) return genitiveForm;
        if (n1 > 1 && n1 < 5) return pluralForm;
        if (n1 == 1) return initialForm;
        return genitiveForm;
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Processor proc = new Processor();
        String inputNumber = proc.numTOstr(in.nextLine());
        System.out.println(inputNumber);
    }
}