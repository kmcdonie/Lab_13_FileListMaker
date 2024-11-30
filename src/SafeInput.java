import java.util.Scanner;
public class SafeInput {
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = "";
        do {
            System.out.println("\n" + prompt + ": ");
            retString = pipe.nextLine();
        } while (retString.length() == 0);
        return retString;
    }

    public static int getInt(Scanner pipe, String prompt) {
        int retInt = 0;
        boolean isValid = false;
        do {
            System.out.println("\n" + prompt);
            if (pipe.hasNextInt()) {
                retInt = pipe.nextInt();
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter an integer");
                pipe.next();
            }
            pipe.nextLine();
        } while (!isValid);
        return retInt;
    }

    public static double getDouble(Scanner pipe, String prompt) {
        double retDouble = 0;
        boolean isValid = false;
        do {
            System.out.println("\n" + prompt);
            if (pipe.hasNextDouble()) {
                retDouble = pipe.nextDouble();
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter a double.");
                pipe.next();
            }
            pipe.nextLine();
        } while (!isValid);
        return retDouble;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int retInt = 0;
        boolean isValid = false;
        do {
            System.out.println("\n" + prompt + "[" + low + " - " + high + "]: ");
            if (pipe.hasNextInt()) {
                retInt = pipe.nextInt();
                if (retInt >= low && retInt <= high) {
                    isValid = true;
                } else {
                    System.out.println("Invalid output. Please enter an integer within the range.");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                pipe.next();
            }
            pipe.nextLine();
        } while (!isValid);
        return retInt;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double retDouble = 0;
        boolean isValid = false;
        do {
            System.out.println("\n" + prompt + " [" + low + " - " + high + "]:");
            if (pipe.hasNextDouble()) {
                retDouble = pipe.nextDouble();
                if (retDouble >= low && retDouble <= high) {
                    isValid = true;
                } else {
                    System.out.println("Invalid input. Please enter a double within the range.");
                }
            } else {
                System.out.println("Invalid input. Please enter a double.");
                pipe.next(); // Clear the invalid input
            }
            pipe.nextLine(); // Clear the newline character
        } while (!isValid);
        return retDouble;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String response;
        boolean isValid = false;
        boolean retVal = false;
        do {
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = pipe.nextLine().trim().toLowerCase();
            if (response.equals("y")) {
                retVal = true;
                isValid = true;
            } else if (response.equals("n")) {
                retVal = false;
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        } while (!isValid);
        return retVal;
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String response;
        boolean isValid = false;
        do {
            System.out.print("\n" + prompt + ": ");
            response = pipe.nextLine();
            if (response.matches(regEx)) {
                isValid = true;
            } else {
                System.out.println("Invalid input. Please follow the pattern: " + regEx);
            }
        } while (!isValid);
        return response;
    }

    public static void prettyHeader(String msg) {
        int width = 60;
        int padding = (width - 6 - msg.length()) / 2;

        System.out.println("*".repeat(width));
        System.out.print("***");
        System.out.print(" ".repeat(padding));
        System.out.print(msg);
        System.out.print(" ".repeat(width - 6 - padding - msg.length()));
        System.out.println("***");
        System.out.println("*".repeat(width));
    }
}
