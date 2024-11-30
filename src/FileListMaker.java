import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileListMaker {
    private static ArrayList<String> list = new ArrayList<>();
    private static Scanner in = new Scanner(System.in);
    private static boolean needsToBeSaved = false;
    private static String currentFileName = null;

    public static void main(String[] args) {
        String menuOption;

        while (true) {
            try {
                displayMenu();
                menuOption = SafeInput.getRegExString(in, "Choose an option [A, D, I, M, V, O, S, C, Q]", "[AaDdIiMmVvOoSsCcQq]");
                switch (menuOption.toUpperCase()) {
                    case "A":
                        addItem();
                        break;
                    case "D":
                        deleteItem();
                        break;
                    case "I":
                        insertItem();
                        break;
                    case "M":
                        moveItem();
                        break;
                    case "V":
                        viewList();
                        break;
                    case "O":
                        loadFile();
                        break;
                    case "S":
                        saveFile();
                        break;
                    case "C":
                        clearList();
                        break;
                    case "Q":
                        if (quitProgram()) return;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nCurrent List");
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + 1) + ". " + list.get(i));
            }
        }
        System.out.println("\nMenu Options:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("M - Move an item");
        System.out.println("V - View the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit the program");
    }

    private static void addItem() {
        String newItem = SafeInput.getNonZeroLenString(in, "Enter the item to add");
        list.add(newItem);
        needsToBeSaved = true;
        System.out.println("Item added.");
    }

    private static void deleteItem() {
        if (list.isEmpty()) {
            System.out.println("The list is empty, nothing to delete.");
            return;
        }
        int itemNumber = SafeInput.getRangedInt(in, "Enter the item number to delete", 1, list.size());
        list.remove(itemNumber - 1);
        needsToBeSaved = true;
        System.out.println("Item deleted.");
    }

    private static void insertItem() {
        int position = SafeInput.getRangedInt(in, "Enter the position to insert the item (1 to " + (list.size() + 1) + ")", 1, list.size() + 1);
        String newItem = SafeInput.getNonZeroLenString(in, "Enter the item to insert");
        list.add(position - 1, newItem);
        needsToBeSaved = true;
        System.out.println("Item inserted.");
    }

    private static void moveItem() {
        if (list.size() < 2) {
            System.out.println("Not enough items to move.");
            return;
        }
        int fromIndex = SafeInput.getRangedInt(in, "Enter the item number to move:", 1, list.size()) - 1;
        int toIndex = SafeInput.getRangedInt(in, "Enter the new position (1 to " + list.size() + "):", 1, list.size()) - 1;

        String item = list.remove(fromIndex);
        list.add(toIndex, item);
        needsToBeSaved = true;
        System.out.println("Item moved.");
    }

    private static void viewList() {
        System.out.println("\nCurrent List");
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + 1) + ". " + list.get(i));
            }
        }
    }

    private static void loadFile() throws IOException {
        if (needsToBeSaved && !confirmSaveBeforeAction()) return;

        String fileName = SafeInput.getNonZeroLenString(in, "Enter the name of the file to load (without extension)") + ".txt";
        Path filePath = Paths.get(fileName);

        if (!Files.exists(filePath)) {
            System.out.println("File not found.");
            return;
        }

        list.clear();
        list.addAll(Files.readAllLines(filePath));
        currentFileName = fileName;
        needsToBeSaved = false;
        System.out.println("File loaded successfully.");
    }

    private static void saveFile() throws IOException {
        if (currentFileName == null) {
            currentFileName = SafeInput.getNonZeroLenString(in, "Enter the name to save the file (without extension)") + ".txt";
        }

        Path filePath = Paths.get(currentFileName);
        Files.write(filePath, list);
        needsToBeSaved = false;
        System.out.println("File saved successfully.");
    }

    private static void clearList() {
        if (SafeInput.getYNConfirm(in, "Are you sure you want to clear the list? (Y/N) ")) {
            list.clear();
            needsToBeSaved = true;
            System.out.println("List cleared.");
        }
    }

    private static boolean quitProgram() throws IOException {
        if (needsToBeSaved) {
            if (SafeInput.getYNConfirm(in, "You have unsaved changes. Save before quitting? (Y/N) ")) {
                saveFile();
            } else if (!SafeInput.getYNConfirm(in, "Are you sure you want to quit without saving? (Y/N) ")) {
                return false;
            }
        }
        System.out.println("Goodbye!");
        return true;
    }

    private static boolean confirmSaveBeforeAction() throws IOException {
        return !needsToBeSaved || SafeInput.getYNConfirm(in, "You have unsaved changes. Save before continuing? (Y/N) ") && saveFileIfNeeded();
    }

    private static boolean saveFileIfNeeded() throws IOException {
        if (needsToBeSaved) {
            saveFile();
            return true;
        }
        return false;
    }
}
