import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class Main 
{
    static Scanner scanner = new Scanner(System.in);
    
    // Static arrays to store Car based on their types
    static Car[] sedanCars = new Car[5];
    static Car[] suvCars = new Car[5];
    static Car[] mpvCars = new Car[5];
    
    // Static arrays to store Motorcycle objects based on their cc
    static Motorcycle[] cc150Motorcycles = new Motorcycle[5];
    static Motorcycle[] cc250Motorcycles = new Motorcycle[5];
    
    // Static array to store Customer objects
    static Customer[] cust = new Customer[100];
    static int custCount = 0; // Count of customers
    
    // Static array to store Citizen Customer objects
    static Citizen[] citizen = new Citizen[100];
    static int citizenCount = 0; // Count of citizen
    
    // Static array to store Non-citizen Customer objects
    static NonCitizen[] nonCitizen = new NonCitizen[100];
    static int nonCitizenCount = 0; // Count of non citizen
    
    static Customer customer;// Customer who are currently logged in
    
    // Static array to store Rental objects
    static Rental[] rentals = new Rental[100];
    static int rentalCount = 0; // To keep track of the number of rentals stored
    
    // Static array to store Rental made by customer that currently login
    static Rental[] currentRentals = new Rental[100];
    static int currentRentalsCount = 0; // To keep track of the number of rentals stored
    private static JFrame frame;
    private static JTextArea outputArea;
    private JTextField vehicleField;
    private JTextField rentDateField;
    private JTextField returnDateField;
    private JLabel resultLabel;


    public static void main(String[] args) 
    {
        // Called method to read vehicles from file VehicleAvailable.txt
        readVehiclesFromFile("VehicleAvailable.txt");
        
        // Load existing customer data from file custInfo.txt into cust[] array
        try 
        {
            FileReader fr = new FileReader("custInfo.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            
            while ((line = br.readLine()) != null) {
                // Split the line into customer details using ';' as the delimiter
                String[] details = line.split(";");

                // Ensure that there are enough parts in the split result
                if (details.length < 5) {
                    continue; // Skip this line if it's malformed
                }

                String name = details[0];
                String email = details[1];
                String noPhone = details[2];
                String password = details[3];
                String additionalInfo = details[4];

                // Determine customer type and add to array
                if (isNumeric(additionalInfo)) {
                    cust[custCount] = new Customer(name, noPhone, email, password);
                    citizen[citizenCount] = new Citizen(name, noPhone, email, password, additionalInfo);
                    citizenCount++;
                } else {
                    cust[custCount] = new Customer(name, noPhone, email, password);
                    nonCitizen[nonCitizenCount] = new NonCitizen(name, noPhone, email, password, additionalInfo);
                    nonCitizenCount++;
                }
                custCount++;
            }
            
            // Close the file reader
            br.close();
        } 
        catch (IOException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
        
         // Create the frame with new size (900x700)
        JFrame frame = new JFrame("Rushdi Vehicle Rent");
        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window on the screen
        
        // Set a layout manager
        frame.setLayout(new BorderLayout());

        // Create a panel to hold the title and buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 102, 204)); // Blue background color
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Add space above the title to move it downward
        panel.add(Box.createRigidArea(new Dimension(0, 200))); // Adjust this value to move it lower

        // Create the title label
        JLabel titleLabel = new JLabel("Welcome to Rushdi Vehicle Rent", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40)); // Larger font for the title
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create the signup button
        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.PLAIN, 20));
        signupButton.setBackground(Color.WHITE); // White background
        signupButton.setFocusPainted(false);
        signupButton.setPreferredSize(new Dimension(200, 50)); // Set fixed size for button

        // Create the login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.setBackground(Color.WHITE); // White background
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 50)); // Set fixed size for button

        // Create the exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setBackground(Color.WHITE); // White background
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(200, 50)); // Set fixed size for button

        // Create a new panel for the buttons (side by side)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Align buttons center with 20px horizontal and vertical gaps
        buttonPanel.setBackground(new Color(0, 102, 204)); // Same background as the main panel

        // Add buttons to the button panel
        buttonPanel.add(signupButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        

        // Add action listeners to buttons
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signup();
                
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Thank you for using Rusdi Vehicle Rental Service!");
                System.exit(0);
            }
        });

         // Add components to the main panel
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between title and buttons
        panel.add(buttonPanel); // Add the button panel to the main panel

        // Add the main panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Set the frame visible
        frame.setVisible(true);
    }

    // Homepage
        public static void homePage()
    {
        // Read rental history (assuming the method already exists)
        readRentalHistoryFromFile("RentalHistory.txt");

        // Create the main frame
        JFrame frame = new JFrame("Vehicle Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null); // Center the window

        // Create main panel with GridLayout
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        mainPanel.setBackground(Color.BLACK);

        // Create buttons for each menu option
        JButton rentVehicleButton = new JButton("Rent Vehicle");
        JButton checkAvailabilityButton = new JButton("Check Availability");
        JButton historyButton = new JButton("History");
        JButton returnVehicleButton = new JButton("Return Vehicle");
        JButton logoutButton = new JButton("Logout");

        // Add buttons to the panel
        mainPanel.add(rentVehicleButton);
        mainPanel.add(checkAvailabilityButton);
        mainPanel.add(historyButton);
        mainPanel.add(returnVehicleButton);
        mainPanel.add(logoutButton);

        rentVehicleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createRental();
            }
        });

        checkAvailabilityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Availability button clicked!");
                checkAvailability();
            }
        });

        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "History button clicked!");
                customerRentalHistory();
            }
        });
        
        returnVehicleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnRental();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Log Out button clicked!");
                System.exit(0);
            }
        });

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.setVisible(true);
        }
    
    // Method to read vehicles from a file VehicleAvailable.txt
    public static void readVehiclesFromFile(String fileName) 
    {
        try 
        {
            // Open the file for reading
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            int sedanIndex = 0, suvIndex = 0, mpvIndex = 0;
            int cc150Index = 0, cc250Index = 0;

            // Read each line from the file
            while ((line = br.readLine()) != null) 
            {
                // Split the line into parts by a delimiter (comma-separated values)
                String[] parts = line.split(",");

                if (parts[0].equalsIgnoreCase("Car")) 
                {
                    // Parse Car data
                    String brand = parts[1].trim();
                    String name = parts[2].trim();
                    int year = Integer.parseInt(parts[3].trim());
                    String availability = parts[4].trim();
                    String type = parts[5].trim();

                    // Create a Car object
                    Car car = new Car(brand, name, year, availability, type);

                    // Add the car to the respective array based on its type
                    if (type.equalsIgnoreCase("Sedan") && sedanIndex < sedanCars.length) {
                        sedanCars[sedanIndex++] = car;
                    } else if (type.equalsIgnoreCase("SUV") && suvIndex < suvCars.length) {
                        suvCars[suvIndex++] = car;
                    } else if (type.equalsIgnoreCase("MPV") && mpvIndex < mpvCars.length) {
                        mpvCars[mpvIndex++] = car;
                    }
                } 
                else if (parts[0].equalsIgnoreCase("Motorcycle")) 
                {
                    // Parse Motorcycle data
                    String brand = parts[1].trim();
                    String name = parts[2].trim();
                    int year = Integer.parseInt(parts[3].trim());
                    String availability = parts[4].trim();
                    int cc = Integer.parseInt(parts[5].trim());

                    // Create a Motorcycle object
                    Motorcycle motorcycle = new Motorcycle(brand, name, year, availability, cc);

                    // Add the motorcycle to the respective array based on its CC
                    if (cc > 0 && cc <= 150 && cc150Index < cc150Motorcycles.length) {
                        cc150Motorcycles[cc150Index++] = motorcycle;
                    } 
                    else if (cc > 150 && cc <= 250 && cc250Index < cc250Motorcycles.length) {
                        cc250Motorcycles[cc250Index++] = motorcycle;
                    }
                }
            }

            // Close the file reader
            br.close();
        } 
        catch (IOException e) 
        {
            // Handle file reading errors
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Method to check vehicles that available to rent
    public static void checkAvailability()
    {
       readVehiclesFromFile("VehicleAvailable.txt");
       // Create the main frame
        frame = new JFrame("Vehicle Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10));

        // Add buttons for each category
        JButton sedanButton = new JButton("Check Sedan Cars");
        JButton suvButton = new JButton("Check SUV Cars");
        JButton mpvButton = new JButton("Check MPV Cars");
        JButton cc150Button = new JButton("Check 150cc Motorcycles");
        JButton cc250Button = new JButton("Check 250cc Motorcycles");
        JButton homeButton = new JButton("Go to Home Page");

        buttonPanel.add(sedanButton);
        buttonPanel.add(suvButton);
        buttonPanel.add(mpvButton);
        buttonPanel.add(cc150Button);
        buttonPanel.add(cc250Button);
         buttonPanel.add(homeButton); 
        

        // Create an output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add components to frame
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners to buttons
        sedanButton.addActionListener(e -> sedanAvailability());
        suvButton.addActionListener(e -> suvAvailability());
        mpvButton.addActionListener(e -> mpvAvailability());
        cc150Button.addActionListener(e -> cc150Availability());
        cc250Button.addActionListener(e -> cc250Availability());
        homeButton.addActionListener(e -> homePage());

        // Make the frame visible
        frame.setVisible(true);
        
    }

    // Method to check sedan cars that available to rent
    public static void sedanAvailability()
    {
        outputArea.setText("Sedan Cars:\n");
        int counter = 1;
        boolean noCars = true;
        for (int i = 0; i < sedanCars.length; i++) {
            if (sedanCars[i] != null && sedanCars[i].getAvailability().equalsIgnoreCase("Available")) {
                outputArea.append(counter + ". " + sedanCars[i].getBrand() + " " + sedanCars[i].getName() + "\n");
                counter++;
                noCars = false;
            }
        }

        if (noCars) {
            outputArea.append("No sedan cars available to rent\n");
        }
    }

    // Method to check suv cars that available to rent
    public static void suvAvailability()
    {
        outputArea.setText("SUV Cars:\n");
        boolean noCars = true;
        int counter = 1;

        for (int i = 0; i < suvCars.length; i++) {
            if (suvCars[i] != null && suvCars[i].getAvailability().equalsIgnoreCase("Available")) {
                outputArea.append(counter + ". " + suvCars[i].getBrand() + " " + suvCars[i].getName() + "\n");
                counter++;
                noCars = false;
            }
        }

        if (noCars) {
            outputArea.append("No SUV cars available to rent\n");
        }
    }

    // Method to check mpv cars that available to rent
    public static void mpvAvailability()
    {
        outputArea.setText("MPV Cars:\n");
        boolean noCars = true;
        int counter = 1;

        for (int i = 0; i < mpvCars.length; i++) {
            if (mpvCars[i] != null && mpvCars[i].getAvailability().equalsIgnoreCase("Available")) {
                outputArea.append(counter + ". " + mpvCars[i].getBrand() + " " + mpvCars[i].getName() + "\n");
                counter++;
                noCars = false;
            }
        }

        if (noCars) {
            outputArea.append("No MPV cars available to rent\n");
        }
    }

    // Method to check motorcycle with cc 150 below or equals that available to rent
    public static void cc150Availability()
    {
       outputArea.setText("150cc Motorcycles:\n");
        boolean noMotorcycles = true;
        int counter = 1;

        for (int i = 0; i < cc150Motorcycles.length; i++) {
            if (cc150Motorcycles[i] != null && cc150Motorcycles[i].getAvailability().equalsIgnoreCase("Available")) {
                outputArea.append(counter + ". " + cc150Motorcycles[i].getBrand() + " " + cc150Motorcycles[i].getName() + "\n");
                counter++;
                noMotorcycles = false;
            }
        }

        if (noMotorcycles) {
            outputArea.append("No 150cc motorcycles available to rent\n");
        }
    }

    // Method to check motorcycle with cc 250 below or equals that available to rent
    public static void cc250Availability()
    {
        outputArea.setText("250cc Motorcycles:\n");
        boolean noMotorcycles = true;
        int counter = 1;

        for (int i = 0; i < cc250Motorcycles.length; i++) {
            if (cc250Motorcycles[i] != null && cc250Motorcycles[i].getAvailability().equalsIgnoreCase("Available")) {
                outputArea.append(counter + ". " + cc250Motorcycles[i].getBrand() + " " + cc250Motorcycles[i].getName() + "\n");
                counter++;
                noMotorcycles = false;
            }
        }

        if (noMotorcycles) {
            outputArea.append("No 250cc motorcycles available to rent\n");
        }
    }
    
     // Signup method
        public static void signup() 
    {
            JPanel panel = new JPanel();
            JFrame frame = new JFrame("Signup");
            frame.setSize(600, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            panel.setLayout(null);
            frame.setLocationRelativeTo(null);

            // Name label and text field
            JLabel namelabel = new JLabel("Name");
            namelabel.setBounds(10, 20, 80, 25);
            panel.add(namelabel);

            JTextField userText = new JTextField(20);
            userText.setBounds(100, 20, 165, 25);
            panel.add(userText);

            // Phone number label and text field
            JLabel phonelabel = new JLabel("PhoneNumber");
            phonelabel.setBounds(10, 50, 80, 25);
            panel.add(phonelabel);

            JTextField phoneNumber = new JTextField(20);
            phoneNumber.setBounds(100, 50, 165, 25);
            panel.add(phoneNumber);

            // Email label and text field
            JLabel emaillabel = new JLabel("Email");
            emaillabel.setBounds(10, 80, 80, 25);
            panel.add(emaillabel);

            JTextField email = new JTextField(20);
            email.setBounds(100, 80, 165, 25);
            panel.add(email);

            // Password label and text field
            JLabel passwordLabel = new JLabel("Password");
            passwordLabel.setBounds(10, 110, 80, 25);
            panel.add(passwordLabel);

            JPasswordField passwordField = new JPasswordField(20);
            passwordField.setBounds(100, 110, 165, 25);
            panel.add(passwordField);

            // Citizen/Non-citizen label and dropdown
            JLabel citizenLabel = new JLabel("Are you a citizen?");
            citizenLabel.setBounds(10, 140, 120, 25);
            panel.add(citizenLabel);

            JComboBox<String> citizenDropdown = new JComboBox<>(new String[]{"Yes", "No"});
            citizenDropdown.setBounds(140, 140, 60, 25);
            panel.add(citizenDropdown);

            // Additional info label and text field (hidden initially)
            JLabel additionalInfoLabel = new JLabel();
            additionalInfoLabel.setBounds(10, 170, 200, 25);
            additionalInfoLabel.setVisible(false);
            panel.add(additionalInfoLabel);

            JTextField additionalInfoField = new JTextField(20);
            additionalInfoField.setBounds(200, 170, 165, 25);
            additionalInfoField.setVisible(false);
            panel.add(additionalInfoField);

            // Submit button
            JButton submitButton = new JButton("Submit");
            submitButton.setBounds(10, 210, 120, 25);
            panel.add(submitButton);

                // Add event listener for citizenship dropdown
                citizenDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) citizenDropdown.getSelectedItem();
                if (selected.equalsIgnoreCase("Yes")) {
                    additionalInfoLabel.setText("Enter Citizen ID:");
                } else {
                    additionalInfoLabel.setText("Enter Passport Number:");
                }
                additionalInfoLabel.setVisible(true);
                additionalInfoField.setVisible(true);
            }
            });


            // Action listener for button
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                String name = userText.getText();
                String phone = phoneNumber.getText();
                String emailText = email.getText();
                String password = new String(passwordField.getPassword());
                String isCitizen = (String) citizenDropdown.getSelectedItem();;
                String additionalInfo = additionalInfoField.getText();

                // Check if email already exists
                for (int i = 0; i < custCount; i++) {
                    if (cust[i].getEmail().equalsIgnoreCase(emailText)) {
                        JOptionPane.showMessageDialog(frame, "Email already exists. Please try logging in.");
                        return;
                    }
                }

                // Process citizen or non-citizen
                if (isCitizen.equalsIgnoreCase("yes")) {
                    cust[custCount] = new Customer(name, phone, emailText, password);
                    citizen[citizenCount] = new Citizen(name, phone, emailText, password, additionalInfo);
                    citizenCount++;
                } else {
                    cust[custCount] = new Customer(name, phone, emailText, password);
                    nonCitizen[nonCitizenCount] = new NonCitizen(name, phone, emailText, password, additionalInfo);
                    nonCitizenCount++;
                }
                custCount++;

                // Write to file
                try (FileWriter fw = new FileWriter("custInfo.txt", true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter pw = new PrintWriter(bw)) {
                    pw.println(name + ";" + emailText + ";" + phone + ";" + password + ";" + additionalInfo);
                    JOptionPane.showMessageDialog(frame, "Signup successful!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error writing to file: " + ex.getMessage());
                }
                login();
            }
                });

            frame.setVisible(true);
    }
    
    // Login method
    public static void login() 
    {
        try 
        {
            FileReader fr = new FileReader("custInfo.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            
            while ((line = br.readLine()) != null) {
                // Split the line into customer details using ';' as the delimiter
                String[] details = line.split(";");

                // Ensure that there are enough parts in the split result
                if (details.length < 5) {
                    continue; // Skip this line if it's malformed
                }

                String name = details[0];
                String email = details[1];
                String noPhone = details[2];
                String password = details[3];
                String additionalInfo = details[4];

                // Determine customer type and add to array
                if (isNumeric(additionalInfo)) {
                    cust[custCount] = new Customer(name, noPhone, email, password);
                    citizen[citizenCount] = new Citizen(name, noPhone, email, password, additionalInfo);
                    citizenCount++;
                } else {
                    cust[custCount] = new Customer(name, noPhone, email, password);
                    nonCitizen[nonCitizenCount] = new NonCitizen(name, noPhone, email, password, additionalInfo);
                    nonCitizenCount++;
                }
                custCount++;
            }
            
            // Close the file reader
            br.close();
        } 
        catch (IOException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
        JFrame frame = new JFrame("Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        // Email label and text field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 20, 80, 25);
        panel.add(emailLabel);

        JTextField emailField = new JTextField(20);
        emailField.setBounds(100, 20, 200, 25);
        panel.add(emailField);

        // Password label and text field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 60, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 60, 200, 25);
        panel.add(passwordField);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 100, 100, 25);
        panel.add(loginButton);

        // Message label
        JLabel messageLabel = new JLabel("");
        messageLabel.setBounds(10, 140, 350, 25);
        panel.add(messageLabel);

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                // Check if email and password match any customer
                boolean loginSuccessful = false;
                for (int i = 0; i < custCount; i++) {
                    if (cust[i].getEmail().equalsIgnoreCase(email) && cust[i].getPassword().equals(password)) {
                        loginSuccessful = true;
                        customer = cust[i];
                        messageLabel.setText("Login successful! Welcome " + customer.getName());
                        JOptionPane.showMessageDialog(frame, "Welcome " + customer.getName() + "!");
                        frame.dispose(); // Close the login window
                        // Redirect to main application or dashboard
                        homePage();// Replace with your main screen
                        return;
                    }
                }

                if (!loginSuccessful) {
                    messageLabel.setText("Invalid email or password. Please try again.");
                }
            }
        });

        frame.setVisible(true);
    }
    
    
    // Helper function to check if a string is numeric
    public static boolean isNumeric(String str) 
    {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // Rental page
    public static void createRental() {
        // Initialize JFrame
        JFrame frame = new JFrame("Create Rental");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new GridLayout(7, 2, 10, 10));
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        frame.setLocationRelativeTo(null);


        // Components for vehicle input
        JLabel vehicleLabel = new JLabel("Enter vehicle brand and name:");
        JTextField vehicleField = new JTextField();

        // Components for rent date input
        JLabel rentDateLabel = new JLabel("Enter rent date (YYYY-MM-DD):");
        JTextField rentDateField = new JTextField();

        // Components for return date input
        JLabel returnDateLabel = new JLabel("Enter return date (YYYY-MM-DD):");
        JTextField returnDateField = new JTextField();

        // Buttons
        JButton submitButton = new JButton("Submit");
        JButton resetButton = new JButton("Reset");
        JButton homeButton = new JButton("Go to Homepage");

        // Add components to the frame
        frame.add(vehicleLabel);
        frame.add(vehicleField);
        frame.add(rentDateLabel);
        frame.add(rentDateField);
        frame.add(returnDateLabel);
        frame.add(returnDateField);
        frame.add(submitButton);
        frame.add(resetButton);
        frame.add(homeButton);

        // Submit button action listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rentedVehicle = vehicleField.getText().trim();
                String rentDateText = rentDateField.getText().trim();
                String returnDateText = returnDateField.getText().trim();

                try {
                    if (rentDateText.isEmpty() || returnDateText.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter both rent and return dates.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    LocalDate rentDate = LocalDate.parse(rentDateText, DateTimeFormatter.ISO_LOCAL_DATE);
                    LocalDate returnDate = LocalDate.parse(returnDateText, DateTimeFormatter.ISO_LOCAL_DATE);

                    if (!returnDate.isAfter(rentDate)) {
                        JOptionPane.showMessageDialog(frame, "Return date (" + returnDateText + ") must be after rent date (" + rentDateText + ").", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Create rental record
                    Rental rental = new Rental(customer.getEmail(), rentDate, returnDate, rentedVehicle);

                    // Add rental record to rental array
                    rentals[rentalCount] = rental;
                    rentalCount++;

                    // Display rental information
                    JOptionPane.showMessageDialog(frame, rental.toString(), "Rental Information", JOptionPane.INFORMATION_MESSAGE);

                    // Calculate price
                    double price = clacPrice(rental);
                    double finalPrice = citizenPrice(rental, price);

                    JOptionPane.showMessageDialog(frame, String.format("Total Price: RM%.2f\nFinal Price: RM%.2f", price, finalPrice), "Price Details", JOptionPane.INFORMATION_MESSAGE);

                    // Payment confirmation
                    String confirmation = JOptionPane.showInputDialog(frame, "Make payment to account: 3354975081 MUHAMMAD RUSDI BIN MARKO RHB Bank.\nType 'done' after payment:");

                    if ("done".equalsIgnoreCase(confirmation)) {
                        // Add rental record to current rentals
                        currentRentals[currentRentalsCount++] = rental;
                        currentRentalsCount++;
                        updateRentalListDisplay(displayArea);

                        // Write rental data to file
                        try (FileWriter fw = new FileWriter("RentalHistory.txt", true);
                             BufferedWriter bw = new BufferedWriter(fw);
                             PrintWriter pw = new PrintWriter(bw)) {
                            pw.println(customer.getEmail() + "," + rentDate + "," + returnDate + "," + rentedVehicle);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(frame, "Error writing to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        // Update vehicle availability
                        boolean isUpdated = updateVehicleAvailability(rentedVehicle);
                        if (!isUpdated) {
                            JOptionPane.showMessageDialog(frame, "Error: Vehicle not available.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Refresh vehicle data
                        readVehiclesFromFile("VehicleAvailable.txt");

                        JOptionPane.showMessageDialog(frame, "Rental successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please complete the payment.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Reset fields after successful submission
                    vehicleField.setText("");
                    rentDateField.setText("");
                    returnDateField.setText("");

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Reset button action listener
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vehicleField.setText("");
                rentDateField.setText("");
                returnDateField.setText("");
            }
        });
        
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Assuming you have a method to open the homepage
                homePage();
            }
        });

        // Make frame visible
        frame.setVisible(true);
    }
    
    // Method for update vehicle availability from available to not available 
    private static boolean updateVehicleAvailability(String rentedVehicle) 
    {
        boolean isUpdated = false;
        List<String> updatedFileContent = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader("VehicleAvailable.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] vehicleDetails = line.split(","); // Assuming the file uses a CSV format
                String brandAndName = vehicleDetails[1] + " " + vehicleDetails[2];
                String availability = vehicleDetails[4];
    
                if (brandAndName.equalsIgnoreCase(rentedVehicle) && availability.equalsIgnoreCase("Available")) {
                    vehicleDetails[4] = "Not Available"; // Update availability
                    isUpdated = true;
                }
    
                updatedFileContent.add(String.join(",", vehicleDetails));
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return false;
        }
    
        if (isUpdated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("VehicleAvailable.txt"))) {
                for (String line : updatedFileContent) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing to the file: " + e.getMessage());
                return false;
            }
        }
    
        return isUpdated;
    }
    
    // Method to display rented vehicle information
    public static void displayRentedVehicle(Rental rental)
    {
        // Check if rented vehicle is sedan car
        for (int i = 0; i < sedanCars.length; i++) {
            if (sedanCars[i] != null && rental.getRentedVehicle().equalsIgnoreCase(sedanCars[i].getBrand() + " " + sedanCars[i].getName())) {
                System.out.println(sedanCars[i].toString());
                return;
            }
        }
    
        // Check if rented vehicle is SUV car
        for (int i = 0; i < suvCars.length; i++) {
            if (suvCars[i] != null && rental.getRentedVehicle().equalsIgnoreCase(suvCars[i].getBrand() + " " + suvCars[i].getName())) {
                System.out.println(suvCars[i].toString());
                return;
            }
        }
    
        // Check if rented vehicle is MPV car
        for (int i = 0; i < mpvCars.length; i++) {
            if (mpvCars[i] != null && rental.getRentedVehicle().equalsIgnoreCase(mpvCars[i].getBrand() + " " + mpvCars[i].getName())) {
                System.out.println(mpvCars[i].toString());
                return;
            }
        }
    
        // Check if rented vehicle is motorcycle with 150cc below
        for (int i = 0; i < cc150Motorcycles.length; i++) {
            if (cc150Motorcycles[i] != null && rental.getRentedVehicle().equalsIgnoreCase(cc150Motorcycles[i].getBrand() + " " + cc150Motorcycles[i].getName())) {
                System.out.println(cc150Motorcycles[i].toString());
                return;
            }
        }
    
        // Check if rented vehicle is motorcycle with 250cc below
        for (int i = 0; i < cc250Motorcycles.length; i++) {
            if (cc250Motorcycles[i] != null && rental.getRentedVehicle().equalsIgnoreCase(cc250Motorcycles[i].getBrand() + " " + cc250Motorcycles[i].getName())) {
                System.out.println(cc250Motorcycles[i].toString());
                return;
            }
        }
    
        // If no match is found
        System.out.println("Rented vehicle details not found.");
    }
    
    // Method to calculate price based on price per day and total days
    public static double clacPrice(Rental rental) 
    {
        if (rental.getRentedVehicle() != null) {
            // Check if rented vehicle is a sedan car
            for (int i = 0; i < sedanCars.length; i++) {
                if (sedanCars[i] != null && rental.getRentedVehicle().equalsIgnoreCase(sedanCars[i].getBrand() + " " + sedanCars[i].getName())) {
                    return sedanCars[i].detPrice() * rental.calculateTotalDays();
                }
            }
    
            // Check if rented vehicle is an SUV car
            for (int i = 0; i < suvCars.length; i++) {
                if (suvCars[i] != null && rental.getRentedVehicle().equalsIgnoreCase(suvCars[i].getBrand() + " " + suvCars[i].getName())) {
                    return suvCars[i].detPrice() * rental.calculateTotalDays();
                }
            }
    
            // Check if rented vehicle is an MPV car
            for (int i = 0; i < mpvCars.length; i++) {
                if (mpvCars[i] != null && rental.getRentedVehicle().equalsIgnoreCase(mpvCars[i].getBrand() + " " + mpvCars[i].getName())) {
                    return mpvCars[i].detPrice() * rental.calculateTotalDays();
                }
            }
    
            // Check if rented vehicle is a motorcycle with 150cc below
            for (int i = 0; i < cc150Motorcycles.length; i++) {
                if (cc150Motorcycles[i] != null && rental.getRentedVehicle().equalsIgnoreCase(cc150Motorcycles[i].getBrand() + " " + cc150Motorcycles[i].getName())) {
                    return cc150Motorcycles[i].detPrice() * rental.calculateTotalDays();
                }
            }
    
            // Check if rented vehicle is a motorcycle with 250cc below
            for (int i = 0; i < cc250Motorcycles.length; i++) {
                if (cc250Motorcycles[i] != null && rental.getRentedVehicle().equalsIgnoreCase(cc250Motorcycles[i].getBrand() + " " + cc250Motorcycles[i].getName())) {
                    return cc250Motorcycles[i].detPrice() * rental.calculateTotalDays();
                }
            }
        } else {
            System.out.println("\nError: No rented vehicle provided to calculate the price!");
        }
    
        return 0; // Return 0 if no match is found or there is an error
    }
    
    // Method to calculate final price based on customer citizenship
    public static double citizenPrice(Rental rental, double price) 
    {
        if (rental != null && rental.getTenantEmail() != null) {
            // Check if the customer is a citizen
            for (int i = 0; i < citizen.length; i++) {
                if (citizen[i] != null && rental.getTenantEmail().equalsIgnoreCase(citizen[i].getEmail())) {
                    System.out.println("\nYou are a citizen, therefore you get a 5% discount.");
                    return price * 0.95;
                }
            }
    
            // Check if the customer is a non-citizen
            for (int i = 0; i < nonCitizen.length; i++) {
                if (nonCitizen[i] != null && rental.getTenantEmail().equalsIgnoreCase(nonCitizen[i].getEmail())) {
                    System.out.println("\nYou are a non-citizen, therefore you must pay a 3% interest.");
                    return price + (price * 0.03);
                }
            }
    
            // If the email is not found in either list
            System.out.println("\nError: Customer email not found in citizen or non-citizen records.");
        } else {
            System.out.println("\nError: Invalid rental or tenant email.");
        }
    
        return price; // Default return price without modification if no match is found
    }
    
    // Method to read rental history from a file RentalHistory.txt
    public static void readRentalHistoryFromFile(String fileName) 
    {
        rentalCount=0;
        currentRentalsCount=0;
        
        try (FileReader fr = new FileReader(fileName); BufferedReader br = new BufferedReader(fr)) 
        {
            String line;
    
            while ((line = br.readLine()) != null) {
                String[] part = line.split(",");
                if (part.length < 4) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
    
                String tenantEmail = part[0];
                LocalDate rentDate = LocalDate.parse(part[1]);
                LocalDate returnDate = LocalDate.parse(part[2]);
                String rentedVehicle = part[3];
    
                rentals[rentalCount] = new Rental(tenantEmail, rentDate, returnDate, rentedVehicle);
                rentalCount++;
    
                if (customer.getEmail().equalsIgnoreCase(tenantEmail)) {
                    currentRentals[currentRentalsCount] = new Rental(tenantEmail, rentDate, returnDate, rentedVehicle);
                    currentRentalsCount++;
                    boolean alreadyExists = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // Method to display all rentals that the current logged-in customer has made
    public static void customerRentalHistory() 
    {
         // Create the main frame
            JFrame frame = new JFrame("Rental History");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        // Create the panel for displaying rentals
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Customer Rental History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea rentalTextArea = new JTextArea();
        rentalTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(rentalTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton refreshButton = new JButton("Refresh");
        JButton homepageButton = new JButton("Go to Homepage");

        buttonPanel.add(refreshButton);
        buttonPanel.add(homepageButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        JLabel messageLabel = new JLabel("Kindly click refresh first to refresh the system", SwingConstants.CENTER);
        frame.add(messageLabel, BorderLayout.NORTH);

        frame.add(panel);

        // Define action for refresh button
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rentalTextArea.setText(""); // Clear the text area
                int count = 1;

                if (currentRentalsCount == 0) {
                    rentalTextArea.setText("No vehicles rented.");
                } else {
                    boolean found = false;
                    for (Rental rental : currentRentals) {
                        if (customer.getEmail().equalsIgnoreCase(rental.getTenantEmail())) {
                            rentalTextArea.append("Rental " + count + ": " + rental + "\n");
                            count++;
                            found = true;
                        }
                    }
                    if (!found) {
                        rentalTextArea.setText("No vehicles rented.");
                    }
                }
            }
        });
            homepageButton.addActionListener(new ActionListener() {
                @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                homePage(); // Call the homepage method (make sure it exists in your program)
            }
        });

        // Show the frame
        frame.setVisible(true);
    }
    
    // Return vehicle page
    public static void returnRental() 
    {
        int counter = 1;
        
        JFrame frame = new JFrame("Return Rental Vehicle");
        JTextArea displayArea = new JTextArea(10, 30);
        JTextField vehicleInputField = new JTextField(20);
        JButton returnButton = new JButton("Return Vehicle");
        JButton homeButton = new JButton("Go to Homepage"); // New button

        // Set up the layout of the frame
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        
        // Add components to the frame
        frame.add(new JLabel("Vehicles Currently Rented:"));
        frame.add(scrollPane);
        frame.add(new JLabel("Enter the brand and name of the vehicle:"));
        frame.add(new JLabel("Kindly click return vehicle first to refresh before entering vehicle name"));
        frame.add(vehicleInputField);
        frame.add(returnButton);
        frame.add(homeButton); // Add the "Go to Homepage" button

        // Set up the button click listener
            returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int counter = 1;
                // Check if there are any current rentals
                if (currentRentalsCount == 0) {
                    displayArea.setText("\nNo vehicles rented.");
                } else {
                    StringBuilder rentalsList = new StringBuilder("\nHere are the vehicles you rented:\n");
                    // Loop through and display the vehicles rented
                    for (int i = 0; i < currentRentalsCount; i++) {
                        rentalsList.append(counter).append(". ").append(counter + ". " + currentRentals[i].getRentedVehicle()).append("\n");
                        counter++;
                    }
                    displayArea.setText(rentalsList.toString());
                    
                    // Get the user's input for the returned vehicle
                    String returnedVehicle = vehicleInputField.getText().trim();
                    
                    if (returnedVehicle.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter the brand and name of the vehicle you are returning.");
                        return;
                    }
                    
                    // Update the vehicle's availability
                    boolean isUpdated = returnVehicle(returnedVehicle);
                
                    if (!isUpdated) {
                        JOptionPane.showMessageDialog(frame, "Error: " + returnedVehicle + " is not currently rented or does not exist.");
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(frame, "Thank you! " + returnedVehicle + " has been returned successfully.\nThe vehicle is now available for rental.");
                    
                    // Remove the rental record from RentalHistory.txt
                    deleteRecordFromFile("RentalHistory.txt", returnedVehicle);

                    // Update the `rentals` and `currentRentals` arrays
                    readRentalHistoryFromFile("RentalHistory.txt");
                    
                    // Update the vehicles in the available vehicles list
                    readVehiclesFromFile("VehicleAvailable.txt");
                }
            }
        });
        homeButton.addActionListener(new ActionListener() {
                @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                homePage(); // Call the homepage method (make sure it exists in your program)
            }
        });
        // Show the frame
        frame.setVisible(true);
    }
    
    public static void updateRentalListDisplay(JTextArea displayArea) {
        StringBuilder rentalsList = new StringBuilder();
        int counter = 1; // To number the rentals

         // Loop through all current rentals
        for (int i = 0; i < currentRentalsCount; i++) {
        if (currentRentals[i] != null) {
            rentalsList.append(counter)
                       .append(". ")
                       .append(currentRentals[i].getRentedVehicle())
                       .append("\n");
            counter++;
        }
        }

        // Update the JTextArea (displayArea) with the rental list
        displayArea.setText(rentalsList.toString());
    }
    // Method for update return vehicle availability from not available to available 
    private static boolean returnVehicle(String returnedVehicle) 
    {
        boolean isUpdated = false;
        List<String> updatedFileContent = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader("VehicleAvailable.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] vehicleDetails = line.split(","); // Assuming the file uses a CSV format
                String brandAndName = vehicleDetails[1] + " " + vehicleDetails[2];
                String availability = vehicleDetails[4];
    
                if (brandAndName.equalsIgnoreCase(returnedVehicle) && availability.equalsIgnoreCase("Not Available")) {
                    vehicleDetails[4] = "Available"; // Update availability
                    isUpdated = true;
                }
    
                updatedFileContent.add(String.join(",", vehicleDetails));
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return false;
        }
    
        if (isUpdated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("VehicleAvailable.txt"))) {
                for (String line : updatedFileContent) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing to the file: " + e.getMessage());
                return false;
            }
        }
    
        return isUpdated;
    }
    
    // Method to delete record from file
    public static void deleteRecordFromFile(String fileName, String returnedVehicle) {
        File inputFile = new File(fileName);
        File tempFile = new File("temp.txt");
    
        boolean isDeleted = false;
    
        try (
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String currentLine;
    
            // Read each line from the file
            while ((currentLine = reader.readLine()) != null) {
                // Check if the line contains the returned vehicle
                if (!currentLine.contains(returnedVehicle)) {
                    // Write the line to the temp file if it doesn't match
                    writer.write(currentLine);
                    writer.newLine();
                } else {
                    isDeleted = true; // Mark the record as deleted
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while processing the file: " + e.getMessage());
            return;
        }
    
        // Replace the original file with the updated file
        if (isDeleted) {
            if (inputFile.delete()) { // Delete the original file
                if (tempFile.renameTo(inputFile)) { // Rename temp file to original file name
                    System.out.println("\nThe record for " + returnedVehicle + " has been successfully removed from the history.");
                } else {
                    System.out.println("\nError: Could not rename the temporary file.");
                }
            } else {
                System.out.println("\nError: Could not delete the original file.");
            }
        } else {
            System.out.println("\nNo matching record found for " + returnedVehicle + " in the file.");
            tempFile.delete(); // Clean up temp file if no changes were made
        }
    }
}
