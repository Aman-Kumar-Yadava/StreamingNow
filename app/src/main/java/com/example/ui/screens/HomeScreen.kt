package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.MediaItem
import com.example.ui.components.AnimatedGradientBackground
import com.example.ui.components.GlassCard
import com.example.ui.theme.TextHighContrast
import com.example.ui.theme.TextMediumContrast

@Composable
fun HomeScreen(
    trendingMovies: List<MediaItem>,
    trendingTv: List<MediaItem>,
    popularMovies: List<MediaItem>,
    topRatedMovies: List<MediaItem>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedGradientBackground()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp) // Space for bottom nav
        ) {
            item {
                TopSection()
            }
            
            item {
                WelcomeSection()
            }
            
            item {
                QuickCategories()
            }
            
            if (trendingMovies.isNotEmpty()) {
                item {
                    SectionHeader("Trending Movies")
                    com.example.ui.components.FocusCarousel(items = trendingMovies)
                }
            }
            
            if (trendingTv.isNotEmpty()) {
                item {
                    SectionHeader("Trending TV Shows")
                    com.example.ui.components.FocusCarousel(items = trendingTv)
                }
            }
            
            if (popularMovies.isNotEmpty()) {
                item {
                    SectionHeader("Popular Movies")
                    com.example.ui.components.FocusCarousel(items = popularMovies)
                }
            }
            
            if (topRatedMovies.isNotEmpty()) {
                item {
                    SectionHeader("Top Rated")
                    com.example.ui.components.FocusCarousel(items = topRatedMovies)
                }
            }
        }
    }
}

@Composable
fun TopSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlassCard(
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = TextHighContrast)
            }
        }
        
        GlassCard(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = TextMediumContrast)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Search Movies, TV Shows...", color = TextMediumContrast, style = MaterialTheme.typography.bodyMedium)
            }
        }
        
        GlassCard(
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp
        ) {
            Box {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = TextHighContrast)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.error)
                )
            }
        }
    }
}

@Composable
fun WelcomeSection() {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Text(
            text = "Welcome Back! 👋",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Discover Movies",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
            color = TextHighContrast
        )
        Text(
            text = "& TV Shows",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
            color = TextHighContrast
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Explore thousands of movies and TV shows from TMDB",
            style = MaterialTheme.typography.bodyMedium,
            color = TextMediumContrast
        )
    }
}

@Composable
fun QuickCategories() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CategoryCard(title = "Movies", subtitle = "Discover", weight = 1f)
        CategoryCard(title = "TV Shows", subtitle = "Explore", weight = 1f)
    }
}

@Composable
fun RowScope.CategoryCard(title: String, subtitle: String, weight: Float) {
    GlassCard(
        modifier = Modifier
            .weight(weight)
            .height(80.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, color = TextHighContrast)
            Text(text = subtitle, fontSize = 12.sp, color = TextMediumContrast)
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = TextHighContrast
        )
        Text(
            text = "View All →",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { }
        )
    }
}


