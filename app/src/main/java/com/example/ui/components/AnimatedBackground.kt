package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.Cyan
import com.example.ui.theme.Lavender
import com.example.ui.theme.LightOrange
import com.example.ui.theme.Peach
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SoftPink
import com.example.ui.theme.SoftPurple
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnimatedGradientBackground(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "gradient")
    
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val radius = width.coerceAtLeast(height)

        val xOffset = cos(Math.toRadians(angle.toDouble())).toFloat() * radius * 0.5f
        val yOffset = sin(Math.toRadians(angle.toDouble())).toFloat() * radius * 0.5f

        val center1 = Offset(width / 2 + xOffset, height / 2 + yOffset)
        val center2 = Offset(width / 2 - xOffset, height / 2 - yOffset)
        val center3 = Offset(width / 2 + yOffset, height / 2 - xOffset)

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Lavender, SkyBlue.copy(alpha = 0.5f), Color.Transparent),
                center = center1,
                radius = radius * 0.8f
            ),
            radius = radius * 0.8f,
            center = center1
        )
        
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(SoftPink, Peach.copy(alpha = 0.6f), Color.Transparent),
                center = center2,
                radius = radius * 0.9f
            ),
            radius = radius * 0.9f,
            center = center2
        )
        
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Cyan.copy(alpha = 0.7f), SoftPurple.copy(alpha = 0.4f), Color.Transparent),
                center = center3,
                radius = radius * 0.7f
            ),
            radius = radius * 0.7f,
            center = center3
        )
    }
}
