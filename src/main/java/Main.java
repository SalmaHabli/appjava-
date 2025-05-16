import java.io.*;
import java.util.*;
import java.awt.Desktop;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final String USERS_FILE = "../data/users.txt";
    static final String FILES_FOLDER = "../files/";

    public static void main(String[] args) throws IOException {
        System.out.println("Bienvenue dans l'application !");
        System.out.println("1. Créer un compte");
        System.out.println("2. Se connecter");
        int choix = scanner.nextInt();
        scanner.nextLine(); // consommer le retour à la ligne

        if (choix == 1) {
            registerUser();
        } else if (choix == 2) {
            if (loginUser()) {
                showFiles();
            } else {
                System.out.println("Identifiants incorrects.");
            }
        }
    }

    public static void registerUser() throws IOException {
        System.out.print("Nom d'utilisateur : ");
        String username = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        FileWriter fw = new FileWriter(USERS_FILE, true);
        fw.write(username + ":" + password + "\n");
        fw.close();

        System.out.println("Compte créé avec succès !");
    }

    public static boolean loginUser() throws IOException {
        System.out.print("Nom d'utilisateur : ");
        String username = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts[0].equals(username) && parts[1].equals(password)) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }

    public static void showFiles() {
        File folder = new File(FILES_FOLDER);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("Aucun fichier disponible.");
            return;
        }

        System.out.println("Fichiers disponibles :");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i+1) + ". " + files[i].getName());
        }

        System.out.print("Choisissez un fichier à ouvrir (numéro) : ");
        int choix = scanner.nextInt();
        File selectedFile = files[choix - 1];

        try {
            Desktop.getDesktop().open(selectedFile);
        } catch (IOException e) {
            System.out.println("Impossible d'ouvrir le fichier.");
        }
    }
}
