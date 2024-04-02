import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Setup {
    public static void LooRakendusedTXT () throws IOException {
        //kaardista programmid
        List<String> programmid = new KaardistaProgrammid().getProgrammid();
        Scanner reaLugeja = new Scanner(System.in);
        String kasutajaValik;

        System.out.print("Järgnevalt väljastatakse kõik leitud kasutaja rakendused (Enter):");
        kasutajaValik = reaLugeja.nextLine();
        String s6num = "\nEemalda neutraalsed rakendused, mis ei mõjuta produktiivsust sisestades rakenduse numbri" +
                "\nJärgmisesse etappi liikumiseks sisesta 'jätka'" +
                "\n sisend: ";
        Abi.esitaProgrammid(programmid,1);

        // küsi kasutajalt millised programmid nimekirjast eemaldada
        // kuni vastuseni "jätka"
        System.out.print(s6num);
        kasutajaValik = "";
        while(!Objects.equals(kasutajaValik, "jätka")){ // nullsafe equals
            kasutajaValik = reaLugeja.nextLine();
            if(kasutajaValik.equals("jätka")) continue;
            try{
                int valik = Integer.parseInt(kasutajaValik);
                if(valik < 0 || valik > programmid.size()) throw (new Exception()); // kui valik on võimalik
                programmid.remove(valik-1); // eemalda programm nimekirjast
            }catch (Exception e) { // püüa kinni valed sisendid
                System.out.print("Vale sisend. Proovi uuesti: ");
                continue;
            }
            Abi.cls();
            Abi.esitaProgrammid(programmid,1);
            System.out.print(s6num);
        }

        // küsi millised programmidest on produktiivsed
        // kuni vastuseni "jätka"
        System.out.println("\n");
        List<String> produktiivsed = new ArrayList<>(); // loo uus järjend produktiivsete rakenduste jaoks
        Abi.cls();
        System.out.print("Järgnevalt kategoriseeri rakendused produktiivseteks ja ebaproduktiivseteks sisestades programmi numbri (Enter):");
        kasutajaValik = reaLugeja.nextLine();
        System.out.println("Ebaproduktiivsed");
        Abi.esitaProgrammid(programmid,1);
        System.out.println("Produktiivsed");
        Abi.esitaProgrammid(produktiivsed, programmid.size() + 1);
        s6num = "Liiguta rakendusi sisestades rakenduse numbri. Jätkamiseks sisesta 'jätka'" +
                "\n sisend: ";
        System.out.print(s6num);
        kasutajaValik = "";
        int summa = programmid.size(); // summa lubab meil programme tagasi ka liigutada ebaproduktiivseteks
        while(!Objects.equals(kasutajaValik, "jätka")){ // null-safe equals
            int valik;
            kasutajaValik = reaLugeja.nextLine(); // kasutaja sisend
            if(kasutajaValik.equals("jätka")) continue; // jäta katki kui sisend on jätka
            try{ // proovi muuta sõne täisarvuks
                valik = Integer.parseInt(kasutajaValik);
                if(valik <= 0 || valik > summa){ // kui number ei vasta mõnele programmile, siis anna veateade
                    throw (new Exception());
                }
                if (valik <= programmid.size()) { // kui number vastab mõnele programmile ebaproduktiivsete nimekirjas, siis liiguta see produktiivsete nimekirja
                    produktiivsed.add(programmid.get(valik - 1));
                    programmid.remove(valik - 1);
                }
                else { // kui number vastab mõnele programmile produktiivsete nimekirjas, siis liiguta see ebaproduktiivsete nimekirja
                    programmid.add(produktiivsed.get(valik - 1 - programmid.size()));
                    produktiivsed.remove((valik - programmid.size()));
                }
            }catch (Exception e) {
                System.out.print("Vale sisend. Proovi uuesti: ");
                continue;
            }
            Abi.cls();
            System.out.println("Ebaproduktiivsed");
            Abi.esitaProgrammid(programmid,1);
            System.out.println("\nProduktiivsed");
            Abi.esitaProgrammid(produktiivsed, programmid.size() + 1);
            System.out.print(s6num);
        }

        // salvesta tulemus faili rakendused.txt
        // kujul: exeNimi; exe aadress; 0 kui ebaproduktiivne ja 1 kui produktiivne
        FileWriter fileWriter = new FileWriter("rakendused.txt");
        String exeNimi;
        for (String exe : programmid) {
            exeNimi = exe.substring(exe.lastIndexOf('\\') + 1);
            fileWriter.write(exeNimi + ";" + exe + ";" + "0\n");
        }
        for (String exe : produktiivsed) {
            exeNimi = exe.substring(exe.lastIndexOf('\\') + 1);
            fileWriter.write(exeNimi + ";" + exe + ";" + "1\n");
        }
        fileWriter.close();
    }


}
