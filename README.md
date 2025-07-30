# <ins>PUCHO</ins>

  An App to count **how many cigarettes we smoke daily** but also **setting monthly goals** to help us strengthen our awareness of our habits and to help us _reduce our daily consumption._
> It shows you my thought process and documentation for creating apps. It'll get better.\
> <sub>This app was build by using [Android Studio](https://developer.android.com/)</sub>
## IN-DEPTH
  To fulfill the purpose of this app i wanted that follows this requirements:
1. Have a daily counter of smokes;
2. Have a button for adding smokes;
3. Have an amount of smokes established as a monthly goal;
4. Have it automatically telling me when i can get my next smoke by dividing the amount of smokes for hours awake;
5. Have a notification when i can get my next smoke, with a shortcut to add the smoke;
6. Choose if wanted to have the notification or not; 
7. Being able to change the goal;
8. Being able to count without a goal;
9. Being able to see a graphs of my daily progress;
10. Being able to see a list of my goals;
### USE CASES
  1 - SET DAILY COUNT WITHOUT A GOAL:
   - **Primary actor:** User;
   - **Goals:** Count daily smokes without a goal;
   - **Stakeholders:** None for now;
   - **Pre-conditions:** User must have install the app in Android and understand his purpose;
   - **Post-conditions:** User has to register his daily smokes and improve his health;
   - **Basic flow:**
     - User access the app;
     - System presents the user with the initial count of 0, the current date, and offers a button to adding smokes to the counter, and a button to set a goal; 
     - User starts counting his consumption;
     - System saves the count;
     - System checks for date changes and sets a new count for each new day;
     - User continues counting his consumption;
     - System saves the count;
     - System sets and exhibits a graph for daily counts;
   - **Alternate path:**
     - User access the app and select the option to set a goal;
     - System presents the user with a form to complete the amount of days for this goal, and the amount of daily consumption to achieve;
     - User set amount of consumption and amount of days to this goal and select option to save it;
     - System saves the goal and waits for the initial smoke to be add;
     - User select the option to add first smoke to the app;
     - System calculates and saves how often has to smoke from the current time until midnight to achieve the giving goal;
     - System set a 'it's abled to smoke´ sign and a notifications for adding the next smoke when the given time has passed;
     - User continues adding smokes;
     - System records progress;
     - System checks for date changes and sets a the count for each new day, and rest a day for the goal amount of days;
       
  2 - SET DAILY COUNT WITH A GOAL:

   - **Primary actor:** User;
   - **Goals:** Count daily smoke with a goal;
   - **Stakeholders:** None for now;
   - **Pre-conditions:** User must have install the app in Android and understand his purpose;
   - **Post-conditions:** User has to register his daily smokes and decide to improve his health;
   - **Basic flow:**
     - User access the app and select the option to set a goal;
     - System presents the user with a form to complete the amount of days for this goal, and the amount of daily consumption to achieve;
     - User set amount of consumption and amount of days to this goal and select option to save it;
     - System saves the goal and waits for the initial smoke to be add;
     - User select the option to add first smoke to the app;
     - System calculates and saves how often has to smoke from the current time until midnight to achieve the giving goal;
     - System set a 'it's abled to smoke´ sign and a notifications for adding the next smoke when the given time has passed;
     - User continues adding smokes;
     - System records progress;
     - System checks for date changes and sets a the count for each new day, and rest a day for the goal amount of days;
   - **Alternate path:**
     - User access the app and select the option to set a goal;
     - System presents the user with a form to complete the amount of time, and the amount of daily consumption;
     - User start counting his consumption;
