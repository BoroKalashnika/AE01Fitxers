package activitat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

public class Controlador {
	private Modelo model;
	private Vista vista;
	private ActionListener actionListener_mostrar, actionListener_buscar, actionListener_reemplazar;

	public Controlador(Modelo model, Vista vista) {
		this.model = model;
		this.vista = vista;
		control();
	}

	public void control() {
		actionListener_mostrar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				vista.getTextArea().setText((model.llistarElementesIDirRecursivo(fixer(), 0, 0)));

			}
		};

		actionListener_buscar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!vista.getTxtBuscarParaula().getText().isEmpty()) {
					vista.getTextArea()
							.setText((model.llistarCoincidencies(fixer(), 0, 0, vista.getTxtBuscarParaula().getText(),
									vista.getChkAccents().isSelected(), vista.getChkMayusMinus().isSelected())));
				} else {
					JOptionPane.showMessageDialog(null, "Error, has d'introduir el filtre!");
				}
			}
		};

		actionListener_reemplazar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!vista.getTxtBuscarParaula().getText().isEmpty()
						&& !vista.getTxtReemplazarParaula().getText().isEmpty()) {
					vista.getTextArea()
							.setText((model.llistarReemplacos(fixer(), 0, 0, vista.getTxtBuscarParaula().getText(),
									vista.getTxtReemplazarParaula().getText(), vista.getChkAccents().isSelected(),
									vista.getChkMayusMinus().isSelected())));
				} else {
					JOptionPane.showMessageDialog(null, "Error, has d'introduir el filtre y la paraula a reemplaçar!");
				}
			}
		};

		vista.getBtnMostrarDirs().addActionListener(actionListener_mostrar);
		vista.getbtnBuscarCoincidncies().addActionListener(actionListener_buscar);
		vista.getbtnReemplazarParaula().addActionListener(actionListener_reemplazar);

	}

	File[] fixer() {

		File arr[] = new File(vista.getTxtDir().getText()).listFiles();
		return arr != null ? arr : new File[0];

	}

}
