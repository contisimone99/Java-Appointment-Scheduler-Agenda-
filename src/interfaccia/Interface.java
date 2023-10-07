/**
 * @author Matteo Pasotti 20035991
 * @author Simone Conti 20034446
 */
package interfaccia;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;

import codice.Agenda;
import codice.Appuntamento;
import exception.AgendaException;
import exception.AppuntamentoException;
import exception.TimeException;
import jbook.util.Input;

public class Interface {

	public static void main(String[] args) {
		Agenda lavoro = new Agenda();
		mainMenu(lavoro);
		System.out.println("Chiusura Programma");
	}

	public static void mainMenu(Agenda target) {
		int answer;
		do {
			System.out.println("1: Inserimento appuntamento");
			System.out.println("2: Rimozione appuntamento");
			System.out.println("3: Modifica appuntamento");
			System.out.println("4: Ricerca appuntamenti");
			System.out.println("5: Mostra appuntamenti");
			System.out.println("6: Lettura/scrittura da/su file");
			System.out.println("7: Termina");
			answer = Input.readInt();
			switch (answer) {
			case 1:
				inserimentoMenu(target);
				break;
			case 2:
				eliminaMenu(target);
				break;
			case 3:
				modificaMenu(target);
				break;
			case 4:
				searchMenu(target);
				break;
			case 5:
				printMenu(target);
				break;
			case 6:
				fileMenu(target);
			case 7:
				break;
			}
		} while (answer != 7);

	}

	public static void modificaMenu(Agenda target) {
		System.out.println("Modifica appuntamento:");
		System.out.println("Specificare come cercare l'appuntamento da modificare");
		Appuntamento delete = searchMenu(target);
		String nome;
		String luogo;
		LocalDateTime newData;
		String dataOra;
		int durata;
		boolean result = false;
		System.out.println("Inserire un nome:");
		while (true) {
			nome = Input.readString();
			if (nome.equals("")) {
				System.out.println("il nome non può essere vuoto");
				continue;
			}
			break;
		}
		while (true) {
			System.out.println("Inserire una data e ora nel formato dd/MM/yyyy HH:mm");
			dataOra = Input.readString();
			try {
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				newData = LocalDateTime.parse(dataOra, formato);
				break;
			} catch (DateTimeParseException ex) {
				System.out.println(
						"La data inserita non e' corretta, si prega di inserirla nuovamente seguendo il formato");
				continue;
			}
		}

		System.out.println("Inserire un luogo (premere invio per lasciare vuoto)");
		luogo = Input.readString();
		System.out.println("Inserire una durata (se non si vuole inserire una durata usare 0)");
		durata = Input.readInt();
		Appuntamento app = new Appuntamento(newData, nome, luogo, durata);
		try {
			result = target.modifica(delete, app);
		} catch (AppuntamentoException msg) {
			System.out.println("Errore: " + msg.getMessage());
		} catch (TimeException msg) {
			System.out.println("Errore: " + msg.getMessage());
		}
		if (result)
			System.out.println("Appuntamento modificato con successo");
		else
			System.out.println("Non e' stato possibile modificare l'appuntamento");
		return;
	}

	static void fileMenu(Agenda target) {
		int answer;
		do {
			System.out.println("1: Salvare l'agenda su file");
			System.out.println("2: Riempire l'agenda da un file");
			System.out.println("3: Tornare al menu' precedente");
			answer = Input.readInt();
			switch (answer) {
			case 1:
				scriviFile(target);
				break;
			case 2:
				leggiFile(target);
				break;
			case 3:
				break;
			}
		} while (answer != 3);

	}

	public static void printMenu(Agenda target) {
		Iterator<Appuntamento> it = target.iterator();
		int i = 0;
		while (it.hasNext()) {
			System.out.println(i + " Appuntamento: " + it.next().toString());
			++i;
		}
		System.out.println();

	}

