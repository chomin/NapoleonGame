package se.t1655002.card.entity;

import se.t1655002.card.game.NapoleonGame;
import se.t1655002.card.util.Suit;

public class CPU extends Player {
    
    /**
     * スート別の枚数(ジョーカーを除く)
     */
    private int[] suitNumbers = { 0, 0, 0, 0 }; 
    
    /**
     * コンストラクタ
     * @param name 名前
     */
    public CPU(String name) {
        super();
        this.name = name;
    }
    
    /**
     * 手札の枚数をスート別にカウントする
     */
    private void countSuitNumbers() {
        for (Card card : hand) {
            
            switch (card.getSuit()) {
            case 0:
                suitNumbers[0]++;
                break;
                
            case 1:
                suitNumbers[1]++;
                break;
                
            case 2:
                suitNumbers[2]++;
                break;
                
            case 3:
                suitNumbers[3]++;
                break;
                
            default: // ジョーカー
                break;
            }
        }
    }
    
    /* (非 Javadoc)
     * @see se.t1655002.card.entity.Player#runForNapoleon(se.t1655002.card.game.NapoleonGame)
     */
    @Override
    public boolean runForNapoleon(NapoleonGame game) {
        
        int declaredNum = game.getDeclaredNum();
        int trumpSuit = game.getTrumpSuit();
        
        if (game.getNapoleon() == this) {
            willRun = true;
            return true;
        }
        
        boolean[] hasRoleCards = { false, false, false, false }; // 役札(正、裏J、マイティ)を持つか
        for (int i=0; i<suitNumbers.length; i++) {
            suitNumbers[i] = 0;
        }
        
        for (Card card : hand) {
            
            switch (card.getSuit()) {
            case 0:
                suitNumbers[0]++;
                if (card.getNumber() == 11) {
                    hasRoleCards[0] = true;
                    hasRoleCards[3] = true;
                } else if (card.getNumber() == 1) { // マイティ
                    for (int i = 0; i < hasRoleCards.length; i++) {
                        hasRoleCards[i] = true;
                    }
                }
                break;
            case 1:
                suitNumbers[1]++;
                if (card.getNumber() == 11) {
                    hasRoleCards[1] = true;
                    hasRoleCards[2] = true;
                }
                break;
            case 2:
                suitNumbers[2]++;
                if (card.getNumber() == 11) {
                    hasRoleCards[1] = true;
                    hasRoleCards[2] = true;
                }
                break;
            case 3:
                suitNumbers[3]++;
                if (card.getNumber() == 11) {
                    hasRoleCards[0] = true;
                    hasRoleCards[3] = true;
                }
                break;
            default: // ジョーカー
                break;
            }
        }
        
        // 役札を持っているスートのうち、枚数が最大のもので、+7枚で立候補できるかを確認
        int declaringTrumpSuit = -1;
        int maxSuitNum = 0;
        for (int i = 0; i < 4; i++) { // iは強さの逆.強いものが残るように、弱い順に探索.
            if (!hasRoleCards[i]) {
                continue;
            }
            
            int strength = 3 - i;
            int suit = Suit.getSuit(strength);
            
            if (maxSuitNum <= suitNumbers[suit]) {
                maxSuitNum = suitNumbers[suit];
                declaringTrumpSuit = suit;
            }
        }
        
        if (declaringTrumpSuit == -1) {
            willRun = false;
            System.out.println(name + ": 立候補しません.");
            return false;
        }
        
        int declarableNum;
        if (trumpSuit == 4) { // 初期値
            declarableNum = 13;
        } else {
            declarableNum = (Suit.getSuitStrength(declaringTrumpSuit) < Suit.getSuitStrength(trumpSuit)) ? declaredNum
                    : declaredNum + 1;
        }
        
        if (declarableNum <= maxSuitNum + 9 && declarableNum <= 20) {
//            System.out.println(suitNumbers[0] + " " + suitNumbers[1] + " " + suitNumbers[2] + " " + suitNumbers[3] );
            game.setDeclaredNum(declarableNum);
            game.setTrumpSuit(declaringTrumpSuit);
            game.setNapoleon(this);
            willRun = true;
            System.out.println(name + ": " + Suit.toString(declaringTrumpSuit) + "で" + declarableNum + "枚");
            return true;
        } else {
            willRun = false;
            System.out.println(name + ": 立候補しません.");
            return false;
        }
        
    }
    
