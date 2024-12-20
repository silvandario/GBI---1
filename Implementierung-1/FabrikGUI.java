import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class FabrikGUI {
    private JFrame frame;
    private DefaultListModel<String> bestellungsListe;
    private JTextArea lagerBestandArea;
    private JTextArea konsolenAusgabeArea;
    private Produktions_Manager manager;

    public FabrikGUI(Produktions_Manager manager) {
        this.manager = manager;

        // Hauptfenster
        frame = new JFrame("Fabrikübersicht");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        // Layout
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));

        // Statusbereich oben
        JLabel statusLabel = new JLabel("Produktionsmanager läuft...");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(statusLabel, BorderLayout.NORTH);

        // Bestellungsbereich Mitte
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bestellungsListe = new DefaultListModel<>();
        JList<String> bestellungsAnzeigen = new JList<>(bestellungsListe);
        JScrollPane scrollPane = new JScrollPane(bestellungsAnzeigen);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Bestellungen"));
        centerPanel.add(scrollPane);

        // Konsolenausgabe
        konsolenAusgabeArea = new JTextArea();
        konsolenAusgabeArea.setEditable(false);
        JScrollPane consoleScrollPane = new JScrollPane(konsolenAusgabeArea);
        consoleScrollPane.setBorder(BorderFactory.createTitledBorder("Konsolenausgabe"));
        centerPanel.add(consoleScrollPane);

        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Eingabebereich rechts
        JPanel eingabePanel = new JPanel(new GridLayout(8, 1, 5, 5));
        eingabePanel.setBorder(BorderFactory.createTitledBorder("Neue Bestellung"));

        JTextField standardTuerenField = new JTextField();
        JTextField premiumTuerenField = new JTextField();
        JButton addButton = new JButton("Hinzufügen");
        JButton processButton = new JButton("Bearbeite Bestellung");
        processButton.setEnabled(false);

        addButton.addActionListener(e -> {
            try {
                int standard = Integer.parseInt(standardTuerenField.getText());
                int premium = Integer.parseInt(premiumTuerenField.getText());
                Bestellung bestellung = new Bestellung(standard, premium, manager.getBestellungenInProduktion().size() + 1);
                manager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung);
                bestellungsListe.addElement("Bestellung #" + bestellung.gibBestellungsNr() +
                        ": " + standard + " Standard, " + premium + " Premium");
                standardTuerenField.setText("");
                premiumTuerenField.setText("");
                processButton.setEnabled(true); // Aktiviert den Button
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Bitte geben Sie gültige Zahlen ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        processButton.addActionListener(e -> {
            if (!bestellungsListe.isEmpty()) {
                manager.bearbeiteBestellungen();
                updateConsole("Bestellung wird bearbeitet...");
            } else {
                JOptionPane.showMessageDialog(frame, "Keine Bestellung vorhanden!", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        eingabePanel.add(new JLabel("Standardtüren:"));
        eingabePanel.add(standardTuerenField);
        eingabePanel.add(new JLabel("Premiumtüren:"));
        eingabePanel.add(premiumTuerenField);
        eingabePanel.add(addButton);
        eingabePanel.add(processButton);
        contentPane.add(eingabePanel, BorderLayout.EAST);

        // Lagerbereich unten
        lagerBestandArea = new JTextArea(5, 20);
        lagerBestandArea.setEditable(false);
        lagerBestandArea.setBorder(BorderFactory.createTitledBorder("Lagerbestand"));
        contentPane.add(new JScrollPane(lagerBestandArea), BorderLayout.SOUTH);

        frame.setVisible(true);

        // Umleitung der System-Ausgabe zur GUI
        redirectSystemStreams();

        // Simuliere Lagerupdates
        new Timer(2000, e -> updateLager()).start();
    }

    /**
     * Aktualisiert den Lagerbestand in der GUI.
     */
    public void updateLager() {
        String lagerBestand = manager.getMeinLager().gibLagerBestandAlsString();
        lagerBestandArea.setText(lagerBestand);
    }

    /**
     * Aktualisiert die Konsolenausgabe in der GUI.
     *
     * @param text Der anzuzeigende Text.
     */
    public void updateConsole(String text) {
        konsolenAusgabeArea.append(text + "\n");
    }

    /**
     * Leitet die Systemausgabe (stdout und stderr) zur GUI um.
     */
    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                konsolenAusgabeArea.append(String.valueOf((char) b));
                konsolenAusgabeArea.setCaretPosition(konsolenAusgabeArea.getDocument().getLength());
            }

            @Override
            public void write(byte[] b, int off, int len) {
                konsolenAusgabeArea.append(new String(b, off, len));
                konsolenAusgabeArea.setCaretPosition(konsolenAusgabeArea.getDocument().getLength());
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

    public static void main(String[] args) {
        Lager lager = new Lager();
        Produktions_Manager manager = new Produktions_Manager(new Fabrik(), lager);
        SwingUtilities.invokeLater(() -> new FabrikGUI(manager));
        manager.start();
    }
}