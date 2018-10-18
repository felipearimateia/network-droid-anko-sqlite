package org.androidbh.networkdroid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import org.androidbh.networkdroid.database.ContactDao
import org.androidbh.networkdroid.database.database

class MainActivity : AppCompatActivity() {

    private lateinit var dao: ContactDao
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewManager = LinearLayoutManager(this)

        this.dao = ContactDao(database)

        listView?.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = ContactAdapter(ArrayList(), dao)
        }

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadAndShowData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun loadAndShowData() {
        GlobalScope.launch(Dispatchers.Main) {
            val dataset = GlobalScope.async(Dispatchers.IO) { dao.getAll() }.await()
            val viewAdapter = ContactAdapter(ArrayList(dataset), dao)
            listView?.apply {
                adapter = viewAdapter
            }
        }
    }
}
