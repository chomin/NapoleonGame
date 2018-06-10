package se.t1655002.card.entity;


import se.t1655002.card.game.NapoleonGame;

public class CPU extends Player {
    
    public CPU(String name) {
        super();
        this.name = name;
    }
    
    @Override
    public boolean runForNapoleon(NapoleonGame game) {
        
        int declaredNum = game.getDeclaredNum();
        int trumpSuit = game.getTrumpSuit();
        
        int[] suitNumbers = { 0, 0, 0, 0 }; // スート別の枚数(ジョーカーを除く)
        boolean[] hasRoleCards = { false, false, false, false }; // 役札(正、裏J、マイティ)を持つか
        
        for (Card card : hand) {
            
            switch (card.getSuit()) {
            case 0:
                suitNumbers[0]++;
                if (card.getNumber() == 11) {
                    hasRoleCards[0] = true;
                    hasRoleCards[3] = true;
                } else if (card.getNumber() == 1) { // マイティ
                   for(int i=0; i<hasRoleCards.length; i++) {
                       hasRoleCards[i] = true;
                   }
                }
            case 1:
                suitNumbers[1]++;
                if (card.getNumber() == 11) {
                    hasRoleCards[1] = true;
                    hasRoleCards[2] = true;
                }
            case 2:
                suitNumbers[2]++;
                if (card.getNumber() == 11) {
                    hasRoleCards[1] = true;
                    hasRoleCards[2] = true;
                }
            case 3:
                suitNumbers[3]++;
                if (card.getNumber() == 11) {
                    hasRoleCards[0] = true;
                    hasRoleCards[3] = true;
                }
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
            
            int strength = 3-i;
            int suit = Suit.getSuit(strength);
            
            if (maxSuitNum <= suitNumbers[suit]) {
                maxSuitNum = suitNumbers[suit];
                declaringTrumpSuit = suit;
            }
        }
        
        if (declaringTrumpSuit == -1) { return false; }
        
        int declarableNum = (Suit.getSuitStrength(declaringTrumpSuit) < Suit.getSuitStrength(trumpSuit)) ? declaredNum : declaredNum + 1;
        
        if (declarableNum <= maxSuitNum+7 && declarableNum <= 20) {
            game.setDeclaredNum(declarableNum);
            game.setTrumpSuit(declaringTrumpSuit);
            game.setNapoleon(this);
            return true;
        } else {
            return false;
        }
        
    }
    
}