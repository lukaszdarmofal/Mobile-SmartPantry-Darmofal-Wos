package com.example.mobile_smart_pantry_project_iv

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobile_smart_pantry_project_iv.Model.Product
import com.example.mobile_smart_pantry_project_iv.databinding.ActivityMainBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val products = loadProducts()
        val adapter = PantryAdapter(products)

        binding.listView.adapter = adapter

        binding.filterFood.setOnClickListener {
            val filteredProducts = filterFood()
            val adapter = PantryAdapter(filteredProducts)

            binding.listView.adapter = adapter
        }

        binding.filterOxygen.setOnClickListener {
            val filteredProducts = filterOxygen()
            val adapter = PantryAdapter(filteredProducts)

            binding.listView.adapter = adapter
        }

        binding.filterNone.setOnClickListener {
            val products = loadProducts()
            val adapter = PantryAdapter(products)

            binding.listView.adapter = adapter
        }

        binding.searchName.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                binding.filterNone.isChecked = true
                val name: String = binding.searchName.query.toString()
                val products = searchName(name)
                val adapter = PantryAdapter(products)


                binding.listView.adapter = adapter

                return false
            }

        })
    }

    private fun searchName(name: String): MutableList<Product> {
        val inputStream = resources.openRawResource(R.raw.pantry)
        val reader = InputStreamReader(inputStream)
        val jsonString = reader.readText()

        val fullList = Json.decodeFromString<List<Product>>(jsonString).toMutableList()
        val filteredList = mutableListOf<Product>()

        fullList.forEach {
            if(it.nazwa.lowercase().contains(name.lowercase())) {
                filteredList.add(it)
            }
        }

        return filteredList
    }

    private fun filterFood(): MutableList<Product> {
        val inputStream = resources.openRawResource(R.raw.pantry)
        val reader = InputStreamReader(inputStream)
        val jsonString = reader.readText()

        val fullList = Json.decodeFromString<List<Product>>(jsonString).toMutableList()
        val filteredList = mutableListOf<Product>()

        fullList.forEach {
            if(it.kategoria == "Żywność") {
                filteredList.add(it)
            }
        }

        return filteredList
    }

    private fun filterOxygen(): MutableList<Product> {
        val inputStream = resources.openRawResource(R.raw.pantry)
        val reader = InputStreamReader(inputStream)
        val jsonString = reader.readText()

        val fullList = Json.decodeFromString<List<Product>>(jsonString).toMutableList()
        val filteredList = mutableListOf<Product>()

        fullList.forEach {
            if(it.kategoria == "Tlen") {
                filteredList.add(it)
            }
        }

        return filteredList
    }


    private fun loadProducts(): MutableList<Product> {

        val inputStream = resources.openRawResource(R.raw.pantry)
        val reader = InputStreamReader(inputStream)
        val jsonString = reader.readText()

        return Json.decodeFromString<List<Product>>(jsonString).toMutableList()
    }


}