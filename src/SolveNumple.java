import java.io.IOException;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
public class SolveNumple {
    public static void main(String[] args) {
        //2020/8/21 13:00~2020/8/24 10:30
        /*
        流れ：
        ①ナンプレのそれぞれのマス毎に、そのマスに入り得る数字の配列を持つオブジェクトを作成
      　②配列から縦、横、ブロックにある数字を削除する
      　③すべてのオブジェクトが持つ配列の長さが1になるまで繰り返す
         */
        //読み込むファイルを定義
        String pass = "C:\\Exercise\\Numple.txt";
        //Readメソッドよりcsv読み込み
        List<String> textList = Read(pass);

        //txtの行数分ループ
        for (int loop = 0; loop < textList.size(); loop++) {
            String str = textList.get(loop);
            List<MysteryNum> allNum = new ArrayList<>();
            //81個のオブジェクト作成
            for (int i = 0; i < 81; i++) {
                MysteryNum Num = new MysteryNum(Character.getNumericValue(str.charAt(i)));
                allNum.add(Num);
            }

            System.out.println("解読前:" + (loop + 1));
            //コンソール出力
            OutputCons(allNum);

            out:
            for (int i = 0; ; i++) {
                //10回ループしても解けないなら解読不可とみなす
//                if(i >10&&i%3==0){
//                    System.out.println("解読不可:");
//                    for (int j = 0; j < allNum.size(); j++) {
//                        if (allNum.get(j).getEnterable().size() == 2) {
//                            allNum.get(j).getEnterable().remove(0);
//                            System.out.println("仮置き");
//                            break ;
//                        }
//
//                    }
//                    //break ;
//                }
//                if(i >30&&i%3==0){
//                    System.out.println("解読不可2:");
//                    for (int j = 0; j < allNum.size(); j++) {
//                        if (allNum.get(j).getEnterable().size() == 3) {
//                            allNum.get(j).getEnterable().remove(0);
//                        }
//                        System.out.println("仮置き2");
//                        break ;
//                    }
//                    //break ;
//                }
                if (i == 10) {
                    System.out.println("解読不可:");
                    //isTempTrue(allNum);
                    break;
                }
                //横ライン計算処理
                allNum = calcLine(allNum);
                //縦ライン計算処理
                allNum = calcRow(allNum);
                //ブロック計算処理
                allNum = calcBlock(allNum);
                //すべてのオブジェクトが持つ配列"Enterable"の長さが1になるまで繰り返す
                for (int j = 0; j < allNum.size(); j++) {
                    if (allNum.get(j).getEnterable().size() != 1) {
                        continue out;
                    }
                }
                break;
            }
            System.out.println("解読後:");
            //コンソール出力
            OutputCons(allNum);
        }
    }

    //ファイルを読込むメソッド
    public static List<String> Read(String path_s) {
        List<String> textList = new ArrayList<>();
        try {
            Path path = Paths.get(path_s);
            textList = Files.readAllLines(path, StandardCharsets.UTF_8);

            //指定したファイルが存在しない、アクセスできない場合、エラーメッセージを出力
        } catch (FileNotFoundException e) {
            System.out.print("ファイル読込みに失敗しました");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("ファイル読込みに失敗しました");
            e.printStackTrace();
        }
        return textList;
    }

    //コンソール出力メソッド
    public static void OutputCons(List<MysteryNum> allNum) {
        for (int i = 0; i < 81; i++) {
            if (allNum.get(i).getEnterable().size() == 1) {
                System.out.print(allNum.get(i).getEnterable());
            } else {
                System.out.print("[0]");
            }
            if ((i + 1) % 9 == 0) {
                System.out.println();
            }
        }
    }

    //横ライン計算処理
    public static List<MysteryNum> calcLine(List<MysteryNum> allNum) {
        for (int i = 0; i < 81; i++) {
            //対象のEnterableの中身が未確定なら処理をする
            if (allNum.get(i).getEnterable().size() != 1) {
                for (int j = 0; j < 9; j++) {
                    //比較対象のEnterableの中身が確定かつ対象のEnterableの中身が未確定なら
                    if (allNum.get((i / 9 * 9) + j).getEnterable().size() == 1 && allNum.get(i).getEnterable().size() != 1) {
                        //Enterableに比較対象の値が含まれているならEnterableからその値を削除
                        if (allNum.get(i).getEnterable().contains(allNum.get((i / 9 * 9) + j).getEnterable().get(0))) {
                            int deleteNum = allNum.get((i / 9 * 9) + j).getEnterable().get(0);
                            allNum.get(i).getEnterable().remove(Integer.valueOf(deleteNum));
                        }
                    }
                }
            }
        }
        return allNum;
    }

