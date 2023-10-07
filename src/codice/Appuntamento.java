/**
 * Questa classe gestisce la correttezza dei parametri dell'appuntamento e controlla eventuali
 * sovrapposizioni
 */

package codice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appuntamento {
	private LocalDateTime dataOra;
	private String nomePersona;
	private String luogoApp;
	private int durata;
	private static final String LUOGO_DEFAULT = "";
	private static final int DURATA_DEFAULT = 0;

	/**
	 * Costruttore di appuntamento
	 * 
	 * @param data   contiene una data e ora nel formato dd/MM/yyyy HH:mm
	 * @param nome   specifica il nome della persona con cui si ha un appuntamento
	 * @param luogo  identifica il luogo dell'appuntamento
	 * @param durata indica la durata dell'appuntamento
	 */
	
	public Appuntamento(LocalDateTime data, String nome, String luogo, int durata) {
		this.dataOra = data;
		this.nomePersona = nome;
		this.luogoApp = luogo;
		this.durata = durata;
	}

	/**
	 * Costruttore di appuntamento
	 * 
	 * @param nome   specifica il nome della persona con cui si ha un appuntamento
	 * @param data   contiene una data nel formato dd/MM/yyyy
	 * @param ora    contiene un'ora nel formato HH:mm
	 * @param luogo  identifica il luogo dell'appuntamento
	 * @param durata indica la durata dell'appuntamento
	 */

	public Appuntamento(String nome, String data, String ora, String luogo, int durata) {
		String dataCheck = data + " " + ora;
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		this.dataOra = LocalDateTime.parse(dataCheck, pattern);
		this.nomePersona = nome;
		this.luogoApp = luogo;
		this.durata = durata;
	}

	/**
	 * Costruttore di appuntamento
	 * 
	 * @param nome specifica il nome della persona con cui si ha un appuntamento
	 * @param data contiene una data nel formato dd/MM/yyyy
	 * @param ora  contiene un'ora nel formato HH:mm
	 */

	public Appuntamento(String nome, String data, String ora) {
		this(nome, data, ora, LUOGO_DEFAULT);
	}

	/**
	 * Costruttore di appuntamento
	 * 
	 * @param nome   specifica il nome della persona con cui si ha un appuntamento
	 * @param data   contiene una data nel formato dd/MM/yyyy
	 * @param ora    contiene un'ora nel formato HH:mm
	 * @param durata indica la durata dell'appuntamento
	 */

	public Appuntamento(String nome, String data, String ora, int durata) {
		this(nome, data, ora, LUOGO_DEFAULT, durata);
	}

	/**
	 * Costruttore di appuntamento
	 * 
	 * @param nome  specifica il nome della persona con cui si ha un appuntamento
	 * @param data  contiene una data nel formato dd/MM/yyyy
	 * @param ora   contiene un'ora nel formato HH:mm
	 * @param luogo identifica il luogo dell'appuntamento
	 */
	public Appuntamento(String nome, String data, String ora, String luogo) {
		this(nome, data, ora, luogo, DURATA_DEFAULT);
	}

	/**
	 * Questo metodo confronta l'oggetto passato come parametro con l'appuntamento
	 * 
	 * @param appuntamento contiene l'oggetto da confrontare con il nostro
	 *                     appuntamento
	 * @return true se gli oggetti confrontati sono entrambi di tipo Appuntamento e
	 *         con lo stesso contenuto; false altrimenti
	 */
	@Override
	public boolean equals(Object appuntamento) {
		if (appuntamento == null || appuntamento.getClass() != this.getClass())
			return false;
		Appuntamento test = (Appuntamento) appuntamento;
		if (!this.dataOra.equals(test.dataOra))
			return false;
		if (this.durata != test.durata)
			return false;
		if (!this.luogoApp.equals(test.luogoApp))
			return false;
		if (!this.nomePersona.equals(test.nomePersona))
			return false;
		return true;
	}

	/**
	 * Questo metodo restituisce l'oggetto Appuntamento sotto forma di String
	 * separando i vari attributi con il ";"
	 * 
	 * @return restituisce la rappresentazione in String dell'oggetto Appuntamento
	 *         es: dd/MM/yyyy;HH:mm;nome;luogo;durata
	 */
	@Override
	public String toString() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy" + ";" + "HH:mm");
		return this.nomePersona + ";" + formato.format(dataOra) + ";" + this.luogoApp + ";" + this.durata;
	}

	public LocalDate getData() {
		return dataOra.toLocalDate();
	}


	public int getOra() {
		return this.dataOra.getHour();
	}

	public int getMinuti() {
		return this.dataOra.getMinute();
	}

	public String getLuogo() {
		return luogoApp;
	}

	public void setLuogo(String luogo) {
		this.luogoApp = luogo;
	}

	public String getNome() {
		return nomePersona;
	}

	public void setNome(String nome) {
		this.nomePersona = nome;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}

	public int getDurata() {
		return durata;
	}
	
	/**
	 * Questo metodo aggiorna il localDateTime dell'istanza corrente aggiungendo 
	 * la durata dell'appuntamento
	 * 
	 * @return un nuovo oggetto di tipo LocalDateTime 
	 */

	public LocalDateTime fineAppuntamento() {
		return this.dataOra.plusMinutes(this.getDurata());
	}

	/**
	 * Questo metodo confronta l'appuntamento dell'istanza corrente con quello
	 * passato come parametro, controllando che non si sovrappongano
	 * 
	 * @param check l'appuntamento da confrontare
	 * @return false se si sovrappongono, true altrimenti
	 */
	
	public boolean sovrapposto(Appuntamento check) {
		LocalDateTime fineCorrente = this.fineAppuntamento();
		LocalDateTime fineCheck = check.fineAppuntamento();
		if (check.dataOra.isBefore(this.dataOra))
			return !(fineCheck.isAfter(this.dataOra));
		else
			return !(fineCorrente.isAfter(check.dataOra));

	}

}
