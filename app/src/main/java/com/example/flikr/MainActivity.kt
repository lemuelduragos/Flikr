package com.example.flikr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.flikr.databinding.ActivityMainBinding
import com.example.flikr.views.FragmentFavorites
import com.example.flikr.views.FragmentPhotos
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.title = "Photos"
        supportActionBar?.title = "Photos"

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FragmentPhotos()).commit()

        binding.bottomNavigation.setOnItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment: Fragment? = null
            when (item.itemId) {
            R.id.photos -> {
                selectedFragment = FragmentPhotos()
                actionBar?.title = "Photos"
                supportActionBar?.title = "Photos"
            }
            R.id.favorites -> {
                selectedFragment = FragmentFavorites()
                actionBar?.title = "Favorites"
                supportActionBar?.title = "Favorites"
            }
        }

        if (selectedFragment == null) return true
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
        return true
    }
}