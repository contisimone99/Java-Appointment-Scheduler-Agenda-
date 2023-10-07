/**
 * La classe Agenda effettua le operazioni di aggiunta, elimina, modifica, lettura/scrittura su file
 *  e la iterazione. Questa classe lavora insieme alla classe Appuntamento richiamando 
 * i vari metodi che lavorano con il tempo, il formato dei dati e i controlli. 
 */

package codice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import exception.AgendaException;
import exception.AppuntamentoException;
import exception.TimeException;

public class Agenda implements Iterable<Appuntamento> {
	private ArrayList<Appuntamento> agenda;

	/**
	 * Costruttore di agenda
	 */
	public Agenda() {
		agenda = new ArrayList<Appuntamento>();
	}

	/**
	 * Questo metodo inserisce un nuovo appuntamento nell'agenda, controllando che
	 * non vada in conflitto con altri appuntamenti già esistenti nell'agenda
	 * 
	 * @param daControllare contiene l'appuntamento che si vuole aggiungere
	 * @throws AppuntamentoException nel caso in cui l'appuntamento sia già presente
	 *                               nell'agenda
	 * @throws TimeException         nel caso in cui non sia possibile inserire
	 *                               l'appuntamento a causa di mancanza di tempo
	 *                               dovuto a conflitti tra appuntamenti
	 */
	public void aggiungi(Appuntamento daControllare) throws AppuntamentoException, TimeException {
		int i = 0;
		for (; (i < agenda.size()) && 
				(!agenda.get(i).getDataOra().isAfter(daControllare.getDataOra())); i++);
		if (((i == 0) || (daControllare.sovrapposto(agenda.get(i - 1))))
				&& ((i == agenda.size()) || (daControllare.sovrapposto(agenda.get(i))))) {
			agenda.add(i, daControllare);
			return;
		}
		if (agenda.contains(daControllare))
			throw new AppuntamentoException("Appuntamento gia' esistente");
		else
			throw new TimeException("Mancanza di tempo");

	}

	/**
	 * Questo metodo elimina dall'agenda un Appuntamento se presente
	 * 
	 * @param daEliminare l'appuntamento da eliminare
	 * @return true se viene eliminato un elemento dall'agenda, false altrimenti
	 */
	public boolean elimina(Appuntamento daEliminare) {
		return agenda.remove(daEliminare);
	}

	/**
	 * Questo metodo elimina dall'agenda un Appuntamento in base all'indice
	 * 
	 * @param indice la posizione da cui si vuole eliminare l'appuntamento
	 * @return true se elimina l'elemento, altrimenti lancia un'eccezione
	 * @throws IndexOutOfBoundsException se l'indice è fuori dal range
	 */

	public boolean elimina(int indice) throws IndexOutOfBoundsException {
		agenda.remove(indice);
		return true;
	}

	/**
	 * Questo metodo modifica se possibile un appuntamento presente nella rubrica
	 * con uno nuovo
	 * 
	 * @param old    il vecchio appuntamento da modificare
	 * @param newApp il nuovo appuntamento da inserire
	 * @return true se non ci sono stati problemi con la modifica, false altrimenti
	 * @throws AppuntamentoException nel caso in cui il nuovo appuntamento che si
	 *                               cerca di inserire sia già presente nell'agenda
	 * @throws TimeException  nel caso in cui non sia possibile inserire il
	 *                        nuovo appuntamento a causa di mancanza di tempo
	 *                        dovuto a conflitti tra appuntamenti
	 */
	public boolean modifica(Appuntamento old, Appuntamento newApp) throws AppuntamentoException, TimeException {
		agenda.remove(old);
		try {
			aggiungi(newApp);
		} catch (AgendaException err) {
			aggiungi(old);
			return false;
		}
		return true;
	}

	/**
	 * Questo metodo si occupa di cercare tutti gli appuntamenti compresi tra due
	 * date e di restituirne la lista
	 * 
	 * @param begin data di inizio
	 * @param end   data di fine
	 * @return l'insieme degli Appuntamenti compresi tra le due date
	 */

	public ArrayList<Appuntamento> ricerca(LocalDateTime begin, LocalDateTime end) {
		ArrayList<Appuntamento> out = new ArrayList<Appuntamento>();
		if (!begin.isBefore(agenda.get(agenda.size() - 1).getDataOra()))
			return out;
		int startIndex = cercaTemporale(begin, true);
		int endIndex = cercaTemporale(end, false);
		if (endIndex == -1)
			endIndex = agenda.size() - 1;
		for (int i = startIndex; i <= endIndex; i++) {
			out.add(agenda.get(i));
		}
		return out;
	}

