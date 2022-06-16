package com.example.calculato;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class CalculateHelper {
    public static double num1;
    public static double num2;
    public static double resultNumber;
    private String sin = "+";

    //사용자의 input을 각각 구분하여 ArrayList에 저장하는 메소드
    private ArrayList splitTokens(String equation) {
        String[] inputData = equation.split(" "); //공백을 기준

        ArrayList inputList = new ArrayList();
        double number = 1;

        boolean flag = false;
        for (String data : inputData) {
            if (data.equals(" ")) {
                continue;
            }
            if (checkNumber(data)) {
                number = number * Double.parseDouble(data);
                flag = true;
            } else {
                if (flag) {
                    inputList.add(number);
                    if(data.equals("-")){
                        number = -1;
                    }else{
                        number = 1;
                    }
                }
                flag = false;
                inputList.add(data);
            }
        }

        if (flag) {
            inputList.add(number);
        }

        return inputList;
    }

    //후위 표기식으로 변형
    private ArrayList infixToPostfix(ArrayList inputData) {
        ArrayList result = new ArrayList();
        HashMap level = new HashMap();
        Stack stack = new Stack();

        //각 기호의 우선순위 레벨. 곱하기, 나누기 > 더하기, 빼기 > 기타
        level.put("*", 3);
        level.put("/", 3);
        level.put("+", 2);
        level.put("-", 2);
        level.put("(", 1);

        for (Object object : inputData) {
            if (object.equals("(")) {           //object가 괄호 일때
                stack.push(object);
            } else if (object.equals(")")) {
                while (!stack.peek().equals("(")) { //바로 앞에 "("있는지 확인
                    Object val = stack.pop();
                    if (!val.equals("(")) {
                        result.add(val);
                    }
                }
                stack.pop();
            } else if (level.containsKey(object)) {     //object가 부호이면 put한것과 같은게 있는지 확인
                if (stack.isEmpty()) {                  //부호 우선순위 비교
                    stack.push(object);
                } else {
                    if (Double.parseDouble(level.get(stack.peek()).toString()) >= Double.parseDouble(level.get(object).toString())) {
                        result.add(stack.pop());
                        stack.push(object);
                    } else {
                        stack.push(object);
                    }
                }
            } else {                                    //object가 숫자면 리스트에 추가
                result.add(object);
            }
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }

    //후위 표기식을 계산
    private Double postFixEval(ArrayList expr) {
        Stack numberStack = new Stack();
        for (Object o : expr) {
            if (o instanceof Double) {
                numberStack.push(o);
            } else if (o.equals("+")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 + num1);
            } else if (o.equals("-")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 + num1);
            } else if (o.equals("*")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 * num1);
            } else if (o.equals("/")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 / num1);
            }
        }

        resultNumber = (Double) numberStack.pop();

        return resultNumber;
    }

    public Double process(String equation) {
        ArrayList postfix = infixToPostfix(splitTokens(equation));      // 후위표기식 변경 적용
        Double result = postFixEval(postfix);               //후위표기식 계산 적용
        return result;
    }

    public boolean checkNumber(String str) {        //숫자면 true 부호면 false
        char check;

        if (str.equals(""))         //비어있는지 체크
            return false;

        for (int i = 0; i < str.length(); i++) {
            check = str.charAt(i);
            if (check < 48 || check > 58) {
                if (check != '.') {
                    if(str.length()<2){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public boolean checkError(String str) {
        char check;

        if (str.equals(""))         //비어있는지 체크
            return false;

        for (int i = 0; i < str.length(); i++) {
            check = str.charAt(i);
            if (check < 48 || check > 58) {
                if (check != '.' && check != '+' && check != '-' && check != '*' && check != '/' && check != '(' && check != ')' && check != ' ')
                    return true;
            }
        }
        return false;
    }


    public String sorted(String reNumber) {
        String[] arrresult = reNumber.split(" ");
        ArrayList<String> resultList = new ArrayList<>();
        ArrayList<String> bracket = new ArrayList<>();  //괄호 포함
        ArrayList<String> muldiv = new ArrayList<>();  //* , /
        ArrayList<String> addsub = new ArrayList<>();  // + , -
        Collections.addAll(resultList, arrresult);
        String bracketStr = "";
        String muldivStr = "";
        String addsubStr = "";
        String bracketMuldivStr = "";
        String bracketAddsubStr = "";
        int count = -1;
        for (int i = 0; i < resultList.size(); i++) {
            if (resultList.get(i).equals("(")) {
                if (bracket.size() > 0) {
                    bracket.add(resultList.get(i - 1));       //괄호가 2개 이상일 경우에 사이에 들어갈 부호도 추가      + 를 예시로>>> ex) ( 2 x 3 ) + ( 3 / 4 )
                }
                bracket.add("(");
                while (!resultList.get(i).equals(")")) {        // 닫힘 괄호 나오기 전까지 모두담는다.
                    i++;
                    bracket.add(resultList.get(i));
                }
                i++;
                if (i == resultList.size()) {                     //i가 최대치일 경우 break;
                    break;
                }
            }
            if (resultList.get(i).equals("*") || resultList.get(i).equals("/")) {               // * , / 만 분리
                if(i>1){
                    if(resultList.get(i-2).equals("+") || resultList.get(i-2).equals("-")){
                        muldiv.add(resultList.get(i-2));
                    }
                }
                if(!(resultList.get(i - 1).equals("(") || resultList.get(i - 1).equals(")"))){
                    muldiv.add(resultList.get(i - 1));
                }
                boolean vo = true;
                while (vo) {
                    if(i < resultList.size()-1){
                        if(resultList.get(i+1).equals("(")){
                            break;
                        }
                    }
                    if (resultList.get(i).equals("+") || resultList.get(i).equals("-") || resultList.get(i).equals("(")) { //덧셈 뺄셈 괄호열기 부호 나올때까지 다 남아라
                        vo = false;
                        if (resultList.get(i).equals("(")) {
                            i = i-2;
                        }
                    } else {
                        muldiv.add(resultList.get(i));
                        i++;
                    }
                    if (i == resultList.size()) {             //i가 최대치일 경우 break;
                        break;
                    }
                }
                if (i == resultList.size()) {                //i가 최대치일 경우 break;
                    break;
                }
            }
            if(addsub.size() == 0 && (resultList.get(1).equals("+") || resultList.get(1).equals("-"))){
                addsub.add(resultList.get(0));
                count++;
            }
            if (resultList.get(i).equals("+") || resultList.get(i).equals("-")) {
                boolean bo = true;
                while (bo) {
                    if(i < resultList.size()-1){
                        if(resultList.get(i+1).equals("(")){
                            break;
                        }
                    }
                    if (resultList.get(i).equals("*") || resultList.get(i).equals("/") || resultList.get(i).equals("(")) {  //곱셈 나눗셈 괄호열기 부호 나올때까지 다 남아라
                        bo = false;
                        if (resultList.get(i).equals("(")) {        //괄호 열기가 나오면 괄호 열기 직전 부호 지워라  5 (+) (
                            addsub.remove(count);
                            count--;
                        } else {
                            addsub.remove(count);                           // * , / 가 나오면 그 전 숫자와 부호를 지워라 2 (+ 5) *
                            addsub.remove(count - 1);
                            count = count -2;
                        }
                        i--;
                    } else {
                        addsub.add(resultList.get(i));
                        i++;
                        count++;
                    }
                    if (i == resultList.size()) {                 //i가 최대치일 경우 break;
                        break;
                    }
                }
            }
        }
        if (bracket.size() != 0) {                              //괄호 배열 정렬
            ArrayList<String> resultList5 = new ArrayList<>();              //괄호 곱셈 나눗셈
            ArrayList<String> resultList6 = new ArrayList<>();              //괄호 덧셈 뺄셈
            if(bracket.contains("*") || bracket.contains("/")){                     //괄호 안에서 우선순위 적용해서 곱셈나눗셈, 뺄셈 덧셈 따로 나누기
                for(int i = 0; i<bracket.size(); i++){
                    if(bracket.get(i).equals("*") || bracket.get(i).equals("/")){
                        resultList5.add(bracket.get(i));
                        if(checkNumber(bracket.get(i+1))){
                            resultList5.add(bracket.get(i+1));
                        }else{
                            resultList5.add(bracket.get(i+2));
                        }
                    }
                    if(bracket.get(i).equals("+") || bracket.get(i).equals("-")){
                        if(bracket.get(i+2).equals("*") || bracket.get(i+2).equals("/")){
                            resultList5.add(bracket.get(i+2));
                            resultList5.add(bracket.get(i+1));
                        }else if(bracket.get(i+1).equals("(")){
                            resultList6.add(bracket.get(i));
                            resultList6.add(bracket.get(i + 2));
                        }else{
                            resultList6.add(bracket.get(i));
                            resultList6.add(bracket.get(i + 1));
                        }
                    }
                    if(i==1){                       //시작 부호[2]로 첫 숫자[1]를 어느 배열에 넣을지 정한다. [0]은 괄호
                        if(bracket.get(2).equals("*") || bracket.get(2).equals("/")){
                            resultList5.add(bracket.get(1));
                        }else{
                            resultList6.add(bracket.get(1));
                        }
                    }
                }
                String test_separ = separation(resultList5);
                if(resultList6.get(0).equals("+") || resultList6.get(0).equals("-")){
                    sin = resultList6.get(0);
                    resultList6.remove(0);
                }
                String test_separa = separation(resultList6);
                bracketMuldivStr = "( "+ test_separ.substring(3);
                bracketAddsubStr = test_separa + " )";
                bracketStr = bracketMuldivStr + bracketAddsubStr;
            }else{
                bracketStr = merge(bracket);
                bracketStr = "( "+ bracketStr.substring(3) + " )";                //괄호안에 곱셈 나눗셈이 없을 경우 숫자 우선순위만 적용한다.
            }
        }
        if (muldiv.size() != 0) {                       //곱셈 나눗셈 배열 정렬
            if(!checkNumber(muldiv.get(0))){
                sin = muldiv.get(0);
                muldiv.remove(0);
            }
            muldivStr = separation(muldiv);             //중간에 덧셈이 끼어있는 곱셈 나눗셈 묶어서 분류
        }
        if(addsub.size() != 0){                     //덧셈 뺄셈 배열 정렬
            addsubStr = merge(addsub);
        }
        String result = "";
        result = bracketStr + muldivStr + addsubStr;
        if(!checkNumber(String.valueOf(result.charAt(0)))){
            if(result.charAt(1) == '-'){
                result = result.substring(3);
                result = "-" + result;
            }else if(result.charAt(0) != '('){
                result = result.substring(3);
            }
            result.replace(" * - ", " * -");
            result.replace(" / - ", " / -");
        }
        return result;
    }

    public String separation(ArrayList dm){                 //곱셈 나눗셈 한 묶음씩 묶고 재배열
        ArrayList<String> test1 = new ArrayList<>();
        ArrayList<String> test_arr = new ArrayList<>();
        String separation = "";
        if (dm.size() != 0) {               //곱셈 나눗셈 배열 정렬
            int count = 0;
            for(int i =0; i<dm.size(); i++){
                test_arr.add(String.valueOf(dm.get(i)));
                if(dm.get(i).equals("+") || dm.get(i).equals("-")){
                    test_arr.remove(count);
                    test1.add( " "+ sin +" "+ merge(test_arr).substring(3));
                    sin = String.valueOf(dm.get(i));
                    test_arr.clear();
                    count = 0;
                }else if(i == dm.size()-1){
                    test1.add( " "+ sin +" "+ merge(test_arr).substring(3));
                    test_arr.clear();
                }
                count++;
            }

            String[] test_str = new String[test1.size()];   // 곱셈 나눗셈 묶음

            for(int i=0; i<test1.size(); i++){
                test_str[i] = test1.get(i);
            }
            int[] test_int = new int[test_str.length];     //각 묶음에서 가장 큰 수만 따로 모아서 비교
            for(int i =0; i<test_str.length; i++){
                if(test_str[i].charAt(1) == '-'){
                    test_int[i] = Integer.parseInt("-" + test_str[i].charAt(3));
                }else{
                    test_int[i] = Integer.parseInt(test_str[i].substring(3,4));
                }
            }
            for(int i =0; i<test_str.length; i++) {
                for (int k = 0; k <test_str.length; k++) {
                    if (test_int[i] > test_int[k]) {
                        int b = test_int[i];
                        test_int[i] = test_int[k];
                        test_int[k] = b;
                        String e = test_str[i];
                        test_str[i] = test_str[k];
                        test_str[k] = e;
                    }
                }
            }
            for(int i =0; i<test_str.length; i++){
                separation += test_str[i];
            }
        }
        sin = "+";
        return separation;
    }


    public String merge(ArrayList value){
        String[] numBerStr = new String[value.size()];        // 양수 음수 둘다 넣은 string
        Integer[] numBer = new Integer[value.size()];              // numberStr에 넣은 양수 음수를 int로 넘겨받음
        ArrayList<String> markNum = new ArrayList<>();        //부호 + 숫자가 함께 들어있는 배열
        Integer[] index = new Integer[value.size()];          //우선순위로 인해 바뀐 순서대로 string에 넣을 용도
        int count =0;
        for(int i =0; i<value.size(); i++){
            if(!(value.get(i).equals("(") || value.get(i).equals(")"))){
                if(!(value.get(i).equals("+") || value.get(i).equals("*") || value.get(i).equals("/"))){
                    if(value.get(i).equals("-")){
                        index[count] = count;
                        numBerStr[count] = "-" + value.get(i+1);
                        count++;
                        i++;
                    }else{
                        index[count] = count;
                        numBerStr[count] = String.valueOf(value.get(i));
                        count++;
                    }
                }
            }
        }
        int count1 = 0;
        if(checkNumber(String.valueOf(value.get(count1)))) {     //첫 부호 정하기
            if (value.size() > 1) {
                if(value.get(count1 + 1).equals("*") || value.get(count1 + 1).equals("/")) {
                    markNum.add(" " + value.get(count1 + 1) + " " + value.get(count1));
                }else{
                    markNum.add(" + " + value.get(count1));
                }
            } else {
                markNum.add(" + " + value.get(count1));
            }
        }
        count1++;
        while(count1 != value.size()){
            if (value.get(count1).equals("(")) {
                markNum.add(" + " + value.get(count1 + 1));
                count1++;
            }else if (checkNumber(String.valueOf(value.get(count1)))) {
                if(value.get(count1 - 1).equals("(")){
                    markNum.add(" + " + value.get(count1));
                }else{
                    markNum.add(" " + value.get(count1 - 1) + " "+value.get(count1));
                }
            }
            count1++;
        }
        for(int i =0; i<count; i++){
            numBer[i] = Integer.parseInt(numBerStr[i]);
        }
        for(int i =0; i<count; i++){
            for(int k =0; k<count; k++){
                if(numBer[i]>numBer[k]) {
                    int a = numBer[i];
                    numBer[i] = numBer[k];
                    numBer[k] = a;
                    int bx = index[i];
                    index[i] = index[k];
                    index[k] = bx;
                }
            }
        }
        String str = "";                        //리턴 값
        for(int i =0; i<count; i++){
            str += markNum.get(index[i]);
        }
        return str;
    }
}