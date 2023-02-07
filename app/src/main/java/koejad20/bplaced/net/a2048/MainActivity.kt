package koejad20.bplaced.net.a2048

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.start).setOnClickListener{
            val intent = Intent(this, Game::class.java)
            intent.putExtra("difficulty", difficulty)
            intent.putExtra("highScore", highScore)
            startActivity(intent)
        }
    }

    companion object {
        var difficulty: String = "normal"
        var highScore: Int = 0
    }
}