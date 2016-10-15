import java.util.Scanner;

/**
 * Created by rodya on 12.10.16.
 */
public class Main {
    public static void main(String[] args)
    {
    Scanner in = new Scanner(System.in);
       Interpretator string = new Interpretator(in.nextLine());
        String temp = new String();
        temp = string.numTOstr();
        System.out.println(temp);
    }
}
