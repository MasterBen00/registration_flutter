import 'package:auth/model/user.dart';
import 'package:auth/provider/auth_provider.dart';
import 'package:auth/provider/user_provider.dart';
import 'package:auth/util/validators.dart';
import 'package:auth/util/widgets.dart';
import 'package:flushbar/flushbar.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class Register extends StatelessWidget {
  const Register({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final formKey = new GlobalKey<FormState>();

    final TextEditingController _pass = TextEditingController();

    String _username, _email, _password, _confirmPassword;

    AuthProvider auth = Provider.of<AuthProvider>(context);

    final usernameField = TextFormField(
      autofocus: false,
      validator: (value) => value.isEmpty ? "Please enter username" : null,
      onSaved: (value) => _username = value,
      decoration:
          buildInputDecoration("username", Icons.supervised_user_circle),
    );

    final emailField = TextFormField(
      autofocus: false,
      validator: validateEmail,
      onSaved: (value) => _email = value,
      decoration: buildInputDecoration("email", Icons.email),
    );

    final passwordField = TextFormField(
      controller: _pass,
      autofocus: false,
      obscureText: true,
      validator: (value) => value.isEmpty ? "Please enter password" : null,
      onSaved: (value) => _password = value,
      decoration: buildInputDecoration("password", Icons.lock),
    );

    final confirmPassword = TextFormField(
      autofocus: false,
      validator: (value) {
        if (value.isEmpty) {
          return "confirm password is required";
        } else if (value != _pass.text) {
          return "password do not match";
        }
        return null;
      },
      onSaved: (value) => _confirmPassword = value,
      obscureText: true,
      decoration: buildInputDecoration("Confirm password", Icons.lock),
    );

    var loading = Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        CircularProgressIndicator(),
        Text(" Registering ... Please wait")
      ],
    );

    final forgotLabel = Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        FlatButton(
          padding: EdgeInsets.all(0.0),
          child: Text("Forgot password?",
              style: TextStyle(fontWeight: FontWeight.w300)),
          onPressed: () {},
        ),
        FlatButton(
          padding: EdgeInsets.only(left: 0.0),
          child: Text("Sign in", style: TextStyle(fontWeight: FontWeight.w300)),
          onPressed: () {
            Navigator.pushReplacementNamed(context, '/login');
          },
        ),
      ],
    );

    var doRegister = () {
      final form = formKey.currentState;
      if (form.validate()) {
        form.save();
        auth
            .register(_username, _email, _password, _confirmPassword)
            .then((response) {
          if (response['status']) {
            User user = response['data'];
            Provider.of<UserProvider>(context, listen: false).setUser(user);
            Navigator.pushReplacementNamed(context, '/dashboard');
          } else {
            Flushbar(
              title: "Registration Failed",
              message: response.toString(),
              duration: Duration(seconds: 10),
            ).show(context);
          }
        });
      } else {
        Flushbar(
          title: "Invalid form",
          message: "Please Complete the form properly",
          duration: Duration(seconds: 10),
        ).show(context);
      }
    };

    return SafeArea(
      child: Scaffold(
        body: SingleChildScrollView(
          child: Container(
            padding: EdgeInsets.all(40.0),
            child: Form(
              key: formKey,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  SizedBox(height: 150.0),
                  label("Username"),
                  SizedBox(height: 5.0),
                  usernameField,
                  SizedBox(height: 5.0),
                  label("Email"),
                  SizedBox(height: 5.0),
                  emailField,
                  SizedBox(height: 15.0),
                  label("Password"),
                  SizedBox(height: 10.0),
                  passwordField,
                  SizedBox(height: 15.0),
                  label("Confirm Password"),
                  SizedBox(height: 10.0),
                  confirmPassword,
                  SizedBox(height: 20.0),
                  auth.registeredInStatus == Status.Registering
                      ? loading
                      : longButtons("Register", doRegister),
                  SizedBox(height: 5.0),
                  forgotLabel
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}