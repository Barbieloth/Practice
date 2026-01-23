package net.barbieloth.wordle;

import java.util.*;

public class Main {
    private static int wordLenght = 5;
    private static int maxAttempts = 6;
    private static String secretWord;

    private static final Map<Integer, String[]> DICTIONARY = Map.of(
            5, new String[]{
                    "АЛМАЗ", "БАГАТ", "ВІДЕО", "ГРОШІ", "ВИШНЯ", "ЕКРАН", "ЖИТТЯ", "ЗЕМЛЯ", "ІГРОК", "КНИГА",
                    "ЛИМОН", "МЕТРО", "НАЗВА", "ОКЕАН", "ПІСНЯ", "РАНОК", "СЛОТЬ", "ТЕКСТ", "УВАГА", "ФОТОС"
            },
            6, new String[]{
                    "БАЛКОН", "ВЕКТОР", "ГРАФІК", "ДРАКОН", "КОПИТО", "ЖУРНАЛ", "ЗИМОВИ", "ІСТОРІ", "КАВУНИ", "ЛИСТОК",
                    "МАГНІТ", "НЕБОСХ", "ОБЛИЧЧ", "ПЛАНЕТ", "РОБОТА", "СТЕЖКА", "ТАЛАНТ", "УСМІШК", "ФУТБОЛ", "ХМАРИН"
            },
            7, new String[]{
                    "АВТОБУС", "БУДИНОК", "ВИТРАТИ", "ГРОМАДА", "ДЖЕРЕЛО", "ЕКЗАМЕН", "ЗАПИСКИ", "ІГРАШКА", "КАРТИНА", "ЛЮДСТВО",
                    "МОМЕНТИ", "НАПИСИ", "ОСТРОВИ", "ПРИГОДА", "РЕЦЕПТИ", "СИСТЕМА", "ТЕЛЕФОН", "УЧИТЕЛЬ", "ФОРМАТИ", "ГОРБУША"
            },
            8, new String[]{
                    "АКАДЕМІЯ", "БЕРЕЗЕНЬ", "ВІДПУСТК", "ГОРИЗОНТ", "ДОПОМОГА", "ЕЛЕМЕНТИ", "ЖИВОПИСЬ", "ЗВ'ЯЗОК", "ІНТЕРНЕТ", "КОМП'ЮТЕ",
                    "ЛІТЕРАТУ", "МАГАЗИНИ", "НАВЧАННЯ", "ОПЕРАЦІЯ", "ПРОГРАМА", "РЕЗУЛЬТА", "СТОРІНКА", "ТЕХНІКА", "УКРАЇНА", "ФАНТАЗІЯ"
            }
    );

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BG = "\u001B[42m";
    public static final String ANSI_YELLOW_BG = "\u001B[43m";
    public static final String ANSI_GRAY_BG = "\u001B[100m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true){
            showMainMenu();
            String choise = sc.nextLine();

            switch (choise){
                case "1": startGame(sc); break;
                case "2": openSetting(sc); break;
                case "3": System.exit(0); break;
                default: System.out.println("Невірний вибір!");
            }
        }


    }

    private static void showMainMenu(){
        System.out.println("\n--- WORLDE --- ");
        System.out.println("1. Старт");
        System.out.println("2. Налаштування");
        System.out.println("3. Вихід");
        System.out.print("Оберіть опцію: ");
    }

    private static void openSetting(Scanner sc){
        while(true) {
            System.out.println("\n--- Налаштування --- ");
            System.out.println("1. Довжина слова (5-8, зараз: " + wordLenght + ")");
            System.out.println("2. Макс.спроб (зараз: " + maxAttempts + ")");
            System.out.println("3. Вихід в головне меню");
            System.out.print("Оберіть опцію: ");

            try {
                String choise = sc.nextLine();
                if (choise.equals("1")) {
                    System.out.print("Введіть нову довжину: ");
                    wordLenght = Integer.parseInt(sc.nextLine());
                } else if (choise.equals("2")) {
                    System.out.print("Введіть нову кількість спроб: ");
                    maxAttempts = Integer.parseInt(sc.nextLine());
                } else if (choise.equals("3")) break;
                } catch (NumberFormatException e) {System.out.println("Помилка: введіть число!");}
            }
    }

    private static void clearConsole() {
        System.out.println(new String(new char[50]).replace("\0", "\r\n"));
    }

    private static void drawGrid(List<String> history) {
        clearConsole();
        System.out.println("====== WORDLE ======");

        for (String row : history) {
            System.out.println(row);
        }

        String graySquare = "\u001B[100m   \u001B[0m ";
        for (int i = history.size(); i < maxAttempts; i++) {
            for (int j = 0; j < wordLenght; j++) System.out.print(graySquare);
            System.out.println();
        }
        System.out.println("===================");
    }
    private static void startGame(Scanner scanner) {

        String[] words = DICTIONARY.get(wordLenght);
        secretWord = words[new Random().nextInt(words.length)];

        List<String> history = new ArrayList<>();
        boolean isWon = false;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            drawGrid(history);
            System.out.print("Введіть ваше слово (Спроба " + attempt + " з " + maxAttempts + "): ");

            String guess = scanner.nextLine().toUpperCase();

            if (guess.length() != wordLenght) {
                System.out.println("Помилка! Довжина слова має бути " + wordLenght);
                attempt--;
                continue;
            }

            String feedback = checkWord(guess, secretWord);
            history.add(feedback);

            if (guess.equals(secretWord)) {
                isWon = true;
                drawGrid(history);
                System.out.println("ПЕРЕМОГА! Вгадано за спроб: " + attempt);
                break;
            }
        }

        if (!isWon) {
            drawGrid(history);
            System.out.println("ПРОГРАШ! Загадане слово було: " + secretWord);
        }
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
}
