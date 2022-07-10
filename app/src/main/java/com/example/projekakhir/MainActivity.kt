package com.example.projekakhir

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var  recyclerView: RecyclerView
        lateinit var adapterrv: CustomAdapter
        lateinit var arrayrv: ArrayList<DataClass>
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
        buatAdapter()
        buatRecyclerView()
        saveData()
        addAndDeleteNotes()
        noteCLick()

        search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                search(p0.toString())
            }


        })

    }

    private fun search(text: String){
        val filterredList = ArrayList<DataClass>()

        for (item in arrayrv){
            if(item.judul_data.lowercase().contains(text.lowercase())){
                filterredList.add(item)
            }
        }
        adapterrv.filteredData(filterredList)
    }

    private fun loadData() {
        val save = getSharedPreferences("shareData", Context.MODE_PRIVATE)
        val gson = Gson()
        arrayrv = ArrayList()
        val empty = gson.toJson(arrayrv)
        val json = save.getString("json", empty)
        val type = object : TypeToken<ArrayList<DataClass>>() {}.type
        arrayrv = gson.fromJson(json, type)

    }

    private fun saveData() {
        val save = getSharedPreferences("shareData", Context.MODE_PRIVATE)
        val editor = save.edit()
        val gson = Gson()
        val json = gson.toJson(arrayrv)

        editor.apply {
            putString("json", json)
            apply()
        }
    }


    private fun buatRecyclerView(){
        recyclerView = rv_notes
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapterrv
    }

    private fun buatAdapter(){
        adapterrv = CustomAdapter(arrayrv)
    }

    private fun addAndDeleteNotes(){
        fabAddNotes.setOnClickListener{
            Intent(this@MainActivity, TambahNotesActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun noteCLick(){
        adapterrv.setItemClick(object :CustomAdapter.itemClick{
            override fun click(position: Int){
                Intent(this@MainActivity, TambahNotesActivity::class.java).also {
                    it.putExtra("POSITION", position)
                    startActivity(it)
                }
            }
        })
    }

}

