//WORK IN PROGRESS
//https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
import java.io.IOException;

public class CMD {
    public static void cls() {
//        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("\n".repeat(50));
    }
    //tasklist.exe /fo csv /nh
    public static void tasklist() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "tasklist.exe /fo csv /nh").inheritIO().start().waitFor();
    }
}
class testCMD{
    public static void main(String[] args) throws IOException, InterruptedException {
        CMD.tasklist();
        CMD.cls();
        System.out.println("Testrida");
    }
}