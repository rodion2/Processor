import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by rodya on 12.10.16.
 */
public class DigestReader {
    private String[][] exps = new String[22][3];
    private String[] numerals = new String[8];
    private String[] dickers = new String[7];
    private String[] hundreds = new String[8];
    private String[] elevNine = new String[11];


    /**
     * Reading data from json files in array of strings.
     *
     * @param file - path to the file.
     * @return array of strings with data.
     */
    public String[] readDigest(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (mapper.readValue(file, String[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reading data from json files in two-dimensional array of strings.
     *
     * @param file - path to the file.
     * @return two-dimensional array of strings with data.
     */
    public String[][] readDigest_1(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (mapper.readValue(file, String[][].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getNumerals() {
        return numerals;
    }

    public String[][] getExps() {
        return exps;
    }

    public String[] getDickers() {
        return dickers;
    }

    public String[] getHundreds() {
        return hundreds;
    }

    public String[] getElevNine() {
        return elevNine;
    }

    public DigestReader() {
        exps = this.readDigest_1(new File("src/main/resources/Exponents.json"));
        numerals = this.readDigest(new File("src/main/resources/Numerals.json"));
        dickers = this.readDigest(new File("src/main/resources/Dickers.json"));
        hundreds = this.readDigest(new File("src/main/resources/Hundreds.json"));
        elevNine = this.readDigest(new File("src/main/resources/ElevNine.json"));
    }

}
