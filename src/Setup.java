//Funktsionaalselt valmis
//Kommentaare juurde vaja
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
        esitaProgrammid(programmid,1);
        //küsi kasutajalt millised programmid nimekirjast eemaldada
        //kuni vastuseni "jätka"
        System.out.print(s6num);
        kasutajaValik = "";
        while(!Objects.equals(kasutajaValik, "jätka")){
            kasutajaValik = reaLugeja.nextLine();
            if(kasutajaValik.equals("jätka")) continue;
            try{
                int valik = Integer.parseInt(kasutajaValik);
                if(valik < 0 || valik > programmid.size()) throw (new Exception());
                programmid.remove(valik-1);
            }catch (Exception e) {
                System.out.print("Vale sisend. Proovi uuesti: ");
                continue;
            }
            CMD.cls();
            esitaProgrammid(programmid,1);
            System.out.print(s6num);
        }

        //küsi millised programmidest on produktiivsed
        //kuni vastuseni "jätka"
        System.out.println("\n");
        List<String> produktiivsed = new ArrayList<>();
        System.out.print("Järgnevalt kategoriseeri rakendused produktiivseteks ja ebaproduktiivseteks sisestades programmi numbri (Enter):");
        kasutajaValik = reaLugeja.nextLine();
        System.out.println("Ebaproduktiivsed");
        esitaProgrammid(programmid,1);
        System.out.println("Produktiivsed");
        esitaProgrammid(produktiivsed, programmid.size() + 1);
        s6num = "Liiguta rakendusi sisestades rakenduse numbri" +
                "\n sisend: ";
        System.out.print(s6num);
        kasutajaValik = "";
        int summa = programmid.size();
        while(!Objects.equals(kasutajaValik, "jätka")){
            int valik;
            kasutajaValik = reaLugeja.nextLine();
            if(kasutajaValik.equals("jätka")) continue;
            try{
                valik = Integer.parseInt(kasutajaValik);
                if(valik <= 0 || valik > summa){
                    throw (new Exception());
                }
                if (valik <= programmid.size()) {
                    produktiivsed.add(programmid.get(valik - 1));
                    programmid.remove(valik - 1);
                }
                else {
                    programmid.add(produktiivsed.get(valik - 1 - programmid.size()));
                    produktiivsed.remove((valik - programmid.size()));
                }
            }catch (Exception e) {
                System.out.print("Vale sisend. Proovi uuesti: ");
                continue;
            }
            CMD.cls();
            System.out.println("Ebaproduktiivsed");
            esitaProgrammid(programmid,1);
            System.out.println("\nProduktiivsed");
            esitaProgrammid(produktiivsed, programmid.size() + 1);
            System.out.print(s6num);
        }

        //salvesta tulemus faili rakendused.txt

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

    private static void esitaProgrammid(List<String> programmid, int algusIndeks){
        String exeNimi;
        System.out.println("*".repeat(50));
        for (String exe : programmid) {
            exeNimi = exe.substring(exe.lastIndexOf('\\') + 1);
            System.out.println("("+algusIndeks+++") "+exeNimi);
        }
        System.out.println("*".repeat(50));
    }
}
