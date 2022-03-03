import pyrebase

def firebaseInit():
    
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
    return db
    
def main():
    firebaseInit()
    db = firebaseInit()
    data ={"age": str("55"), "firstName": "jjjj","lastName": "thisisatest","userName": "hhhhh"}
    
    db.child("Users").push(data)
    
    
if __name__ == "__main__":
    main()