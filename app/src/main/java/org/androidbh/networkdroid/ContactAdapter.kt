package org.androidbh.networkdroid

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_contact.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.androidbh.networkdroid.database.ContactDao
import org.androidbh.networkdroid.entity.Contact

class ContactAdapter(private val dataset: ArrayList<Contact>, private val dao: ContactDao) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView = view.nameView
        val emailView = view.emailView
        val btnDelte = view.btnDelete
        val contactView = view.contactView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false) as View
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = dataset[position]
        holder.nameView?.text = contact.name
        holder.emailView?.text = contact.email
        holder.btnDelte.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                dao.delete(contact.id)
            }

            dataset.remove(contact)
            notifyItemRemoved(position)
        }
        holder.contactView.setOnClickListener {
            val intent = Intent(it.context, AddContactActivity::class.java)
            intent.putExtra("contact",contact)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataset.size
}