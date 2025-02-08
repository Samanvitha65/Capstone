---
You are developing a Angular application for a university. The university wishes to manage its Professor who heads the program.

Entities:

Professor:
  professorID: Unique Identifier for the Professor.
  name: Name of the professor.
  email: Email address of the professor.

Tasks:

You will receive JSON data for Professor entity and add this to JSON server (db.json)
On Frontend (Angular), Set up a basic Angular application.

Implement Angular services for Professor entity to interact with the JSON file using JSON Server.Design forms to add new professors with proper validations and store them in JSON Server.

Create Angular component for Professor entity to display details of specific Professor by retrieving JSON data from JSON Server.

Sample db.json:
{
  "professors": [
    {
      "id": 1,
      "name": "Dr. John Smith",
      "email": "john.smith@university.edu"
    },
    {
      "id": 2,
      "name": "Dr. Jane Doe",
      "email": "jane.doe@university.edu"
    },
    {
      "name": "Dr Damith",
      "email": "damith@ucsc.lk",
      "id": 3
    }
  ]
}

Your task is to complete the following files:
  1. src/app/professor-list/professor-list.component.ts
  2. src/app/professor-form/professor-form.component.ts
  3. src/app/services/professor.service.ts

Notes:
1. Do not change file names, class names , method declarations.
2. Use Test App & Submit option often so you will be guided by test error messages.
3. You do not need to add template files or styles to the application
4. Do not remove entries from db.json

Testing & Submitting your code:

Step 1: Click on the WeCP Projects Button.
Step 2: Click on Test & Submit app button to test your code.You will receive a congratulations message upon successful completion of the task.
Step 3: Click on start app to run the angular server.
Step 4: Click on Open browser preview to view GUI.
