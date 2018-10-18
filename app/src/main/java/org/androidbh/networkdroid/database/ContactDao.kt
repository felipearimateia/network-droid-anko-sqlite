package org.androidbh.networkdroid.database

import org.androidbh.networkdroid.entity.Contact
import org.jetbrains.anko.db.*

class ContactDao(private val database: AppDatabaseOpenHelper) {

    fun insert(contact: Contact) {
        database.use {
            insert("Contact",
                "name" to contact.name,
                "phone" to contact.phone,
                "email" to contact.email)
        }
    }

    fun getAll(): List<Contact> {
        return database.use {
            select("Contact").parseList(classParser())
        }
    }

    fun getWithName(name: String): Contact {
        return database.use {
            select("Contact")
                .whereArgs("name = {contactName}",
                    "contactName" to name).
                    parseSingle(classParser())
        }
    }

    fun delete(contactId: Int) {
        database.use {
            delete("Contact",
                "id = {contactID}",
                "contactID" to contactId)
        }
    }

    fun update(contact: Contact) {
        database.use {
            update("Contact", "name" to contact.name,
                "phone" to contact.phone,
                "email" to contact.email)
                .whereSimple("id = ?", contact.id.toString())
                .exec()
        }
    }
}