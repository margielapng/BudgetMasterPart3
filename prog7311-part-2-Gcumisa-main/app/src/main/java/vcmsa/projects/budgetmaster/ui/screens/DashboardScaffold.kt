package vcmsa.projects.BudgetMaster.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vcmsa.projects.BudgetMaster.activities.*
import vcmsa.projects.BudgetMaster.ui.components.DashboardHomeScreen

// Define all the screens accessible from the dashboard
enum class DashboardScreen {
    Home, Add, Categories, Expenses, Graphs
}

@Composable
fun DashboardScaffold(username: String) {
    // Keeps track of which screen is currently selected in the navigation bar
    var selectedScreen by remember { mutableStateOf(DashboardScreen.Home) }

    // Scaffold layout provides a structure for consistent layouting (e.g., bottom bar)
    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedScreen) { selectedScreen = it }
        }
    ) { innerPadding ->
        // Load the screen content based on current selection
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedScreen) {
                DashboardScreen.Home -> DashboardHomeScreen(username) // Home dashboard
                DashboardScreen.Add -> AddExpenseScreenNav() // Add new expense
                DashboardScreen.Categories -> CategoryScreenNav() // Manage categories
                DashboardScreen.Expenses -> ViewExpensesScreenNav() // View expense list
                DashboardScreen.Graphs -> GraphScreenNav() // Graphs and insights
            }
        }
    }
}

@Composable
fun BottomNavigationBar(current: DashboardScreen, onSelected: (DashboardScreen) -> Unit) {
    // Bottom navigation bar with labeled icons for each screen
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = current == DashboardScreen.Home,
            onClick = { onSelected(DashboardScreen.Home) },
            label = { Text("Home") },
            icon = { Text("🏠") }
        )
        NavigationBarItem(
            selected = current == DashboardScreen.Add,
            onClick = { onSelected(DashboardScreen.Add) },
            label = { Text("Add") },
            icon = { Text("➕") }
        )
        NavigationBarItem(
            selected = current == DashboardScreen.Categories,
            onClick = { onSelected(DashboardScreen.Categories) },
            label = { Text("Categories") },
            icon = { Text("🗂️") }
        )
        NavigationBarItem(
            selected = current == DashboardScreen.Expenses,
            onClick = { onSelected(DashboardScreen.Expenses) },
            label = { Text("Expenses") },
            icon = { Text("📊") }
        )
        NavigationBarItem(
            selected = current == DashboardScreen.Graphs,
            onClick = { onSelected(DashboardScreen.Graphs) },
            label = { Text("Graphs") },
            icon = { Text("📈") }
        )
    }
}

@Composable
fun CategoryScreenNav() {
    // Loads the screen responsible for managing and displaying categories
    AddCategoryScreenNav()
}
