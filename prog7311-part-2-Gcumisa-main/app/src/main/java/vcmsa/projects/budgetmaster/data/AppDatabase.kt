package vcmsa.projects.BudgetMaster.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vcmsa.projects.BudgetMaster.models.Category
import vcmsa.projects.BudgetMaster.models.Expense
import vcmsa.projects.BudgetMaster.models.BudgetGoal

// Main Room database for the BudgetMaster app
@Database(
    entities = [User::class, Category::class, Expense::class, BudgetGoal::class],
    version = 6
)
abstract class AppDatabase : RoomDatabase() {

    // DAO access methods for the database
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun budgetGoalDao(): BudgetGoalDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        // Singleton pattern to ensure only one instance of the database is created
        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "BudgetMaster_db"
                )
                    .fallbackToDestructiveMigration() // Rebuild database on schema changes
                    .build()
                    .also { instance = it }
            }
    }
}

