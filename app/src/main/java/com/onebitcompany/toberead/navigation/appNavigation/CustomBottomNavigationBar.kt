package com.onebitcompany.toberead.navigation.appNavigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onebitcompany.toberead.ui.theme.Primary
import com.onebitcompany.toberead.ui.theme.PrimaryLight

@Composable
fun CustomBottomNavigationBar(
    bottomBarState: MutableState<Boolean>,
    currentScreenId: String,
    onItemSelected: (AppScreen) -> Unit
) {
    val bottomItems = AppScreen.BottomBarItems.ItemList
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            bottomItems.forEach { item ->
                CustomBottomNavItem(item = item, isSelected = item.route == currentScreenId) {
                    onItemSelected(item)
                }
            }
        }
    }

}

@Composable
fun CustomBottomNavItem(item: AppScreen, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor =
        if (isSelected) PrimaryLight.copy(alpha = 0.1f) else Color.Transparent
    val contentColor =
        if (isSelected) Primary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .wrapContentHeight()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = item.icon ?: Icons.Outlined.Settings,
                contentDescription = null,
                tint = contentColor
            )
            AnimatedVisibility(visible = isSelected) {
                Text(text = item.title ?: "", color = contentColor)
            }
        }
    }
}

@Preview
@Composable
fun ItemPreview() {
    CustomBottomNavItem(item = AppScreen.HOME, isSelected = true) {}
}