import 'package:auth/model/user.dart';
import 'package:auth/preference/user_preference.dart';
import 'package:flutter/material.dart';

class Welcome extends StatelessWidget {
  final User user;
  
  const Welcome({Key key, @required this.user}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: Column(
          children: [
            SizedBox(height: 250.0),
            Center(
              child: Text("WELCOME ${user.name}"),
            ),
            SizedBox(height: 100),
            RaisedButton(
              onPressed: () {
                userPreferences.removeUser();
                Navigator.pushNamedAndRemoveUntil(
                    context, '/login', (route) => false);
              },
              child: Text("Logout"),
              color: Colors.lightBlueAccent,
            )
          ],
        ),
      ),
    );
  }
}
