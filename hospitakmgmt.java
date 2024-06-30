import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

class Patient {
    private int id;
    private String name;
    private int age;
    private String medicalHistory;
    private List<String> appointments;

    public Patient(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.medicalHistory = "";
        this.appointments = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
    public List<String> getAppointments() { return appointments; }
    public void addAppointment(String appointment) { appointments.add(appointment); }
}

class Hospital {
    private Map<Integer, Patient> patientsById;
    private Map<String, Patient> patientsByName;
    private int nextId;

    public Hospital() {
        patientsById = new HashMap<>();
        patientsByName = new HashMap<>();
        nextId = 1;
    }

    public void addPatient(String name, int age) {
        Patient patient = new Patient(nextId, name, age);
        patientsById.put(nextId, patient);
        patientsByName.put(name, patient);
        nextId++;
    }

    public void updateMedicalHistory(int id, String history) {
        Patient patient = patientsById.get(id);
        if (patient != null) {
            patient.setMedicalHistory(history);
        }
    }

    public void scheduleAppointment(int id, String appointment) {
        Patient patient = patientsById.get(id);
        if (patient != null) {
            patient.addAppointment(appointment);
        }
    }

    public Patient getPatientById(int id) {
        return patientsById.get(id);
    }

    public Patient getPatientByName(String name) {
        return patientsByName.get(name);
    }
}

public class Main extends JFrame {
    private Hospital hospital;
    private JTextField nameField, ageField, idField, historyField, appointmentField, searchField;
    private JTextArea displayArea;
    private JComboBox<String> searchTypeComboBox;

    public Main() {
        hospital = new Hospital();
        setTitle("Hospital Information System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Patient ID:"));
        idField = new JTextField();
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Medical History:"));
        historyField = new JTextField();
        inputPanel.add(historyField);
        inputPanel.add(new JLabel("Appointment:"));
        appointmentField = new JTextField();
        inputPanel.add(appointmentField);
        inputPanel.add(new JLabel("Search:"));
        searchField = new JTextField();
        inputPanel.add(searchField);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Patient");
        JButton updateButton = new JButton("Update History");
        JButton scheduleButton = new JButton("Schedule Appointment");
        JButton searchButton = new JButton("Search Patient");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(scheduleButton);
        buttonPanel.add(searchButton);

        String[] searchTypes = {"ID", "Name"};
        searchTypeComboBox = new JComboBox<>(searchTypes);
        buttonPanel.add(searchTypeComboBox);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                hospital.addPatient(name, age);
                displayArea.setText("Patient added successfully.");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String history = historyField.getText();
                hospital.updateMedicalHistory(id, history);
                displayArea.setText("Medical history updated.");
            }
        });

        scheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String appointment = appointmentField.getText();
                hospital.scheduleAppointment(id, appointment);
                displayArea.setText("Appointment scheduled.");
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchType = (String) searchTypeComboBox.getSelectedItem();
                String searchValue = searchField.getText();
                Patient patient = null;
                if (searchType.equals("ID")) {
                    int id = Integer.parseInt(searchValue);
                    patient = hospital.getPatientById(id);
                } else if (searchType.equals("Name")) {
                    patient = hospital.getPatientByName(searchValue);
                }
                if (patient != null) {
                    displayArea.setText("ID: " + patient.getId() + "\n" +
                            "Name: " + patient.getName() + "\n" +
                            "Age: " + patient.getAge() + "\n" +
                            "Medical History: " + patient.getMedicalHistory() + "\n" +
                            "Appointments: " + patient.getAppointments());
                } else {
                    displayArea.setText("Patient not found.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
