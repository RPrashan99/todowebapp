import React, { useEffect, useState } from "react";
import { getTasks, addTask, deleteTask } from "./ApiService/Api";

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  useEffect(() => {
    loadTasks();
  }, []);

  const loadTasks = async () => {
    const res = await getTasks();
    setTasks(res.data);
  };

  const handleAdd = async () => {

    if (!title.trim()) {
      alert("Task title cannot be empty.");
      return;
    }

    if (!description.trim()) {
      alert("Task description cannot be empty.");
      return;
    }

    if(title.length > 100) {
      alert("Title cannot exceed 100 characters.");
      return;
    }

    if(description.length > 200) {
      alert("Description cannot exceed 200 characters.");
      return;
    }

    await addTask({ title, description });
    setTitle("");
    setDescription("");
    loadTasks();
  };

  const handleDelete = async (id) => {
    await deleteTask(id);
    loadTasks();
  };

  return (
    <div className="todo-app-wrapper">
      <div className="todo-container">
        <div className="todo-column add-column">
          <header className="app-header">
            <h1>Task Library</h1>
            <p>Task Management Web Application</p>
          </header>

          <div className="input-group">
            <label>Task Title</label>
            <input
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="What needs to be done?"
            />
          </div>

          <div className="input-group">
            <label>Description</label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Tell me more about this task..."
            />
          </div>

          <button className="add-btn" onClick={handleAdd}>
            Create Task
          </button>
        </div>

        <div className="todo-column list-column">
          <div className="list-header">
            <h2>Current Tasks</h2>
            <span className="task-count">{tasks.length}</span>
          </div>
          
          <div className="scroll-area">
            {tasks.length > 0 ? (
              <ul className="task-list">
                {tasks.map((task) => (
                  <li key={task.id} className="task-card">
                    <div className="task-content">
                      <h3>{task.title}</h3>
                      {task.description && <p>{task.description}</p>}
                      <span className="task-date">{task.date}</span>
                    </div>
                    <button className="delete-btn" onClick={() => handleDelete(task.id)}>
                      <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                      </svg>
                    </button>
                  </li>
                ))}
              </ul>
            ) : (
              <div className="empty-state">
                <p>Your workspace is clear.</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
