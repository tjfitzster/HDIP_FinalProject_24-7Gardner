#Pyrebase Authentication
import pyrebase

firebaseConfig = {
  "apiKey": "AIzaSyBcbLqGgsB4ZqpT-HVL8miDF0qSAOkD0LM",
  "authDomain": "fir-auth-f1f14.firebaseapp.com",
  "projectId": "fir-auth-f1f14",
  "storageBucket": "fir-auth-f1f14.appspot.com",
  "messagingSenderId": "884348390673",
  "databaseURL": "https://fir-auth-f1f14-default-rtdb.europe-west1.firebasedatabase.app/",
  "appId": "1:884348390673:web:1a477771db145e991b50b0",
  "measurementId": "G-V58SR6H9WL"
};

firebase = pyrebase.initialize_app(firebaseConfig)

auth=firebase.auth()

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