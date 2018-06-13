package se.t1655002.card.test;

import junit.framework.TestCase;
import se.t1655002.card.util.Suit;

/**
 * @author NakaiKohei
 * Suitクラスのテスト
 */
public class SuitTest extends TestCase {
    
    /**
     * スペード
     */
    int suit1 = 0;
    
    /* (非 Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    /* (非 Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /**
     * toStringのテスト
     */
    public void testToStringInt() {
        assertEquals("スペード", Suit.toString(suit1));
    }
    
    /**
     * getSuitStrengthのテスト
     */
    public void testGetSuitStrength() {
        assertEquals(0, Suit.getSuitStrength(suit1));
    }
    
    /**
     * getSuitのテスト
     */
    public void testGetSuit() {
        assertEquals(suit1, Suit.getSuit(Suit.getSuitStrength(suit1)));
    }
    
    /**
     * getReverseSuitのテスト
     */
    public void testGetReverseSuit() {
        assertEquals(3, Suit.getReverseSuit(suit1));
    }
    
}
