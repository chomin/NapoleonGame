package se.t1655002.card.entity;

import se.t1655002.card.game.NapoleonGame;
import se.t1655002.card.util.Suit;

/**
 * @author t1655002 トランプのカードのクラス。スートと数を持つ。
 */
public class Card {

    /**
     * 0（スペード）, 1（ダイヤ）, 2（ハート）, 3（クラブ）,-1(ジョーカー) 立候補のときは、0>2>1>3の順で強い
     */
    private int suit;
    /**
     * 1~13, 0(ジョーカー)
     */
    private int number;

    public Card(int suit, int number) {
        super();
        this.suit = suit;
        this.number = number;
    }

    public int getSuit() {
        return suit;
    }

    public int getNumber() {
        return number;
    }

    /**
     * カード情報を整数表現(インデクス)に変換する 整数表現とは，[[ スペード1の13まで, ダイヤ1の13まで, ハート1の13まで,
     * クラブ1の13まで]]を，この順番に，0から51までの通し番号をつけたものである． また，ジョーカーの整数表現は，-1である
     * 
     * @return 整数表現
     */
    public int toIndex() {
        if (suit > -1) {
            return suit * 13 + number - 1;
        } else {// ジョーカー
            return -1;
        }
    }

    /**
     * 絵柄文字列と数字文字列を連接したもの
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String suitStr, numberStr;

        suitStr = Suit.toString(suit);

        switch (number) {
        case 0: // ジョーカー
            numberStr = "";
            break;
        case 1:
            numberStr = "A";
            break;
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
            numberStr = Integer.toString(number);
            break;
        case 11:
            numberStr = "J";
            break;
        case 12:
            numberStr = "Q";
            break;
        case 13:
            numberStr = "K";
            break;
        default:
            System.out.println("numberが不正です:" + number);
            numberStr = "";
            break;
        }

        return String.format(suitStr + numberStr);
    }

    /**
     * toString()で得られた文字列をそのまま表示する
     */
    public void show() {
        System.out.println(this);
    }

 
     
    /**
     * トリックにおける、このカードの強さを取得する(マイティ0表1裏2切り札3~15台札16~28他29)(重複による欠番あり)
     * @param game ナポレオンゲーム
     * @return このカードの強さ
     */
    public int getTrickStrength(NapoleonGame game) {
        int trumpSuit = game.getTrumpSuit();
        int trickSuit = game.getTrickSuit();

        if (suit == 0 && number == 1) {
            return 0;
        } else if (suit == trumpSuit && number == 11) {
            return 1;
        } else if (suit == Suit.getReverseSuit(trumpSuit) && number == 11) {
            return 2;
        } else if (suit == trumpSuit) {
            if (number == 1) {
                return 3;
            } else {
                return 3 + 14 - number;
            }
        } else if (suit == trickSuit) {
            if (number == 1) {
                return 16;
            } else {
                return 16 + 14 - number;
            }
        } else {
            return 29;
        }

    }

    /**
     * @return このカードがアナーカード(絵札)であるならtrue
     */
    public boolean isAnnor() {
        switch (number) {
        case 1:
        case 10:
        case 11:
        case 12:
        case 13:
            return true;
        default:
            return false;
        }
    }

    /**
     * @param suit　スート
     * @param number 番号
     * @return 対応するカードの整数表現を返す
     */
    public static int getIndex(int suit, int number) {
        return new Card(suit, number).toIndex();
    }

    /**
     * @param suit　スート
     * @param number　番号
     * @return 対応するカードの文字列表現を返す
     */
    public static String getString(int suit, int number) {
        return new Card(suit, number).toString();
    }

}
