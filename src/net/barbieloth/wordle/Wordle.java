package net.barbieloth.wordle;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Wordle {
    private static int wordLenght = 5;
    private static int maxAttempts = 6;
    private static String secretWord;

    private static final Map<Integer, List<String>> DICTIONARY = new HashMap<>();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BG = "\u001B[42m";
    public static final String ANSI_YELLOW_BG = "\u001B[43m";
    public static final String ANSI_GRAY_BG = "\u001B[100m";

    public static void main(String[] args) throws IOException, InterruptedException {

        loadDictionary("words.txt");

         if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "chcp 65001 > nul").inheritIO().start().waitFor();
         }

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);

        while (true){
            clearConsole();
            showMainMenu();
            String choise = sc.nextLine();

            switch (choise){
                case "1": startGame(sc); break;
                case "2": openSetting(sc); break;
                case "3": {
                    System.out.println("Бувай!");
                    System.exit(0);
                }
                default: System.out.println("Невірний вибір!");
            }
        }


    }

    private static void showMainMenu() {
        clearConsole();
        System.out.println("╔════════════════════════╗");
        System.out.println("║         WORDLE         ║");
        System.out.println("╠════════════════════════╣");
        System.out.println("║ 1. Старт               ║");
        System.out.println("║ 2. Налаштування        ║");
        System.out.println("║ 3. Вихід               ║");
        System.out.println("╚════════════════════════╝");
        System.out.print("Оберіть опцію: ");
    }

    private static void openSetting(Scanner sc){
        while(true) {
            clearConsole();
            System.out.println("╔════════════════════════╗");
            System.out.println("║      НАЛАШТУВАННЯ      ║");
            System.out.println("╠════════════════════════╣");
            System.out.println("║ 1. Довжина слова   <" + wordLenght + "> ║");
            System.out.println("║ 2. Макс.спроб      <" + maxAttempts + "> ║");
            System.out.println("║ 3. Вихід в меню        ║");
            System.out.println("╚════════════════════════╝");
            System.out.print("Оберіть опцію: ");

            String choise = sc.nextLine();
            try {
                if (choise.equals("1")) {
                    System.out.print("Введіть нову довжину: ");
                    wordLenght = Integer.parseInt(sc.nextLine());
                } else if (choise.equals("2")) {
                    System.out.print("Введіть нову кількість спроб: ");
                    maxAttempts = Integer.parseInt(sc.nextLine());
                }  else if (choise.equals("3")) break;
            } catch (NumberFormatException e) {System.out.println("Помилка: введіть число!");}
        }
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void drawGrid(List<String> history) {
        clearConsole();
        int contentWidth = (wordLenght * 3) + (wordLenght - 1);
        int totalWidth = contentWidth + 4;
        String line = "═".repeat(totalWidth);

        System.out.println("╔" + line + "╗");

        String title = "WORDLE";
        int padding = (totalWidth - title.length()) / 2;
        System.out.println("║" + " ".repeat(padding) + title + " ".repeat(totalWidth - title.length() - padding) + "║");

        System.out.println("╠" + line + "╣");

        for (String row : history) {
            System.out.println("║  " + row + "  ║");
        }

        String graySquare = ANSI_GRAY_BG + " . " + ANSI_RESET;
        for (int i = history.size(); i < maxAttempts; i++) {
            System.out.print("║  ");
            for (int j = 0; j < wordLenght; j++) {
                System.out.print(graySquare);
                if (j < wordLenght - 1) System.out.print(" ");
            }
            System.out.println("  ║");
        }

        System.out.println("╚" + line + "╝");
    }


    private static void startGame(Scanner scanner) {
        List<String> words = DICTIONARY.get(wordLenght);
        if (words == null || words.isEmpty()) {
            System.out.println("Слів такої довжини не знайдено! Натисніть Enter...");
            scanner.nextLine();
            return;
        }

        secretWord = words.get(new Random().nextInt(words.size()));

        List<String> history = new ArrayList<>();
        boolean isWon = false;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            drawGrid(history);
            System.out.println("Спроба " + attempt + " з " + maxAttempts);
            System.out.print("Введіть слово: ");

            String guess = scanner.nextLine().toUpperCase();

            if (guess.equals("DEBUG")) {System.exit(0);}

            if (guess.length() != wordLenght) {
                System.out.println("Помилка! Довжина слова має бути " + wordLenght + "літер! Натисніть Enter..." );
                scanner.nextLine();
                attempt--;
                continue;
            }

            String feedback = checkWord(guess, secretWord);
            history.add(feedback);

            if (guess.equals(secretWord)) {
                isWon = true;
                break;
            }
        }

        drawGrid(history);
        if (isWon) {
            System.out.println("ПЕРЕМОГА!");
        } else {
            System.out.println("ПРОГРАШ! Загадане слово було: " + secretWord);
        }
        System.out.println("Натисніть Enter, щоб вийти в меню...");
        scanner.nextLine();
    }

    private static String checkWord(String guess, String secret) {
        int len = guess.length();
        String[] result = new String[len];
        boolean[] secretUsed = new boolean[len];
        boolean[] guessUsed = new boolean[len];

        for (int i = 0; i < len; i++) {
            if (guess.charAt(i) == secret.charAt(i)) {
                result[i] = ANSI_GREEN_BG + " " + guess.charAt(i) + " " + ANSI_RESET;
                secretUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        for (int i = 0; i < len; i++) {
            if (guessUsed[i]) continue;

            for (int j = 0; j < len; j++) {
                if (!secretUsed[j] && guess.charAt(i) == secret.charAt(j)) {
                    result[i] = ANSI_YELLOW_BG + " " + guess.charAt(i) + " " + ANSI_RESET;
                    secretUsed[j] = true;
                    guessUsed[i] = true;
                    break;
                }
            }
        }

        for (int i = 0; i < len; i++) {
            if (result[i] == null) {
                result[i] = ANSI_GRAY_BG + " " + guess.charAt(i) + " " + ANSI_RESET;
            }
        }

        return String.join(" ", result);
    }
    public static void loadDictionary(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
        for (String word : lines) {
            word = word.trim().toUpperCase();
            if (word.isEmpty()) continue;

            DICTIONARY.computeIfAbsent(word.length(), k -> new ArrayList<>()).add(word);
        }
    }
}
