package se.t1655002.card.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        if (napoleon == this) {
            return true;
        }

        if (napoleon == null) {
            System.out.println("現時点で立候補者はいません.");
        } else {
            System.out.println("現在の候補者は" + napoleon.getName() + "さんで、切り札を" + Suit.toString(trumpSuit) + "とし、"
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
                if (trumpSuit == 4) { // 初期値
                    declarableNum = 13;
                } else {
                    declarableNum = (Suit.getSuitStrength(declaringTrumpSuit) < Suit.getSuitStrength(trumpSuit))
                            ? declaredNum : declaredNum + 1;
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
                willRun = false;
                return false;
            }
        }

    }

    @Override
    public void chooseAdjutantAndChangeCards(NapoleonGame game) {

        User us = (User) game.getNapoleon();

        String question = "副官を指名してください.";
        List<String> commands = new ArrayList<>();
        commands.add("オールマイティ");
        commands.add("正ジャック");
        commands.add("裏ジャック");
        commands.add("その他");
        switch (Keyboard.inputCommand(question, commands)) {
        case 0:
            game.setAdjutantCard(new Card(0, 1));
            break;
        case 1:
            game.setAdjutantCard(new Card(game.getTrumpSuit(), 11));
            break;
        case 2:
            game.setAdjutantCard(new Card(Suit.getReverseSuit(game.getTrumpSuit()), 11));
            break;
        case 3:
            System.out.println("副官のカードを直接入力していただきます.");
            game.setAdjutantCard(Keyboard.inputCard());
            break;
        }

        System.out.println("中央のカードを引きます.");
        for (Card card : game.getFirstTableCards()) {
            us.addHand(card);
        }
        us.sortHand();
        us.showHand();
        for (int i = 0; i < 3; i++) {
            question = "捨てるカードを選んでください(" + (i + 1) + "枚目)";
            commands.clear();
            for (Card card : us.getHand()) {
                commands.add(card.toString());
            }
            us.getHand().remove(Keyboard.inputCommand(question, commands));
        }

    }

    @Override
    public void playACard(NapoleonGame game) {

        game.showTable();
        showHand();
        String question = "プレイするカードを選んでください";
        List<String> commands = new ArrayList<>();
        List<Integer> handIndexTable = new ArrayList<>(); // コマンド（このリストのindex）と手札のindexを対応させる.
        for (int i=0; i<hand.size(); i++) {
            Card card = hand.get(i);
            if (game.getParent() == this || game.getTrickSuit() == card.getSuit() || card.getSuit() == -1) {
                commands.add(card.toString());
                handIndexTable.add(i);
            }
        }
        if (commands.isEmpty()) { // 台札がない場合
            for (int i=0; i<hand.size(); i++) {
                Card card = hand.get(i);
                commands.add(card.toString());
                handIndexTable.add(i);
            }
        }

        int index = handIndexTable.get(Keyboard.inputCommand(question, commands));
        if (hand.get(index).getSuit() == game.getAdjutantCard().getSuit()
                && hand.get(index).getNumber() == game.getAdjutantCard().getNumber()) {
            game.setAdjutant(this);
            game.setAdjutantAppeared(true);
        }
        game.getTablecards().put(this, hand.get(index));
        hand.remove(index);

    }

}
