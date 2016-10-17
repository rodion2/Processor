import org.junit.Test;

import java.io.File;

/**
 * Created by rodya on 17.10.16.
 */
public class InterpretatorTest {
    @Test
    public void testGetNameAllTable() throws Exception {
    DigestReader readTest = new DigestReader();
        String[][] test = readTest.readDigest_1(new File("src/main/resources/test.json"));
        for(int i=0;i<test.length;i++)
        {

            if((test[i][1]).equals(Interpretator.numTOstr(test[i][0])))
            {
                System.out.println("Тест "+ i +" выполнен успешно "+test[i][0]+" "+Interpretator.numTOstr(test[i][0]));
            }
            else{
                System.out.println("Тест "+ i +"выполнен с ошибкой. Ошибка в числе "+test[i][0]);
            }
        }
    }

}