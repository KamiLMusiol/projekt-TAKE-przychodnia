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
            ProcessBuilder pb = new ProcessBuilder("plik_laczacy.exe");
            
            // wypisywanie bledów z ptyhona na naszej konsoli jakby byly i inne napisy
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