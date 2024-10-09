package activitat;

public class Principal {

	public static void main(String[] args) {
		Modelo model = new Modelo();
		Vista vista = new Vista();
		@SuppressWarnings("unused")
		Controlador controlador = new Controlador(model, vista);
	}

}
