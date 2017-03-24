import java.util.Scanner;

/**
 * Created by qiuzheng on 2017/3/21.
 */
public class main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        ReadAndCalculate rac = new ReadAndCalculate();
        String path = in.nextLine();
        rac.readTextAndCalculate(path);

    }
}