    /* (非 Javadoc)
     * @see se.t1655002.card.entity.Player#chooseAdjutantAndChangeCards(se.t1655002.card.game.NapoleonGame)
     */
    @Override
    public void chooseAdjutantAndChangeCards(NapoleonGame game) {
        
        game.setAdjutantCard(new Card(0, 1));
        
        for (Card card : game.getFirstTableCards()) {
            game.getNapoleon().addHand(card);
        }
        
        countSuitNumbers();
        
        int minSuitNumber = suitNumbers[0];
        int minSuit = 0;
        for (int i = 1; i < suitNumbers.length; i++) {
            if (suitNumbers[i] < minSuitNumber && game.getTrumpSuit() != i) {
                minSuitNumber = suitNumbers[i];
                minSuit = i;
            }
        }
        
        sortHand();
        removeHand: for (int i = 0; i < 3; i++) {
            for (Card card : hand) {
                if (card.getSuit() == minSuit) {
                    hand.remove(card);
                    continue removeHand;
                }
            }
            hand.remove(0); // 最小数のスートのカードがなければ最初のを捨てる
        }
        
        // TODO 自動生成されたメソッド・スタブ
        
    }
    
    /* (非 Javadoc)
     * @see se.t1655002.card.entity.Player#playACard(se.t1655002.card.game.NapoleonGame)
     */
    @Override
    public void playACard(NapoleonGame game) {
        
        
        for (Card card : hand) {
            if (game.getParent() == this || game.getTrickSuit() == card.getSuit()) { //切り札＝＝台札のとき
                if (card.getSuit() == game.getTrumpSuit()) { // できるだけ切り札を出す
                    game.getTablecards().put(this, card);

                    if (card.getSuit() == game.getAdjutantCard().getSuit()
                            && card.getNumber() == game.getAdjutantCard().getNumber()) { // 副官発覚
                        game.setAdjutant(this);
                        game.setAdjutantAppeared(true);
                    }
                    hand.remove(card);
                    return;
                }
            }
        }
        
        for (Card card : hand) {
            if (game.getParent() == this || card.getSuit() == game.getTrickSuit() || card.getSuit() == -1) { // 台札!=切り札のときは台札を出す
                game.getTablecards().put(this, card);

                if (card.getSuit() == game.getAdjutantCard().getSuit()
                        && card.getNumber() == game.getAdjutantCard().getNumber()) { // 副官発覚
                    game.setAdjutant(this);
                    game.setAdjutantAppeared(true);
                }
                hand.remove(card);
                return;
            }
        }
        
        for (Card card : hand) {
                if (card.getSuit() == game.getTrumpSuit()) {    // 台札がなければできるだけ切り札を出す 
                    game.getTablecards().put(this, card);

                    if (card.getSuit() == game.getAdjutantCard().getSuit()
                            && card.getNumber() == game.getAdjutantCard().getNumber()) {
                        game.setAdjutant(this);
                        game.setAdjutantAppeared(true);
                    }
                    hand.remove(card);
                    return;
                }
            
        }
        
        for (Card card : hand) {
            if (card.getSuit() == game.getTrumpSuit()) {
                game.getTablecards().put(this, card);
                
                if (card.getSuit() == game.getAdjutantCard().getSuit() && card.getNumber() == game.getAdjutantCard().getNumber()) {
                    game.setAdjutant(this);
                    game.setAdjutantAppeared(true);
                }
                hand.remove(card);
                return;
            }
        }
        
        // 台札も切り札もなければ適当に出す
        game.getTablecards().put(this, hand.get(0));
        if (hand.get(0).getSuit() == game.getAdjutantCard().getSuit() && hand.get(0).getNumber() == game.getAdjutantCard().getNumber()) {
            game.setAdjutant(this);
            game.setAdjutantAppeared(true);
        }
        hand.remove(0);
        
        
    }
    
}
