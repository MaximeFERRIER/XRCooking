package fr.droidfactory.xrcooking

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import fr.droidfactory.xrcooking.ui.presentation.XRCookingNavigation
import fr.droidfactory.xrcooking.ui.theme.XRCookingTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            XRCookingTheme {
                XRCookingNavigation()
            }
        }
    }
}