import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//põhiklass
public class JärjekordneProduktiivsuseÄpp {
    public static void main(String[] args) throws IOException {
                //Kontrolli kas rakendused.txt eksisteerib

        File f = new File("rakendused.txt"); //kontrolli log.txt olemasolu ja vajadusel loo see
        if (!f.isFile()) { // kui rakendused.txt on puudu, siis tee loo see
            //setup
            Setup.LooRakendusedTXT();
        }
        TaskMonitor taskmonitor = new TaskMonitor();
            List<String[]> programmid = Abi.loeFailListi("rakendused.txt");

        //UI
        Scanner reaLugeja = new Scanner(System.in);
        String kastajaValik = "";
        String päis = " Järjekordne Produktiivsuse Äpp\n" +
                "--------------------------------\n";
        String ridaA = "1. alusta jälgimist\n";
        String ridaB = "1. peata jälgimine\n";
        String valikud = "2. \"Im feeling lucky\" - ava juhuslik rakendus\n" +
                "3. näita rakendusi\n" +
                "4. näita seisu\n" +
                "5. tee setup uuesti\n" +
                "6. EXIT\n" +
                "--------------------------------\n" +
                "tee valik: ";
        String menüüA = päis + ridaA + valikud;
        String menüüB = päis + ridaB + valikud;
        Abi.cls();
        System.out.print(menüüA);
        A:
        while (true){
            kastajaValik = reaLugeja.nextLine();
            switch (kastajaValik){
                case "1":{
                    Abi.cls();
                    if (!taskmonitor.isRunning()){
                        Thread lõim = new Thread(taskmonitor);
                        lõim.start();
                        System.out.print(menüüB);
                    }
                    else {
                        taskmonitor.stop();
                        System.out.print(menüüA);
                    }
                    continue;
                }
                case "2":{
                    App.käivitaSuvaline(programmid);
                    System.out.print("tee valik: ");
                    continue;
                }
                case "3":{
                    Abi.cls();
                    int ebaproduktiivseid = Abi.leiaEbaproduktiivseteArv(programmid);
                    System.out.println("Ebaproduktiivsed");
                    Abi.esitaProgrammidA(programmid.subList(0,ebaproduktiivseid-1),1);
                    System.out.println("\nProduktiivsed");
                    Abi.esitaProgrammidA(programmid.subList(ebaproduktiivseid-1,programmid.size()), ebaproduktiivseid);
                    System.out.print("jätkamiseks vajuta Enter");
                    kastajaValik = reaLugeja.nextLine();
                    Abi.cls();
                    if (taskmonitor.isRunning())
                        System.out.print(menüüB);
                    else
                        System.out.print(menüüA);
                    continue;
                }
                case "4":{
                    if(taskmonitor.isRunning()) System.out.print("Jälgimine: aktiivne; ");
                    else System.out.print("Jälgimine: seisab; ");
                    System.out.println("Hetke skoor: " + taskmonitor.getSkoor());
                    System.out.print("tee valik: ");
                    continue;
                }
                case "5":{

                        Abi.cls();
                        Setup.LooRakendusedTXT();
                        Abi.cls();
                        if (taskmonitor.isRunning())
                            System.out.print(menüüB);
                        else
                            System.out.print(menüüA);
                        continue;
                    }
                    case "6": {
                        taskmonitor.stop();
                        break A;
                    }
                    default: {
                        System.out.print("Vigane sisend. Proovi uuesti: ");
                    }
                }
            }
        }

    }
