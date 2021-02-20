package ua.nure.notes.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import ua.nure.notes.databinding.ActivitySplashBinding
import ua.nure.notes.ui.list.NoteListActivity

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNextPageTimeout()
    }

    private fun setupNextPageTimeout() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, NoteListActivity::class.java))
            finish()
        }, 1500)
    }
}
