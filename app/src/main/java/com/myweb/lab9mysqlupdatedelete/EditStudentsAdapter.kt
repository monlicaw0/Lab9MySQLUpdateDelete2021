package com.myweb.lab9mysqlupdatedelete

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.myweb.lab9mysqlupdatedelete.databinding.EditDeleteItemLayoutBinding

class EditStudentsAdapter(val items :List<Student>, val context: Context):
    RecyclerView.Adapter<EditStudentsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View, val binding: EditDeleteItemLayoutBinding) : RecyclerView.ViewHolder(view) {
        init {
            binding.tvEditDelete.setOnClickListener {
                val item = items[adapterPosition]
                val context_v:Context = view.context
                val intent = Intent(context_v, EditDeleteActivity::class.java)
                intent.putExtra("mId",item.std_id)
                intent.putExtra("mName",item.std_name)
                intent.putExtra("mAge",item.std_age.toString())
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): EditStudentsAdapter.ViewHolder {
        val binding = EditDeleteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent,
                false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: EditStudentsAdapter.ViewHolder, position: Int) {
        val binding_holder = holder.binding
        binding_holder.tvID.text =  items[position].std_id
        binding_holder.tvName.text = items[position].std_name
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
