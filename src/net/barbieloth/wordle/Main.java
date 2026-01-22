package net.barbieloth.wordle;

import java.util.*;

public class Main {
    private static int wordLength = 5;
    private static int maxAttempts = 6;

    private static final String SECRET_WORD = "apple";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BG = "\u001B[42m";
    public static final String ANSI_YELLOW_BG = "\u001B[43m";
    public static final String ANSI_GRAY_BG = "\u001B[100m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            showMenu();
            String choise = sc.nextLine();

            switch (choise) {
                case "1": startGame(sc); break;
                case "2": openSetting(sc); break;
                case "3": System.exit(0); break;
                default:
                    System.out.println("Невірний вибір");
            }
        }

    }

    private static void showMenu() {
        System.out.println("\\n=== WORDLE ===");
        System.out.println("1. Старт");
        System.out.println("2. Налаштування");
        System.out.println("3. Вихід");
        System.out.print("Оберіть опцію: ");
    }

    public static void openSetting(Scanner sc) {
        while (true) {
            System.out.println("\n--- Налаштування ---");
            System.out.println("1. Довжина слова (зараз: " + wordLength + ")");
            System.out.println("2. Макс. спроб (зараз " + maxAttempts + ")");
            System.out.println("3. Назад в головне меню");

            String choice = sc.nextLine();
            if (choice.equals("1")) {
                System.out.println("Введіть нову довжину: ");
                wordLength = Integer.parseInt(sc.nextLine());
            } else if (choice.equals("2")) {
                System.out.println("Введіть нову кількість спроб: ");
                wordLength = Integer.parseInt(sc.nextLine());
            } else if  (choice.equals("3")) break;
        }
    }

    public static void startGame(Scanner sc) {
        List<String> history =  new ArrayList<>();

        while (true) {


            System.out.println("====== WORDLE ======");

            for (String row : history) {
                System.out.println(row);
            }
            for (int i = history.size(); i < maxAttempts; i++) {
                System.out.println("\u001B[48;5;240m   \u001B[0m ".repeat(5));
            }
            System.out.println("====================\n");

            String words = "";
            words = sc.nextLine();
        }
    }
}
