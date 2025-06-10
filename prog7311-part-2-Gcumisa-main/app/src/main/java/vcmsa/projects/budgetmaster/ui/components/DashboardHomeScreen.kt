package vcmsa.projects.BudgetMaster.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vcmsa.projects.BudgetMaster.R
import vcmsa.projects.BudgetMaster.activities.LogInActivity
import vcmsa.projects.BudgetMaster.data.AppDatabase
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardHomeScreen(username: String) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }

    val currentMonth = remember {
        SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())
    }

    var fullList by remember { mutableStateOf(emptyList<CategorySummary>()) }
    var visibleSummaries by remember { mutableStateOf(emptyList<CategorySummary>()) }
    var showDialogFor by remember { mutableStateOf<CategorySummary?>(null) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val expenses = db.expenseDao().getAllExpenses().filter { it.date.startsWith(currentMonth) }
            val categories = db.categoryDao().getAllCategories()
            val summaries = mutableListOf<CategorySummary>()

            categories.forEach { cat ->
                val spent = expenses.filter { it.categoryId == cat.id }.sumOf { it.amount }
                summaries.add(
                    CategorySummary(
                        categoryName = cat.name,
                        spent = spent,
                        minBudget = cat.minBudget,
                        maxBudget = cat.maxBudget
                    )
                )
            }

            fullList = summaries
            visibleSummaries = summaries.take(4)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50))
            .padding(16.dp)
    ) {
        // Top logo
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Welcome text and Logout button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Welcome, ${username.replaceFirstChar { it.uppercase() }}",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = {
                    val intent = Intent(context, LogInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("🚪 Logout", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Show summaries
        visibleSummaries.forEach {
            CategoryWidget(
                summary = it,
                onClick = { showDialogFor = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Edit/Remove Dialog
        showDialogFor?.let { selected ->
            var newMaxBudget by remember { mutableStateOf(selected.maxBudget.toString()) }

            AlertDialog(
                onDismissRequest = { showDialogFor = null },
                title = { Text("Edit or Remove ${selected.categoryName}") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newMaxBudget,
                            onValueChange = { newMaxBudget = it },
                            label = { Text("New Max Budget (R)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("To remove this wedge from the dashboard, tap Remove below.")
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        val updatedBudget = newMaxBudget.toDoubleOrNull()
                        if (updatedBudget != null) {
                            visibleSummaries = visibleSummaries.map {
                                if (it.categoryName == selected.categoryName)
                                    it.copy(maxBudget = updatedBudget)
                                else it
                            }
                            showDialogFor = null
                        }
                    }) {
                        Text("💾 Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        visibleSummaries = visibleSummaries.filterNot { it.categoryName == selected.categoryName }
                        showDialogFor = null
                    }) {
                        Text("➖ Remove")
                    }
                }
            )
        }
    }
}

@Composable
fun CategoryWidget(summary: CategorySummary, onClick: () -> Unit) {
    val progress = if (summary.maxBudget > 0) {
        (summary.spent / summary.maxBudget).toFloat().coerceAtMost(1f)
    } else 0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("📅 This Month: ${summary.categoryName}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("💸 Spent: R%.2f".format(summary.spent))
            Text("🎯 Min Budget: R%.2f".format(summary.minBudget))
            Text("🎯 Max Budget: R%.2f".format(summary.maxBudget))

            Spacer(modifier = Modifier.height(8.dp))

            // Progress bar to show budget usage
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = Color(0xFF2196F3),
                trackColor = Color.LightGray
            )
        }
    }
}

// Updated summary model to hold min and max budget
data class CategorySummary(
    val categoryName: String,
    val spent: Double,
    val minBudget: Double,
    val maxBudget: Double
)



