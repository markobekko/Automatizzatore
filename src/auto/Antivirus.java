package auto;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;

public class Antivirus extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7391665381349634001L;

	private JPanel contentPane;

	public static File fileSingolo = null;
	public static File fileCartella = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Antivirus frame = new Antivirus();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Antivirus() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 421, 199);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton Bottone1 = new JButton("Seleziona File");
		Bottone1.setBounds(47, 66, 149, 25);
		contentPane.add(Bottone1);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(58, 31, 275, 22);
		contentPane.add(textArea);
		textArea.setText("");

		JButton Bottone2 = new JButton("Seleziona Directory");
		Bottone2.setBounds(208, 66, 149, 25);
		contentPane.add(Bottone2);

		JButton Bottone3 = new JButton("Scansiona");
		Bottone3.setBounds(154, 95, 97, 25);
		contentPane.add(Bottone3);

		Bottone1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| UnsupportedLookAndFeelException ex) {
						}
						fileCartella = null;
						textArea.setText("");
						Antivirus frame = new Antivirus();
						JFileChooser chooser = new JFileChooser();
						if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
							File file = chooser.getSelectedFile();
							textArea.setText(file.getAbsolutePath());
							fileSingolo = file;
						}
					}
				});
			}
		});
		Bottone2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| UnsupportedLookAndFeelException ex) {
						}
						fileSingolo = null;
						textArea.setText(null);
						Antivirus frame = new Antivirus();
						JFileChooser chooser = new JFileChooser();
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
							File file = chooser.getSelectedFile();
							textArea.setText(file.getAbsolutePath());
							fileCartella = file;
						}
					}
				});
			}
		});
		Bottone3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(textArea.getText());
				switch (textArea.getText()) {
				case "":
					JOptionPane.showMessageDialog(null, "Selezionare un file o una cartella", "Errore",
							JOptionPane.ERROR_MESSAGE);
					break;
				default:
				}
				String fileScan;
				if (fileSingolo != null) {
					fileScan = fileSingolo.toString();
				} else {
					fileScan = fileCartella.toString();
				}
				String dirUser = System.getProperty("user.dir");
				// System.out.println("1");
//
//				PowerShellResponse response = PowerShell.executeSingleCommand("cd " + dir);
//
//				System.out.println("2");
//				System.out.println("List Processes:" + response.getCommandOutput());
				JLabel Label1 = new JLabel("New label");
				Label1.setBounds(154, 133, 134, 16);
				contentPane.add(Label1);
				Label1.setText("Scansione in corso...");
				try (PowerShell powerShell = PowerShell.openSession()) {
					Map<String, String> config = new HashMap<String, String>();
					config.put("maxWait", "8000000");
					// Execute a command in PowerShell session
					String directory = "cd " + dirUser + "\\ClamAV";
					PowerShellResponse response = powerShell.executeCommand(directory);
					// Print results
					JOptionPane.showMessageDialog(null,
							"La scansione ora avrà inizio,in base al numero e alla grandezza dei file ci vorrà più tempo,attendere prego"
									+ response.getCommandOutput(),
							"Inizio scansione", JOptionPane.INFORMATION_MESSAGE);
					JOptionPane.showMessageDialog(null,
							"Se il risultato della scansione è 'OK' allora il file non contiene virus,sennò contiene un virus"
									+ response.getCommandOutput(),
							"Attenzione", JOptionPane.INFORMATION_MESSAGE);

					// Execute another command in the same PowerShell session
					response = powerShell.configuration(config)
							.executeCommand(".\\clamscan.exe --recursive " + fileScan);
					Label1.setText("Scansione completata");
					JOptionPane.showMessageDialog(null, "" + response.getCommandOutput(), "Risultato:",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
}
