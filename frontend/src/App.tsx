import { useState, useEffect } from 'react';
import toast, { Toaster } from 'react-hot-toast';
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
  const [isSubmitting, setIsSubmitting] = useState(false);

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
      toast.error('Erro ao buscar tarefas.');
    }
  };

  const handleAddTask = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newTaskTitle.trim()) return;

    setIsSubmitting(true);
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
        toast.success('Tarefa adicionada com sucesso!');
      } else {
        toast.error('Erro ao adicionar tarefa.');
      }
    } catch (error) {
      console.error('Error adding task:', error);
      toast.error('Erro de conexão ao adicionar.');
    } finally {
      setIsSubmitting(false);
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
        toast.success('Tarefa concluída! 🎉');
      } else {
        toast.error('Erro ao concluir tarefa.');
      }
    } catch (error) {
      console.error('Error completing task:', error);
      toast.error('Erro de conexão ao concluir.');
    }
  };

  const handleDeleteTask = async (id: string) => {
    try {
      const response = await fetch(`${API_URL}/${id}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        fetchTasks();
        toast.success('Tarefa removida!');
      } else {
        toast.error('Erro ao deletar tarefa.');
      }
    } catch (error) {
      console.error('Error deleting task:', error);
      toast.error('Erro de conexão ao deletar.');
    }
  };

  return (
    <div className="app-container">
      <Toaster position="bottom-right" toastOptions={{ 
        style: {
          background: '#333',
          color: '#fff',
        }
      }} />
      <h1>🚀 To-Do App</h1>
      
      <form className="task-form" onSubmit={handleAddTask}>
        <input
          type="text"
          className="task-input"
          placeholder="O que precisa ser feito?"
          value={newTaskTitle}
          onChange={(e) => setNewTaskTitle(e.target.value)}
          disabled={isSubmitting}
        />
        <button type="submit" className="btn-add" disabled={isSubmitting}>
          {isSubmitting ? 'Adicionando...' : 'Adicionar'}
        </button>
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
                  title="Marcar como concluída"
                >
                  ✓
                </button>
              )}
              <button 
                className="btn-icon btn-delete" 
                onClick={() => handleDeleteTask(task.id)}
                title="Deletar tarefa"
              >
                ✕
              </button>
            </div>
          </div>
        ))}
        {tasks.length === 0 && (
          <div style={{ textAlign: 'center', opacity: 0.5, marginTop: '2rem' }}>
            Nenhuma tarefa ainda. Aproveite o seu dia! 🌟
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
