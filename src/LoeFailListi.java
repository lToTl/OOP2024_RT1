import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoeFailListi {
    public static List<String[]> loe(String failiNimi) {
        List<String[]> nimekiri = new ArrayList<>();
        try {
            File sisu = new File(failiNimi);
            Scanner lugeja = new Scanner(sisu, StandardCharsets.UTF_8);
            while (lugeja.hasNextLine()) {
                String rida = lugeja.nextLine();
                String[] andmed = rida.split(";");
                nimekiri.add(andmed);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return nimekiri;
    }
}