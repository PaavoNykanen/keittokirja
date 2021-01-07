package keittokirja.test;
// Generated by ComTest BEGIN
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.*;
import keittokirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.24 09:45:37 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class ReseptitTest {



  // Generated by ComTest BEGIN
  /** testTallenna44 */
  @Test
  public void testTallenna44() {    // Reseptit: 44
    Reseptit reseptit1 = new Reseptit(); 
    Resepti pyttipannu1 = new Resepti(); 
    pyttipannu1.rekisteroi(); 
    pyttipannu1.asetaTiedot("Lasagne", "Paista jliha yms", "1h", "karri"); 
    try {
    reseptit1.lisaaResepti(pyttipannu1); 
    } catch (keittokirja.SailoException e) {
    e.printStackTrace(); 
    }
    Reseptit.setTiedostoSijainti("tiedostoTestit/reseptiTesti.dat"); 
    reseptit1.tallenna(); 
    reseptit1.lueTiedosto(); 
    assertEquals("From: Reseptit line: 57", 1, reseptit1.anna(0).getReseptinId());  //nämä testaa että vaikka loimme resepteja vain yhden, niin tallenna tallenti ja sen ja lueTiedosto 
    assertEquals("From: Reseptit line: 58", 1, reseptit1.anna(1).getReseptinId());  //loi uuden reseptin joka on siihen identtinen
    try {
    assertEquals("From: Reseptit line: 59", 1, reseptit1.anna(2).getReseptinId()); 
    fail("Reseptit: 59 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); } //tämä menee yli indexin koska vain kaksi reseptiä
    Reseptit.setLkm(0); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaaResepti118 
   * @throws SailoException when error
   */
  @Test
  public void testLisaaResepti118() throws SailoException {    // Reseptit: 118
    Reseptit.setLkm(0); 
    Reseptit reseptit = new Reseptit(); 
    Resepti pytti = new Resepti(), lasa = new Resepti(); 
    assertEquals("From: Reseptit line: 125", 0, reseptit.getLkm()); 
    reseptit.lisaaResepti(pytti); assertEquals("From: Reseptit line: 126", 1, reseptit.getLkm()); 
    reseptit.lisaaResepti(lasa); assertEquals("From: Reseptit line: 127", 2, reseptit.getLkm()); 
    reseptit.lisaaResepti(pytti); assertEquals("From: Reseptit line: 128", 3, reseptit.getLkm()); 
    reseptit.lisaaResepti(pytti); assertEquals("From: Reseptit line: 129", 4, reseptit.getLkm()); 
    reseptit.lisaaResepti(pytti); assertEquals("From: Reseptit line: 130", 5, reseptit.getLkm()); 
    Reseptit.setLkm(0); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoistaResepti212 
   * @throws SailoException when error
   */
  @Test
  public void testPoistaResepti212() throws SailoException {    // Reseptit: 212
    Reseptit.setLkm(0); 
    Reseptit reseptit = new Reseptit(); 
    Resepti pytti = new Resepti(), lasa = new Resepti(), keitto = new Resepti(); 
    assertEquals("From: Reseptit line: 219", 0, reseptit.getLkm()); 
    reseptit.lisaaResepti(pytti); assertEquals("From: Reseptit line: 220", 1, reseptit.getLkm()); 
    pytti.rekisteroi(); 
    reseptit.lisaaResepti(lasa); assertEquals("From: Reseptit line: 222", 2, reseptit.getLkm()); 
    lasa.rekisteroi(); 
    reseptit.lisaaResepti(keitto); assertEquals("From: Reseptit line: 224", 3, reseptit.getLkm()); 
    keitto.rekisteroi(); 
    assertEquals("From: Reseptit line: 226", 3, reseptit.getLkm()); 
    reseptit.poistaResepti(pytti); 
    assertEquals("From: Reseptit line: 228", 2, reseptit.getLkm()); 
    Reseptit.setLkm(0); 
  } // Generated by ComTest END
}