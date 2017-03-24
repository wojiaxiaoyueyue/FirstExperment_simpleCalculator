/**
 * Created by qiuzheng on 2017/3/21.
 */

import java.io.*;
import java.util.ArrayList;

/**
 * 这个类用于读取并且计算
 */
public class ReadAndCalculate {
    //行与偏移量
    private Position pos = new Position();
    //Token链表
    private ArrayList<Token> tokenArrayList = new ArrayList<>();

    public void readTextAndCalculate(String txtPath){
        BufferedReader buf = null;
        String currLine;
        pos.setLineNumber(0);
        try{
            buf = new BufferedReader(new InputStreamReader(new FileInputStream(txtPath)));
            while((currLine=buf.readLine())!=null){
                pos.addLineNumber();
                if(currLine.startsWith("print")) {
                    currLine=preTreatPrint(currLine);
                    printResult(currLine);
                }
                else {
                    getToken(currLine);
                    currLine=preTreat(currLine);
                    String varName = getVarName(currLine);
                    int varSize = getVarSize(currLine);
                    String expression = currLine.substring(varSize+1,currLine.length()-1);
                    updateTokenTable(varName,Expression.Calculate(expression)+"");
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //将变量存储在Token链表里
    public void getToken(String _currLine){
        //初始化偏移量
        pos.setOffset(0);
        //变量开始的偏移量
        int varTokenBegin=0;
        //当前偏移量
        int offset = pos.getOffset();
        //当前字符串
        char currCharacter;
        //当前token
        Token token;

        while(offset<_currLine.length()){
            currCharacter = _currLine.charAt(offset);
            //如果遇到换行、空格、TAB均跳过
            if(currCharacter==' '||currCharacter=='\t'||currCharacter=='\n'){
                pos.addOffset();
                offset=pos.getOffset();
            }
            else if(currCharacter>='a'&&currCharacter<='z'||currCharacter>='A'){
                varTokenBegin = offset;
                //继续读下一个char
                offset=pos.addOffset();
                currCharacter = _currLine.charAt(offset);
                if(currCharacter=='='){
                    token = new Token();
                    token.setType(Token_type.var);
                    String varName = _currLine.substring(varTokenBegin,offset);
                    token.setValue(varName);
                    tokenArrayList.add(token);
                    token=null;
                    break;
                }
                else{
                    //判断变量构成不能是非法字符
                    while(!(currCharacter>=36 && currCharacter <= 47) && currCharacter !=';'){
                        pos.addOffset();
                        offset = pos.getOffset();
                        if(offset<_currLine.length()&&currCharacter!='='){
                            currCharacter = _currLine.charAt(offset);
                        }
                        else if(currCharacter=='='){
                            token = new Token();
                            token.setType(Token_type.var);
                            String varName = _currLine.substring(varTokenBegin,--offset);
                            token.setValue(varName);
                            tokenArrayList.add(token);
                            token=null;
                            break;
                        }
                    }
                }
            }
            else if(currCharacter==';'){
                break;
            }
            else{
                offset=pos.addOffset();
            }
        }

    }

    /**
     * 预处理，对当前行右值中的变量(如果上文已求出结果)，则进行值之间的代换
     * @param _currline 当前行
     * @return
     */
    public String preTreat(String _currline){
        String rValue = _currline.substring(getVarSize(_currline)+1,_currline.length()-1);
        for(int i =0;i<this.tokenArrayList.size();i++){
            if(tokenArrayList.get(i).getVarValue()!=null){
                rValue=rValue.replaceAll(tokenArrayList.get(i).getValue(),tokenArrayList.get(i).getVarValue());
            }
        }
        //处理
        return _currline.substring(0,getVarSize(_currline)+1)+rValue+";";

    }

    public static int getVarSize(String _currLine){

        int varEnd=0;
        char ch = _currLine.charAt(varEnd);
        while(ch!='='){
            ch=_currLine.charAt(++varEnd);
        }
        return varEnd;
    }

    /**
     * 此函数的目的是为了获得变量名
     * @param _currLine
     * @return
     */
    public static String getVarName(String _currLine){
        String varName = _currLine.substring(0,getVarSize(_currLine));
        return varName;
    }

    /**
     * 打印结果
     * @param _currline
     * @return
     */
    public void printResult(String _currline){
        String var = _currline.substring(6,_currline.length()-2);
        for(int i = 0;i<tokenArrayList.size();i++){
            if(tokenArrayList.get(i).getValue().equals(var)){
                System.out.printf("%.2f\n",tokenArrayList.get(i).getVarValue());
                return;
            }
        }

        //如果print()内部是表达式
        System.out.printf("%.2f\n",Expression.Calculate(var));
        return;

    }

    /**
     * 通过计算来更新变量的值
     * @param _value    变量名
     * @param _varValue     变量值
     */
    public void updateTokenTable(String _value,String _varValue){
        for (int i = 0; i <this.tokenArrayList.size()  ; i++) {
            if(tokenArrayList.get(i).getValue().equals(_value)){
                tokenArrayList.get(i).setVarValue(_varValue);
                break;
            }
        }
    }

    /**
     * 对类似print(a+b) 即print()里面包含位置变量的预处理
     * @param _currline
     */
    public String preTreatPrint(String _currline){
        String rValue = _currline.substring(6,_currline.length()-2);
        for(int i =0;i<this.tokenArrayList.size();i++){
            if(tokenArrayList.get(i).getVarValue()!=null){
                rValue=rValue.replaceAll(tokenArrayList.get(i).getValue(),tokenArrayList.get(i).getVarValue());
            }
        }
        //处理
        return _currline.substring(0,6)+rValue+");";
    }
}
