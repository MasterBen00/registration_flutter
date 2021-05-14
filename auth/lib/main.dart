import 'package:auth/model/user.dart';
import 'package:auth/preference/user_preference.dart';
import 'package:auth/provider/auth_provider.dart';
import 'package:auth/provider/user_provider.dart';
import 'package:auth/screens/dashboard.dart';
import 'package:auth/screens/login.dart';
import 'package:auth/screens/register.dart';
import 'package:auth/screens/welcome.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => AuthProvider()),
        ChangeNotifierProvider(create: (context) => UserProvider()),
      ],
      child: MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter auth Demo',
      theme: ThemeData(
          primarySwatch: Colors.deepPurple,
          visualDensity: VisualDensity.adaptivePlatformDensity),
      home: MyHomePage(),
      routes: {
        '/dashboard': (context) => DashBoard(),
        '/login': (context) => Login(),
        '/register': (context) => Register(),
      },
    );
  }
}

class MyHomePage extends StatelessWidget {
  const MyHomePage({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Future<User> getUserData() => UserPreferences().getUser();

    return FutureBuilder(
        future: getUserData(),
        builder: (context, snapshot) {
          switch (snapshot.connectionState) {
            case ConnectionState.none:
            case ConnectionState.waiting:
              return CircularProgressIndicator();
            default:
              if (snapshot.hasError)
                return Text('Error: ${snapshot.error}');
              else if (snapshot.data.token == null)
                return Login();
              else
                //UserPreferences().removeUser();
              return Welcome(user: snapshot.data);
          }
        });
  }
}
