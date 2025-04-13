import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class JavaTherapistGUI {
    private static int score = 0;
    private static int currentQuestion = 0;
    private static Clip clip;

    // Questions and answers
    private static String[] questions = {
        "How often do you feel stressed?",
        "Do you have trouble sleeping?",
        "Are you feeling overwhelmed at work?",
        "Do you take breaks during work?",
        "Do you feel physically exhausted frequently?",
        "Do you have a support system?",
        "Do you exercise regularly?"
    };

    private static JTextField nameField, ageField;
    private static JFrame frame;
    private static JPanel panel;
    private static JTextArea questionArea;

    public static void main(String[] args) {
        frame = new JFrame("Java Therapist");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().setBackground(new Color(40, 40, 40));  // Dark background color
        
        // Initial panel to get name and age
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40)); // Dark background for this panel
        panel.setPreferredSize(new Dimension(600, 400)); // Ensure proper size

        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setForeground(Color.WHITE); // White text
        nameField = new JTextField(10);  // Smaller input field for name
        nameField.setBackground(new Color(60, 60, 60)); // Dark input field background
        nameField.setForeground(Color.WHITE); // White text in input field
        nameField.setPreferredSize(new Dimension(150, 30)); // Smaller width

        JLabel ageLabel = new JLabel("Enter your age:");
        ageLabel.setForeground(Color.WHITE); // White text
        ageField = new JTextField(10);  // Smaller input field for age
        ageField.setBackground(new Color(60, 60, 60)); // Dark input field background
        ageField.setForeground(Color.WHITE); // White text in input field
        ageField.setPreferredSize(new Dimension(150, 30)); // Smaller width

        JButton startButton = new JButton("Start Assessment");
        startButton.setBackground(new Color(90, 90, 255)); // Bright blue button
        startButton.setForeground(Color.WHITE); // White text
        startButton.setPreferredSize(new Dimension(200, 40)); // Proper button size

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(startButton);

        frame.add(panel);
        frame.setVisible(true);

        // Start button event
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String age = ageField.getText();
                if (name.isEmpty() || age.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both name and age.");
                } else {
                    // Proceed to question panel
                    playBackgroundMusic("sad"); // Default to sad music initially
                    showNextQuestion();
                }
            }
        });
    }

    private static void showNextQuestion() {
        // Hide the initial input screen
        panel.setVisible(false);
        
        // Create a new panel for the questions
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40));  // Dark background for this panel
        panel.setPreferredSize(new Dimension(600, 400)); // Ensure proper size

        // Show question
        questionArea = new JTextArea();
        questionArea.setText(questions[currentQuestion]);
        questionArea.setEditable(false);
        questionArea.setForeground(Color.WHITE); // White text
        questionArea.setBackground(new Color(60, 60, 60)); // Dark background for text area
        questionArea.setPreferredSize(new Dimension(550, 50)); // Text area size
        panel.add(questionArea);

        // Add a slider to the question, centered horizontally
        JSlider slider = new JSlider(0, 10, 5); // Range from 0 (low) to 10 (high)
        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(new Color(60, 60, 60)); // Dark background for slider
        slider.setPreferredSize(new Dimension(400, 50)); // Slider size (centered)
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the slider horizontally
        panel.add(slider);

        // Submit button to save the response and move to the next question
        JButton submitButton = new JButton("Submit Answer");
        submitButton.setBackground(new Color(90, 90, 255)); // Bright blue button
        submitButton.setForeground(Color.WHITE); // White text
        submitButton.setPreferredSize(new Dimension(200, 40)); // Proper button size
        panel.add(submitButton);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        // Submit button action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score += slider.getValue(); // Increment score based on slider value
                currentQuestion++;
                if (currentQuestion < questions.length) {
                    showNextQuestion(); // Show next question
                } else {
                    showResult(); // Show result once all questions are answered
                }
            }
        });
    }

    private static void showResult() {
        // Hide the question screen
        panel.setVisible(false);

        // Final result screen
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40));  // Dark background for this panel
        panel.setPreferredSize(new Dimension(600, 400)); // Ensure proper size

        // Display score and message based on score
        String resultMessage = "You scored: " + score + "/70\n";
        if (score <= 20) {
            resultMessage += "You may want to consider seeing a professional therapist.";
            resultMessage += "\nSuggested exercises: Yoga, Meditation, Deep Breathing";
            playBackgroundMusic("sad"); // Play sad music for low score
        } else if (score <= 50) {
            resultMessage += "It's important to take care of yourself. Try some exercises and relaxation techniques.";
            resultMessage += "\nSuggested exercises: Walking, Stretching, Light Jogging";
            playBackgroundMusic("climax"); // Play climax music for medium score
        } else {
            resultMessage += "You are doing well! Keep up the good work and continue taking care of yourself!";
            resultMessage += "\nSuggested exercises: Running, Weight Training, HIIT";
            playBackgroundMusic("climax"); // Play climax music for high score
        }

        JTextArea resultArea = new JTextArea();
        resultArea.setText(resultMessage);
        resultArea.setEditable(false);
        resultArea.setForeground(Color.WHITE); // White text
        resultArea.setBackground(new Color(60, 60, 60)); // Dark background for result text area
        resultArea.setPreferredSize(new Dimension(550, 100)); // Result area size
        panel.add(resultArea);

        // Close button to exit the program
        JButton closeButton = new JButton("Exit");
        closeButton.setBackground(new Color(255, 60, 60)); // Red button
        closeButton.setForeground(Color.WHITE); // White text
        closeButton.setPreferredSize(new Dimension(200, 40)); // Proper button size
        panel.add(closeButton);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        // Close button action
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Close the application
            }
        });
    }

    private static void playBackgroundMusic(String type) {
        try {
            String musicFile = "";
            if (type.equals("sad")) {
                musicFile = "sadSong.wav"; // Replace with actual path to the sad song
            } else if (type.equals("climax")) {
                musicFile = "climaxSong.wav"; // Replace with actual path to the climax song
            }

            File audioFile = new File(musicFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Play music continuously in the background
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace(); // Print error if the file can't be played
        }
    }
}
