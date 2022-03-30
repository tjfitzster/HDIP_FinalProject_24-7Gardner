
import pyrebase


def firebaseInit():
    
    firebaseConfig = {
  "apiKey": "AIzaSyA0nZc9Rz-q5BGob_SeICUvYhreUvnLVeo",
  "authDomain": "finalproject-83f4a.firebaseapp.com",
  "databaseURL": "https://finalproject-83f4a-default-rtdb.europe-west1.firebasedatabase.app",
  "projectId": "finalproject-83f4a",
  "storageBucket": "finalproject-83f4a.appspot.com",
  "messagingSenderId": "714618999720",
  "appId": "1:714618999720:web:88509c12285efaac59839d",
  "measurementId": "G-TNHFFDFX6S"
};
    
    firebase = pyrebase.initialize_app(firebaseConfig)
    db=firebase.database()
    return db

db = firebaseInit()

data = {"DeviceType": "2", "DeviceName": "DHT22",  "MeasurmentPin": "1", "DeviceAddress": "0"}
db.child("Devices").push(data)
data = {"DeviceType": "2", "DeviceName": "DHT22",  "MeasurmentPin": "3", "DeviceAddress": "0"}
db.child("Devices").push(data)
data = {"DeviceType": "2", "DeviceName": "DHT22",  "MeasurmentPin": "2", "DeviceAddress": "0"}
db.child("Devices").push(data)
