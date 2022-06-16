package com.example.calculato;

import static com.example.calculato.util.Constants.CHECK_LOG;
import static com.example.calculato.util.Constants.FUNCTION_1;
import static com.example.calculato.util.Constants.FUNCTION_2;
import static com.example.calculato.util.Constants.FUNCTION_3;
import static com.example.calculato.util.Constants.GRAPH_LOG_TAG;
import static com.example.calculato.util.Constants.RANGE;
import static com.example.calculato.util.Constants.START_NUM;
import static com.example.calculato.util.Constants.STEP;
import static com.example.calculato.util.Constants.X_LINE;
import static com.example.calculato.util.Constants.X_String;
import static com.example.calculato.util.Constants.Y_LINE;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/*
 AsyncTask 는 비동기 작업을 수행하기위해 사용됩니다.
 AsyncTask 의 형태 == AsyncTask<Params, Progress, Result>
  1. Params: doInBackground 메서드의 파라미터 타입이 되며, execute 메서드 인자 값이 됩니다.
  2. progress: doInBackground 작업 시 진행 단위의 타입으로 onProgressUpdate 파라미터 타입입니다.
  3. Result: doInBackground 리턴값으로 onPostExecute 파라미터 타입입니다.
 */
public class GraphAsyncTask extends AsyncTask<String, Void, ArrayList<Entry>> {

    private String function;
    private Activity activity;
    private int position;
    private String xStringMinus;

