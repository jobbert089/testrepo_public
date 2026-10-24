package com.example.welcomeapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.hypot
import kotlin.random.Random

/**
 * Zeigt einen roten Kreis, der jede Sekunde an eine neue zufällige Stelle springt,
 * bis er getroffen wird.
 */
class GameView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    var onHit: (() -> Unit)? = null

    // Fingergroß: 80 dp Durchmesser (ca. 1,3 cm)
    private val radius = 40 * resources.displayMetrics.density
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.RED }

    private var circleX = 0f
    private var circleY = 0f
    private var running = false

    private val moveCircle = object : Runnable {
        override fun run() {
            if (width == 0 || height == 0) {
                // Noch nicht gelayoutet, gleich nochmal versuchen
                post(this)
                return
            }
            circleX = randomCoordinate(width)
            circleY = randomCoordinate(height)
            invalidate()
            postDelayed(this, TIME_PER_CIRCLE_MS)
        }
    }

    fun start() {
        running = true
        removeCallbacks(moveCircle)
        post(moveCircle)
    }

    fun stop() {
        running = false
        removeCallbacks(moveCircle)
        invalidate()
    }

    private fun randomCoordinate(size: Int): Float {
        val max = (size - radius).coerceAtLeast(radius)
        return radius + Random.nextFloat() * (max - radius)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (running) {
            canvas.drawCircle(circleX, circleY, radius, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (running && event.actionMasked == MotionEvent.ACTION_DOWN &&
            hypot(event.x - circleX, event.y - circleY) <= radius
        ) {
            performClick()
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        stop()
        onHit?.invoke()
        return true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks(moveCircle)
    }

    companion object {
        private const val TIME_PER_CIRCLE_MS = 1000L
    }
}
