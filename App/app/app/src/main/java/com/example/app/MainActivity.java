package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//Veronika Martinova
//Calculator
public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);

        int[] numberButtonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener numberClickListener = v -> {
            Button btn = (Button) v;
            if (isNewOperation) {
                currentInput = "";
                isNewOperation = false;
            }
            currentInput += btn.getText().toString();
            textViewResult.setText(currentInput);
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(numberClickListener);
        }

        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> setOperator("/"));

        findViewById(R.id.btnEquals).setOnClickListener(v -> calculateResult());

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            currentInput = "";
            operator = "";
            firstOperand = 0;
            isNewOperation = true;
            textViewResult.setText("0");
        });
    }

    private void setOperator(String op) {
        try {
            firstOperand = Double.parseDouble(currentInput);
        } catch (NumberFormatException e) {
            firstOperand = 0;
        }
        operator = op;
        isNewOperation = true;
    }

    private void calculateResult() {
        try {
            double secondOperand = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        textViewResult.setText("Error");
                        return;
                    }
                    break;
            }

            // Parāda bez komatiem, ja iespējams
            if (result == (int) result) {
                textViewResult.setText(String.valueOf((int) result));
            } else {
                textViewResult.setText(String.valueOf(result));
            }

            currentInput = String.valueOf(result);
            isNewOperation = true;

        } catch (NumberFormatException e) {
            textViewResult.setText("Error");
        }
    }
}