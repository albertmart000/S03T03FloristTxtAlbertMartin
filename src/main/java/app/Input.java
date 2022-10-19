package app;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    static Scanner sc = new Scanner(System.in);

    public static String askForString(String question) {
        System.out.println(question);
        sc.nextLine();
        return sc.nextLine();
    }

    public static int askForInt(String question) {
        int input;
        do {
            try {
                System.out.println(question);
                input = sc.nextInt();
                break;
            }catch(InputMismatchException e) {
                System.out.println("Entrada no vàlida. Torna a provar-ho.");
                sc.next();
            }
         }while(true);
         return input;
    }

    public static double askForDouble(String question) {
        double input;
        do {
            try {
                System.out.println(question);
                input = sc.nextDouble();
                break;
            }catch(InputMismatchException e) {
                System.out.println("Entrada no vàlida. Torna a provar-ho.");
                sc.next();
            }
        }while(true);
        return input;
    }

    public static boolean askForYesOrNot(String question) {
        boolean result = false;
        do {
            try {
                System.out.println(question);
                int option = sc.nextInt();
                if (option == 1) {
                    result = true;
                } else if (option == 0) {
                    result = false;
                }
            } catch (Exception e) {
                System.out.println("Valor no vàlid. Torna a provar-ho.");
                sc.next();
            }
            return result;
        } while (true);
    }
}