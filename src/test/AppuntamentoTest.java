package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import codice.Appuntamento;

class AppuntamentoTest {

	@Test
	void testCostruttoreCompleto() {
		Appuntamento test = new Appuntamento("Gennaro", "01/05/2002", "15:03", "Napoli", 30);
		assertEquals(test.getData().toString(), "2002-05-01");
		assertEquals(test.getOra(), 15);
		assertEquals(test.getMinuti(), 3);
		assertEquals(test.getNome(), "Gennaro");
		assertEquals(test.getLuogo(), "Napoli");
		assertEquals(test.getDurata(), 30);
	}
	
	void testCostruttoreCompletoException() {
		DateTimeParseException msg = assertThrows(DateTimeParseException.class,
				() -> new Appuntamento("Ciruzzo", "15/14/2001", "06:06", "Roma", 0));
		assertEquals("Text '15/14/2001 06:06' could not be parsed:"
				+ " Invalid value for MonthOfYear (valid values 1 - 12): 14", msg.getMessage());
	}
 
	@Test
	void testCostruttoreMinimo() {
		Appuntamento test = new Appuntamento("Domenico", "03/06/2006", "23:11");
		assertEquals(test.getData().toString(), "2006-06-03");
		assertEquals(test.getOra(), 23);
		assertEquals(test.getMinuti(), 11);
		assertEquals(test.getNome(), "Domenico");
		assertEquals(test.getLuogo(), "");
		assertEquals(test.getDurata(), 0);
	}

	@Test
	void testCostruttoreConLuogo() {
		Appuntamento test = new Appuntamento("Domenico", "03/06/2006", "23:11", "Milano");
		assertEquals(test.getData().toString(), "2006-06-03");
		assertEquals(test.getOra(), 23);
		assertEquals(test.getMinuti(), 11);
		assertEquals(test.getNome(), "Domenico");
		assertEquals(test.getLuogo(), "Milano");
		assertEquals(test.getDurata(), 0);
	}

	@Test
	void testCostruttoreConDurata() {
		Appuntamento test = new Appuntamento("Domenico", "03/06/2006", "23:11", 30);
		assertEquals(test.getData().toString(), "2006-06-03");
		assertEquals(test.getOra(), 23);
		assertEquals(test.getMinuti(), 11);
		assertEquals(test.getNome(), "Domenico");
		assertEquals(test.getLuogo(), "");
		assertEquals(test.getDurata(), 30);
	}

	@Test
	void testEquals() {
		Appuntamento test = new Appuntamento("Ciro", "12/01/2012", "06:06");
		Appuntamento test2 = new Appuntamento("Ciro", "12/01/2012", "06:06");
		Appuntamento test3 = new Appuntamento("Ciro", "13/01/2012", "06:06");
		Object controllo = new Object();
		assertEquals(true, test.equals(test2));
		assertEquals(false, test.equals(controllo));
		assertEquals(false, test.equals(test3));
	}

	@Test
	void testToString() {
		Appuntamento test = new Appuntamento("Gennaro", "01/05/2002", "15:03", "Napoli", 30);
		assertEquals("Gennaro;01/05/2002;15:03;Napoli;30", test.toString());
	}

}
