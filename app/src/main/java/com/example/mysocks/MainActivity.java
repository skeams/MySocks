package com.example.mysocks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * Helper class Category.
     *
     * Holds buttons and fields for each budget category.
     */
    private class Category {
        String title;
        Integer initValue;
        TextView amount;
        TextView juster;
        Button add;
        Button sub;

        Category(String title, Integer initValue, final TextView amount, final TextView juster, Button add, Button sub) {
            this.title = title;
            this.initValue = initValue;
            this.amount = amount;
            this.juster = juster;
            this.add = add;
            this.sub = sub;

            this.amount.setText(initValue.toString());
            this.amount.setKeyListener(null);
            this.juster.setText("0");

            this.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer addValue = Integer.parseInt(juster.getText().toString());
                    Integer currentValue = Integer.parseInt(amount.getText().toString());
                    Integer newValue = currentValue + addValue;
                    amount.setText(newValue.toString());
                    juster.setText("0");
                    closeKeyBoard();
                    juster.clearFocus();
                    updateSUM();
                    save();
                }
            });

            this.sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer subValue = Integer.parseInt(juster.getText().toString());
                    Integer currentValue = Integer.parseInt(amount.getText().toString());
                    Integer newValue = currentValue - subValue;
                    amount.setText(newValue.toString());
                    juster.setText("0");
                    closeKeyBoard();
                    juster.clearFocus();
                    updateSUM();
                    save();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initValues();
    }

    private TextView SUMAmount;
    private List<Category> categories;

    /**
     * Initialize buttons and fields
     */
    private void initValues() {
        categories = new ArrayList<>();
        categories.add(
            new Category("Mat", 2000,
                    (TextView) findViewById(R.id.MatAmount),
                    (TextView) findViewById(R.id.MatJuster),
                    (Button) findViewById(R.id.MatAdd),
                    (Button) findViewById(R.id.MatSub)
            )
        );
        categories.add(
                new Category("Drikke", 2000,
                        (TextView) findViewById(R.id.DrikkeAmount),
                        (TextView) findViewById(R.id.DrikkeJuster),
                        (Button) findViewById(R.id.DrikkeAdd),
                        (Button) findViewById(R.id.DrikkeSub)
                )
        );
        categories.add(
                new Category("Ute", 2000,
                        (TextView) findViewById(R.id.UteAmount),
                        (TextView) findViewById(R.id.UteJuster),
                        (Button) findViewById(R.id.UteAdd),
                        (Button) findViewById(R.id.UteSub)
                )
        );
        categories.add(
                new Category("Annet", 1000,
                        (TextView) findViewById(R.id.AnnetAmount),
                        (TextView) findViewById(R.id.AnnetJuster),
                        (Button) findViewById(R.id.AnnetAdd),
                        (Button) findViewById(R.id.AnnetSub)
                )
        );

        SUMAmount = (TextView) findViewById(R.id.SUMAmount);
        SUMAmount.setKeyListener(null);
        updateSUM();
    }

    /**
     * Closes Keyboard
     */
    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Updates SUM field
     */
    private void updateSUM() {
        Integer sum = 0;
        for (Category c : categories) {
            sum += Integer.parseInt(c.amount.getText().toString());
        }
        SUMAmount.setText(sum.toString());
    }

    /**
     * Store data
     */
    private void save() {
        // TODO Implement
        // TODO Implement
        // TODO Implement
        // TODO Implement    ---------------- AND LOADING
        // TODO Implement
        // TODO Implement
        // TODO Implement
    }

}
