package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import codice.Agenda;
import codice.Appuntamento;
import exception.AgendaException;
import exception.AppuntamentoException;
import exception.TimeException;

class AgendaTest {

	@Test
	void testAggiungi() throws AppuntamentoException, TimeException {
		Agenda test = new Agenda();
		assertEquals(test.dim(), 0);
		Appuntamento check = new Appuntamento("Marco", "05/09/2021", "15:00", "Milano", 50);
		test.aggiungi(check);
		check = new Appuntamento("Luca", "05/09/2021", "16:00", "Milano", 30);
		test.aggiungi(check);
		check = new Appuntamento("Marco", "01/09/2021", "12:00", "Pavia", 50);
		test.aggiungi(check);
		check = new Appuntamento("Marco", "04/09/2021", "15:00", "Alessandria", 50);
		test.aggiungi(check);
		check = new Appuntamento("Marco", "01/09/2021", "13:00", "Pavia", 50);
		test.aggiungi(check);
		assertEquals(test.dim(),5);
	}
	
	@Test
	void testAggiungiException() throws AppuntamentoException, TimeException {
		Agenda test = new Agenda();
		Appuntamento check = new Appuntamento("Marco", "05/09/2021", "15:00", "Milano", 50);
		test.aggiungi(check);
		check = new Appuntamento("Luca", "05/09/2021", "16:00", "Milano", 30);
		test.aggiungi(check);
		check = new Appuntamento("Marco", "01/09/2021", "12:00", "Pavia", 50);
		test.aggiungi(check);
		check = new Appuntamento("Marco", "04/09/2021", "15:00", "Alessandria", 50);
		test.aggiungi(check);
		check = new Appuntamento("Marco", "01/09/2021", "13:00", "Pavia", 50);
		test.aggiungi(check);
		AgendaException msg = assertThrows(AgendaException.class,
				() -> test.aggiungi(new Appuntamento("Marco", "05/09/2021", "15:00", "Milano", 50)));
		assertEquals("Appuntamento gia' esistente", msg.getMessage());
		msg = assertThrows(AgendaException.class,
				() -> test.aggiungi(new Appuntamento("Marco", "01/09/2021", "12:30", "Pavia", 50)));
		assertEquals("Mancanza di tempo", msg.getMessage());
		msg = assertThrows(AgendaException.class,
				() -> test.aggiungi(new Appuntamento("Marco", "01/09/2021", "12:55", "Pavia", 50)));
		assertEquals("Mancanza di tempo", msg.getMessage());
	}
	@Test
	void testElimina() throws AppuntamentoException, TimeException {
		Agenda test = new Agenda();
		Appuntamento check = new Appuntamento("Marco", "05/09/2021", "15:00", "Milano", 50);
		test.aggiungi(check);
		check = new Appuntamento("Luca", "05/09/2021", "16:00", "Milano", 30);
		test.aggiungi(check);
		check = new Appuntamento("Marco", "01/09/2021", "12:00", "Pavia", 50);
		test.aggiungi(check);
		check = new Appuntamento("Marco", "04/09/2021", "15:00", "Alessandria", 50);
		test.aggiungi(check);
		check = new Appuntamento("Marco", "01/09/2021", "13:00", "Pavia", 50);
		test.aggiungi(check);
		check = new Appuntamento("Giovanni", "07/09/2021", "12:00", "Pavia", 50);
		test.aggiungi(check);
		assertEquals(test.dim(), 6);
		test.elimina(new Appuntamento("Marco", "01/09/2021", "12:00", "Pavia", 50));
		assertEquals(test.dim(), 5);
		test.elimina(2);
		assertEquals(test.dim(), 4);
	}

	@Test
	void testModifica() throws AppuntamentoException, TimeException {
		Agenda test = new Agenda();
		Appuntamento check = new Appuntamento("Marco", "05/09/2021", "15:00", "Milano", 50);
		test.aggiungi(check);
		check = new Appuntamento("Luca", "03/09/2021", "16:00", "Milano", 30);
		test.aggiungi(check);
		check = new Appuntamento("Marco", "01/09/2021", "12:00", "Pavia", 50);
		test.aggiungi(check);
		Appuntamento daMettere = new Appuntamento("Marco", "04/09/2021", "12:00", "Pavia", 50);
		boolean x = test.modifica(check, daMettere);
		assertEquals(x, true);
		daMettere = new Appuntamento("Marco", "03/09/2021", "15:50", "Pavia", 50);
		x = test.modifica(new Appuntamento("Marco", "05/09/2021", "15:00", "Milano", 50), daMettere);
		assertEquals(x, false);
	}

