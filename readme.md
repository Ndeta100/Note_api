# Note Resource API

This is a RESTful API for managing notes.

## Endpoints

### Get All Notes


- Returns all notes in the database.
- Response:
    - Status: 200 OK
    - Body: `HttpResponse<Note>` containing the list of notes.

### Create a Note


- Creates a new note.
- Request body: JSON representation of a `Note` object.
- Response:
    - Status: 201 Created
    - Location header: URL of the newly created note
    - Body: `HttpResponse<Note>` containing the created note.

### Filter Notes by Level


- Filters notes in the database by their level.
- Query parameter:
    - `level` (required): The level to filter by (e.g., HIGH, MEDIUM, LOW).
- Response:
    - Status: 200 OK
    - Body: `HttpResponse<Note>` containing the filtered notes.

### Update a Note


- Updates an existing note.
- Request body: JSON representation of a `Note` object.
- Response:
    - Status: 200 OK
    - Body: `HttpResponse<Note>` containing the updated note.

### Delete a Note


- Deletes a note by its ID.
- Path parameter:
    - `noteId` (required): The ID of the note to delete.
- Response:
    - Status: 200 OK
    - Body: `HttpResponse<Note>` containing the deleted note.

### Error Handling


- Handles errors for invalid or unknown paths.
- Response:
    - Status: 404 Not Found
    - Body: `HttpResponse<?>` containing the error details.

## Prerequisites

- JDK 8 or higher
- Spring Boot
- Next.js

## Usage

1. Clone the repository.
2. Build and run the project.
3. Access the API endpoints using the provided URLs.

## Author

Ndeta Innocent
