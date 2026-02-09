# <ins>PUCHO</ins>
La aplicación está escrita en JAVA. Utilizo SQLite con SQLiteOpenHelper. Su diseño es sencillo, apenas 2 actividades, una para ingresar datos, y la principal tiene 2 fragmentos cambiables donde muestro una lista y un gráfico. Cuenta con notificaciones, para las cuales solicita el permiso, y desde la misma notificación se puede seleccionar una acción de agregar un consumo.
Acá dejo unas capturas:
</br>
<img src="https://github.com/user-attachments/assets/ea6a7912-b273-4328-96a8-049c1dcc46af" width="20%"/>
<img src="https://github.com/user-attachments/assets/e1b3bed6-1e9f-4d78-9753-3002062e40a6" width="20%"/>
<img src="https://github.com/user-attachments/assets/bea6910f-21d6-44ef-8591-e8124508d5ce" width="20%"/>

Hice esta aplicación para llevar una cuenta de los cigarrillos que consumo y que me asista en el proceso de dejarlo o al menos reducir el consumo. Se puede optar por establecer una meta diaria, y se puede activar notificaciones. La aplicación toma la hora del último cigarrillo consumido y calcula el tiempo que debe transcurrir para el próximo consumo de acuerdo con la meta establecida. En base a ello envía una única notificación. Si se realiza otro consumo, entonces habrá una nueva notificación en el momento pertinente.
En mi experiencia, puedo decir que consumía alrededor de 20 diarios y reduciendo mensualmente de a 2, logré buenos resultados. Por lo que recomiendo ese mismo plan. 

Para ampliar un poco más:</br>
  This App works by counting **daily smokes** 🚬. Also **setting monthly goals** 🏆 may help you strengthen your awareness._ 

> It shows you my thought process and documentation for creating apps. It'll get better.👍\
> **Technicals:** 💻
> - <sup>**DBMS:** SQLite</sup>
> - <sup>**IDE:** Android Studio</sup>
> - <sup>**Languaje:** JAVA</sup>
> - <sup>**SDK:** 35</sup>
> - <sup>**Build tool:** Gradle</sup>
>
## IN-DEPTH
To fulfill the purpose of this app i wanted that follows this requirements:\
FUNCTIONAL REQUIREMENTS:
1. Have a daily counter of smokes;
2. Have a button for adding smokes;
3. Have an amount of smokes established as a monthly goal;
4. Have it automatically telling me when i can get my next smoke by dividing the amount of smokes for hours awake;
5. Have a notification when i can get my next smoke, with a shortcut to add the smoke;
6. Choose if wanted to have the notification or not; 
7. Being able to change the goal;
8. Being able to count without a goal;
9. Being able to see a graphs of my daily progress;
10. Being able to see a list of my goals.<br/>

NON-FUNCTIONAL REQUIREMENTS:<br/>
1. System must allow user to easily count daily smokes;
2. System must have options for 30/60/90 days to avoid unnecessary thoughts when goals are being set.
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
     - System presents the user with a form to complete the amount of days for this goal, and the amount of daily consumption to achieve;
     - User set amount of consumption and amount of days to this goal and select option to save it;
     - System saves the goal and waits for the initial smoke to be add;
     - User select option to add first smokes on a different day;
     - System calculates and saves how often has to smoke from the current time until midnight to achieve the giving goal;
     - System set a 'it's abled to smoke´ sign and a notifications for adding the next smoke when the given time has passed;
     - 
    
  ### REQUIREMENTS ANALYSIS
  It will be necessary to store:
- Count of smokes related to a day;
- Count to achive related to a day;
- State of the current goal related to a day;
- Time to each smoke in case a goal has been set;
- Date related to the start of a goal;
- Count of smokes to achive related to a goal;
- Days until complete the goal;

