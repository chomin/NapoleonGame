package se.t1655002.card.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.t1655002.card.entity.CPU;
import se.t1655002.card.entity.Card;
import se.t1655002.card.entity.CardDeck;
import se.t1655002.card.entity.Player;
import se.t1655002.card.entity.User;
import se.t1655002.card.util.Suit;

/**
 * @author NakaiKohei
 * ナポレオンゲームを管理するクラス
 */
public class NapoleonGame {

    /**
     * 対戦回数
     */
    private int numberOfGames;
    /**
     * 山札
     */
    private CardDeck deck = new CardDeck();
    /**
     * カード及び出した人の情報
     */
    private HashMap<Player, Card> tablecards = new HashMap<>();
    /**
     * 親
     */
    private Player parent;
    /**
     * 参加しているプレイヤー
     */
    private ArrayList<Player> players;
    /**
     * 最初に場に置かれる3枚のカード
     */
    private ArrayList<Card> firstTableCards = new ArrayList<>();
    /**
     * ナポレオンが宣言したスート(切り札)
     */
    private int trumpSuit = 4; 
    /**
     * ナポレオンが宣言した枚数
     */
    private int declaredNum = 13; 
    /**
     * ナポレオンとなったプレーヤー
     */
    private Player napoleon;
    /**
     * 副官となったプレーヤー
     */
    private Player adjutant; 
    /**
     * このカードを持っている人が副官となる
     */
    private Card adjutantCard;
    /**
     * 副官が発覚したか
     */
    private boolean isAdjutantAppeared = false;
    /**
     * 台札のスート
     */
    private int trickSuit; 

    /**
     * NapoleonGameのコンストラクタ
     * @param numberOfGames 対戦回数
     * @param players 参加するプレーヤーのリスト(5人とすること)
     */
    public NapoleonGame(int numberOfGames, List<Player> players) {
        super();
        this.numberOfGames = numberOfGames;
        this.players = (ArrayList<Player>) players;
    }

