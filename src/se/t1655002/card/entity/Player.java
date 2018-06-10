package se.t1655002.card.entity;

import java.util.ArrayList;
import java.util.Comparator;

import se.t1655002.card.game.NapoleonGame;

public abstract class Player {

    protected String name;
    protected int point = 0;
    protected ArrayList<Card> hand = new ArrayList<>();
    
    public void addHand(Card card) {
        hand.add(card);
    }
    
    public void sortHand() {
        Comparator<Card> c = Comparator.comparing(Card::toIndex);
        hand.sort(c);
    }
    
    public void showHand() {
        for(Card card: hand) {
            System.out.print(card + " ");
        }
        System.out.println();
    }
    
    
    
    public abstract boolean runForNapoleon(NapoleonGame game);
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public ArrayList<Card> getHand() {
        return hand;
    }
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    
    
    
}
