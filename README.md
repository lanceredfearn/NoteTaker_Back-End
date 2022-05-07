Create a note taking application

NoteTaker Back End:

CRUD	| Verb	 | Path	      | Name	        | Purpose
Create	| POST	 | /note      | “create” route	| Creates a new note
Read	| GET	 | /note/{id} | “show” route	| Responds with a single note record
Update	| PATCH	 | /note/{id} | “update” route	| Updates information for note / add additional content
Delete	| DELETE | /note/{id} | “delete” route	| Deletes the note
List	| GET	 | /note	  | “list” route    | Responds with a list of all notes

In Your Database:
Note Entity:
Data Type	Attribute
Long	      id
String 	      title
String	      content
NoteTaker Frontend:
Create a note application that holds onto notes and allows users to update content
Given I am a User of this application
When I navigate to the main page, I should see a list of all previously created notes on the left hand side, and on the right I should see a detailed view of the current note.
Acceptance Criteria:
A list of notes titles on the right hand side of the page
A single note on the right with a title and content displayed in inputs
Given I am a User of this application
When I click on the add button above the main page
Then I am able to input a title into the created input
And a new note will be created with that title
Acceptance Criteria:
A button which renders an input box when clicked
An input box which when the user presses enter, saves a new note to the database and displays it on the list of notes
Given I am a User of this application
When I click on the title or the content of the detailed note, Then I should be able to update those values and see it reflected on my page
Acceptance Criteria:
A title input which sends an AJAX call to update the database when it is changed
A content input which sends an AJAX call to update the database when it is changed
Nightmare mode:
Given I am a User of this application
When I update content or title it should be autosaved in the database as I type
Acceptance Criteria:
Inputs should update the database as they are changed
Given I am a User of this application
When I Add a tag to my current note
Then I should be able to search my list of notes for specific tags
Acceptance Criteria:
An input that allows tags to be added to the current note
An input that filters the list for only notes that match the tags entered