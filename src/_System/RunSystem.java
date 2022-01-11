package _System;

import _Manager.ContactManager;
import _Model.Contact;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class RunSystem {
    Scanner scanner = new Scanner(System.in);
    ContactManager contactManager = new ContactManager();

    public RunSystem() {
    }

    public void menuOfSystem() {
        try {
            do {
                System.out.println("----MANAGER PHONEBOOK----");
                System.out.println("1. View PhoneBook list");
                System.out.println("2. Add new contact");
                System.out.println("3. Edit contact");
                System.out.println("4. Delete contact");
                System.out.println("5.Search contact");
                System.out.println("6. Read file");
                System.out.println("7. Write file");
                System.out.println("8. Exit");
                System.out.println("Input your choice:");
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice != 0 && choice != 1 && choice != 2 && choice != 3 && choice != 4
                            && choice != 5 &&
                            choice != 6 && choice != 7 && choice != 8) {
                        System.err.println("only type from 1 to 8:");
                        menuOfSystem();
                        return;
                    }
                } catch (Exception e) {
                    System.err.println("only number is availabe!!!");
                    menuOfSystem();
                    return;
                }
                switch (choice) {
                    case 1:
                        contactManager.displayAll();
                        break;
                    case 2:
                        contactManager.addContact();
                        break;
                    case 3:
                        System.out.println("Input edit PhoneNumber:");
                        String phoneEdit = scanner.nextLine();
                        if (phoneEdit.equals("")) {
                            menuOfSystem();
                        } else {
                            contactManager.updateContact(phoneEdit);
                        }
                        break;
                    case 4:
                        System.out.println("Input Delete PhoneNumber:");
                        String phoneDelete = scanner.nextLine();
                        if (phoneDelete.equals("")) {
                            menuOfSystem();
                        } else {
                            contactManager.deleteContact(phoneDelete);
                        }
                        break;
                    case 5:
                        System.out.println("Input Keyword:");
                        String keyword = scanner.nextLine();
                        contactManager.searchContactByNameOrPhone(keyword);
                        break;
                    case 6:
                        ArrayList<Contact> contactArrayList = contactManager.readFile(ContactManager.PATH_NAME);
                        contactArrayList.forEach(System.out::println);
                        System.out.println("⛔ Read file successfully !");
                        System.out.println("--------------------");
                        break;
                    case 7:
                        contactManager.writeFile(contactManager.getContactList(), ContactManager.PATH_NAME);
                        break;
                    case 8:
                        System.exit(8);
                }
            } while (true);
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println();
            System.err.println("Input wrong, try again !!!");
            System.out.println("--------------------");
            System.out.println();
            menuOfSystem();
        }
    }
}
