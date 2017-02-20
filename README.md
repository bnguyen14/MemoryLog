# MemoryLog
A basic leitner-style spaced repitition studying program. Written for the command line, this program allows a user to have reminders for when they need to review something on a day-to-day basis so that they can maximize the use of their studying time. Items can have an associated quiz that the user can take when that item appears in the list. The quizzes simulate a random flash-card deck that accepts text answers only.
  
Installation Instructions:

It should be noted that this software is designed for use on Linux. In order to use it on Windows you simply need to change the paths of the configuration files. Edit MemoryLog.java and TestManager.java. When you scroll down, you'll find some file paths that are Linux-style. Just swap the '/' with a '\\' and you should be good to go. (There's only three lines to edit).

1. Install the Java JDK so that you can compile the source.
2. Place memorylog and quizletcopy directories together.
3. Compile TestManagerLauncher.java in quizletcopy, and MemoryLog.java in memorylog.

At this point the installation is done, but the software won't have anything to use. We'll now create quiz in a text file, let the program be able to access it, and then create an entry for it in the program. You can see the example configuration.txt as well as the format of the quiz files in the root directory.

1. Create a new file and start editing it.
2. Put the number 0 as the first line in the file. (We'll use this later.)
3. On the next line, think of a question you want to enter. Write it's answer, and then tab once (You can have multiple answers as long as they are tab separated).
4. Write the question.
5. Save your file as a .txt (IMPORTANT). You can put it anywhere, but keep track of the path.

Now we're going to set up the configuration file so that the program can find your quiz.

1. Start editing the configuration.txt in quizletcopy.
2. On the first line, type the subject of the quiz you just made (for example, "programming_101_quizzes"). 
  - This will correspond with the '0' that you entered in your test from before. You can specify subjects for new quizzes by adding other lines below this one, starting with "1". 
3. Press enter twice (so there's a blank line above your cursor).
4. Type the absolute path of your quiz.

Now your quiz is set up. You can find it by running the program (MemoryLog.class) and typing 3 for 'Take quiz.'. Now we're going to set up an entry for it so that you can use the program as a spaced repitition helper.

1. In the program menu (run the program), select 4 to add an entry.
  - If you mess up, just complete the creation and then delete it afterwards.
2. "Add this" means how many days inbetween reviewing there are. It's probably best to start with 1.
3. Enter the date on which it will appear in the program (probably choose the current date).
4. Name your entry.
5. You can decide if your entry has modifiers. For example, if you have a set of math problems, you can have it tell you to do the odd problems on one reviewing and the even problems on the next reviewing. Modifiers are simply strings that you enter.

You should have your entry set up now, when you say to display today's entries, your entry should appear there as long as it's review date is either today or in the past.

IMPORTANT:

In order to properly execute this program, you need to execute it from the folder that holds memorylog and quizlet copy. This either consists of running it from the command line while in that folder, or creating your own script to execute it from that folder. The program uses relative directories to find the configuration files, and until I change this, just make sure you're executing so that these directories can be found.
