package com.raulespim.testing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.raulespim.testing.feature.login.presentation.LoginScreen
import com.raulespim.testing.ui.theme.TestingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestingTheme {
                Surface {
                    LoginScreen()
                }
            }
        }
    }
}