	@Test
	void testRicercaCompreso() throws AppuntamentoException, TimeException {
		Agenda test = new Agenda();
		Appuntamento x = new Appuntamento("Marco", "05/09/2021", "15:00", "Milano", 50);
		test.aggiungi(x);
		x = new Appuntamento("Marco", "01/09/2021", "15:00", "Pavia", 50);
		test.aggiungi(x);
		x = new Appuntamento("Marco", "10/09/2021", "15:00", "Verreto", 50);
		test.aggiungi(x);
		x = new Appuntamento("Marco", "04/09/2021", "15:00", "Lungavilla", 50);
		test.aggiungi(x);
		x = new Appuntamento("Luca", "05/09/2021", "16:00", "Oriolo", 30);
		test.aggiungi(x);
		x = new Appuntamento("Paolo", "05/09/2021", "10:00", "Torremenapace", 10);
		test.aggiungi(x);
		assertEquals(6, test.dim());
		LocalDateTime begin = LocalDateTime.of(2021, 9, 5, 0, 0);
		LocalDateTime end = LocalDateTime.of(2021, 9, 6, 0, 0);
		assertEquals(3, test.ricerca(begin, end).size());
		end = LocalDateTime.of(2021, 9, 20, 0, 0);
		assertEquals(4, test.ricerca(begin, end).size());
		begin = LocalDateTime.of(2021, 10, 5, 0, 0);
		end = LocalDateTime.of(2021, 9, 6, 0, 0);
		assertEquals(0, test.ricerca(begin, end).size());
	}

	@Test
	void testRicercaNome() throws AppuntamentoException, TimeException {
		Agenda test = new Agenda();
		Appuntamento x = new Appuntamento("Marco", "05/09/2021", "15:00", "Milano", 50);
		test.aggiungi(x);
		x = new Appuntamento("Marco", "01/09/2021", "15:00", "Pavia", 50);
		test.aggiungi(x);
		x = new Appuntamento("Luca", "10/09/2021", "15:00", "Verreto", 50);
		test.aggiungi(x);
		x = new Appuntamento("Paolo", "04/09/2021", "15:00", "Lungavilla", 50);
		test.aggiungi(x);
		assertEquals(test.ricercaNome("Piergiuseppa").size(), 0);
		assertEquals(test.ricercaNome("Marco").size(), 2);
	}

	@Test
	void testData() throws AppuntamentoException, TimeException {
		Agenda test = new Agenda();
		Appuntamento x = new Appuntamento("Marco", "05/09/2021", "15:00", "Milano", 50);
		test.aggiungi(x);
		x = new Appuntamento("Marco", "01/09/2021", "15:00", "Pavia", 50);
		test.aggiungi(x);
		x = new Appuntamento("Pietro", "10/09/2021", "15:00", "Verreto", 50);
		test.aggiungi(x);
		x = new Appuntamento("Marco", "04/09/2021", "15:00", "Lungavilla", 50);
		test.aggiungi(x);
		x = new Appuntamento("Luca", "05/09/2021", "16:00", "Oriolo", 30);
		test.aggiungi(x);
		x = new Appuntamento("Paolo", "05/09/2021", "10:00", "Torremenapace", 10);
		test.aggiungi(x);
		LocalDate check = LocalDate.of(2021, 9, 5);
		assertEquals(test.ricercaData(check).size(), 3);
	}

	@Test
	void testFile() throws AppuntamentoException, TimeException, IOException {
		Agenda test = new Agenda();
		Agenda test2 = new Agenda();
		Appuntamento x = new Appuntamento("Marco", "05/09/2021", "15:00", "Milano", 50);
		test.aggiungi(x);
		x = new Appuntamento("Marco", "01/09/2021", "15:00", "Pavia", 50);
		test.aggiungi(x);
		x = new Appuntamento("Pietro", "10/09/2021", "15:00", "Verreto", 50);
		test.aggiungi(x);
		x = new Appuntamento("Marco", "04/09/2021", "15:00", "Lungavilla", 50);
		test.aggiungi(x);
		x = new Appuntamento("Luca", "05/09/2021", "16:00", "Oriolo", 30);
		test.aggiungi(x);
		x = new Appuntamento("Paolo", "05/09/2021", "10:00", "Torremenapace", 10);
		test.aggiungi(x);
		test.scriviAgenda("file.txt");
		test2.leggiAgenda("file.txt");
		assertEquals(test.dim(), test2.dim());
	}
}
