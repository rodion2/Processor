import org.junit.*;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by rodya on 17.10.16.
 */
public class ProcessorTest {
    private DigestReader digest;
    private String[][] test;

    @Before
    public void init() {
        digest = new DigestReader();
        test = digest.readDigest_1(new File("src/main/resources/test.json"));
    }

    @After
    public void clearResources() {
        test = null;
        digest = null;
    }

    @Test
    public void minusZero() {
        Processor proc = new Processor();
        String ZERO = new String("минус ноль");
        assertEquals("ошибка, ноль без знака", ZERO, proc.numTOstr("0"));
    }

    @Test
    public void testGetNameAllTable() {

        for (int i = 0; i < test.length; i++) {
            Processor proc = new Processor();
            String testString = proc.numTOstr(test[i][0]);
            assertEquals("ошибка в числе Тестовое число : " + test[i][1] + " Выход программы : " + testString, test[i][1], testString);
        }
    }


}