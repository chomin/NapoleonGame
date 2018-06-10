package se.t1655002.card.entity.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import se.t1655002.card.entity.Card;
import se.t1655002.card.entity.Player;
import se.t1655002.card.entity.User;

public class PlayerTest extends TestCase {
    
    private Player pl = new User("テスト");
    private Card c1 = new Card(2,4);
    private Card c2 = new Card(1,1);
    
    
    
    protected void setUp() throws Exception {
        super.setUp();
        pl.addHand(c1);
        pl.addHand(c2);
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testSortHand() {
        
        pl.sortHand();
        assertEquals(c2.getNumber(), pl.getHand().get(0).getNumber());
        assertEquals(c1.getNumber(), pl.getHand().get(1).getNumber());
    }
    
}
