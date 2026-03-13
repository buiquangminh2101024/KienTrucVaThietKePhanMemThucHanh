const Router = require("express").Router;
const router = Router();

const authController = require('../controllers/authController');
const contentController = require('../controllers/contentController');

// Home page
router.get("/", (req, res) => {
  res.render("index");
});

// Auth routes
router.get('/register', authController.showRegister);
router.post('/register', authController.register);
router.get('/login', authController.showLogin);
router.post('/login', authController.login);

// Content routes
router.get('/add-content', contentController.showAddContent);
router.post('/add-content', contentController.addContent);
router.get('/content', contentController.showContent);

module.exports = router;
