package se.t1655002.card.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import se.t1655002.card.entity.Card;

/**
 * @author NakaiKohei
 * キーボード入力に関するユーティリティクラス
 */
public class Keyboard {
    
    /**
     * キーボードから1行読み取る．エラーがあれば再入力させる．
     *
     * @return 読み込んだ文字列
     */
    public static String inputString() {
        String line;
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            line = br.readLine();
        } catch (IOException e) {
            System.err.println("エラー：入出力例外です．もう一度入力．");
            line = inputString();
        }
        
        return line;
        
    }
    
    /**
     * ユーザに整数を1つ入力してもらい，返り値として返す． 正しい入力が得られるまで繰り返す．
     *
     */
    public static int inputNumber() {
        int number;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = br.readLine();
            number = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.err.println("フォーマット例外です. 数字を入力し直してください.");
            number = inputNumber(); // 再起呼び出し
        } catch (IOException e) {
            System.err.println("入出力例外です．もう一度．");
            number = inputNumber(); // 再起呼び出し
        }
        
        return number;
    }
    
    /**
     * 質問とコマンドを表示し、ユーザーにコマンドを入力してもらう。
     * @param question 質問の文字列
     * @param commands 受け付けるコマンドの文字列のリスト
     * @return 入力されたコマンド
     */
    public static int inputCommand(String question, List<String> commands) {
        
        int commandNum = -1;
        do {
            System.out.print(question + ": [ ");
            for (int i = 0; i < commands.size(); i++) {
                String command = commands.get(i);
                System.out.print(i + ":" + command + " ");
            }
            System.out.println("]");
            
            commandNum = Keyboard.inputNumber();
            
            if (commandNum < 0 || commandNum >= commands.size()) {
                System.out.println("正しいコマンドを入力し直してください.");
            }
            
        } while (commandNum < 0 || commandNum >= commands.size());
        
        return commandNum;
    }
    
    /**
     * ユーザーにトランプカードに関する情報を入力してもらい、そのカードを返す.
     * @return 入力されたトランプのカード
     */
    public static Card inputCard() {
        int suit, number;
        String question = "スートを入力してください.";
        List<String> commands = new ArrayList<>();
        commands.clear();
        commands.add("スペード");
        commands.add("ダイヤ");
        commands.add("ハート");
        commands.add("クラブ");
        suit = Keyboard.inputCommand(question, commands);
        while (true) {
            System.out.println("番号を入力してください.");
            number = Keyboard.inputNumber();
            if (number > 0 && number < 14) {
                break;
            } else {
                System.out.println("正しい番号を入力してください.");
            }
        }
        
        Card card = new Card(suit, number);
        question = card.toString() + " でよろしいですか？";
        commands.clear();
        commands.add("はい");
        commands.add("いいえ");
        if (Keyboard.inputCommand(question, commands) == 1) {
            card = Keyboard.inputCard();
        }
        
        return card;
    }
    
}
