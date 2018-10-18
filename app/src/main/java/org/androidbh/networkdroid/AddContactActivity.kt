package org.androidbh.networkdroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_contact.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.androidbh.networkdroid.database.ContactDao
import org.androidbh.networkdroid.database.database
import org.androidbh.networkdroid.entity.Contact

class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        val contact = getContact()

        contact.let {
            editName.setText(it.name)
            editEmail.setText(it.email)
            editPhone.setText(it.phone)
        }

        btnOk.setOnClickListener {
            val dao = ContactDao(database)


            contact.apply {
                name = editName.text.toString()
                email = editEmail.text.toString()
                phone = editPhone.text.toString()
            }


            if (contact.id > 0) {
                GlobalScope.launch(Dispatchers.IO){ dao.update(contact) }
            } else {
                GlobalScope.launch(Dispatchers.IO){ dao.insert(contact) }
            }

            finish()
        }


    }

    private fun getContact(): Contact {
        return this.intent.getParcelableExtra("contact") ?: Contact(0,"", "","")
    }
}
