package se.t1655002.card.entity.test;

import java.util.List;
import java.util.Random;

import junit.framework.TestCase;
import se.t1655002.card.entity.Card;
import se.t1655002.card.entity.CardDeck;

public class CardDeckTest extends TestCase {
    
    private CardDeck cd = new CardDeck();
    private Card card = new Card(1, 1);
    
    /**
     * 各テストメソッドの実行の前処理
     */
    protected void setUp() throws Exception {
        cd.createFullDeck();
    }
    
    /**
     * 各テストメソッドの実行の後処理
     */
    protected void tearDown() throws Exception {
        // 後処理は特に無し
    }
    
    public void testCreateFullDeck() {
        cd.createFullDeck();
        assertEquals(52, cd.size());
    }
    
    public void testClear() {
        cd.clear();
        assertEquals(0, cd.size());
    }
    
    // public void testShuffle() {
    // cd.createFullDeck();
    // cd.shuffle();
    //
    // System.out.println("シャッフルできてるか目視で確認してください");
    // cd.showAllCards(); // 目視で確認
    // }
    
    public void testAddCardCard() {
        cd.addCard(card);
        // cd.showAllCards();
        assertEquals(card, cd.getAllCards().get(cd.getAllCards().size() - 1));
        
    }
    
    public void testAddCardIntCard() {
        cd.addCard(3, card);
        assertEquals(card, cd.getAllCards().get(2));
    }
    
    public void testTakeCard() {
        List<Card> firstList = cd.getAllCards();
        int firstListSize = firstList.size();
        
        assertEquals(firstList.get(0), cd.takeCard());
        assertEquals(firstListSize - 1, cd.getAllCards().size());
    }
    
    public void testTakeCardInt() {
        cd.createFullDeck();
        List<Card> firstList = cd.getAllCards();
        int firstListSize = firstList.size();
        
        cd.createFullDeck();
        
        assertEquals(firstList.get(1), cd.takeCard(2));
        assertEquals(firstListSize - 1, cd.getAllCards().size());
    }
    
    public void testSeeCard() {
        cd.createFullDeck();
        List<Card> firstList = cd.getAllCards();
        
        assertEquals(firstList.get(1), cd.seeCard(2));
        assertEquals(firstList.size(), cd.getAllCards().size());
    }
    
    public void testSearchCard() {
        List<Card> firstList = cd.getAllCards();
        int rnd = new Random().nextInt(firstList.size());
        Card card2 = cd.seeCard(rnd);
        
        assertEquals(rnd, cd.searchCard(card2.getSuit(), card2.getNumber()));
        
        cd.takeCard(rnd);
        
        assertEquals(0, cd.searchCard(card2.getSuit(), card2.getNumber()));
    }
    
    public void testIsEmpty() {
        List<Card> firstList = cd.getAllCards();
        
        assertEquals(firstList.isEmpty(), cd.isEmpty());
        
        cd.clear();
        
        assertTrue(cd.isEmpty());
        
    }
    
    public void testSize() {
        List<Card> firstList = cd.getAllCards();
        
        assertEquals(firstList.size(), cd.size());
    }
    
    // public void testShowAllCards() {
    // System.out.println("デッキが表示できてるか目視で確認してください");
    // cd.showAllCards(); // 目視で確認
    // }
    //
    // public void testGetAllCards() {
    // fail("まだ実装されていません");
    // }
    
}
