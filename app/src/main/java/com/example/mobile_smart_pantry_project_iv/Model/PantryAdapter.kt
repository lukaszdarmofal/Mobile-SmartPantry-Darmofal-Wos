package com.example.mobile_smart_pantry_project_iv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mobile_smart_pantry_project_iv.Model.Product

class PantryAdapter(
    private val products: MutableList<Product>
) : BaseAdapter() {

    override fun getCount(): Int = products.size

    override fun getItem(position: Int): Any = products[position]

    override fun getItemId(position: Int): Long = products[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_product, parent, false)

        val product = products[position]

        val txtName = view.findViewById<TextView>(R.id.txtName)
        val txtCategory = view.findViewById<TextView>(R.id.txtCategory)
        val txtQuantity = view.findViewById<TextView>(R.id.txtQuantity)
        val btnIncrease = view.findViewById<Button>(R.id.btnIncrease)
        val btnDecrease = view.findViewById<Button>(R.id.btnDecrease)

        txtName.text = product.nazwa
        txtCategory.text = product.kategoria
        txtQuantity.text = "Ilość: ${product.ilosc} ${product.jednostka}"

        btnIncrease.setOnClickListener {
            product.ilosc++
            notifyDataSetChanged()
        }

        btnDecrease.setOnClickListener {
            if (product.ilosc > 0) {
                product.ilosc--
                notifyDataSetChanged()
            }
        }

        when {
            product.ilosc < 3 -> {
                txtQuantity.setTextColor(android.graphics.Color.RED)
            }
            product.ilosc in 3..5 -> {
                txtQuantity.setTextColor(android.graphics.Color.YELLOW)
            }
            else -> {
                txtQuantity.setTextColor(android.graphics.Color.WHITE)
            }
        }

        return view
    }
}