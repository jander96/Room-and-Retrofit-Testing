package com.example.roomtesting

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.roomtesting.room.Item
import com.example.roomtesting.room.ItemDao
import com.example.roomtesting.room.ItemDb
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    private lateinit var itemDao: ItemDao
    private lateinit var itemDb: ItemDb
    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        itemDb = Room.inMemoryDatabaseBuilder(context, ItemDb::class.java).build()
        itemDao = itemDb.itemDao()
    }
    @After
    fun cleanup(){
        itemDb.close()

    }


    @Test
    fun addItems_shouldReturn_theSameItems()= runTest{
        val item1 = Item(id = 1, itemName = "item 1")
        val item2 = Item(id = 2, itemName = "item 2")
        itemDao.addItem(item1)
        itemDao.addItem(item2)
       val items: List<Item> =  itemDao.getAllItems().first()
        assert(items.contains(item1))
        assert(items.contains(item2))
    }
}
