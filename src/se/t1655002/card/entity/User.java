package se.t1655002.card.entity;

import java.util.ArrayList;
import java.util.List;

import se.t1655002.card.game.NapoleonGame;
import se.t1655002.card.util.Keyboard;

public class User extends Player {
    
    public User(String name) {
        super();
        this.name = name;
    }
    
    @Override
    public boolean runForNapoleon(NapoleonGame game) {
        
        int declaredNum = game.getDeclaredNum();
        int trumpSuit = game.getTrumpSuit();
        Player napoleon = game.getNapoleon();
        
        if (napoleon == null) {
            System.out.println("現時点で立候補者はいません.");
        } else {
            System.out.println("現在立候補しているのは" + napoleon.getName() + "さんで、切り札を" + Suit.toString(trumpSuit) + "とし、"
                    + declaredNum + "枚のアナーカードを取ると宣言しています.");
        }
        
        while (true) {
            String question = "立候補しますか?";
            List<String> commands = new ArrayList<>();
            commands.add("立候補する");
            commands.add("立候補しない");
            
            if (Keyboard.inputCommand(question, commands) == 0) {
                question = "切り札とするスートを入力してください.";
                commands.clear();
                commands.add("スペード");
                commands.add("ダイヤ");
                commands.add("ハート");
                commands.add("クラブ");
                int declaringTrumpSuit = Keyboard.inputCommand(question, commands);
                
                int declarableNum;
                if (trumpSuit == 4) {   // 初期値
                    declarableNum = 13;
                } else {
                    declarableNum = (Suit.getSuitStrength(declaringTrumpSuit) < Suit.getSuitStrength(trumpSuit)) ? declaredNum : declaredNum + 1;
                }
                
                
                System.out.println("枚数を入力してください.(" + declarableNum + "枚以上.0でキャンセル)");
                int declaringNum = Keyboard.inputNumber();
                if (declaringNum == 0) {
                    continue;
                } else if (declaringNum >= declarableNum && declaringNum <= 20) {
                    game.setDeclaredNum(declaringNum);
                    game.setTrumpSuit(declaringTrumpSuit);
                    game.setNapoleon(this);
                    return true;
                } else {
                    System.out.println("正しい枚数を入力してください.");
                    continue;
                }
            } else {
                return false;
            }
        }
        
    }
    
}
