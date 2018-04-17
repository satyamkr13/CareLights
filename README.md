# CareLights
Care Lights is a smart lighting solution for home and work providing cutting edge technology at lower prices. It includes first in industry ‘Reading Mode’ which automatically adjusts the brightness so that user gets the same intensity of light on book irrespective of his/her position in the room. Care Lights won consolation prize in NIT Uttarakhand Innovation Competition and is featured as Top 50 startups at VNIT Nagpur’s Startup Conclave.\n



Being citizens of a developing country like India saving power is an invaluable means of dealing with the problem of growing power demand. So as a pertinent solution we put forward CARE Lights to help control the problem of energy wastage. Not only the energy will be conserved by turning it on and off as required, but also we will increase the life of the lighting equipments. More precisely the on and off action is imperceivable by the eyes reducing the brightness , as opposed to shunts that wastes energy.
According to a survey by US Now nearly 66 percent energy employed for lighting is wasted due to our ignorance, one simply forgets to turn the lights off, while walking out of a room. Our Project, as it may be interpreted from the name itself focuses mainly on switching on light  when needed  and as much needed. 
It is an artificially intelligent lighting system that keeps the count of the number of people inside the room by processing data obtained from  PIR(Passive Infra -Red) sensor HC-SR501 Pyroelectric Infrared Module and Ultrasonic Sensor i.e. HCSR-04,the former makes sure if a human has entered the room and the later keeps the count of the number of people. These are installed at the entrance of the room. Microcontroller turns on the lights automatically when someone enters the room.  As soon as the count of people becomes zero lights are automatically turned off. Thus saving that 66% hence this serves the purpose of lights   when needed .And saves you from unknowingly wasting energy.
And for the second part that is, lights will be giving you desirable intensity. Basically the room will stay at the same ambiance as preset by the user. Now, what percentage of the light is the sunlight is the AI’s job to decide.The software burnt into the microcontroller will measure how bright is it outside the room or how much of light intensity is entering through the windows. The windows of a room will be enabled with light intensity sensors (LDR) they will send intensity data to the arduino . That will allow the intensity calculations and thus the interior lights will be brightened and dimmed accordingly.
CARE System uses a bluetooth module for pairing up with smartphones, and saves user preference to an SD Card which allows Care Lights to function intelligently even when a smartphone is not in range.
Our lightning system can be controlled through a mobile application. The app makes it very easy for Care lighting to be installed almost in all types of room. Users can easily install the system on their own with the help of app, which includes calibration of the ultrasonic sensors for the width of door and ambient light sensors. These calibrations need to be done only once to make sure that you get the perfect lightning for years to come. The app will also allow you to adjust brightness in three modes viz. manual, semi-automatic and fully automatic. For people who have a habit of waking up at night they'll be able to turn on the lights at with just a tap of a button. The lights will also be turned on automatically when you’ll wake up in the morning and their intensity would be increased gradually to making it easy for our eyes to accommodate. You can also reduce the amount of blue light emitted during night (manually adjustable time) with the help of slider shown below.  \n
  
