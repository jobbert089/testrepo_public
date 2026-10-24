package com.example.welcomeapp

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var startScreen: View
    private lateinit var gameView: GameView
    private lateinit var wonText: TextView

    private val endGame = Runnable {
        wonText.visibility = View.GONE
        startScreen.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startScreen = findViewById(R.id.startScreen)
        gameView = findViewById(R.id.gameView)
        wonText = findViewById(R.id.wonText)

        findViewById<Button>(R.id.startButton).setOnClickListener { startGame() }

        findViewById<Button>(R.id.helpButton).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.help)
                .setMessage(R.string.help_text)
                .setPositiveButton(R.string.ok, null)
                .show()
        }

        findViewById<Button>(R.id.quitButton).setOnClickListener {
            // Schließt die Activity und entfernt die App aus der Liste der letzten Apps
            finishAndRemoveTask()
        }

        gameView.onHit = {
            gameView.visibility = View.GONE
            wonText.visibility = View.VISIBLE
            // Nach 5 Sekunden ist das Spiel beendet, zurück zum Startbildschirm
            wonText.postDelayed(endGame, WON_DISPLAY_MS)
        }
    }

    private fun startGame() {
        startScreen.visibility = View.GONE
        gameView.visibility = View.VISIBLE
        gameView.start()
    }

    override fun onDestroy() {
        wonText.removeCallbacks(endGame)
        gameView.stop()
        super.onDestroy()
    }

    companion object {
        private const val WON_DISPLAY_MS = 5000L
    }
}
