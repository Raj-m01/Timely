1.	Develop a flutter application to make a container with font weight bold and text center

import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text(
            "Experiment 1 ",
          ),
        ),
        body: Center(
          child: const Text(
            "MAD & PWA Lab ",
            style: TextStyle(fontWeight: FontWeight.bold),
          ),
        ),
      ),
    );
  }
}


2.	Develop a flutter application to add image from internet

import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text("Experiment 2"),
        ),
        body: Center(
          child: Image.network(
              "https://images.unsplash.com/photo-1471644778460-220908b75485?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=872&q=80.png"),
        ),
      ),
    );
  }
}

3.	Develop a flutter application to add image from system 

Pubspec.yaml file
 


import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text("Experiment 3"),
        ),
        body: Column(
          children: [Image.asset("assets/images/butterfly.jpg")],
        ),
      ),
    );
  }
}

4.	Develop a flutter application to add navigation icon into header with text welcome

import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
 @override
 Widget build(BuildContext context) {
   return MaterialApp(
     home: Scaffold(
       appBar: AppBar(
         title: const Text("Welcome"),
       ),
       drawer: Drawer(
         child: ListView(children: const <Widget>[
           DrawerHeader(
             decoration: BoxDecoration(color: Colors.green),
             child: Text("Home"),
           ),
           ListTile(
             title: Text("Home"),
             leading: Icon(Icons.home),
           ),
         ]),
       ),
     ),
*/     debugShowCheckedModeBanner: false,
   );
 }
}



5.	Develop a flutter application to add button and on pressed write MAD & PWA

import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(
    home: HomeRoute(),
  ));
}

class HomeRoute extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text("First Screen"),
          backgroundColor: Colors.blue,
        ),
        body: Center(
          child: RaisedButton(
            child: Text("Click Me !"),
            onPressed: () {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => SecondRoute()));
            },
            color: Colors.blue,
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(50)),
          ),
        ),
      ),
    );
  }
}

class SecondRoute extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Second Screen"),
      ),
      body: Center(child: const Text("MAD AND PWA")),
    );
  }
}

--


6.	Develop a flutter application to connect with firebase

Practical:

1. Create a new project with VS Code.

 













2. Create an account at Firebase

 

3. Add new project at firebase and add project name

 q
 


4. Select default account for firebase and click on create project

 

 


 









5.  Go to your flutter project at VS Code and modify the package name on the following path:
    1. android/app/src/build.gradle/
       inside defaultconfg function change the package such as com.demo.projectname
In this case name is ‘com.example.demoexp7’

 

   2. android/app/src/debug/AndroidMainfest.xml (change package)

 


   3. android/app/src/main/AndroidMainfest.xaml (change package)

 

   4.android/app/src/profile/AndroidMainfest.xml (change package) and save all the changes

 








6. Go to console and click on android icon

 

7. Add Android package name which you have change at VS Code flutter project.

 





8. Add app nickname and click on register app

 

9. Download the google-service.json file and paste it inside the flutter project android/app folder

 

 

10. Add the plugin as follows:
    Project-level build.gradle (<project>/build.gradle): 
     inside dependencies :
      classpath 'com.google.gms:google-services:4.3.10'

 





11. App-level build.gradle (<project>/<app-module>/build.gradle): at the bottom of page

     implementation 'com.google.firebase:firebase-analytics'
     implementation platform('com.google.firebase:firebase-bom:29.3.1')
     apply plugin: 'com.google.gms.google-services'

 

12. Now click on continue to console

 

13. Run the flutter project, if you find zero error at debug console then connectivity is successful

 

 

 
Conclusion :
We have successfully connected


7.	Create a html page with pwa service worker

Index.html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>PWA Tutorial</title>
        <meta name="Demo App" content="#aa7700">
        <meta name="theme-color" content="black">
        <link rel="manifest" href="manifest.json">
    </head>
    <body>
        <h1 style="color: green;">GeeksforGeeks</h1>
        <p>
            This is a simple tutorial for
            creating a PWA application.
        </p>
        <script>
            window.addEventListener('load', () => {
            registerSW();
            });

            async function registerSW() {
            if ('serviceWorker' in navigator) {
                try {
                await navigator
                        .serviceWorker
                        .register('serviceworker.js');
                }
                catch (e) {
                console.log('SW registration failed');
                }
            }
            }
        </script>
    </body>
</html>
Manifest.json
{
    "name":"PWA Tutorial",
    "short_name":"PWA",
    "start_url":"index.html",
    "display":"standalone",
    "background_color":"#5900b3",
    "theme_color":"black",
    "scope": ".",
    "description":"This is a PWA tutorial.",
    "icons":[
    {
    "src":"images/icon-192x192.png",                    
    "sizes":"192x192",
    "type":"image/png"
    },
    {
    "src":"images/icon-512x512.png",
    "sizes":"512x512",
    "type":"image/png"
    }
]
}




















Serviceworker.js
var staticCacheName = "pwa";

self.addEventListener("install", function (e) {
e.waitUntil(
    caches.open(staticCacheName).then(function (cache) {
    return cache.addAll(["/"]);
    })
);
});

self.addEventListener("fetch", function (event) {
console.log(event.request.url);

event.respondWith(
    caches.match(event.request).then(function (response) {
    return response || fetch(event.request);
    })
);
});


8.	Develop a flutter program for basic hello world

import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text("Nafees Posharkar"),
        ),
        body: Center(
          child: Text("Hello World!!"),
        ),
      ),
    );
  }
}


9.	Develop a flutter program to add bottom navigation with three menu

import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}


class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text("Experiment 8"),
        ),
        bottomNavigationBar: BottomNavigationBar(items: const [
          BottomNavigationBarItem(
            label: "Home",
            icon: Icon(Icons.home),
          ),
          BottomNavigationBarItem(
              label: "Search",
              icon: Icon(Icons.search),
              backgroundColor: Colors.white),
          BottomNavigationBarItem(
              label: "Profile",
              icon: Icon(Icons.person),
              backgroundColor: Colors.white),
        ], onTap: (int indexOfItem) {}),
      ),
    );
  }
}


10.	Develop a flutter program to add navigation of two screens

import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(
    home: HomeRoute(),
  ));
}

class HomeRoute extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('First Screen'),
        backgroundColor: Colors.green,
      ),
      body: Center(
        child: RaisedButton(
            child: Text('Click Me!'),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => SecondRoute()),
              );
            }),
      ),
    );
  }
}

class SecondRoute extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Second Screen"),
        backgroundColor: Colors.green,
      ),
      body: Center(
        child: RaisedButton(
          onPressed: () {
            Navigator.pop(context);
          },
          child: Text('Home!'),
        ),
      ),
    );
  }
}



