db.createUser(
  {
    user: "test",
    pwd: "test_pwd",
    roles: [
      {
        role: "readWrite",
        db: "testDB"
      }
    ]
  }
)
