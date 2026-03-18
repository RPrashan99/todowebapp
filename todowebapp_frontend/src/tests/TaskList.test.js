import { render, screen } from "@testing-library/react";
import TaskList from "../components/TaskList";

test("renders TaskList component", () => {
  const tasks = [
    { id: 1, title: "Task 1", description: "Description for Task 1" },
    { id: 2, title: "Task 2", description: "Description for Task 2" },
  ];

  function handleComplete(id) {
    console.log(`Complete task with id: ${id}`);
  }

  function handleDelete(id) {
    console.log(`Delete task with id: ${id}`);
  }

  render(
    <TaskList tasks={tasks} handleComplete={handleComplete} handleDelete={handleDelete} />
  );

  expect(screen.getByText("Task 1")).toBeInTheDocument();
  expect(screen.getByText("Description for Task 1")).toBeInTheDocument();
  expect(screen.getByText("Task 2")).toBeInTheDocument();
  expect(screen.getByText("Description for Task 2")).toBeInTheDocument();
});
