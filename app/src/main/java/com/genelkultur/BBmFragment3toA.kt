package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_b_b_m_fragment1to_a.*
import kotlinx.android.synthetic.main.fragment_b_bm_fragment3to_a.*


class BBmFragment3toA : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_bm_fragment3to_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bilgi:String?=null
        arguments?.let {
            bilgi=BBmFragment3toAArgs.fromBundle(it).bilgi
            bilgi?.let { k->
                getDataGuncelBilgi(k)
            }
            //çek...
        }
        imageView1.setOnClickListener {
            bilgi?.let { k->
                getDataGuncelBilgi(k)
            }
        }
        imageView2.setOnClickListener {
            bilgi?.let { k->
                getDataGuncelBilgi(k)
            }
        }
    }

    fun getDataGuncelBilgi(bilgi:String){
        //konu=Guncel
        val ref_t = Firebase.database.getReference("BunlariBiliyormusunuz/Guncel/Guncel")//firebase den yolumu belirledim.
           ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
               override fun onDataChange(snapshot: DataSnapshot) {
                    val rnd = (0..snapshot.childrenCount-1).random().toInt()//0 dan bilgi sayisina kadar random üret
                    val ss = snapshot.children.toList()[rnd] //listele
                    val data = ss.child("data").getValue().toString() //seç
                    textViewG.text=data //yapıştır

                }

               override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }

            })
        }


    }




