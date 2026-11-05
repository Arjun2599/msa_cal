package com.example.calc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView display;
    private double first = 0;
    private String op = "";
    private boolean newNumber = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);
    }

    public void onButton(View v) {
        String t = ((Button) v).getText().toString();
        String shown = display.getText().toString();

        if (shown.equals("Error")) {
            reset();
            shown = "0";
        }

        if (t.matches("[0-9]")) {
            if (newNumber || shown.equals("0")) {
                display.setText(t);
            } else {
                display.append(t);
            }
            newNumber = false;
        } else if (t.equals(".")) {
            if (newNumber) {
                display.setText("0.");
                newNumber = false;
            } else if (!shown.contains(".")) {
                display.append(".");
            }
        } else if (t.equals("C")) {
            reset();
        } else if (t.equals("=")) {
            calculate();
            op = "";
            newNumber = true;
        } else {
            if (!op.isEmpty() && !newNumber) {
                calculate();
            }
            first = parse(display.getText().toString());
            op = t;
            newNumber = true;
        }
    }

    private void calculate() {
        if (op.isEmpty()) return;
        double second = parse(display.getText().toString());
        double result = 0;

        switch (op) {
            case "+": result = first + second; break;
            case "\u2212": result = first - second; break;
            case "\u00d7": result = first * second; break;
            case "\u00f7":
                if (second == 0) {
                    display.setText("Error");
                    op = "";
                    newNumber = true;
                    return;
                }
                result = first / second;
                break;
        }

        first = result;
        if (result == (long) result) {
            display.setText(String.valueOf((long) result));
        } else {
            display.setText(String.valueOf(result));
        }
    }

    private double parse(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0;
        }
    }

    private void reset() {
        display.setText("0");
        first = 0;
        op = "";
        newNumber = true;
    }
}
