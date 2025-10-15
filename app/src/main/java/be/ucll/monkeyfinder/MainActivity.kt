package be.ucll.monkeyfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import be.ucll.monkeyfinder.ui.MonkeyFinderApp
import be.ucll.monkeyfinder.ui.theme.MonkeyFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonkeyFinderTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MonkeyFinderApp()
                }
            }
        }
    }
}