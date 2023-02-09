package koejad20.bplaced.net.a2048;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import koejad20.bplaced.net.a2048.bl.Colors;
import koejad20.bplaced.net.a2048.bl.Directions;
import koejad20.bplaced.net.a2048.bl.EasySwipeDetector;
import koejad20.bplaced.net.a2048.bl.Game2048;

public class Game extends AppCompatActivity {
    private final Button[][] buttons = new Button[4][4];
    private final Game2048 engine = new Game2048();
    private TextView score;

    private final static Map<Integer, Colors> map = new HashMap<>(){{
        put(0, Colors.A1);
        put(2, Colors.A2);
        put(4, Colors.A3);
        put(8, Colors.A4);
        put(16, Colors.A5);
        put(32, Colors.A6);
        put(64, Colors.A7);
        put(128, Colors.A8);
        put(256, Colors.A9);
        put(512, Colors.A10);
        put(1024, Colors.A11);
        put(2048, Colors.A12);
    }};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        score = findViewById(R.id.score);
        final EasySwipeDetector easySwipeDetector = new EasySwipeDetector(this, 100, 1) {

            @Override
            public void onSwipeLeft(float distanceX, float distanceY) {
                engine.move(Directions.LEFT);
                Toast.makeText(Game.this, "LEFT", Toast.LENGTH_SHORT).show();
                update();
            }

            @Override
            public void onSwipeRight(float distanceX, float distanceY) {
                engine.move(Directions.RIGHT);
                Toast.makeText(Game.this, "RIGHT", Toast.LENGTH_SHORT).show();
                update();
            }

            @Override
            public void onSwipeDown(float distanceX, float distanceY) {
                engine.move(Directions.DOWN);
                Toast.makeText(Game.this, "DOWN", Toast.LENGTH_SHORT).show();
                update();
            }

            @Override
            public void onSwipeUp(float distanceX, float distanceY) {
                engine.move(Directions.UP);
                Toast.makeText(Game.this, "UP", Toast.LENGTH_SHORT).show();
                update();
            }
        };

        engine.start();
        //gestureDetector = new GestureDetector(this, new GesturesSUS(this));

        int[] ids = {R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15, R.id.button16};
        for (int i = 0; i < ids.length; i++) {
            Button sus = findViewById(ids[i]);
            sus.setText("");
            buttons[i / 4][i % 4] = sus;
        }

        findViewById(R.id.controller).setOnTouchListener(easySwipeDetector::onTouch);

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
                buttons[i][j].setBackgroundColor(Color.parseColor(Objects.requireNonNull(map.get(a[i][j])).getRgb()));
                score.setText(String.format(Locale.ENGLISH, "%d", engine.getScore()));
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
                    .setMessage(String.format(Locale.ENGLISH, "You %s. Your Score: %d\n", engine.isWon()? "Won": "Lost", engine.getScore()))
                    .setPositiveButton(R.string.backToMainMenu, (dialog, which) -> {
                        dialog.dismiss();
                        finish();
                    })
                    .show();
        }
    }
}