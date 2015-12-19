Family Protector is an Android application that helps parents keep a track of their children.

This application works in two modes 'Parent Mode' and the 'Child Mode'. In the parent mode, the parents can set up various location rules for the children, which includes the address and the perimeter for the location. The parent can also specify the time and days of week for which the rule is applicable.

In the child mode the parent needs to initially login and choose the child who the device belongs to, thats it!

The database for this application is hosted on the Parse cloud, which can be easily accessed and stored using parse queries. The database is used to store the rule locations. This application also uses the Parse push functionality, which helps to send notification to the parent in case the child steps out of the location at the specified time and day.

Some other resources of the project include:

1. Initial Design Document: https://github.com/amruta-d/CSC-780/blob/master/Design/DesignDoc_FamilyProtector.pdf
2. The weekly update on the project: https://github.com/amruta-d/CSC-780/blob/master/Journal.pdf


This application uses various android features such as:
-Location API provided by Google to set up geofences.
-Parse cloud storage
-Recycler View
-Multi selection spinner
-Google Maps API to search for a particular location
-Alarm Manager to run a service automatically everyday.

###Installation Steps
(Note: in order to test the notification functionality two android phones are required for testing this app)

- Download the project from github and run the app.
- An initial setup is required which includes registering with an email Id and choosing the mode for this phone
- Add one or more children
- Set up one or more rules for the children on different days and times.
- Install the app on the child phone, login with your credentials and select child mode and then the child who the phone belongs to.
- Once set up the app on the child phone will run automatically on a hourly basis to setup the geofence.
- Sample id for the application: amruta1209@gmail.com (pass)abcd
- Sample child mode: child name: Ajit which belongs to the above id.

In case of any doubts or issues, please email me at amruta1209@gmail.com
