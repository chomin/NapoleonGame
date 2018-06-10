package se.t1655002.card.entity;

public class Suit {
    
    public static String toString(int suit) {
        switch (suit) {
        case 0:
            return "スペード";
        case 1:
            return "ダイヤ";
        case 2:
            return "ハート";
        case 3:
            return "クラブ";
        case -1:
            return "ジョーカー";
        default:
            System.out.println("suitが不正です:" + suit + " @Suit.toString");
            return "";
        }
    }
    
    /**立候補のときは、0>2>1>3の順で強い
     * @param suit
     * @return
     */
    public static int getSuitStrength(int suit) {
        switch(suit) {
        case 0: return 0;
        case 1: return 2;
        case 2: return 1;
        case 3: return 3;
        default:
            System.err.println("スートの値が不適切です@getSuitStrength(), suit = " + suit);
            System.exit(1);
            return -1;
        }
    }
    
    public static int getSuit(int strength) {
        switch(strength) {
        case 0: return 0;
        case 1: return 2;
        case 2: return 1;
        case 3: return 3;
        default:
            System.err.println("スートの強さの値が不適切です@getSuit()");
            System.exit(1);
            return -1;
        }
    }
    
    
}
