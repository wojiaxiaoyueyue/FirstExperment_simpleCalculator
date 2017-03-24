/**
 * Created by qiuzheng on 2017/3/21.
 */

/**
 * 这个类用于来定义位置
 */
public class Position {
    private int lineNumber;
    private int offset;

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getOffset() {
        return offset;
    }

    public int addLineNumber(){
        return ++lineNumber;
    }

    public int addOffset(){
        return ++offset;
    }
}
