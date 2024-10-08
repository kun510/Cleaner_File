package com.kun510.cleaner

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.EventNote
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.VolunteerActivism
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kun510.cleaner.data.datastore.DataStore
import com.kun510.cleaner.data.model.ui.navigation.BottomNavigationScreen
import com.kun510.cleaner.data.model.ui.navigation.NavigationDrawerItem
import com.kun510.cleaner.ui.appmanager.AppManagerComposable
import com.kun510.cleaner.ui.help.HelpActivity
import com.kun510.cleaner.ui.home.HomeComposable
import com.kun510.cleaner.ui.imageoptimizer.imagepicker.ImagePickerActivity
import com.kun510.cleaner.ui.memory.MemoryManagerComposable
import com.kun510.cleaner.ui.settings.SettingsActivity
import com.kun510.cleaner.utils.IntentUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainComposable() {
    val bottomBarItems: List<BottomNavigationScreen> = listOf(
        BottomNavigationScreen.Home,
        BottomNavigationScreen.AppManager,
        BottomNavigationScreen.MemoryManager
    )
    val drawerItems = listOf(

        NavigationDrawerItem(
            title = R.string.image_optimizer, selectedIcon = Icons.Outlined.Image
        ),

        NavigationDrawerItem(
            title = R.string.settings,
            selectedIcon = Icons.Outlined.Settings,
        ),
        NavigationDrawerItem(
            title = R.string.help_and_feedback,
            selectedIcon = Icons.AutoMirrored.Outlined.HelpOutline,
        ),
        NavigationDrawerItem(
            title = R.string.updates,
            selectedIcon = Icons.AutoMirrored.Outlined.EventNote,
        ),
        NavigationDrawerItem(
            title = R.string.share, selectedIcon = Icons.Outlined.Share
        ),
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope: CoroutineScope = rememberCoroutineScope()
    val navController: NavHostController = rememberNavController()
    val context: Context = LocalContext.current
    val dataStore: DataStore = DataStore.getInstance(context)
    val selectedItemIndex: Int by rememberSaveable { mutableIntStateOf(-1) }
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet {
            Spacer(modifier = Modifier.height(16.dp))
            drawerItems.forEachIndexed { index, item ->
                val title: String = stringResource(item.title)
                NavigationDrawerItem(label = { Text(text = title) },
                    selected = index == selectedItemIndex,
                    onClick = {
                        when (item.title) {

                            R.string.image_optimizer -> {
                                IntentUtils.openActivity(
                                    context, ImagePickerActivity::class.java
                                )
                            }

                            R.string.settings -> {
                                IntentUtils.openActivity(
                                    context, SettingsActivity::class.java
                                )
                            }

                            R.string.help_and_feedback -> {
                                IntentUtils.openActivity(
                                    context, HelpActivity::class.java
                                )
                            }

                            R.string.updates -> {
                                IntentUtils.openUrl(
                                    context,
                                    "https://github.com/D4rK7355608/${context.packageName}/blob/master/CHANGELOG.md"
                                )
                            }

                            R.string.share -> {
                                IntentUtils.shareApp(context)
                            }
                        }
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            item.selectedIcon, contentDescription = title
                        )
                    },
                    badge = {
                        item.badgeCount?.let {
                            Text(text = item.badgeCount.toString())
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))
                if (item.title == R.string.image_optimizer) {
                    HorizontalDivider(modifier = Modifier.padding(8.dp))
                }
            }
        }

    }, content = {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(text = stringResource(R.string.app_name))
            }, navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(id = R.string.navigation_drawer_open)
                    )
                }
            }, actions = {
                IconButton(onClick = {
                    //IntentUtils.openActivity(context, SupportActivity::class.java)
                }) {
                    Icon(
                        Icons.Outlined.VolunteerActivism,
                        contentDescription = stringResource(id = R.string.support_us)
                    )
                }
            })
        }, bottomBar = {
            Column {
                NavigationBar {
                    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
                    val currentRoute: String? = navBackStackEntry?.destination?.route
                    bottomBarItems.forEach { screen ->
                        NavigationBarItem(icon = {
                            val iconResource: ImageVector =
                                if (currentRoute == screen.route) screen.selectedIcon else screen.icon
                            Icon(iconResource, contentDescription = null)
                        },
                            label = { Text(stringResource(screen.title)) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            })
                    }
                }
            }
        }) { innerPadding ->
            NavHost(navController, startDestination = BottomNavigationScreen.Home.route) {
                composable(BottomNavigationScreen.Home.route) {
                    Box(modifier = Modifier.padding(innerPadding)) {
                        HomeComposable()
                    }
                }
                composable(BottomNavigationScreen.AppManager.route) {
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppManagerComposable()
                    }
                }
                composable(BottomNavigationScreen.MemoryManager.route) {
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MemoryManagerComposable()
                    }
                }
            }
        }
    })
}