# Student Union Chaos Simulator

A text-based management game written in Java. You play as the new Student Union President and have 10 weeks to balance the budget, keep students happy, protect the union's reputation, and stop complaints getting out of control.

Built for my first-year Semester 2 project portfolio on the BSc Software Engineering course at the University of Huddersfield.

## How the game works

Each week you choose one action, then a story-based challenge tests how well the union is doing.

| Action | Effect |
| --- | --- |
| Run a student event | Raises happiness and reputation, but riskier events can go wrong and create complaints |
| Fund a society | Raises happiness and reputation, and lowers complaints |
| Improve union facilities | Raises happiness and reputation and lowers complaints, with bigger upgrades costing more |
| Save money | Keeps the budget intact, but lowers happiness and reputation and adds complaints |

| Outcome | Condition |
| --- | --- |
| **Win** | Finish week 10 with happiness 75+, reputation 70+, and a budget of GBP 200+ |
| **Lose** | Budget reaches GBP 0, happiness reaches 0, or complaints reach 60 |

If you survive without winning, your final stats decide which other ending you get.

## Features

- 10 weekly challenges, some of which depend on your stats or on chance
- 5 events and 5 societies with different costs, rewards, and risks
- Automatic saving after every week, with the option to continue a previous game
- Warnings when the budget, happiness, reputation, or complaints reach risky levels
- Multiple endings
- Menu input validation, so invalid choices are handled without crashing

## Example

```text
==============================================
                WEEK 1
==============================================
Budget: GBP 1000
Student Happiness: 60/100
Union Reputation: 50/100
Complaints: 10
Total Event Attendance: 0
Weeks Remaining: 10
----------------------------------------------
Status: Stable
==============================================

What would you like to do this week?
1. Run a student event
2. Fund a society
3. Improve union facilities
4. Save money this week
5. View full union report
6. Save and exit
7. Exit without saving again
Choose an option:
```

## Project structure

| File | Purpose |
| --- | --- |
| `StudentUnionSimulator.java` | Program entry point, menus, input validation, and the game loop |
| `StudentUnion.java` | Game state, weekly challenges, and saving and loading |
| `UnionActivity.java` | Abstract parent class for events and societies |
| `Event.java` | Student events with attendance and risk |
| `Society.java` | Societies with funding and activity levels |

## Concepts demonstrated

- **Object-oriented programming:** abstraction, inheritance, method overriding, and encapsulation
- **File handling:** writing and reading a save file, with fallback to a new game if it is missing or invalid
- **Input validation and exception handling:** repeated prompts until a valid menu choice is entered
- **Game state management:** stat limits, win and lose checks, and randomised outcomes
