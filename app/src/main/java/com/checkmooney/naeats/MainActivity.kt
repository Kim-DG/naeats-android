@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.checkmooney.naeats

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.checkmooney.naeats.databinding.ContentMainBinding
import com.checkmooney.naeats.ui.theme.mainText
import com.google.android.material.navigation.NavigationView
import java.lang.reflect.Type
import java.time.format.TextStyle

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ContentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ContentMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val navView: NavigationView = viewBinding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        setContent {
            TodayRecommend()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodayRecommend() {
    Scaffold() {
        Column() {
            Text(
                text = "오늘의 추천 메뉴",
                style = mainText,
            )
        }
    }
}
