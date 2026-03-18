export default function TaskList({ tasks, handleComplete, handleDelete }) {
  return (
    <ul className="task-list">
    {tasks.map((task) => (
        <li key={task.id} className="task-card">
            <div className="task-content">
                <h3>{task.title}</h3>
                {task.description && <p>{task.description}</p>}
            </div>
            <div className="task-actions">
                <button className="complete-btn" aria-label="complete-btn" onClick={() => handleComplete(task.id)}>
                    <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 13l4 4L19 7" />
                    </svg>
                </button>
                <button className="delete-btn" aria-label="delete-btn" onClick={() => handleDelete(task.id)}>
                    <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                </button>
            </div>
        </li>
    ))}
    </ul>
)};