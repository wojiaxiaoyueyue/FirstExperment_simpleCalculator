import java.util.Stack;

/**
 * Created by qiuzheng on 2017/3/21.
 */
public class Expression {
    public static StringBuilder toPostFix(String infix){    //将中缀表达式转化成后缀表达式
        Stack<String> stringStack = new Stack<>();  //运算符栈
        StringBuilder postFix = new StringBuilder();
        int i=0;
        while(i<infix.length()){
            char ch = infix.charAt(i);
            switch (ch){
                case '+':case '-':
                    while(!stringStack.empty()&&!stringStack.peek().equals("("))
                        postFix.append(stringStack.pop());
                    stringStack.push(ch+"");
                    i++;
                    break;
                case '*' :case  '/':
                    while (!stringStack.empty()&&(stringStack.peek().equals("*")||stringStack.peek().equals("/")))
                        postFix.append(stringStack.pop());
                    stringStack.push(ch+"");
                    i++;
                    break;
                case '(':
                    stringStack.push(ch+"");
                    i++;
                    break;
                case ')':
                    String out = stringStack.pop();
                    while(out!=null&&!out.equals("(")){
                        postFix.append(out);
                        out=stringStack.pop();
                    }
                    i++;
                    break;
                default:
                    while(i<infix.length()&&ch>='0'&&ch<='9'||ch=='.'){
                        postFix.append(ch);
                        i++;
                        if(i<infix.length()){
                            ch=infix.charAt(i);
                        }
                    }
                    postFix.append(" ");

            }

        }
        while(!stringStack.empty()){
            postFix.append(stringStack.pop());
        }
        return postFix;
    }
    public static double toValue(StringBuilder postFix){
        Stack<Double> doubleStack = new Stack<>();
        double value=0.0;
        boolean isDouble=false;
        double div;
        for(int i=0;i<postFix.length();i++){
            char ch = postFix.charAt(i);
            if(ch>='0'&&ch<='9'){
                value=0.0;
                div=10.0;
                isDouble=false;
                while(ch!=' '){
                    if((isDouble||ch=='.')&&ch!=' ')//如果是浮点数
                    {
                        if(ch=='.')
                            ch=postFix.charAt(++i);
                        value = value + (ch-'0')/div;
                        div *= 10;
                        isDouble=true;
                        ch=postFix.charAt(++i);
                    }
                    if(!isDouble) {
                        value = value * 10 + ch - '0';
                        ch = postFix.charAt(++i);
                    }
                }
                doubleStack.push(value);
            }
            else {
                if(ch!=' '&&ch!='.'){
                    double y=doubleStack.pop(),x=doubleStack.pop();
                    switch (ch){
                        case '+':value=x+y;break;
                        case '-':value=x-y;break;
                        case '*':value=x*y;break;
                        case '/':
                            if(y!=0)
                                value=x/y;
                            else
                                System.err.println("除零操作");
                            break;
                    }
                    doubleStack.push(value);
                }
            }
        }
        return doubleStack.pop();
    }
    public static double Calculate(String infix){
        StringBuilder postFix = toPostFix(infix);
        double result = toValue(postFix);
        return result;
    }
}
