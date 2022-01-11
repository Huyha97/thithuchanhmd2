package _Manager;

import _Model.Contact;
import _Validate.Validate;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager {
    private final ArrayList<Contact> contactList;
    private final Scanner scanner = new Scanner(System.in);
    private final Validate validate = new Validate();
    public static final String PATH_NAME = "C:\\Users\\This MC\\Desktop\\thiketthucmd3\\src\\contacts.csv";

    public ContactManager() {
        if (new File(PATH_NAME).length() == 0) {
            this.contactList = new ArrayList<>();
        } else {
            this.contactList = readFile(PATH_NAME);
        }
    }

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public void addContact() {
        String phoneNumber = enterPhoneNumber();
        System.out.println("▹ Input group name:");
        String group = scanner.nextLine();
        System.out.println("▹ Input name:");
        String name = scanner.nextLine();
        System.out.println("▹ Input Gender:");
        String gender = scanner.nextLine();
        System.out.println("▹ Input address:");
        String address = scanner.nextLine();
        System.out.println("▹ input date of bitrh(dd-mm-yyyy):");
        String date = scanner.nextLine();
        LocalDate dateOfBirth = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-LL-yyyy"));
        String email = enterEmail();
        for (Contact phone : contactList) {
            if (phone.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("⛔ Duplicate phoneNumber, try again !!!");
                System.out.println("--------------------");
                return;
            }
        }
        Contact contact = new Contact(phoneNumber, group, name, gender, address,dateOfBirth, email);
        contactList.add(contact);
        System.out.println(" Add " + phoneNumber + " successfully !!!");
        System.out.println("-----------------------------------------");
    }

    public void updateContact(String phoneNumber) {
        Contact editContact = null;
        for (Contact contact : contactList) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                editContact = contact;
            }
        }
        if (editContact != null) {
            int index = contactList.indexOf(editContact);
            System.out.println("Input new group name:");
            editContact.setGroup(scanner.nextLine());
            System.out.println("Input new name:");
            editContact.setName(scanner.nextLine());
            System.out.println("▹ Input Gender:");
            String gender = scanner.nextLine();
            editContact.setGender(gender);
            System.out.println("Input new address:");
            editContact.setAddress(scanner.nextLine());
            System.out.println("Input new date of birth(dd-mm-yyyy):");
            String date = scanner.nextLine();
            LocalDate dateOfBirth = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-LL-yyyy"));
            editContact.setDateOfBirth(dateOfBirth);
            editContact.setEmail(enterEmail());
            contactList.set(index, editContact);
            System.out.println("Edit " + phoneNumber + " successfully !!!");
            System.out.println("-----------------------------------------");
        } else {
            System.err.println("Not found these phoneNumber !!!");
            System.out.println("-------------------------------");
        }
    }

    public void deleteContact(String phoneNumber) {
        Contact deleteContact = null;
        for (Contact contact : contactList) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                deleteContact = contact;
            }
        }
        if (deleteContact != null) {
            System.out.println("Are you sure you want to delete?:");
            System.out.println("▹ Y: Yes");
            System.out.println("Different characters: Out");
            String confirm = scanner.next();
            if (confirm.equalsIgnoreCase("y")) {
                contactList.remove(deleteContact);
//                writeFile(contactList, PATH_NAME);
                System.out.println("Delete " + phoneNumber + "Successfully !");
                System.out.println("----------------------------------------");
            }
        } else {
            System.err.println("Not found these phoneNumber !!!");
            System.out.println("-------------------------------");
        }
    }

    public void searchContactByNameOrPhone(String keyword) {
        ArrayList<Contact> contacts = new ArrayList<>();
        for (Contact contact : contactList) {
            if (validate.validatePhoneOrName(keyword, contact.getPhoneNumber()) || validate.validatePhoneOrName(keyword, contact.getName())) {
                contacts.add(contact);
            }
        }
        if (contacts.isEmpty()) {
            System.err.println("Not found these phoneNumber !!!");
            System.out.println("-------------------------------");
        } else {
            System.out.println("PhoneBook need to find: ");
            contacts.forEach(System.out::println);
            System.out.println("------------------------");
        }
    }

    public void displayAll() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("| %-20s| %-10s| %-20s| %-10s| %-10s|\n", "PhoneNumber", "Group", "Name", "Gender", "Address");
        System.out.println("-----------------------------------------------------------------------");
        for (Contact contact : contactList) {
            System.out.printf("| %-20s| %-10s| %-20s| %-10s| %-10s|\n", contact.getPhoneNumber(), contact.getGroup(), contact.getName(), contact.getGender(), contact.getAddress());
            System.out.println("-----------------------------------------------------------------------");
        }
    }

    public String enterPhoneNumber() {
        String phoneNumber;
        while (true) {
            System.out.print("▹ Input phoneNumber: ");
            String phone = scanner.nextLine();
            if (!validate.validatePhone(phone)) {
                System.err.println(" PhoneNUmber invalid!!!");
                System.err.println(" Your PhoneNUmber must has 10 number, start with 0!!!");
                System.out.println("--------------------");
            } else {
                phoneNumber = phone;
                break;
            }
        }
        return phoneNumber;
    }

    private String enterEmail() {
        String email;
        while (true) {
            System.out.print("▹ InputEmail: ");
            String inputEmail = scanner.nextLine();
            if (!validate.validateEmail(inputEmail)) {
                System.err.println("Email Invalid!!!");
                System.out.println("your email must follow these example: a@gmail.com, ab@yahoo.com, abc@hotmail.com");
                System.out.println("--------------------");
            } else {
                email = inputEmail;
                break;
            }
        }
        return email;
    }

    public void writeFile(ArrayList<Contact> contactList, String path) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            for (Contact contact : contactList) {
                bufferedWriter.write(contact.getPhoneNumber() + "," + contact.getGroup() + "," + contact.getName() + "," + contact.getGender() + ","
                + contact.getAddress() + "," + contact.getDateOfBirth() + "," + contact.getEmail() + "\n");
            }
            bufferedWriter.close();
            System.out.println("⛔ Write file successfully !");
            System.out.println("--------------------");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public ArrayList<Contact> readFile(String path) {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(",");
                contacts.add(new Contact(strings[0], strings[1], strings[2], strings[3], strings[4], LocalDate.parse(strings[5], DateTimeFormatter.ISO_LOCAL_DATE), strings[6]));
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return contacts;
    }
}
