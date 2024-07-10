package dev.argraur.yandex.todo.data.network

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import dev.argraur.yandex.todo.data.network.dto.TodoItemDTO
import dev.argraur.yandex.todo.data.network.dto.TodoItemListDTO

interface TodoService {
    @GET("list")
    suspend fun getTodoItems(): TodoItemListDTO

    @PATCH("list")
    suspend fun updateTodoItemList(@Body list: TodoItemListDTO, @Header("X-Last-Known-Revision") revision: Int): TodoItemListDTO

    @POST("list")
    suspend fun addTodoItem(@Body item: TodoItemDTO, @Header("X-Last-Known-Revision") revision: Int): TodoItemDTO

    @PUT("list/{id}")
    suspend fun updateTodoItem(@Body item: TodoItemDTO, @Path("id") id: String = item.element.id, @Header("X-Last-Known-Revision") revision: Int): TodoItemDTO

    @GET("list/{id}")
    suspend fun getTodoItemById(@Path("id") id: String): TodoItemDTO

    @DELETE("list/{id}")
    suspend fun deleteTodoItemById(@Path("id") id: String, @Header("X-Last-Known-Revision") revision: Int): TodoItemDTO
}