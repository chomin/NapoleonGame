package se.t1655002.card.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.t1655002.card.entity.Card;
import se.t1655002.card.entity.CardDeck;
import se.t1655002.card.entity.Player;
import se.t1655002.card.entity.Suit;
import se.t1655002.card.entity.User;
import se.t1655002.card.util.Keyboard;

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
    private int trumpSuit = 4;      // ナポレオンが宣言したスート
    private int declaredNum = 13;   // ナポレオンが宣言した枚数
    private Player napoleon;
    private Player adjutant;    // 副官
    private Card adjutantCard;

    public NapoleonGame(int numberOfGames, List<Player> players) {
        super();
        this.numberOfGames = numberOfGames;
        this.players = (ArrayList<Player>) players;
    }

    public void play() {
        game: for (int gameNum = 0; gameNum < numberOfGames; gameNum++) {
            System.out.println((gameNum + 1) + "回戦を始めます。");

            // 配る
            deck.createFullDeck();
            deck.addCard(new Card(-1, 0)); // ジョーカー
            deck.shuffle();

            for (Player player : players) {
                player.setHand(new ArrayList<Card>());
                for (int j = 0; j < 10; j++) {
                    player.addHand(deck.takeCard());
                }
            }
            // 真ん中に3枚置く
            firstTableCards.clear();
            for (int j = 0; j < 3; j++) {
                firstTableCards.add(deck.takeCard());
            }

            players.get(0).sortHand();
            players.get(0).showHand();

            // ナポレオンを決める
           chooseNap: while (true) {
                int candidatesCount = 0;
                for (Player player : players) {
                    if (player.runForNapoleon(this)) {
                        candidatesCount++;
                    }
                }
                if (candidatesCount == 0) {
                    System.out.println("立候補者がいないため配り直します。");
                    gameNum--;
                    continue game; // 配り直す
                } else if (candidatesCount == 1) {
                    break chooseNap;
                }
                System.out.println("立候補者が複数人いるため競りを続けます.");
            }
            
            System.out.println("ナポレオンは" + napoleon.getName() + "さんに決まりました.");
            
            // 副官を決める。さらに、カードを引き、選んで捨てる
            if (napoleon instanceof User){
                User us = (User) napoleon;
                
                String question = "副官を指名してください.";
                List<String> commands = new ArrayList<>();
                commands.add("オールマイティ");
                commands.add("正ジャック");
                commands.add("裏ジャック");
                commands.add("その他");
                switch(Keyboard.inputCommand(question, commands)){
                case 0: 
                    adjutantCard = new Card(0, 1);
                    break;
                case 1: 
                    adjutantCard = new Card(trumpSuit, 11);
                    break;
                case 2: 
                    adjutantCard = new Card(Suit.getReverseSuit(trumpSuit), 11);
                    break;
                case 3:
                    System.out.println("副官のカードを直接入力していただきます.");
                    adjutantCard = Keyboard.inputCard();
                    break;
                }
                
                System.out.println("中央のカードを引きます.");
                for(Card card: firstTableCards) {
                    us.addHand(card);
                }
                us.sortHand();
                us.showHand();
                for(int i = 0; i < 3; i++) {
                    question = "捨てるカードを選んでください(" + (i+1) + "枚目)";
                    commands.clear();
                    for (Card card: us.getHand()) {
                        commands.add(card.toString());
                    }
                    us.getHand().remove(Keyboard.inputCommand(question, commands));
                }
                
            } else {    // cpu
                
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
