package com.cbellmont.ejercicioadapterstarwars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var adapter : FilmsAdapter = FilmsAdapter()
    private lateinit var model :MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        createRecyclerView()
        downloadAll()
    }

    private fun createRecyclerView() {
        film_recycler_view.layoutManager = LinearLayoutManager(this)
        film_recycler_view.adapter = adapter
    }


    private fun downloadAll(){
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0 until model.filmsSize()) {
                val film = loadFilmInBackground(i)
                setTextOnMainThread(film)
            }
            hideProgressBar()
        }

    }

    private suspend fun loadFilmInBackground(position : Int) : Film {
        return withContext(Dispatchers.IO) {
            return@withContext model.getFilm(position)
        }
    }

    private suspend fun setTextOnMainThread(filmsList: Film) {
        withContext(Dispatchers.Main) {
            adapter.addFilmToList(filmsList)
        }
    }

    private fun hideProgressBar(){
        pbLoading.visibility = View.GONE
    }
}