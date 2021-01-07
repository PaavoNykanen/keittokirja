package keittokirja.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import keittokirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.24 09:45:02 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class LiimaTest {
    


  // Generated by ComTest BEGIN
  /** testRekisteroi51 */
  @Test
  public void testRekisteroi51() {    // Liima: 51
    Liima jliha = new Liima(1, 2, "500g"); 
    Liima maito = new Liima(2, 4, "500g"); 
    assertEquals("From: Liima line: 54", 4, jliha.rekisteroi());  //toString testissä seuraavaId asetetaan jo 4
    assertEquals("From: Liima line: 55", 5, maito.rekisteroi());  //eikä suostu testaamaan rekisteroi() ensin :(
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString120 */
  @Test
  public void testToString120() {    // Liima: 120
    Liima aine = new Liima(); 
    aine.parse("   3  |  2  |  54  | ripaus  "); 
    assertEquals("From: Liima line: 123", true, aine.toString().startsWith("3|2|54|"));  // on enemmäkin kuin 3 kenttää, siksi loppu |
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse141 */
  @Test
  public void testParse141() {    // Liima: 141
    Liima aine = new Liima(); 
    aine.parse("   3  |  2  |  54  | ripaus  "); 
    assertEquals("From: Liima line: 144", 3, aine.getLiimaId()); 
    assertEquals("From: Liima line: 145", true, aine.toString().startsWith("3|2|54|"));  // on enemmäkin kuin 3 kenttää, siksi loppu |
    aine.rekisteroi(); 
    int n = aine.getLiimaId(); 
    aine.parse(""+(n+20));  // Otetaan merkkijonosta vain tunnusnumero
    aine.rekisteroi();  // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
    assertEquals("From: Liima line: 151", n+20+1, aine.getLiimaId()); 
  } // Generated by ComTest END
}