	public static void inserimentoMenu(Agenda target) {
		System.out.println("Inserire un nome:");
		String nome;
		String luogo;
		LocalDateTime newData;
		String dataOra;
		int durata;
		while (true) {
			nome = Input.readString();
			if (nome.equals("")) {
				System.out.println("il nome non può essere vuoto");
				continue;
			}
			break;
		}
		while (true) {
			System.out.println("Inserire una data e ora nel formato dd/MM/yyyy HH:mm");
			dataOra = Input.readString();
			try {
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				newData = LocalDateTime.parse(dataOra, formato);
				break;
			} catch (DateTimeParseException ex) {
				System.out.println(
						"La data inserita non e' corretta, si prega di inserirla nuovamente seguendo il formato");
				continue;
			}
		}

		System.out.println("Inserire un luogo (premere invio per lasciare vuoto)");
		luogo = Input.readString();
		System.out.println("Inserire una durata (se non si vuole inserire una durata usare 0)");
		durata = Input.readInt();
		Appuntamento app = new Appuntamento(newData, nome, luogo, durata);
		try {
			target.aggiungi(app);
		} catch (AgendaException msg) {
			System.out.println("Errore: " + msg.getMessage());
		}
		System.out.println();

		return;
	}

	public static void scriviFile(Agenda target) {
		String fileNameOut;
		while (true) {
			System.out.println("Inserire il nome del file:");
			fileNameOut = Input.readString();
			try {
				target.scriviAgenda(fileNameOut);
				break;
			} catch (IOException msg) {
				System.out.println("Il file inserito non esiste; inserirne uno valido");
				continue;
			}
		}
	}

	public static void leggiFile(Agenda target) {
		String fileNameIn;
		while (true) {
			System.out.println("Inserire il nome del file:");
			fileNameIn = Input.readString();
			try {
				target.leggiAgenda(fileNameIn);
				break;
			} catch (IOException msg) {
				System.out.println("Il file inserito non esiste; inserirne uno valido");
				continue;
			} catch (AgendaException msg) {
				System.out.println("Errore: " + msg.getMessage());
			}
		}

	}

	public static Appuntamento searchMenu(Agenda target) {
		int answer;
		String dataOra;
		LocalDateTime newData;
		LocalDateTime newData2;
		LocalDate data;
		String nome;
		ArrayList<Appuntamento> result = new ArrayList<>();
		System.out.println("1: Ricerca appuntamenti compresi tra due date");
		System.out.println("2: Ricerca appuntamenti in una data");
		System.out.println("3: Ricerca appuntamenti per nome");
		answer = Input.readInt();
		switch (answer) {
		case 1:
			while (true) {
				System.out.println("Inserire una data e ora nel formato dd/MM/yyyy HH:mm");
				dataOra = Input.readString();
				try {
					DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
					newData = LocalDateTime.parse(dataOra, formato);
					break;
				} catch (DateTimeParseException ex) {
					System.out.println(
							"La data inserita non e' corretta, si prega di inserirla nuovamente seguendo il formato");
					continue;
				}
			}
			while (true) {
				System.out.println("Inserire una data e ora nel formato dd/MM/yyyy HH:mm");
				dataOra = Input.readString();
				try {
					DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
					newData2 = LocalDateTime.parse(dataOra, formato);
					break;
				} catch (DateTimeParseException ex) {
					System.out.println(
							"La data inserita non e' corretta, si prega di inserirla nuovamente seguendo il formato");
					continue;
				}
			}
			result = target.ricerca(newData, newData2);

			break;
		case 2:
			while (true) {
				System.out.println("Inserire una data e ora nel formato dd/MM/yyyy");
				dataOra = Input.readString();
				try {
					DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					data = LocalDate.parse(dataOra, formato);
					break;
				} catch (DateTimeParseException ex) {
					System.out.println(
							"La data inserita non e' corretta, si prega di inserirla nuovamente seguendo il formato");
					continue;
				}
			}
			result = target.ricercaData(data);
			break;
		case 3:
			System.out.println("Inserire il nome");
			while (true) {
				nome = Input.readString();
				if (nome.equals("")) {
					System.out.println("il nome non può essere vuoto");
					continue;
				}
				break;
			}
			result = target.ricercaNome(nome);
			break;
		}

		Iterator<Appuntamento> it = result.iterator();
		int i = 0;
		while (it.hasNext()) {
			System.out.println(i + " Appuntamento: " + it.next().toString());
			++i;
		}
		if (result.size() == 0)
			return null;
		if(result.size()==1) {
			System.out.println("Selezione automatica dell'unico appuntamento disponibile");
			return result.get(0);
		}
		System.out.println("Quale appuntamento vuoi?");
		int index = Input.readInt();
		return result.get(index);
	}

	public static void eliminaMenu(Agenda target) {
		Appuntamento del = searchMenu(target);
		target.elimina(del);
		System.out.println("Appuntamento eliminato con successo");

	}
}
