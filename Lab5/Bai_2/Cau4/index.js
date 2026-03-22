const express = require("express")
const app = express()

app.use(express.static("./"))
app.set("view engine", "ejs")
app.set("views", "./")

app.get("/", (req, res) => {
    res.render("index")
})

app.listen(3000, () => {
    console.log("Server is running at http://localhost:3000");
})