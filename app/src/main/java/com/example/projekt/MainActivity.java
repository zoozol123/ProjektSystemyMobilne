package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultTextView, solutionTextView;
    MaterialButton cButton, openBracketButton, closeBracketButton, divideButton;
    MaterialButton sqrtButton, squareButton, percentButton, piButton;
    MaterialButton sevenButton, eightButton, nineButton, multiplyButton;
    MaterialButton fourButton, fiveButton, sixButton, addButton;
    MaterialButton oneButton, twoButton, threeButton, subtractButton;
    MaterialButton ceButton, zeroButton, dotButton, resultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.result_text_view);
        solutionTextView = findViewById(R.id.solution_text_view);

        cButton = findViewById(R.id.button_c);
        cButton.setOnClickListener(this);
        openBracketButton = findViewById(R.id.button_open_bracket);
        openBracketButton.setOnClickListener(this);
        closeBracketButton = findViewById(R.id.button_close_bracket);
        closeBracketButton.setOnClickListener(this);
        divideButton = findViewById(R.id.button_divide);
        divideButton.setOnClickListener(this);
        sevenButton = findViewById(R.id.button_7);
        sevenButton.setOnClickListener(this);
        eightButton = findViewById(R.id.button_8);
        eightButton.setOnClickListener(this);
        nineButton = findViewById(R.id.button_9);
        nineButton.setOnClickListener(this);
        multiplyButton = findViewById(R.id.button_multiply);
        multiplyButton.setOnClickListener(this);
        fourButton = findViewById(R.id.button_4);
        fourButton.setOnClickListener(this);
        fiveButton = findViewById(R.id.button_5);
        fiveButton.setOnClickListener(this);
        sixButton = findViewById(R.id.button_6);
        sixButton.setOnClickListener(this);
        addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(this);
        oneButton = findViewById(R.id.button_1);
        oneButton.setOnClickListener(this);
        twoButton = findViewById(R.id.button_2);
        twoButton.setOnClickListener(this);
        threeButton = findViewById(R.id.button_3);
        threeButton.setOnClickListener(this);
        subtractButton = findViewById(R.id.button_subtract);
        subtractButton.setOnClickListener(this);
        ceButton = findViewById(R.id.button_ce);
        ceButton.setOnClickListener(this);
        zeroButton = findViewById(R.id.button_0);
        zeroButton.setOnClickListener(this);
        dotButton = findViewById(R.id.button_dot);
        dotButton.setOnClickListener(this);
        resultButton = findViewById(R.id.button_result);
        resultButton.setOnClickListener(this);
        sqrtButton = findViewById(R.id.button_sqrt);
        sqrtButton.setOnClickListener(this);
        squareButton = findViewById(R.id.button_square);
        squareButton.setOnClickListener(this);
        percentButton = findViewById(R.id.button_percent);
        percentButton.setOnClickListener(this);
        piButton = findViewById(R.id.button_pi);
        piButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String buttonValue = button.getText().toString();
        String dataToCalculate = solutionTextView.getText().toString();

        if(buttonValue.equals("ce")) {
            solutionTextView.setText("");
            resultTextView.setText("0");
            return;
        }
        if(buttonValue.equals("=")) {
            resultTextView.setText(getResult(dataToCalculate));
            return;
        }
        if(buttonValue.equals("x\u00B2")) {
            dataToCalculate += "Math.pow(, 2)";
            solutionTextView.setText(dataToCalculate);
            return;
        }
        if(buttonValue.equals("π")) {
            if( !dataToCalculate.endsWith("Math.pow(, 2)") && !dataToCalculate.endsWith("Math.sqrt()") ) {
                dataToCalculate += "Math.PI";
                solutionTextView.setText(dataToCalculate);
                return;
            }
        }
        if(buttonValue.equals("\u221A")) {
            dataToCalculate += "Math.sqrt()";
            solutionTextView.setText(dataToCalculate);
            return;
        }
        if(buttonValue.equals("C")) {
            if(solutionTextView.getText() != "") {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
                solutionTextView.setText(dataToCalculate);
            } else {
                solutionTextView.setText(dataToCalculate);
                return;
            }
        } else {
            if (dataToCalculate.endsWith("Math.pow(, 2)")) {
                int length = dataToCalculate.length();
                if (length >= 13) {
                    String begin = dataToCalculate.substring(0, length - 5);
                    String end = dataToCalculate.substring(length - 4);
                    if(buttonValue.equals("π"))
                        dataToCalculate = begin + "(" + "Math.PI" + end;
                    else
                        dataToCalculate = begin + "(" + buttonValue + end;
                } else {
                    dataToCalculate += buttonValue;
                }
            } else if (dataToCalculate.endsWith("Math.sqrt()")) {
                int length = dataToCalculate.length();
                if (length >= 11) {
                    String begin = dataToCalculate.substring(0, length - 2);
                    String end = dataToCalculate.substring(length - 1);
                    if(buttonValue.equals("π"))
                        dataToCalculate = begin + "(" + "Math.PI" + end;
                    else
                        dataToCalculate = begin + "(" + buttonValue + end;
                } else {
                    dataToCalculate += buttonValue;
                }
            } else {
                dataToCalculate += buttonValue;
            }

            solutionTextView.setText(dataToCalculate);
        }


    }

    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            try {
                double resultValue = Double.parseDouble(finalResult);
                DecimalFormat decimalFormat = new DecimalFormat("#.########");
                finalResult = decimalFormat.format(resultValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }
}