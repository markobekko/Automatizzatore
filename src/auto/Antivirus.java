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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
		setTitle("Antivirus");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 421, 334);
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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 133, 338, 153);
		contentPane.add(scrollPane);

		JTextArea textArea1 = new JTextArea();
		textArea1.setEditable(false);
		scrollPane.setViewportView(textArea1);
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
				try (PowerShell powerShell = PowerShell.openSession()) {
					JOptionPane.showMessageDialog(null,
							"La scansione ora avrà inizio,in base al numero e alla grandezza dei file ci vorrà più tempo,attendere prego",
							"Attenzione", JOptionPane.INFORMATION_MESSAGE);
					Map<String, String> config = new HashMap<String, String>();
					config.put("maxWait", "8000000");
					// Execute a command in PowerShell session
					String directory = "cd " + dirUser + "\\ClamAV";
					PowerShellResponse response = powerShell.executeCommand(directory);
					// Print results
					// Execute another command in the same PowerShell session
					response = powerShell.configuration(config)
							.executeCommand(".\\clamscan.exe --recursive " + fileScan);
					textArea1.append("Scansione completata:\n");
					textArea1.append(response.getCommandOutput());
					if (response.getCommandOutput().contains(": OK")) {
						System.out.println("ciao");
					}
				}
			}
		});
	}
}
