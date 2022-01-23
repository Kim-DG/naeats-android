package com.checkmooney.naeats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.checkmooney.naeats.databinding.ContentMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ContentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ContentMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val navView: NavigationView = viewBinding.navView
        val navController = findNavController(viewBinding.navHostFragment.id)
        navView.setupWithNavController(navController)
    }
}
