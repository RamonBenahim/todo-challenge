import { useState, useEffect } from 'react';
import './index.css';

interface Task {
  id: string;
  title: string;
  description: string;
  status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED';
  createdAt: string;
}

const API_URL = 'http://localhost:8080/api/tasks';

function App() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [newTaskTitle, setNewTaskTitle] = useState('');

  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await fetch(API_URL);
      const data = await response.json();
      setTasks(data);
    } catch (error) {
      console.error('Error fetching tasks:', error);
    }
  };

  const handleAddTask = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newTaskTitle.trim()) return;

    try {
      const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: newTaskTitle,
          status: 'PENDING',
        }),
      });

      if (response.ok) {
        setNewTaskTitle('');
        fetchTasks();
      }
    } catch (error) {
      console.error('Error adding task:', error);
    }
  };

  const handleCompleteTask = async (task: Task) => {
    try {
      const response = await fetch(`${API_URL}/${task.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: task.title,
          description: task.description,
          status: 'COMPLETED',
        }),
      });

      if (response.ok) {
        fetchTasks();
      }
    } catch (error) {
      console.error('Error completing task:', error);
    }
  };

  const handleDeleteTask = async (id: string) => {
    try {
      const response = await fetch(`${API_URL}/${id}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        fetchTasks();
      }
    } catch (error) {
      console.error('Error deleting task:', error);
    }
  };

  return (
    <div className="app-container">
      <h1>🚀 To-Do App</h1>
      
      <form className="task-form" onSubmit={handleAddTask}>
        <input
          type="text"
          className="task-input"
          placeholder="What needs to be done?"
          value={newTaskTitle}
          onChange={(e) => setNewTaskTitle(e.target.value)}
        />
        <button type="submit" className="btn-add">Add Task</button>
      </form>

      <div className="task-list">
        {tasks.map(task => (
          <div key={task.id} className="task-item">
            <div className="task-content">
              <span className="task-title">{task.title}</span>
              <span className={`task-status status-${task.status}`}>
                {task.status.replace('_', ' ')}
              </span>
            </div>
            <div className="task-actions">
              {task.status !== 'COMPLETED' && (
                <button 
                  className="btn-icon btn-complete" 
                  onClick={() => handleCompleteTask(task)}
                  title="Mark as completed"
                >
                  ✓
                </button>
              )}
              <button 
                className="btn-icon btn-delete" 
                onClick={() => handleDeleteTask(task.id)}
                title="Delete task"
              >
                ✕
              </button>
            </div>
          </div>
        ))}
        {tasks.length === 0 && (
          <div style={{ textAlign: 'center', opacity: 0.5, marginTop: '2rem' }}>
            No tasks yet. Enjoy your day! 🌟
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
