import java.util.Scanner;

/**
 * Created by rodya on 12.10.16.
 */
public class Main {
    public static void main(String[] args)
    {
    DigestReader temp = new DigestReader();
    Scanner in = new Scanner(System.in);
        String tempStr = new String();
        tempStr = in.nextLine();
        System.out.println("Вывод : " + tempStr);
        System.out.println(temp.getExps()[8]);

        System.out.println(temp.getNumerals()[8]);

        System.out.println(temp.getDickers()[8]);

        System.out.println(temp.getElevNine()[8]);

        System.out.println(temp.getHundreds()[8]);

    }
}
