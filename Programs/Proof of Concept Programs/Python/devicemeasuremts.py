import pyrebase
from datetime import datetime

def readDeviceMeasurment(db):
    try:
        datalog = open("deviceMeasurments.csv", "r")
        data = datalog.read()
        data = data.strip()
        datalog.close()
        indexedData = data.split("\n")
        for x in range(len(indexedData)):    
           comma_seperated_indexed_data = indexedData[x].split(",")
           data = {"Timestamp": comma_seperated_indexed_data[2],"DeviceID": comma_seperated_indexed_data[1], "Device": comma_seperated_indexed_data[0], "Unit": comma_seperated_indexed_data[1], "Value": comma_seperated_indexed_data[4]}
           db.child("SensorMeasurments").push(data)
        
        open("deviceMeasurments.csv", "w").close()
      
    except:
        print("Error uploading CSV file")

    
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
readDeviceMeasurment(db)