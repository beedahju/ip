# Ladis User Guide

Ladis is a **command-line task management application** that helps you organize your to-dos, deadlines, and events efficiently. Simple, fast, and keyboard-driven.

![UI.png](UI.png)

## Quick Start

1. Download the latest `Ladis.jar` from releases
2. Double-click to launch or run: `java -jar Ladis.jar`
3. Type commands and press Enter to manage your tasks
4. Type `bye` to exit

---

## Features

### 1. Adding a Todo Task

Add a simple task without a deadline.

**Command:** `todo DESCRIPTION`

**Example:** `todo Buy groceries`

**Expected Output:**
```
Got it. I've added this task:
  [T][ ] Buy groceries
Now you have 1 task in the list.
```

---

### 2. Adding a Deadline

Add a task with a deadline date (optionally with time).

**Command:** `deadline DESCRIPTION /by DATE [TIME]`
- DATE format: `DD/MM/YYYY` (e.g., `28/02/2026`)
- TIME format: `HHMM` in 24-hour format (e.g., `1400` for 2:00 PM)

**Examples:**
```
deadline Finish project report /by 28/02/2026
deadline Submit assignment /by 01/03/2026 1400
```

**Expected Output:**
```
Got it. I've added this task:
  [D][ ] Finish project report (by: Feb 28 2026)
Now you have 2 tasks in the list.
```

---

### 3. Adding an Event

Add a task that spans a time period with start and end dates/times.

**Command:** `event DESCRIPTION /from DATE [TIME] /to DATE [TIME]`

**Examples:**
```
event Team meeting /from 20/02/2026 1000 /to 20/02/2026 1100
event Vacation /from 01/03/2026 /to 07/03/2026
```

**Expected Output:**
```
Got it. I've added this task:
  [E][ ] Team meeting (from: Feb 20 2026, 10:00 AM to: Feb 20 2026, 11:00 AM)
Now you have 3 tasks in the list.
```

---

### 4. Listing All Tasks

Display all tasks with their completion status.

**Command:** `list`

**Expected Output:**
```
Here are the tasks in your list:
1. [T][ ] Buy groceries
2. [D][ ] Finish project report (by: Feb 28 2026)
3. [E][ ] Team meeting (from: Feb 20 2026, 10:00 AM to: Feb 20 2026, 11:00 AM)
```

---

### 5. Marking a Task as Done

Mark a task as completed with a checkmark.

**Command:** `mark TASK_NUMBER`

**Example:** `mark 1`

**Expected Output:**
```
Nice! I've marked this task as done:
  [T][X] Buy groceries
```

---

### 6. Unmarking a Task

Remove the checkmark from a completed task.

**Command:** `unmark TASK_NUMBER`

**Example:** `unmark 1`

**Expected Output:**
```
OK, I've marked this task as not done yet:
  [T][ ] Buy groceries
```

---

### 7. Finding Tasks

Search for tasks by keyword (case-insensitive).

**Command:** `find KEYWORD`

**Example:** `find project`

**Expected Output:**
```
Here are the matching tasks in your list:
1. [D][ ] Finish project report (by: Feb 28 2026)
```

---

### 8. Deleting a Task

Permanently remove a task from your list.

**Command:** `delete TASK_NUMBER`

**Example:** `delete 2`

**Expected Output:**
```
Noted. I've removed this task:
  [D][ ] Finish project report (by: Feb 28 2026)
Now you have 2 tasks in the list.
```

---

### 9. Archiving a Task

Move a task to archive instead of deleting it (preserves history).

**Command:** `archive TASK_NUMBER`

**Example:** `archive 1`

**Expected Output:**
```
Archived! I've moved this task to archive:
  [T][X] Buy groceries
Now you have 1 task in the list.
```

---

### 10. Exiting the Application

Close Ladis gracefully.

**Command:** `bye`

**Expected Output:**
```
Bye. Hope to see you again soon!
```

---

## Task Icon Reference

| Icon | Meaning |
|------|---------|
| `[T]` | Todo task |
| `[D]` | Deadline task |
| `[E]` | Event task |
| `[ ]` | Not completed |
| `[X]` | Completed ✓ |

---

## Command Summary

| Command | Purpose |
|---------|---------|
| `todo DESCRIPTION` | Add a simple task |
| `deadline DESCRIPTION /by DATE [TIME]` | Add a task with deadline |
| `event DESCRIPTION /from DATE [TIME] /to DATE [TIME]` | Add a time-blocked event |
| `list` | Show all tasks |
| `mark TASK_NUMBER` | Mark task as done |
| `unmark TASK_NUMBER` | Mark task as not done |
| `find KEYWORD` | Search tasks by keyword |
| `delete TASK_NUMBER` | Remove a task |
| `archive TASK_NUMBER` | Archive a task |
| `bye` | Exit application |

---

## Tips & Tricks

✅ **Tip 1:** Tasks are automatically saved to file. No need to manually save!

✅ **Tip 2:** Use `find` to quickly locate tasks instead of scrolling through the list.

✅ **Tip 3:** Archive completed tasks to keep your active list uncluttered.

✅ **Tip 4:** Date format is strict: `DD/MM/YYYY`. Use leading zeros (e.g., `05/03/2026` not `5/3/2026`).

✅ **Tip 5:** times are in 24-hour format. `0900` = 9:00 AM, `1700` = 5:00 PM.

---

## Troubleshooting

**"Invalid task number"**
- Check that you're using a valid task number from the list displayed via `list` command.

**"Invalid date format"**
- Use `DD/MM/YYYY` format. Example: `25/12/2026` for December 25, 2026.

**"Missing deadline/event details"**
- For deadlines: use `/by` keyword. For events: use `/from` and `/to` keywords.

**Tasks not saving**
- Ensure the application closed normally (use `bye` command).
- Check disk space availability.

---

**Developed with ❤️ by Ladis Team**