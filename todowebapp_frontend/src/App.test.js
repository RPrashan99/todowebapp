import { act, fireEvent, render, screen } from '@testing-library/react';
import App from './App.js';
import { addTask, completeTask, deleteTask, getUncompletedTasks } from './ApiService/Api.js';

jest.mock('./ApiService/Api.js', () => ({
  addTask: jest.fn(),
  deleteTask: jest.fn(),
  completeTask: jest.fn(),
  getUncompletedTasks: jest.fn().mockResolvedValue({data: []}),
}));

test('renders todo web app', async () => {

  getUncompletedTasks.mockResolvedValueOnce({
    data: [],
  });

  render(<App />);
  const linkElement = await screen.findByText(/Task Library/i);
  expect(linkElement).toBeInTheDocument();
});

test('renders add task form', () => {

  getUncompletedTasks.mockResolvedValueOnce({
    data: [],
  });

  render(<App />);
  const titleInput = screen.getByPlaceholderText(/What needs to be done?/i);
  const descriptionInput = screen.getByPlaceholderText(/Tell me more about this task.../i);
  const addButton = screen.getByText(/Create Task/i);

  expect(titleInput).toBeInTheDocument();
  expect(descriptionInput).toBeInTheDocument();
  expect(addButton).toBeInTheDocument();
});

test('renders adding task functionality', () => {

  addTask.mockResolvedValue(
    { 
      data: { id: 1, title: 'Test Task', description: 'This is a test task.' } 
    }
  );

  getUncompletedTasks.mockResolvedValue(
    { data: 
      [
        { id: 1, title: 'Test Task', description: 'This is a test task.' }
      ] 
    }
  );

  render(<App />);
  const titleInput = screen.getByPlaceholderText(/What needs to be done?/i);
  const descriptionInput = screen.getByPlaceholderText(/Tell me more about this task.../i);
  const addButton = screen.getByText(/Create Task/i);

  expect(titleInput).toBeInTheDocument();
  expect(descriptionInput).toBeInTheDocument();
  expect(addButton).toBeInTheDocument();

  act(() => {
    fireEvent.change(titleInput, { target: { value: 'Test Task' } });
    fireEvent.change(descriptionInput, { target: { value: 'This is a test task.' } });
    fireEvent.click(addButton);
  });

  const taskListHeader = screen.getByText(/Test Task/i);

  expect(taskListHeader).toBeInTheDocument();
});

test('validates task input', () => {

  getUncompletedTasks.mockResolvedValueOnce({
    data: [],
  });

  render(<App />);
  const titleInput = screen.getByPlaceholderText(/What needs to be done?/i);
  const descriptionInput = screen.getByPlaceholderText(/Tell me more about this task.../i);
  const addButton = screen.getByText(/Create Task/i);

  act(() => {
    fireEvent.change(titleInput, { target: { value: '' } });
    fireEvent.change(descriptionInput, { target: { value: '' } });
    fireEvent.click(addButton);
  });

  const errorMessage = screen.getByText(/Task title cannot be empty./i);
  expect(errorMessage).toBeInTheDocument();
});

test('render complete task functionality', async () => {

  completeTask.mockResolvedValueOnce({ data: {} });

  getUncompletedTasks.mockResolvedValueOnce({
    data: [
      { id: 1, title: 'Test Task', description: 'This is a test task.' }
    ],
  });

  deleteTask.mockResolvedValue();

  render(<App />);

  const titleLabel = await screen.findByText('Test Task');
  const descritionLabel = await screen.findByText('Test Task');
  const deleteBtn = await screen.findByRole('button', { name: /delete-btn/i})

  expect(titleLabel).toBeInTheDocument();
  expect(descritionLabel).toBeInTheDocument();
  expect(deleteBtn).toBeInTheDocument();

  getUncompletedTasks.mockResolvedValueOnce({
    data: []
  });

  act(() =>{
    fireEvent.click(deleteBtn);
  })

  expect(titleLabel).toBeInTheDocument(false);
  expect(descritionLabel).toBeInTheDocument(false);
});
