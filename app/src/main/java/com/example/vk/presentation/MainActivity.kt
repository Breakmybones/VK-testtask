package com.example.vk.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vk.R
import com.example.vk.databinding.ActivityMainBinding
import com.example.vk.presentation.fragment.DialogType
import com.example.vk.presentation.fragment.HomeFragment

class MainActivity : AppCompatActivity(), DialogType.TypeInputListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState != null) {
            return
        }

        supportFragmentManager.beginTransaction()
            .add(
                R.id.container_of_fragments,
                HomeFragment.newInstance(Bundle()),
                HomeFragment.HOME_FRAGMENT_TAG
            ).commit()
    }

    override fun onTypeInputted(name: String) {
        val homeFragment = supportFragmentManager.findFragmentById(R.id.container_of_fragments) as HomeFragment
        homeFragment.getFilesTypes(name)
    }
}