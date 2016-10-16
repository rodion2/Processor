import org.junit.Test;

import java.io.File;

/**
 * Created by rodya on 17.10.16.
 */
public class InterpretatorTest {
    @Test
    public void testGetNameAllTable() throws Exception {
    DigestReader readTest = new DigestReader();
        String[][] test = readTest.readDigest_1(new File("/home/rodya/IdeaProjects/Processor/src/main/resources/test.json"));
        for(int i=0;i<test.length;i++)
        {

            if(test[i][1].equals(Interpretator.numTOstr(test[i][0])))
            {
                System.out.println("Тест "+ i +"выполнен успешно");
            }
            else{
                System.out.println("Тест "+ i +"выполнен с ошибкой. Ошибка в числе"+test[i][0]);
            }
        }
    }

}