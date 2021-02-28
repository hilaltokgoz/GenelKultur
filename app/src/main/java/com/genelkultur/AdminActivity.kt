package com.genelkultur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_admin.*
import java.util.*

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        var bolum:String="Tarih"
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

        //Bolumler
        radioButtonTarih.setOnClickListener {
            bolum="Tarih"
            et_bunlari_biliyormusun_konu.setText("")
        }
        radioButtonCoğrafya.setOnClickListener {
            bolum="Cografya"
            et_bunlari_biliyormusun_konu.setText("")

        }
        radioButtonVatandaslik.setOnClickListener {
            bolum="Vatandaslik"
            et_bunlari_biliyormusun_konu.setText("")

        }
        radioButtonGuncel.setOnClickListener {
            bolum="Guncel"
            et_bunlari_biliyormusun_konu.setText("Guncel")

        }





        btn_bunlari_biliyormusun_ekle.setOnClickListener {
            val konu = et_bunlari_biliyormusun_konu.text.toString()
            val bilgi = et_bunlari_biliyormusun_bilgi.text.toString()
            var uuid =UUID.randomUUID().toString()
            val data =mapOf(
                "uid" to uuid,
                "data" to bilgi
            )
            dataWrite("BunlariBiliyormusunuz/$bolum/$konu/$uuid",data)

        }

        btn_omu_bumu_ekle.setOnClickListener {
            val question = et_omu_bumu_soru.text.toString()
            val o1=et_omu_bumu_dogru_cevap.text.toString()
            val o2=et_omu_bumu_yanlis_cevap.text.toString()

            var uuid =UUID.randomUUID().toString()
            val data =mapOf(
                "uid" to uuid,
                "question" to question,
                "o1" to o1,
                "o2" to o2
            )
            dataWrite("OmuBumu/$bolum/$uuid",data)
        }
        btn_test_ekle.setOnClickListener {
            val question = et_test_soru.text.toString()
            val cevap1=et_test_dogru_cevap.text.toString()
            val cevap2=et_test_yanlis_cevap1.text.toString()
            val cevap3=et_test_yanlis_cevap2.text.toString()
            val cevap4=et_test_yanlis_cevap3.text.toString()
            val cevap5=et_test_yanlis_cevap4.text.toString()

            var uuid =UUID.randomUUID().toString()

            val testdata =mapOf(
                "uid" to uuid,
                "question" to question,
                "cevap1" to cevap1 ,
                "cevap2" to cevap2 ,
                "cevap3" to cevap3 ,
                "cevap4" to cevap4 ,
                "cevap5" to cevap5
            )
            dataWrite("Test/$bolum/$uuid",testdata)
        }
    }
    fun dataWrite(ref: String, data: Map<String, String>){
        val dbRef=Firebase.database.reference
       dbRef.child(ref).setValue(data)
           .addOnSuccessListener {
               Toast.makeText(this, "Ekleme Başarılı.", Toast.LENGTH_SHORT).show()
               et_bunlari_biliyormusun_konu.setText("")
               et_bunlari_biliyormusun_bilgi.setText("")

               et_omu_bumu_soru.setText("")
               et_omu_bumu_dogru_cevap.setText("")
               et_omu_bumu_yanlis_cevap.setText("")

               et_test_soru.setText("")
               et_test_dogru_cevap.setText("")
               et_test_yanlis_cevap1.setText("")
               et_test_yanlis_cevap2.setText("")
               et_test_yanlis_cevap3.setText("")
               et_test_yanlis_cevap4.setText("")
           }
           .addOnFailureListener {
               Toast.makeText(this, "Başarısız ${it.message.toString()}", Toast.LENGTH_SHORT).show()
           }

    }
}