"""
NAme: Main Python Program IOT Project.
Student: TJ Fitzpatrick
Student NUmner: 20027865
"""
##############
#   Imports
##############
from datetime import datetime
import time, os, requests
from termcolor import colored
import random
import pyrebase
from datetime import datetime
import time
import os
import logging

##############
#   Functions
##############
def testInternet_connection():
    os.system('color')
    url = 'https://www.google.com/'
    timeout = 2
    op = None
    now = datetime.now()
    try:
        op = requests.get(url, timeout=timeout).status_code
        if op == 200:
            print(now, colored("Internet Connected", "green"))
            return True
        else:
           # print(now, colored("Status Code is not 200", "red"))
            #print("status Code", op)
            return False
    except:
        print(now, colored("No Internet Connected", "red"))
       # print("status Code", op)
        return False

def soilMoisture():
    return random.randint(0, 255)

def gasSensor():
    return random.randint(0, 255)

def getfreq(db):
  try:   
    objfreq = db.child("Configuration").child("Frequency (s)").get()
    freq = objfreq.val()
  
    if (freq < 2):
        freq = 2
    else:
        pass
  except: 
      freq = 10
      
  return freq

def takeDeviceMeasurment(devPinNo):
    return random.randint(0, 255)
    

def readDevices(db):
      allDevices = db.child("Devices").get()
      for device in allDevices.each():    
              devkey = str(device.key())
              devAttrs = db.child("Devices").child(devkey).get()
              for attrib in devAttrs.each():
                  if(str(attrib.key()) == "DeviceType"):
                        if(str(attrib.val()) == str(1)): #DHT22
                            value = takeDeviceMeasurment(str(attrib.val()))
                            print("Device:" + str(devkey) + " Humidity: " + str(value))
                        elif(str(attrib.val()) == str(1)): # MQ11
                            value = takeDeviceMeasurment(str(attrib.val()))
                            print("Device:" + str(devkey) + " Temprature: " + str(value))
                        elif(str(attrib.val()) == str(3)): # Soil Moisture
                            value = takeDeviceMeasurment(str(attrib.val()))
                            print("Device:" + str(devkey) + " Soil Moisture: " + str(value))
                            
                  

def lightSensor():
    return random.randint(0, 255)

def getTimestamp():
    ct = datetime.now()
    return str(ct)

def readLogfile():
    try:
        datalog = open("dataLog.log", "r")
        data = datalog.read()
        data = data.strip()
        datalog.close()
        return data
    except:
        datalog = open("dataLog.log","a")
        datalog.close()
    
##############
#   Standard Setup
##############

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
##############
#   Main Program
##############
def main():
    if(testInternet_connection()):
        db = firebaseInit()
        freq = getfreq(db)
        #readDevices(db)
        while(True): 
            if (testInternet_connection()):
                connectionFlag = True  
                readDevices(db)
                #data = {"Timestamp": getTimestamp(), "Moisture": str(soilMoisture()),"Light": str(lightSensor()),"Gas": str(gasSensor())}
               # db.child("Historical Sensor Values").push(data)
              #  db.child("Latest Measurment").set(data)
                time.sleep(freq)  
            else: 
                connectionFlag = False
                #logfile = open("dataLog.log","a")
                #logfile.write("\n")
                #logfile.write(str(getTimestamp()) +"," + str(soilMoisture()) + "," + str(lightSensor()) + "," + str(gasSensor()))
               # logfile.close()
                time.sleep(freq)  
                
    else:
          print("Not Connected")
          input("Exiting Program")
  #  logFlag = False
  #  db = firebaseInit()
     # SET CONNECTION FLAG
  #  while(True): 
  #      if (testInternet_connection()):   
     #       if(not connectionFlag):
  #              data = readLogfile()
        #        indexedData = data.split("\n")
           #     open("dataLog.log", "w").close()
                   
         #       for line in indexedData:
          #          splitline = line.split(",")
          #          timestamp = splitline[0]
             #       Moisture = splitline[1]
           #         light = splitline[2]
           #         gas = splitline[3]
                    
            #        data ={"Timestamp": timestamp, "Moisture": Moisture,"Light": light,"Gas": gas}
             #       db.child("Historical Sensor Values").push(data)
                    
           #     connectionFlag = True
             #   #read log file
            #    #delete log file
            
         #   data ={"Timestamp": getTimestamp(), "Moisture": str(soilMoisture()),"Light": str(lightSensor()),"Gas": str(gasSensor())}
          #  db.child("Historical Sensor Values").push(data)
           # db.child("Latest Measurment").set(data)
         
            
       # else:
        #     connectionFlag = False
         #    logfile = open("dataLog.log","a")
          #   logfile.write("\n")
           #  logfile.write(str(getTimestamp()) +"," + str(soilMoisture()) + "," + str(lightSensor()) + "," + str(gasSensor()))
            # logfile.close()
             
    #time.sleep(10)         
        # Setup connection with a Garden. 
        # setup a COnnection with sensors. 
     

    exit()
    
if __name__ == "__main__":
    main()


