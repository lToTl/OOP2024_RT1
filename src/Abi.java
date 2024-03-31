import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Abi {
    public static List<String[]> loeFailListi(String failiNimi) {
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
    public static void esitaProgrammid(List<String> programmid, int algusIndeks){
        String exeNimi;
        System.out.println("*".repeat(50));
        for (String exe : programmid) {
            exeNimi = exe.substring(exe.lastIndexOf('\\') + 1);
            System.out.println("("+algusIndeks+++") "+exeNimi);
        }
        System.out.println("*".repeat(50));
    }
    public static void esitaProgrammidA(List<String[]> programmid, int algusIndeks){
        String exeNimi;
        System.out.println("*".repeat(50));
        for (String[] exe : programmid) {
            exeNimi = exe[0];
            System.out.println("("+algusIndeks+++") "+exeNimi);
        }
        System.out.println("*".repeat(50));
    }
    public static int leiaEbaproduktiivseteArv(List<String[]> programmid){
        for (int i = 0; i < programmid.size(); i++) {
            if (programmid.get(i)[2].equals("1"))
                return i+1;
        }
        return 0;
    }
    public static void cls() { // puhasta konsool
//        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("\n".repeat(50));
    }
}