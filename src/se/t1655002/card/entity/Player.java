package se.t1655002.card.entity;

import java.util.ArrayList;
import java.util.Comparator;

import se.t1655002.card.game.NapoleonGame;

/**
 * @author NakaiKohei
 * プレーヤーに関する抽象クラス
 */
public abstract class Player {

    /**
     * 名前
     */
    protected String name;
    /**
     * 得点
     */
    protected int point = 0;
    /**
     * 手札
     */
    protected ArrayList<Card> hand = new ArrayList<>();
    /**
     * 取得したアナーカードの枚数
     */
    protected int annorNum = 0;
    /**
     * 立候補するか.(一度でも辞退したら決まるまではfalseになる)
     */
    protected boolean willRun = true;
    


    /**
     * 手札にカードを加える
     * @param card カード
     */
    public void addHand(Card card) {
        hand.add(card);
    }
    
    /**
     * 手札をカードのindex順にソートする
     */
    public void sortHand() {
        Comparator<Card> c = Comparator.comparing(Card::toIndex);
        hand.sort(c);
    }
    
    /**
     * 手札を表示する
     */
    public void showHand() {
        System.out.println();
        System.out.println("----------" + name + "さんの手札を表示します----------");
        for(Card card: hand) {
            System.out.print(card + " ");
        }
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println();
    }
    
    
    
    /**
     * ナポレオンに立候補する
     * @param game ナポレオンゲーム
     * @return 立候補したか
     */
    public abstract boolean runForNapoleon(NapoleonGame game);
    
    /**
     * 副官を指名し、場の札を引いて3枚捨てる
     * @param game ナポレオンゲーム
     */
    public abstract void chooseAdjutantAndChangeCards(NapoleonGame game);
    
    /**
     * トリックにおいてカードを場に出す
     * @param game ナポレオンゲーム
     */
    public abstract void playACard(NapoleonGame game);
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public ArrayList<Card> getHand() {
        return hand;
    }
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getAnnorNum() {
        return annorNum;
    }

    public void setAnnorNum(int annorNum) {
        this.annorNum = annorNum;
    }

    public boolean isWillRun() {
        return willRun;
    }

    public void setWillRun(boolean willRun) {
        this.willRun = willRun;
    }
    
    
}
