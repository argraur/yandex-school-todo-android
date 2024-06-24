package dev.argraur.yandex.todo.data.source

import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.model.Urgency
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat
import java.util.UUID

class MockTodoItemsDataSource {
    private var mockTodoItems = listOf(
        TodoItem(
            UUID.randomUUID().toString(),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            Urgency.Urgent,
            false,
            creationDate = LocalDate(2024, 6, 20),
            deadline = LocalDate(2024, 6, 22)
        ),
        TodoItem(
            UUID.randomUUID().toString(),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            Urgency.Low,
            false,
            creationDate = LocalDate(2024, 4, 17),
            deadline = LocalDate(2024, 6, 20)
        ),
        TodoItem(
            UUID.randomUUID().toString(),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            Urgency.Normal,
            false,
            creationDate = LocalDate(2024, 4, 18)
        ),
        TodoItem(
            UUID.randomUUID().toString(),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            Urgency.Normal,
            false,
            creationDate = LocalDate(2024, 6, 17),
            deadline = LocalDate(2024, 6, 22)
        ),
        TodoItem(
            UUID.randomUUID().toString(),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            Urgency.Normal,
            false,
            creationDate = LocalDate(2023, 4, 17)
        ),
        TodoItem(
            UUID.randomUUID().toString(),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            Urgency.Urgent,
            false,
            creationDate = LocalDate(2024, 5, 18)
        ),
        TodoItem(
            UUID.randomUUID().toString(),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            Urgency.Normal,
            false,
            creationDate = LocalDate(2024, 6, 10)
        ),
        TodoItem(
            UUID.randomUUID().toString(),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            Urgency.Normal,
            false,
            creationDate = LocalDate(2024, 4, 25)
        ),
        TodoItem(
            UUID.randomUUID().toString(),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            Urgency.Normal,
            false,
            creationDate = LocalDate(2024, 3, 17)
        ),
        TodoItem(
            UUID.randomUUID().toString(),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            Urgency.Normal,
            false,
            creationDate = LocalDate(2024, 2, 17)
        )
    )

    fun getTodoItems(): List<TodoItem> =
        mockTodoItems

    fun addTodoItem(item: TodoItem): Boolean {
        mockTodoItems = mockTodoItems + item
        return true
    }

    fun updateTodoItem(item: TodoItem): Boolean {
        mockTodoItems = mockTodoItems - mockTodoItems.first { it.id == item.id } + item
        return true
    }

    fun removeTodoItem(id: String): Boolean {
        mockTodoItems = mockTodoItems - mockTodoItems.first { it.id == id }
        return true
    }
}