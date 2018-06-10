package se.t1655002.card.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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
    
}
