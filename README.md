# MemoryLog

A utility that helps the user keep track of a leitner-style spaced repitition studying system. The
program allows a user to have reminders for when they need to review something up to a precision of
days. The days between studying periods are user-set, and the program is not intelligent. Each time
you complete a task and go to update it, it will ask you to set the new revision period (the number
of days until you review it again.) - it's up to the user to learn what works best for them.
The program will however automatically spread out entries to make the load more predictable. Though
this only really makes sense when there are a lot of entries in the manager.

Another program called SubjectTester is used to test the user on a "subject". The idea is to load
a large number of questions into a file, which the program will then ask the user for the answers to
the ones that need to be reviewed on any specific day. The program uses a basic algorithm involving
increasing days between review on correct answers and resetting the review period to one day on
incorrect answers. This is an update to the TestManager program described below, but rather than
using small sets of questions, the granularity is increased so that the questions themselves are
the things being tracked rather than a group. 

Another program in this repository is TestManager, which can be used as a rudimentary replacement
for flash card sets. See the examples directory for information on how to make the files that it
takes.

Together, these three programs offer a considerable tool to study information, learn new
information, and keep track of recurring events.

# Original work that uses console is pulled from Aroweeri, https://github.com/Aroweeri/MemoryLog
Note: this was done for a class project. This branch of MemoryLog does not have full functionality 
implemented to work with GUI. ie, deleting questions, adding multiple answers. Changes and additions
were made by https://github.com/bnguyen14 and https://github.com/Nguyen2010

# Implementation:
Added GUI for easier use:
-Main Menu
-Add Questions
-Deep - quiz that uses SRS system
-General - quiz that chooses at most of 8 random questions to go through and shuffles them
-Review/Practice - shows questions on the front and can reveal answer in the back, like a flashcard
 (Currently there are two buttons that goes to the same GUI

# Changes:
-Modified SubjectTester to accept parameters from GUI
	-the add method now accepts a question and answer (only one answer for the sake of time).
	-the run method returns a list of questions for today.
	-a sizable chunck of algorithm from the ask method was moved and modified in SRSquiz class
	 to go through today's questions.
# Additions:
-Main - for the main menu
-AddQuestionsFrame - to add questions
-SRSquiz - explained in deep
-QuizRun/QuizConts - explained in general
-FlashCard/FlashCardA - explained in review/practice
-QuizTester - class that works like SubjectTester, but with slight modification and does not save to
 savedQuiz
-ChooseQuiz - menu for selecting different studying types
