package com.example.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.MediaItem
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FocusCarousel(items: List<MediaItem>, modifier: Modifier = Modifier) {
    if (items.isEmpty()) return
    
    val pagerState = rememberPagerState(
        initialPage = items.size / 2,
        pageCount = { items.size }
    )

    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxWidth()
            .height(380.dp),
        contentPadding = PaddingValues(horizontal = 64.dp), // Leaves room for adjacent items
        pageSpacing = 16.dp
    ) { page ->
        val item = items[page]
        val pageOffset = (
            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
        ).absoluteValue

        // Animate the scale based on the offset from the center
        val scale by animateFloatAsState(
            targetValue = if (pageOffset == 0f) 1f else 0.85f,
            animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f),
            label = "scale"
        )
        
        val alpha by animateFloatAsState(
            targetValue = if (pageOffset == 0f) 1f else 0.7f,
            animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f),
            label = "alpha"
        )
        
        val elevation by animateDpAsState(
            targetValue = if (pageOffset == 0f) 16.dp else 4.dp,
            animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f),
            label = "elevation"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                },
            contentAlignment = Alignment.Center
        ) {
            FocusCarouselItem(item = item, elevation = elevation, isFocused = pageOffset == 0f)
        }
    }
}

@Composable
fun FocusCarouselItem(item: MediaItem, elevation: androidx.compose.ui.unit.Dp, isFocused: Boolean) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${item.posterPath}"
    
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f/3f),
        shape = RoundedCornerShape(32.dp),
        elevation = elevation
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = item.displayTitle,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            
            // Rating Chip
            if (item.voteAverage != null && item.voteAverage > 0) {
                GlassCard(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x66000000), // Darker glass for contrast against image
                    borderWidth = 0.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", item.voteAverage),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
