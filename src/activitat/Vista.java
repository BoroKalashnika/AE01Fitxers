package activitat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.SystemColor;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDir;
	private JTextField txtBuscarParaula;
	private JTextField txtReemplazarParaula;
	private JTextArea textArea;
	private JButton btnMostrarDirs;
	private JCheckBox chkAccents;
	private JCheckBox chkMayusMinus;
	private JButton btnBuscarCoincidncies;
	private JButton btnReemplazarParaula;

	public JButton getbtnBuscarCoincidncies() {
		return btnBuscarCoincidncies;
	}

	public JButton getbtnReemplazarParaula() {
		return btnReemplazarParaula;
	}

	public JCheckBox getChkAccents() {
		return chkAccents;
	}

	public JCheckBox getChkMayusMinus() {
		return chkMayusMinus;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public JTextField getTxtDir() {
		return txtDir;
	}

	public JTextField getTxtBuscarParaula() {
		return txtBuscarParaula;
	}

	public JTextField getTxtReemplazarParaula() {
		return txtReemplazarParaula;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JButton getBtnMostrarDirs() {
		return btnMostrarDirs;
	}

	public Vista() {
		initialize();
		setVisible(true);
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1030, 477);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.info);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtDir = new JTextField();
		txtDir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDir.setBounds(248, 20, 521, 20);
		contentPane.add(txtDir);
		txtDir.setColumns(10);

		JLabel lblRuta = new JLabel("Introduïx la rauta i el directori:");
		lblRuta.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRuta.setBounds(22, 23, 227, 14);
		contentPane.add(lblRuta);

		btnMostrarDirs = new JButton("Mostrar el contingut del dir");
		btnMostrarDirs.setFont(new Font("Tahoma", Font.BOLD, 14));

		btnMostrarDirs.setBounds(779, 115, 227, 33);
		contentPane.add(btnMostrarDirs);

		btnBuscarCoincidncies = new JButton("Buscar coincidències");
		btnBuscarCoincidncies.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBuscarCoincidncies.setBounds(779, 175, 227, 33);
		contentPane.add(btnBuscarCoincidncies);

		btnReemplazarParaula = new JButton("Reemplaçar paraula");
		btnReemplazarParaula.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnReemplazarParaula.setBounds(779, 236, 227, 33);
		contentPane.add(btnReemplazarParaula);

		JLabel lblBuscarParaula = new JLabel("Buscar paraula en els fixers:");
		lblBuscarParaula.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBuscarParaula.setBounds(22, 372, 212, 17);
		contentPane.add(lblBuscarParaula);

		txtBuscarParaula = new JTextField();
		txtBuscarParaula.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtBuscarParaula.setColumns(10);
		txtBuscarParaula.setBounds(264, 370, 343, 20);
		contentPane.add(txtBuscarParaula);

		chkMayusMinus = new JCheckBox("Distingix majúscules i minúscules");
		chkMayusMinus.setBackground(SystemColor.info);
		chkMayusMinus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chkMayusMinus.setBounds(779, 366, 227, 23);
		contentPane.add(chkMayusMinus);

		chkAccents = new JCheckBox("Respectar accents");
		chkAccents.setBackground(SystemColor.info);
		chkAccents.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chkAccents.setBounds(779, 394, 183, 23);
		contentPane.add(chkAccents);

		JLabel lblReemplazarParaula = new JLabel("Reemplaçar paraula en els fixers:");
		lblReemplazarParaula.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblReemplazarParaula.setBounds(22, 400, 234, 17);
		contentPane.add(lblReemplazarParaula);

		txtReemplazarParaula = new JTextField();
		txtReemplazarParaula.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtReemplazarParaula.setColumns(10);
		txtReemplazarParaula.setBounds(264, 400, 343, 20);
		contentPane.add(txtReemplazarParaula);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 51, 747, 311);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
	}

}
