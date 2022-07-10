package com.example.projekakhir

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projekakhir.MainActivity.Companion.adapterrv
import com.example.projekakhir.MainActivity.Companion.arrayrv
import com.google.gson.Gson
import kotlinx.android.synthetic.main.notes.*

class TambahNotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)
        val pos = intent.getIntExtra("POSITION",-1)

        if (pos != -1){
            judul_noteslyt.setText(arrayrv[pos].judul_data)
            catatan_noteslyt.setText(arrayrv[pos].catatan_data)
        }

        btnSaveNotes.setOnClickListener{
            val judul =judul_noteslyt.text.toString()
            val catatan = catatan_noteslyt.text.toString()


            if (pos != -1){
                arrayrv[pos] = DataClass(judul, catatan)
                adapterrv.notifyItemChanged(pos)
            }
            else{
                arrayrv.add(DataClass(judul, catatan))
                adapterrv.notifyItemInserted(arrayrv.size)
            }

            saveData()
            finish()

        }
        btnBack.setOnClickListener{
            finish()
        }
        btnDeleteNotes.setOnClickListener {
            arrayrv.removeAt(pos)
            adapterrv.notifyItemRemoved(pos)
            saveData()
            finish()
        }
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
}






