# CalorieCounter
You need to create a Firebase project and download the google-services.json
Then you need to create the authentication,realtime database and cloud firestore.
Realtime database and cloud firestore create in test mode
Rules for realtime database : {
  "rules": {
    ".read": true,
    ".write": true,
  }
}

Rules for clour firestore : 

rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth!=null;
    }
  }
}
