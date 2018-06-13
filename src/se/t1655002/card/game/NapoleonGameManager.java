package se.t1655002.card.game;

import java.util.ArrayList;
import java.util.List;

import se.t1655002.card.entity.CPU;
import se.t1655002.card.entity.Player;
import se.t1655002.card.entity.User;
import se.t1655002.card.util.Keyboard;

/**
 * @author NakaiKohei
 * NapoleonGameを管理するクラス
 */
public class NapoleonGameManager {
    
    /**
     * 管理するNapoleonGame
     */
    private NapoleonGame game;

    /**
     * コンストラクタ。同時にgameとPlayerを作成.
     */
    public NapoleonGameManager() {
        
        super();
        String name;
        User user;
        CPU[] cpus = new CPU[4];
        List<Player> players = new ArrayList<>();
        
        System.out.println("名前を入力してください：");
        name = Keyboard.inputString();
        if(name == null) { name = "名無しユーザ"; }
        user = new User(name);
        players.add(user);
        System.out.println(name + "さんを登録しました。");
        
        for(int i=0; i<4; i++) {
            cpus[i] = new CPU("cpu" + i);
            players.add(cpus[i]);
        }
        
        game = new NapoleonGame(3, players);
    }
    
    /**
     * NapoleonGameで実際に遊ぶ.
     */
    public void playGame() {
        System.out.println(game.getNumberOfGames() + "回勝負のナポレオンを開始します.");
        game.play();
    }
    
    
    
}
