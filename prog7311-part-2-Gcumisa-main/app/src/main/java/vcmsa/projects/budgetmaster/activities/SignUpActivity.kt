package vcmsa.projects.BudgetMaster.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vcmsa.projects.BudgetMaster.R
import vcmsa.projects.BudgetMaster.data.AppDatabase
import vcmsa.projects.BudgetMaster.data.User

// Activity for user registration
class SignUpActivity : AppCompatActivity() {

    // Declare view fields
    private lateinit var nameField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var confirmPasswordField: EditText
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Bind layout views
        nameField = findViewById(R.id.editTextName)
        emailField = findViewById(R.id.editTextEmail)
        passwordField = findViewById(R.id.editTextPassword)
        confirmPasswordField = findViewById(R.id.editTextConfirmPassword)
        signupButton = findViewById(R.id.buttonSignup)

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        // Register logic on button click
        signupButton.setOnClickListener {
            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            // Input validation
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Launch coroutine to save new user
            lifecycleScope.launch(Dispatchers.IO) {
                val existingUser = userDao.getUserByEmail(email)
                if (existingUser != null) {
                    runOnUiThread {
                        Toast.makeText(this@SignUpActivity, "User already exists", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Create and insert new user
                    userDao.insertUser(User(name = name, email = email, password = password))
                    runOnUiThread {
                        Toast.makeText(this@SignUpActivity, "Account created!", Toast.LENGTH_SHORT).show()

                        // Redirect to login
                        val intent = Intent(this@SignUpActivity, LogInActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}

