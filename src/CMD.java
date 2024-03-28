//WORK IN PROGRESS
//https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.lang.Process;

public class CMD {
    public static void cls() {
//        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("\n".repeat(50));
    }
    //tasklist.exe /fo csv /nh
    public static List<String> tasklist0() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "tasklist.exe /fo csv /nh").inheritIO().start().waitFor();
        return null;
    }
//    https://stackoverflow.com/questions/40194017/get-the-output-result-of-a-process-with-java
    public static List<String> tasklist() throws InterruptedException, IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "tasklist.exe /fo csv /nh").inheritIO();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));
        Thread.sleep(1000); //Sleep for 1 second
        List<String> nimeikiri = new ArrayList<>();
        while (stdInput.ready()) { //While there's something in the buffer
            //read & print - replace with a buffered read (into an array) if the output doesn't contain CR/LF
            nimeikiri.add(stdInput.readLine());
        }
        return nimeikiri;
    }

}
class testCMD{
    public static void main(String[] args) throws IOException, InterruptedException {
        CMD.tasklist();
        CMD.cls();
        System.out.println("Testrida");
    }
}