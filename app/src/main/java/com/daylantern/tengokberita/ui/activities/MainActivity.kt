package com.daylantern.tengokberita.ui.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.daylantern.tengokberita.Constants
import com.daylantern.tengokberita.R
import com.daylantern.tengokberita.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "Tengok Berita"
        setupNavigation()
        setupBottomNavView()
        showHideBottomNav()
        showHideToolbar()
    }

    private fun showHideBottomNav() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {
                R.id.searchFragment, R.id.articleSelectedFragment -> binding.bottomNav.visibility = View.GONE
                else -> binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    private fun showHideToolbar() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {
                R.id.searchFragment -> binding.toolbar.visibility = View.GONE
                else -> binding.toolbar.visibility = View.VISIBLE
            }
        }
    }

    private fun setupBottomNavView() {
        binding.bottomNav.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.bookmarkFragment, R.id.settingsFragment))
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.homeFragment -> {
                navController.navigate(R.id.homeFragment)
                true
            }
            R.id.bookmarkFragment -> {
                navController.navigate(R.id.bookmarkFragment)
                true
            }
            R.id.settingsFragment -> {
                navController.navigate(R.id.settingsFragment)
                true
            }
            else -> false
        }
    }

    private fun setupNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.edit().remove(Constants.SHARED_PREF_NAME).apply()
    }
}