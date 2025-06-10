<<<<<<< HEAD


=======
>>>>>>> a14a3f66584254bbc80f9c2309501317e4f79d21
Budget Master - Expense Tracker App

Project Overview:

Budget Master is an Android application developed using Kotlin and Jetpack Compose. The app assists users in managing their monthly budgets by allowing them to set a minimum and maximum budget for expense categories, record their expenses with details, and visualize their spending trends using graphs. This project was completed as part of a group assignment.

Group Members:

Khulekani Kubheka- ST10033782

Amahle Gcumisa- ST10315126


Technologies Used: 

Kotlin (Programming Language) 

Jetpack Compose (User Interface Framework) 

Room Database (Local Database Management) 

Coroutine Scopes (Asynchronous Programming) 

MPAndroidChart Library (Data Visualization) 

Android Studio (Development Environment) 

GitHub (Version Control)


Features: 

User Authentication (Sign Up and Login) 

Set a global Monthly Budget Goal Create Expense 

Categories with: Minimum Budget Maximum Budget Record 

Expenses with:

- Description
- Amount
- Date and Time
- Category Association
- Optional Photo Attachment

View all recorded expenses 

Edit and Delete expenses 

View categorized total spending 

Filter expenses by a selected date range 

Generate graphs to visualize category-based expenses 

Logout functionality 

Splash screen on app startup


How to Build and Run Clone the repository: 



Open the project in Android Studio. 

Let Gradle sync the dependencies automatically. 

Connect an emulator or an Android device (minimum API level 24). 

Run the application.


Project Structure: 

Folder Description:

activities folder contains activities like LoginActivity, SignUpActivity, SplashActivity, DashboardActivity, and others. data Includes Room database setup, entity models, and DAO interfaces. models Entity classes for User, Expense, Category, and BudgetGoal. ui/components Reusable Composable components like the Dashboard home screen. ui/screens Individual Composable screens for Add Expense, Add Category, View Expenses, Graphs, and Edit Expense. ui/navigation Handles the bottom navigation bar setup. ui/theme Application themes and styling configurations.


Code Documentation and Comments The codebase includes: 

Inline comments explaining the purpose of classes, functions, and key logic. Clear variable naming conventions. Toast messages for user feedback on key actions such as saving, updating, or deleting data. Proper coroutine usage for database operations to ensure smooth UI responsiveness. Special attention was given to maintaining readability and structure throughout the project.


Video Demonstration: https://youtu.be/kYM5e7zYA1E?si=d-oZ3_v8jLIah2kw


Final Notes Budget Master demonstrates best practices in mobile application development, including clean architecture, user-centered design, and efficient data management using Room. This application was built as part of a practical group project to reinforce the development of full Android applications using modern frameworks and technologies.
