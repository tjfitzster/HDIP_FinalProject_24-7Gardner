import pyrebase


def readDevices(db):
      devices = db.child("Device").get()
      with open("devicesID.Config", 'w') as f:       
        for device in devices.each():    
              #print(device.key())
             # print(device.val())
             f.write(device.key())
             f.write("\n") 
      f.close()
        
     # devicesfile = open("devices.config","a")
     # devicesfile.write("\n")
      #devicesfile.write(str("ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg"))
     # devicesfile.close()


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

#objfreq = db.child("Configuration").child("Frequency (s)").get()
#freq = objfreq.val()




readDevices(db)
