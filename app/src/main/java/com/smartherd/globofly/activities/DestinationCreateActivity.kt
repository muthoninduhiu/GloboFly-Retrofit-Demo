package com.smartherd.globofly.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Toast
import com.smartherd.globofly.R
import com.smartherd.globofly.databinding.ActivityDestinyCreateBinding
import com.smartherd.globofly.models.Destination
import com.smartherd.globofly.services.DestinationService
import com.smartherd.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationCreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDestinyCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinyCreateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        val context = this

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnAdd.setOnClickListener {
            val newDestination = Destination()
            newDestination.city = binding.etCity.text.toString()
            newDestination.description = binding.etDescription.text.toString()
            newDestination.country = binding.etCountry.text.toString()

            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall = destinationService.addDestination(newDestination)

            requestCall.enqueue(object: Callback<Destination> {

                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        var newlyCreatedDestination = response.body() // Use it or ignore it
                        Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
