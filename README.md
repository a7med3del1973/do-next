# Do Next

## Description

Do Next is a simple API for managing tasks and to-do lists. It allows users to efficiently organize, prioritize, and track their tasks hassle-free.

## Features

- User Authentication: Secure login and registration system.
- User Authorization: Role-based access control.
- Add Task: Users can add new tasks to their to-do list.
- Update Task: Users can update the details of their tasks.
- Delete Task: Users can remove tasks from their to-do list.
- Mark Task as Complete: Users can mark their tasks as complete.
- Search Task: Users can search for tasks in their to-do list.

## Technologies Used

- Java: The project is written in Java.
- Spring Boot: The backend framework used.
- Maven: Dependency Management.

## Setup and Installation

1. Clone the repository: `git clone https://github.com/yousofkortam/do-next.git`
2. Navigate to the project directory: `cd donext`
3. Install dependencies: `mvn install`
4. Run the application: `mvn spring-boot:run`

## Usage

After running the application, you can use the following endpoints:

### Auth

- POST `/auth/login`: Login a user.
- POST `/auth/register`: Register a new user.
- POST `/auth/verify-email`: Verify a user's email.
- POST `/auth/logout`: Logout a user.

### Task

- GET `/task`: Get all tasks.
- GET `/task/{id}`: Get a specific task.
- GET `/task/search`: Search for tasks.
- POST `/task`: Add a new task.
- PUT `/task/{id}`: Update a task.
- DELETE `/task/{id}`: Delete a task.
- POST `/task/toggle-complete/{id}`: Mark a task as complete or incomplete.

## Response Data Structure

Our API returns the response in the following format:

Success Response:
```json
{
    "success": true,
    "data": {}
}
```

Error Response:
```json
{
    "success": false,
    "error": {
        "StatusCode": 400,
        "message": "Error message",
        "timeStamp": "2021-09-01T12:00:00.000Z",
        "description": "Error description"
    }
}
```

Task Model
```json
{
    "id": 1,
    "title": "Task Title",
    "description": "Task Description",
    "completed": false,
    "createdAt": "2021-09-01T12:00:00.000Z",
    "dueDate": "2021-09-01T12:00:00.000Z",
    "completedDate": "2021-09-01T12:00:00.000Z"
}
```

## Contributing

If you want to contribute to this project, please fork the repository and create a pull request.

## License

This project is licensed under the MIT License.

## Contact Information

Yousof Kortam - yousofkortam@gmail.com - [LinkedIn](https://www.linkedin.com/in/yousofkortam/)