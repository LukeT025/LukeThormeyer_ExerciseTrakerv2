/*
 * This class will pop up a window where the user will have to login into the app to access the information to put in
 * Once that is done will they will put in all the information to get there result
 * Will see there information on the result side 
 * and can save to a file, log out if need, learn about the app ,and exit as well through the small menus in the top left 
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

/**
 * The private textFeilds, Areas, Dialogs, and booleans 
 */
public class ExerciseTracker extends JFrame {
    private JTextField nameField;
    private JTextField dateField;
    private JTextField durationField;
    private JTextField distanceField;
    private JTextArea commentArea;
    private JTextArea summaryArea;
    private JDialog loginDialog;
    private boolean isAllowed = false;
    
    /**
     * This will set the constructors, set up the GUI components and initializes the application
     */
    public ExerciseTracker() {
        setTitle("Exercise Tracker V2.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(createInputPanel());
        leftPanel.add(createCommentPanel());
        leftPanel.add(createButtonPanel());
        add(leftPanel, BorderLayout.WEST);  
        add(createSummaryPanel(), BorderLayout.CENTER);  
        setEnabledComponents(false); 
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loginItem = new JMenuItem("Log In");
        loginItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoginDialog();
            }
        });
        fileMenu.add(loginItem);
        JMenuItem logoutItem = new JMenuItem("Log Out");
        logoutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logOut();
            }
        });
        fileMenu.add(logoutItem);
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveExercises();
            }
        });
        fileMenu.add(saveItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAboutDialog();
            }
        });
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    /**
     * creates the input panels for name, date , duration and distance
     * @return
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Name:"));
        nameField = new JTextField(10);
        panel.add(nameField);
        panel.add(new JLabel("Date:"));
        dateField = new JTextField(10);
        panel.add(dateField);
        panel.add(new JLabel("Duration:"));
        durationField = new JTextField(10);
        panel.add(durationField);
        panel.add(new JLabel("Distance:"));
        distanceField = new JTextField(10);
        panel.add(distanceField);
        return panel;
    }
    
    /**
     * creates the comment panel
     * @return
     */
    private JPanel createCommentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Add comment:"), BorderLayout.NORTH);
        commentArea = new JTextArea(4, 20);
        panel.add(new JScrollPane(commentArea), BorderLayout.CENTER);
        return panel;
    }
    
    /**
     * creates the summary panel
     * @return
     */
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Exercise Summary"));
        summaryArea = new JTextArea(4, 20);
        summaryArea.setEditable(false);
        panel.add(new JScrollPane(summaryArea), BorderLayout.CENTER);
        return panel;
    }
    
    /**
     * creates the exercise button panels
     * @return
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton exerciseButton = new JButton("Add Exercise");
        exerciseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addExercise();
            }
        });
        panel.add(exerciseButton);
        return panel;
    }

    /**
     * This will enable and disable 
     * @param enabled
     */
    private void setEnabledComponents(boolean enabled) {
        nameField.setEnabled(enabled);
        dateField.setEnabled(enabled);
        durationField.setEnabled(enabled);
        distanceField.setEnabled(enabled);
        commentArea.setEnabled(enabled);
        summaryArea.setEnabled(enabled);
    }
    
    /**
     * This will show the login menu and buttons 
     */
    private void showLoginDialog() {
        loginDialog = new JDialog(this, "Please Log In", true);
        loginDialog.setLayout(new GridLayout(3, 2));
        loginDialog.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        loginDialog.add(usernameField);
        loginDialog.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        loginDialog.add(passwordField);
        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attemptLogin(usernameField.getText(), new String(passwordField.getPassword()));
            }
        });
        loginDialog.add(loginButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginDialog.dispose();
            }
        });
        loginDialog.add(cancelButton);
        loginDialog.pack();
        loginDialog.setLocationRelativeTo(this);
        loginDialog.setVisible(true);
    }
    
    /**
     * Will look if the user put in the username and password of healthy and donuts 
     * If not will put an error message up that its wrong
     * If its that username and password will let enable components
     * @param username
     * @param password
     */
    private void attemptLogin(String username, String password) {
        if ("healthy".equals(username) && "donuts".equals(password)) {
        	isAllowed = true;
            setEnabledComponents(true);
            loginDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This will go through the users inputs for name, date, duration,distance, and comments 
     * Then will go through the date, duration and distance input and see if the date is formated right, and the duration and distance are both not less than 0 and are ints.
     * Well pop up a menu telling them to format it right and when they do get it right will calculate the calories burned and will put a summary to the right
     * Then will let the user keep to doing that  
     */
    private void addExercise() {
        StringBuilder error = new StringBuilder();
        String name = nameField.getText();
        String dateString = dateField.getText();
        String durationString = durationField.getText();
        String distanceString = distanceField.getText();
        String comment = commentArea.getText();
        if (!dateString.matches("\\d{2}/\\d{2}/\\d{4}")) {
            error.append("The date must be in the format mm/dd/yyyy.\n");
        }
        double duration = 0.0;
        try {
            duration = Double.parseDouble(durationString);
            if (duration <= 0) {
                error.append("Duration must be a positive number.\n");
            }
        } catch (Exception ex) {
            error.append("Duration must be a numeric value.\n");
        }
        double distance = 0.0;
        try {
            distance = Double.parseDouble(distanceString);
            if (distance <= 0) {
                error.append("Distance must be a positive number.\n");
            }
        } catch (Exception ex) {
            error.append("Distance must be a numeric value.\n");
        }
        if (error.length() > 0) {
            JOptionPane.showMessageDialog(this, error.toString(), "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double caloriesBurned = (distance / duration) * 9000;
        String summary = String.format("Name: %s, Date: %s, Burned: %.2f, Comment: %s%n", name, dateString, burned, comment);
        summaryArea.append(summary);
        nameField.setText("");
        dateField.setText("");
        durationField.setText("");
        distanceField.setText("");
        commentArea.setText("");
    }
    
    /**
     * This will save the save the exercise to a file that the user selects 
     */
    private void saveExercises() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save Exercises");
        int userSelection = fc.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fc.getSelectedFile();
            try (FileWriter fileWriter = new FileWriter(fileToSave)) {
                fileWriter.write(summaryArea.getText());
                JOptionPane.showMessageDialog(this, "Exercises saved successfully", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Exercies failed to save", "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * When the user presses the about button, will appear this text for them
     */
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "ExerciseTracker V2.0 - CPSC 24500 Fall 2023\n", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This will log out the user by disabling components
     */
    private void logOut() {
    	isAllowed = false;
        setEnabledComponents(false);
    }

    /**
     * This will run the application
     * @param args
     */
    public static void main(String[] args) {
           ExerciseTracker et = new ExerciseTracker();
           et.setVisible(true);
    }
}