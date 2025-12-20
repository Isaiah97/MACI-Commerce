Topic: The MACI Commerce Flower Shop is a personal project our team built to make ordering flowers online feel simple and smooth. It’s a desktop app where you can browse through different bouquets, search for specific flowers you like, and manage your cart as you go, and remove bouquets from the car if youchange your mind before checking out. Behind the scenes, it handles all the heavy lifting like calculating totals, processing payments, and sending out confirmation emails, all wrapped up in a clean, easy-to-use design.

------------------------------------------------------------------------------------------------
Why is it important?
Our project is important because it demonstrates how to bridge complex backend logic with a user-centric interface using the Model-View-Controller (MVC) architecture. It provides a scalable solution for small businesses by managing real-time data synchronization, such as dynamic inventory searching and live cart updates. It highlights the practical application of software engineering principles to ensure a stable, secure, and accessible digital retail environment.

------------------------------------------------------------------------------------------------
How to use:
1) Create an empty file within computer
2) Cd into said file
3) Log into github
4) Click on green button, copy ssh link
5) Open terminal, cd empty file
6) Type git clone paste ssh link
7) cd into src
     -app (view) 
     -service (controller)
     -model (model)
9) cd into MACI-commerce
10) Type "javac -d bin $(find src/app src/model src/service -name "*.java")"
11) cd into bin
12) Type "java app.GUIMain"
13) It is now a customer interface features: search, add, remove, checkout
    
Logging into Admin:
15) Click admin panel
16) enter password: secure123
17) admin side: view orders, update status
