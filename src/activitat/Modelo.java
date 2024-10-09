package activitat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * @author Boromir
 * @version 1.0.0
 */
public class Modelo {

	/**
	 * Llista de forma recursiva els elements d'un directori.
	 * 
	 * @param arr    Arxiu o directoris a llistar.
	 * @param index  Índex actual en el qual es troba la iteració.
	 * @param nivell Nivell d'indentació dins dels subdirectoris.
	 * @return Cadena amb el llistat dels elements.
	 */
	public String llistarElementesIDirRecursivo(File[] arr, int index, int nivell) {
		if (arr.length != 0) {
			StringBuilder resultat = new StringBuilder();

			if (index == arr.length)

				return resultat.toString();

			if (index == 0 && nivell == 0)
				resultat.append(ultimeDirectori(arr[0]) + "\n");

			for (int i = 0; i < nivell; i++)

				resultat.append("|  ");

			if (arr[index].isFile()) {

				resultat.append("|-- ").append(dadesFixer(arr[index])).append("\n");

			} else if (arr[index].isDirectory() && arr[index].listFiles().length > 0) {

				resultat.append("|--\\").append(arr[index].getName()).append("\n");

				resultat.append(llistarElementesIDirRecursivo(arr[index].listFiles(), 0, nivell + 1));

			}

			resultat.append(llistarElementesIDirRecursivo(arr, ++index, nivell));

			return resultat.toString();
		} else {
			JOptionPane.showMessageDialog(null, "Error, comprova la ruta!");
			return "";
		}
	}

	/**
	 * Retorna el nom de l'últim directori en la ruta d'un arxiu.
	 * 
	 * @param fixer Arxiu del qual es vol obtenir el nom del directori.
	 * @return Nom de l'últim directori.
	 */
	String ultimeDirectori(File fixer) {
		String ruta = fixer.getParent();
		if (ruta == null || ruta.isEmpty()) {
			return "";
		}
		String[] parts = ruta.split("\\\\");
		return parts[parts.length - 1];
	}

	/**
	 * Obté les dades d'un arxiu: nom, grandària i data de modificació.
	 * 
	 * @param file Arxiu del qual es volen obtenir les dades.
	 * @return Cadena amb les dades de l'arxiu.
	 */
	String dadesFixer(File file) {
		double grandariaArxiuKB = file.length() / 1024.0;

		String grandaria = String.format("%.1f", grandariaArxiuKB);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		String dataHoraCreat = sdf.format(new Date(file.lastModified()));

		return file.getName() + " (" + grandaria + "KB - " + dataHoraCreat + ")";
	}

	/**
	 * Llista les coincidències d'una paraula dins d'un directori o arxiu, permetent
	 * opcions com l'accent i les majúscules.
	 * 
	 * @param arr              Arxiu o directoris a llistar.
	 * @param index            Índex actual en el qual es troba la iteració.
	 * @param nivell           Nivell d'indentació dins dels subdirectoris.
	 * @param paraulaPerBuscar Paraula a buscar.
	 * @param accentSensible   Indica si s'han de tenir en compte els accents.
	 * @param mayusSensible    Indica si s'han de tenir en compte les majúscules.
	 * @return Cadena amb les coincidències trobades.
	 */
	public String llistarCoincidencies(File[] arr, int index, int nivell, String paraulaPerBuscar,
			boolean accentSensible, boolean mayusSensible) {
		if (arr.length != 0) {
			StringBuilder resultat = new StringBuilder();

			if (index == arr.length)

				return resultat.toString();

			if (index == 0 && nivell == 0)
				resultat.append(ultimeDirectori(arr[0]) + "\n");

			for (int i = 0; i < nivell; i++)

				resultat.append("|  ");

			if (arr[index].isFile()) {

				if (arr[index].toString().endsWith(".pdf")) {

					resultat.append(
							("|-- " + arr[index].getName() + " (" + buscarCoincidenciesPdf(arr[index].getAbsolutePath(),
									paraulaPerBuscar, accentSensible, mayusSensible) + " coincidències)"))
							.append("\n");

				} else {

					resultat.append(
							("|-- " + arr[index].getName() + " (" + buscarCoincidencies(arr[index].getAbsolutePath(),
									paraulaPerBuscar, accentSensible, mayusSensible) + " coincidències)"))
							.append("\n");

				}

			} else if (arr[index].isDirectory() && arr[index].listFiles().length > 0) {

				resultat.append("|--\\").append(arr[index].getName()).append("\n");

				resultat.append(llistarCoincidencies(arr[index].listFiles(), 0, nivell + 1, paraulaPerBuscar,
						accentSensible, mayusSensible));

			}

			resultat.append(
					llistarCoincidencies(arr, ++index, nivell, paraulaPerBuscar, accentSensible, mayusSensible));

			return resultat.toString();
		} else {
			JOptionPane.showMessageDialog(null, "Error, comprova la ruta!");
			return "";
		}
	}