	/**
	 * Questo metodo definisce in che posizione dell'agenda gli appuntamenti sono
	 * compresi nel range delle date
	 * 
	 * @param data la data da usare per controllare gli appuntamenti
	 * @param pos  se true indica che l'elemento passato è la data di inizio, se
	 *             false indica che è la data di fine
	 * @return l'indice dell'agenda dove la data è prima della data di un
	 *         Appuntamento successivo; -1 nel caso in cui si è arrivati in fondo
	 *         all'agenda
	 */

	private int cercaTemporale(LocalDateTime data, boolean pos) {
		for (Appuntamento x : agenda) {
			if (x.getDataOra().isAfter(data))
				return pos ? agenda.indexOf(x) : agenda.indexOf(x) - 1;
		}
		return -1;
	}

	/**
	 * Questo metodo si occupa di cercare tutti gli Appuntamenti nell'agenda che
	 * contengono il nome specificato
	 * 
	 * @param nome il nome della persona con cui si ha appuntamento
	 * @return l'insieme degli appuntamenti che contengono il nome
	 */

	public ArrayList<Appuntamento> ricercaNome(String nome) {
		ArrayList<Appuntamento> out = new ArrayList<Appuntamento>();
		for (Appuntamento x : agenda)
			if (x.getNome().equals(nome))
				out.add(x);
		return out;
	}

	/**
	 * Questo metodo si occupa di cercare tutti gli Appuntamenti nell'agenda che
	 * avvengono in una data specificata
	 * 
	 * @param data la data dell'Appuntamento in cui si svolge
	 * @return l'insieme degli appuntamenti che contengono la data specificata
	 */

	public ArrayList<Appuntamento> ricercaData(LocalDate data) {
		ArrayList<Appuntamento> out = new ArrayList<Appuntamento>();
		for (Appuntamento x : agenda)
			if (x.getDataOra().toLocalDate().equals(data))
				out.add(x);
		return out;
	}

	/**
	 * Questo metodo legge da un file tutti gli appuntamenti presenti, e controlla
	 * che siano elementi validi
	 * 
	 * @param fileName il nome del file da aprire
	 * @throws IOException           se avviene un errore nell'operazione di I/O
	 * @throws AppuntamentoException nel caso in cui l'appuntamento sia già presente
	 *                               nell'agenda
	 * @throws TimeException         nel caso in cui non sia possibile inserire
	 *                               l'appuntamento a causa di mancanza di tempo
	 *                               dovuto a conflitti tra appuntamenti
	 */

	public void leggiAgenda(String fileName) throws IOException, AppuntamentoException, TimeException {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String linea = in.readLine();
		while (linea != null) {
			String[] app = linea.split(";");
			int lung = app.length;
			if (app[0].matches("[A-Z][a-zA-Z]+") // controllo nome
					&& app[1].matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$")// controllo data
					&& (app[2].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))) {// controllo ora
				if (lung == 3)
					aggiungi(new Appuntamento(app[0], app[1], app[2]));
				else if (lung == 4) {
					if (app[3].matches("[0-9]+"))
						aggiungi(new Appuntamento(app[0], app[1], app[2], Integer.parseInt(app[4])));
					else
						aggiungi(new Appuntamento(app[0], app[1], app[2], app[4]));
				} else if (lung == 5)
					aggiungi(new Appuntamento(app[0], app[1], app[2], app[3], Integer.parseInt(app[4])));
			}
			linea = in.readLine();
		}
		in.close();
	}

	/**
	 * Questo metodo scrive su un file tutti gli appuntamenti salvati sull'agenda
	 * 
	 * @param fileName nome del file da creare/aprire
	 * @throws IOException nel caso in cui ci siano stati errori nelle operazioni di
	 *                     I/O
	 */

	public void scriviAgenda(String fileName) throws IOException {
		PrintWriter out = new PrintWriter(new File(fileName));
		for (Appuntamento daScrivere : agenda) {
			String appuntamento = daScrivere.toString();
			out.println(appuntamento);
		}
		out.close();
	}

	/**
	 * Metodo che restituisce un oggetto di tipo iterator di agenda
	 */

	@Override
	public Iterator<Appuntamento> iterator() {
		return agenda.iterator();
	}

	/**
	 * Metodo che restituisce il numero di appuntamenti presenti nell'agenda
	 * 
	 * @return il numero di Appuntamenti nell'agenda
	 */

	public int dim() {
		return agenda.size();
	}

}
