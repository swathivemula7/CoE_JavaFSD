package week3;

import java.util.Scanner;

public class FeeReportSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Admin Login");
            System.out.println("2. Accountant Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Admin Username: ");
                    String adminUsername = scanner.nextLine();
                    System.out.print("Enter Admin Password: ");
                    String adminPassword = scanner.nextLine();
                    if (Admin.authenticateAdmin(adminUsername, adminPassword)) {
                        System.out.println("Admin logged in successfully!");
                    } else {
                        System.out.println("Invalid Admin credentials.");
                    }
                    break;
                case 2:
                    System.out.print("Enter Accountant Email: ");
                    String accountantEmail = scanner.nextLine();
                    System.out.print("Enter Accountant Password: ");
                    String accountantPassword = scanner.nextLine();
                    if (Accountant.authenticateAccountant(accountantEmail, accountantPassword)) {
                        System.out.println("Accountant logged in successfully!");
                        System.out.println("1. Add Student");
                        System.out.println("2. Check Due Fees");
                        System.out.println("3. View All Students");
                        System.out.print("Enter choice: ");
                        int accChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (accChoice == 1) {
                        	System.out.print("Enter Student Name: ");
                            String studentName = scanner.nextLine();
                            System.out.print("Enter Email: ");
                            String studentEmail = scanner.nextLine();
                            System.out.print("Enter Course: ");
                            String course = scanner.nextLine();
                            System.out.print("Enter Fee: ");
                            double fee = scanner.nextDouble();
                            System.out.print("Enter Amount Paid: ");
                            double paid = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.print("Enter Address: ");
                            String address = scanner.nextLine();
                            System.out.print("Enter Phone: ");
                            String phone = scanner.nextLine();
                            if (Student.addStudent(studentName, studentEmail, course, fee, paid, address, phone)) {
                                System.out.println("Student added successfully.");
                            } else {
                                System.out.println("Error adding student.");
                            }
                        } else if (accChoice == 2) {
                            Student.displayDueFees();
                        } else if (accChoice == 3) {
                            Student.viewAllStudents();
                        }
                    } else {
                        System.out.println("Invalid Accountant credentials.");
                    }
                    break;
                case 3:
                    System.out.println("Terminated");
                    scanner.close();
                    System.exit(0);
            }
        }
    }
}
