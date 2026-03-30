package przychodnia.baza.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GeneratorDanychPrzedUruhomieniem implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Serwer Spring Boot działa. Uruchamiam generator danych (.exe)...");

        try {
        	
            // przygotowanie procesu (exe musi byc w glownym katalogu)
        	
        	String os = System.getProperty("os.name").toLowerCase();
            String executableName;

           
            if (os.contains("win")) {
                executableName = "./plik_laczacy.exe"; //windows
            } else {
                //mac/linux
                executableName = "./plik_laczacy";
            }
            ProcessBuilder pb = new ProcessBuilder(executableName);
            
           
            pb.inheritIO(); 

            //start
            Process process = pb.start();

            // czekanie az skonczy
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                System.out.println("Dane zostały pomyślnie zaimportowane do bazy.");
            } else {
                System.out.println("Generator zakończył pracę z kodem błędu: " + exitCode);
            }

        } catch (IOException e) {
            System.err.println("Nie udało się znaleźć lub uruchomić pliku .exe: " + e.getMessage());
        }
    }
}