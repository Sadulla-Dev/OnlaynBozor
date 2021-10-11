 package com.example.onlayndars.screen.cart.makeOrder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.onlayndars.MapsActivity
import com.example.onlayndars.R
import com.example.onlayndars.model.AddressModel
import com.example.onlayndars.model.ProductModel
import com.example.onlayndars.screen.cart.MainViewModel
import com.example.onlayndars.utils.Constants
import kotlinx.android.synthetic.main.activity_make_order.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

 class MakeOrderActivity : AppCompatActivity() {
     var address: AddressModel? = null
     lateinit var items: List<ProductModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_order)

        items = intent.getSerializableExtra(Constants.EXTRA_DATA) as List<ProductModel>

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
        tvTotalAmount.setText(items.sumByDouble { it.cartCount.toDouble() * (it.price.replace(" ", "").toDoubleOrNull() ?: 0.0) }.toString())

        edAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        cardViewBack.setOnClickListener {
            finish()
        }

        btnMakeOrder.setOnClickListener {
            if (edAddress.length() > 0){
                Toast.makeText(this, "Mallumot ko'rilmoqda", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Joyingizni kiriting", Toast.LENGTH_SHORT).show()
            }


        }


    }
     
     override fun onDestroy() {
         super.onDestroy()
         if (EventBus.getDefault().isRegistered(this)){
             EventBus.getDefault().unregister(this)
         }
     }

     @Subscribe
     fun onEvent(address: AddressModel){
         this.address = address
         edAddress.setText("${address.latitude}, ${address.longitude}")
     }
}