db.createUser(
    {
        user: "lgaricoix",
        pwd: "010133",
        roles: [
            {
                role: "readWrite",
                db: "myclinicpay"
            }
        ]
    }
)