	/**
	 * Busca el nombre de coincidències d'una paraula en un arxiu de text.
	 * 
	 * @param fixerRuta      Ruta de l'arxiu.
	 * @param paraula        Paraula a buscar.
	 * @param accentSensible Indica si s'han de tenir en compte els accents.
	 * @param mayusSensible  Indica si s'han de tenir en compte les majúscules.
	 * @return Nombre de coincidències trobades.
	 */
	int buscarCoincidencies(String fixerRuta, String paraula, boolean accentSensible, boolean mayusSensible) {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(fixerRuta), StandardCharsets.UTF_8));

			StringBuilder contingut = new StringBuilder();
			String linia;

			while ((linia = br.readLine()) != null) {
				if (!accentSensible) {
					linia = Normalizer.normalize(linia, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
					paraula = Normalizer.normalize(paraula, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
				}

				contingut.append(linia).append(" ");
			}

			br.close();

			String text = contingut.toString();
			String paraulaBuscada = paraula;

			if (!mayusSensible) {
				text = text.toLowerCase();
				paraulaBuscada = paraula.toLowerCase();
			}

			String patro = "\\b" + Pattern.quote(paraulaBuscada) + "\\b";
			Pattern pattern = Pattern.compile(patro);
			Matcher matcher = pattern.matcher(text);

			int apareix = 0;

			while (matcher.find()) {
				apareix++;
			}

			return apareix;

		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Busca el nombre de coincidències d'una paraula en un arxiu PDF.
	 * 
	 * @param fixerRuta      Ruta de l'arxiu PDF.
	 * @param paraula        Paraula a buscar.
	 * @param accentSensible Indica si s'han de tenir en compte els accents.
	 * @param mayusSensible  Indica si s'han de tenir en compte les majúscules.
	 * @return Nombre de coincidències trobades.
	 */
	int buscarCoincidenciesPdf(String fixerRuta, String paraula, boolean accentSensible, boolean mayusSensible) {
		try {
			File fixerPdf = new File(fixerRuta);
			PDDocument document = Loader.loadPDF(fixerPdf);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String text = pdfStripper.getText(document);
			String paraulaBuscada = paraula;

			if (!accentSensible) {
				text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
				paraulaBuscada = Normalizer.normalize(paraulaBuscada, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
			}

			if (!mayusSensible) {
				text = text.toLowerCase();
				paraulaBuscada = paraulaBuscada.toLowerCase();
			}

			String patro = "\\b" + Pattern.quote(paraulaBuscada) + "\\b";
			Pattern pattern = Pattern.compile(patro);
			Matcher matcher = pattern.matcher(text);

			int apareix = 0;

			while (matcher.find()) {
				apareix++;
			}

			document.close();
			return apareix;

		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Aquesta funció llista tots els arxius i directoris dins d'un array de
	 * fitxers, i compta quantes vegades s'ha realitzat un reemplaçament d'una
	 * paraula específica.
	 * 
	 * @param arr              Un array de fitxers i directoris a llistar.
	 * @param index            L'índex actual dins de l'array.
	 * @param nivell           El nivell actual de profunditat en la jerarquia de
	 *                         directoris.
	 * @param paraulaPerBuscar La paraula que es vol buscar dins dels fitxers.
	 * @param paraulaCanviar   La paraula que substituirà la paraula buscada.
	 * @param accentSensible   Un booleà que indica si la cerca ha de ser sensible
	 *                         als accents.
	 * @param mayusSensible    Un booleà que indica si la cerca ha de ser sensible a
	 *                         majúscules.
	 * @return Un String amb la llista dels fitxers i directoris, incloent el nombre
	 *         de reemplaços.
	 */
	public String llistarReemplacos(File[] arr, int index, int nivell, String paraulaPerBuscar,

			String paraulaCanviar, boolean accentSensible, boolean mayusSensible) {
		if (arr.length != 0) {
			StringBuilder resultat = new StringBuilder();

			if (index == arr.length)

				return resultat.toString();

			if (index == 0 && nivell == 0)
				resultat.append(ultimeDirectori(arr[0]) + "\n");

			for (int i = 0; i < nivell; i++)

				resultat.append("|  ");

			if (arr[index].isFile()) {

				if (!arr[index].toString().endsWith(".txt")) {

					resultat.append(("|-- " + arr[index].getName() + " (0 reemplaços)")).append("\n");

				} else {

					resultat.append(("|-- " + arr[index].getName() + " ("

							+ reemplacarParaula(arr[index], paraulaPerBuscar, paraulaCanviar, accentSensible,
									mayusSensible)

							+ " reemplaços)")).append("\n");

				}

			} else if (arr[index].isDirectory() && arr[index].listFiles().length > 0) {

				resultat.append("|--\\").append(arr[index].getName()).append("\n");

				resultat.append(llistarReemplacos(arr[index].listFiles(), 0, nivell + 1, paraulaPerBuscar,
						paraulaCanviar, accentSensible, mayusSensible));

			}

			resultat.append(llistarReemplacos(arr, ++index, nivell, paraulaPerBuscar, paraulaCanviar, accentSensible,
					mayusSensible));

			return resultat.toString();
		} else {
			JOptionPane.showMessageDialog(null, "Error, comprova la ruta!");
			return "";
		}

	}

	/**
	 * Aquesta funció reemplaça una paraula per una altra en un fitxer, i retorna el
	 * nombre de vegades que s'ha realitzat el reemplaçament.
	 * 
	 * @param fixer          El fitxer en el qual es realitzarà el reemplaçament.
	 * @param paraulaBuscar  La paraula que es vol buscar i reemplaçar.
	 * @param paraulaCanviar La paraula que substituirà la paraula buscada.
	 * @param accentSensible Un booleà que indica si la cerca ha de ser sensible als
	 *                       accents.
	 * @param mayusSensible  Un booleà que indica si la cerca ha de ser sensible a
	 *                       majúscules.
	 * @return El nombre de reemplaços realitzats.
	 */
	int reemplacarParaula(File fixer, String paraulaBuscar, String paraulaCanviar, boolean accentSensible,
			boolean mayusSensible) {
		try {
			FileWriter fw = new FileWriter(fixer.getParent() + "\\MOD_" + fixer.getName(), StandardCharsets.UTF_8);
			BufferedWriter bw = new BufferedWriter(fw);

			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(fixer), StandardCharsets.UTF_8));

			String linia;
			int reemplacos = 0;

			String paraulaBuscarNormalitzada = paraulaBuscar;
			String paraulaCanviarNormalitzada = paraulaCanviar;

			if (!accentSensible) {
				paraulaBuscarNormalitzada = Normalizer.normalize(paraulaBuscarNormalitzada, Normalizer.Form.NFD)
						.replaceAll("\\p{M}", "");
				paraulaCanviarNormalitzada = Normalizer.normalize(paraulaCanviarNormalitzada, Normalizer.Form.NFD)
						.replaceAll("\\p{M}", "");
			}

			if (!mayusSensible) {
				paraulaBuscarNormalitzada = paraulaBuscarNormalitzada.toLowerCase();
			}

			while ((linia = br.readLine()) != null) {
				String compararLinia = linia;

				if (!mayusSensible) {
					compararLinia = compararLinia.toLowerCase();
				}

				if (!accentSensible) {
					compararLinia = Normalizer.normalize(compararLinia, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
				}

				reemplacos = (compararLinia.length() - compararLinia.replace(paraulaBuscarNormalitzada, "").length())
						/ paraulaBuscarNormalitzada.length();

				String modifiedLine = compararLinia.replace(paraulaBuscarNormalitzada, paraulaCanviarNormalitzada);

				bw.write(modifiedLine);
				bw.newLine();
			}

			bw.close();
			br.close();

			return reemplacos;

		} catch (Exception e) {
			return 0;
		}
	}
}