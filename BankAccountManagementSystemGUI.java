import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BankAccountManagementSystemGUI extends JFrame {
    
    private static final String CORRECT_PIN = "1234";
    private static double balance = 1000.00;
    private int loginAttempts = 0;
    
    // GUI Components
    private JPanel loginPanel;
    private JPanel menuPanel;
    private JPasswordField pinField;
    private JLabel statusLabel;
    private JLabel balanceLabel;
    private JTextField amountField;
    private JTextArea transactionHistory;
    
    public BankAccountManagementSystemGUI() {
        setTitle("Bank Account Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize components
        initializeComponents();
        
        // Show login panel first
        showLoginPanel();
    }
    
    private void initializeComponents() {
        // Login Panel
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(240, 248, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("Bank Account Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);
        
        JLabel pinLabel = new JLabel("Enter PIN:");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        loginPanel.add(pinLabel, gbc);
        
        pinField = new JPasswordField(10);
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1;
        loginPanel.add(pinField, gbc);
        
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(event -> attemptLogin());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);
        
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        loginPanel.add(statusLabel, gbc);
        
        // Menu Panel
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(new Color(240, 248, 255));
        
        // Top panel with welcome message and balance
        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome to Bank Account Management");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(new Color(25, 25, 112));
        topPanel.add(welcomeLabel);
        
        balanceLabel = new JLabel("Current Balance: $" + String.format("%.2f", balance));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balanceLabel.setForeground(new Color(0, 100, 0));
        topPanel.add(balanceLabel);
        
        JLabel timeLabel = new JLabel(getTimeStamp());
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(timeLabel);
        
        menuPanel.add(topPanel, BorderLayout.NORTH);
        
        // Center panel with buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        JButton checkBalanceBtn = createStyledButton("Check Balance");
        checkBalanceBtn.addActionListener(event -> checkBalance());
        buttonPanel.add(checkBalanceBtn);
        
        JButton depositBtn = createStyledButton("Deposit Money");
        depositBtn.addActionListener(event -> depositMoney());
        buttonPanel.add(depositBtn);
        
        JButton withdrawBtn = createStyledButton("Withdraw Money");
        withdrawBtn.addActionListener(event -> withdrawMoney());
        buttonPanel.add(withdrawBtn);
        
        JButton exitBtn = createStyledButton("Exit");
        exitBtn.setBackground(new Color(220, 20, 60));
        exitBtn.addActionListener(event -> exitSystem());
        buttonPanel.add(exitBtn);
        
        menuPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Bottom panel with transaction history
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Transaction History"));
        bottomPanel.setBackground(new Color(240, 248, 255));
        
        transactionHistory = new JTextArea(5, 30);
        transactionHistory.setEditable(false);
        transactionHistory.setFont(new Font("Monospaced", Font.PLAIN, 11));
        transactionHistory.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(transactionHistory);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        
        menuPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Add Enter key listener to PIN field
        pinField.addActionListener(event -> attemptLogin());
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        return button;
    }
    
    private void showLoginPanel() {
        getContentPane().removeAll();
        add(loginPanel);
        revalidate();
        repaint();
        pinField.requestFocus();
    }
    
    private void showMenuPanel() {
        getContentPane().removeAll();
        add(menuPanel);
        revalidate();
        repaint();
        updateBalanceDisplay();
    }
    
    private void attemptLogin() {
        String inputPin = new String(pinField.getPassword());
        
        if (inputPin.equals(CORRECT_PIN)) {
            statusLabel.setText("Login successful!");
            statusLabel.setForeground(new Color(0, 128, 0));
            addToHistory("Login successful - " + getTimeStamp());
            
            // Small delay to show success message
            Timer timer = new Timer(1000, event -> showMenuPanel());
            timer.setRepeats(false);
            timer.start();
        } else {
            loginAttempts++;
            if (loginAttempts >= 3) {
                statusLabel.setText("Too many failed attempts. System locked.");
                pinField.setEnabled(false);
                addToHistory("System locked due to failed login attempts - " + getTimeStamp());
            } else {
                statusLabel.setText("Incorrect PIN. " + (3 - loginAttempts) + " attempts left.");
                pinField.setText("");
                pinField.requestFocus();
            }
        }
    }
    
    private void checkBalance() {
        updateBalanceDisplay();
        addToHistory("Balance checked: $" + String.format("%.2f", balance) + " - " + getTimeStamp());
        JOptionPane.showMessageDialog(this, 
            "Your current balance is: $" + String.format("%.2f", balance) + "\n" + getTimeStamp(),
            "Balance Information", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void depositMoney() {
        String amountStr = JOptionPane.showInputDialog(this, 
            "Enter amount to deposit:", 
            "Deposit Money", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Please enter a positive amount.", 
                        "Invalid Amount", 
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    balance += amount;  // Fixed: was subtracting in original code
                    updateBalanceDisplay();
                    addToHistory("Deposited: $" + String.format("%.2f", amount) + " - " + getTimeStamp());
                    JOptionPane.showMessageDialog(this, 
                        "You have successfully deposited $" + String.format("%.2f", amount) + ".\n" + getTimeStamp(),
                        "Deposit Successful", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid number.", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void withdrawMoney() {
        String amountStr = JOptionPane.showInputDialog(this, 
            "Enter amount to withdraw:", 
            "Withdraw Money", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Please enter a positive amount.", 
                        "Invalid Amount", 
                        JOptionPane.ERROR_MESSAGE);
                } else if (amount > balance) {
                    JOptionPane.showMessageDialog(this, 
                        "Insufficient balance.\nCurrent balance: $" + String.format("%.2f", balance), 
                        "Insufficient Funds", 
                        JOptionPane.ERROR_MESSAGE);
                    addToHistory("Withdrawal failed - Insufficient funds - " + getTimeStamp());
                } else {
                    balance -= amount;
                    updateBalanceDisplay();
                    addToHistory("Withdrawn: $" + String.format("%.2f", amount) + " - " + getTimeStamp());
                    JOptionPane.showMessageDialog(this, 
                        "You have withdrawn $" + String.format("%.2f", amount) + ".\n" + getTimeStamp(),
                        "Withdrawal Successful", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid number.", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void exitSystem() {
        int choice = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to exit?", 
            "Exit Confirmation", 
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            addToHistory("System exit - " + getTimeStamp());
            JOptionPane.showMessageDialog(this, 
                "Thank you for using Bank Account Management System!", 
                "Goodbye", 
                JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
    
    private void updateBalanceDisplay() {
        balanceLabel.setText("Current Balance: $" + String.format("%.2f", balance));
    }
    
    private void addToHistory(String transaction) {
        transactionHistory.append(transaction + "\n");
        transactionHistory.setCaretPosition(transactionHistory.getDocument().getLength());
    }
    
    private String getTimeStamp() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "Timestamp: " + currentTime.format(formatter);
    }
    
    public static void main(String[] args) {
        // Set look and feel
        // try {
        //     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        // } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        //     e.printStackTrace();
        // }
        
        SwingUtilities.invokeLater(() -> {
            new BankAccountManagementSystemGUI().setVisible(true);
        });
    }
}