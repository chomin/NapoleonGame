package se.t1655002.card.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author t1655002
 *  Cardのデッキクラス。
 *  @see se.t1655002.card.entity.Card
 */
public class CardDeck {

    private ArrayList<Card> cards = new ArrayList<>();

    /**
     * 生成した瞬間には「空デッキ（カードが一枚もない状態）」である
     */
    public CardDeck() {

    }

    /**
     * 自らをフルデッキにする(５２枚にする)
     */
    public void createFullDeck() {
        this.clear();
        
        for (int suit = 0; suit < 4; suit++) {
            for (int number = 1; number < 14; number++) {
                cards.add(new Card(suit, number));
            }
        }
        
    }

    /**
     * 自らを空デッキにする
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Fisher–Yatesシャッフルアルゴルズムにより並べ替える。
     */
    public void shuffle() {
        Random rnd = new Random();
        
        for(int i = 0; i < cards.size(); i++) {
            int j = rnd.nextInt(cards.size()-1);
            Card t = cards.get(i);
            
            cards.set(i, cards.get(j));
            cards.set(j, t);
        }
    }

    /**
     * デッキの一番最後に，任意のカードcardを追加する
     * @param card
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * デッキの i 番目に，任意のカードcardを追加する
     * @param i番目
     * @param card
     */
    public void addCard(int i, Card card) {
        cards.add(i-1, card);
    }

    /**
     * デッキの一番上の（１番目の）カードを取る
     * @return デッキの一番上の（１番目の）カード
     */
    public Card takeCard() {
        return cards.remove(0);
    }

    /**
     * デッキの i 番目から，カードを抜き取る
     * @param i(>0)番目
     * @return  とったカード
     */
    public Card takeCard(int i) {
        return cards.remove(i-1);
    }
    
    /**
     * デッキのi番目にあるカードを見る
     * @param i>0
     * @return デッキのi番目にあるカード
     */
    public Card seeCard(int i) {
        return cards.get(i-1);
    }
    
    /**
     * 絵柄suitと番号numberを与えて，そのカードがデッキの何番目にあるかを調べる
     * @param suit
     * @param number
     * @return そのカードがデッキの何番目にあるか.ない場合は0.
     */
    public int searchCard(int suit, int number) {
        
        int i=0;
        for (Card card: cards) {
            if (card.getSuit() == suit && card.getNumber() == number) { return i+1; }
            i++;
        }
        return 0;
        
    }
    
    /**
     * 現在のデッキが空かどうか，判定する
     * @return 現在のデッキが空かどうか
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
    
    /**
     * 現在デッキにあるカード枚数を返す
     * @return 現在デッキにあるカード枚数
     */
    public int size() {
        return cards.size();
    }
    
    /**
     * 現在のすべてのカードを画面に表示する
     */
    public  void showAllCards() {
        System.out.println("------------現在の山を表示します．-----------");
        int i=1;
        for (Card card: cards) {
            System.out.println(i + "番目のカード：" + card.toString());
            i++;
        }
        System.out.println("------------ここまで-----------");
    }
    
    /**
     * 現在デッキにある全てのカードを返す
     * @return 現在デッキにある全てのカード
     */
    public List<Card> getAllCards() {
        return cards;
    }
}
