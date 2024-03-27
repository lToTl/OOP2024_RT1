//WORK IN PROGRESS
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Setup {
    public static void LooRakendusedTXT () throws IOException, InterruptedException {
        //kaardista programmid
        List<String> programmid = new KaardistaProgrammid().getProgrammid();
        Scanner reaLugeja = new Scanner(System.in);
        String kasutajaValik = "";

        System.out.print("Järgnevalt väljastatakse kõik leitud kasutaja rakendused (Enter):");
        kasutajaValik = reaLugeja.nextLine();
        String s6num = "\nEemalda neutraalsed rakendused, mis ei mõjuta produktiivsust sisestades rakenduse numbri" +
                "\nJärgmisesse etappi liikumiseks sisesta 'jätka'" +
                "\n sisend: ";
        esitaProgrammid(programmid);
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
            esitaProgrammid(programmid);
            System.out.print(s6num);
        }

        //küsi millised programmidest on produktiivsed
        //kuni vastuseni "jätka"
        List<String> produktiivsed = new ArrayList<>();
        s6num = "Vali millised programmidest on produktiivsed sisestades programmi numbri";
        System.out.println(s6num);
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
            esitaProgrammid(programmid);
            System.out.print(s6num);
        }

        //salvesta tulemus faili rakendused.txt
    }

    private static void esitaProgrammid(List<String> programmid){
        int i = 1;
        String nimi = "";
        String exeNimi = "";
        for (String exe : programmid) {
            exeNimi = exe.substring(exe.lastIndexOf('\\') + 1);
            System.out.println("("+i+++") "+exeNimi);
        }
    }
}
