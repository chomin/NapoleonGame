package se.t1655002.card.test;

import java.awt.List;
import java.util.ArrayList;

import junit.framework.TestCase;
import se.t1655002.card.entity.Card;
import se.t1655002.card.util.Keyboard;

/**
 * @author NakaiKohei
 * Keyboardクラスのテスト
 */
public class KeyboardTest extends TestCase {
    
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
     * inputStringのテスト
     */
    public void testInputString() {
        System.out.println("inputStringを確認");
        System.out.println(Keyboard.inputString());
    }
    
    /**
     * inputNumberのテスト
     */
    public void testInputNumber() {
        System.out.println("inputNumberを確認");
        System.out.println(Keyboard.inputNumber());
    }
    
    /**
     * inputCommandのテスト
     */
    public void testInputCommand() {
        String q = "成功していますか?";
        ArrayList<String> c = new ArrayList<>();
        c.add("はい");
        System.out.println("inputCommandを確認");
        int com = Keyboard.inputCommand(q, c);
        System.out.println(com + c.get(com));
    }
    
    /**
     * inputCardのテスト
     */
    public void testInputCard() {
       
        System.out.println("inputCardを確認");
        Card c = Keyboard.inputCard();
        System.out.println(c);
    }
    
}
