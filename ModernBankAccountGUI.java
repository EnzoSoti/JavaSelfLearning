import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ModernBankAccountGUI extends JFrame {
    
    private static final String CORRECT_PIN = "1234";
    private static double balance = 1000.00;
    private int loginAttempts = 0;
    
    // Modern Color Palette
    private static final Color PRIMARY_COLOR = new Color(26, 35, 126);      // Deep Blue
    private static final Color SECONDARY_COLOR = new Color(63, 81, 181);    // Indigo
    private static final Color ACCENT_COLOR = new Color(3, 218, 198);       // Teal
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);      // Green
    private static final Color ERROR_COLOR = new Color(244, 67, 54);        // Red
    private static final Color WARNING_COLOR = new Color(255, 152, 0);      // Orange
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250); // Light Gray
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 33, 33);
    private static final Color TEXT_SECONDARY = new Color(117, 117, 117);
    
    // GUI Components
    private JPanel loginPanel;
    private JPanel menuPanel;
    private JPasswordField pinField;
    private JLabel statusLabel;
    private JLabel balanceLabel;
    private JTextArea transactionHistory;
    
    public ModernBankAccountGUI() {
        setTitle("SecureBank - Account Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Set modern look and feel
        // try {
        //     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        
        initializeComponents();
        showLoginPanel();
    }
    
    private void initializeComponents() {
        createLoginPanel();
        createMenuPanel();
    }
    
    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BorderLayout());
        loginPanel.setBackground(BACKGROUND_COLOR);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(600, 120));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("SecureBank", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(30, 0, 10, 0));
        
        JLabel subtitleLabel = new JLabel("Account Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        // Login Card
        JPanel cardPanel = createCard();
        cardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel loginTitle = new JLabel("Sign In");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        loginTitle.setForeground(TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(20, 0, 30, 0);
        cardPanel.add(loginTitle, gbc);
        
        JLabel pinLabel = new JLabel("PIN Code");
        pinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pinLabel.setForeground(TEXT_SECONDARY);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 8, 0);
        gbc.anchor = GridBagConstraints.WEST;
        cardPanel.add(pinLabel, gbc);
        
        pinField = new JPasswordField(15);
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pinField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)));
        pinField.setBackground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cardPanel.add(pinField, gbc);
        
        JButton loginButton = createModernButton("Sign In", PRIMARY_COLOR);
        loginButton.addActionListener(e -> attemptLogin());
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 20, 0);
        cardPanel.add(loginButton, gbc);
        
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(ERROR_COLOR);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 20, 0);
        cardPanel.add(statusLabel, gbc);
        
        // Center the card
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(cardPanel);
        
        loginPanel.add(headerPanel, BorderLayout.NORTH);
        loginPanel.add(centerPanel, BorderLayout.CENTER);
        
        pinField.addActionListener(e -> attemptLogin());
    }
    
    private void createMenuPanel() {
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(BACKGROUND_COLOR);
        
        // Header with balance
        JPanel headerPanel = createHeaderPanel();
        
        // Main content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Action buttons
        JPanel actionsPanel = createActionsPanel();
        
        // Transaction history
        JPanel historyPanel = createHistoryPanel();
        
        contentPanel.add(actionsPanel, BorderLayout.CENTER);
        contentPanel.add(historyPanel, BorderLayout.SOUTH);
        
        menuPanel.add(headerPanel, BorderLayout.NORTH);
        menuPanel.add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(600, 100));
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(PRIMARY_COLOR);
        leftPanel.setBorder(new EmptyBorder(20, 30, 20, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        
        JLabel timeLabel = new JLabel(getTimeStamp());
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(new Color(200, 200, 200));
        
        leftPanel.add(welcomeLabel, BorderLayout.CENTER);
        leftPanel.add(timeLabel, BorderLayout.SOUTH);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(PRIMARY_COLOR);
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 30));
        
        JLabel balanceTitle = new JLabel("Current Balance");
        balanceTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        balanceTitle.setForeground(new Color(200, 200, 200));
        balanceTitle.setHorizontalAlignment(SwingConstants.RIGHT);
        
        balanceLabel = new JLabel("$" + String.format("%.2f", balance));
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        balanceLabel.setForeground(ACCENT_COLOR);
        balanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        rightPanel.add(balanceTitle, BorderLayout.NORTH);
        rightPanel.add(balanceLabel, BorderLayout.CENTER);
        
        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createActionsPanel() {
        JPanel actionsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        actionsPanel.setBackground(BACKGROUND_COLOR);
        actionsPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Action cards
        JPanel checkBalanceCard = createActionCard("💰", "Check Balance", "View current account balance");
        checkBalanceCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { checkBalance(); }
        });
        
        JPanel depositCard = createActionCard("💳", "Deposit", "Add money to account");
        depositCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { depositMoney(); }
        });
        
        JPanel withdrawCard = createActionCard("💸", "Withdraw", "Remove money from account");
        withdrawCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { withdrawMoney(); }
        });
        
        JPanel exitCard = createActionCard("🚪", "Exit", "Sign out of system");
        exitCard.setBackground(new Color(255, 245, 245));
        exitCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { exitSystem(); }
        });
        
        actionsPanel.add(checkBalanceCard);
        actionsPanel.add(depositCard);
        actionsPanel.add(withdrawCard);
        actionsPanel.add(exitCard);
        
        return actionsPanel;
    }
    
    private JPanel createActionCard(String icon, String title, String description) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        Color originalColor = card.getBackground();
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 248, 248));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(originalColor);
            }
        });
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_SECONDARY);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(card.getBackground());
        textPanel.add(titleLabel, BorderLayout.CENTER);
        textPanel.add(descLabel, BorderLayout.SOUTH);
        
        card.add(iconLabel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createHistoryPanel() {
        JPanel historyPanel = createCard();
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel historyTitle = new JLabel("Transaction History");
        historyTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        historyTitle.setForeground(TEXT_PRIMARY);
        historyTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        transactionHistory = new JTextArea(6, 0);
        transactionHistory.setEditable(false);
        transactionHistory.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        transactionHistory.setBackground(new Color(250, 250, 250));
        transactionHistory.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(transactionHistory);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        historyPanel.add(historyTitle, BorderLayout.NORTH);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        
        return historyPanel;
    }
    
    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(240, 240, 240), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        
        // Add subtle shadow effect
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 2, new Color(0, 0, 0, 20)),
            card.getBorder()));
        
        return card;
    }
    
    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 45));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
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
            statusLabel.setText("✓ Login successful!");
            statusLabel.setForeground(SUCCESS_COLOR);
            addToHistory("✓ Login successful - " + getTimeStamp());
            
            Timer timer = new Timer(1000, e -> showMenuPanel());
            timer.setRepeats(false);
            timer.start();
        } else {
            loginAttempts++;
            if (loginAttempts >= 3) {
                statusLabel.setText("⚠ System locked - Too many failed attempts");
                pinField.setEnabled(false);
                addToHistory("⚠ System locked due to failed login attempts - " + getTimeStamp());
            } else {
                statusLabel.setText("✗ Incorrect PIN - " + (3 - loginAttempts) + " attempts remaining");
                pinField.setText("");
                pinField.requestFocus();
            }
        }
    }
    
    private void checkBalance() {
        updateBalanceDisplay();
        addToHistory("💰 Balance checked: $" + String.format("%.2f", balance) + " - " + getTimeStamp());
        
        JOptionPane optionPane = new JOptionPane(
            "<html><div style='text-align: center; font-family: Segoe UI;'>" +
            "<h3>Account Balance</h3>" +
            "<p style='font-size: 18px; color: #1B5E20;'>$" + String.format("%.2f", balance) + "</p>" +
            "<p style='font-size: 12px; color: #666;'>" + getTimeStamp() + "</p>" +
            "</div></html>",
            JOptionPane.INFORMATION_MESSAGE);
        
        JDialog dialog = optionPane.createDialog(this, "Balance Information");
        dialog.setVisible(true);
    }
    
    private void depositMoney() {
        String amountStr = JOptionPane.showInputDialog(this,
            "<html><div style='font-family: Segoe UI;'>" +
            "<h3>Deposit Money</h3>" +
            "<p>Enter the amount you want to deposit:</p>" +
            "</div></html>",
            "Deposit Transaction",
            JOptionPane.QUESTION_MESSAGE);
        
        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    showErrorDialog("Invalid Amount", "Please enter a positive amount.");
                } else {
                    balance += amount;
                    updateBalanceDisplay();
                    addToHistory("💳 Deposited: $" + String.format("%.2f", amount) + " - " + getTimeStamp());
                    showSuccessDialog("Deposit Successful", 
                        "Successfully deposited $" + String.format("%.2f", amount));
                }
            } catch (NumberFormatException ex) {
                showErrorDialog("Invalid Input", "Please enter a valid number.");
            }
        }
    }
    
    private void withdrawMoney() {
        String amountStr = JOptionPane.showInputDialog(this,
            "<html><div style='font-family: Segoe UI;'>" +
            "<h3>Withdraw Money</h3>" +
            "<p>Enter the amount you want to withdraw:</p>" +
            "<p style='font-size: 12px; color: #666;'>Available balance: $" + String.format("%.2f", balance) + "</p>" +
            "</div></html>",
            "Withdrawal Transaction",
            JOptionPane.QUESTION_MESSAGE);
        
        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    showErrorDialog("Invalid Amount", "Please enter a positive amount.");
                } else if (amount > balance) {
                    showErrorDialog("Insufficient Funds", 
                        "Cannot withdraw $" + String.format("%.2f", amount) + 
                        "\nAvailable balance: $" + String.format("%.2f", balance));
                    addToHistory("⚠ Withdrawal failed - Insufficient funds - " + getTimeStamp());
                } else {
                    balance -= amount;
                    updateBalanceDisplay();
                    addToHistory("💸 Withdrawn: $" + String.format("%.2f", amount) + " - " + getTimeStamp());
                    showSuccessDialog("Withdrawal Successful", 
                        "Successfully withdrawn $" + String.format("%.2f", amount));
                }
            } catch (NumberFormatException ex) {
                showErrorDialog("Invalid Input", "Please enter a valid number.");
            }
        }
    }
    
    private void exitSystem() {
        int choice = JOptionPane.showConfirmDialog(this,
            "<html><div style='font-family: Segoe UI; text-align: center;'>" +
            "<h3>Exit Confirmation</h3>" +
            "<p>Are you sure you want to sign out?</p>" +
            "</div></html>",
            "Exit System",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            addToHistory("🚪 System exit - " + getTimeStamp());
            showSuccessDialog("Goodbye", "Thank you for using SecureBank!");
            System.exit(0);
        }
    }
    
    private void showSuccessDialog(String title, String message) {
        JOptionPane.showMessageDialog(this,
            "<html><div style='font-family: Segoe UI;'>" +
            "<p style='color: #4CAF50;'>✓ " + message + "</p>" +
            "<p style='font-size: 12px; color: #666;'>" + getTimeStamp() + "</p>" +
            "</div></html>",
            title,
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this,
            "<html><div style='font-family: Segoe UI;'>" +
            "<p style='color: #F44336;'>✗ " + message + "</p>" +
            "</div></html>",
            title,
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void updateBalanceDisplay() {
        balanceLabel.setText("$" + String.format("%.2f", balance));
    }
    
    private void addToHistory(String transaction) {
        transactionHistory.append(transaction + "\n");
        transactionHistory.setCaretPosition(transactionHistory.getDocument().getLength());
    }
    
    private String getTimeStamp() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModernBankAccountGUI().setVisible(true);
        });
    }
}