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
#auth=firebase.auth()

#storage=firebase.storage()

"""
################################################################
#Authentication
################################################################
"""
#Login
"""
email = input("Enter your Email: ")
password = input("Enter Your Password: ")
try:
    auth.sign_in_with_email_and_password(email, password)
    print("Succesfully Signed In")
except Exception as e:
    print(e.message)

#Signup
email = input("Enter your Email: ")
password = input("Enter Your Password: ")
confirmPassword = input("Confirm Your Password: ")
if password == confirmPassword:
    try:
        auth.create_user_with_email_and_password(email, password)
        print("Succesfully signed Up")
    except Exception as e:
        print(e)
"""


"""
################################################################
#Storage
################################################################
"""
"""
#filename = input("Enter the name of the file you wish to upload ")
cloudfilename = input("Enter the name of the file  on the cloud ")

#storage.child(cloudfilename).put(filename)

#print(storage.child(cloudfilename).get_url(None))

storage.child(cloudfilename).download("", "output.txt")
"""
################################################################
#Database
################################################################
#create
#data ={"age": "50","firstName": "Jim York","lastName": "true", "userName": "Jim Smith"}
#db.push(data)
#db.child("Users").push(data)
#db.child("Users").child("TomTom").set(data)

#update
#db.child("Users").child("TomTom").update({"age": "60"})
#people = db.child("Users").get()

#for person in people.each():
    #print(person.val())
   # print(person.key())
  # if person.val()["userName"] == "TJFITZSTER":
     #  print("We found you TJ")
       #db.child("Users").child(person.key()).update({"name":"Jane"})

#delete
#db.child("Person").child("Age").remove()

#db.child("Person").remove()
people = db.child("People").get()

for person in people.each():
    print(person.val())
    print(person.key())
    if person.val()["userName"] == "John Smith":
       print("We found you JOhn")
       db.child("People").child(person.key()).child("age").remove()

#read