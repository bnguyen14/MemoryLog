# MemoryLog
A basic leitner-style spaced repitition studying program. Written for the command line, this program allows a user to have reminders for when they need to review something on a day-to-day basis so that they can maximize the use of their studying time. Items can have an associated quiz that the user can take when that item appears in the list. The quizzes simulate a random flash-card deck that accepts text answers only.
  
Installation Instructions:

1. Place memorylog and quizletcopy directories together.
2. Delete contents of savedQuiz.txt and configuration.txt in quizletcopy, auto_memory_log.txt in memorylog.
3. Compile TestManagerLauncher.java in quizletcopy, and MemoryLog.java in memorylog.

At this point the installation is done, but the software won't have anything to use. We'll now create quiz in a text file, let the program be able to access it, and then create an entry for it in the program. You can see the example configuration.txt as well as the format of the quiz files in the root directory.

1. Create a new file and start editing it.
2. Put the number 1 as the first line in the file. (We'll use this later.)
3. On the next line, think of a question you want to enter. Write it's answer, and then tab once (You can have multiple answers as long as they are tab separated).
4. Write the question.
5. Save your file. You can put it anywhere, but keep track of the path.
