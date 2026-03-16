import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const getTasks = () => api.get('/tasks');
export const getUncompletedTasks = () => api.get('/tasks/uncompleted');
export const addTask = (task) => api.post('/tasks', task);
export const deleteTask = (id) => api.delete(`/tasks/${id}`);
export const completeTask = (id) => api.post(`/tasks/complete/${id}`);