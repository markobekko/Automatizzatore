package auto;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6924454176733389805L;
	private JPanel contentPane;
	private JTextField textField;
	public static boolean Shutdown = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void updateUI() {
		SwingUtilities.updateComponentTreeUI(this);
	}

	public Main() {
		setTitle("Automatizzatore");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String[] Lista = { "Pulire i file temporanei", "Spegni il computer in", "Scansione Antivirus" };

		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox comboBox = new JComboBox(Lista);
		comboBox.setBounds(112, 13, 156, 22);
		contentPane.add(comboBox);
		comboBox.setSelectedIndex(0);

		JButton Bottone1 = new JButton("OK");
		Bottone1.setBounds(305, 12, 97, 25);
		contentPane.add(Bottone1);

		textField = new JTextField();
		textField.setBounds(152, 64, 71, 22);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel Label1 = new JLabel("secondi");
		Label1.setBounds(163, 48, 60, 16);
		contentPane.add(Label1);

		JButton Bottone2 = new JButton("Spegni");
		Bottone2.setBounds(142, 99, 97, 25);
		contentPane.add(Bottone2);

		JButton Bottone3 = new JButton("Annulla Spegnimento");
		Bottone3.setBounds(235, 63, 167, 25);
		contentPane.add(Bottone3);

		textField.setVisible(false);
		Label1.setVisible(false);
		Bottone2.setVisible(false);
		Bottone3.setVisible(false);

		Bottone1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setVisible(false);
				Label1.setVisible(false);
				Bottone2.setVisible(false);
				Bottone3.setVisible(false);
				if (comboBox.getSelectedItem() == "Pulire i file temporanei") {
					PuliziaFileTemporanei pulizia = new PuliziaFileTemporanei();
					pulizia.setVisible(true);
				} else if (comboBox.getSelectedItem() == "Spegni il computer in") {
					textField.setVisible(true);
					Label1.setVisible(true);
					Bottone2.setVisible(true);
					Bottone3.setVisible(true);

					updateUI();
					Bottone2.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							Runtime runtime = Runtime.getRuntime();
							String testo = textField.getText();
							try {
								int tempo = Integer.parseInt(testo);
								@SuppressWarnings("unused")
								String shutdownCommand = null, t = tempo == 0 ? "now" : String.valueOf(tempo);

								shutdownCommand = "shutdown.exe -s -t " + tempo;
								Shutdown = true;
								testo = "Il computer si spegnerà tra " + tempo + " secondi";
								JOptionPane.showMessageDialog(null, testo, "Attenzione",
										JOptionPane.INFORMATION_MESSAGE);
								try {
									runtime.exec(shutdownCommand);
								} catch (IOException e1) {
								}

							} catch (Exception e2) {
								System.out.println("Errore");
								JOptionPane.showMessageDialog(null, "Inserire un numero valido", "Errore",
										JOptionPane.ERROR_MESSAGE);
							}

						}

					});
					Bottone3.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							Runtime runtime = Runtime.getRuntime();
							String annullaShutdown = "shutdown.exe -a";
							if (Shutdown == true) {
								try {
									runtime.exec(annullaShutdown);
									JOptionPane.showMessageDialog(null, "Lo shutdown è stato cancellato", "Attenzione",
											JOptionPane.INFORMATION_MESSAGE);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								Shutdown = false;
							}

						}

					});
				} else if (comboBox.getSelectedItem() == "Scansione Antivirus") {
					Antivirus antivirus = new Antivirus();
					antivirus.setVisible(true);
				}

			}
		});
	}
}