    //縦ライン計算処理
    public static List<MysteryNum> calcRow(List<MysteryNum> allNum) {
        for (int i = 0; i < 81; i++) {
            //対象のEnterableの中身が未確定なら処理をする
            if (allNum.get(i).getEnterable().size() != 1) {
                for (int j = 0; j < 9; j++) {
                    //比較対象のEnterableの中身が確定かつ対象のEnterableの中身が未確定なら
                    if (allNum.get((i % 9) + (j * 9)).getEnterable().size() == 1 && allNum.get(i).getEnterable().size() != 1) {
                        //Enterableに比較対象の値が含まれているならEnterableからその値を削除
                        if (allNum.get(i).getEnterable().contains(allNum.get((i % 9) + (j * 9)).getEnterable().get(0))) {
                            int deleteNum = allNum.get((i % 9) + (j * 9)).getEnterable().get(0);
                            allNum.get(i).getEnterable().remove(Integer.valueOf(deleteNum));
                        }
                    }
                }
            }
        }
        return allNum;
    }

    //ブロック計算処理
    public static List<MysteryNum> calcBlock(List<MysteryNum> allNum) {
        for (int i = 0; i < 81; i++) {
            //対象のEnterableの中身が未確定なら処理をする
            if (allNum.get(i).getEnterable().size() != 1) {
                for (int j = 0; j < 9; j++) {
                    //比較対象のEnterableの中身が確定かつ対象のEnterableの中身が未確定なら
                    int index = (((i / 9) / 3) * 27) + (((i % 9) / 3) * 3) + j % 3 + ((j / 3) * 9);
                    if (allNum.get(index).getEnterable().size() == 1 && allNum.get(i).getEnterable().size() != 1) {
                        //Enterableに比較対象の値が含まれているならEnterableからその値を削除
                        if (allNum.get(index).getEnterable().contains(allNum.get(index).getEnterable().get(0))) {
                            int deleteNum = allNum.get(index).getEnterable().get(0);
                            allNum.get(i).getEnterable().remove(Integer.valueOf(deleteNum));
                        }
                    }
                }
            }
        }
        return allNum;
    }

    //解読不可判定のナンプレを解読　作成途中
    public static List<MysteryNum> isTempTrue(List<MysteryNum> allNum) {
        int temp1 = 0;
        int temp2;
        int tempIndex=0;



        List<MysteryNum> allNum2 = new ArrayList<>(allNum);


        for (int j = 0; j < allNum2.size(); j++) {
            if (allNum2.get(j).getEnterable().size() == 2) {
                temp1 = allNum2.get(j).getEnterable().get(1);
                tempIndex = j;
                allNum2.get(j).getEnterable().remove(0);
                System.out.println("仮置き");
                break;
            }
        }

        out:
        for (int i = 0; ; i++) {
            //横ライン計算処理
            allNum2 = calcLine(allNum2);
            //縦ライン計算処理
            allNum2 = calcRow(allNum2);
            //ブロック計算処理
            allNum2 = calcBlock(allNum2);
            if (i == 10) {
                System.out.println("解読不可:");
                allNum.get(tempIndex).getEnterable().remove(0);
                allNum.get(tempIndex).getEnterable().add(temp1);
                isTempTrue(allNum);
            }
            //すべてのオブジェクトが持つ配列"Enterable"の長さが1になるまで繰り返す
            for (int j = 0; j < allNum2.size(); j++) {
                if (allNum2.get(j).getEnterable().size() != 1) {
                    continue out;
                }
            }
            break ;
        }
        for (int i = 0; i < 81; i++) {
                for (int j = 0; j < 9; j++) {
                    //比較対象のEnterableの中身が確定かつ対象のEnterableの中身が未確定なら
                    if ((allNum.get((i / 9 * 9) + j).getEnterable().get(0) ==  allNum.get(i).getEnterable().get(0))
                    ||(allNum.get((i % 9) + (j * 9)).getEnterable().get(0) ==  allNum.get(i).getEnterable().get(0))
                            ||(allNum.get((((i / 9) / 3) * 27) + (((i % 9) / 3) * 3) + j % 3 + ((j / 3) * 9)).getEnterable().get(0) ==  allNum.get(i).getEnterable().get(0))
                    ) {
                        allNum.get(tempIndex).getEnterable().remove(0);
                        allNum.get(tempIndex).getEnterable().add(temp1);
                        isTempTrue(allNum);
                    }

                }

        }

        return allNum2;
    }
//
//}
}