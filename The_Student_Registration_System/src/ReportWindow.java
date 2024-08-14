import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportWindow extends JFrame {
    private JTextArea reportTextArea;
    private JButton saveReportButton;

    public ReportWindow() {
        setTitle("Generate Report");
        setSize(400, 300);
        setLocationRelativeTo(null);

        reportTextArea = new JTextArea(10, 40);
        reportTextArea.setLineWrap(true);
        reportTextArea.setWrapStyleWord(true);
        reportTextArea.setEditable(true);

        JScrollPane scrollPane = new JScrollPane(reportTextArea);

        saveReportButton = new JButton("Save Report");
        saveReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveReportToFile();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(saveReportButton, BorderLayout.SOUTH);

        getContentPane().add(panel);
    }

    private void saveReportToFile() {
        String reportText = reportTextArea.getText();
        if (!reportText.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            int userChoice = fileChooser.showSaveDialog(this);

            if (userChoice == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try (PrintWriter writer = new PrintWriter(selectedFile)) {
                    writer.write(reportText);
                    JOptionPane.showMessageDialog(
                            this,
                            "Report saved successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            this,
                            "Error saving the report.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }
}
