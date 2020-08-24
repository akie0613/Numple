import java.util.ArrayList;
import java.util.List;

public class MysteryNum {

    private List<Integer> enterable ;

    public MysteryNum( int num) {
        List<Integer> enterable_new = new ArrayList<>() ;
        //ナンプレのそれぞれのマス毎に、そのマスに入り得る数字の配列を作成
        //引数が0なら1~9の値を、それ以外なら引数の値をenterableに追加
        if(num==0){
            for (int i = 1;i<=9;i++){
                enterable_new.add(i);
            }
        }
        else {
            enterable_new.add(num);
        }
        enterable=enterable_new;
    }
    public List<Integer> getEnterable() {
        return enterable;
    }
}
