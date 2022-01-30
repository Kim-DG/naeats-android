package com.checkmooney.naeats

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.checkmooney.naeats.components.BottomNavigationBar
import com.checkmooney.naeats.components.NaEatsScaffold
import com.checkmooney.naeats.databinding.ContentMainBinding
import com.checkmooney.naeats.ui.theme.NaEatsTheme
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ContentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ContentMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}