    /**
     * 実際にナポレオンで遊ぶ
     */
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
                    if (player.isWillRun()) {
                        if (player.runForNapoleon(this)) {
                            candidatesCount++;
                        }
                    }

                }
                if (candidatesCount == 0) {
                    System.out.println("立候補者がいないため配り直します。");
                    gameNum--;
                    tablecards.clear();
                    for (Player pl : players) {
                        pl.setWillRun(true);
                    }
                    continue game; // 配り直す
                } else if (candidatesCount == 1) {
                    break chooseNap;
                }
                System.out.println("立候補者が複数人いるため競りを続けます.");
            }

            System.out.println("ナポレオンは" + napoleon.getName() + "さんに決まりました.");

            // 副官を決める。さらに、カードを引き、選んで捨てる
            if (napoleon instanceof User) {
                User us = (User) napoleon;
                us.chooseAdjutantAndChangeCards(this);

            } else { // cpu
                CPU cp = (CPU) napoleon;
                cp.chooseAdjutantAndChangeCards(this);
            }

            System.out.println("トリックを開始します.");

            parent = napoleon;

            while (!players.get(0).getHand().isEmpty()) {
                int parentIndex = players.indexOf(parent);
                for (int i = parentIndex; i < players.size(); i++) {
                    players.get(i).playACard(this);
                    if (i == parentIndex) {
                        trickSuit = tablecards.get(parent).getSuit();
                    }
                }
                for (int i = 0; i < parentIndex; i++) {
                    players.get(i).playACard(this);
                }

                // 強さを判定(マイティ表裏切り札台札他)し、枚数を与える

                int annorCount = 0;// 絵札の枚数だけ加算
                int maxStrength = tablecards.get(parent).getTrickStrength(this);
                Player strongestPlayer = parent;
                for (Map.Entry<Player, Card> entry : tablecards.entrySet()) {
                    int strength = entry.getValue().getTrickStrength(this);
                    if (maxStrength > strength) {
                        maxStrength = strength;
                        strongestPlayer = entry.getKey();
                    }

                    if (entry.getValue().isAnnor()) {
                        annorCount++;
                    }
                }

                System.out.println();
                System.out.println("トリック結果を表示します.");
                showTable();
                strongestPlayer.setAnnorNum(strongestPlayer.getAnnorNum() + annorCount);
                parent = strongestPlayer;
                tablecards.clear();
                

                String strengthStr;
                switch (maxStrength) {
                case 0:
                    strengthStr = "オールマイティです.";
                    break;
                case 1:
                    strengthStr = "正ジャックです.";
                    break;
                case 2:
                    strengthStr = "裏ジャックです.";
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    strengthStr = "切り札です.";
                    break;
                default:
                    strengthStr = "台札です.";
                    break;
                }
                
                System.out.println(strengthStr);
                System.out.println(strongestPlayer.getName() + "さんが" + annorCount + "枚獲得しました.");
                
            }

            // 枚数判定とポイント精算
            if (!isAdjutantAppeared || adjutant == napoleon) { // 副官カードが捨てられたorナポレオンが所持している場合
                if (napoleon.getAnnorNum() >= declaredNum) {
                    napoleonWin();
                } else {
                    napoleonLose();
                }
            } else {
                if (napoleon.getAnnorNum() + adjutant.getAnnorNum() >= declaredNum) {
                    napoleonWin();
                } else {
                    napoleonLose();
                }
            }

            isAdjutantAppeared = false;
            adjutant = null;
            trumpSuit = 4; // ナポレオンが宣言したスート(切り札)
            declaredNum = 13; // ナポレオンが宣言した枚数
            napoleon = null;
            for (Player pl : players) {
                pl.setAnnorNum(0);
                pl.setWillRun(true);
            }

        }

        // 得点判定、及び勝敗の表示
        Comparator<Player> c = Comparator.comparing(Player::getPoint).reversed();
        players.sort(c);

        System.out.println();
        System.out.println("*****最終結果を表示します*****");
        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + "位: " + players.get(i).getName() + " " + players.get(i).getPoint() + "点");
        }
        System.out.println("********************");
    }

    /**
     * 切り札、（台札）と宣言枚数、副官となるカード、ナポレオンや副官、連合軍とともに場に出ているカードを表示
     */
    public void showTable() {

        System.out.println();
        System.out.println(Suit.toString(trumpSuit) + "で" + declaredNum + "枚、副官は" + adjutantCard.toString());
        System.out.println("=====場に出ているカード=====");
        for (Map.Entry<Player, Card> entry : getTablecards().entrySet()) {
            Player pl = entry.getKey();
            Card ca = entry.getValue();
            String printStr = entry.getKey().getName();

            if (pl == napoleon) {
                printStr += "(ナポレオン, " + pl.getAnnorNum() + "枚): " + ca.toString();
            } else if (isAdjutantAppeared) {
                if (pl == adjutant) {
                    printStr += "(副官, " + pl.getAnnorNum() + "枚): " + ca.toString();
                } else {
                    printStr += "(連合軍, " + pl.getAnnorNum() + "枚): " + ca.toString();
                }
            } else {
                printStr += "(" + pl.getAnnorNum() + "枚): " + ca.toString();
            }

            if (pl == parent) {
                printStr += "     ←親";
            }

            System.out.println(printStr);
        }
        System.out.println("====================");
    }

    /**
     * ナポレオンチームが勝った場合の得点処理をする
     */
    public void napoleonWin() {

        System.out.println();
        System.out.println("+++++ポイントの精算を行います+++++");
        System.out.println("名前 増減 総得点");
        for (Player pl : players) {
            if (pl == napoleon) {
                pl.setPoint(pl.getPoint() + 2);
                System.out.println(pl.getName() + " +2 " + pl.getPoint() + "    ←ナポレオン");
            } else if (pl == adjutant) {
                pl.setPoint(pl.getPoint() + 1);
                System.out.println(pl.getName() + " +1 " + pl.getPoint() + "    ←副官");
            } else {
                pl.setPoint(pl.getPoint() - 1);
                System.out.println(pl.getName() + " -1 " + pl.getPoint());
            }
        }

        System.out.println("++++++++++++++++++++");
    }

    /**
     * ナポレオンチームが負けた場合の得点処理をする
     */
    public void napoleonLose() {

        System.out.println();
        System.out.println("+++++ポイントの精算を行います+++++");
        System.out.println("名前 増減 総得点");
        
        for (Player pl : players) {
            if (pl == napoleon) {
                pl.setPoint(pl.getPoint() - 2);
                System.out.println(pl.getName() + " -2 " + pl.getPoint() + "    ←ナポレオン");
            } else if (pl == adjutant) {
                pl.setPoint(pl.getPoint() - 1);
                System.out.println(pl.getName() + " -1 " + pl.getPoint() + "    ←副官");
            } else {
                pl.setPoint(pl.getPoint() + 1);
                System.out.println(pl.getName() + " +1 " + pl.getPoint());
            }
        }

        System.out.println("++++++++++++++++++++");
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

    public Card getAdjutantCard() {
        return adjutantCard;
    }

    public void setAdjutantCard(Card adjutantCard) {
        this.adjutantCard = adjutantCard;
    }

    public boolean isAdjutantAppeared() {
        return isAdjutantAppeared;
    }

    public void setAdjutantAppeared(boolean isAdjutantAppeared) {
        this.isAdjutantAppeared = isAdjutantAppeared;
    }

    public int getTrickSuit() {
        return trickSuit;
    }

    public void setTrickSuit(int trickSuit) {
        this.trickSuit = trickSuit;
    }

}
