import pyrebase
from sense_hat import SenseHat


sense = SenseHat()
sense.clear()

config = {
    "apiKey": "",
    "authDomain": "",
    "databaseURL": "",
    "projectId": "",
    "storageBucket": "",
    "messagingSenderId": "",
    "appId": ""
};


firebase = pyrebase.initialize_app(config)


humidity = sense.get_humidity()
temprature = sense.get_temperature()

storage = firebase.storage()
database = firebase.database()
print ("Humidity is: " + str(humidity))
print ("Temprature is: " + str(temprature))
database.child("MEASURMENTS")
data = {"Humidity": humidity,
        "Temprature": temprature}
database.set(data)

