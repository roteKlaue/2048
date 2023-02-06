package koejad20.bplaced.net.a2048;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import koejad20.bplaced.net.a2048.bl.Game2048;

public class Game extends AppCompatActivity {
    private final Button[][] buttons = new Button[4][4];
    private final Game2048 engine = new Game2048();
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        engine.start();
        gestureDetector = new GestureDetector(this, new GesturesSUS(this));


        int[] ids = {R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15, R.id.button16};
        for (int i = 0; i < ids.length; i++) {
            Button sus = findViewById(ids[i]);
            sus.setText("");
            sus.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
            buttons[i / 4][i % 4] = sus;
        }

        findViewById(R.id.imageButton).setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Reset")
                .setMessage("Do you really want to reset the current game?")
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    dialog.dismiss();
                    engine.start();
                    update();
                }).setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss())
                .show());

        update();
    }

    public Game2048 getEngine() {
        return engine;
    }

    public void update() {
        int[][] a = engine.getGrid();
        for(int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if(a[i][j] == 0) {
                    buttons[i][j].setText("");
                    continue;
                }
                buttons[i][j].setText(String.format(Locale.ENGLISH, "%d", a[i][j]));
            }
        }

        if (engine.isOver()) {
            new AlertDialog.Builder(this)
                    .setTitle("Game Over")
                    .setMessage(engine.isWon()? "You Won.": "You Lost.")
                    .setPositiveButton(R.string.backToMainMenu, (dialog, which) -> {
                        dialog.dismiss();
                        finish();
                    })
                    .show();
        }
    }
}