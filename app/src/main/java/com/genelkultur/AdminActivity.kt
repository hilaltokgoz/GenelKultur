package com.genelkultur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        radioButtonBBM.setOnClickListener {
            if (radioButtonBBM.isChecked){
                ll_bunlari_biliyormusun.visibility= View.VISIBLE
                ll_omu_bumu.visibility=View.GONE
                ll_test.visibility=View.GONE
            }
        }
        radioButtonOB.setOnClickListener {
            if (radioButtonOB.isChecked){
                ll_bunlari_biliyormusun.visibility= View.GONE
                ll_omu_bumu.visibility=View.VISIBLE
                ll_test.visibility=View.GONE
            }
        }
        radioButtonT.setOnClickListener {
            if (radioButtonT.isChecked){
                ll_bunlari_biliyormusun.visibility= View.GONE
                ll_omu_bumu.visibility=View.GONE
                ll_test.visibility=View.VISIBLE
            }
        }
    }
}