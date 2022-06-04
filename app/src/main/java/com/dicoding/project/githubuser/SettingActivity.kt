package com.dicoding.project.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.project.githubuser.data.local.PrefHelper
import com.dicoding.project.githubuser.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingBinding
    private val pref by lazy { PrefHelper(this) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.action_bar_setting)

        binding.switchDark.isChecked = pref.getBoolean("Dark_Mode")
    
        binding.switchDark.setOnCheckedChangeListener { compoundButton, isChecked ->
            when(isChecked){
                true -> {
                    pref.put("Dark_Mode", true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                false -> {
                    pref.put("Dark_Mode", false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
}