    public GraphAsyncTask(String function, Activity activity, int position) {
        this.function = function;
        this.activity = activity;
        this.position = position;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Entry> doInBackground(String... strings) {
        if (CHECK_LOG) Log.i(GRAPH_LOG_TAG, "doInBackground() " + position);

        ArrayList<Entry> entryList = new ArrayList<>();
        if (function != null && activity != null && function.length() != 0) {
            if (position == X_LINE) {
                for (float i = -50.00f; i < 50; i += 0.01f) {
                    float y = i;
                    float x = 0;
                    entryList.add(new Entry(x, y));
                }
            } else if (position == Y_LINE) {
                for (float i = -50.00f; i < 50; i += 0.01f) {
                    float y = 0;
                    float x = i;
                    entryList.add(new Entry(x, y));

                }
            } else {
                if (function != null && function.length() != 0) {
                    /* 전체 함수에서 y=을 제거한 String */
                    String functionDeleteEqualY = function.replaceFirst("y=", "");
                    Log.v(GRAPH_LOG_TAG, "firstFunctionDeleteEqualY : " + functionDeleteEqualY);

                    /* 전체 함수에서 y=을 제거 후, x의 index 확인 */
                    int firstFindXTest = functionDeleteEqualY.indexOf(X_String);
                    Log.v(GRAPH_LOG_TAG, "firstFindXTest : " + firstFindXTest);

                    /* x앞에 붙어 있는 숫자, 문자 확인 */
                    String xFirstFront = functionDeleteEqualY.substring(0, firstFindXTest);
                    Log.v(GRAPH_LOG_TAG, "xFirstFront : " + xFirstFront);

                    /* x앞에 오는 값이 숫자인지 문자인지 확인 (아무것도 오지 않을 경우 ex. y=x~ xFirstFront 값을 1로 처리 */
                    if (xFirstFront.equals("")) {
                        xFirstFront = "1";
                    }

                    /* x뒤에 붙어 있는 계산 및 연산 String 확인 */
                    String xFirstBehind = functionDeleteEqualY.substring(firstFindXTest + 1).trim();
                    if (xFirstBehind.equals("")) {
                        xFirstBehind = "+0";
                    }
                    String xFirstBehindNumber = xFirstBehind.replaceAll("[^0-9]", "");
                    Log.v(GRAPH_LOG_TAG, "xFirstBehind : " + xFirstBehind);
                    Log.v(GRAPH_LOG_TAG, "xFirstBehindNumber : " + xFirstBehindNumber);

                    /* 연산 String 을 Parsing 하여 계산 */

                    String[] xFirstBehindArray = xFirstBehind.split("");

                    // x 뒤에 오는 계산식 String 값을 배열로 만듭니다.
                    String[] strArray = functionStrSplit(xFirstBehind);
                    // 만든 배열을 계산합니다.
                    int behindCalculateResult = calculationStrArray(strArray);

                    for (int i = 0; i < xFirstBehindArray.length; i++) {
                        Log.i(GRAPH_LOG_TAG, "xFirstBehindArray[" + i + "] = " + xFirstBehindArray[i]);
                    }

                    Log.i(GRAPH_LOG_TAG, "xFirstBehindArray.length = " + xFirstBehindArray.length);

                    String behindCalculate = Integer.toString(behindCalculateResult);

                    int xIntFront = 0;
                    boolean xFront = com.example.calculato.GraphActivity.numberCheck(xFirstFront);

                    if (xFront) { /* y = 정수 , x = 정수 , x항과 숫자항이 존재할 때, x항이 뒤로가는 경우는 아직 생각 안함 */
                        xIntFront = Integer.parseInt(xFirstFront);
                        String operatorCheck = functionDeleteEqualY.substring(firstFindXTest + 1, firstFindXTest + 2);
                        Log.v(GRAPH_LOG_TAG, "operatorCheck : " + operatorCheck);

                        if (operatorCheck.equals("+")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i + Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("-")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i - Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("*")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i * Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("/")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i / Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        }
                    } else {
                        if (xFirstFront.charAt(0) == 's') {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = (float) sin(i);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        } else if (xFirstFront.charAt(0) == 'c') {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = (float) cos(i);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        } else if (xFirstFront.charAt(0) == 't') {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = (float) tan(i);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        }
                    }
                }
            }
        }
        return entryList;
    }

    @Override
    protected void onPostExecute(ArrayList<Entry> result) {
        if (CHECK_LOG) Log.i(GRAPH_LOG_TAG, "onPostExecute() " + position);

        if (result != null) {
            LineDataSet lineDataSet = new LineDataSet(result, function);

            if (CHECK_LOG) Log.i(GRAPH_LOG_TAG, position + " lineDataSet = " + lineDataSet);
            int setColor = -1;
            switch (position) {
                case X_LINE:
                case Y_LINE:
                    setColor = Color.LTGRAY;
                    break;
                case FUNCTION_1:
                    setColor = Color.DKGRAY;
                    break;
                case FUNCTION_2:
                    setColor = Color.GREEN;
                    break;
                case FUNCTION_3:
                    setColor = Color.BLUE;
                    break;
            }
            if (setColor != -1) {
                lineDataSet.setColor(setColor);
                lineDataSet.setCircleColor(setColor);
            }
            if (CHECK_LOG) Log.i(GRAPH_LOG_TAG, position + " setColor = " + setColor);
            com.example.calculato.GraphActivity graphActivity = null;
            if (activity != null) graphActivity = (com.example.calculato.GraphActivity) activity;
            List<ILineDataSet> dataSets = graphActivity.getDataSets();
            dataSets.add(lineDataSet);
            if (position == Y_LINE) graphActivity.setXYLineDone(true);
            if (position == FUNCTION_3) graphActivity.setDrawPlay(false);
        }
    }
    // (sbc) 문자열을 부호를 기준으로 잘라서 배열로 만드는 메서드
    private String[] functionStrSplit(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] strArray = null;
        if (str != null && str.length() != 0) {

            // 숫자를 하나로 합쳐서 저장할 변수
            StringBuffer addNumber = new StringBuffer();
            for (int i = 0; i < str.length(); i++) {
                // 공백이 있는경우 스킵
                if(str.charAt(i) == ' ') continue;
                // 아스키 코드 문자형식 0~9 는 48~57 이므로 i 번째가 숫자일 때의 조건문을 만듭니다.
                if (str.charAt(i) > 47 && str.charAt(i) < 58) {
                    addNumber.append(str.charAt(i));
                }else {
                    // 숫자가 아니면 합친 숫자문자열을 배열에 저장합니다.
                    arrayList.add(addNumber.toString());
                    // StringBuffer 를 초기화 합니다.
                    addNumber.setLength(0);

                    // 아스키코드 '+' == 43
                    // 아스키코드 '-' == 45
                    // 아스키코드 '*' == 42
                    // 아스키코드 '/' == 47
                    // 아스키코드 소문자 'x' == 120
                    if (str.charAt(i) == 43 || str.charAt(i) == 45 || str.charAt(i) == 42 || str.charAt(i) == 47) {
                        String signStr = String.valueOf(str.charAt(i));
                        arrayList.add(signStr);
                    }
                }
                if(i == str.length()-1) {
                    arrayList.add(addNumber.toString());
                }
            }

            // list 에서 공백을 모두 제거합니다
            /* ArrayList 의 removeAll 메서드는 파라미터값으로 넣은 컬렉션에 포함된 모든 요소를 삭제하는 메서드입니다.
                거기서 Arrays 의 간단하게 컬렉션을 만들어주는 asList 메서드를 사용해서 파라미터에 지우고 싶은 값들을 넣습니다.
             */
            arrayList.removeAll(Arrays.asList("", null, " "));
        }
        return changeListIntoStringArray(arrayList);
    }

    // (sbc) 계산해주는 메서드
    private int calculationStrArray(String[] strArray) {
        int result = -1;

        // 첫번째 부호가 - 면 음수로 만듭니다.
        if(strArray[0].charAt(0) == 45) {
            strArray[1] = strArray[0] + strArray[1];
            strArray[0] = "";
        }else if(strArray[0].charAt(0) == 43 || strArray[0].charAt(0) == 42 || strArray[0].charAt(0) == 47){
            strArray[0] = "";
        }

        String[] sortArray = infixToPostfix(strArray);
        return postFixEval(sortArray);
    }

