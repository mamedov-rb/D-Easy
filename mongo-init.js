
db.createUser(
        {
            user: "order-user",
            pwd: "order-password",
            roles: [
                {
                    role: "readWrite",
                    db: "order-db"
                }
            ]
        }
)

db.users.insert({
  name: "order-user"
})      
     
