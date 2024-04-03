import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//põhiklass
public class JärjekordneProduktiivsuseÄpp {
    public static void main(String[] args) throws IOException {
                //Kontrolli kas rakendused.txt eksisteerib

        File f = new File("rakendused.txt"); //kontrolli log.txt olemasolu ja vajadusel loo see
        if (!f.isFile()) { // kui rakendused.txt on puudu, siis tee setup ja loo rakendused.txt
            Setup.LooRakendusedTXT();
        }
        TaskMonitor taskmonitor = new TaskMonitor();
        List<String[]> programmid = Abi.loeFailListi("rakendused.txt");


        //UI
        Scanner reaLugeja = new Scanner(System.in); //lähesta kasutajaliidese sisend
        String kasutajaValik;
        String päis = " Järjekordne Produktiivsuse Äpp\n" + //salvestame menüü hilisemaks kasutamiseks
                "--------------------------------\n";
        String ridaA = "1. alusta jälgimist\n";
        String ridaB = "1. peata jälgimine\n";
        String valikud = """
                2. "Im feeling lucky" - ava juhuslik rakendus
                3. näita rakendusi
                4. tee setup uuesti
                5. EXIT
                --------------------------------
                """;
        String menüüA = päis + ridaA + valikud; //menüü juhuks kui taskmonitor seisab
        String menüüB = päis + ridaB + valikud; //menüü juhuks kui taskmonitor on aktiivne
        Abi.cls(); //genereeri 50 tühja rida, ehk puhasta konsool
        System.out.print(menüüA);
        näitaSeisu(taskmonitor);
        System.out.print("tee valik: ");
        A: // silt, et programmist väljuda (et 'break' kehtiks while loopi kohta)
        while (true) { //peamenüü loop
            kasutajaValik = reaLugeja.nextLine(); // kasutaja sisend
            switch (kasutajaValik) {
                case "1": { // 1. alusta jälgimist / peata jälgimine
                    Abi.cls();
                    if (!taskmonitor.isRunning()) { // kui taskmonitor ei tööta -
                        Thread lõim = new Thread(taskmonitor); // siis loo uus lõim taskmonitori jaoks
                        lõim.start(); // käivita lõim
                        System.out.print(menüüB);
                    } else { // kui taskmonitor käib -
                        taskmonitor.stop(); // peata taskmonitor
                        System.out.print(menüüA);
                    }
                    näitaSeisu(taskmonitor);
                    System.out.print("tee valik: ");
                    continue;
                }
                case "2": { // 2. "Im feeling lucky"
                    if (taskmonitor.getSkoor() < 1) {
                        System.out.println("Teie skoor on liiga madal, et seda valikut kasutada!");
                    } else {
                        Abi.käivitaSuvaline(programmid);
                    }
                    System.out.print("tee valik: ");
                    continue;
                }
                case "3": { // 3. näita rakendusi
                    Abi.cls();
                    int ebaproduktiivseid = Abi.leiaEbaproduktiivseteArv(programmid);
                    System.out.println("Ebaproduktiivsed");
                    Abi.esitaProgrammidA(programmid.subList(0, ebaproduktiivseid - 1), 1); // esita programmide alamnimekiri kus on ainult ebaproduktiivsed, nummerdamist alusta 1-st)
                    System.out.println("\nProduktiivsed");
                    Abi.esitaProgrammidA(programmid.subList(ebaproduktiivseid - 1, programmid.size()), ebaproduktiivseid); //esita programmide alamnimekiri kus on ainult produktiivsed, jätka nummerdamist viimasest ebaproduktiivsest
                    System.out.println("rakenduse avamiseks sisesta rakendusele vastav number");
                    System.out.println("menüüsse naasmiseks vajuta Enter");
                    System.out.print("tee valik: ");

                    while (true) {
                        kasutajaValik = reaLugeja.nextLine();
                        if (kasutajaValik.isEmpty()) { // kasutaja sisestas Enter
                            Abi.cls();
                            if (taskmonitor.isRunning()) {
                                System.out.print(menüüB);
                            }
                            else {
                                System.out.print(menüüA);
                            }
                            näitaSeisu(taskmonitor);
                            System.out.print("tee valik: ");
                            continue A;
                        }
                        else {
                            try {
                                int valik;
                                valik = Integer.parseInt(kasutajaValik);
                                if (valik > programmid.size() || valik < 1) { // kasutaja valik on massiivist väljas
                                    System.out.print("Vigane sisend. Proovi uuesti: ");
                                } else if (taskmonitor.getSkoor() < 1 && programmid.get(valik - 1)[2].equals("0")) { // kasutaja tahab avada ebaproduktiivset rakendust, kui skoor on alla nulli
                                    System.out.println("\nTeie skoor on liiga madal ebaproduktiivsete rakenduste kasutamiseks!");
                                    System.out.println("Skoori suurendamiseks veeda aega produktiivsetes rakendustes.");
                                    System.out.println("\nrakenduse avamiseks sisesta rakendusele vastav number");
                                    System.out.println("menüüsse naasmiseks vajuta Enter");
                                    System.out.print("tee valik: ");
                                } else {
                                    System.out.print("Peagi avatakse rakendus: " + programmid.get(valik - 1)[0]);
                                    Abi.käivita(programmid.get(valik - 1)[1]);
                                    Thread.sleep(4000); // annab arvutile aega rakendus avada
                                    Abi.cls();
                                    if (taskmonitor.isRunning()) {
                                        System.out.print(menüüB);
                                    }
                                    else {
                                        System.out.print(menüüA);
                                    }
                                    näitaSeisu(taskmonitor);
                                    System.out.print("tee valik: ");
                                    continue A;
                                }
                            } catch (NumberFormatException e) {
                                System.out.print("Vigane sisend. Proovi uuesti: ");
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
//                case "4": { // 4. näita seisu
//                    if (taskmonitor.isRunning()) System.out.print("Jälgimine: aktiivne; ");
//                    else System.out.print("Jälgimine: seisab; ");
//                    System.out.println("Hetke skoor: " + taskmonitor.getSkoor());
//                    System.out.print("tee valik: ");
//                    continue;
//                }
                case "4": { // 5. tee setup uuesti

                    Abi.cls();
                    Setup.LooRakendusedTXT();
                    Abi.cls();
                    if (taskmonitor.isRunning()) {
                        System.out.print(menüüB);
                    }
                    else {
                        System.out.print(menüüA);
                    }
                    näitaSeisu(taskmonitor);
                    System.out.print("tee valik: ");

                    continue;
                }
                case "5": { // 6. EXIT
                    taskmonitor.stop();
                    break A;
                }
                default: {
                    System.out.print("Vigane sisend. Proovi uuesti: ");
                }
            }
            }
        }
        private static void näitaSeisu(TaskMonitor taskmonitor){
            if (taskmonitor.isRunning()) System.out.print("Jälgimine: aktiivne; ");
            else System.out.print("Jälgimine: seisab; ");
            System.out.println("Hetke skoor: " + taskmonitor.getSkoor());
        }

    }
