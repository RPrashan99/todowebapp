import React, { useEffect, useState } from "react";
import { addTask, deleteTask, completeTask, getUncompletedTasks } from "./ApiService/Api.js";
import { ToastContainer, toast } from "react-toastify";
import TaskList from "./components/TaskList.js";

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  useEffect(() => {
    loadTasks();
  }, []);

  const loadTasks = async () => {
    const res = await getUncompletedTasks();
    setTasks(res.data);
  };

  const handleAdd = async () => {

    if (!title.trim()) {
      toast.error("Task title cannot be empty.");
      return;
    }

    if(title.length > 100) {
      toast.error("Title cannot exceed 100 characters.");
      return;
    }

    if(description.length > 200) {
      toast.error("Description cannot exceed 200 characters.");
      return;
    }

    await addTask({ title, description });
    toast.success("Task added successfully!");
    setTitle("");
    setDescription("");
    loadTasks();
  };

  const handleDelete = async (id) => {
    try{
      await deleteTask(id);
    }catch(e){
      toast.error("Task delete error.")
    }
    toast.success("Task deleted.");
    loadTasks();
  };

  const handleComplete = async (id) => {
    await completeTask(id);
    toast.success("Task marked as completed.");
    loadTasks();
  }

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
              <TaskList tasks={tasks} handleComplete={handleComplete} handleDelete={handleDelete} />
            ) : (
              <div className="empty-state">
                <p>Your workspace is clear.</p>
              </div>
            )}
          </div>
        </div>
      </div>
      <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} 
        newestOnTop={false} closeOnClick rtl={false} pauseOnFocusLoss draggable pauseOnHover />
    </div>
  );
}

export default App;