And the data relayed from the light intensity sensor will be clubbed with the lights to give you the required intensity at minimum input. Thus the LED will be dimmed and brightened accordingly. And as the relation between power consumed and width modulated leds’ brightness is linearly related to the power consumption so, we suffer low power consumption. 
Components:
•	Passive Infrared Sensor HCSR 501 
•	Ultrasonic Sensor and receiver HC SR04 (Two of them)
The concept behind counting is that, say at the beginning the room was empty so we have a count variable is storing a numerical value 0 , now as soon as a person enters the room an on signal is relayed to the microcontroller, and the count increases by 1, and thus becomes one.
Now the question arises what if the people walk in in simultaneously will they be able the fool the count yes they will however if there are heat signatures in the room the lights will remain turned on even if the count becomes zero. So if two people enter simultaneously side by side the count becomes one, now if one of them leaves the count becomes zero, but still the lights will not be turned off unless there is a heat signature .We will install two ultrasonic sensors  for  detection of motion of human (whether he enters the room or moves out)
\n
CONCEPT BEHIND AMBIANCE CONTROL:
Components Used:\n
•	 IC only For the Prototyping Purpose used is LM2621, for realisation purposes higher ICs performing AC to Dc operations maybe used 
\n•	LDR Sensors 
\n•	12 V Leds (For Prototyping purpose)
\nConcept For dimming is to manage the intensity by using analog signals, the Duty Cycles , explained below are managed by modulating the signals, 
\nPulse width modulation
During the 20,000 microseconds we have to turn the LED either on or off depending on the required duty-cycle so, for example, a 75% duty-cycle requires the pulse to be on for 15,000 microseconds and then off for 5,000 microseconds.
The duty-cycle refers to the total amount of time a pulse is ‘on’ over the duration of the cycle, so at 75% brightness the duty-cycle of the LED is 75%. 
Hence with such a technique, if the LED runs at a certain duty cycle only that percentage of the of the power, hence that amount of intensity, is used if little losses are ignored we can get really neat results.
\n
DETAILS REGARDING THE “COUNTING SYSTEM” OF THE ROOM:
\nAlgorithm regarding counting the number of people coming in and going out of the room:
We are using PIR(Passive Infra -Red) sensor Pyroelectric Infrared Module and an Ultrasonic sensor HC SR04 for human detection.Its not consuming any energy (its energy efficient ) ,when human or pet enter room their body heat radiated in form of infrared wave is used by sensor to send signal to our arduino microcontroller .PIR sensor is installed in front of door , as a human passes by it sends high or ON signal to our board than light will glow with intensity according to the algorithm of intensity discussed below ,user can also manually control light  bulb intensity by using their mobile application discussed below  .We are also using counting variable ch to count number of humans entered the room  ,if no human is inside the room bulb will remain switched off .PIR sensor is used to check human passes by through the sensor then our algorithm will count number of human enter and exit from room ,and for no human light will not glow and in presence of single human too light will glow .
To check for human entering the room or exiting out of room 
We will install two ultrasonic sensors  for  detection of motion of human (whether he enters the room or moves out )
DETAILS REGARDING THE ANDROID APPLICATION AND BLUETOOTH CONNECTIVITY ASSOCIATED WITH THE CARE LIGHT:
Our mobile app currently supports smartphones running Android 4.0.3 (IceCream Sandwitch) or above. It is designed with help of Android Studio, and is written in XML and Java. The app would use the bluetooth of the phone to control the lights wirelessly.
CARE System uses HC-05 an embedding bluetooth module for pairing up with smartphones to be controlled from our app and a Break out MicroSD board which hosts a micro SD card for data logging that saves the user preferences.
HC-05 bluetooth module has an inbuilt antenna for wireless serial connection.The CARE system auto reconnects to the last device on power as default and auto-reconnects in 30 mins when disconnected as a result of beyond the range of connection.The input commands from the smartphone is received by serial data transmission and sent to the microcontroller for further action processing as per the requirement.
A SD card is used in CARE lighting system for data logging.The SD card is connected to arduino by using an Micro SD breakout board.This board consists of CS/SS[Chip Select/Slave Select] pin along with DI[Data Input] and DO[Data Output] and CLK[SPI CLocK] pin.These are connected to hardware SPI pins on the microcontroller,since Serial Peripheral Interface[SPI] provides a lot faster data transfer rate.

\nDETAILS REGARDING THE AMBIANCE CONTROL SYSTEM OF THE ROOM:
\nCARE lights use bulb dimming and brightening using PWM technique for reduced power Consumption.
As explained in the section before this.
The intensity varies linearly with the voltage supplied although the arduino is only operable between 0-5 V. But the voltage can be increased using the LM2621 is a high efficiency, step-up DC-DC switching regulator for battery-powered and low input voltage systems. It accepts an input voltage between 1.2 V and 14 V and converts it into a regulated output voltage. The output voltage can be adjusted between 1.24 V and 14 V. It has an internal 0.17-Ω N-Channel MOSFET power switch. Efficiencies up to 90% are achievable using the LM2621 thus leds working upto any voltage can be obtained using a suitable IC.
Now using pulse width modulation the voltage supply of the arduino is switched on and off thus reducing intensity and in turn the power consumption.


\nAlgorithm for dimming using PWM:
\n1.	Start. 
2.	The intensity is preset by the user.
3.	At that point the duty cycle of the LED is stored, in a variable D, which is linearly and directly proportional to the power delivered by the light bulb.D is saved in D_backup.
4.	An intensity sensor is setup at a particular position in the room as per the user’s wish at a distance say r,  from the bulb,but should be facing the bulb,and its reading is stored in a variable I. And this I is saved in a variable say I_backup.
            From here onwards the code goes in an Infinite loop unless calibration takes place again.
5.	Now another variable keeps storing the intensity in regular intervals of time ,say I_temp.
6.	If there is a change of more than 5 percent, then the change is stored in a variable ch.   
7.	And now if the Duty Cycle D of the LED of accordingly increased or decreased until the lux count comes back to normal.



\nFuture scope  
\nInternet is the need of the hour, hence soon our system would be able to connect to the internet through Wi-Fi for remote controlling of different appliances. CARE lightning would continue to use bluetooth for faster controlling and offline compatibility. The app will also connect with other apps and services in future making room for further automations. For example the app will pull up driving data from Google Maps and your geyser will be switched on automatically 15 minutes before your arrival at home.
\nOver the Air Updates would ensure that CARE lightning gets better day by day. We would be constantly improving our algorithms for better functioning of the system, and lower power consumption. These updates will be rolled out with the help of companion app.
\nIncreased App Compatibility would allow users to control their lightning system from majority of devices like iOS, Windows, Linux and even from Web. 

