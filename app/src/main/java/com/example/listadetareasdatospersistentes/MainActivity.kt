package com.example.listadetareasdatospersistentes

import TodoItems.TodoItemData
import TodoItems.TodoListAdapter
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import helpers.ActivitiesHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var todoItems:MutableList<TodoItemData> = ArrayList()
    var adapter:TodoListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setTitle("Lista de tareas")
        vAdd.setOnClickListener {
            startActivityForResult(ActivitiesHelper().openAddTodo(this), ActivitiesHelper().OPEN_ADD_TODO_RID)
        }
        /* Obtener información*/
        getTodos()
        initTodoRecycler()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) { // Si no falla
            if(requestCode == ActivitiesHelper().OPEN_ADD_TODO_RID) {
                todoItems.add(
                    TodoItemData(
                        todoItems.size,
                        data?.getStringExtra("TITLE")!!,
                        data?.getStringExtra("MESSAGE")!!,
                        data?.getStringExtra("DATE")!!,
                        data?.getStringExtra("IMAGE_URI")!!
                    )
                )
            } else if(requestCode == ActivitiesHelper().OPEN_EDIT_TODO_RID) {
                //
            }
            println("=======================")
            println(requestCode)
            println(resultCode)




            println("=======================")
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            // Si algo falla
        }

    }

    /* SetUp fuctions */
    fun getTodos(){
        todoItems.add(
            TodoItemData(1, "Entrega proyecto final", "Esta debe ser la descripción de la tarea a realizar.", "03/11/2020", "https://culcobcs.com/wp-content/uploads/2019/03/UABCS-1.jpg")
        )
        todoItems.add(
            TodoItemData(2, "Entrega proyecto final", "Esta debe ser la descripción de la tarea a realizar.", "03/11/2020", "https://culcobcs.com/wp-content/uploads/2019/03/UABCS-1.jpg")
        )
    }
    fun initTodoRecycler() {
        adapter = TodoListAdapter(todoItems, this)
        vRecyclerTodos.layoutManager = LinearLayoutManager(this)
        vRecyclerTodos.adapter = adapter
    }
}