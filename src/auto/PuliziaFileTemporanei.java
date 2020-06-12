package auto;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class PuliziaFileTemporanei extends JFrame {

	private static final long serialVersionUID = -2811784734576183574L;
	private static JPanel contentPane;
	private JCheckBox GoogleCronologia;
	public static boolean messaggio = false;
	static JButton Button1 = new JButton("PULISCI");

	public static void eliminaDirectory(File directory) {
		// For per cancellare i file in una directory,non importa la quantità di file
		for (File file : directory.listFiles()) {
			if (file.isDirectory())
				eliminaDirectory(file);
			file.delete();
		}
	}

	public static void BarraCaricamento() {
		JProgressBar progressBar = new JProgressBar();
		progressBar.setMaximum(150);
		progressBar.setBounds(144, 221, 178, 20);
		contentPane.add(progressBar);
		progressBar.setValue(0);

		int timerDelay = 10;
		new javax.swing.Timer(timerDelay, new ActionListener() {
			private int index = 0;
			private int maxIndex = 150;

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				// Se index è minore del massimo allora si incrementa sennò finisci e se si
				// cancella dinuovo si resetta il contatore
				if (index < maxIndex) {
					Button1.setEnabled(false);
					progressBar.setValue(index);
					index++;
				} else {
					progressBar.setValue(maxIndex);
					JOptionPane me = new JOptionPane();
					me.showMessageDialog(null, "I file sono stati eliminati", "Pulizia Completata",
							JOptionPane.INFORMATION_MESSAGE);

					((javax.swing.Timer) e.getSource()).stop(); // stop the timer
					Button1.setEnabled(true);
				}
			}
		}).start();
		progressBar.setValue(progressBar.getMinimum());
	}

	public PuliziaFileTemporanei() {
		setTitle("Ottimizzatore File");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 514, 301);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JCheckBox CheckBox1 = new JCheckBox("File Temporanei");
		CheckBox1.setBounds(373, 31, 130, 25);
		contentPane.add(CheckBox1);

		JCheckBox CheckBox2 = new JCheckBox("Cestino");
		CheckBox2.setBounds(256, 31, 113, 25);
		contentPane.add(CheckBox2);

		JCheckBox CheckBox3 = new JCheckBox("Download");
		CheckBox3.setBounds(256, 61, 113, 25);
		contentPane.add(CheckBox3);

		JLabel Label1 = new JLabel("Generale");
		Label1.setBounds(326, 13, 58, 16);
		contentPane.add(Label1);

		Button1.setBounds(182, 183, 97, 25);
		contentPane.add(Button1);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setMaximum(150);
		progressBar.setBounds(144, 221, 178, 20);
		contentPane.add(progressBar);

		JLabel Label2 = new JLabel("Google Chrome");
		Label2.setBounds(12, 13, 97, 16);
		contentPane.add(Label2);

		JLabel Label3 = new JLabel("Microsoft Edge");
		Label3.setBounds(12, 125, 122, 16);
		contentPane.add(Label3);

		GoogleCronologia = new JCheckBox("Cronologia");
		GoogleCronologia.setBounds(8, 31, 113, 25);
		contentPane.add(GoogleCronologia);

		JCheckBox GoogleCache = new JCheckBox("Cache");
		GoogleCache.setBounds(8, 61, 113, 25);
		contentPane.add(GoogleCache);

		JCheckBox GoogleCookie = new JCheckBox("Cookie");
		GoogleCookie.setBounds(8, 91, 113, 25);
		contentPane.add(GoogleCookie);

		JCheckBox EdgeCronologia = new JCheckBox("Cronologia");
		EdgeCronologia.setBounds(8, 150, 113, 25);
		contentPane.add(EdgeCronologia);

		JCheckBox EdgeCache = new JCheckBox("Cache");
		EdgeCache.setBounds(8, 183, 113, 25);
		contentPane.add(EdgeCache);

		JCheckBox EdgeCookie = new JCheckBox("Cookie");
		EdgeCookie.setBounds(8, 213, 113, 25);
		contentPane.add(EdgeCookie);

		JButton BottoneSeleziona = new JButton("Seleziona Tutto");
		BottoneSeleziona.setBounds(343, 216, 142, 25);
		contentPane.add(BottoneSeleziona);

		JCheckBox[] listaCheckBox = { CheckBox1, CheckBox2, CheckBox3, GoogleCronologia, GoogleCache, GoogleCookie,
				EdgeCache, EdgeCookie, EdgeCronologia };

		Button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Fa caricare la barra di caricamento se almeno 1 delle checkbox è stata
				// spuntata
				for (int i = 0; i < listaCheckBox.length; i++) {
					if (listaCheckBox[i].isSelected()) {
						BarraCaricamento();
						break;
					}
				}
				// Serie di if per vedere qualche CheckBox si è spuntato
				if (CheckBox1.isSelected()) {
					String path = System.getProperty("user.home") + File.separator + "AppData" + File.separator
							+ "Local" + File.separator + "Temp";
					File temp = new File(path);
					eliminaDirectory(temp);
				}
				if (CheckBox2.isSelected()) {
					String path = "C:\\$Recycle.Bin\\";
					File temp = new File(path);
					eliminaDirectory(temp);
				}
				if (CheckBox3.isSelected()) {
					String path = System.getProperty("user.home") + File.separator + "Downloads";
					File temp = new File(path);
					eliminaDirectory(temp);
				}
				if (GoogleCronologia.isSelected()) {
					String path = System.getProperty("user.home") + File.separator + "AppData" + File.separator
							+ "Local" + File.separator + "Google" + File.separator + "Chrome" + File.separator
							+ "User Data" + File.separator + "Default" + File.separator + "Web Data";
					File temp = new File(path);
					eliminaDirectory(temp);
				}
				if (GoogleCache.isSelected()) {
					String path = System.getProperty("user.home") + File.separator + "AppData" + File.separator
							+ "Local" + File.separator + "Google" + File.separator + "Chrome" + File.separator
							+ "User Data" + File.separator + "Default" + File.separator + "Cache";
					File temp = new File(path);
					eliminaDirectory(temp);
				}
				if (GoogleCookie.isSelected()) {
					String path = System.getProperty("user.home") + File.separator + "AppData" + File.separator
							+ "Local" + File.separator + "Google" + File.separator + "Chrome" + File.separator
							+ "User Data" + File.separator + "Default" + File.separator + "Cookies";
					File temp = new File(path);
					eliminaDirectory(temp);
				}
				if (EdgeCookie.isSelected()) {
					String path = System.getProperty("user.home")
							+ "\\AppData\\Local\\Packages\\Microsoft.MicrosoftEdge_8wekyb3d8bbwe\\AC\\MicrosoftEdge\\Cookies";
					File temp = new File(path);
					eliminaDirectory(temp);
				}
				if (EdgeCache.isSelected()) {
					String path = System.getProperty("user.home")
							+ "\\AppData\\Local\\Packages\\Microsoft.MicrosoftEdge_8wekyb3d8bbwe\\AC\\#!001\\MicrosoftEdge\\Cache";
					File temp = new File(path);
					eliminaDirectory(temp);
				}
				if (EdgeCronologia.isSelected()) {
					String path = System.getProperty("user.home") + "AppData\\Local\\Microsoft\\Windows\\WebCache";
					File temp = new File(path);
					eliminaDirectory(temp);
				}
			}
		});

		BottoneSeleziona.addActionListener(new ActionListener() {
			int contatore = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				// Se si preme il pulsante piu di una volta allora fa il contrario di
				// selezionare tutto
				if (contatore == 0) {
					for (int i = 0; i < listaCheckBox.length; i++) {
						listaCheckBox[i].setSelected(true);
					}
					contatore++;
				} else {
					for (int i = 0; i < listaCheckBox.length; i++) {
						listaCheckBox[i].setSelected(false);
					}
					contatore = 0;
				}
			}
		});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PuliziaFileTemporanei frame = new PuliziaFileTemporanei();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
