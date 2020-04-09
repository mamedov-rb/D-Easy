
db = db.getSiblingDB('d_easy_db')
db.createUser({
    user: "d_easy_user",
    pwd: "123qwe",
    roles: [
        {role: "readWrite", db: "d_easy_db"}
    ]
});
db.createCollection("orders");
db.createCollection("order_check_details");
