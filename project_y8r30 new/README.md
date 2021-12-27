### CPSC 210: Personal Project

# *- Adventure -*

Welcome to my adventurous project! The name itself doesn't quite do its job in explaining what the application does— 
dive in with me about the details!

+ ***Why** is this project of interest?*  
Indecisiveness is a common disease— hard to cure and limits the excitement that an adventurous choice can bring. The 
idea of this project sprouts from the fact that I am also a major victim of the disease. If someONE can't make the 
decision for me, why not someTHING?


+ ***What** will the application do?*  
This application uses the power of randomness to help the users make adventurous choices. From the next restaurant to 
visit, to the next hiking location, the user is able to keep track of the possible adventures they might want to try 
in the future. When it is time to make a decision, it takes a list of choices, and generates a decision for the user. 


+ ***Who** will use the application?*  
From children, to adults, anyone is free to go on their *Adventure*! The application is open for those who craves 
a little thrill in their choices. 

#User Story
+ As a user, I want to be able to make a wishlist of different subjects.
+ As a user, I want to be able to add a new subject to the wishlist.
+ As a user, I want to be able to remove a subject from the wishlist (including all its options).
+ As a user, I want to be able to make a list of options within a wishlistSubject.
+ As a user, I want to be able to add new option to an existing list of options.
+ As a user, I want to be able to remove a subjectOption from a list of options.
+ As a user, I want to be able to categorize the options.
+ As a user, I want to be able to give a rating to each of the options.
+ As a user, I want to be able to generate a random decision from a list of options.
+ As a user, I want to be able to generate a random decision from a list of options based on category.
+ As a user, I want to be able to generate a random decision from a list of options based on rating.

+ As a user, I want to be able to save my wishlist to a file.
+ As a user, I want to be able to load my wishlist from file.

##Phase 4: Task 2
Thu Nov 25 00:54:51 PST 2021 </br>
Added WishlistSubject: food

Thu Nov 25 00:54:56 PST 2021 </br>
Added WishlistSubject: travel

Thu Nov 25 00:55:01 PST 2021 </br>
Accessing WishlistSubject with name: food

Thu Nov 25 00:55:13 PST 2021 </br>
Added Option: sushi  |  japan  |  3

Thu Nov 25 00:55:22 PST 2021 </br>
Added Option: pho  |  viet  |  3

Thu Nov 25 00:55:40 PST 2021 </br>
Added Option: kimchi  |  korea  |  5

Thu Nov 25 00:55:58 PST 2021 </br>
Removed Option: pho


##Phase 4: Task 3

If I had more time to work on this project, I would try to make the SubjectUI class
extend the Wishlist UI class. While I was working on my code, I realized there are
multiple similarities in the helper methods between two classes, where the SubjectUI 
class can definitely use by extending the WishlistUI class.

