import java.io.*;
import java.util.Scanner;

public class FirstTimeSetup {
    public static void main(String[] args) {
        System.out.println("Programmi praegune versioon ei oska veel sinu kõvakettalt kõiki rakendusi üles leida.");
        System.out.println("Palun sisesta selle kausta absoluutne teekond, mis sisaldab kõiki sinu rakendusi, mida sa soovid, et programm näeb." + "\n");
        System.out.println("Kui sul puudub selline kaust, või ei tea kuidas absoluutset teed leida, siis toimi järgnevalt:");
        System.out.println("    1. Tee töölauale uus kaust. (parem klõps töölaual -> uus kaust)");
        System.out.println("    2. Lohista kõik oma rakendused, soovitatavalt nende otsetee sellesse kausta. (parem klõps rakendusel -> näita rohkem valikuid -> tee otsetee)");
        System.out.println("    3. Kopeeri kausta absoluutne teekond (parem klõps kaustal -> atribuudid -> asukoht -> kopeeri)");
        System.out.println("    4. Programmi sisestades, ära unusta ka kopeeritud asukoha lõppu kirjutada kausta nime.");

        Scanner kirjutaja = new Scanner(System.in);

        String kaustaTee = kirjutaja.nextLine();
        String ebaproduktiivseFailiTee = "ebaproduktiivsedRakendused.txt";

        try {
            FileWriter writer = new FileWriter(ebaproduktiivseFailiTee);
            File kaust = new File(kaustaTee);
            File[] failid = kaust.listFiles();

            for (File fail : failid) {
                String nimi = fail.getName();
                String tee = fail.getAbsolutePath();
                writer.write(nimi + " | " + tee + "\n");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            File ebaproduktiivsedFail = new File(ebaproduktiivseFailiTee);
            while (true) {
                Scanner lugeja = new Scanner(ebaproduktiivsedFail);
                int järjekorraNumber = 1;
                System.out.println("Teie arvutist leiti järgmised rakendused." + "\n");
                while (lugeja.hasNextLine()) {
                    String rida = lugeja.nextLine();
                    String[] tükeldaja = rida.split(" \\| ");
                    String failiNimi = tükeldaja[0];
                    System.out.println("(" + järjekorraNumber + ") " + failiNimi);
                    järjekorraNumber++;
                }
                System.out.println("\n" + "Kui mingi rakendus on nimekirjas üle liia, kirjuta sellele rakendusele vastav järjekorra number." + "\n");
                System.out.println("Kui kõik on korras, kirjuta 'jätka'");
                String kasutajaValik = kirjutaja.nextLine();

                if (kasutajaValik.equals("jätka")) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}