class User {
  String userId;
  String name;
  String email;
  String type;
  String token;

  User({this.userId, this.name, this.email, this.type, this.token});

  factory User.fromJson(Map<String, dynamic> responseData) {
    return User(
        userId: responseData['userId'],
        name: responseData['userName'],
        email: responseData['email'],
        type: responseData['type'],
        token: responseData['authenticationResponse']['accessToken']);
  }
}
