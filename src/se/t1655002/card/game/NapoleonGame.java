package se.t1655002.card.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.t1655002.card.entity.Card;
import se.t1655002.card.entity.CardDeck;
import se.t1655002.card.entity.Player;



public class NapoleonGame {
    
    private int numberOfGames;
    private CardDeck deck = new CardDeck();
    /**
     * カード及び出した人の情報
     */
    private HashMap<Player, Card> tablecards;
    private Player parent;
    private ArrayList<Player> players;
    private ArrayList<Card> firstTableCards = new ArrayList<>();
    private int trumpSuit = 4;
    private int declaredNum = 13;
    private Player napoleon;
    private Player adjutant;
    
    
    public NapoleonGame(int numberOfGames, List<Player> players) {
        super();
        this.numberOfGames = numberOfGames;
        this.players = (ArrayList<Player>) players;
    }
    
   
    
    public void play() {
        for (int i=0; i<numberOfGames; i++) {
            System.out.println((i+1) + "回戦を始めます。");
            
            // 配る
            deck.createFullDeck();
            deck.addCard(new Card(-1, 0)); // ジョーカー
            deck.shuffle();
            
            for (Player player: players) {
                player.setHand(new ArrayList<Card>());
                for (int j=0; j<10; j++) {
                   player.addHand(deck.takeCard());
                }
            }
            // 真ん中に3枚置く
            firstTableCards.clear();
            for (int j=0; j<3; j++) {
                firstTableCards.add(deck.takeCard());
             }
            
            players.get(0).sortHand();
            players.get(0).showHand();
            
            // ナポレオンを決める
            for(Player player: players) {
                player.runForNapoleon(this);
            }
            
            
            
        }
    }
    
    
    public int getNumberOfGames() {
        return numberOfGames;
    }
    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }
    public CardDeck getDeck() {
        return deck;
    }
    public void setDeck(CardDeck deck) {
        this.deck = deck;
    }
    public HashMap<Player, Card> getTablecards() {
        return tablecards;
    }
    public void setTablecards(HashMap<Player, Card> tablecards) {
        this.tablecards = tablecards;
    }
    public Player getParent() {
        return parent;
    }
    public void setParent(Player parent) {
        this.parent = parent;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = (ArrayList<Player>) players;
    }

    public ArrayList<Card> getFirstTableCards() {
        return firstTableCards;
    }

    public void setFirstTableCards(ArrayList<Card> firstTableCards) {
        this.firstTableCards = firstTableCards;
    }

    public int getTrumpSuit() {
        return trumpSuit;
    }

    public void setTrumpSuit(int trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    public int getDeclaredNum() {
        return declaredNum;
    }

    public void setDeclaredNum(int declaredNum) {
        this.declaredNum = declaredNum;
    }

    public Player getNapoleon() {
        return napoleon;
    }

    public void setNapoleon(Player napoleon) {
        this.napoleon = napoleon;
    }

    public Player getAdjutant() {
        return adjutant;
    }

    public void setAdjutant(Player adjutant) {
        this.adjutant = adjutant;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    
    
}
