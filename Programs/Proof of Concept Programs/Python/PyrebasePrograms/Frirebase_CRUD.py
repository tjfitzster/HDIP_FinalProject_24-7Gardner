#Pyrebase tutorial
import pyrebase


firebaseConfig = {
  "apiKey": "AIzaSyAp9ePL5zkDKhvcXOrTANVygg25ZM1TMu8",
  "authDomain": "rpidatabase2.firebaseapp.com",
  "databaseURL": "https://rpidatabase2-default-rtdb.europe-west1.firebasedatabase.app",
  "projectId": "rpidatabase2",
  "storageBucket": "rpidatabase2.appspot.com",
  "messagingSenderId": "477017064476",
  "appId": "1:477017064476:web:489538a7d097af2d504bd9",
  "measurementId": "G-TNHFFDFX6S"
};


firebase = pyrebase.initialize_app(firebaseConfig)


db=firebase.database()


################################################################
#Database
################################################################
#create
data ={"age": "50","firstName": "Jim York","lastName": "true", "userName": "Jim Smith"}
#db.push(data)
db.child("Users").push(data)
#db.child("Users").child("TomTom").set(data)

#update
#db.child("Users").child("TomTom").update({"age": "60"})
people = db.child("Users").get()

for person in people.each():
    #print(person.val())
   # print(person.key())
   if person.val()["userName"] == "TJFITZSTER":
       print("We found you TJ")
       #db.child("Users").child(person.key()).update({"name":"Jane"})
db.child("users").child("-LzqIcMVMPaQKVLLjK5d").remove()