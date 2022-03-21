import pyrebase


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

"""
data ={"configid":"wrewarwrr","3": "False", "5": "True","7": "False","8": "False",
"10": "False","11": "False", "12": "False","13": "False", "15": "False", "16": "False","18": "False","19": "False",
"21": "False", "22": "False","23": "False","24": "False", "26": "False","27": "False","28": "False","29": "False",
"31": "False", "32": "False","33": "False", "35": "False", "36": "False","37": "False","38": "False","40": "False"}
"""
"""
data ={"configid":"wrewarwrr", "1": "False", "2": "False","3": "False","4": "True", "5": "False", "6": "False","7": "False","8": "False","9": "False",
"10": "False","11": "False", "12": "False","13": "False","14": "False", "15": "False", "16": "False","17": "False","18": "False","19": "False","20": "False",
"21": "False", "22": "False","23": "False","24": "False", "25": "False", "26": "False","27": "False","28": "False","29": "False","30": "False",
"31": "False", "32": "False","33": "False","34": "False", "35": "False", "36": "False","37": "True","38": "False","39": "True","40": "True"}

db.child("RaspberryPiConfig").child("wrewrwrar").set(data)
"""
config = db.child("RaspberryPiConfig").get()

#for pin in config:
  #  print(config.val())

for pin in config.each():
     #print(pin.key())
     print(pin.val()["1"])
     print(pin.val()["2"])


