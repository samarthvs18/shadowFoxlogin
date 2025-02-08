package com.example.shadowfoxlogin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    private EditText display;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;
    private double secondOperand = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        display = findViewById(R.id.display);

        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        Button btnDivide = findViewById(R.id.btnDivide);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btnMinus = findViewById(R.id.btnMinus);
        Button btn0 = findViewById(R.id.btn0);
        Button btnDot = findViewById(R.id.btnDot);
        Button btnEquals = findViewById(R.id.btnEquals);
        Button btnPlus = findViewById(R.id.btnPlus);
        Button btnCube = findViewById(R.id.btnCube);
        Button btnPercent = findViewById(R.id.btnPercent);
        Button btnSquare = findViewById(R.id.btnSquare);
        Button btnSqrt = findViewById(R.id.btnSqrt);
        Button btnReset = findViewById(R.id.btnReset);
        Button  btnBack = findViewById(R.id.btnBack);

        btn7.setOnClickListener(v -> appendNumber("7"));
        btn8.setOnClickListener(v -> appendNumber("8"));
        btn9.setOnClickListener(v -> appendNumber("9"));
        btn4.setOnClickListener(v -> appendNumber("4"));
        btn5.setOnClickListener(v -> appendNumber("5"));
        btn6.setOnClickListener(v -> appendNumber("6"));
        btn1.setOnClickListener(v -> appendNumber("1"));
        btn2.setOnClickListener(v -> appendNumber("2"));
        btn3.setOnClickListener(v -> appendNumber("3"));
        btn0.setOnClickListener(v -> appendNumber("0"));
        btnDot.setOnClickListener(v -> appendNumber("."));

        btnPlus.setOnClickListener(v -> setOperator("+"));
        btnMinus.setOnClickListener(v -> setOperator("-"));
        btnMultiply.setOnClickListener(v -> setOperator("*"));
        btnDivide.setOnClickListener(v -> setOperator("/"));
        btnPercent.setOnClickListener(v -> setOperator("%"));
        btnSquare.setOnClickListener(v -> calculateSquare());
        btnCube.setOnClickListener(v -> calculateCube());
        btnSqrt.setOnClickListener(v -> calculateSquareRoot());

        btnEquals.setOnClickListener(v -> calculateResult());
        btnReset.setOnClickListener(v -> resetCalculator());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculatorActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void appendNumber(String number) {
        currentInput += number;
        display.setText(currentInput);
    }

    private void setOperator(String op) {
        if (currentInput.isEmpty()) return;
        firstOperand = Double.parseDouble(currentInput);
        currentInput = "";
        operator = op;
    }

    private void calculateResult() {
        if (currentInput.isEmpty()) return;
        secondOperand = Double.parseDouble(currentInput);
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
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case "%":
                result = (firstOperand * secondOperand) / 100;
                break;
        }

        display.setText(String.valueOf(result));
        currentInput = String.valueOf(result);
        operator = "";
    }

    private void calculateSquare() {
        if (currentInput.isEmpty()) return;
        double result = Math.pow(Double.parseDouble(currentInput), 2);
        display.setText(String.valueOf(result));
        currentInput = String.valueOf(result);
    }

    private void calculateCube() {
        if (currentInput.isEmpty()) return;
        double result = Math.pow(Double.parseDouble(currentInput), 3);
        display.setText(String.valueOf(result));
        currentInput = String.valueOf(result);
    }

    private void calculateSquareRoot() {
        if (currentInput.isEmpty()) return;
        double result = Math.sqrt(Double.parseDouble(currentInput));
        display.setText(String.valueOf(result));
        currentInput = String.valueOf(result);
    }

    private void resetCalculator() {
        currentInput = "";
        display.setText("");
        operator = "";
        firstOperand = 0;
        secondOperand = 0;
    }
}
