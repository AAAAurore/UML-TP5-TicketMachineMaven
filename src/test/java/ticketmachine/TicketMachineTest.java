package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix.");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
                // Les montants ont été correctement additionnés  
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour.");              
	}

	@Test
	// S3 : On n’imprime pas le ticket si le montant inséré est insuffisant
	public void nImprimePasSiPasAssezArgent() {
		// Given : J'ai une machine neuve
		// When : Je n'ai pas assez d'argent
		machine.insertMoney(PRICE);
		// Then : On ne doit pas imprimer le ticket
		assertFalse(machine.printTicket(), "Il n'y a pas assez d'argent, donc on ne doit pas imprimer le ticket.");
	}

	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	public void imprimeSiMontantInsereSuffisant() {
		// Given : J'ai une machine neuve
		// When : J'ai assez d'argent
		machine.insertMoney(PRICE);
		// Then : On doit imprimer le ticket
		assertTrue(machine.printTicket(), "Il y a assez d'argent, donc on doit imprimer le ticket.");
	}

	@Test
	// S5 : Quand on imprime un ticket, la balance est décrémentée du prix du ticket
	public void imprimeQuandBalanceDecrementee() {
		// Given : J'ai une machine neuve
		// When : On imprime le ticket
		machine.insertMoney(PRICE);
		machine.printTicket();
		// Then : La balance est décrémentée du prix du ticket
		assertEquals(PRICE - PRICE, machine.getBalance(), "La balance n'est pas décrémentée du prix du ticket.");
	}

	@Test
	// S6 : Le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	public void montantCollecteMAJQuandImprime() {
		// Given : J'ai une machine neuve
		// When : On imprime le ticket
		machine.insertMoney(PRICE);
		machine.printTicket();
		// Then : Le montant collecté est mis à jour
		assertEquals(PRICE, machine.getTotal(), "Le montant collecté n'est pas mise à jour.");  
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	public void rendCorrectementMonnaie() {
		// Given : J'ai une machine neuve
		// When : J'ai mis trop d'argent
		machine.insertMoney(100);
		machine.printTicket();
		// Then : Il rend la monnaie
		assertEquals(100 - PRICE, machine.refund());
	}

	@Test
	// S8 : refund() remet la balance à zéro
	public void remetBalanceAZero() {
		// Given : J'ai une machine neuve
		// When : J'ai pris le ticket
		machine.insertMoney(PRICE);
		machine.printTicket();
		// Then : Il remet la balance à zéro
		machine.refund();
		assertEquals(0, machine.getBalance(), "La balance n'est pas remis à zéro."); 
	}

	@Test
	// S9 : On ne peut pas insérer un montant négatif
	public void nePeutPasInsererMontantNegatif() {
		assertThrows(IllegalArgumentException.class,
			() -> { machine.insertMoney(-PRICE); },
			"On ne peut pas insérer un montant négatif.");
	}

	@Test
	// S10 : On ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	public void nePeutPasCreerMachineQuiDelivreTicketDontPrixNegatif() {
		assertThrows(IllegalArgumentException.class,
			() -> { machine.insertMoney(-PRICE); },
			"On ne peut pas créer de machine qui délivre des tickets.");
	}
}
