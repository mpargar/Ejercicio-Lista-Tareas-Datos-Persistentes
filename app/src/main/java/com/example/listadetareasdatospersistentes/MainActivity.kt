package com.example.listadetareasdatospersistentes

import TodoItems.TodoDb
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
    var todoDbController:TodoDb.Controller? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoDbController = TodoDb.Controller(this)
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
                var elemento = todoItems?.find { it.id == data?.getIntExtra("ID", -1)!!}
                elemento?.title = data?.getStringExtra("TITLE")!!
                elemento?.message = data?.getStringExtra("MESSAGE")!!
                elemento?.date = data?.getStringExtra("DATE")!!
                elemento?.imageUri = data?.getStringExtra("IMAGE_URI")!!
            }
            adapter?.notifyDataSetChanged()
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            // Si algo falla
        }
    }

    fun clickEdit(item:TodoItemData) {
        startActivityForResult(ActivitiesHelper().openEditTodo(this, item), ActivitiesHelper().OPEN_EDIT_TODO_RID)
    }

    /* SetUp fuctions */
    fun getTodos(){
        /*
        todoItems.add(
            TodoItemData(1, "Entrega proyecto final", "Esta debe ser la descripción de la tarea a realizar.", "03/11/2020", "https://culcobcs.com/wp-content/uploads/2019/03/UABCS-1.jpg")
        )
        todoItems.add(
            TodoItemData(2, "Entrega proyecto final", "Esta debe ser la descripción de la tarea a realizar.", "03/11/2020", "https://www.princot.com/wp-content/uploads/2020/06/cuadros-decorativos-canvas-800x800.jpg")
        )
        */
        todoItems = todoDbController!!.getTodos()
    }

    fun initTodoRecycler() {
        adapter = TodoListAdapter(todoItems, this, ::clickEdit)
        vRecyclerTodos.layoutManager = LinearLayoutManager(this)
        vRecyclerTodos.adapter = adapter
    }
}