    //후위 표기식으로 변형
    private String[] infixToPostfix(String[] inputData) {
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
            // 배열이 공백일때는 패스
            String objStr = (String)object;
            if(objStr == null || objStr.trim().length() == 0) continue;
            if (object.equals("(")) {
                stack.push(object);
            } else if (object.equals(")")) {
                while (!stack.peek().equals("(")) {
                    Object val = stack.pop();
                    if (!val.equals("(")) {
                        result.add(val);
                    }
                }
                stack.pop();
            } else if (level.containsKey(object)) {
                if (stack.isEmpty()) {
                    stack.push(object);
                } else {
                    // 여기서 level 을 나타내는 숫자들로 비교를 하다보니, + 와 - 부호의 순서가 맞지않는 현상이 발생했다.
                    // 해결: - 부호와 + 부호를 비교할때 >= 는 비교식 때문에 -가 배열로 들어가버려서 생기는 문제였다. 비교식을 > 로 변경했다.
                    // 문제: 비교식을 > 로 수정하면 * 와 / 를 비교할때 배열에 남아있게되는 문제가 발생한다.
                    // 해결: if 문을 사용해서 * / 일때와 + - 일때의 처리를 다르게 해준다.
                    // 아스키 코드 120 == x , 47 == /
                    if(stack.peek().toString().charAt(0) == 42 || stack.peek().toString().charAt(0) == 47) {
                        if (Integer.parseInt(level.get(stack.peek()).toString()) >= Integer.parseInt(level.get(object).toString())) {
                            result.add(stack.pop());
                        }
                    }else{
                        if (Integer.parseInt(level.get(stack.peek()).toString()) > Integer.parseInt(level.get(object).toString())) {
                            result.add(stack.pop());
                        }
                    }
                    stack.push(object);
                }
            } else {
                result.add(object);
            }
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return changeListIntoStringArray(result);
    }

    public static int num1;
    public static int num2;
    public static int resultNumber;

    //후위 표기식을 계산
    private int postFixEval(String[] expr) {
        //(shin) stack 하나로 모든 계산을 수행했더니 +, - 가 뒤에서부터 계산되어서 결과값이 맞지않는 문제가 있어서 Deque 과 stack 을 사용한 방법으로 수정
        Stack<String> plusMinusStack = new Stack();
        Deque<String> numberStack = new ArrayDeque();
        for (String o : expr) {

            if (o.charAt(0) > 47 && o.charAt(0) < 58) {
                // Deque 은 push 와 pop 을 하게 되면 맨 앞에서부터 넣고 빼고 하므로 addLast 나 add 메서드를 사용해야한다.
                numberStack.addLast(o);
                continue;
            }else if(o.charAt(0) == 45) {
                // 음수를 charAt 하니까 - 가 걸려서 음수가 숫자로 처리되지 않아서 if 문 추가
                if(o.length() > 1) {
                    if(o.charAt(1) > 47 && o.charAt(1) < 58) {
                        numberStack.addLast(o);
                        continue;
                    }
                }
            }

            if (o.equals("+") || o.equals("-")) {
                plusMinusStack.push(o);
            } else if (o.equals("*")) {
                num1 = Integer.parseInt(numberStack.pollLast());
                num2 = Integer.parseInt(numberStack.pollLast());
                numberStack.addLast(Integer.toString(num2 * num1));
            } else if (o.equals("/")) {
                num1 = Integer.parseInt(numberStack.pollLast());
                num2 = Integer.parseInt(numberStack.pollLast());
                numberStack.addLast(Integer.toString(num2 / num1));
            }
        }
        // 스텍은 뒤에서부터 꺼내고, 숫자는 앞에서 부터 꺼낸다.(+ - 는 왼쪽부터 계산)
        while (plusMinusStack.size() != 0) {
            String o = plusMinusStack.pop();
            if (o.equals("+")) {
                num1 = Integer.parseInt(numberStack.pollLast());
                num2 = Integer.parseInt(numberStack.pollLast());
                numberStack.push(Integer.toString(num1 + num2));
            } else if (o.equals("-")) {
                num1 = Integer.parseInt(numberStack.pollLast());
                num2 = Integer.parseInt(numberStack.pollLast());
                numberStack.push(Integer.toString(num1 - num2));
            }
        }

        resultNumber = Integer.parseInt(numberStack.pollLast());;

        return resultNumber;
    }

    // List 를 String 배열로 바꾸는 메서드
    private String[] changeListIntoStringArray(List<String> list) {
        String[] strArray = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).equals(" ")) continue;
            strArray[i] = list.get(i);
        }
        return strArray;
    }
}