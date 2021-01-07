package keittokirja.test;
// Generated by ComTest BEGIN
import keittokirja.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.24 09:44:57 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class KeittokirjaTest {



  // Generated by ComTest BEGIN
  /** testAnnaAineet89 */
  @Test
  public void testAnnaAineet89() {    // Keittokirja: 89
    Keittokirja keittokirja = new Keittokirja(); 
    Liima liima1 = new Liima(1, 0, "500g"); 
    Liima liima2 = new Liima(1, 1, "400g"); 
    Liima liima3 = new Liima(2, 2, "100g"); 
    Aine aine1 = new Aine("peruna"); 
    Aine aine2 = new Aine("pottu"); 
    Aine aine3 = new Aine("perunia"); 
    liima1.rekisteroi(); liima2.rekisteroi(); liima3.rekisteroi(); 
    aine1.rekisteroi(); aine2.rekisteroi(); aine3.rekisteroi(); 
    try {
    keittokirja.lisaaLiima(liima1); 
    keittokirja.lisaaLiima(liima2); 
    keittokirja.lisaaLiima(liima3); 
    keittokirja.lisaa(aine1); 
    keittokirja.lisaa(aine2); 
    keittokirja.lisaa(aine3); 
    } catch (SailoException e) {
    System.err.println(e.getMessage()); 
    }
    List<Aine> lista = new ArrayList<Aine>(); 
    lista = keittokirja.annaAineet(1); 
    assertEquals("From: Keittokirja line: 114", 2, lista.size()); 
    List<Aine> lista2 = new ArrayList<Aine>(); 
    lista2 = keittokirja.annaAineet(2); 
    assertEquals("From: Keittokirja line: 118", 1, lista2.size()); 
  } // Generated by ComTest END
}