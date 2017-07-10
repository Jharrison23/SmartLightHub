# SmartLightHub

This is a Senior Design Project which is a Smart Home automation lighting system simliar to Philips Hue. This is an android application which controls RGB three LED strips

### This Android application allows the users to:

#### Create an account

* Using Googles Firebase for Authentication a user can create an account and store some basic information such as
  * Name
  * Email
  * UserName
  * Password
  * The user will also give the three lights their initial name

<a href="https://github.com/Jharrison23/Resources/blob/master/CreateUser.gif"><img src="https://github.com/Jharrison23/Resources/blob/master/CreateUser.gif" title="Create User"/></a>

#### Login
* Using Googles Firebase for authentication a user can login with the email and password after creating an account.

<a href="https://github.com/Jharrison23/Resources/blob/master/LoginPage.gif"><img src="https://github.com/Jharrison23/Resources/blob/master/LoginPage.gif" title="Login"/></a>

#### Control Main Lights
* From the Home page a user has a couple different options
  * Short click: Brings up a color picker, which allow the user to change the color of the light
  * Change the state(on/off) by clicking the switch for that light
  * Long click: Brings up a page with the full light information including the name, color and state to allow the user to edit all the information from one view

<a href="https://github.com/Jharrison23/Resources/blob/master/ChangeColors.gif"><img src="https://github.com/Jharrison23/Resources/blob/master/ChangeColors.gif" title="Main Light Control"/></a>

#### Moods
* A type of preset lights settings, a Mood Preset is created by:
  1. Importing an image from the phones gallery
  2. To select the color, the user will slide thier finger over the pixel with the desired color
  3. If needed change the name and state of the lights

<a href="https://github.com/Jharrison23/Resources/blob/master/MoodCreation.gif"><img src="https://github.com/Jharrison23/Resources/blob/master/MoodCreation.gif" title="Mood Creation"/></a>

#### Custom Presets
* The other type of preset a user can create is a custom preset, this is created by manually selecting the colors for each light in the preset
 
<a href="https://github.com/Jharrison23/Resources/blob/master/CustomPresets.gif"><img src="https://github.com/Jharrison23/Resources/blob/master/CustomPresets.gif" title="Custom Presets"/></a>

### Presets Tab
* From the presets tab the user can do a few things:
  * Select a preset and update the main lights by simply clicking the presets name
  * Adjust each presets lights quickly by clicking on the desired color and a color picker will pop up
  * Delete the preset by performing a long click on the name
  * Load the presets light information by performing a long click on the presets color
  
### Routines
* Users can create alarms, to complete change the states of the lights at a specific time

### Database
This application uses Googles Firebase for the Realtime Database and User authentication.

### PubNub
Also uses pubnub for publish and subscribe service to allow the user to control the lights from any location. 
