package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.GlassCard
import com.example.ui.theme.TextHighContrast
import com.example.ui.theme.TextMediumContrast
import com.example.viewmodel.AuthState
import com.example.viewmodel.StreamViewModel

@Composable
fun MainScreen(viewModel: StreamViewModel = viewModel()) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        bottomBar = {
            AnimatedVisibility(
                visible = authState is AuthState.Authenticated,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp)
                        .navigationBarsPadding(),
                    contentAlignment = Alignment.Center
                ) {
                    GlassCard(
                        shape = RoundedCornerShape(32.dp),
                        elevation = 8.dp,
                        color = Color(0x33FFFFFF)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BottomNavItem(
                                title = "Home",
                                icon = Icons.Default.Home,
                                selected = currentRoute == "home" || currentRoute == null,
                                onClick = { navController.navigate("home") { launchSingleTop = true } }
                            )
                            BottomNavItem(
                                title = "Discover",
                                icon = Icons.Default.PlayCircle,
                                selected = currentRoute == "discover",
                                onClick = { }
                            )
                            BottomNavItem(
                                title = "Downloads",
                                icon = Icons.Default.Download,
                                selected = currentRoute == "downloads",
                                onClick = { }
                            )
                            BottomNavItem(
                                title = "Profile",
                                icon = Icons.Default.Person,
                                selected = currentRoute == "profile",
                                onClick = { }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "auth_check",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("auth_check") {
                LaunchedEffect(authState) {
                    when (authState) {
                        is AuthState.Authenticated -> navController.navigate("home") { popUpTo("auth_check") { inclusive = true } }
                        is AuthState.Unauthenticated -> navController.navigate("onboarding") { popUpTo("auth_check") { inclusive = true } }
                        else -> {}
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) // Empty while checking
            }
            
            composable("onboarding") {
                OnboardingScreen(
                    authState = authState,
                    onValidate = { viewModel.validateAndSaveApiKey(it) },
                    onResetError = { viewModel.resetAuthError() }
                )
            }
            
            composable("home") {
                val trendingMovies by viewModel.trendingMovies.collectAsStateWithLifecycle()
                val trendingTv by viewModel.trendingTv.collectAsStateWithLifecycle()
                val popularMovies by viewModel.popularMovies.collectAsStateWithLifecycle()
                val topRatedMovies by viewModel.topRatedMovies.collectAsStateWithLifecycle()
                
                HomeScreen(
                    trendingMovies = trendingMovies,
                    trendingTv = trendingTv,
                    popularMovies = popularMovies,
                    topRatedMovies = topRatedMovies
                )
            }
        }
    }
}

@Composable
fun BottomNavItem(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val tint = if (selected) MaterialTheme.colorScheme.primary else TextMediumContrast
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = title, tint = tint)
        }
        Text(text = title, fontSize = 10.sp, color = tint, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
    }
}
