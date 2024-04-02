import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Abi {
    //loe ette antud failist read sõnejärjendi massiivi
    public static List<String[]> loeFailListi(String failiNimi) {
        List<String[]> nimekiri = new ArrayList<>();
        try { //proovi avada fail
            File sisu = new File(failiNimi);
            Scanner lugeja = new Scanner(sisu, StandardCharsets.UTF_8);
            while (lugeja.hasNextLine()) { // loe rea kaupa
                String rida = lugeja.nextLine();
                String[] andmed = rida.split(";"); // tükelda rida
                nimekiri.add(andmed);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return nimekiri;
    }
    // väljasta programmide nimekiri lisades ette indeksi kasutajaliidese jaoks
    public static void esitaProgrammid(List<String> programmid, int algusIndeks){
        String exeNimi;
        System.out.println("*".repeat(50));
        for (String exe : programmid) {
            exeNimi = exe.substring(exe.lastIndexOf('\\') + 1);
            System.out.println("("+algusIndeks+++") "+exeNimi);
        }
        System.out.println("*".repeat(50));
    }
    // sama mis eelmine, aga sisendiks on List<String[]> mitte List<String>
    public static void esitaProgrammidA(List<String[]> programmid, int algusIndeks){
        String exeNimi;
        System.out.println("*".repeat(50));
        for (String[] exe : programmid) {
            exeNimi = exe[0];
            System.out.println("("+algusIndeks+++") "+exeNimi);
        }
        System.out.println("*".repeat(50));
    }


    public static int leiaEbaproduktiivseteArv(List<String[]> programmid){ //käib läbi programmide nimekirja kuni leiab esimese produktiivse ja väljastab indeksi
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
    public static void käivita(String path) {
        try {
            File fail = new File(path); // 'File' objekt, esindab faili (praegu steam.app).

            // Käitub nagu desktop (nt kui double click et avada app)
            if (Desktop.isDesktopSupported()) { // Käsurealt tulevad 'permission' errorid niiet see ainukesena töötab
                Desktop desktop = Desktop.getDesktop(); // loob desktop objekti
                desktop.open(fail); // avab appi, muutuja file salvestatud andmete abil
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void käivitaSuvaline(List<String[]> programmid){
        int suvaline = (int) (Math.random()*programmid.size()); //genereeri juhuslik täisarv, mis vastaks ühele programmile nimekirjas
        System.out.println("avati rakendus: " + programmid.get(suvaline)[0]);
        käivita(programmid.get(suvaline)[1]); // käivita juhuslik rakendus
    }
    public static void sulge(String exeNimi){ // sulge

    }
}