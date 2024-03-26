//Ei tööta, WORK IN PROGRESS
//https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
import java.io.IOException;

public class CMD {
    public static void cls() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}