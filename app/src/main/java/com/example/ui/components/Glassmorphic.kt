package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassWhite

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(24.dp),
    color: Color = GlassWhite,
    borderColor: Color = GlassBorder,
    borderWidth: Dp = 1.dp,
    elevation: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape, spotColor = Color(0x33000000), ambientColor = Color(0x11000000))
            .clip(shape)
            .background(color)
            .border(width = borderWidth, color = borderColor, shape = shape)
    ) {
        content()
    }
}
