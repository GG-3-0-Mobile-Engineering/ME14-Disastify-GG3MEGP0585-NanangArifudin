package com.arfdn.disastify.presentation.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.arfdn.disastify.R
import com.arfdn.disastify.databinding.ActivityProfileBinding
import com.arfdn.disastify.utils.bindActivity
import com.arfdn.disastify.utils.getThemePreference
import com.arfdn.disastify.utils.setThemePreference

class ProfileActivity : AppCompatActivity() {
    private val binding by bindActivity(ActivityProfileBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val theme = getThemePreference(this@ProfileActivity)
        binding.optLight.apply {
            isChecked = theme=="light"
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) toggleTheme("dark")
            }
        }
        binding.optDark.apply {
            isChecked = theme=="dark"
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) toggleTheme("light")
            }
        }

    }

    fun toggleTheme(theme: String) {
        when (theme) {
            "light" -> {
                setThemePreference(this@ProfileActivity,"dark")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }
            "dark" -> {
                setThemePreference(this@ProfileActivity,"light")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }
    }
}

