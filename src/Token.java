/**
 * Created by qiuzheng on 2017/3/21.
 */
enum Token_type{num,var,oper}

/**
 * 这个类来定义每一个token的类型、值
 */
public class Token {
    private Token_type type; //此值存储token类型
    private String value;//此值存储变量名|数字值|操作符号值
    private String varValue;//此值存储变量被计算过后的值
    public Token(){
        varValue=null;  //初始化，为每一个变量的值先复制为空
    }

    public String getValue() {
        return value;
    }
    public String getVarValue(){
        return varValue;
    }

    public void setType(Token_type type) {
        this.type = type;
    }

    public Token_type getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }
}
