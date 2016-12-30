package tsyki.java_conf.gr.jp.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AnotherCalcActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private EditText numberInput1;
    private EditText numberInput2;

    private Spinner operatorSelector;
    private TextView calcResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_calc);

        numberInput1 = (EditText) findViewById(R.id.numberInput1);
        numberInput1.addTextChangedListener(this);
        numberInput2 = (EditText) findViewById(R.id.numberInput2);
        numberInput2.addTextChangedListener(this);

        operatorSelector = (Spinner) findViewById(R.id.operatorSelector);
        calcResult = (TextView) findViewById(R.id.calcResult);

        findViewById(R.id.backButton).setOnClickListener(this);
    }

    private boolean isInputted() {
        return !TextUtils.isEmpty(numberInput1.getText().toString()) && !TextUtils.isEmpty(numberInput2.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        refreshResult();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton:
                if (!isInputted()) {
                    setResult(RESULT_CANCELED);
                    break;
                }
                int result = calc();
                Intent returnResultData = new Intent();
                returnResultData.putExtra("result", result);
                setResult(RESULT_OK, returnResultData);
                break;
        }
        finish();
    }

    private void refreshResult() {
        if (isInputted()) {
            int result = calc();
            calcResult.setText(String.valueOf(result));
        } else {
            calcResult.setText("計算結果");
        }
    }

    private int calc() {
        String input1 = numberInput1.getText().toString();
        String input2 = numberInput2.getText().toString();
        int number1 = Integer.parseInt(input1);
        int number2 = Integer.parseInt(input2);
        // XXX positionで見るのイマイチ。getSelectedItemでStringが取れる？
        int operator = operatorSelector.getSelectedItemPosition();
        switch (operator) {
            case 0:
                return number1 + number2;
            case 1:
                return number1 - number2;
            case 2:
                return number1 * number2;
            case 3:
                return number1 / number2;
            default:
                throw new IllegalStateException();
        }
    }
}
