
db = db.getSiblingDB('d_easy_db')
db.createUser({
    user: "d_easy_user",
    pwd: "123qwe",
    roles: [
        { role: "readWrite", db: "d_easy_db" }
    ]
});
db.createCollection("orders");
db.createCollection("payments");
db.createCollection("accounts");


var testOrder = {
    "_id": "387b39ca-a30c-4501-be3c-f88a90ed1413",
    "name": "pizza, sushi, drinks",
    "description": "Some description and wishes",
    "totalPrice": 3000.00,
    "totalPriceAfterDiscount": 2662.00,
    "totalWeight": 6.9,
    "totalVolume": 274500.0,
    "consumerAddress": "some street, some house, some flat",
    "restaurantAddress": "some street, some building",
    "orderPositions": [
        {
            "name": "pizza",
            "quantity": 3,
            "price": 400.00,
            "discount": 10,
            "weight": 0.5,
            "width": 40.0,
            "length": 40.0,
            "height": 5.0,
            "additionalInfo": "Some wishes"
        },
        {
            "name": "sushi",
            "quantity": 2,
            "price": 700.00,
            "discount": 13,
            "weight": 1.5,
            "width": 35.0,
            "length": 51.0,
            "height": 60.0,
            "additionalInfo": "Some wishes"
        },
        {
            "name": "drinks",
            "quantity": 4,
            "price": 100.00,
            "discount": 9,
            "weight": 0.6,
            "width": 15.0,
            "length": 11.0,
            "height": 55.0,
            "additionalInfo": "Some wishes"
        }
    ],
    "checkStatuses": [],
    "checkDetails": {},
    "paymentStatus": "NEW",
    "transactionId": null,
    "created": "2020-07-05T17:24:18.232",
    "updated": "2020-07-05T17:25:12.569"
}

var account1 = {
    "_id": "930019b8-eef5-42cc-b28e-929e88593763",
    "bankAccountNumber": "123",
    "holder": {
        "firstName": "iDOAtkyJpJ",
        "lastName": "lvdlTGcMhw",
        "phone": "1726865682"
    },
    "balance": "14470.00"
}

var account2 = {
    "_id": "3056338e-2c4f-41ed-b430-47461f5bbd62",
    "bankAccountNumber": "456",
    "holder": {
        "firstName": "qBjAikZdXa",
        "lastName": "XxpXVcDeky",
        "phone": "0626405471"
    },
    "balance": "2980.00"
}

db.runCommand(
    {
        insert: "orders",
        documents: [testOrder]
    }
);

db.runCommand(
    {
        insert: "accounts",
        documents: [account1, account2]
    }
);

